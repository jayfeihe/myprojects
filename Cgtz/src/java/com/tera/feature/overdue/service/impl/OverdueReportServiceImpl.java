package com.tera.feature.overdue.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.overdue.dao.OverdueReportDao;
import com.tera.feature.overdue.dao.SalesMenClltDao;
import com.tera.feature.overdue.model.OverdueReport;
import com.tera.feature.overdue.model.SalesMenCllt;
import com.tera.feature.overdue.service.IOverdueReportService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 逾期报告服务类
 * <b>功能：</b>OverdueReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("overdueReportService")
public class OverdueReportServiceImpl extends MybatisBaseService<OverdueReport> implements IOverdueReportService{

	private final static Logger log = Logger.getLogger(OverdueReportServiceImpl.class);

	@Autowired(required=false)
    private OverdueReportDao dao;
	@Override
	@Transactional
	public void add(OverdueReport... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(OverdueReport obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(OverdueReport obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(OverdueReport obj)  throws Exception {
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
	public List<OverdueReport> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public OverdueReport queryByKey(Object id) throws Exception {
		return (OverdueReport)dao.queryByKey(id);
	}
	//逾期报告提交人，和提交时间
	public void updateReport(HttpServletRequest request,OverdueReport report){
	}
	@Override
	public OverdueReport queryByNum(int num, String contractId) {	
		return dao.queryByNum(num, contractId);
	}
	@Override
	public PageList<OverdueReport> queryPageList(Map<String, Object> params) throws Exception {
		return this.selectPageList(OverdueReportDao.class, "queryList", params);
	}
}
