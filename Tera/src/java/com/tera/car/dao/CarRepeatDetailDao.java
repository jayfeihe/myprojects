package com.tera.car.dao;

import java.util.List;
import java.util.Map;

import com.tera.car.model.CarRepeatDetail;

/**
 * 
 * 信用贷款申请查重信息详细表DAO
 * <b>功能：</b>CarRepeatDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-29 16:03:00<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CarRepeatDetailDao {
		
	public void add(CarRepeatDetail obj);	
	
	public void update(CarRepeatDetail obj);
	
	public void updateOnlyChanged(CarRepeatDetail obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CarRepeatDetail> queryList(Map<String, Object> map);
	
	public List<CarRepeatDetail> queryListGroupBy(Map<String, Object> map);

	public CarRepeatDetail queryByKey(Object obj);
	
	public List<CarRepeatDetail> repeatQueryByNoId(Map<String, Object> map);

	public List<CarRepeatDetail> repeatQueryByContact(Map<String, Object> map);
	
	public List<CarRepeatDetail> repeatQueryByKeyValue(Map<String, Object> map);

	/**
	 * 根据 APP_ID 清空 当前申请的 重复记录
	 * @param appId
	 */
	public void deleteByAppId(String appId);
	
	
}
