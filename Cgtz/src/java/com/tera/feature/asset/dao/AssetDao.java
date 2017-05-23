package com.tera.feature.asset.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.asset.model.Asset;

/**
 * 
 * 资产信息DAO
 * <b>功能：</b>AssetDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface AssetDao {
		
	public void add(Asset obj);	
	
	public void update(Asset obj);
	
	public void updateOnlyChanged(Asset obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Asset> queryList(Map<String, Object> map);

	public Asset queryByKey(Object obj);

}
