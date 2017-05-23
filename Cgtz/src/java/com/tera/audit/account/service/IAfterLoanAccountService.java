package com.tera.audit.account.service;

import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.model.form.AccountJsonMsg;
import com.tera.audit.account.model.form.AfterLoanQBean;

public interface IAfterLoanAccountService {

	/**
	 * 贷后收款
	 * @param bean
	 * @param loginId
	 * @return
	 */
	AccountJsonMsg collected(AccountFormBean bean, String loginId) throws Exception;
	
	PageList<AfterLoanQBean> queryPageList(Map<String, Object> params);
}