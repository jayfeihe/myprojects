package com.tera.audit.account.service;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.sys.model.JsonMsg;

public interface IFinanceReviewService {

	/**
	 * 财务复核流程操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	JsonMsg operateProcess(AuditFormBean formBean, String loginId, String orgId) throws Exception;

}