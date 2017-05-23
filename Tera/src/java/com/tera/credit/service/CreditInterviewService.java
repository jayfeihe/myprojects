package com.tera.credit.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.credit.dao.CreditInterviewDao;
import com.tera.credit.model.CreditInterview;

/**
 * 
 * 信用贷款面审调查表服务类
 * <b>功能：</b>CreditInterviewDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:54:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("creditInterviewService")
public class CreditInterviewService {

	private final static Logger log = Logger.getLogger(CreditInterviewService.class);

	@Autowired(required=false)
    private CreditInterviewDao dao;

	@Transactional
	public void add(CreditInterview... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CreditInterview obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CreditInterview obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CreditInterview obj)  throws Exception {
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
	
	public List<CreditInterview> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CreditInterview queryByKey(Object id) throws Exception {
		return (CreditInterview)dao.queryByKey(id);
	}
	
}
