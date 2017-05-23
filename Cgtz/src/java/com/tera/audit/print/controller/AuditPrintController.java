package com.tera.audit.print.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.judge.model.JudgeAdv;
import com.tera.audit.judge.service.IJudgeAdviceService;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.Contact;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.IContactService;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.retplan.model.RetPlan;
import com.tera.audit.retplan.service.IRetPlanCalculateService;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.service.ProcessService;
import com.tera.sys.model.Dept;
import com.tera.sys.model.UserExt;
import com.tera.sys.service.DeptService;
import com.tera.sys.service.UserExtService;
import com.tera.util.DateUtils;
import com.tera.util.RMBUpper;
import com.tera.util.StringUtils;

/**
 * 打印Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/print")
public class AuditPrintController {

	private static final Logger log = Logger.getLogger(AuditPrintController.class);
	
	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private ILoanAppService loanAppService;
	@Autowired
	private ICollateralService collateralService;
	@Autowired
	private ProcessService processService;
	@Autowired
	private IJudgeAdviceService judgeAdviceService;
	@Autowired
	private IContractService contractService;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private IContactService contactService;
	@Autowired
	private IRetPlanCalculateService retPlanCalculateService;
	
	
	/**
	 * 业务审批单
	 * 
	 * @param loanId 申请编号
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/businessAuditPrint.do")
	public String businessAudit(String loanId,ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		Map<String, Object> queryMap = new HashMap<String,Object>();
		// 基本信息
		queryMap.put("loanId", loanId);
		queryMap.put("mainFlag", "1"); // 主借款人
		LoanApp loanApp = this.loanAppService.queryList(queryMap).get(0);
		
		// 申请信息
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		
		// 计算费用
		List<RetPlan> createRetPlan = this.retPlanCalculateService.createRetPlan(loanId, DateUtils.formatDate(DateUtils.getDateNow()));
		
		RetPlan retPlan = createRetPlan.get(0);
		
		map.put("retPlan", retPlan);
		
		// 质/抵押物
		queryMap.clear();
		queryMap.put("loanId", loanId);
		List<Collateral> colls = this.collateralService.queryList(queryMap );
		
		map.put("loanBase", loanBase);
		map.put("loanApp", loanApp);
		map.put("colls", colls);
		
		// 获取审批人员
		List<BpmLog> logs = null;
		String operatorName = null;
		Timestamp operatorTime = null;
		
		// 分公司业务员
		logs = this.processService.getProcessHistoryLog(loanId, CommonConstant.PROCESS_A);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getIntime();
			map.put("saleUser", operatorName);
			map.put("inputTime", operatorTime);
		}
		
		// 分公司经理
		logs = this.processService.getProcessHistoryLog(loanId, CommonConstant.PROCESS_B);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("branchUser", operatorName);
			map.put("branchTime", operatorTime);
		}
		
		// 风控经理
		logs = this.processService.getProcessHistoryLog(loanId, CommonConstant.PROCESS_C);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("riskFirstUser", operatorName);
			map.put("riskFirstTime", operatorTime);
		}
		
		// 评审会秘书
		logs = this.processService.getProcessHistoryLog(loanId, CommonConstant.PROCESS_D);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("meetFirstAuditUser", operatorName);
			map.put("meetFirstAuditTime", operatorTime);
		}
		
		// 评审会评委
		queryMap.clear();
		queryMap.put("loanId", loanBase.getLoanId());
		queryMap.put("num", this.judgeAdviceService.getNextNum(loanBase.getLoanId())-1);
		List<JudgeAdv> advs = this.judgeAdviceService.queryList(queryMap);
		if (advs != null && advs.size() > 0) {
			map.put("judgeAdvs", advs);
		}
		
		// 风控总监
		logs = this.processService.getProcessHistoryLog(loanId, CommonConstant.PROCESS_F);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("riskReviewUser", operatorName);
			map.put("riskReviewTime", operatorTime);
		}
		
		// 法务初审
		logs = this.processService.getProcessHistoryLog(loanId, CommonConstant.PROCESS_G1);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("lawFirstUser", operatorName);
			map.put("lawFirstTime", operatorTime);
		}
		
		// 法务内勤
		logs = this.processService.getProcessHistoryLog(loanId, CommonConstant.PROCESS_G2);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("lawInsiderUser", operatorName);
			map.put("lawInsiderTime", operatorTime);
		}
		
		// 法务复核
		logs = this.processService.getProcessHistoryLog(loanId, CommonConstant.PROCESS_G3);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("lawReviewUser", operatorName);
			map.put("lawReviewTime", operatorTime);
		}
		
		// 财务部
		logs = this.processService.getProcessHistoryLog(loanId, CommonConstant.PROCESS_I1);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("financeUser", operatorName);
			map.put("financeTime", operatorTime);
		}
		
		log.info(thisMethodName+":end");
		return "print/businessAuditPrint";
	}
	
	/**
	 * 合同打印
	 * 
	 * @param contractId 合同编号
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/contractPrint.do")
	public String contractPrint(String contractId,ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		// 合同信息
		Contract contract = this.contractService.queryByContractId(contractId);
		map.put("contract", contract);
		
		// 合同大写金额
		String upperLoanAmt = RMBUpper.toBigAmt(contract.getLoanAmt());
		map.put("upperLoanAmt", upperLoanAmt);
		
		Map<String, Object> queryMap = new HashMap<String,Object>();
		// 基本信息
		queryMap.put("loanId", contract.getLoanId());
		queryMap.put("mainFlag", "1"); // 主借款人
		LoanApp loanApp = this.loanAppService.queryList(queryMap).get(0);
		map.put("loanApp", loanApp);
		
		// 申请信息
		LoanBase loanBase = this.loanBaseService.queryByLoanId(contract.getLoanId());
		map.put("loanBase", loanBase);
		
		// 联系人
		List<Contact> contacts = this.contactService.queryByLoanId(contract.getLoanId());
		if (contacts != null && contacts.size() > 0) {
			for (Contact contact : contacts) {
				if (StringUtils.isNotNullAndEmpty(contact.getTel())) {
					map.put("contact", contact);
					break;
				}
			}
		}
		
		// 获取审批人员
		List<BpmLog> logs = null;
		String operatorName = null;
		Timestamp operatorTime = null;
		
		// 放款申请人信息
		logs = this.processService.getProcessHistoryLog(contract.getLoanId(), CommonConstant.PROCESS_H);
		if (logs != null && logs.size() > 0) {
			BpmLog bpmLog = logs.get(0);
			map.put("loanApplyUser", bpmLog.getOperatorName());
			map.put("loanApplyTime", DateUtils.formatDate(bpmLog.getOuttime()));
			
			// 部门
			UserExt userExt = this.userExtService.queryByKey(bpmLog.getOperator());
			int deptId = userExt.getDeptId();
			if (0 != deptId) {
				Dept dept = this.deptService.queryByKey(deptId);
				map.put("deptName", dept.getName());
			}
			
		}
		
		// 分公司经理
		logs = this.processService.getProcessHistoryLog(contract.getLoanId(), CommonConstant.PROCESS_B);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("branchUser", operatorName);
			map.put("branchTime", operatorTime);
		}
		
		// 风控总监
		logs = this.processService.getProcessHistoryLog(contract.getLoanId(), CommonConstant.PROCESS_F);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("riskReviewUser", operatorName);
			map.put("riskReviewTime", operatorTime);
		}
		
		// 法务复核
		logs = this.processService.getProcessHistoryLog(contract.getLoanId(), CommonConstant.PROCESS_G3);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("lawReviewUser", operatorName);
			map.put("lawReviewTime", operatorTime);
		}

		// 财务部
		logs = this.processService.getProcessHistoryLog(contract.getLoanId(), CommonConstant.PROCESS_I1);
		if (logs != null && logs.size() > 0) {
			operatorName = logs.get(0).getOperatorName();
			operatorTime = logs.get(0).getOuttime();
			map.put("financeUser", operatorName);
			map.put("financeTime", operatorTime);
		}
		log.info(thisMethodName+":end");
		return "print/contractPrint";
	}
}
