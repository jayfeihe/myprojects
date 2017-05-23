package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarDecision;

/**
 * 
 * 信用贷款决策表DAO
 * <b>功能：</b>CarDecisionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:02:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarDecisionDao {
		
	public void add(CarDecision obj);	
	
	public void update(CarDecision obj);
	
	public void updateOnlyChanged(CarDecision obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarDecision> queryList(Map<String, Object> map);

	public CarDecision queryByKey(Object obj);

}
