package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseDecision;

/**
 * 
 * 信用贷款决策表DAO
 * <b>功能：</b>HouseDecisionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:02:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseDecisionDao {
		
	public void add(HouseDecision obj);	
	
	public void update(HouseDecision obj);
	
	public void updateOnlyChanged(HouseDecision obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseDecision> queryList(Map<String, Object> map);

	public HouseDecision queryByKey(Object obj);

}
