package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseRepeatDetail;

/**
 * 
 * 信用贷款申请查重信息详细表DAO
 * <b>功能：</b>HouseRepeatDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-29 16:03:00<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseRepeatDetailDao {
		
	public void add(HouseRepeatDetail obj);	
	
	public void update(HouseRepeatDetail obj);
	
	public void updateOnlyChanged(HouseRepeatDetail obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseRepeatDetail> queryList(Map<String, Object> map);
	
	public List<HouseRepeatDetail> queryListGroupBy(Map<String, Object> map);

	public HouseRepeatDetail queryByKey(Object obj);
	
	public List<HouseRepeatDetail> repeatQueryByNoId(Map<String, Object> map);

	public List<HouseRepeatDetail> repeatQueryByContact(Map<String, Object> map);
	
	public List<HouseRepeatDetail> repeatQueryByKeyValue(Map<String, Object> map);

	/**
	 * 根据 APP_ID 清空 当前申请的 重复记录
	 * @param appId
	 */
	public void deleteByAppId(String appId);
	
	
}
