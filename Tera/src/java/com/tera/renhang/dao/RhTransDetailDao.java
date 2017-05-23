package com.tera.renhang.dao;

import java.util.List;
import java.util.Map;

import com.tera.renhang.model.RhTransDetail;

/**
 * 
 * 信用贷款人行报告交易明细DAO
 * <b>功能：</b>RhTransDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:06:05<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RhTransDetailDao {
		
	public void add(RhTransDetail obj);	
	
	public void update(RhTransDetail obj);
	
	public void updateOnlyChanged(RhTransDetail obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RhTransDetail> queryList(Map<String, Object> map);

	public RhTransDetail queryByKey(Object obj);

}
