package com.tera.collection.phone.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.phone.model.CollectionRecord;

/**
 * 
 * 催收记录表DAO
 * <b>功能：</b>CollectionRecordDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:39:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionRecordDao {
		
	public void add(CollectionRecord obj);	
	
	public void update(CollectionRecord obj);
	
	public void updateOnlyChanged(CollectionRecord obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollectionRecord> queryList(Map<String, Object> map);

	public CollectionRecord queryByKey(Object obj);

}
