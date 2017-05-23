package com.tera.house.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.house.dao.HouseCallLogDao;
import com.tera.house.model.HouseCallLog;

/**
 * 
 * 信用贷款面审调查通话记录服务类
 * <b>功能：</b>HouseCallLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:55:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseCallLogService")
public class HouseCallLogService {

	private final static Logger log = Logger.getLogger(HouseCallLogService.class);

	@Autowired(required=false)
    private HouseCallLogDao dao;

	@Transactional
	public void add(HouseCallLog... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(HouseCallLog obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(HouseCallLog obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(HouseCallLog obj)  throws Exception {
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
	
	public List<HouseCallLog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public HouseCallLog queryByKey(Object id) throws Exception {
		return (HouseCallLog)dao.queryByKey(id);
	}
	
}
