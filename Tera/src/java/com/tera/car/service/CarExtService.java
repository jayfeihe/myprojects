package com.tera.car.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.car.dao.CarExtDao;
import com.tera.car.model.CarExt;

/**
 * 
 * 信用贷款申请信息扩展表服务类
 * <b>功能：</b>CarExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:25:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carExtService")
public class CarExtService {

	private final static Logger log = Logger.getLogger(CarExtService.class);

	@Autowired(required=false)
    private CarExtDao dao;

	@Transactional
	public void add(CarExt... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CarExt obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CarExt obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CarExt obj)  throws Exception {
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
	
	public List<CarExt> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CarExt queryByKey(Object id) throws Exception {
		return (CarExt)dao.queryByKey(id);
	}
	
}
