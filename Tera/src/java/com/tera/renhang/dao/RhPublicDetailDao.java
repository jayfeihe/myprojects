package com.tera.renhang.dao;

import java.util.List;
import java.util.Map;

import com.tera.renhang.model.RhPublicDetail;

/**
 * 
 * 信用贷款人行报告公共信息明细DAO
 * <b>功能：</b>RhPublicDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:07:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RhPublicDetailDao {
		
	public void add(RhPublicDetail obj);	
	
	public void update(RhPublicDetail obj);
	
	public void updateOnlyChanged(RhPublicDetail obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<RhPublicDetail> queryList(Map<String, Object> map);

	public RhPublicDetail queryByKey(Object obj);

}
