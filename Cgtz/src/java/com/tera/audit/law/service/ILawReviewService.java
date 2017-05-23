package com.tera.audit.law.service;

import com.tera.audit.law.model.form.LawAuditFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;

public interface ILawReviewService {

	/**
	 * 法务复核流程操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	LoanBaseJsonMsg operateProcess(LawAuditFormBean bean, String loginId, String orgId) throws Exception;

}