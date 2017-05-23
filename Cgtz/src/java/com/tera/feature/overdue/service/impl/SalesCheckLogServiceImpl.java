package com.tera.feature.overdue.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.overdue.dao.OverdueReportDao;
import com.tera.feature.overdue.dao.SalesCheckLogDao;
import com.tera.feature.overdue.model.OverdueReport;
import com.tera.feature.overdue.model.SalesCheckLog;
import com.tera.feature.overdue.service.ISalesCheckLogService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 业务员，稽查人员催收跟进记录表服务类
 * <b>功能：</b>SalesCheckLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 14:02:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("salesCheckLogService")
public class SalesCheckLogServiceImpl extends MybatisBaseService<SalesCheckLog> implements ISalesCheckLogService{

	private final static Logger log = Logger.getLogger(SalesCheckLogServiceImpl.class);

	@Autowired(required=false)
    private SalesCheckLogDao dao;
    @Override
	@Transactional
	public void add(SalesCheckLog... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(SalesCheckLog obj : objs ){
			dao.add(obj);
		}
	}
    @Override
	@Transactional
	public void update(SalesCheckLog obj)  throws Exception {
		dao.update(obj);
	}
    @Override
	@Transactional
	public void updateOnlyChanged(SalesCheckLog obj)  throws Exception {
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
	public List<SalesCheckLog> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
    @Override
	public SalesCheckLog queryByKey(Object id) throws Exception {
		return (SalesCheckLog)dao.queryByKey(id);
	}
    @Override
	public PageList<SalesCheckLog> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(SalesCheckLogDao.class, "queryList", params);
	}
	
}
