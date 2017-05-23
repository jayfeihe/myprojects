package com.tera.renhang.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.renhang.dao.RhReportDao;
import com.tera.renhang.model.RhReport;

/**
 * 
 * 信用贷款人行报告服务类
 * <b>功能：</b>RhReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:05:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("rhReportService")
public class RhReportService {

	private final static Logger log = Logger.getLogger(RhReportService.class);

	@Autowired(required=false)
    private RhReportDao dao;

	@Transactional
	public void add(RhReport... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(RhReport obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(RhReport obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(RhReport obj)  throws Exception {
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
	
	public List<RhReport> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public RhReport queryByKey(Object id) throws Exception {
		return (RhReport)dao.queryByKey(id);
	}
	
}
