package com.tera.audit.law.service;

import com.tera.audit.law.model.form.LawFirstFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;

public interface ILawFirstAuditService {

	/**
	 * 法务初审流程操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	LoanBaseJsonMsg operateProcess(LawFirstFormBean bean, String loginId, String orgId) throws Exception;

}