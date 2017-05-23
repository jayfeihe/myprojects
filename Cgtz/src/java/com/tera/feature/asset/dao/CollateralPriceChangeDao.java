package com.tera.feature.asset.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.asset.model.CollateralPriceChange;

/**
 * 
 * 价值变动表DAO
 * <b>功能：</b>CollateralPriceChangeDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-12 11:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollateralPriceChangeDao {
		
	public void add(CollateralPriceChange obj);	
	
	public void update(CollateralPriceChange obj);
	
	public void updateOnlyChanged(CollateralPriceChange obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollateralPriceChange> queryList(Map<String, Object> map);

	public CollateralPriceChange queryByKey(Object obj);

}
