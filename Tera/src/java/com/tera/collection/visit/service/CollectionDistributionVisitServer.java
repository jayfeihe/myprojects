package com.tera.collection.visit.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import com.tera.collection.phone.model.CollectionDistributionCount;
import com.tera.collection.visit.dao.CollectionDistributionVisitDao;
 
import com.tera.collection.visit.model.VisitDistribution;

/**
 * 
 * 催收信息基本表服务类
 * <b>功能：</b>CollectionBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("CollectionDistributionVisitServer")
public class CollectionDistributionVisitServer {

	private final static Logger log = Logger.getLogger(CollectionBaseVisitService.class);

	@Autowired(required=false)
    private CollectionDistributionVisitDao collectionDisDao;
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return collectionDisDao.queryCount(map);
	}
	
	public List<VisitDistribution> queryList(Map<String, Object> map) throws Exception {
		return collectionDisDao.queryList(map);
	}

	public VisitDistribution queryByKey(Object id) throws Exception {
		return (VisitDistribution)collectionDisDao.queryByKey(id);
	}
	@Transactional
	public void updateVisitCollectionMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		collectionDisDao.updateVisitCollectionMap(map);
		collectionDisDao.addVisitDistributionMap(map);
	}

	
	//分单统计
	public int queryTaskNumCount(Map<String, Object> map)throws Exception {
		return collectionDisDao.queryTaskNumCount(map);
	}
	public List<CollectionDistributionCount> queryTaskNumList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return collectionDisDao.queryTaskNumList(queryMap);
		  
	}
	
}
