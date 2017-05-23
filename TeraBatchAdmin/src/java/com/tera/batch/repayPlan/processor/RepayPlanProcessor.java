package com.tera.batch.repayPlan.processor;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.batch.errorLog.dao.BatchErrorLogDao;
import com.tera.batch.errorLog.model.BatchErrorLog;
import com.tera.batch.exception.BatchProcessException;
import com.tera.bpm.constant.BpmConstants;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.service.CreditSignService;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

public class RepayPlanProcessor implements ItemProcessor<LoanRepayPlan, String> {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(RepayPlanProcessor.class);
	
    private LoanRepayPlanService loanRepayPlanService;
    
    private BatchErrorLogDao<BatchErrorLog> batchErrorLogDao;
   
   
	private ContractService contractService;
    
    
	private ProductService<Product> productService;
    
    
	private PaymentService<Payment> paymentService;
	
	private AccounttingService<Accountting> accounttingService;
	
	private CreditSignService creditSignService;
	
	/**
     * 对取到的超期还款计划进行简单的处理。
     * 
     * @param student
     *            处理前的数据。
     * @return 处理后的数据。
     * @exception Exception
     *                处理是发生的任何异常。
     */
    @Override
    public String process(LoanRepayPlan loanRepayPlan) throws Exception {
    	try {
			//超期
			Map<String, Object> mapCon=new HashMap<String, Object>();
			mapCon.put("contractNo", loanRepayPlan.getContractNo());
			List<Contract> listContracts=contractService.queryList(mapCon);
			
			Contract contract=listContracts.get(0);
			Map<String, Object> mapPro=new HashMap<String, Object>();
			mapPro.put("name", contract.getLoanProduct());
			List<Product> listProducts=productService.queryList(mapPro);
			
			Product product=listProducts.get(0);
			
			String loanAppId=contract.getLoanAppId().trim();
    		//抵押贷
    		if(loanAppId.startsWith("B")) {
				double db=contract.getLoanAmount()*product.getPenaltyRate()/100;
				int dayRange=DateUtils.getDayRange(loanRepayPlan.getRepaymentDate(), DateUtils.getDateNow());
				db=db*dayRange;
				loanRepayPlan.setPenaltyReceivable(db);
				loanRepayPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				// 应收金额
				double dbAmount=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
				
				//更新payment表中数据
				Map<String, Object> mapPay=new HashMap<String, Object>();
				mapPay.put("contractNo", loanRepayPlan.getContractNo());
				mapPay.put("periodNum", loanRepayPlan.getPeriodNum());
				mapPay.put("subject", "收利息");
				List<Payment>  listPayments=paymentService.queryList(mapPay);
				if (listPayments.size()==0||listPayments==null) {
					mapPay.put("subject", "收本息");
					listPayments=paymentService.queryList(mapPay);
				}
				if (listPayments.size()==0||listPayments==null) {
					return null;
				}
				Payment payment=listPayments.get(0);
				payment.setPlanAmount(dbAmount);
				payment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				paymentService.update(payment);
				
				//记账
				if ("0".equals(loanRepayPlan.getDefaultFlag())) {
					
					Accountting account=new Accountting();
					account.setInOut("2");
					account.setContractNo(loanRepayPlan.getContractNo());
					account.setSource("");
					account.setPeriodNum(loanRepayPlan.getPeriodNum());
					account.setState("1");
					account.setOperator(BpmConstants.SYSTEM_SYS);
					account.setOrgId("");
					long time=System.currentTimeMillis();
					account.setCreateTime(new Timestamp(time));
					account.setUpdateTime(new Timestamp(time));
					//记账。风险金补利息
					account.setId(0);
					account.setAccount("风险金");
					account.setSubject("业务往来-风险金补利息");
					account.setPlanAmount(loanRepayPlan.getInterestReceivable());
					account.setActualAmount(loanRepayPlan.getInterestReceivable());
					accounttingService.add(account);
					
					//记账。风险金补本金
					if (loanRepayPlan.getPrincipalReceivable()>0){
						//本金不为0，是最后一期
						account.setId(0);
						account.setAccount("风险金");
						account.setSubject("业务往来-风险金补本金");
						account.setPlanAmount(loanRepayPlan.getPrincipalReceivable());
						account.setActualAmount(loanRepayPlan.getPrincipalReceivable());
						accounttingService.add(account);
					}
					loanRepayPlan.setDefaultFlag("1");	
				}
    		} else if(loanAppId.startsWith("C")) {
    			//TODO 信用贷计算罚息和还款; 风险金补利息和本金需要拆分
				int dayRange=DateUtils.getDayRange(loanRepayPlan.getRepaymentDate(), DateUtils.getDateNow());
				//罚息
				double db=creditSignService.getFxje(contract.getLoanAmount(), product, dayRange);
				//滞纳金
				double znAmount=creditSignService.getZnje(
						MathUtils.add(
									loanRepayPlan.getPrincipalReceivable()
									,loanRepayPlan.getInterestReceivable()
						), product);
				//滞纳金 不足100 按照100计算
				znAmount=znAmount>100?znAmount:100.0;
				
				loanRepayPlan.setPenaltyReceivable(db);		//罚息
				loanRepayPlan.setDelayReceivable(znAmount);	//滞纳金
				loanRepayPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				
				//应收金额
				double dbAmount=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
				
				//更新payment表中数据
				Map<String, Object> mapPay=new HashMap<String, Object>();
				mapPay.put("contractNo", loanRepayPlan.getContractNo());
				mapPay.put("periodNum", loanRepayPlan.getPeriodNum());
				mapPay.put("subject", "收本息");
				List<Payment>  listPayments=paymentService.queryList(mapPay);
				
				if (listPayments.size()==0||listPayments==null) {
					return null;
				}
				Payment payment=listPayments.get(0);
				payment.setPlanAmount(dbAmount);
				payment.setActualAmount(dbAmount);
				payment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				paymentService.update(payment);
				
				//记账
				if ("0".equals(loanRepayPlan.getDefaultFlag())) {
					
					Accountting account=new Accountting();
					account.setInOut("2");
					account.setContractNo(loanRepayPlan.getContractNo());
					account.setSource("");
					account.setPeriodNum(loanRepayPlan.getPeriodNum());
					account.setState("1");
					account.setOperator(BpmConstants.SYSTEM_SYS);
					account.setOrgId("");
					Timestamp time=new Timestamp(System.currentTimeMillis());
					account.setCreateTime(time);
					account.setUpdateTime(time);
					
					//记账。风险金补利息
					account.setId(0);
					account.setAccount("风险金");
					account.setSubject("业务往来-风险金补利息");
					account.setPlanAmount(loanRepayPlan.getInterestReceivable());
					account.setActualAmount(loanRepayPlan.getInterestReceivable());
					accounttingService.add(account);
					
					//本金不为0，是最后一期
					account.setId(0);
					account.setAccount("风险金");
					account.setSubject("业务往来-风险金补本金");
					account.setPlanAmount(loanRepayPlan.getPrincipalReceivable());
					account.setActualAmount(loanRepayPlan.getPrincipalReceivable());
					accounttingService.add(account);
					loanRepayPlan.setDefaultFlag("1");	
				}
    		}
			loanRepayPlanService.update(loanRepayPlan);
    	} catch (Exception e) {
    		throw new BatchProcessException(e);
		}
    	return null;
    }

	public LoanRepayPlanService getLoanRepayPlanService() {
		return loanRepayPlanService;
	}
	
	public void setLoanRepayPlanService(LoanRepayPlanService loanRepayPlanService) {
		this.loanRepayPlanService = loanRepayPlanService;
	}

	public BatchErrorLogDao<BatchErrorLog> getBatchErrorLogDao() {
		return batchErrorLogDao;
	}
	
	public void setBatchErrorLogDao(BatchErrorLogDao<BatchErrorLog> batchErrorLogDao) {
		this.batchErrorLogDao = batchErrorLogDao;
	}

	public ContractService getContractService() {
		return contractService;
	}
	
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	public ProductService<Product> getProductService() {
		return productService;
	}

	public void setProductService(ProductService<Product> productService) {
		this.productService = productService;
	}

	public PaymentService<Payment> getPaymentService() {
		return paymentService;
	}

	public AccounttingService<Accountting> getAccounttingService() {
		return accounttingService;
	}

	public void setAccounttingService(
			AccounttingService<Accountting> accounttingService) {
		this.accounttingService = accounttingService;
	}

	public void setPaymentService(PaymentService<Payment> paymentService) {
		this.paymentService = paymentService;
	}

	public CreditSignService getCreditSignService() {
		return creditSignService;
	}

	public void setCreditSignService(CreditSignService creditSignService) {
		this.creditSignService = creditSignService;
	}
	
}
