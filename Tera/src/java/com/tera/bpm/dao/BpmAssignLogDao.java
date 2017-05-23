package com.tera.bpm.dao;

import java.util.List;
import java.util.Map;

import com.tera.bpm.model.BpmAssignLog;

/**
 * 
 * 流程任务分配日志DAO
 * <b>功能：</b>BpmAssignLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-03 10:50:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface BpmAssignLogDao {
		
	public void add(BpmAssignLog obj);	
	
	public void update(BpmAssignLog obj);
	
	public void updateOnlyChanged(BpmAssignLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<BpmAssignLog> queryList(Map<String, Object> map);

	public BpmAssignLog queryByKey(Object obj);

}
