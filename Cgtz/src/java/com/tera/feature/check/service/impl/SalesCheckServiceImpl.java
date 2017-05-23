package com.tera.feature.check.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.check.dao.CheckOverDueDao;
import com.tera.feature.check.dao.SalesCheckDao;
import com.tera.feature.check.model.CheckOverDue;
import com.tera.feature.check.model.SalesCheck;
import com.tera.feature.check.service.ISalesCheckService;
import com.tera.feature.overdue.model.SalesCheckLog;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 业务员，稽查人员催收跟进记录表服务类
 * <b>功能：</b>SalesCheckLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 14:02:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("salesCheckService")
public class SalesCheckServiceImpl extends MybatisBaseService<SalesCheck> implements ISalesCheckService{

	private final static Logger log = Logger.getLogger(SalesCheckServiceImpl.class);

	@Autowired(required=false)
    private SalesCheckDao dao;
    @Override
	@Transactional
	public void add(SalesCheck... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(SalesCheck obj : objs ){
			dao.add(obj);
		}
	}
    @Override
	@Transactional
	public void update(SalesCheck obj)  throws Exception {
		dao.update(obj);
	}
    @Override
	@Transactional
	public void updateOnlyChanged(SalesCheck obj)  throws Exception {
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
	public List<SalesCheck> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
    @Override
	public SalesCheck queryByKey(Object id) throws Exception {
		return (SalesCheck)dao.queryByKey(id);
	}
    @Override
	public PageList<SalesCheck> queryPageList(Map<String, Object> params) {
		return this.selectPageList(SalesCheckDao.class, "queryList", params);
	}
	
}
