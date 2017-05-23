package com.tera.batch.errorLog.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>BatchErrorLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-07-11 16:12:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface BatchErrorLogDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);

	public T queryByKey(Object t);

}
