package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.ReportDeal;

public interface IReportDealService {
    
	public int queryCount(Map<String, Object> map);
	
	public List<ReportDeal> queryList(Map<String, Object> map) throws Exception;
    
	public PageList<ReportDeal> queryPageList(Map<String, Object> params) throws Exception;
}
