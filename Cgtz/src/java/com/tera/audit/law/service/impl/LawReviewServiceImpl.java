package com.tera.audit.law.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.model.form.LawAuditFormBean;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.law.service.ILawReviewService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.mail.service.MailService;
import com.tera.sys.constant.BusinessConstants;
import com.tera.util.StringUtils;


/** 法务初审Service
 * @author QYANZE
 *
 */
@Service("lawReviewService")
public class LawReviewServiceImpl implements ILawReviewService {

	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private ICommonHandlerService commonHandlerService;
	@Autowired
	private ICollateralService collateralService;
	@Autowired
	private IContractService contractService;
	@Autowired
	private TaskExecutor taskExecutor;

	/* (non-Javadoc)
	 * @see com.tera.audit.law.service.ILawReviewService#operateProcess(com.tera.audit.law.model.form.LawAuditFormBean, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public LoanBaseJsonMsg operateProcess(LawAuditFormBean bean, String loginId,String orgId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		AuditFormBean formBean = bean.getFormBean();
		
		String decision = formBean.getDecision();
		String loanId = formBean.getLoanId();
		String remark = formBean.getRemark();
		
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		String bizKey = loanBase.getLoanId();
		String state1st = null,state2nd = null;
		
		// 保存合同信息
		Contract contract = bean.getContract();
		
		Contract cont = contractService.queryByKey(contract.getId());
		cont.setIsAllSet(contract.getIsAllSet());
		cont.setLendUserId(contract.getLendUserId());
		cont.setGetLoanWay(contract.getGetLoanWay());
		cont.setRemark(contract.getRemark());
		cont.setLawName(contract.getLawName());
		cont.setUpdateTime(nowTime);
		this.contractService.update(cont);
		
		if ("submit".equals(bean.getButtonType())) {
			// 通过
			if ("1".equals(decision)) {
				String nextUser = null;

				// 直投方式-推送线上
				if (Contract.LOAN_WAY_ZT.equals(contract.getGetLoanWay())) {
					decision = "推送线上";
					String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_N);
					if (StringUtils.isNotNullAndEmpty(prevUser)) {
						nextUser = prevUser;
					} else {
						nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_PUSH_STAFF,
								BusinessConstants.ORG_CODE, loanBase.getOrg());
					}

					if (StringUtils.isNotNullAndEmpty(nextUser)) {
						this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_N, nextUser, loginId,
								decision, remark);
						state1st = "N";
						state2nd = "1";
					} else {
						return new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), false,
								CommonConstant.PROCESS_N + "没有相关操作人，提交失败！");
					}

					// 发送邮件提醒
					String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
					if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
						this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_N, loanId, reciveEmail));
					}
				} else {
					// 线下和债权转让-出纳核帐
					decision = "通过";
					String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_H);
					if (StringUtils.isNotNullAndEmpty(prevUser)) {
						nextUser = prevUser;
					} else {
						nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_CASH_STAFF,
								BusinessConstants.ORG_CODE, loanBase.getOrg());
					}

					if (StringUtils.isNotNullAndEmpty(nextUser)) {
						this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_H, nextUser, loginId,
								decision, remark);
						state1st = "H";
						state2nd = "1";
						loanBase.setCashTime(nowTime);
					} else {
						return new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), false,
								CommonConstant.PROCESS_H + "没有相关操作人，提交失败！");
					}
					
					// 发送邮件提醒
					String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
					if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
						this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_H, loanId, reciveEmail));
					}
				}
			}
			
			// 不通过
			if ("0".equals(decision)) {
				decision = "不通过";

				String node = formBean.getNode();

				// 到录入申请
				if (CommonConstant.PROCESS_A.equals(node)) {
					this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_A, loanBase.getSalesman(),
							loginId, decision + ",退回到" + node, remark);
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
			}
			// 更新loanBase信息
			loanBase.setState1st(state1st);
			loanBase.setState2nd(state2nd);
			loanBase.setUpdateTime(nowTime);
			loanBase.setUpdateUid(loginId);
			this.loanBaseService.updateOnlyChanged(loanBase);
		}
		return new LoanBaseJsonMsg(loanBase.getId(), loanBase.getLoanId(), true, "成功");
	}
}
