package com.tera.audit.account.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.account.model.MarginInfo;

public interface IMarginInfoService {
    
	public void add(MarginInfo... objs)throws Exception;	
	
	public void update(MarginInfo obj)throws Exception;
	
	public void updateOnlyChanged(MarginInfo obj)throws Exception;
		
	public void delete(Object... ids)throws Exception;

	public int queryCount(Map<String, Object> map)throws Exception;
	
	public List<MarginInfo> queryList(Map<String, Object> map)throws Exception;

	public MarginInfo queryByKey(Object obj)throws Exception;
	
	public PageList<MarginInfo> queryPageList(Map<String, Object> params);
} 
