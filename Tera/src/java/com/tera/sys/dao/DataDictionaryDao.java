package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.DataDictionary;

/**
 * 
 * <br>
 * <b>功能：</b>DataDictionaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 16:19:12<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface DataDictionaryDao<T> {	
		
	public void add(DataDictionary t);	
	
	public void update(DataDictionary t);
	
	public void updateOnlyChanged(DataDictionary t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<DataDictionary> queryList(Map<String, Object> map);

	public DataDictionary queryByKey(Object t);

	public List<String> queryKeyNamesAndParentKeys();
}
