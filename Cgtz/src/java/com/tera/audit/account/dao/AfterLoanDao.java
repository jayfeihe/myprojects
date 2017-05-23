package com.tera.audit.account.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.account.model.form.AfterLoanQBean;

public interface AfterLoanDao {

	List<AfterLoanQBean> queryList(Map<String, Object> map);
}
