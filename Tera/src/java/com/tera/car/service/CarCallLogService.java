package com.tera.car.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.car.dao.CarCallLogDao;
import com.tera.car.model.CarCallLog;

/**
 * 
 * 信用贷款面审调查通话记录服务类
 * <b>功能：</b>CarCallLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:55:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carCallLogService")
public class CarCallLogService {

	private final static Logger log = Logger.getLogger(CarCallLogService.class);

	@Autowired(required=false)
    private CarCallLogDao dao;

	@Transactional
	public void add(CarCallLog... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CarCallLog obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CarCallLog obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CarCallLog obj)  throws Exception {
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
	
	public List<CarCallLog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CarCallLog queryByKey(Object id) throws Exception {
		return (CarCallLog)dao.queryByKey(id);
	}
	
}
