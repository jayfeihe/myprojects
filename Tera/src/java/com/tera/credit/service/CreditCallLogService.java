package com.tera.credit.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.credit.dao.CreditCallLogDao;
import com.tera.credit.model.CreditCallLog;

/**
 * 
 * 信用贷款面审调查通话记录服务类
 * <b>功能：</b>CreditCallLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:55:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("creditCallLogService")
public class CreditCallLogService {

	private final static Logger log = Logger.getLogger(CreditCallLogService.class);

	@Autowired(required=false)
    private CreditCallLogDao dao;

	@Transactional
	public void add(CreditCallLog... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CreditCallLog obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CreditCallLog obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CreditCallLog obj)  throws Exception {
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
	
	public List<CreditCallLog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CreditCallLog queryByKey(Object id) throws Exception {
		return (CreditCallLog)dao.queryByKey(id);
	}
	
}
