package com.tera.audit.risk.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.loan.model.Collateral;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ICollateralService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.risk.service.IRiskFirstAuditService;
import com.tera.mail.service.MailService;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.util.StringUtils;


/** 风控初审Service
 * @author QYANZE
 *
 */
@Service("riskFirstAuditService")
public class RiskFirstAuditServiceImpl implements IRiskFirstAuditService {

	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private ICommonHandlerService commonHandlerService;
	@Autowired
	private ICollateralService collateralService;
	@Autowired
	private TaskExecutor taskExecutor;

	/* (non-Javadoc)
	 * @see com.tera.audit.risk.service.IRiskFirstAuditService#operateProcess(com.tera.audit.branch.model.AuditFormBean, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public JsonMsg operateProcess(AuditFormBean bean, String loginId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		// 判断核价是否完成
		boolean isFinish = isPriceFinished(bean.getLoanId());
		
		if (!isFinish) {
			return new JsonMsg(false,"核价未完成，提交失败！");
		}
		
		// 风控初审
		String decision = bean.getDecision();
		String loanId = bean.getLoanId();
		String remark = bean.getRemark();
		
		
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		String bizKey = loanBase.getLoanId();
		String state1st = null,state2nd = null;
		
		// 通过-风控总监审批
		if ("1".equals(decision)) {
			String nextUser = null;
			
			decision = "通过";
			
			String prevUser = this.commonHandlerService.getPrevUser(loanId, CommonConstant.PROCESS_F);
			if (StringUtils.isNotNullAndEmpty(prevUser)) {
				nextUser = prevUser;
			} else {
				nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_RISK_DIR, BusinessConstants.ORG_CODE,loanBase.getOrg());
			}
			
			if (StringUtils.isNotNullAndEmpty(nextUser)) {
				this.commonHandlerService.workFlow(bizKey, CommonConstant.PROCESS_F, nextUser, loginId, decision,remark);
				state1st = "F";
				state2nd = "1";
				loanBase.setRiskCheckTime(nowTime);
			} else {
				return new JsonMsg(false, CommonConstant.PROCESS_F + "没有相关操作人，提交失败！");
			}
			
			// 发送邮件提醒
			String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
			if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
				this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_F, loanId, reciveEmail));
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
			
		} 
		
		// 更新loanBase信息
		loanBase.setState1st(state1st);
		loanBase.setState2nd(state2nd);
		loanBase.setUpdateTime(nowTime);
		loanBase.setUpdateUid(loginId);
		this.loanBaseService.updateOnlyChanged(loanBase);
		
		return new JsonMsg(true,"成功");
	}

	/**
	 * 判断核价是否已完成
	 * @param loanId
	 * @return
	 * @throws Exception
	 */
	private boolean isPriceFinished(String loanId) throws Exception {
		boolean isFinished = true;
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("loanId", loanId);
		List<Collateral> collateralList = this.collateralService.queryList(queryMap);
		if (collateralList != null && collateralList.size() > 0) {
			for (Collateral col : collateralList) {
				if ("0".equals(col.getAuditPriceState())) {
					isFinished = false;
					break;
				}
			}
		}
		return isFinished;
	}
}
