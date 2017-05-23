package com.tera.feature.check.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.check.model.AuditReport;

/**
 * 
 * 逾期报告服务类
 * <b>功能：</b>OverdueReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface IAuditReportService {

	
	public void add(AuditReport... objs)  throws Exception;
	
	
	public void update(AuditReport obj)  throws Exception ;
	
	
	public void updateOnlyChanged(AuditReport obj)  throws Exception ;
	
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception;
	
	public List<AuditReport> queryList(Map<String, Object> map) throws Exception ;

	public AuditReport queryByKey(Object id) throws Exception;
	
	public AuditReport printByKey(Object obj) throws Exception;
	
	public void updateReport(HttpServletRequest request,AuditReport report);


	public PageList<AuditReport> queryPageList(Map<String, Object> params);
	
}
