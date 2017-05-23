package com.tera.audit.risk.service;

import com.tera.audit.branch.model.AuditFormBean;
import com.tera.sys.model.JsonMsg;

public interface IRiskReviewService {

	/**
	 * 风控复核流程操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	JsonMsg operateProcess(AuditFormBean bean, String loginId) throws Exception;

}