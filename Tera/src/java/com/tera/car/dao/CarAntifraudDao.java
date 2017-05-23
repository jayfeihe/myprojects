package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarAntifraud;

/**
 * 
 * 信用贷款反欺诈表DAO
 * <b>功能：</b>CarAntifraudDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:07:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarAntifraudDao {
		
	public void add(CarAntifraud obj);	
	
	public void update(CarAntifraud obj);
	
	public void updateOnlyChanged(CarAntifraud obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarAntifraud> queryList(Map<String, Object> map);

	public CarAntifraud queryByKey(Object obj);

	public CarAntifraud queryLatestByAppId(Map<String, Object> map);
}
