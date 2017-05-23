package com.tera.renhang.dao;

import java.util.List;
import java.util.Map;

import com.tera.renhang.model.RhReport;

/**
 * 
 * 信用贷款人行报告DAO
 * <b>功能：</b>RhReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:05:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RhReportDao {
		
	public void add(RhReport obj);	
	
	public void update(RhReport obj);
	
	public void updateOnlyChanged(RhReport obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RhReport> queryList(Map<String, Object> map);

	public RhReport queryByKey(Object obj);

}
