package com.tera.feature.cllt.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.cllt.dao.ClltDao;
import com.tera.feature.cllt.dao.ClltLogDao;
import com.tera.feature.cllt.model.Cllt;
import com.tera.feature.cllt.model.ClltLog;
import com.tera.feature.cllt.service.IClltLogService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * T_CLLT_LOG服务类
 * <b>功能：</b>ClltLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-19 21:45:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("clltLogService")
public class ClltLogServiceImpl extends MybatisBaseService<ClltLog> implements IClltLogService{

	private final static Logger log = Logger.getLogger(ClltLogServiceImpl.class);

	@Autowired(required=false)
    private ClltLogDao dao;

	@Transactional
	public void add(ClltLog... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(ClltLog obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(ClltLog obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(ClltLog obj)  throws Exception {
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
	
	public List<ClltLog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public ClltLog queryByKey(Object id) throws Exception {
		return (ClltLog)dao.queryByKey(id);
	}
	@Override
	public PageList<ClltLog> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(ClltLogDao.class, "queryList", params);
	}
}
