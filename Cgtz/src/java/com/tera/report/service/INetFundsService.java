package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.NetFunds;

public interface INetFundsService {
    
	public int queryCount(Map<String, Object> map);
	
	public List<NetFunds> queryList(Map<String, Object> map) throws Exception;
    
	public PageList<NetFunds> queryPageList(Map<String, Object> params) throws Exception;
}
