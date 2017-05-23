package com.tera.collection.phone.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.collection.phone.dao.CollectionResultDao;
import com.tera.collection.phone.model.CollectionResult;

/**
 * 
 * 催收人员绩效表服务类
 * <b>功能：</b>CollectionResultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:40:25<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collectionResultService")
public class CollectionResultService {

	private final static Logger log = Logger.getLogger(CollectionResultService.class);

	@Autowired(required=false)
    private CollectionResultDao dao;

	@Transactional
	public void add(CollectionResult... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CollectionResult obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CollectionResult obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CollectionResult obj)  throws Exception {
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
	
	public List<CollectionResult> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CollectionResult queryByKey(Object id) throws Exception {
		return (CollectionResult)dao.queryByKey(id);
	}
	
}
