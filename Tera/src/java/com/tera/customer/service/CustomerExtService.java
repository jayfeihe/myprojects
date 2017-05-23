package com.tera.customer.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.customer.dao.CustomerExtDao;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-29 18:35:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("customerExtService")
public class CustomerExtService<T> {

	private final static Logger log = Logger.getLogger(CustomerExtService.class);

	@Autowired
    private CustomerExtDao dao;

	@Transactional
	public void add(T... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(T t : ts ){
			dao.add(t);
		}
	}

	@Transactional
	public void update(T t)  throws Exception {
		dao.update(t);
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
	
	public List<T> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}
	
}
