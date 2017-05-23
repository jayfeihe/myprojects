package com.tera.audit.account.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.account.model.form.PayOutQBean;

public interface PayOutDao {

	List<PayOutQBean> queryList(Map<String, Object> map); 
}
