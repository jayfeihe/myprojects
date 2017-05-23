package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.WageFlow;

/**
 * 
 * 信用贷款申请人工资流水DAO
 * <b>功能：</b>WageFlowDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-06 14:29:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseWageFlowDao {
		
	public void add(WageFlow obj);	
	
	public void update(WageFlow obj);
	
	public void updateOnlyChanged(WageFlow obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<WageFlow> queryList(Map<String, Object> map);

	public WageFlow queryByKey(Object obj);

}
