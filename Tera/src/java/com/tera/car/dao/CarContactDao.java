package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarContact;
import com.tera.car.model.CarContactHistory;

/**
 * 
 * 信用借款申请联系人表DAO
 * <b>功能：</b>CarContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:22:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarContactDao {
		
	public void add(CarContact obj);	
	
	public void update(CarContact obj);
	
	public void updateOnlyChanged(CarContact obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarContact> queryList(Map<String, Object> map);

	public List<CarContact> queryListOrderByRelation(Map<String, Object> map);
	
	public List<CarContactHistory> getRelationList(Object map); 

	public CarContact queryByKey(Object obj);
}
