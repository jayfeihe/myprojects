package com.tera.audit.law.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.common.service.ICommonHandlerService;
import com.tera.audit.law.model.form.LawAdviceFormBean;
import com.tera.audit.law.service.ILawAdviceService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.mail.service.MailService;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.util.StringUtils;

/**
 * 法律意见
 * @author QYANZE
 *
 */
@Service("lawAdviceService")
public class LawAdviceServiceImpl implements ILawAdviceService {

	@Autowired
	private ICommonHandlerService commonHandlerService;
	@Autowired
	private ILoanBaseService loanBaseService;
	@Autowired
	private TaskExecutor taskExecutor;

	/** (non-Javadoc)
	 * @see com.tera.audit.law.service.impl.ILawAdviceService#operateProcess(com.tera.audit.law.model.form.LawAdviceFormBean, java.lang.String, java.lang.String)
	 */
	@Override
	public JsonMsg operateProcess(LawAdviceFormBean formBean, String loginId) throws Exception {
		
		String loanId = formBean.getLoanId();
		String remark = formBean.getRemark();
		
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		
		String decision = "推送线上";
		String nextUser = this.commonHandlerService.getNextUser(CommonConstant.ROLE_PUSH_STAFF, BusinessConstants.ORG_CODE,loanBase.getOrg());
		
		// 债权转让流程-推送线上
		if (StringUtils.isNotNullAndEmpty(nextUser)) {
			this.commonHandlerService.workFlow(loanId, CommonConstant.PROCESS_N, nextUser, loginId, decision,remark);
		} else {
			return new JsonMsg(false, CommonConstant.PROCESS_N + "没有相关操作人，提交失败！");
		}
		
		// 发送邮件提醒
		String reciveEmail = this.commonHandlerService.getEmailByLoginId(nextUser);
		if (StringUtils.isNotNullAndEmpty(reciveEmail)) {
			this.taskExecutor.execute(new MailService(CommonConstant.PROCESS_N, loanId, reciveEmail));
		}
		return new JsonMsg(true, "操作成功");
	}
}
