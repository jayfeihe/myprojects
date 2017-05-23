package com.tera.audit.branch.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.audit.branch.service.IBranchAuditService;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.mail.service.MailService;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.service.OrgService;
import com.tera.util.StringUtils;

@Service("branchAuditService")
public class BranchAuditServiceImpl implements IBranchAuditService {

	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private ICommonHandlerService commonHandlerService;
	@Autowired
	private OrgService orgService;

	@Autowired
	private ICollateralService collateralService;
	@Autowired
	private TaskExecutor taskExecutor;
	
	/* (non-Javadoc)
	 * @see com.tera.audit.branch.service.impl.IBranchAuditService#operateProcess(com.tera.audit.branch.model.AuditFormBean, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public JsonMsg operateProcess(AuditFormBean bean, String loginId,String orgId) throws Exception {
		String decision = bean.getDecision();
		String loanId = bean.getLoanId();
		String remark = bean.getRemark();
		
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		String bizKey = loanBase.getLoanId();
		String state1st = null,state2nd = null;
		
		// 通过
		if ("1".equals(decision)) {
			decision = "通过";
			Org saleOrg = this.orgService.queryByOrgId(loanBase.getOrg());
			double branchAuditAmount = saleOrg.getAduitAmt();
			double loanAmt = loanBase.getLoanAmt();
			
			// 营业部审批额度为0，取分公司审批额度
			if (0d == branchAuditAmount) {
				Org branchOrg = this.orgService.queryByOrgId(orgId);
				branchAuditAmount = branchOrg.getAduitAmt();
			}
			
			
			String nextUser = null;
			
			// 小于等于,提交到风控初审
			if (loanAmt <= branchAuditAmount) {
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_C);
				if (StringUtils.isNotNullAndEmpty(prevUser)) {
					nextUser = prevUser;
				} else {
					nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_RISK_MGR, BusinessConstants.ORG_CODE,loanBase.getOrg());
				}
				
				if (StringUtils.isNotNullAndEmpty(nextUser)) {
					this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_C, nextUser, loginId, decision,remark);
					state1st = "C";
					state2nd = "1";
					loanBase.setIsBeyond("0");
					loanBase.setRiskFirstTime(nowTime);
					
					// 清除核价信息
					this.collateralService.clearPriceInfo(loanId, loginId, nowTime);
					
				} else {
					return new JsonMsg(false, CommonConstant.PROCESS_C + "没有相关操作人，提交失败！");
				}

				// 发送邮件提醒
				String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
				if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
					this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_C, loanId, reciveEmail));
				}
			} else {
				// 大于,提交到评审会初审
				String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_D);
				if (StringUtils.isNotNullAndEmpty(prevUser)) {
					nextUser = prevUser;
				} else {
					nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_JUDGE_STAFF, BusinessConstants.ORG_CODE,loanBase.getOrg());
				}
				if (StringUtils.isNotNullAndEmpty(nextUser)) {
					this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_D, nextUser, loginId, decision,remark);
					state1st = "D";
					state2nd = "1";
					loanBase.setIsBeyond("1");
					loanBase.setMeetFirstTime(nowTime);
				} else {
					return new JsonMsg(false, CommonConstant.PROCESS_D + "没有相关操作人，提交失败！");
				}
				
				// 发送邮件提醒
				String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
				if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
					this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_D, loanId, reciveEmail));
				}
			}
		}
		
		// 不通过
		if ("0".equals(decision)) {
			decision = "不通过";
			String node = bean.getNode();
			// 退回到申请
			if (CommonConstant.PROCESS_A.equals(node)) {
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_A, loanBase.getSalesman(), loginId,
						decision + ",退回到" + node, remark);
				state1st = "A";
				state2nd = "2";
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
}
