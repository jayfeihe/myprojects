package com.tera.audit.account.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.account.model.form.OnLineLoanQBean;

public interface OnLineLoanDao {

	List<OnLineLoanQBean> queryList(Map<String, Object> map);
}
