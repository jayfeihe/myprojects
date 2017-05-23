package com.tera.audit.account.service;

import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.sys.model.JsonMsg;

public interface IOffLineService {

	/**
	 * 线下放款流程操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	JsonMsg operateProcess(AccountFormBean formBean, String loginId, String orgId) throws Exception;

}