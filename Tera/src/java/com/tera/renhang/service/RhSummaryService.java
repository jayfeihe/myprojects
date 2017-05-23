package com.tera.renhang.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.renhang.dao.RhSummaryDao;
import com.tera.renhang.model.RhSummary;

/**
 * 
 * 信用贷款人行报告信息概要服务类
 * <b>功能：</b>RhSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:05:39<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("rhSummaryService")
public class RhSummaryService {

	private final static Logger log = Logger.getLogger(RhSummaryService.class);

	@Autowired(required=false)
    private RhSummaryDao dao;

	@Transactional
	public void add(RhSummary... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(RhSummary obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(RhSummary obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(RhSummary obj)  throws Exception {
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
	
	public List<RhSummary> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public RhSummary queryByKey(Object id) throws Exception {
		return (RhSummary)dao.queryByKey(id);
	}
	
}
