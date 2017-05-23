package com.tera.blacklist.dao;

import java.util.List;
import java.util.Map;

import com.tera.blacklist.model.Blacklist;

/**
 * 
 * 黑名单表DAO
 * <b>功能：</b>BlacklistDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-10-08 10:39:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface BlacklistDao {
		
	public void add(Blacklist obj);	
	
	public void update(Blacklist obj);
	
	public void updateOnlyChanged(Blacklist obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Blacklist> queryList(Map<String, Object> map);

	public Blacklist queryByKey(Object obj);

}
