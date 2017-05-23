package com.tera.payment.pay;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.phone.service.CollectionBatchService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.service.CreditAppService;
import com.tera.lend.model.LendApp;
import com.tera.lend.service.LendAppService;
import com.tera.match.model.Lend2match;
import com.tera.match.model.MatchResult;
import com.tera.match.service.Lend2matchService;
import com.tera.match.service.MatchResultService;
import com.tera.payment.constant.AllinpayConstant;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.model.Payment;
import com.tera.payment.model.ThirdPaymentLog;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;
import com.tera.payment.service.ThirdPaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.model.Workday;
import com.tera.sys.service.WorkdayService;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;

public abstract class PayService {

	private final static Logger log = Logger.getLogger(PayService.class);

	@Autowired(required=false)
	protected PaymentService<Payment> paymentService;
	@Autowired(required=false)
	protected ThirdPaymentService thirdPaymentService;
	@Autowired(required=false)
	protected MatchResultService<MatchResult> matchResultService;
	@Autowired(required=false)
	protected LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false)
	protected AccounttingService<Accountting> accounttingService;
	@Autowired(required=false)
	protected ContractService contractService;
	@Autowired(required=false)
	protected ProcessService processService;
	@Autowired(required=false)
	protected LendAppService lendAppService;
	@Autowired(required=false)
	protected ProductService<Product> productService;
	@Autowired(required=false)
	protected Lend2matchService<Lend2match> lend2matchService;
	@Autowired(required=false)
	protected WorkdayService<Workday> workdayService;
	@Autowired(required=false)
	protected CreditAppService creditAppService;
	@Autowired(required=false)
	protected CollectionBatchService collectionBatchService;
	

	/**
	 * 实时代收
	 * @param pmt
	 * @return
	 * @throws Exception
	 */
	public abstract Payment daishou(Payment pmt) throws Exception;

	/**
	 * 实时代付
	 * @param pmt
	 * @return
	 * @throws Exception
	 */
	public abstract Payment daiFu(Payment pmt) throws Exception;

	/**
	 * 把 同一个人的  收款信息 合并
	 * @param listPayInfo
	 * @return
	 * @throws CloneNotSupportedException
	 */
	protected List<Payment> mergePayment(List<Payment> listPayInfo) throws CloneNotSupportedException{
		if (listPayInfo.size()==0) {
			return listPayInfo;
		}
		String htNO="";
		Map<String, Payment> jgMap=new HashMap<String, Payment>();
		//合并 同一个人的  支付
		for (Payment pmt : listPayInfo) {
			//合同号 相同合并
			if(!htNO.equals(pmt.getContractNo())){
				htNO=pmt.getContractNo();
			}
			Payment zsPmt=jgMap.get(htNO);
			if(zsPmt==null){
				zsPmt= pmt.clone();
				//List 不实用引用
				zsPmt.setThirdPaymentLogList(new ArrayList<ThirdPaymentLog>());
				jgMap.put(htNO,zsPmt);
			}else{
				zsPmt.setActualAmount(MathUtils.add(zsPmt.getActualAmount(),pmt.getActualAmount()));
			}
			//添加log记录
			ThirdPaymentLog thirdPaymentLog=new ThirdPaymentLog();
			thirdPaymentLog.setPaymentId(pmt.getId());
			thirdPaymentLog.setOrderNo("");
			thirdPaymentLog.setContractNo(pmt.getContractNo());
			thirdPaymentLog.setPeriodNum(pmt.getPeriodNum());
			thirdPaymentLog.setSendTime(new Timestamp(System.currentTimeMillis()));
			thirdPaymentLog.setSourceAccount(AllinpayConstant.merchantIdPay);
			thirdPaymentLog.setTargetAccount(pmt.getAccounttNo());
			thirdPaymentLog.setAmount(pmt.getActualAmount());
			thirdPaymentLog.setSubject(pmt.getSubject());
			thirdPaymentLog.setState("2");
			thirdPaymentLog.setOperator("sysauto");
			thirdPaymentLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			thirdPaymentLog.setOrgId(pmt.getOrgId());
			thirdPaymentLog.setSn("0");
			pmt.addThirdPaymentLog(thirdPaymentLog);
			zsPmt.addThirdPaymentLog(thirdPaymentLog);
		}
		
		List<Payment> jgList=new ArrayList<Payment>();
		jgList.addAll(jgMap.values());
		return jgList;
	}
	
	/**
	 * 代收批处理
	 * @param listPayInfo   必须是根据合同号排序 的List
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Payment> batchDaishou(List<Payment> listPayInfo) throws Exception {	
		if (listPayInfo.size()==0) {
			return listPayInfo;
		}
		//处理 代收 合并同一个 合同的 申请
		List<Payment> mergePayList = mergePayment(listPayInfo);
		
		for (Payment pmt : mergePayList) {
			//执行 收款 
			this.daishou(pmt);
		}
		// 合并了的状态 拆分后 在同步
		for (Payment payment : mergePayList) {
			for (Payment pmInfo : listPayInfo) {
				if(payment.getContractNo().equals(pmInfo.getContractNo())){
					pmInfo.setState(payment.getState());
					pmInfo.setReason(payment.getReason());
					pmInfo.setUpdateTime(payment.getUpdateTime());
				}
			}
		}
		
		//最后操作 
		for (Payment pmt : listPayInfo) {
			paymentService.updateOnlyChanged(pmt);
			if("5".equals(pmt.getState())){  //只有成功 才处理 一下操作
				String contractNo=pmt.getContractNo();
				try {
					if(contractNo.startsWith("L")){//出借申请 借款
						LendApp lendApp=lendAppService.queryByAppId(contractNo);
						List<BpmTask> taskList = processService.getProcessInstanceByBizKey(contractNo);
						BpmTask bpmTask=taskList.get(0);
						bpmTask=processService.goNext(bpmTask,BpmConstants.SYSTEM_SYS);
						Product pt=productService.queryByName(lendApp.getProduct());
						long timeLong=System.currentTimeMillis();
						//放入撮合队列    》》自动撮合
						Lend2match l2=new Lend2match();
						l2.setLendAppId(contractNo);
						l2.setType("1"); // 1 新增  2差额
						l2.setMatchType("0");	//撮合类型 为0 自动
						l2.setAppTime(new Timestamp(timeLong));
						l2.setLendAmount(lendApp.getAmount());
						l2.setLendProduct(lendApp.getProduct());
						l2.setLendPeriod(pt.getPeriod());
						l2.setLendInterestRate(pt.getInterestRate());
						l2.setLendServiceRate(pt.getSreviceFeeRate());
						l2.setOrgId(lendApp.getOrgId());
						l2.setState("1");//初始状态 带撮合
						l2.setOrgId2("");
						l2.setCreateTime(new Timestamp(timeLong));
						l2.setUpdateTime(new Timestamp(timeLong));
						//计算Lend2match的结束时间，一旦支付成功，3个工作日开始计息，出借申请的结束日期不变
						Date dt = workdayService.afterWorkDay(l2.getCreateTime(), 4);
						l2.setValueDate(dt);
						dt = DateUtils.addMonth(dt, l2.getLendPeriod());
						dt = DateUtils.addDay(dt, -1);
						l2.setEndDate(dt); //供提前赎回使用
						lend2matchService.add(l2);
						// 计算还款  支付  添加还款 数据表
						paymentService.addInterest2LendPlan(lendApp.getAppId());
						log.info(l2);
						//记录 记账明细
						accounttingService.accountting(pmt);
					}else{//还款操作 
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("contractNo", pmt.getContractNo());
						map.put("periodNum", pmt.getPeriodNum());
						//查询当期还款计划
						List<LoanRepayPlan> listLoanRepayPlans=loanRepayPlanService.queryList(map);
						LoanRepayPlan loanRepayPlan=listLoanRepayPlans.get(0);
						//分析还款计划  
						loanRepayPlan=loanRepayPlanService.finishRepay(loanRepayPlan);
						accounttingService.repayPlanAccountting(loanRepayPlan);
						//更新合同状态
						contractService.ifContractEnd(pmt.getContractNo());
						//催收绩效记录
						collectionBatchService.handleCollectionAfterPayment( pmt.getContractNo());
					}
				} catch (Exception e) {
					log.error("批处理收款 成功后 记账处理时报错。Payment ID："+pmt.getId(),e);
				}
			}
		}
		return listPayInfo;
	}
	
	/**
	 * 批处理 付款 操作
	 * @param pmtList
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Payment> batchDaiFu(List<Payment> pmtList) throws Exception {	
		if (pmtList==null) {
			return pmtList;
		}
		for (Payment pmt : pmtList) {
			//调用付款接口
			this.daiFu(pmt);
			//保存 结果
			paymentService.update(pmt);
			//付款成功
			if("5".equals(pmt.getState())){
				
	    		if ("赎回资金".equals(pmt.getSubject())) {
	    			//科目是赎回资金，提前撤资，需要做一些特殊处理
	    			matchResultService.lendDivest(pmt.getContractNo());
	    		}else if("放款".equals(pmt.getSubject())){
	    			
	    			Map<String, Object> mapCon=new HashMap<String, Object>();
		    		mapCon.put("contractNo", pmt.getContractNo());
		    		mapCon.put("state", "1");
		    		List<Contract>listContracts=contractService.queryList(mapCon);
		    		if (listContracts.size()==0) {
		    			continue;
		    		}
		    		Contract contract=listContracts.get(0);
		    		String loanAppId=contract.getLoanAppId().trim();
	    			
	    			//抵押贷
		    		if(loanAppId.startsWith("B")) {
			    		//完成业务记账
			    		accounttingService.accountting(pmt);								    		
			    		if ("放款".equals(pmt.getSubject())) {
			    			//放款完成后的操作：合同处理，生成还款计划
				    		matchResultService.handleAfterPayment(pmt.getContractNo(), pmt.getUpdateTime());
				    		loanRepayPlanService.addRepayPlan(pmt.getContractNo());
						}
		    		} else if(loanAppId.startsWith("C")) {
		    			//完成业务记账
			    		accounttingService.accountting(pmt);
			    		//判断流程状态是否是放款，是就继续
			    		//实际上里面只有一个BpmTask对象
			    		List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(loanAppId);
			    		BpmTask task = bpmTasks.get(0);
			    		if (task!=null && "放款".equals(task.getState())) {
			    			task.setOperator(BpmConstants.SYSTEM_SYS);
				    		task = processService.goNext(task, "结束", task.getProcesser());
			    		}
			    		Map<String, Object> appMap = new HashMap<String, Object>();
			    		appMap.put("appId", loanAppId);
			    		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
			    		creditApp.setState("23");
			    		creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			    		creditAppService.update(creditApp);
			    		contract.setState("2");
			    		contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			    		contract.setChannelTime(new Timestamp(System.currentTimeMillis()));
			    		contractService.update(contract);
			    		//付款成功 调用，更新 还款计划，支付信息 状态
			    		paymentService.paymentSuccessUpdate(contract.getContractNo(), null);
		    		}
	    		}else if("付利息".equals(pmt.getSubject()) || "付本息".equals(pmt.getSubject())  ){
	    			//财富端 付款后记账
	    			Accountting accountting = new Accountting();
	    			accountting.setInOut("1"); //1 收
	    			accountting.setAccount("出借金额");
	    			accountting.setContractNo(pmt.getContractNo());
	    			accountting.setSubject("业务往来-赎回利息");
	    			accountting.setActualAmount(pmt.getActualAmount());
	    			accountting.setPlanAmount(pmt.getPlanAmount());
	    			accountting.setSource("0");
	    			accountting.setPeriodNum(pmt.getPeriodNum());
	    			accountting.setState("1");
	    			accountting.setOperator("sysauto");
	    			accountting.setOrgId(pmt.getOrgId());
	    			accountting.setCreateTime(new Timestamp(System.currentTimeMillis()));
	    			
	    			//付款金额
	    			double pmtAmount=pmt.getActualAmount();
	    			
	    			//付本息 要做单独处理，要记录 付本 账目
    				if("付本息".equals(pmt.getSubject())){
		    			LendApp lendApp=lendAppService.queryByAppId(pmt.getContractNo());
	    				double bj=lendApp.getAmount();
	    				
	    				accountting.setActualAmount(bj);
		    			accountting.setPlanAmount(bj);
		    			
		    			accountting.setSubject("业务往来-赎回本金");
		    			accounttingService.add(accountting);
		    			//如果是  付本 付息 状态 利息应该是 支付金额 减去 本金
	    				pmtAmount=pmtAmount-bj;
	    			}
    				accountting.setActualAmount(pmtAmount);
	    			accountting.setPlanAmount(pmtAmount);
	    			accountting.setSubject("业务往来-赎回利息");
	    			accounttingService.add(accountting);
	    			
	    			//获取 
	    			Map<String, Object> pmtMap=new HashMap<String, Object>();
	    			pmtMap.put("contractNo", pmt.getContractNo());
	    			pmtMap.put("inOut", "2");
	    			List<Payment> p_list=paymentService.queryList(pmtMap);
	    			Collections.sort(p_list, new Comparator<Payment>() {
	    				@Override
	    				public int compare(Payment o1, Payment o2) {
	    					Date o1Date=o1.getRepaymentDate();
	    					Date o2Date=o2.getRepaymentDate();
	    					return o1Date.after(o2Date)?-1:1;
	    				}
	    			});
	    			if(pmt.getRepaymentDateStr().equals(p_list.get(0).getRepaymentDateStr())){
		    			BpmTask task = null;
		    			//实际上里面只有一个BpmTask对象
		    			List<BpmTask> bpmTasks =  processService.getProcessInstanceByBizKey(pmt.getContractNo());
		    			for (int m = 0;m<bpmTasks.size();m++){
		    				task = bpmTasks.get(0);
		    			}
		    			if (task!=null&&"自动撮合".equals(task.getState())) {
		    				task.setOperator(BpmConstants.SYSTEM_SYS);
		    				task = processService.goNext(task, "交割", BpmConstants.SYSTEM_SYS);		
		    				task = processService.goNext(task, "结束", BpmConstants.SYSTEM_SYS);
		    			}
	    			}
	    		}
	    		
	    		
			}else if("6".equals(pmt.getState())){
				Map<String, Object> mapCon=new HashMap<String, Object>();
	    		mapCon.put("contractNo", pmt.getContractNo());
	    		mapCon.put("state", "1");
	    		List<Contract>listContracts=contractService.queryList(mapCon);
	    		if (listContracts.size()==0) {
	    			continue;
	    		}
	    		Contract contract=listContracts.get(0);
	    		String loanAppId=contract.getLoanAppId().trim();
	    		//信用贷 更改申请ID
	    		if(loanAppId.startsWith("C")) {
	    			Map<String, Object> appMap = new HashMap<String, Object>();
		    		appMap.put("appId", loanAppId);
		    		CreditApp creditApp = creditAppService.queryList(appMap).get(0);
		    		creditApp.setState("22");
		    		creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		    		creditAppService.update(creditApp);
	    		}
			}
		}
		return pmtList;
	}
}