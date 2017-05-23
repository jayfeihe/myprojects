package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseAntifraud;

/**
 * 
 * 信用贷款反欺诈表DAO
 * <b>功能：</b>HouseAntifraudDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:07:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseAntifraudDao {
		
	public void add(HouseAntifraud obj);	
	
	public void update(HouseAntifraud obj);
	
	public void updateOnlyChanged(HouseAntifraud obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseAntifraud> queryList(Map<String, Object> map);

	public HouseAntifraud queryByKey(Object obj);

	public HouseAntifraud queryLatestByAppId(Map<String, Object> map);
}
