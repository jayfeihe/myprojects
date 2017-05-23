package com.tera.renhang.dao;

import java.util.List;
import java.util.Map;

import com.tera.renhang.model.RhQueryDetail;

/**
 * 
 * 信用贷款人行报告查询明细DAO
 * <b>功能：</b>RhQueryDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:07:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RhQueryDetailDao {
		
	public void add(RhQueryDetail obj);	
	
	public void update(RhQueryDetail obj);
	
	public void updateOnlyChanged(RhQueryDetail obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RhQueryDetail> queryList(Map<String, Object> map);

	public RhQueryDetail queryByKey(Object obj);

}
