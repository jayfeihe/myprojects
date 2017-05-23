package com.tera.contract.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.contract.dao.SealDao;
import com.tera.contract.model.Seal;

/**
 * 
 * 服务类
 * <b>功能：</b>SealDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-29 13:26:30<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("sealService")
public class SealService {

	private final static Logger log = Logger.getLogger(SealService.class);

	@Autowired(required=false)
    private SealDao dao;

	@Transactional
	public void add(Seal... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(Seal obj : objs ){
			dao.add(obj);
		}
	}

	@Transactional
	public void update(Seal obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(Seal obj)  throws Exception {
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
	
	public List<Seal> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public Seal queryByKey(Object id) throws Exception {
		return (Seal)dao.queryByKey(id);
	}
	public String getSealCode(){
		return dao.getSealCode();
	}
}
