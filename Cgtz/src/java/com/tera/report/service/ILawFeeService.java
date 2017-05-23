package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.dao.LawFeeDao;
import com.tera.report.model.LawFee;
import com.tera.report.model.ReportDeal;
import com.tera.report.model.ReportOverdue;

public interface ILawFeeService {
	
	public int queryCount(Map<String, Object> map);
	public List<LawFee> queryList(Map<String, Object> map)throws Exception ;
	
	public PageList<LawFee> queryPageList(Map<String, Object> params) throws Exception;
}
