package com.tera.report.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.credit.model.AuditReportBean;

public interface AuditReportDao {

	public Integer queryAmountWithDecision(Map<String, Object> map);

	public List<AuditReportBean> queryList(Map<String, Object> map);
}
