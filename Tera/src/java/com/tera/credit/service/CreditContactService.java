package com.tera.credit.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.credit.dao.CreditContactDao;
import com.tera.credit.model.CreditContact;
import com.tera.credit.model.CreditContactHistory;

/**
 * 
 * 信用借款申请联系人表服务类
 * <b>功能：</b>CreditContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:22:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("creditContactService")
public class CreditContactService {

	private final static Logger log = Logger.getLogger(CreditContactService.class);

	@Autowired(required=false)
    private CreditContactDao dao;

	@Transactional
	public void add(CreditContact... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CreditContact obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CreditContact obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CreditContact obj)  throws Exception {
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
	
	public List<CreditContact> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public List<CreditContactHistory> getRelationList(Map<String, Object> map) throws Exception {
		return dao.getRelationList(map);
	}
	public List<CreditContact> queryListOrderByRelation(Map<String, Object> map) throws Exception {
		return dao.queryListOrderByRelation(map);
	}
	
	public CreditContact queryByKey(Object id) throws Exception {
		return (CreditContact)dao.queryByKey(id);
	}
	
}
