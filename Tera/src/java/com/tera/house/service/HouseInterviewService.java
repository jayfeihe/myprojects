package com.tera.house.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.house.dao.HouseInterviewDao;
import com.tera.house.model.HouseInterview;

/**
 * 
 * 信用贷款面审调查表服务类
 * <b>功能：</b>HouseInterviewDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:54:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseInterviewService")
public class HouseInterviewService {

	private final static Logger log = Logger.getLogger(HouseInterviewService.class);

	@Autowired(required=false)
    private HouseInterviewDao dao;

	@Transactional
	public void add(HouseInterview... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(HouseInterview obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(HouseInterview obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(HouseInterview obj)  throws Exception {
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
	
	public List<HouseInterview> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public HouseInterview queryByKey(Object id) throws Exception {
		return (HouseInterview)dao.queryByKey(id);
	}
	
}
