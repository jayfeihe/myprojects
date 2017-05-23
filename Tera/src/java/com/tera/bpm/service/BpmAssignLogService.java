package com.tera.bpm.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.dao.BpmAssignLogDao;
import com.tera.bpm.model.BpmAssignLog;

/**
 * 
 * 流程任务分配日志服务类
 * <b>功能：</b>BpmAssignLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-11-03 10:50:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service
public class BpmAssignLogService {

	private final static Logger log = Logger.getLogger(BpmAssignLogService.class);

	@Autowired(required=false)
    private BpmAssignLogDao dao;

	@Transactional
	public void add(BpmAssignLog... objs)  {
		if(objs == null || objs.length < 1){
			return;
		}
		for(BpmAssignLog obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(BpmAssignLog obj) {
		dao.update(obj);
	}
		
	@Transactional
	public void delete(Object... ids)  {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)  {
		return dao.queryCount(map);
	}
	
	public List<BpmAssignLog> queryList(Map<String, Object> params)  {
		return dao.queryList(params);
	}

	public BpmAssignLog queryByKey(Object id) {
		return (BpmAssignLog)dao.queryByKey(id);
	}
	
}
