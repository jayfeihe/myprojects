package com.tera.collection.outsourcing.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.outsourcing.model.CollectionOutsourcing;
/**
 * 
 * DAO 外包催收Dao
 * <b>功能：</b>CollectionOutsourcingDao<br>
 * <b>作者：</b>zhukun<br>
 * <b>日期：</b>2015-06-12 10:43:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionOutsourcingDao {
	
	public int queryCount(Map<String, Object> map);
	
	public List<CollectionOutsourcing> queryList(Map<String, Object> map);
	
	public CollectionOutsourcing queryById(Object object);
}
