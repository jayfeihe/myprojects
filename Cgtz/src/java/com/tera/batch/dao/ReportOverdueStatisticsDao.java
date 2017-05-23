package com.tera.batch.dao;

import java.util.List;
import java.util.Map;

import com.tera.batch.model.ReportOverdueStatistics;

/**
 * 
 * 分公司逾期统计表DAO
 * <b>功能：</b>ReportOverdueStatisticsDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-15 10:21:53<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ReportOverdueStatisticsDao {
		
	public void add(ReportOverdueStatistics obj);	
	
	public void update(ReportOverdueStatistics obj);
	
	public void updateOnlyChanged(ReportOverdueStatistics obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<ReportOverdueStatistics> queryList(Map<String, Object> map);

	public ReportOverdueStatistics queryByKey(Object obj);

}
