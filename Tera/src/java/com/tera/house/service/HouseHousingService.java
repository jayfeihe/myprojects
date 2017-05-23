package com.tera.house.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.house.dao.HouseHousingDao;
import com.tera.house.model.HouseHousing;

/**
 * 
 * 信用贷款申请房产信息服务类
 * <b>功能：</b>HouseHousingDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:32<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseHousingService")
public class HouseHousingService {

	private final static Logger log = Logger.getLogger(HouseHousingService.class);

	@Autowired(required=false)
    private HouseHousingDao dao;

	@Transactional
	public void add(HouseHousing... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(HouseHousing obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(HouseHousing obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(HouseHousing obj)  throws Exception {
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
	
	public List<HouseHousing> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public HouseHousing queryByKey(Object id) throws Exception {
		return (HouseHousing)dao.queryByKey(id);
	}
	
}
