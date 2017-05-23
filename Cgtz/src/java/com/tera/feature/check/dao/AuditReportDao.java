package com.tera.feature.check.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.check.model.AuditReport;

/**
 * 
 * 逾期报告DAO
 * <b>功能：</b>OverdueReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface AuditReportDao {
		
	public void add(AuditReport obj);	
	
	public void update(AuditReport obj);
	
	public void updateOnlyChanged(AuditReport obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<AuditReport> queryList(Map<String, Object> map);

	public AuditReport queryByKey(Object obj);
	
	public AuditReport printByKey(Object obj);

}
