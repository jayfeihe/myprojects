package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarInterview;

/**
 * 
 * 信用贷款面审调查表DAO
 * <b>功能：</b>CarInterviewDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:54:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarInterviewDao {
		
	public void add(CarInterview obj);	
	
	public void update(CarInterview obj);
	
	public void updateOnlyChanged(CarInterview obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarInterview> queryList(Map<String, Object> map);

	public CarInterview queryByKey(Object obj);

}
