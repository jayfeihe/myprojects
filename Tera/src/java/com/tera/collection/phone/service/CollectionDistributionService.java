package com.tera.collection.phone.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.collection.phone.dao.CollectionDistributionDao;
import com.tera.collection.phone.model.CollectionDistribution;
import com.tera.collection.phone.model.CollectionDistributionCount;

/**
 * 
 * 催收分配表服务类
 * <b>功能：</b>CollectionDistributionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:37:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collectionDistributionService")
public class CollectionDistributionService {

	private final static Logger log = Logger.getLogger(CollectionDistributionService.class);

	@Autowired(required=false)
    private CollectionDistributionDao dao;

	@Transactional
	public void add(CollectionDistribution... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CollectionDistribution obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CollectionDistribution obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CollectionDistribution obj)  throws Exception {
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
	
	public List<CollectionDistribution> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CollectionDistribution queryByKey(Object id) throws Exception {
		return (CollectionDistribution)dao.queryByKey(id);
	}
	public List<CollectionDistributionCount> queryPartDetailList(Map<String, Object> map)throws Exception {
		return dao.queryPartDetailList(map);
	}
	public int queryPartDetailCount(Map<String, Object> map)throws Exception {
		return dao.queryPartDetailCount(map);
	}
	
}
