package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseHousing;

/**
 * 
 * 信用贷款申请房产信息DAO
 * <b>功能：</b>HouseHousingDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:32<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseHousingDao {
		
	public void add(HouseHousing obj);	
	
	public void update(HouseHousing obj);
	
	public void updateOnlyChanged(HouseHousing obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseHousing> queryList(Map<String, Object> map);

	public HouseHousing queryByKey(Object obj);

}
