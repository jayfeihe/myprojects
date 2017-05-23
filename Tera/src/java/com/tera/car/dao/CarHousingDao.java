package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarHousing;

/**
 * 
 * 信用贷款申请房产信息DAO
 * <b>功能：</b>CarHousingDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:32<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarHousingDao {
		
	public void add(CarHousing obj);	
	
	public void update(CarHousing obj);
	
	public void updateOnlyChanged(CarHousing obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarHousing> queryList(Map<String, Object> map);

	public CarHousing queryByKey(Object obj);

}
