package com.tera.audit.risk.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.risk.service.IRiskReviewService;
import com.tera.mail.service.MailService;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.util.StringUtils;


/** 风控复核Service
 * @author QYANZE
 *
 */
@Service("riskReviewService")
public class RiskReviewServiceImpl implements IRiskReviewService {

	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private ICommonHandlerService commonHandlerService;
	@Autowired
	private ICollateralService collateralService;
	@Autowired
	private TaskExecutor taskExecutor;

	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.IRiskReviewService#operateProcess(com.tera.audit.branch.model.AuditFormBean, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public JsonMsg operateProcess(AuditFormBean bean, String loginId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		// 风控复核
		String decision = bean.getDecision();
		String loanId = bean.getLoanId();
		String remark = bean.getRemark();
		
		
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		String bizKey = loanBase.getLoanId();
		String state1st = null,state2nd = null;
		
		// 通过-法务初审
		if ("1".equals(decision)) {
			String nextUser = null;
			
			decision = "通过";
			
			String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_G1);
			if (StringUtils.isNotNullAndEmpty(prevUser)) {
				nextUser = prevUser;
			} else {
				nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_LAW_STAFF, BusinessConstants.ORG_CODE, loanBase.getOrg());
			}
			
			if (StringUtils.isNotNullAndEmpty(nextUser)) {
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_G1, nextUser, loginId, decision,remark);
				state1st = "G";
				state2nd = "1";
				loanBase.setLawTime(nowTime);
			} else {
				return new JsonMsg(false, CommonConstant.PROCESS_G1 + "没有相关操作人，提交失败！");
			}
			
			// 发送邮件提醒
			String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
			if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
				this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_G1, loanId, reciveEmail));
			}
		}
		
		// 不通过
		if ("0".equals(decision)) {
			decision = "不通过";
			
			String node = bean.getNode();
			
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
