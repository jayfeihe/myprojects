package com.tera.contract.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.constant.BpmConstants;
import com.tera.collection.phone.service.CollectionBatchService;
import com.tera.contract.model.Contract;
import com.tera.contract.model.form.PaymentManageMsgBean;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.service.CreditDecisionService;
import com.tera.credit.service.CreditSignService;
import com.tera.match.model.MatchResult;
import com.tera.match.service.MatchResultService;
import com.tera.payment.constant.AllinpayConstant;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.model.ThirdPaymentLog;
import com.tera.payment.pay.PayService;
import com.tera.payment.service.AllinPayTranxService;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;
import com.tera.payment.service.ThirdPaymentService;
import com.tera.product.model.Product;
import com.tera.product.model.ProductFeeRate;
import com.tera.product.service.ProductFeeRateService;
import com.tera.product.service.ProductService;
import com.tera.sys.model.ResultObj;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

/**
 * 
 * <br>
 * <b>功能：</b>ContractDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-20 14:25:57<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("paymentManageService")
public class PaymentManageService {

	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false) //自动注入
	ContractService contractService;
	@Autowired(required=false) //自动注入
	private AllinPayTranxService allinPayTranxService;
	@Autowired(required=false) //自动注入
	private ThirdPaymentService thirdPaymentService;
	@Autowired(required=false) //自动注入
	PaymentService<Payment> paymentService;
	@Autowired(required=false) //自动注入
	CreditDecisionService creditDecisionService;
	@Autowired(required=false) //自动注入
	private ProductFeeRateService productFeeRateService;
	@Autowired(required=false) //自动注入
	CreditSignService creditSignService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
//	@Autowired(required=false) //自动注入
//	private CreditAppService creditAppService;

	@Autowired(required=false) //自动注入
	private AccounttingService<Accountting> accounttingService;
	@Autowired(required=false) //自动注入
	private PayService defaultPayService;
	@Autowired(required=false) //自动注入
	private MatchResultService<MatchResult> matchResultService;
	@Autowired(required=false) //自动注入
	private CollectionBatchService collectionBatchService;
	
	
	/**
	 * 减免 操作
	 * @param contractNo
	 * @param subAmount
	 * @param loginId
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public PaymentManageMsgBean abatementFine(String contractNo,double subAmount,String loginId,String orgId) throws Exception{
		
		if(subAmount<=0){
			return new PaymentManageMsgBean(false, "减免金额有误。");
		}
		
		double resultAmount=0.0;
		double ysAmount=subAmount;
		
		Map<String, Object> dbMap=new HashMap<String, Object>();
		dbMap.put("contractNo", contractNo);
		dbMap.put("states", new String[]{"1","3"});
		dbMap.put("existPenalty", "true");	//存在罚息， 至查询 违约的 还款计划
		List<LoanRepayPlan> repayPlanList= loanRepayPlanService.queryListExt(dbMap);
		if(repayPlanList==null || repayPlanList.isEmpty()){
			return new PaymentManageMsgBean(false, "不存在违约，没有罚息不能减免。");
		}
		//减免金额不能超过最高减免金额
		double maxRelief = getMaxjmje(contractNo);
		if(subAmount>maxRelief){
			return new PaymentManageMsgBean(false, "实际减免金额超过最高减免金额。");
		}
		for (LoanRepayPlan loanRepayPlan : repayPlanList) {
			//声明记账 对象
			Accountting account=new Accountting();
			account.setInOut("2");
			account.setContractNo(loanRepayPlan.getContractNo());
			account.setPeriodNum(loanRepayPlan.getPeriodNum());
			account.setState("1");
			account.setOperator(loginId);
			account.setOrgId(orgId);
			long time=System.currentTimeMillis();
			account.setCreateTime(new Timestamp(time));
			account.setUpdateTime(new Timestamp(time));
			//最大可减免 的滞纳金金额
			double subDelay=MathUtils.sub(
								MathUtils.sub(loanRepayPlan.getDelayReceivable(), 
											  loanRepayPlan.getDelayReceived()),
								loanRepayPlan.getDelayReduce());
			if(subDelay>0&&ysAmount>0){
				if(ysAmount>=subDelay){
					ysAmount=MathUtils.sub(ysAmount,subDelay);
				}else{
					subDelay=ysAmount;
					ysAmount=0.0;
				}
				account.setId(0);
				account.setAccount("其它收入");
				account.setSubject("业务往来-滞纳金减免");
				account.setPlanAmount(subDelay);
				account.setActualAmount(subDelay);
				accounttingService.add(account);
				loanRepayPlan.setDelayReduce(MathUtils.add(
											loanRepayPlan.getDelayReduce(),
											subDelay));
				resultAmount=MathUtils.add(resultAmount, subDelay);
			}
			//最大可减免 的  罚息 金额
			double subReduce=MathUtils.sub(
								MathUtils.sub(loanRepayPlan.getPenaltyReceivable(), 
											  loanRepayPlan.getPenaltyReceived()),
								loanRepayPlan.getPenaltyReduce());
			if(subReduce>0&&ysAmount>0){
				if(ysAmount>=subReduce){
					ysAmount=MathUtils.sub(ysAmount,subReduce);
				}else{
					subReduce=ysAmount;
					ysAmount=0.0;
				}
				account.setId(0);
				account.setAccount("其它收入");
				account.setSubject("业务往来-罚息减免");
				account.setPlanAmount(subReduce);
				account.setActualAmount(subReduce);
				accounttingService.add(account);
				loanRepayPlan.setPenaltyReduce(MathUtils.add(
												loanRepayPlan.getPenaltyReduce(),
												subReduce));
				resultAmount=MathUtils.add(resultAmount, subReduce);
			}
			//更新还款计划
			loanRepayPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			loanRepayPlanService.update(loanRepayPlan);
		//以下更新 payment
			//应收金额
			double dbAmount=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
			//更新payment表中数据
			Map<String, Object> mapPay=new HashMap<String, Object>();
			mapPay.put("contractNo", loanRepayPlan.getContractNo());
			mapPay.put("periodNum", loanRepayPlan.getPeriodNum());
			mapPay.put("inOut", "1");
			mapPay.put("subject", "收本息");
			List<Payment>  listPayments=paymentService.queryList(mapPay);
			Payment payment=listPayments.get(0);
			payment.setPlanAmount(dbAmount);
			payment.setActualAmount(dbAmount);
			payment.setOperator(loginId);	//当前操作人
			payment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			paymentService.update(payment);
			//判断减免金额是否 完成
			if(ysAmount==0){
				break;
			}
		}
		collectionBatchService.syncCollectionBaseData(contractNo);
		return new PaymentManageMsgBean(true, "减免操作已完成，最终减免金额：￥"+resultAmount);
	}
	
	/**
	 * 实时 扣款
	 * @param contractNo
	 * @param realTimeAmount
	 * @param loginId
	 * @param orgId
	 * @throws Exception 
	 */
	@Transactional
	public PaymentManageMsgBean realTimePayment(String contractNo,double realTimeAmount,String loginId,String orgId) throws Exception{
		if(realTimeAmount<=0){
			return new PaymentManageMsgBean(false, "收款金额有误。");
		}
		
		
		Timestamp newTime = new Timestamp(System.currentTimeMillis());
		
		Payment payment = new Payment();
		payment.setContractNo(contractNo);
		payment.setInOut("3");//实时收
		payment.setSubject("实时收款");////科目
		payment.setRepaymentDate(newTime);//还款日
		payment.setPlanAmount(realTimeAmount);//计划金额
		payment.setActualAmount(realTimeAmount);//实际金额
		payment.setState("1");//状态 : 1 未支付,2 已发盘,3 发盘失败,4 发盘成功,5 支付成功,6 支付失败
		payment.setDetail("实时还款");//明细
		payment.setCreateTime(newTime);//创建日期
		payment.setUpdateTime(newTime);//修改日期
		payment.setRepayment(false);//是否付款
		paymentService.add(payment);
		allinPayTranxService.daishouRealTime(payment, AllinpayConstant.URLhttps);
		List<ThirdPaymentLog> thirdPaymentLogList = payment.getThirdPaymentLogList();
		for (ThirdPaymentLog thirdPaymentLog : thirdPaymentLogList) {
			thirdPaymentService.add(thirdPaymentLog);//生成支付日志表
		}
		
		paymentService.update(payment);
		
		if("5".equals(payment.getState())){
			
			//把传入金额 复制 用于 下面业务操作
			double realTimeAmount1=realTimeAmount;
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("contractNo", contractNo);
			map.put("states", new String[]{"1","3"});
			List<LoanRepayPlan> repayPlanList = loanRepayPlanService.queryListExt(map);
			for (LoanRepayPlan loanRepayPlan : repayPlanList) {
				
				//应收金额
				double dbAmount=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
				if(dbAmount>0&&realTimeAmount1>0){
					//更新payment表中数据
					Map<String, Object> mapPay=new HashMap<String, Object>();
					mapPay.put("contractNo", loanRepayPlan.getContractNo());
					mapPay.put("periodNum", loanRepayPlan.getPeriodNum());
					mapPay.put("subject", "收本息");
					mapPay.put("inOut", "1");
					List<Payment>  listPayments=paymentService.queryList(mapPay);
					Payment pmt=listPayments.get(0);
					
					pmt.setOperator(loginId);	//当前操作人
					pmt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					
					if(realTimeAmount1>dbAmount){ //实时还款， 足额还款
						realTimeAmount1=MathUtils.sub(realTimeAmount1, dbAmount);
						//分析还款计划  
						loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan);
						//记账
						accounttingService.repayPlanAccountting(loanRepayPlan);
						pmt.setState("5");
						paymentService.update(pmt);
					}else{
						dbAmount=realTimeAmount1;
						realTimeAmount1=0.0;
						//分析还款计划  
						loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan,dbAmount);
						//记账
						accounttingService.repayPlanAccountting(loanRepayPlan);
						
						//剩余未还金额
						double pmtdouble=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
						pmt.setPlanAmount(pmtdouble);
						pmt.setActualAmount(pmtdouble);
						paymentService.update(pmt);
						
					}
					//收款金额 为零了 跳出循环 结束操作
					if(realTimeAmount1<=0){
						break;
					}
				}
			}
			//更新合同状态
			contractService.ifContractEnd(contractNo);
			return new PaymentManageMsgBean(true, "实时收款成功，收款金额：￥"+realTimeAmount+"元。");
		}else{
			return new PaymentManageMsgBean(false, "代收失败，失败原因："+payment.getReason()+"。");
		}
		
	}
	
	/**
	 * 实时 扣款(富友)
	 * @param contractNo
	 * @param realTimeAmount
	 * @param loginId
	 * @param orgId
	 * @throws Exception 
	 */
	@Transactional
	public PaymentManageMsgBean realTimePaymentFY(String contractNo,double realTimeAmount,String loginId,String orgId) throws Exception{
		if(realTimeAmount<=0){
			return new PaymentManageMsgBean(false, "收款金额有误。");
		}
		Timestamp newTime = new Timestamp(System.currentTimeMillis());
		
		Map<String, Object> fmap = new HashMap<String, Object>();
		fmap.put("contractNo", contractNo);
		List<Contract> contractList = contractService.queryList(fmap);
		//合同
		Contract contract=contractList.get(0);
		/*
		Map<String, Object> appMap = new HashMap<String, Object>();
		appMap.put("appId", contract.getLoanAppId());
		//查询申请
		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
		
		*/
		Payment payment = new Payment();
		payment.setContractNo(contractNo);
		payment.setInOut("3");//实时收
		payment.setSubject("实时收款");////科目
		payment.setRepaymentDate(newTime);//还款日
		payment.setPlanAmount(realTimeAmount);//计划金额
		payment.setActualAmount(realTimeAmount);//实际金额
		payment.setState("1");//状态 : 1 未支付,2 已发盘,3 发盘失败,4 发盘成功,5 支付成功,6 支付失败
		payment.setDetail("实时还款");//明细
		payment.setCreateTime(newTime);//创建日期
		payment.setUpdateTime(newTime);//修改日期
		payment.setRepayment(false);//是否付款
		payment.setOperator(loginId);//当前处理人
		payment.setOrgId(orgId);//当前登录机构
		
		payment.setBankNo(contract.getBankCode());
		payment.setAccountName(contract.getBankAccountName());
		payment.setAccounttNo(contract.getBankAccount());
		payment.setIdType(contract.getLoanIdType());
		payment.setIdNo(contract.getLoanIdNo());
		
		payment.setMobile(contract.getBankMobile());//手机,接口必传参数
		payment.setLoanAppId(contract.getLoanAppId());//借款申请ID
		
		paymentService.add(payment);
		// 调用代收接口
		defaultPayService.daishou(payment);
		
		paymentService.update(payment);
		//支付成功
		if("5".equals(payment.getState())){
			
			//把传入金额 复制 用于 下面业务操作
			double realTimeAmount1=realTimeAmount;
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("contractNo", contractNo);
			map.put("states", new String[]{"1","3"});
			List<LoanRepayPlan> repayPlanList = loanRepayPlanService.queryListExt(map);
			for (LoanRepayPlan loanRepayPlan : repayPlanList) {
				
				//应收金额
				double dbAmount=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
				if(dbAmount>0&&realTimeAmount1>0){
					//更新payment表中数据
					Map<String, Object> mapPay=new HashMap<String, Object>();
					mapPay.put("contractNo", loanRepayPlan.getContractNo());
					mapPay.put("periodNum", loanRepayPlan.getPeriodNum());
					mapPay.put("subject", "收本息");
					mapPay.put("inOut", "1");
					List<Payment>  listPayments=paymentService.queryList(mapPay);
					Payment pmt=listPayments.get(0);
					
					pmt.setOperator(loginId);	//当前操作人
					pmt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					
					if(realTimeAmount1>=dbAmount){ //实时还款， 足额还款
						realTimeAmount1=MathUtils.sub(realTimeAmount1, dbAmount);
						//分析还款计划  
						loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan);
						//记账
						accounttingService.repayPlanAccountting(loanRepayPlan);
						pmt.setState("5");
						paymentService.update(pmt);
					}else{
						dbAmount=realTimeAmount1;
						realTimeAmount1=0.0;
						//分析还款计划  
						loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan,dbAmount);
						//记账
						accounttingService.repayPlanAccountting(loanRepayPlan);
						
						//剩余未还金额
						double pmtdouble=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
						if(pmtdouble>0){
							pmt.setPlanAmount(pmtdouble);
							pmt.setActualAmount(pmtdouble);
						}else{
							pmt.setState("5");
						}
						paymentService.update(pmt);
					}
					//收款金额 为零了 跳出循环 结束操作
					if(realTimeAmount1<=0){
						break;
					}
				}
			}
			//更新合同状态
			contractService.ifContractEnd(contractNo);
			
			//记录催收绩效信息
			collectionBatchService.addCollectionResult(contractNo, realTimeAmount);
			
			return new PaymentManageMsgBean(true, "实时收款成功，收款金额：￥"+realTimeAmount+"元。");
		}else{
			return new PaymentManageMsgBean(false, "代收失败，失败原因："+payment.getReason()+"。");
		}
		
	}
	
	/**
	 * 一次性还款（通联支付）
	 * @param contractNo
	 * @param paymentList
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ResultObj advancePayment(String contractNo, List<Payment> paymentList) throws Exception{
		/**
		 * 1.计算出一次性还款的金额，生成支付表
		 * 2.调用通联支付
		 * 3.支付成功之后生成支付日志表，修改借款计划还款情况及状态改为2(支付完)和修改支付表支付情况及状态
		 * 4.一次性还款记账,更新合同状态
		 */
		double ycxhkje = 0.0;
		//违约还款计划表集合
		Map<String, Object> repayQueryMap = new HashMap<String, Object>();
		repayQueryMap.put("contractNo", contractNo);
		repayQueryMap.put("states", new String[]{"1","3"});
		repayQueryMap.put("existPenalty", "true");	//存在罚息， 至查询 违约的 还款计划
		List<LoanRepayPlan> wyRepayPlanList = loanRepayPlanService.queryListExt(repayQueryMap);
		double whje = 0.0;
		for (LoanRepayPlan loanRepayPlan2 : wyRepayPlanList) {
			whje = MathUtils.add(whje, loanRepayPlanService.countLoanRepayPlan(loanRepayPlan2));//违约未还金额
		}
		//查出当期还款计划
		Map<String, Object> dqMap = new HashMap<String, Object>();
		dqMap.put("contractNo", contractNo);
		dqMap.put("states", new String[]{"1", "3"});
		List<LoanRepayPlan> dqRepayPlanList = loanRepayPlanService.queryListExt(dqMap);
		double fhfwfje = 0.0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		List<Contract> contractList = contractService.queryList(map);
		
		if(null != dqRepayPlanList && dqRepayPlanList.size() > 0){
			LoanRepayPlan loanRepayPlan = dqRepayPlanList.get(0);
			
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", contractList.get(0).getLoanAppId());
			fmap.put("state", "1");
			fmap.put("type", "0");
			List<CreditDecision> creditDecisionList = creditDecisionService.queryList(fmap);
			Map<String, Object> fmap1=new HashMap<String, Object>();
			fmap1.put("name", creditDecisionList.get(0).getProduct());
			fmap1.put("periodNum", loanRepayPlan.getPeriodNum());
			List<ProductFeeRate> productFeeRateList = productFeeRateService.queryList(fmap1);
			Product pro = productService.queryByName(creditDecisionList.get(0).getProduct());
			double htjkje = creditSignService.getHtJkje(creditDecisionList.get(0), pro);//获取合同借款金额
			double yhkje = creditSignService.getYhkje(htjkje, pro);//获取月还款额
			double djfwfje = creditSignService.getDjfwfje(htjkje, creditDecisionList.get(0).getAmount());//获取趸交服务费总额
			fhfwfje = creditSignService.getFhfwfje(djfwfje, productFeeRateList.get(0));//返还服务费金额
			ycxhkje = MathUtils.add(MathUtils.sub(loanRepayPlan.getRestPrincipal(), fhfwfje), yhkje);
			//一次性还款金额
			//ycxhkje = MathUtils.add(MathUtils.sub(MathUtils.add(loanRepayPlan.getRestPrincipal(), MathUtils.add(loanRepayPlan.getInterestReceivable(), loanRepayPlan.getPrincipalReceivable())), loanRepayPlan.getSreviceFeeReceivable()), whje);
		}
		Timestamp newTime = new Timestamp(System.currentTimeMillis());
		ycxhkje = MathUtils.round(ycxhkje, 2);//一次性还款金额
		//支付表
		Payment payment = new Payment();
		payment.setContractNo(contractNo);
		payment.setInOut("3");//实时收
		payment.setSubject("一次性还清");////科目
		payment.setRepaymentDate(newTime);//还款日
		payment.setPlanAmount(ycxhkje);//计划金额
		payment.setActualAmount(ycxhkje);//实际金额
		payment.setState("1");//状态 : 1 未支付,2 已发盘,3 发盘失败,4 发盘成功,5 支付成功,6 支付失败
		payment.setDetail("一次性还款收款");//明细
		payment.setCreateTime(newTime);//创建日期
		payment.setUpdateTime(newTime);//修改日期
		payment.setRepayment(false);//是否付款
		payment.setLoanAppId(contractList.get(0).getLoanAppId());//借款申请ID
		payment.setAccountName(contractList.get(0).getBankAccountName());//账户名
		payment.setAccounttNo(contractList.get(0).getBankAccount());//账号
		paymentService.add(payment);
		//调用通联支付
		allinPayTranxService.daishouRealTime(payment, AllinpayConstant.URLhttps);
		List<ThirdPaymentLog> thirdPaymentLogList = payment.getThirdPaymentLogList();
		for (ThirdPaymentLog thirdPaymentLog : thirdPaymentLogList) {
			thirdPaymentService.add(thirdPaymentLog);//生成支付日志表
		}
		paymentService.update(payment);
		//支付成功
		if("5".equals(payment.getState())){
			
			Contract contract=contractList.get(0);
			contract.setState("3");
			contract.setCreateTime(newTime);//创建日期
			contractService.updateOnlyChanged(contract);//更新合同状态

			//修改借款计划还款情况及状态改为2(支付完)
			Map<String, Object> repayQueryMap1 = new HashMap<String, Object>();
			repayQueryMap1.put("contractNo", contractNo);
			repayQueryMap.put("states", new String[]{"1","3"});
			List<LoanRepayPlan> repayPlanList = loanRepayPlanService.queryListExt(repayQueryMap1);
			for (LoanRepayPlan loanRepayPlan : repayPlanList) {
				if(loanRepayPlan.getId()!=dqRepayPlanList.get(0).getId()){
					loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan);
				}
			}
			//修改支付表支付情况及状态
			for (Payment payment2 : paymentList) {
				payment2.setState("5");//支付成功
				paymentService.update(payment2);
			}
			double fxjBlx=0.0;//风险金补利息
			//一次性还款记账
			for (LoanRepayPlan loanRepayPlan : repayPlanList) {
				if(loanRepayPlan.getPenaltyReceivable() > 0){
					//违约记账
					accounttingService.repayPlanAccountting(loanRepayPlan);
				}else if(loanRepayPlan.getId()!=dqRepayPlanList.get(0).getId()){
					//计算 风险金 补了多少利息
					fxjBlx=MathUtils.add(fxjBlx, loanRepayPlan.getInterestReceivable());
				}
			}
			
			LoanRepayPlan loanRepayPlan=loanRepayPlanService.finishRepay(dqRepayPlanList.get(0));
			Accountting account=new Accountting();
			account.setInOut("1");
			account.setContractNo(loanRepayPlan.getContractNo());
			account.setPeriodNum(loanRepayPlan.getPeriodNum());
			account.setState("1");
			account.setOperator(BpmConstants.SYSTEM_SYS);
			account.setOrgId(loanRepayPlan.getOrgId());
			long time=System.currentTimeMillis();
			account.setCreateTime(new Timestamp(time));
			account.setUpdateTime(new Timestamp(time));
			
			if(loanRepayPlan.getPrincipalCurrent()>0){ 
				account.setId(0);
				account.setAccount("出借金额");
				account.setSubject("业务往来-一次性还款实收本金");
				account.setPlanAmount(MathUtils.add(loanRepayPlan.getPrincipalReceivable(),loanRepayPlan.getRestPrincipal()));
				account.setActualAmount(MathUtils.add(loanRepayPlan.getPrincipalCurrent(),loanRepayPlan.getRestPrincipal()));
				accounttingService.add(account);
			}
			//实收利息
			if(loanRepayPlan.getInterestCurrent()>0){ 
				account.setId(0);
				account.setAccount("出借金额");
				account.setSubject("业务往来-一次性还款实收利息");
				account.setPlanAmount(loanRepayPlan.getInterestReceivable());
				account.setActualAmount(loanRepayPlan.getInterestCurrent());
				accounttingService.add(account);
			}
			if(fxjBlx>0){ 
				account.setId(0);
				account.setInOut("2");
				account.setAccount("风险金");
				account.setSubject("业务往来-风险金补利息");
				account.setPlanAmount(fxjBlx);
				account.setActualAmount(fxjBlx);
				accounttingService.add(account);
			}
			if(fhfwfje>0){ 
				account.setId(0);
				account.setInOut("2");
				account.setAccount("服务费");
				account.setSubject("业务往来-一次性还款退还服务费");
				account.setPlanAmount(fhfwfje);
				account.setActualAmount(fhfwfje);
				accounttingService.add(account);
			}
			return new ResultObj("0", "一次性结算成功。", true);
		}else{
			return new ResultObj("0", "一次性结算失败。", false);
		}
	}
	
	/**
	 * 一次性还款（富有支付）
	 * 1.计算出一次性还款的金额，生成支付表
	 * 2.调用 支付
	 * 3.支付成功之后 撮合结果处理 修改借款计划 状态改为2  修改支付表支付情况及状态 ,更新合同状态
	 * 4.一次性还款记账
	 * @param contractNo
	 * @param paymentList
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ResultObj advancePaymentFY(String contractNo, List<Payment> paymentList, Date selectDate,String loginId,String orgId) throws Exception{
		
		//查询合同 计算  所有未还 金额
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		List<Contract> contractList = contractService.queryList(map);
		//合同
		Contract contract=contractList.get(0);

		/*Map<String, Object> appMap = new HashMap<String, Object>();
		appMap.put("appId", contract.getLoanAppId());
		//查询申请
		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
		*/
		
		//查出所有还款计划
		Map<String, Object> dqMap = new HashMap<String, Object>();
		dqMap.put("contractNo", contractNo);
		List<LoanRepayPlan> planList = loanRepayPlanService.queryListExt(dqMap);//所有还款计划
		
		double hk_bj=residueCapital(planList, selectDate); // 一次还款   剩余本金
//		double hk_yw=analAllMust(planList, selectDate); // 一次还款   当期 以及 往期 还款金额
		double hk_jm_li=this.mitigateInterests(planList); // 一次还款   减免的利息
		double hk_sj=getYcxhkje(contractNo); // 一次还款   实际金额
		
		double hk_fh=0; //找出当期 还款计划 计算返还服务费
		
		/** 未完金额 减去  返还服务费  得到最终 一次性还款金额**/
		if(//积木 与 鼎轩 渠道  一次性还款都不退还服务费
//			contract.getLoanProduct().indexOf("JM")<=0 && contract.getLoanProduct().indexOf("DX")<=0 && contract.getLoanProduct().indexOf("MD")<=0
			"HD".equals(contract.getChannelType())
		  ){ 
			hk_fh=this.getFhfwf(planList, contract, selectDate); // 一次还款   返还服务费
			hk_sj=MathUtils.sub(hk_sj, hk_fh);
		}
		
		// 生成支付对象
		Timestamp newTime = new Timestamp(System.currentTimeMillis());
		Date payDate = new Date();
		Payment payment = new Payment();
		payment.setContractNo(contractNo);
		payment.setInOut("3");//实时收
		payment.setSubject("一次性还清");////科目
		payment.setRepaymentDate(newTime);//还款日
		payment.setPlanAmount(hk_sj);//计划金额
		payment.setActualAmount(hk_sj);//实际金额
		payment.setState("1");//状态 : 1 未支付,2 已发盘,3 发盘失败,4 发盘成功,5 支付成功,6 支付失败
		payment.setDetail("一次性还款收款");//明细
		payment.setCreateTime(newTime);//创建日期
		payment.setUpdateTime(newTime);//修改日期
		payment.setRepayment(false);//是否付款
		payment.setOperator(loginId);//当前处理人
		payment.setOrgId(orgId);//当前登录机构
		
		payment.setAccountName(contract.getBankAccountName());//账户名
		payment.setAccounttNo(contract.getBankAccount());//账号
		payment.setBankNo(contract.getBankCode());
		payment.setIdType(contract.getLoanIdType());
		payment.setIdNo(contract.getLoanIdNo());
		
		payment.setLoanAppId(contract.getLoanAppId());//借款申请ID
		payment.setMobile(contract.getBankMobile());	  //富友接口 必传手机号
		
		
		paymentService.add(payment);
		//调用 支付 代收 接口
		defaultPayService.daishou(payment);
		//最终结果入库
		paymentService.update(payment);
		
		//支付成功
		if("5".equals(payment.getState())){
			//调老毕写的撮合结果处理
			matchResultService.onetimePayOff(contract.getLoanAppId());
			
			//还款成功更改合同状态
			contract.setState("3");
			contract.setCreateTime(newTime);//创建日期
			contractService.updateOnlyChanged(contract);//更新合同状态

			// 处理还款计划
			Date newDate=DateUtils.addMonth(new Date(), 1);
			for (LoanRepayPlan loanRepayPlan:planList) {
				if("1".equals(loanRepayPlan.getState())||"3".equals(loanRepayPlan.getState())){
					int sjDay=DateUtils.compareDate(loanRepayPlan.getRepaymentDate(),newDate);
					if(sjDay<0){
						loanRepayPlanService.finishRepay(loanRepayPlan);
						accounttingService.repayPlanAccountting(loanRepayPlan);
					}
					//更改所有 还款计划的状态  统统 改成已还款
					loanRepayPlan.setPrincipalReceived(loanRepayPlan.getPrincipalReceivable());
					loanRepayPlan.setState("2");
					loanRepayPlan.setUpdateTime(newTime);
					loanRepayPlan.setPayDate(payDate);
					loanRepayPlanService.updateOnlyChanged(loanRepayPlan);
				}
			}
			//修改支付表支付情况及状态
			for (Payment payment2 : paymentList) {
				if(!"5".equals(payment2.getState())&&!"0".equals(payment2.getState())){
					payment2.setState("5");//支付成功
					payment2.setReason("一次性还款,"+payment.getReason());//支付成功
					payment2.setUpdateTime(newTime);//支付成功
					paymentService.update(payment2);
				}
				
			}

			Accountting account=new Accountting();
			account.setInOut("1");
			account.setContractNo(contract.getContractNo());
			account.setPeriodNum(contract.getLoanPeriod());
			account.setState("1");
			account.setOperator(BpmConstants.SYSTEM_SYS);
			account.setOrgId(contract.getOrgId());
			long time=System.currentTimeMillis();
			account.setCreateTime(new Timestamp(time));
			account.setUpdateTime(new Timestamp(time));
			
			if(hk_bj>0){ 
				account.setId(0);
				account.setAccount("出借金额");
				account.setSubject("业务往来-一次性还款实收本金");
				account.setPlanAmount(hk_bj);
				account.setActualAmount(hk_bj);
				accounttingService.add(account);
			}
			if(hk_jm_li>0){ 
				account.setInOut("2");
				account.setAccount("风险金");
				account.setSubject("业务往来-风险金补利息");
				account.setPlanAmount(hk_jm_li);
				account.setActualAmount(hk_jm_li);
				accounttingService.add(account);
			}
			if(hk_fh>0){ 
				account.setInOut("2");
				account.setAccount("服务费");
				account.setSubject("业务往来-一次性还款退还服务费");
				account.setPlanAmount(hk_fh);
				account.setActualAmount(hk_fh);
				accounttingService.add(account);
			}
			
			//记录催收绩效信息,按结清处理
			collectionBatchService.handleCollectionAfterPayment(contractNo);
			return new ResultObj("0", "一次性结算成功。收款金额：￥" + hk_sj + "元。", true);
		}else{
			return new ResultObj("0", "一次性结算失败。失败原因：" + payment.getReason(), false);
		}
	}
	
	/**
	 * 得到一次性还款额    没有 减去 返还服务费
	 * @param contractNo
	 * @return
	 * @throws Exception
	 */
	public double getYcxhkje(String contractNo) throws Exception{
		double ycxhkje = 0.0;
		//违约还款计划表集合
		Map<String, Object> repayQueryMap = new HashMap<String, Object>();
		repayQueryMap.put("contractNo", contractNo);
		repayQueryMap.put("states", new String[]{"1","3"});
		List<LoanRepayPlan> wyRepayPlanList = loanRepayPlanService.queryListExt(repayQueryMap);
		//当期以及往期  全部金额
		double analAllMust=analAllMust(wyRepayPlanList, new Date());
		//余下的还款计划   剩余本金
		double residueCapital=residueCapital(wyRepayPlanList, new Date());
		ycxhkje=MathUtils.add(analAllMust, residueCapital);
		return MathUtils.round(ycxhkje, 2);
	}
	
	/**
	 * 得到一次性还款额    没有 减去 返还服务费
	 * @param contractNo
	 * @return
	 * @throws Exception
	 */
	public double getYcxhkje(List<LoanRepayPlan> wyRepayPlanList, Date selectDate) throws Exception{
		double ycxhkje = 0.0;
		//当期以及往期  全部金额
		double analAllMust=analAllMust(wyRepayPlanList, selectDate);
		//余下的还款计划   剩余本金
		double residueCapital=residueCapital(wyRepayPlanList, selectDate);
		ycxhkje=MathUtils.add(analAllMust, residueCapital);
		return MathUtils.round(ycxhkje, 2);
	}
	
	/**
	 * 根据还款计划分析  当期与以前的 所有应还金额
	 * @param repayPlanList
	 * @return
	 */
	private double analAllMust(List<LoanRepayPlan> repayPlanList, Date selectDate){
		double jg=0.0;
		Date newDate=DateUtils.addMonth(selectDate, 1);
		for (LoanRepayPlan loanRepayPlan:repayPlanList) {
			if("1".equals(loanRepayPlan.getState())||"3".equals(loanRepayPlan.getState())){
				//判断 当前时间 加一个月 是否  大于 还款日
				int sjDay=DateUtils.compareDate(loanRepayPlan.getRepaymentDate(),newDate);
				if(sjDay<0){//如果是当期与以前的 还款计划， 统计还款计划所要支付的所有金额
					jg=MathUtils.add(jg,loanRepayPlanService.countLoanRepayPlan(loanRepayPlan));
				}
			}
		}
		return MathUtils.round(jg,2);
	}
	
	/**
	 * 根据还款计划分析  剩余本金
	 * @param repayPlanList
	 * @return
	 */
	private double residueCapital(List<LoanRepayPlan> repayPlanList, Date selectDate){
		double jg=0.0;
		Date newDate=DateUtils.addMonth(selectDate, 1);
		for (LoanRepayPlan loanRepayPlan:repayPlanList) {
			if("1".equals(loanRepayPlan.getState())||"3".equals(loanRepayPlan.getState())){
				//判断 当前时间 加一个月 是否  大于 还款日
				int sjDay=DateUtils.compareDate(loanRepayPlan.getRepaymentDate(),newDate);
				if(sjDay>=0){
					double dqbj_ys=loanRepayPlan.getPrincipalReceivable();
					double dqbj_ss=loanRepayPlan.getPrincipalReceived();
					jg=MathUtils.add(jg,MathUtils.sub(dqbj_ys,dqbj_ss));
				}
			}
		}
		return MathUtils.round(jg,2);
	}
	
	/**
	 * 根据还款计划分析  一次性还款 少收利息分析
	 * @param repayPlanList
	 * @return
	 */
	private double mitigateInterests(List<LoanRepayPlan> repayPlanList){
		double jg=0.0;
		Date newDate=DateUtils.addMonth(new Date(), 1);
		for (LoanRepayPlan loanRepayPlan:repayPlanList) {
			if("1".equals(loanRepayPlan.getState())||"3".equals(loanRepayPlan.getState())){
				//判断 当前时间 加一个月 是否  大于 还款日
				int sjDay=DateUtils.compareDate(loanRepayPlan.getRepaymentDate(),newDate);
				if(sjDay>=0){
					double dqlx_ys=loanRepayPlan.getInterestReceivable();
					double dqlx_ss=loanRepayPlan.getInterestReceived();
					jg=MathUtils.add(jg,MathUtils.sub(dqlx_ys,dqlx_ss));
				}
			}
		}
		return MathUtils.round(jg,2);
	}
	
	/**
	 * 得到最高减免金额
	 * @param contractNo
	 * @return
	 * @throws Exception
	 */
	public double getMaxjmje(String contractNo) throws Exception {
		double maxjmje = 0.0;
		//违约还款计划表集合
		Map<String, Object> repayQueryMap = new HashMap<String, Object>();
		repayQueryMap.put("contractNo", contractNo);
		repayQueryMap.put("states", new String[]{"1","3"});
		repayQueryMap.put("existPenalty", "true");	//存在罚息， 至查询 违约的 还款计划
		List<LoanRepayPlan> wyRepayPlanList = loanRepayPlanService.queryListExt(repayQueryMap);
		for (LoanRepayPlan loanRepayPlan2 : wyRepayPlanList) {
			maxjmje = MathUtils.add(maxjmje, loanRepayPlanService.countLoanRepayPlanMax(loanRepayPlan2));//最高减免金额
		}
		return MathUtils.round(maxjmje, 2);
	}
	
	/**
	 * 得到最高减免金额
	 * @param contractNo
	 * @return
	 * @throws Exception
	 */
	public double getMaxjmje(List<LoanRepayPlan> wyRepayPlanList) throws Exception {
		double maxjmje = 0.0;
		for (LoanRepayPlan loanRepayPlan2 : wyRepayPlanList) {
			maxjmje = MathUtils.add(maxjmje, loanRepayPlanService.countLoanRepayPlanMax(loanRepayPlan2));//最高减免金额
		}
		return MathUtils.round(maxjmje, 2);
	}
	
	/**
	 * 得到当期的返还服务费
	 * @param repayPlanList
	 * @return
	 * @throws Exception 
	 */
	public double getFhfwf(List<LoanRepayPlan> planList, Contract contract, Date selectDate) throws Exception{
		double hk_fh = 0.0;
		for (LoanRepayPlan plan : planList) {
			int sjDay=DateUtils.compareDate( selectDate,plan.getRepaymentDate());
			if(sjDay<1){
				Map<String, Object> fmap=new HashMap<String, Object>();
				fmap.put("appId", contract.getLoanAppId());
				fmap.put("state", "1");
				fmap.put("type", "0");
//				最终决策
				List<CreditDecision> creditDecisionList = creditDecisionService.queryList(fmap);
				Map<String, Object> fmap1=new HashMap<String, Object>();
				fmap1.put("name", creditDecisionList.get(0).getProduct());
				fmap1.put("periodNum", plan.getPeriodNum());
//				返还服务费  费率
				List<ProductFeeRate> productFeeRateList = productFeeRateService.queryList(fmap1);
				//趸交服务费
				double djfwfje = creditSignService.getDjfwfje(contract.getLoanAmount(), creditDecisionList.get(0).getAmount());//获取趸交服务费总额
				/**计算出 返还服务费**/
				hk_fh = creditSignService.getFhfwfje(djfwfje, productFeeRateList.get(0));//返还服务费金额
				hk_fh = MathUtils.round(hk_fh, 2);
//				找到当期 计算出返还服务费 就跳出循环
				break;
			}
		}
		return hk_fh;
	}
	
	/**
	 * 对公划款
	 * @param contractNo
	 * @param dghkAmount
	 * @param loginId
	 * @param orgId
	 * @throws Exception 
	 */
	@Transactional
	public PaymentManageMsgBean dghk(String contractNo,double dghkAmount,String loginId,String orgId) throws Exception{
		if(dghkAmount<=0){
			return new PaymentManageMsgBean(false, "对公划款金额有误。");
		}
		
		//把传入金额 复制 用于 下面业务操作
		double dghkAmount1=dghkAmount;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("states", new String[]{"1","3"});
		List<LoanRepayPlan> repayPlanList = loanRepayPlanService.queryListExt(map);
		for (LoanRepayPlan loanRepayPlan : repayPlanList) {
			//应收金额
			double dbAmount=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
			if(dbAmount>0&&dghkAmount1>0){
				//更新payment表中数据
				Map<String, Object> mapPay=new HashMap<String, Object>();
				mapPay.put("contractNo", loanRepayPlan.getContractNo());
				mapPay.put("periodNum", loanRepayPlan.getPeriodNum());
				mapPay.put("subject", "收本息");
				mapPay.put("inOut", "1");
				List<Payment>  listPayments=paymentService.queryList(mapPay);
				Payment pmt=listPayments.get(0);
				
				pmt.setOperator(loginId);	//当前操作人
				pmt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				
				if(dghkAmount1>=dbAmount){ //实时还款， 足额还款
					dghkAmount1=MathUtils.sub(dghkAmount1, dbAmount);
					//分析还款计划  
					loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan);
					//记账
					accounttingService.repayPlanAccountting(loanRepayPlan);
					pmt.setState("5");
					paymentService.update(pmt);
				}else{
					dbAmount=dghkAmount1;
					dghkAmount1=0.0;
					//分析还款计划  
					loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan,dbAmount);
					//记账
					accounttingService.repayPlanAccountting(loanRepayPlan);
					
					//剩余未还金额
					double pmtdouble=loanRepayPlanService.countLoanRepayPlan(loanRepayPlan);
					if(pmtdouble>0){
						pmt.setPlanAmount(pmtdouble);
						pmt.setActualAmount(pmtdouble);
					}else{
						pmt.setState("5");
					}
					paymentService.update(pmt);
					
				}
				//收款金额 为零了 跳出循环 结束操作
				if(dghkAmount1<=0){
					break;
				}
			}
		}
		//更新合同状态
		contractService.ifContractEnd(contractNo);
		//记录催收绩效信息
		collectionBatchService.addCollectionResult(contractNo, dghkAmount);
		
		return new PaymentManageMsgBean(true, "对公划款成功，收款金额：￥"+dghkAmount+"元。");
		
	}
	
	
	
	/**
	 * 对公 一次性还款
	 * 1.计算出一次性还款的金额，生成支付表
	 * 2.分析对公还款金额
	 * 3. 撮合结果处理 修改借款计划 状态改为2  修改支付表支付情况及状态 ,更新合同状态
	 * 4.一次性还款记账（根据 对公还款金额 记账）
	 * @param contractNo
	 * @param paymentList
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ResultObj publicAdvancePayment(String contractNo, List<Payment> paymentList, Date selectDate,double publicAmount,String loginId,String orgId) throws Exception{
		
		//查询合同 计算  所有未还 金额
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		List<Contract> contractList = contractService.queryList(map);
		//合同
		Contract contract=contractList.get(0);

		//查出所有还款计划
		Map<String, Object> dqMap = new HashMap<String, Object>();
		dqMap.put("contractNo", contractNo);
		List<LoanRepayPlan> planList = loanRepayPlanService.queryListExt(dqMap);//所有还款计划
		
		double hk_bj=residueCapital(planList, selectDate); // 一次还款   剩余本金
		double hk_yw=analAllMust(planList, selectDate); // 一次还款   当期 以及 往期 还款金额
		double hk_jm_li=this.mitigateInterests(planList); // 一次还款   减免的利息
		double hk_sj=getYcxhkje(contractNo); // 一次还款   实际金额
		
		double hk_fh=0; //返还服务费
		
		/** 未完金额 减去  返还服务费  得到最终 一次性还款金额**/
		if(//积木 与 鼎轩 渠道  一次性还款都不退还服务费
//			contract.getLoanProduct().indexOf("JM")<=0 && contract.getLoanProduct().indexOf("DX")<=0 && contract.getLoanProduct().indexOf("MD")<=0
			"HD".equals(contract.getChannelType())
		  ){ 
			hk_fh=this.getFhfwf(planList, contract, selectDate); // 一次还款   返还服务费
			hk_sj=MathUtils.sub(hk_sj, hk_fh);
		}
		
		if(publicAmount<hk_yw){
			return new ResultObj("0", "操作失败，对公一次性还款金额 有误。", false);
		}
		
		
		//调老毕写的撮合结果处理
		matchResultService.onetimePayOff(contract.getLoanAppId());
		
		Timestamp newTime=new Timestamp(System.currentTimeMillis());
		
		//还款成功更改合同状态
		contract.setState("3");
		contract.setCreateTime(newTime);//创建日期
		contractService.updateOnlyChanged(contract);//更新合同状态

		// 处理还款计划
		Date newDate=DateUtils.addMonth(new Date(), 1);
		for (LoanRepayPlan loanRepayPlan:planList) {
			if("1".equals(loanRepayPlan.getState())||"3".equals(loanRepayPlan.getState())){
				int sjDay=DateUtils.compareDate(loanRepayPlan.getRepaymentDate(),newDate);
				if(sjDay<0){
					loanRepayPlanService.finishRepay(loanRepayPlan);
					accounttingService.repayPlanAccountting(loanRepayPlan);
				}
				//更改所有 还款计划的状态  统统 改成已还款
				loanRepayPlan.setPrincipalReceived(loanRepayPlan.getPrincipalReceivable());
				loanRepayPlan.setState("2");
				loanRepayPlan.setUpdateTime(newTime);
				loanRepayPlan.setPayDate(new Date());
				loanRepayPlanService.updateOnlyChanged(loanRepayPlan);
			}
		}
		//修改支付表支付情况及状态
		for (Payment payment2 : paymentList) {
			if(!"5".equals(payment2.getState())&&!"0".equals(payment2.getState())){
				payment2.setState("5");//支付成功
				payment2.setReason("对公一次性还款");
				payment2.setUpdateTime(newTime);
				paymentService.update(payment2);
			}
		}

		Accountting account=new Accountting();
		account.setInOut("1");
		account.setContractNo(contract.getContractNo());
		account.setPeriodNum(contract.getLoanPeriod());
		account.setState("1");
		account.setOperator(BpmConstants.SYSTEM_SYS);
		account.setOrgId(contract.getOrgId());
		long time=System.currentTimeMillis();
		account.setCreateTime(new Timestamp(time));
		account.setUpdateTime(new Timestamp(time));
		
		//计算对公还款金额差额
		double shortfall=MathUtils.sub(hk_sj, publicAmount);
		
		//差额大于 领    还款本金 就会减少
		if(shortfall>0){
			hk_bj=MathUtils.sub(hk_bj, shortfall);
		}
		if(hk_bj>0){ 
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-一次性还款实收本金");
			account.setPlanAmount(hk_bj);
			account.setActualAmount(hk_bj);
			accounttingService.add(account);
		}
		if(hk_jm_li>0){ 
			account.setInOut("2");
			account.setAccount("风险金");
			account.setSubject("业务往来-风险金补利息");
			account.setPlanAmount(hk_jm_li);
			account.setActualAmount(hk_jm_li);
			accounttingService.add(account);
		}
		if(hk_fh>0){ 
			account.setInOut("2");
			account.setAccount("服务费");
			account.setSubject("业务往来-一次性还款退还服务费");
			account.setPlanAmount(hk_fh);
			account.setActualAmount(hk_fh);
			accounttingService.add(account);
		}
		
		//对公还款差额不等于 0 的时候  处理记账
		if(shortfall!=0){ 
			double jzje=shortfall;
			if(jzje>0){
				account.setInOut("2");
				account.setAccount("风险金");
				account.setSubject("业务往来-风险金补本金");
			}else {
				account.setInOut("1");
				account.setAccount("风险金");
				account.setSubject("业务往来-每期实收利息补风险金");
				jzje=Math.abs(jzje);
			}
			account.setPlanAmount(jzje);
			account.setActualAmount(jzje);
			accounttingService.add(account);
		}
		//记录催收绩效信息,按结清处理
		collectionBatchService.handleCollectionAfterPayment(contractNo);
		
		String msgString ="对公还款一次性结算成功。收款金额：￥" + publicAmount + "元。";
		if(shortfall!=0){ 
			if(shortfall>0){
				msgString+="少收：￥"+shortfall;
			}else{
				msgString+="多收：￥"+Math.abs(shortfall);
			}
		}

		return new ResultObj("0",msgString, true);
		
	}

	
}
