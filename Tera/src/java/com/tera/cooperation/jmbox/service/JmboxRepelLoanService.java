package com.tera.cooperation.jmbox.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.accounting.model.Accountting;
import com.tera.accounting.service.AccounttingService;
import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.form.RepelLoanFBean;
import com.tera.credit.service.CreditAppService;
import com.tera.payment.model.Payment;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.payment.service.PaymentService;

/**
 * 积木盒子 拒贷服务类
 * @author zhang.yue
 *
 */
@Service("jmboxRepelLoanService")
public class JmboxRepelLoanService {

	private final static Logger log = Logger.getLogger(JmboxRepelLoanService.class);

	@Autowired(required=false)
    private ContractService contractService;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false)
	private CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	ProcessService processService;
	@Autowired(required=false) //自动注入
	AccounttingService<Accountting> accounttingService;
	@Autowired(required=false) //自动注入
	PaymentService<Payment> paymentService;
	
	/**
	 * 审核流程跳转
	 * @param appId			申请ID
	 * @param nextState		下一个节点名称
	 * @param operator		当前流程 实际处理人（当前登录用户）
	 * @param processer		下个流程的 待处理人
	 */
	@Transactional
	public void contract(String appId,String nextState,String operator,String processer,
				String logContent1,String logContent2,String logContent3,String logContent4){
		//得到当前流程					
		List<BpmTask> taskList = processService.getProcessInstanceByBizKey(appId);
		BpmTask task=taskList.get(0);
		task.setOperator(operator);
		task.setVariable("logContent1",logContent1);
		task.setVariable("logContent2",logContent2);
		task.setVariable("logContent3",logContent3);
		task.setVariable("logContent4",logContent4);
		task = processService.goNext(task, nextState, processer);
		
	}
	
	/***
	 * 积木盒子拒贷
	 * @param repelLoanFBean			积木盒子传入参数
	 * @param loginId
	 * @throws Exception 
	 * @return 
	 */
	@Transactional
	public boolean jmRepelLoan(RepelLoanFBean repelLoanFBean, String loginId) throws Exception {
		Map<String, Object> cQuerymap = new HashMap<String, Object>();
		cQuerymap.put("loanAppId", repelLoanFBean.getAppId());
		cQuerymap.put("contractClass", "03");
		List<Contract> contractList = contractService.queryList(cQuerymap);
		if(null != contractList && contractList.size() > 0){
			/*修改合同状态为0*/
			Contract contract = contractList.get(0);
			contract.setState("0");
			contractService.updateOnlyChanged((Contract) contract);
			/*清空支付列表*/
			paymentService.deleteByContractNo(contract.getContractNo());
			/*清空还款计划*/
			loanRepayPlanService.deleteByContractNo(contract.getContractNo());
			/*修改申请状态为24*/
			Map<String, Object> appMap = new HashMap<String, Object>();
			appMap.put("appId", repelLoanFBean.getAppId());
			CreditApp creditApp = creditAppService.queryList(appMap).get(0);
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
			//拒贷-跳转流程
			this.contract(contract.getLoanAppId(), Constants.PROCESS_STATE_REJECT, loginId, BpmConstants.SYSTEM_SYS, "拒贷", repelLoanFBean.getFeedbackDescribe(), "拒贷码", "拒贷码1：" + repelLoanFBean.getDecisionCode3() + "——拒贷码2：" + repelLoanFBean.getDecisionCode4());
			this.contract(contract.getLoanAppId(), Constants.PROCESS_END_APP, loginId, BpmConstants.SYSTEM_SYS, null, null, null, null);
			return true;
		}
		return false;
	}
	
}
