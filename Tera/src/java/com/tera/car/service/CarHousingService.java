package com.tera.car.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.car.dao.CarHousingDao;
import com.tera.car.model.CarHousing;

/**
 * 
 * 信用贷款申请房产信息服务类
 * <b>功能：</b>CarHousingDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:32<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carHousingService")
public class CarHousingService {

	private final static Logger log = Logger.getLogger(CarHousingService.class);

	@Autowired(required=false)
    private CarHousingDao dao;

	@Transactional
	public void add(CarHousing... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CarHousing obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CarHousing obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CarHousing obj)  throws Exception {
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
	
	public List<CarHousing> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CarHousing queryByKey(Object id) throws Exception {
		return (CarHousing)dao.queryByKey(id);
	}
	
}
