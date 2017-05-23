package com.tera.audit.law.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.law.dao.ContractOnlineDao;
import com.tera.audit.law.model.ContractOnline;
import com.tera.audit.law.service.IContractOnlineService;

/**
 * 
 * 线下线上合同关联表服务类
 * <b>功能：</b>ContractOnlineDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 15:03:28<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("contractOnlineService")
public class ContractOnlineService implements IContractOnlineService{

	private final static Logger log = Logger.getLogger(ContractOnlineService.class);

	@Autowired(required=false)
    private ContractOnlineDao dao;
	@Override
	@Transactional
	public void add(ContractOnline... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(ContractOnline obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(ContractOnline obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(ContractOnline obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	@Override
	public List<ContractOnline> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public ContractOnline queryByKey(Object id) throws Exception {
		return (ContractOnline)dao.queryByKey(id);
	}
	
}
