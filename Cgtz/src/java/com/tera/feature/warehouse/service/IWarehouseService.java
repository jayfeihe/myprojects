package com.tera.feature.warehouse.service;



import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.warehouse.model.Warehouse;

/**
 * 
 * T_WAREHOUSE服务类
 * <b>功能：</b>WarehouseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface IWarehouseService {

	public void add(Warehouse... objs)  throws Exception ;
	
	
	public void update(Warehouse obj)  throws Exception ;
	
	public void updateOnlyChanged(Warehouse obj)  throws Exception ;
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<Warehouse> queryList(Map<String, Object> map) throws Exception;

	public Warehouse queryByKey(Object id) throws Exception ;

	public PageList<Warehouse> queryPageList(Map<String, Object> params);
}
