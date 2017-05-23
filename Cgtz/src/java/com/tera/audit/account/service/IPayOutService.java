package com.tera.audit.account.service;

import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.model.form.PayOutQBean;
import com.tera.sys.model.JsonMsg;

public interface IPayOutService {

	/**
	 * 垫付
	 * @param bean
	 * @param loginId
	 * @return
	 */
	JsonMsg payOut(AccountFormBean bean, String loginId) throws Exception;

	PageList<PayOutQBean> queryPageList(Map<String, Object> params);
}