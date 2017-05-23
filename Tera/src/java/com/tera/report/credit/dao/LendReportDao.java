package com.tera.report.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.credit.model.LendReportBean;

public interface LendReportDao {

	List<LendReportBean> queryList(Map<String, Object> map);

	public String queryLendAmountByOrgAndProduct(Map<String, Object> map);
}
