package com.tera.audit.query.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.query.model.IntegratedQueryBean;

public interface IntegratedQueryDao {

	public List<IntegratedQueryBean> integratedQueryList(Map<String, Object> map);
}
