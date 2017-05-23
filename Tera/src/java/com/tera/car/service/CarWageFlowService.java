package com.tera.car.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.car.dao.CarWageFlowDao;
import com.tera.car.model.WageFlow;

/**
 * 
 * 信用贷款申请人工资流水服务类
 * <b>功能：</b>WageFlowDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-06 14:29:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carWageFlowService")
public class CarWageFlowService {

	private final static Logger log = Logger.getLogger(CarWageFlowService.class);

	@Autowired(required=false)
    private CarWageFlowDao dao;

	@Transactional
	public void add(WageFlow... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(WageFlow obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(WageFlow obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(WageFlow obj)  throws Exception {
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
	
	public List<WageFlow> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public WageFlow queryByKey(Object id) throws Exception {
		return (WageFlow)dao.queryByKey(id);
	}
	
}
