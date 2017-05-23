package com.tera.feature.check.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.check.model.SalesCheck;

/**
 * 
 * 业务员，稽查人员催收跟进记录表DAO
 * <b>功能：</b>SalesCheckLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 14:02:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface SalesCheckDao {
		
	public void add(SalesCheck obj);	
	
	public void update(SalesCheck obj);
	
	public void updateOnlyChanged(SalesCheck obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<SalesCheck> queryList(Map<String, Object> map);

	public SalesCheck queryByKey(Object obj);

}
