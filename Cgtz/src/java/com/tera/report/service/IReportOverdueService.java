package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.warehouse.model.Warehouse;
import com.tera.report.model.ReportDeal;
import com.tera.report.model.ReportOverdue;

public interface IReportOverdueService {
    
	//插入当天的逾期数据
	public void add(ReportOverdue obj)throws Exception;
	
	//查询逾期统计信息
	public List<ReportOverdue> queryOverdueStatics(Map<String,Object> map)throws Exception;
	
	public int queryCount(Map<String, Object> map)throws Exception;
	
	public List<ReportOverdue> queryList(Map<String, Object> map) throws Exception;
    
	public PageList<ReportOverdue> queryPageList(Map<String, Object> params) throws Exception;
}
