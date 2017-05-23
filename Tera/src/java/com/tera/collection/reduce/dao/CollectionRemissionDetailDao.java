package com.tera.collection.reduce.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.reduce.model.CollectionRemissionDetail;

/**
 * 
 * 减免明细记录表DAO
 * <b>功能：</b>CollectionRemissionDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:49:16<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionRemissionDetailDao {
		
	public void add(CollectionRemissionDetail obj);	
	
	public void update(CollectionRemissionDetail obj);
	
	public void updateOnlyChanged(CollectionRemissionDetail obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollectionRemissionDetail> queryList(Map<String, Object> map);

	public CollectionRemissionDetail queryByKey(Object obj);

}
