package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.HouseInterview;

/**
 * 
 * 信用贷款面审调查表DAO
 * <b>功能：</b>HouseInterviewDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:54:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseInterviewDao {
		
	public void add(HouseInterview obj);	
	
	public void update(HouseInterview obj);
	
	public void updateOnlyChanged(HouseInterview obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseInterview> queryList(Map<String, Object> map);

	public HouseInterview queryByKey(Object obj);

}
