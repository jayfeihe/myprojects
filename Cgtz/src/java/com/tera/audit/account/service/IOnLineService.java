package com.tera.audit.account.service;

import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.model.form.AccountFormBean;
import com.tera.audit.account.model.form.OnLineLoanQBean;
import com.tera.sys.model.JsonMsg;

public interface IOnLineService {

	/**
	 * 线上放款流程操作
	 * @param bean
	 * @param loginId
	 * @return 
	 * @throws Exception
	 */
	JsonMsg operateProcess(AccountFormBean formBean, String loginId, String orgId) throws Exception;

	PageList<OnLineLoanQBean> queryPageList(Map<String, Object> params);

}