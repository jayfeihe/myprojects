package com.tera.product.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>TrustProductDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-08 00:48:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface TrustProductDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);
	
	//杨长收添加
	public int queryTrustProCount(Map<String, Object> map);
	
	//杨长收添加
	public List<T> queryTrustProList(Map<String, Object> map);

	public T queryByKey(Object t);

}
