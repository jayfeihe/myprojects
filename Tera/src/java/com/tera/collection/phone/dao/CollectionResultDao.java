package com.tera.collection.phone.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.phone.model.CollectionResult;

/**
 * 
 * 催收人员绩效表DAO
 * <b>功能：</b>CollectionResultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:40:25<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionResultDao {
		
	public void add(CollectionResult obj);	
	
	public void update(CollectionResult obj);
	
	public void updateOnlyChanged(CollectionResult obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollectionResult> queryList(Map<String, Object> map);

	public CollectionResult queryByKey(Object obj);

}
