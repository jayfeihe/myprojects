package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseSummary;

/**
 * 
 * 信用贷款申请影像摘要DAO
 * <b>功能：</b>HouseSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseSummaryDao {
		
	public void add(HouseSummary obj);	
	
	public void update(HouseSummary obj);
	
	public void updateOnlyChanged(HouseSummary obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseSummary> queryList(Map<String, Object> map);

	public HouseSummary queryByKey(Object obj);

}
