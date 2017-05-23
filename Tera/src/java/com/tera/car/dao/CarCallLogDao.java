package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarCallLog;

/**
 * 
 * 信用贷款面审调查通话记录DAO
 * <b>功能：</b>CarCallLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:55:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarCallLogDao {
		
	public void add(CarCallLog obj);	
	
	public void update(CarCallLog obj);
	
	public void updateOnlyChanged(CarCallLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarCallLog> queryList(Map<String, Object> map);

	public CarCallLog queryByKey(Object obj);

}
