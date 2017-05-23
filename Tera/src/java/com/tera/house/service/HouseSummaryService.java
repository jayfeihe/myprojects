package com.tera.house.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.house.dao.HouseSummaryDao;
import com.tera.house.model.HouseSummary;

/**
 * 
 * 信用贷款申请影像摘要服务类
 * <b>功能：</b>HouseSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseSummaryService")
public class HouseSummaryService {

	private final static Logger log = Logger.getLogger(HouseSummaryService.class);

	@Autowired(required=false)
    private HouseSummaryDao dao;

	@Transactional
	public void add(HouseSummary... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(HouseSummary obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(HouseSummary obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(HouseSummary obj)  throws Exception {
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
	
	public List<HouseSummary> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public HouseSummary queryByKey(Object id) throws Exception {
		return (HouseSummary)dao.queryByKey(id);
	}
	
}
