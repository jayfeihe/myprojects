package com.tera.feature.warehouse.service.impl;



import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.warehouse.dao.WarehouseDao;
import com.tera.feature.warehouse.model.Warehouse;
import com.tera.feature.warehouse.service.IWarehouseService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * T_WAREHOUSE服务类
 * <b>功能：</b>WarehouseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("warehouseService")
public class WarehouseServiceImpl extends MybatisBaseService<Warehouse> implements IWarehouseService{

	private final static Logger log = Logger.getLogger(WarehouseServiceImpl.class);

	@Autowired(required=false)
    private WarehouseDao dao;
	@Override
	@Transactional
	public void add(Warehouse... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Warehouse obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(Warehouse obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(Warehouse obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	@Override
	public List<Warehouse> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public Warehouse queryByKey(Object id) throws Exception {
		return (Warehouse)dao.queryByKey(id);
	}
	@Override
	public PageList<Warehouse> queryPageList(Map<String, Object> params) {
		return this.selectPageList(WarehouseDao.class, "queryList", params);
	}
	
}
