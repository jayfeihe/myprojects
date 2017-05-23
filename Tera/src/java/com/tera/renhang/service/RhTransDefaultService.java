package com.tera.renhang.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.renhang.dao.RhTransDefaultDao;
import com.tera.renhang.model.RhTransDefault;

/**
 * 
 * 信用贷款人行报告交易逾期透支记录服务类
 * <b>功能：</b>RhTransDefaultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:06:33<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("rhTransDefaultService")
public class RhTransDefaultService {

	private final static Logger log = Logger.getLogger(RhTransDefaultService.class);

	@Autowired(required=false)
    private RhTransDefaultDao dao;

	@Transactional
	public void add(RhTransDefault... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(RhTransDefault obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(RhTransDefault obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(RhTransDefault obj)  throws Exception {
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
	
	public List<RhTransDefault> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public RhTransDefault queryByKey(Object id) throws Exception {
		return (RhTransDefault)dao.queryByKey(id);
	}
	
}
