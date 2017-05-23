package com.tera.collection.phone.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.collection.phone.dao.CollectionRecordDao;
import com.tera.collection.phone.model.CollectionRecord;

/**
 * 
 * 催收记录表服务类
 * <b>功能：</b>CollectionRecordDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:39:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("collectionRecordService")
public class CollectionRecordService {

	private final static Logger log = Logger.getLogger(CollectionRecordService.class);

	@Autowired(required=false)
    private CollectionRecordDao dao;

	@Transactional
	public void add(CollectionRecord... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CollectionRecord obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CollectionRecord obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CollectionRecord obj)  throws Exception {
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
	
	public List<CollectionRecord> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CollectionRecord queryByKey(Object id) throws Exception {
		return (CollectionRecord)dao.queryByKey(id);
	}
	
}
