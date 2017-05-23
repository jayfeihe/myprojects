package com.tera.audit.retplan.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.retplan.model.RetPlan;

/**
 * 
 * 还款计划表DAO
 * <b>功能：</b>RetPlanDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 12:02:12<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RetPlanDao {
		
	public void add(RetPlan obj);	
	
	public void update(RetPlan obj);
	
	public void updateOnlyChanged(RetPlan obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RetPlan> queryList(Map<String, Object> map);

	public RetPlan queryByKey(Object obj);
	
	public List<RetPlan> queryLateByCon(Map<String, Object> map);

}
