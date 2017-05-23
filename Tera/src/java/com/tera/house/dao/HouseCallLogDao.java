package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseCallLog;

/**
 * 
 * 信用贷款面审调查通话记录DAO
 * <b>功能：</b>HouseCallLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:55:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseCallLogDao {
		
	public void add(HouseCallLog obj);	
	
	public void update(HouseCallLog obj);
	
	public void updateOnlyChanged(HouseCallLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseCallLog> queryList(Map<String, Object> map);

	public HouseCallLog queryByKey(Object obj);

}
