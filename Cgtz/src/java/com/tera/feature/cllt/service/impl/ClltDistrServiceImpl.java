package com.tera.feature.cllt.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.cllt.dao.ClltDistrDao;
import com.tera.feature.cllt.model.ClltDistr;
import com.tera.feature.cllt.service.IClltDistrService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 催收分配表服务类
 * <b>功能：</b>ClltDistrDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-20 09:25:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("clltDistrService")
public class ClltDistrServiceImpl extends MybatisBaseService<ClltDistr> implements IClltDistrService{

	private final static Logger log = Logger.getLogger(ClltDistrServiceImpl.class);

	@Autowired(required=false)
    private ClltDistrDao dao;

	@Transactional
	public void add(ClltDistr... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(ClltDistr obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(ClltDistr obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(ClltDistr obj)  throws Exception {
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
	
	public List<ClltDistr> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public ClltDistr queryByKey(Object id) throws Exception {
		return (ClltDistr)dao.queryByKey(id);
	}

	@Override
	public ClltDistr queryByConId(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryByConId(obj);
	}

	@Override
	public void updateByConId(Object obj) throws Exception {
		// TODO Auto-generated method stub
		dao.updateByConId(obj);
	}

	@Override
	public PageList<ClltDistr> queryPageList(Map<String, Object> params) {
		return this.selectPageList(ClltDistrDao.class, "queryList", params);
	}
}
