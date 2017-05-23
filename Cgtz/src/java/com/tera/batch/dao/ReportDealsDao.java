package com.tera.batch.dao;

import java.util.List;
import java.util.Map;

import com.tera.batch.model.ReportDeal;

/**
 * 
 * 每月分公司成交量统计DAO
 * <b>功能：</b>ReportDealDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-15 10:21:34<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ReportDealsDao {
		
	public void add(ReportDeal obj);	
	
	public void update(ReportDeal obj);
	
	public void updateOnlyChanged(ReportDeal obj);
		
	public void delete(Object obj);
	
	public void deleteByMon(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<ReportDeal> queryList(Map<String, Object> map);

	public ReportDeal queryByKey(Object obj);
	
	public List<ReportDeal> queryMonInfo(Map<String, Object> map);

}
