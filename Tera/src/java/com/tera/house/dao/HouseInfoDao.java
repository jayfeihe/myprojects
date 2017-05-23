package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseInfo;

/**
 * 
 * 房产信息表DAO
 * <b>功能：</b>HouseInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-20 17:01:08<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseInfoDao {
		
	public void add(HouseInfo obj);	
	
	public void update(HouseInfo obj);
	
	public void updateOnlyChanged(HouseInfo obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseInfo> queryList(Map<String, Object> map);

	public HouseInfo queryByKey(Object obj);

}
