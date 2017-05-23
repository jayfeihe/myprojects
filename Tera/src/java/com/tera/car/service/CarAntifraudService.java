package com.tera.car.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.car.dao.CarAntifraudDao;
import com.tera.car.model.CarAntifraud;

/**
 * 
 * 信用贷款反欺诈表服务类
 * <b>功能：</b>CarAntifraudDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:07:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carAntifraudService")
public class CarAntifraudService {

	private final static Logger log = Logger.getLogger(CarAntifraudService.class);

	@Autowired(required=false)
    private CarAntifraudDao dao;

	@Transactional
	public void add(CarAntifraud... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CarAntifraud obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CarAntifraud obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CarAntifraud obj)  throws Exception {
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
	
	public List<CarAntifraud> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CarAntifraud queryByKey(Object id) throws Exception {
		return (CarAntifraud)dao.queryByKey(id);
	}
	
	/**
	 * 根据appId和state查找最新的欺诈审核信息
	 * @param appId
	 * @param state 1:未解决,2:已解决
	 * @return
	 */
	public CarAntifraud queryLatestByAppId(String appId,String state) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("appId", appId);
		map.put("state", state);
		return dao.queryLatestByAppId(map);
	}
}
