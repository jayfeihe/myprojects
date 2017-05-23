package com.tera.feature.check.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.check.dao.OverdueAcceptDao;
import com.tera.feature.check.model.OverdueAccept;
import com.tera.feature.check.service.IOverdueAcceptService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 逾期受理登记表服务类
 * <b>功能：</b>OverdueAcceptDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 19:46:37<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("overdueAcceptService")
public class OverdueAcceptServiceImpl extends MybatisBaseService<OverdueAccept> implements IOverdueAcceptService{

	private final static Logger log = Logger.getLogger(OverdueAcceptServiceImpl.class);

	@Autowired(required=false)
    private OverdueAcceptDao dao;
    @Override
	@Transactional
	public void add(OverdueAccept... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(OverdueAccept obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(OverdueAccept obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(OverdueAccept obj)  throws Exception {
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
	
	public List<OverdueAccept> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public OverdueAccept queryByKey(Object id) throws Exception {
		return (OverdueAccept)dao.queryByKey(id);
	}
	
	public PageList<OverdueAccept> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(OverdueAcceptDao.class, "queryList", params);
	} 
}
