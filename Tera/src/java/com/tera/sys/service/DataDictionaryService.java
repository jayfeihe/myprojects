package com.tera.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.dao.DataDictionaryDao;
import com.tera.sys.model.DataDictionary;

/**
 * 
 * <br>
 * <b>功能：</b>DataDictionaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 16:19:12<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("dataDictionaryService")
public class DataDictionaryService<T> {

	private final static Logger log = Logger.getLogger(DataDictionaryService.class);

	@Autowired(required=false)
    private DataDictionaryDao<DataDictionary> dao;

	@Transactional
	public void add(DataDictionary... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(DataDictionary t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(DataDictionary t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(DataDictionary t)  throws Exception {
		dao.updateOnlyChanged(t);
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
	
	public List<DataDictionary> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public DataDictionary queryByKey(Object id) throws Exception {
		return (DataDictionary)dao.queryByKey(id);
	}
	
	public List<String> queryKeyNamesAndParentKeys() {
		return dao.queryKeyNamesAndParentKeys();
	}
}
