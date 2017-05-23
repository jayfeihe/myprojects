package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarSummary;

/**
 * 
 * 信用贷款申请影像摘要DAO
 * <b>功能：</b>CarSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarSummaryDao {
		
	public void add(CarSummary obj);	
	
	public void update(CarSummary obj);
	
	public void updateOnlyChanged(CarSummary obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarSummary> queryList(Map<String, Object> map);

	public CarSummary queryByKey(Object obj);

}
