package com.tera.feature.overdue.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.overdue.model.SalesMenCllt;

public interface ISalesMenClltService {

	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<SalesMenCllt> queryList(Map<String, Object> map) throws Exception ;
	
	public PageList<SalesMenCllt> queryPageList(Map<String, Object> params) throws Exception;
	
}
