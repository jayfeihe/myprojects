package com.tera.sales.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sales.dao.SalesDao;
import com.tera.sales.model.Sales;

/**
 * 
 * <br>
 * <b>功能：</b>SalesDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-08-19 14:55:17<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("salesService")
public class SalesService {

	private final static Logger log = Logger.getLogger(SalesService.class);

	@Autowired(required=false)
    private SalesDao dao;

	@Transactional
	public void add(Sales... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Sales obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(Sales obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(Sales obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<Sales> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public Sales queryByKey(Object id) throws Exception {
		return (Sales)dao.queryByKey(id);
	}
	
	public List<Sales> queryListSelect(Map<String, Object> map) throws Exception {
		return dao.queryListSelect(map);
	}
	
	public List<Sales> queryExcludeSelf(Map<String, Object> map) throws Exception {
		return dao.queryExcludeSelf(map);
	}
}
