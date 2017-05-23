package com.tera.feature.cllt.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.cllt.model.ClltDistr;

/**
 * 
 * 催收分配表DAO
 * <b>功能：</b>ClltDistrDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-20 09:25:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ClltDistrDao {
		
	public void add(ClltDistr obj);	
	
	public void update(ClltDistr obj);
	
	public void updateOnlyChanged(ClltDistr obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<ClltDistr> queryList(Map<String, Object> map);

	public ClltDistr queryByKey(Object obj);
	
	public ClltDistr queryByConId(Object obj);
	
	public void updateByConId(Object obj);

}
