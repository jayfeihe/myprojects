package com.tera.collection.judicial.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.judicial.model.CollectionApplication;


/**
 * 
 * DAO
 * <b>功能：</b>CollectionApplicationDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-12 10:43:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionApplicationDao {
		
	public void add(CollectionApplication obj);	
	
	public void update(CollectionApplication obj);
	
	public void updateOnlyChanged(CollectionApplication obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollectionApplication> queryList(Map<String, Object> map);

	public CollectionApplication queryByKey(Object obj);

}
