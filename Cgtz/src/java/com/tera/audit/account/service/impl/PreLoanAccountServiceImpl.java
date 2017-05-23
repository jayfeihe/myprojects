package com.tera.audit.account.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.account.model.BillAcct;
import com.tera.audit.account.model.BillBase;
import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.model.form.AccountJsonMsg;
import com.tera.audit.account.service.IBillAcctService;
import com.tera.audit.account.service.IBillBaseService;
import com.tera.audit.account.service.IPreLoanAccountService;
import com.tera.audit.branch.model.AuditFormBean;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.mail.service.MailService;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.util.StringUtils;


/** 贷前核帐Service
 * @author QYANZE
 *
 */
@Service("preLoanAccountService")
public class PreLoanAccountServiceImpl implements IPreLoanAccountService {

	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private ICommonHandlerService commonHandlerService;
	@Autowired
	private ICollateralService collateralService;
	@Autowired
	private IBillAcctService billAcctService;
	@Autowired
	private IBillBaseService billBaseService;
	@Autowired
	private TaskExecutor taskExecutor;

	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IPreLoanAccountService#operateProcess(com.tera.audit.branch.model.AuditFormBean, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public JsonMsg operateProcess(AuditFormBean formBean, String loginId,String orgId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		// 贷前核帐申请放款
		String decision = formBean.getDecision();
		String loanId = formBean.getLoanId();
		String remark = formBean.getRemark();
		
		
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		String bizKey = loanBase.getLoanId();
		String state1st = null,state2nd = null;
		
		// 通过-财务审批
		if ("1".equals(decision)) {
			String nextUser = null;
			
			decision = "通过";
			
			String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_I1);
			if (StringUtils.isNotNullAndEmpty(prevUser)) {
				nextUser = prevUser;
			} else {
				nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_ACCT_STAFF, BusinessConstants.ORG_CODE,loanBase.getOrg());
			}
			
			if (StringUtils.isNotNullAndEmpty(nextUser)) {
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_I1, nextUser, loginId, decision,remark);
				state1st = "I";
				state2nd = "1";
				loanBase.setAcctTime(nowTime);
			} else {
				return new JsonMsg(false, CommonConstant.PROCESS_I1 + "没有相关操作人，提交失败！");
			}
			
			// 发送邮件提醒
			String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
			if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
				this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_I1, loanId, reciveEmail));
			}
		}
		
		// 不通过
		if ("0".equals(decision)) {
			decision = "不通过";
			
			String node = formBean.getNode();
			
			// 到录入申请
			if (CommonConstant.PROCESS_A.equals(node)) {
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_A, loanBase.getSalesman(), loginId,
						decision + ",退回到" + node, remark);
				state1st = "A";
				state2nd = "2";
			}
			
			// 到分公司审批
			if (CommonConstant.PROCESS_B.equals(node)) {
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_B);
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_B, prevUser, loginId,
						decision + ",退回到" + node, remark);
				state1st = "B";
				state2nd = "2";
			}
			
			// 到风控经理审批
			if (CommonConstant.PROCESS_C.equals(node)) {
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_C);
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_C, prevUser, loginId,
						decision + ",退回到" + node, remark);
				state1st = "C";
				state2nd = "2";
				
				// 清除核价信息
				this.collateralService.clearPriceInfo(loanId, loginId, nowTime);
			}
			
			// 到评审会初审
			if (CommonConstant.PROCESS_D.equals(node)) {
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_D);
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_D, prevUser, loginId,
						decision + ",退回到" + node, remark);
				state1st = "D";
				state2nd = "2";
			}
			
			// 到评审会复核
			if (CommonConstant.PROCESS_E.equals(node)) {
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_E);
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_E, prevUser, loginId,
						decision + ",退回到" + node, remark);
				state1st = "E";
				state2nd = "3";
			}
			
			// 到风控总监审批
			if (CommonConstant.PROCESS_F.equals(node)) {
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_F);
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_F, prevUser, loginId,
						decision + ",退回到" + node, remark);
				state1st = "F";
				state2nd = "2";
			}
			
			// 到法务初审
			if (CommonConstant.PROCESS_G1.equals(node)) {
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_G1);
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_G1, prevUser, loginId,
						decision + ",退回到" + node, remark);
				state1st = "G";
				state2nd = "4";
			}
			
			// 到法务内勤
			if (CommonConstant.PROCESS_G2.equals(node)) {
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_G2);
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_G2, prevUser, loginId,
						decision + ",退回到" + node, remark);
				state1st = "G";
				state2nd = "4";
			}
			
			// 到法务复核
			if (CommonConstant.PROCESS_G3.equals(node)) {
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_G3);
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_G3, prevUser, loginId,
						decision + ",退回到" + node, remark);
				state1st = "G";
				state2nd = "4";
			}
		} 
		
		// 更新loanBase信息
		loanBase.setState1st(state1st);
		loanBase.setState2nd(state2nd);
		loanBase.setUpdateTime(nowTime);
		loanBase.setUpdateUid(loginId);
		this.loanBaseService.updateOnlyChanged(loanBase);
		
		return new JsonMsg(true,"成功");
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.account.service.impl.IPreLoanAccountService#collectInterest(com.tera.audit.account.model.form.AccountFormBean, java.lang.String)
	 */
	@Override
	@Transactional
	public AccountJsonMsg collectInterest(AccountFormBean bean, String loginId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		//交易表中账目(1收利息2收本金3续贷本金转移4付放款5付保证金6收会员服务费7收法律服务费8收转贷费9收保证金10收其他费用)
		// 交易表记账
		BillAcct acct = new BillAcct();
		acct.setLoanId(bean.getLoanId());
		acct.setContractId(bean.getContractId());
		acct.setAmt(bean.getAmt());
		acct.setRemark(bean.getRemark());
		acct.setType("1"); // 收
		acct.setSubject("收利息"); // 收利息
		acct.setProof(bean.getProof());
		acct.setNum("1"); // 第一期
		acct.setOperTime(nowTime);
		acct.setOperUid(loginId);
		this.billAcctService.add(acct);
		
		// 公司账户表记账
		BillBase base = new BillBase();
		base.setLoanId(bean.getLoanId());
		base.setContractId(bean.getContractId());
		base.setAmt(bean.getAmt());
		base.setRemark(bean.getRemark());
		base.setType("1"); // 收
		base.setSubject("1"); // 收利息
		base.setProof(bean.getProof());
		base.setCreateUid(loginId);
		base.setCreateTime(nowTime);
		base.setState("1"); // 默认为1
		this.billBaseService.add(base);
		
		return new AccountJsonMsg(bean.getLoanId(),bean.getContractId(),true, "成功");
	}
}
