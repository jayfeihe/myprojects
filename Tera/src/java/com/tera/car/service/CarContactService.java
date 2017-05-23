package com.tera.car.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.car.dao.CarContactDao;
import com.tera.car.model.CarContact;
import com.tera.car.model.CarContactHistory;

/**
 * 
 * 信用借款申请联系人表服务类
 * <b>功能：</b>CarContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:22:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carContactService")
public class CarContactService {

	private final static Logger log = Logger.getLogger(CarContactService.class);

	@Autowired(required=false)
    private CarContactDao dao;

	@Transactional
	public void add(CarContact... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CarContact obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CarContact obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CarContact obj)  throws Exception {
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
	
	public List<CarContact> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public List<CarContactHistory> getRelationList(Map<String, Object> map) throws Exception {
		return dao.getRelationList(map);
	}
	public List<CarContact> queryListOrderByRelation(Map<String, Object> map) throws Exception {
		return dao.queryListOrderByRelation(map);
	}
	
	public CarContact queryByKey(Object id) throws Exception {
		return (CarContact)dao.queryByKey(id);
	}
	
}
