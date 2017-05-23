package com.tera.feature.check.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.check.dao.AuditReportDao;
import com.tera.feature.check.dao.CheckOverDueDao;
import com.tera.feature.check.model.AuditReport;
import com.tera.feature.check.model.CheckOverDue;
import com.tera.feature.check.service.IAuditReportService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * 逾期报告服务类
 * <b>功能：</b>OverdueReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("auditReportService")
public class AuditReportServiceImpl extends MybatisBaseService<AuditReport> implements IAuditReportService{

	private final static Logger log = Logger.getLogger(AuditReportServiceImpl.class);

	@Autowired(required=false)
    private AuditReportDao dao;
	@Override
	@Transactional
	public void add(AuditReport... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(AuditReport obj : objs ){
			dao.add(obj);
		}
	}
	@Override
	@Transactional
	public void update(AuditReport obj)  throws Exception {
		dao.update(obj);
	}
	@Override
	@Transactional
	public void updateOnlyChanged(AuditReport obj)  throws Exception {
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
	public List<AuditReport> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	@Override
	public AuditReport queryByKey(Object id) throws Exception {
		return (AuditReport)dao.queryByKey(id);
	}
	//逾期报告审核人，和审核时间
	public void updateReport(HttpServletRequest request,AuditReport report){
		
	}
	@Override
	public PageList<AuditReport> queryPageList(Map<String, Object> params) {
		return this.selectPageList(AuditReportDao.class, "queryList", params);
	}
	@Override
	public AuditReport printByKey(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return dao.printByKey(obj);
	}
}
