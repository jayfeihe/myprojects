package com.tera.renhang.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.renhang.dao.RhPublicDetailDao;
import com.tera.renhang.model.RhPublicDetail;

/**
 * 
 * 信用贷款人行报告公共信息明细服务类
 * <b>功能：</b>RhPublicDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:07:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("rhPublicDetailService")
public class RhPublicDetailService {

	private final static Logger log = Logger.getLogger(RhPublicDetailService.class);

	@Autowired(required=false)
    private RhPublicDetailDao dao;

	@Transactional
	public void add(RhPublicDetail... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(RhPublicDetail obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(RhPublicDetail obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(RhPublicDetail obj)  throws Exception {
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
	
	public List<RhPublicDetail> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public RhPublicDetail queryByKey(Object id) throws Exception {
		return (RhPublicDetail)dao.queryByKey(id);
	}
	
}
