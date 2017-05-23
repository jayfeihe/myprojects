package com.tera.feature.cllt.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.cllt.model.ClltLog;

/**
 * 
 * T_CLLT_LOGDAO
 * <b>功能：</b>ClltLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-19 21:20:36<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ClltLogDao {
		
	public void add(ClltLog obj);	
	
	public void update(ClltLog obj);
	
	public void updateOnlyChanged(ClltLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<ClltLog> queryList(Map<String, Object> map);

	public ClltLog queryByKey(Object obj);

}
