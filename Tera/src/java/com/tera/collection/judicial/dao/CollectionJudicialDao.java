package com.tera.collection.judicial.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.judicial.model.CollectionJudicial;

/**
 * 
 * DAO 司法Dao
 * <b>功能：</b>CollectionJudicialDao<br>
 * <b>作者：</b>zhukun<br>
 * <b>日期：</b>2015-06-12 10:43:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionJudicialDao {
	
	public int queryCount(Map<String, Object> map);
	
	public List<CollectionJudicial> queryList(Map<String, Object> map);
	
	public CollectionJudicial queryById(Object object);
	/**
	 * 查询 已处理单证数
	 * @param map
	 * @return
	 */
	public int queryDoneCount(Map<String, Object> map);
	/**
	 * 查询 已处理列表
	 * @param map
	 * @return
	 */
	public List<CollectionJudicial> queryDoneList(Map<String, Object> map);
}
