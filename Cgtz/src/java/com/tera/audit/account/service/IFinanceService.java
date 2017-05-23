package com.tera.audit.account.service;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.sys.model.JsonMsg;

public interface IFinanceService {

	/**
	 * 财务核帐流程操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	JsonMsg operateProcess(AuditFormBean formBean, String loginId, String orgId) throws Exception;

}