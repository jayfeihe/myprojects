package com.tera.audit.account.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.account.model.BillAcct;
import com.tera.audit.account.model.BillInfo;
import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.service.IBillAcctService;
import com.tera.audit.account.service.IOffLineService;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.retplan.model.RetPlan;
import com.tera.audit.retplan.service.IRetPlanService;
import com.tera.feature.lenduser.model.LendUser;
import com.tera.feature.lenduser.model.LendUserLog;
import com.tera.feature.lenduser.service.ILendUserLogService;
import com.tera.feature.lenduser.service.ILendUserService;
import com.tera.mail.service.MailService;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;
import com.tera.util.StringUtils;


/** 线下放款Service
 * @author QYANZE
 *
 */
@Service("offLineService")
public class OffLineServiceImpl implements IOffLineService {

	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private ICommonHandlerService commonHandlerService;
	@Autowired
	private IContractService contractService;
	@Autowired
	private IBillAcctService billAcctService;
	@Autowired
	private ILendUserService lendUserService;
	@Autowired
	private ILendUserLogService lendUserLogService;
	@Autowired
	private IRetPlanService retPlanService;
	@Autowired
	private TaskExecutor taskExecutor;
	
	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IOffLineService#operateProcess(com.tera.audit.account.model.form.AccountFormBean, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public JsonMsg operateProcess(AccountFormBean formBean, String loginId,String orgId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		// 线下放款
		String decision = "";
		String loanId = formBean.getLoanId();
		String contractId = formBean.getContractId();
		String remark = formBean.getRemark();
		
		
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		String bizKey = loanBase.getLoanId();
		String state1st = null,state2nd = null;
		String nextUser = null;
		
		Contract contract = this.contractService.queryByContractId(contractId);
		
		// 纯线下方式-流程结束
		if (Contract.LOAN_WAY_OFF_LINE.equals(contract.getGetLoanWay())) {
			decision = "确认放款";
			nextUser = CommonConstant.SYS_USER;
			this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_END, nextUser, loginId, decision,remark);
			
		} else 
		// 债权转让方式-法律意见
		if (Contract.LOAN_WAY_ZQ.equals(contract.getGetLoanWay())) {
			decision = "法律意见";
			
			String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_M);
			if (StringUtils.isNotNullAndEmpty(prevUser)) {
				nextUser = prevUser;
			} else {
				nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_LAW_STAFF, BusinessConstants.ORG_CODE,loanBase.getOrg());
			}
			
			if (StringUtils.isNotNullAndEmpty(nextUser)) {
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_M, nextUser, loginId, decision,remark);
			} else {
				return new JsonMsg(false, CommonConstant.PROCESS_M + "没有相关操作人，提交失败！");
			}

			// 发送邮件提醒
			String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
			if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
				this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_M, loanId, reciveEmail));
			}
		}
		
		// 申请状态为确认放款
		state1st = "L";
		state2nd = "1"; 
		
		// 更新loanBase信息
		loanBase.setLoanTime(nowTime);// 放款时间
		loanBase.setState1st(state1st);
		loanBase.setState2nd(state2nd);
		loanBase.setUpdateTime(nowTime);
		loanBase.setUpdateUid(loginId);
		this.loanBaseService.updateOnlyChanged(loanBase);
		
		// 更新合同
		if ("0".equals(loanBase.getIsRenew())) {
			contract.setStartDate(DateUtils.getDateNow());
		} else
		// 续贷操作，续贷的话，查找上一次续贷合同，注意区分不是原始的申请合同
		if("1".equals(loanBase.getIsRenew())) {
			// 查找上一次合同
			String origLoanId = loanBase.getOrigLoanId();
			Map<String, Object> queryMap = new HashMap<String,Object>();
			
			// 第一次续贷查找原申请
			if (1 == loanBase.getRenewNum()) {
				queryMap.put("loanId", origLoanId);
			} 
			// 不是第一次续贷，查找上一次续贷的申请
			else {
				queryMap.put("loanId", origLoanId);
				queryMap.put("renewNum", loanBase.getRenewNum()-1);
				LoanBase lastRenewBase = this.loanBaseService.queryList(queryMap).get(0);
				queryMap.clear();
				queryMap.put("loanId", lastRenewBase.getLoanId());
			}
			
			List<Contract> origConts = this.contractService.queryList(queryMap );
			// 续贷合同开始时间是上一次合同结束时间加一天
			if (origConts != null && origConts.size() > 0) {
				contract.setStartDate(DateUtils.addDay(origConts.get(0).getEndDate(), 1));
			} else {
				contract.setStartDate(DateUtils.getDateNow());
			}
			
			// 原申请还款计划表修订
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("contractId", origConts.get(0).getContractId());
			map.put("type", "2");// 本金
			List<RetPlan> plans = this.retPlanService.queryList(map);
			if (plans != null && plans.size() > 0) {
				RetPlan planCapital = plans.get(0);
				//planCapital.setRealCapital(planCapital.getPlanCapital());
				planCapital.setRetDate(DateUtils.getDateNow());
				planCapital.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				planCapital.setState("4");//续贷转移
				
				this.retPlanService.updateOnlyChanged(planCapital);
			}
		}
		
		contract.setEndDate(loanBase.getEndDate());
		contract.setDays(DateUtils.getDayRange(contract.getStartDate(), contract.getEndDate()) + 1);
		contract.setState("2"); // 合同中
		this.contractService.updateOnlyChanged(contract);
		
		// 生成还款计划      
		this.retPlanService.createRetPlan(contractId);
		// 平帐贷前收息
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("contractId", contractId);
		queryMap.put("type", "1"); // 收
		//queryMap.put("subject", "1"); // 收利息
		queryMap.put("num", "1"); // 第一期
		List<BillAcct> accts = this.billAcctService.queryList(queryMap);
		if (accts != null && accts.size() > 0) {
			
			BillInfo info=new BillInfo();
			for (BillAcct billAcct : accts) {
				info.setContractId(billAcct.getContractId());
				info.setIntAmt(billAcct.getAmt());
				info.setRemarks(billAcct.getRemark());
				info.setLoginId(billAcct.getOperUid());
				info.setProof(billAcct.getProof());
				info.setRetnum(Integer.valueOf(billAcct.getNum()));
				info.setPayTime(billAcct.getOperTimeStr());
				this.retPlanService.repaymentInt(info);
				this.billAcctService.delete(billAcct.getId());//删除之前的记录
			}
			
		} 
		
		// 记账
		if ("0".equals(loanBase.getIsRenew())) {
			// 正常放款记账
			this.accountProcess(formBean, loginId, nowTime);
		} else 
			// 续贷放款记账
			if ("1".equals(loanBase.getIsRenew())) {
				this.renewAccount(formBean, loginId, nowTime);
			}
		
		return new JsonMsg(true,"成功");
	}

	/**
	 * 正常放款记账处理
	 * @param formBean
	 * @param loginId
	 * @param nowTime
	 * @throws Exception 
	 */
	@Transactional
	private void accountProcess(AccountFormBean formBean, String loginId, Timestamp nowTime) throws Exception {
		String loanId = formBean.getLoanId();
		String contractId = formBean.getContractId();
		String lendUserId = formBean.getLendUserId();
		String proof = formBean.getProof();
		double loanAmt = formBean.getAmt();
//		String getLoanWay = formBean.getGetLoanWay();
		String remark = formBean.getRemark();
		
		// 交易表记账
		BillAcct acct = new BillAcct();
		acct.setLoanId(loanId);
		acct.setContractId(contractId);
		acct.setAmt(loanAmt);
		acct.setProof(proof);
		acct.setRemark(remark);
		acct.setType("2"); // 付
		acct.setSubject("放款"); // 放款
		acct.setOperTime(nowTime);
		acct.setOperUid(loginId);
		this.billAcctService.add(acct);
		
		// 出借人资金变动记账
		LendUserLog log = new LendUserLog();
		log.setBizKey(loanId);
		log.setAmt(loanAmt);
		log.setLendUserId(lendUserId);
		log.setProof(proof);
		log.setRemark(remark);
		log.setType("2"); // 付
		log.setCreateTime(nowTime);
		log.setCreateUid(loginId);
		this.lendUserLogService.add(log);
		
		// 更新出借人账户余额
		LendUser lUser = this.lendUserService.queryByKey(lendUserId);
		LendUser lendUser = new LendUser();
		lendUser.setId(lUser.getId());
		lendUser.setAmt(MathUtils.sub(lUser.getAmt(), loanAmt));
		this.lendUserService.updateOnlyChanged(lendUser);
	}
	
	/**
	 * 续贷记账处理
	 * @param formBean
	 * @param loginId
	 * @param nowTime
	 * @throws Exception 
	 */
	@Transactional
	private void renewAccount(AccountFormBean formBean, String loginId, Timestamp nowTime) throws Exception {
		String loanId = formBean.getLoanId();
		String contractId = formBean.getContractId();
		String proof = formBean.getProof();
		double loanAmt = formBean.getAmt();
		String remark = formBean.getRemark();
		
		// 交易表记账
		BillAcct acct = new BillAcct();
		acct.setLoanId(loanId);
		acct.setContractId(contractId);
		acct.setAmt(loanAmt);
		acct.setProof(proof);
		acct.setRemark(remark);
		acct.setType("2"); // 付
		acct.setSubject("续贷本金转移"); // 续贷本金转移
		acct.setOperTime(nowTime);
		acct.setOperUid(loginId);
		this.billAcctService.add(acct);
	}
}
