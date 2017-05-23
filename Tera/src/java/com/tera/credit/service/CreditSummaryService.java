package com.tera.credit.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.credit.dao.CreditSummaryDao;
import com.tera.credit.model.CreditSummary;

/**
 * 
 * 信用贷款申请影像摘要服务类
 * <b>功能：</b>CreditSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("creditSummaryService")
public class CreditSummaryService {

	private final static Logger log = Logger.getLogger(CreditSummaryService.class);

	@Autowired(required=false)
    private CreditSummaryDao dao;

	@Transactional
	public void add(CreditSummary... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CreditSummary obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CreditSummary obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CreditSummary obj)  throws Exception {
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
	
	public List<CreditSummary> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CreditSummary queryByKey(Object id) throws Exception {
		return (CreditSummary)dao.queryByKey(id);
	}
	
}
