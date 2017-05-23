package com.tera.customer.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-30 16:22:42<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CustomerDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);

	public T queryByKey(Object t);
	
	public int queryCustomerCount(Map<String, Object> map);
	
	public List<T> queryCustomerList(Map<String, Object> map);

}
