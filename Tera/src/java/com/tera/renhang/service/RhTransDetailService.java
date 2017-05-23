package com.tera.renhang.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.renhang.dao.RhTransDetailDao;
import com.tera.renhang.model.RhTransDetail;

/**
 * 
 * 信用贷款人行报告交易明细服务类
 * <b>功能：</b>RhTransDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:06:05<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("rhTransDetailService")
public class RhTransDetailService {

	private final static Logger log = Logger.getLogger(RhTransDetailService.class);

	@Autowired(required=false)
    private RhTransDetailDao dao;

	@Transactional
	public void add(RhTransDetail... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(RhTransDetail obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(RhTransDetail obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(RhTransDetail obj)  throws Exception {
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
	
	public List<RhTransDetail> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public RhTransDetail queryByKey(Object id) throws Exception {
		return (RhTransDetail)dao.queryByKey(id);
	}
	
}
