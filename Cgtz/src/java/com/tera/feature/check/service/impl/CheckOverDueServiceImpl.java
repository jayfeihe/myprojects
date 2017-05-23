package com.tera.feature.check.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.check.dao.CheckOverDueDao;
import com.tera.feature.check.model.CheckOverDue;
import com.tera.feature.check.service.ICheckOverDueService;
import com.tera.feature.overdue.model.SalesMenCllt;
import com.tera.feature.warehouse.dao.WarehouseDao;
import com.tera.feature.warehouse.model.Warehouse;
import com.tera.sys.service.MybatisBaseService;

@Service("checkOverDueService")
public class CheckOverDueServiceImpl extends MybatisBaseService<CheckOverDue> implements ICheckOverDueService {
	private final static Logger log = Logger.getLogger(CheckOverDueServiceImpl.class);

	@Autowired(required=false)
    private CheckOverDueDao dao;
	@Override
	@Transactional
	public void add(CheckOverDue... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CheckOverDue obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(CheckOverDue obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(CheckOverDue obj)  throws Exception {
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
	public List<CheckOverDue> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public CheckOverDue queryByKey(Object id) throws Exception {
		return (CheckOverDue)dao.queryByKey(id);
	}
	@Override
	public PageList<CheckOverDue> queryPageList(Map<String, Object> params) {
		return this.selectPageList(CheckOverDueDao.class, "queryList", params);
	}
}
