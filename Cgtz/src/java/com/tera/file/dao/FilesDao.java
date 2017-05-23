package com.tera.file.dao;

import java.util.List;
import java.util.Map;

import com.tera.file.model.Files;

/**
 * 
 * 文件存储DAO
 * <b>功能：</b>FilesDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-08 21:21:37<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface FilesDao {
		
	public void add(Files obj);	
	
	public void update(Files obj);
	
	public void updateOnlyChanged(Files obj);
		
	public void delete(Object obj);
	
	public void deleteByMap(Map<String, Object> map);

	public int queryCount(Map<String, Object> map);
	
	public List<Files> queryList(Map<String, Object> map);

	public Files queryByKey(Object obj);

	public List<Files> queryCategoryCount(Map<String, Object> map);
}
