package com.tera.report.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.model.LoanManBaseQBean;

public interface LoanManBaseDao {

	public List<LoanManBaseQBean> queryList(Map<String,Object> map);
}
