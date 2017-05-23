package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.UserExt;

/**
 * 
 * 用户信息扩展表DAO
 * <b>功能：</b>UserExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 16:47:09<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface UserExtDao {
		
	public void add(UserExt obj);	
	
	public void update(UserExt obj);
	
	public void updateOnlyChanged(UserExt obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<UserExt> queryList(Map<String, Object> map);

	public List<UserExt> queryByKey(Object obj);

}
