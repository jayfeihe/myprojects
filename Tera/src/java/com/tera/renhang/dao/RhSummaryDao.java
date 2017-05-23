package com.tera.renhang.dao;

import java.util.List;
import java.util.Map;

import com.tera.renhang.model.RhSummary;

/**
 * 
 * 信用贷款人行报告信息概要DAO
 * <b>功能：</b>RhSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:05:39<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RhSummaryDao {
		
	public void add(RhSummary obj);	
	
	public void update(RhSummary obj);
	
	public void updateOnlyChanged(RhSummary obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RhSummary> queryList(Map<String, Object> map);

	public RhSummary queryByKey(Object obj);

}
