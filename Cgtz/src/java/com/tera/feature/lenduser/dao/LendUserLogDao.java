package com.tera.feature.lenduser.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.lenduser.model.LendUserLog;

/**
 * 
 * 出借人资金变动记录DAO
 * <b>功能：</b>LendUserLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-10 22:42:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LendUserLogDao {
		
	public void add(LendUserLog obj);	
	
	public void update(LendUserLog obj);
	
	public void updateOnlyChanged(LendUserLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<LendUserLog> queryList(Map<String, Object> map);

	public LendUserLog queryByKey(Object obj);

}
