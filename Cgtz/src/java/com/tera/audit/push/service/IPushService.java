package com.tera.audit.push.service;

import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.sys.model.JsonMsg;

public interface IPushService {

	/**
	 * 推送处理
	 * @param bean
	 * @param loginId
	 * @return
	 * @throws Exception 
	 */
	JsonMsg operateProcess(AccountFormBean bean, String loginId) throws Exception;

}