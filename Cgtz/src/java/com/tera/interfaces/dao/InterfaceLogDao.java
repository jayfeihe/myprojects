package com.tera.interfaces.dao;

import java.util.List;
import java.util.Map;

import com.tera.interfaces.model.InterfaceLog;

/**
 * 
 * 接口对接,日志DAO
 * <b>功能：</b>InterfaceLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-17 10:07:47<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface InterfaceLogDao {
		
	public void add(InterfaceLog obj);	
	
	public void update(InterfaceLog obj);
	
	public void updateOnlyChanged(InterfaceLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<InterfaceLog> queryList(Map<String, Object> map);

	public InterfaceLog queryByKey(Object obj);

}
