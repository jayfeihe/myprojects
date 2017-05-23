package com.tera.feature.lenduser.dao;


import java.util.List;
import java.util.Map;

import com.tera.feature.lenduser.model.LendUser;

/**
 * 
 * 出借人基本信息维护DAO
 * <b>功能：</b>LendUserDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-12-29 14:29:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LendUserDao {
		
	public void add(LendUser obj);	
	
	public void update(LendUser obj);
	
	public void updateOnlyChanged(LendUser obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<LendUser> queryList(Map<String, Object> map);

	public LendUser queryByKey(Object obj);

}
