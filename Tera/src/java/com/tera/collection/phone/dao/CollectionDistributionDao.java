package com.tera.collection.phone.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.phone.model.CollectionDistribution;
import com.tera.collection.phone.model.CollectionDistributionCount;

/**
 * 
 * 催收分配表DAO
 * <b>功能：</b>CollectionDistributionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:37:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionDistributionDao {
		
	public void add(CollectionDistribution obj);	
	
	public void update(CollectionDistribution obj);
	
	public void updateOnlyChanged(CollectionDistribution obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollectionDistribution> queryList(Map<String, Object> map);

	public CollectionDistribution queryByKey(Object obj);
	
	public List<CollectionDistributionCount> queryPartDetailList(Map<String, Object> map);//add by wangyongliang 20150713 

	public int queryPartDetailCount(Map<String, Object> map);//add by wangyongliang 20150713 
	

}
