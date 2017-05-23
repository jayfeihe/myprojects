package com.tera.renhang.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.renhang.dao.RhQueryDao;
import com.tera.renhang.model.RhQuery;

/**
 * 
 * 信用贷款人行报告查询汇总服务类
 * <b>功能：</b>RhQueryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:07:22<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("rhQueryService")
public class RhQueryService {

	private final static Logger log = Logger.getLogger(RhQueryService.class);

	@Autowired(required=false)
    private RhQueryDao dao;

	@Transactional
	public void add(RhQuery... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(RhQuery obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(RhQuery obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(RhQuery obj)  throws Exception {
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
	
	public List<RhQuery> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public RhQuery queryByKey(Object id) throws Exception {
		return (RhQuery)dao.queryByKey(id);
	}
	
}
