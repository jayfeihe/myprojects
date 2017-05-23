package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarExt;

/**
 * 
 * 信用贷款申请信息扩展表DAO
 * <b>功能：</b>CarExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:25:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarExtDao {
		
	public void add(CarExt obj);	
	
	public void update(CarExt obj);
	
	public void updateOnlyChanged(CarExt obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarExt> queryList(Map<String, Object> map);

	public CarExt queryByKey(Object obj);

}
