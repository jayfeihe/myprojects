package com.tera.cooperation.common.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.channeltotal.dao.ChannelTotalDao;
import com.tera.channeltotal.model.ChannelTotal;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.cooperation.common.constants.CooperationConstants;
import com.tera.cooperation.common.dao.ConfirmLoanDao;
import com.tera.cooperation.common.model.ConfirmLoanQBean;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.payment.model.Payment;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;
import com.tera.util.DateUtils;


/**
 * 渠道确认放款管理Service
 * 
 * @author QYANZE
 *
 */
@Service("confirmLoanService")
public class ConfirmLoanService {

	@Autowired(required=false)
	private ConfirmLoanDao dao;
	@Autowired(required=false)
	private CreditAppService creditAppService;
	@Autowired(required=false)
	private ContractService contractService;
	@Autowired(required=false) //自动注入
	private CreditDecisionService creditDecisionService;
	@Autowired(required=false) //自动注入
	private AccounttingService<Accountting> accounttingService;
	@Autowired(required=false) //自动注入
	private PaymentService<Payment> paymentService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false)
	private ChannelTotalDao channelTotalDao;
	
	public int queryConfirmLoanCount(Map<String, Object> map) {
		return dao.queryConfirmLoanCount(map);
	}
	
	public List<ConfirmLoanQBean> queryConfirmLoanList(Map<String, Object> map) {
		return dao.queryConfirmLoanList(map);
	}
	
	/**
	 * 渠道放款管理
	 * @param qBean
	 * @param channelType 渠道类型
	 * @param flag 确认放款/拒贷标志
	 * @param loginId
	 * @throws Exception
	 */
	@Transactional
	public void loanManageOparete(ConfirmLoanQBean qBean,
			String flag, String loginId) throws Exception {
		String appId = qBean.getAppNo();
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("appId", appId);
		CreditApp creditApp = creditAppService.queryList(queryMap).get(0);
		queryMap.put("loanAppId", appId);
		Contract contract = contractService.queryList(queryMap).get(0);
		
		// 确认放款
		if ("Y".equals(flag)) {
			long confirmLoanDate = new SimpleDateFormat("yyyy-MM-dd").parse(qBean.getConfirmLoanDate()).getTime();
			contract.setChannelTime(new Timestamp(confirmLoanDate));
			contract.setChannelState(CooperationConstants.COOP_CHANNEL_STATE_5);
			contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    		contract.setState("2");
    		//更新合同
        	contractService.updateOnlyChanged(contract);
    		//更新 还款计划 与 支付信息状态
			paymentService.paymentSuccessUpdate(contract.getContractNo(),
					DateUtils.getDate(qBean.getConfirmLoanDate()));
    		
			List<BpmTask> bpmTasks = processService
					.getProcessInstanceByBizKey(appId);
    		BpmTask task = bpmTasks.get(0);
			if (task != null && "放款".equals(task.getState())) {
    			task.setOperator(loginId);
	    		task = processService.goNext(task, Constants.PROCESS_END_APP, task.getProcesser());
    		}
    		//更新 APP 状态
    		creditApp.setState("23");
    		creditApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    		creditAppService.update(creditApp);
    		//记账
    		queryMap.put("appId", appId);
    		queryMap.put("state", "1");
    		queryMap.put("type", "0");
    		//最终决策
			CreditDecision creditDecision = creditDecisionService.queryList(
					queryMap).get(0);
    		Accountting account=new Accountting();
    		account.setInOut("2");
    		account.setContractNo(contract.getContractNo());
    		account.setSource("");
    		account.setPeriodNum(0);
    		account.setState("1");
    		account.setOperator(BpmConstants.SYSTEM_SYS);
    		account.setOrgId(contract.getOrgId());
    		long time=System.currentTimeMillis();
    		account.setCreateTime(new Timestamp(time));
    		account.setUpdateTime(new Timestamp(time));
			account.setId(0);
			account.setAccount("出借金额");
			account.setSubject("业务往来-放款本金");
			account.setPlanAmount(creditDecision.getAmount());
			account.setActualAmount(creditDecision.getAmount());
			accounttingService.add(account);
		}
		
		// 拒贷
		if ("N".equals(flag)) {
			/*修改合同状态为0*/
			contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			contract.setChannelState(CooperationConstants.COOP_CHANNEL_STATE_7);
			contract.setState("0");
			contractService.updateOnlyChanged(contract);
			/*清空支付列表*/
			paymentService.deleteByContractNo(contract.getContractNo());
			/*清空还款计划*/
			loanRepayPlanService.deleteByContractNo(contract.getContractNo());
			/*修改申请状态为24*/
			creditApp.setState("24");
			creditAppService.updateOnlyChanged(creditApp);
			/*清空记账*/
			Map<String, Object> accountMap = new HashMap<String, Object>();
			accountMap.put("contractNo", contract.getContractNo());
			accountMap.put("subject", "业务往来-放款风险金");
			List<Accountting> accounttingList1 = accounttingService.queryList(accountMap);
			for (Accountting accountting : accounttingList1) {
				accounttingService.delete(accountting);
			}
			accountMap.put("subject", "业务往来-放款服务费");
			List<Accountting> accounttingList2 = accounttingService.queryList(accountMap);
			for (Accountting accountting : accounttingList2) {
				accounttingService.delete(accountting);
			}
			// 拒贷-跳转流程
			this.denyWorkFlow(qBean,loginId);
		}
	}
	
	/**
	 * 渠道拒贷流程
	 * @param appId
	 * @param params
	 */
	@Transactional
	private void denyWorkFlow(ConfirmLoanQBean qBean, String loginId) {
		
//		String channelType = qBean.getChannelType();
		String operator = loginId;
		String logContent1 = "拒贷";
//		String logContent2 = "";
//		if (CooperationConstants.COOP_CHANNEL_TYPE_DX.equals(channelType)) {
//			logContent2 = "鼎轩拒贷";
//		} 
//		if (CooperationConstants.COOP_CHANNEL_TYPE_MD.equals(channelType)) {
//			logContent2 = "MD拒贷";
//		}
		
		Map<String, Object> channelMap = new HashMap<String, Object>();
		channelMap.put("channelCode", qBean.getChannelType());
		List<ChannelTotal> channelList = channelTotalDao.queryList(channelMap);
		String logContent2 = channelList.get(0).getChannelName()+"拒贷";
		
		String logContent3 = "拒贷码";
		String logContent4 = "拒贷码1：" + qBean.getDecisionCode1() +  
							"——拒贷码2："+ qBean.getDecisionCode2();
		String logContent5 = qBean.getRemark();
		
		// 得到当前流程
		BpmTask task  = processService
				.getProcessInstanceByBizKey(qBean.getAppNo()).get(0);
		task.setOperator(operator);
		task.setVariable("logContent1", logContent1);
		task.setVariable("logContent2", logContent2);
		task.setVariable("logContent3", logContent3);
		task.setVariable("logContent4", logContent4);
		task.setVariable("logContent5", logContent5);
		
		processService.goNext(task, Constants.PROCESS_STATE_REJECT,loginId);
		processService.goNext(task, Constants.PROCESS_END_APP,loginId);

	}
	
}
