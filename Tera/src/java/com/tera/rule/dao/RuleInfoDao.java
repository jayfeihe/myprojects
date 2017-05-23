package com.tera.rule.dao;

import java.util.List;
import java.util.Map;

import com.tera.rule.model.RuleInfo;

/**
 * 
 * 规则执行日志DAO
 * <b>功能：</b>RuleInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-09-28 15:01:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RuleInfoDao {
		
	public void add(RuleInfo obj);	
	
	public void update(RuleInfo obj);
	
	public void updateOnlyChanged(RuleInfo obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RuleInfo> queryList(Map<String, Object> map);

	public RuleInfo queryByKey(Object obj);

}
