package com.tera.collection.phone.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.model.CollectionBaseInfo;

/**
 * 
 * 催收信息基本表DAO
 * <b>功能：</b>CollectionBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionBaseDao {
		
	public void add(CollectionBase obj);	
	
	public void update(CollectionBase obj);
	
	public void updateOnlyChanged(CollectionBase obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollectionBase> queryList(Map<String, Object> map);
	
	public List<CollectionBaseInfo> queryPartList(Map<String, Object> map);//电催分单查询 add by wangyongliang 20150612
	
	public int queryPartCount(Map<String, Object> map);//电催分单查询数量 add by wangyongliang 20150611

	public CollectionBase queryByKey(Object obj);
	
	public List<CollectionBaseInfo> queryActionList(Map<String, Object> map);//电催列表查询 add by wangyongliang 20150611
	
	public int queryActionCount(Map<String, Object> map);//电催分单查询数量 add by wangyongliang 20150611
	
	public CollectionBaseInfo queryInfo(Map<String, Object> map);//电催基本信息 add by wangyongliang 20150611
	
	/**月还款额倒序查询**/
	public List<CollectionBase> queryListOrderByAmount(Map<String, Object> map);
	
	
	public List<CollectionBase> queryListOfWay(Map<String, Object> map);
	
}
