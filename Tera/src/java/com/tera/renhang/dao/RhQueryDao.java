package com.tera.renhang.dao;

import java.util.List;
import java.util.Map;

import com.tera.renhang.model.RhQuery;

/**
 * 
 * 信用贷款人行报告查询汇总DAO
 * <b>功能：</b>RhQueryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:07:22<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RhQueryDao {
		
	public void add(RhQuery obj);	
	
	public void update(RhQuery obj);
	
	public void updateOnlyChanged(RhQuery obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RhQuery> queryList(Map<String, Object> map);

	public RhQuery queryByKey(Object obj);

}
