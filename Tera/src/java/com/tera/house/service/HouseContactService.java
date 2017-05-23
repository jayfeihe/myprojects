package com.tera.house.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.house.dao.HouseContactDao;
import com.tera.house.model.HouseContact;
import com.tera.house.model.HouseContactHistory;

/**
 * 
 * 信用借款申请联系人表服务类
 * <b>功能：</b>HouseContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:22:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseContactService")
public class HouseContactService {

	private final static Logger log = Logger.getLogger(HouseContactService.class);

	@Autowired(required=false)
    private HouseContactDao dao;

	@Transactional
	public void add(HouseContact... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(HouseContact obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(HouseContact obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(HouseContact obj)  throws Exception {
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
	
	public List<HouseContact> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public List<HouseContactHistory> getRelationList(Map<String, Object> map) throws Exception {
		return dao.getRelationList(map);
	}
	public List<HouseContact> queryListOrderByRelation(Map<String, Object> map) throws Exception {
		return dao.queryListOrderByRelation(map);
	}
	
	public HouseContact queryByKey(Object id) throws Exception {
		return (HouseContact)dao.queryByKey(id);
	}
	
}
