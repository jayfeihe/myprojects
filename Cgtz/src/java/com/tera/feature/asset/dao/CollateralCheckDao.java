package com.tera.feature.asset.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.asset.model.CollateralCheck;

/**
 * 
 * 押品检查信息记录
DAO
 * <b>功能：</b>CollateralCheckDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 10:11:56<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollateralCheckDao {
		
	public void add(CollateralCheck obj);	
	
	public void update(CollateralCheck obj);
	
	public void updateOnlyChanged(CollateralCheck obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollateralCheck> queryList(Map<String, Object> map);

	public CollateralCheck queryByKey(Object obj);

}
