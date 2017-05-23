package com.tera.feature.warehouse.dao;


import java.util.List;
import java.util.Map;

import com.tera.feature.warehouse.model.Warehouse;

/**
 * 
 * T_WAREHOUSEDAO
 * <b>功能：</b>WarehouseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface WarehouseDao {
		
	public void add(Warehouse obj);	
	
	public void update(Warehouse obj);
	
	public void updateOnlyChanged(Warehouse obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Warehouse> queryList(Map<String, Object> map);

	public Warehouse queryByKey(Object obj);

}
