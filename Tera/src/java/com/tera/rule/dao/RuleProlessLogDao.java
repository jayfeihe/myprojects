package com.tera.rule.dao;

import java.util.List;
import java.util.Map;

import com.tera.rule.model.RuleProlessLog;

/**
 * 
 * 规则日志DAO
 * <b>功能：</b>RuleProlessLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-09-28 11:28:18<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RuleProlessLogDao {
		
	public void add(RuleProlessLog obj);	
	
	public void update(RuleProlessLog obj);
	
	public void updateOnlyChanged(RuleProlessLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RuleProlessLog> queryList(Map<String, Object> map);

	public RuleProlessLog queryByKey(Object obj);

}
