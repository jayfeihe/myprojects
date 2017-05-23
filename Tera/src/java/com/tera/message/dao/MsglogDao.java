package com.tera.message.dao;

import java.util.List;
import java.util.Map;

import com.tera.message.model.Msglog;

/**
 * 
 * 短信日志表DAO
 * <b>功能：</b>MsglogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-07-01 16:57:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface MsglogDao {
		
	public void add(Msglog obj);	
	
	public void update(Msglog obj);
	
	public void updateOnlyChanged(Msglog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Msglog> queryList(Map<String, Object> map);

	public Msglog queryByKey(Object obj);
}
