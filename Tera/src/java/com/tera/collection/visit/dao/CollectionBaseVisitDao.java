package com.tera.collection.visit.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.phone.model.CollectionBase;

/**
 * 
 * 催收信息基本表DAO
 * <b>功能：</b>CollectionBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionBaseVisitDao {
		
	
	public CollectionBase queryByKey(Object obj);
	public void updateVisitCollectionMap(Map<String,Object> map);
	public int queryCount(Map<String, Object> map);
	public List<CollectionBase> queryList(Map<String, Object> map);
	 
}
