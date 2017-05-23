package com.tera.renhang.dao;

import java.util.List;
import java.util.Map;

import com.tera.renhang.model.RhTransDefault;

/**
 * 
 * 信用贷款人行报告交易逾期透支记录DAO
 * <b>功能：</b>RhTransDefaultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:06:33<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RhTransDefaultDao {
		
	public void add(RhTransDefault obj);	
	
	public void update(RhTransDefault obj);
	
	public void updateOnlyChanged(RhTransDefault obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RhTransDefault> queryList(Map<String, Object> map);

	public RhTransDefault queryByKey(Object obj);

}
