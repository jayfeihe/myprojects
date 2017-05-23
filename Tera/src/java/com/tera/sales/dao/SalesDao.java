package com.tera.sales.dao;

import java.util.List;
import java.util.Map;

import com.tera.sales.model.Sales;

/**
 * 
 * <br>
 * <b>功能：</b>SalesDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-08-19 16:13:28<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface SalesDao {
		
	public void add(Sales obj);	
	
	public void update(Sales obj);
	
	public void updateOnlyChanged(Sales obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Sales> queryList(Map<String, Object> map);

	public Sales queryByKey(Object obj);

	public List<Sales> queryListSelect(Map<String, Object> map);
	
	public List<Sales> queryExcludeSelf(Map<String, Object> map);
}
