package com.tera.feature.overdue.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.overdue.model.SalesCheckLog;

/**
 * 
 * 业务员，稽查人员催收跟进记录表DAO
 * <b>功能：</b>SalesCheckLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 14:02:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface SalesCheckLogDao {
		
	public void add(SalesCheckLog obj);	
	
	public void update(SalesCheckLog obj);
	
	public void updateOnlyChanged(SalesCheckLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<SalesCheckLog> queryList(Map<String, Object> map);

	public SalesCheckLog queryByKey(Object obj);

}
