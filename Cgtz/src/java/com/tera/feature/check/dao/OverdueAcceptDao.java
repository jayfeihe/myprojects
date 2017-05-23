package com.tera.feature.check.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.check.model.OverdueAccept;

/**
 * 
 * 逾期受理登记表DAO
 * <b>功能：</b>OverdueAcceptDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 19:46:37<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface OverdueAcceptDao {
		
	public void add(OverdueAccept obj);	
	
	public void update(OverdueAccept obj);
	
	public void updateOnlyChanged(OverdueAccept obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<OverdueAccept> queryList(Map<String, Object> map);

	public OverdueAccept queryByKey(Object obj);

}
