package com.tera.feature.overdue.service;

import java.util.List;
import java.util.Map;





import javax.servlet.http.HttpServletRequest;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.overdue.model.OverdueReport;

/**
 * 
 * 逾期报告服务类
 * <b>功能：</b>OverdueReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface IOverdueReportService {

	
	public void add(OverdueReport... objs)  throws Exception;
	
	
	public void update(OverdueReport obj)  throws Exception ;
	
	
	public void updateOnlyChanged(OverdueReport obj)  throws Exception ;
	
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception;
	
	public List<OverdueReport> queryList(Map<String, Object> map) throws Exception ;

	public OverdueReport queryByKey(Object id) throws Exception;
	
	public void updateReport(HttpServletRequest request,OverdueReport report);
	
	public OverdueReport queryByNum(int num,String contractId);


	public PageList<OverdueReport> queryPageList(Map<String, Object> params)throws Exception;
	
}
