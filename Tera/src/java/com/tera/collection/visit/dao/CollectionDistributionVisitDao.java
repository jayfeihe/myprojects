package com.tera.collection.visit.dao;

import java.util.List;
import java.util.Map;

 
import com.tera.collection.phone.model.CollectionDistributionCount;
import com.tera.collection.visit.model.VisitDistribution;

/**
 * 
 * 催收分配表DAO
 * <b>功能：</b>CollectionDistributionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:37:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionDistributionVisitDao {
		
	 

	public void addVisitDistributionMap(Map<String, Object> map);

	public int queryCount(Map<String, Object> map);//催收列表信息数量查询方法

	public List<VisitDistribution> queryList(Map<String, Object> map);//催收列表查询方法

	public VisitDistribution queryByKey(Object id);

	public void updateVisitCollectionMap(Map<String, Object> map);

	public void updateVisitDistributionMap(Map<String, Object> map);

	public int queryTaskNumCount(Map<String, Object> map);//催收列表信息数量查询方法
	public List<CollectionDistributionCount> queryTaskNumList(Map<String, Object> queryMap);//分单统计查询方法
	
}
