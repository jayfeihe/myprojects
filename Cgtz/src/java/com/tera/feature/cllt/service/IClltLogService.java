package com.tera.feature.cllt.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.cllt.model.ClltLog;

public interface IClltLogService {
    
	public void add(ClltLog... obj) throws Exception;	
	
	public void update(ClltLog obj) throws Exception;
	
	public void updateOnlyChanged(ClltLog obj) throws Exception;
		
	public void delete(Object... obj) throws Exception;

	public int queryCount(Map<String, Object> map) throws Exception;
	
	public List<ClltLog> queryList(Map<String, Object> map) throws Exception;

	public ClltLog queryByKey(Object obj) throws Exception;

	public PageList<ClltLog> queryPageList(Map<String, Object> params)
			throws Exception;
}
