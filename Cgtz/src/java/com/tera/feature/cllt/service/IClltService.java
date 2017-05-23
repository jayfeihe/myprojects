package com.tera.feature.cllt.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.cllt.model.Cllt;

public interface IClltService {
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<Cllt> queryList(Map<String, Object> map) throws Exception ;
	
	public PageList<Cllt> queryPageList(Map<String, Object> params) throws Exception;
}
