package com.tera.customer.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-29 18:35:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CustomerExtDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);

	public void delete(T t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);

	public T queryByKey(T t);

}
