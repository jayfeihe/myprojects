package com.tera.feature.check.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.check.model.OverdueAccept;

public interface IOverdueAcceptService {
    
	public void add(OverdueAccept... objs) throws Exception;	
	
	public void update(OverdueAccept obj) throws Exception;
	
	public void updateOnlyChanged(OverdueAccept obj) throws Exception;
		
	public void delete(Object... objs) throws Exception;

	public int queryCount(Map<String, Object> map) throws Exception;
	
	public List<OverdueAccept> queryList(Map<String, Object> map) throws Exception;

	public OverdueAccept queryByKey(Object obj) throws Exception;

	public PageList<OverdueAccept> queryPageList(Map<String, Object> params) throws Exception;
}
