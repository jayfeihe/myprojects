package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.BillAcctFlow;
import com.tera.report.model.NetFunds;

public interface IBillAcctFlowService {
    
	public int queryCount(Map<String, Object> map);
	
	public List<BillAcctFlow> queryList(Map<String, Object> map) throws Exception;
    
	public PageList<BillAcctFlow> queryPageList(Map<String, Object> params) throws Exception;
}
