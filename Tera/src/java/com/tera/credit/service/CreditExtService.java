package com.tera.credit.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.credit.dao.CreditExtDao;
import com.tera.credit.model.CreditExt;

/**
 * 
 * 信用贷款申请信息扩展表服务类
 * <b>功能：</b>CreditExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:25:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("creditExtService")
public class CreditExtService {

	private final static Logger log = Logger.getLogger(CreditExtService.class);

	@Autowired(required=false)
    private CreditExtDao dao;

	@Transactional
	public void add(CreditExt... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CreditExt obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CreditExt obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CreditExt obj)  throws Exception {
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
	
	public List<CreditExt> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CreditExt queryByKey(Object id) throws Exception {
		return (CreditExt)dao.queryByKey(id);
	}
	
}
