package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarInfo;

/**
 * 
 * 车辆信息表DAO
 * <b>功能：</b>CarInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-11 17:13:38<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarInfoDao {
		
	public void add(CarInfo obj);	
	
	public void update(CarInfo obj);
	
	public void updateOnlyChanged(CarInfo obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarInfo> queryList(Map<String, Object> map);

	public CarInfo queryByKey(Object obj);

}
