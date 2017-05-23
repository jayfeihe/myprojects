package com.tera.feature.overdue.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.overdue.model.OverdueReport;

/**
 * 
 * 逾期报告DAO
 * <b>功能：</b>OverdueReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface OverdueReportDao {
		
	public void add(OverdueReport obj);	
	
	public void update(OverdueReport obj);
	
	public void updateOnlyChanged(OverdueReport obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<OverdueReport> queryList(Map<String, Object> map);

	public OverdueReport queryByKey(Object obj);
	
	public OverdueReport queryByNum(int num,String contractId);

}
