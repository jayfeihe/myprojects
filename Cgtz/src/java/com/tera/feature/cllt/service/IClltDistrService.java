package com.tera.feature.cllt.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.cllt.model.ClltDistr;

public interface IClltDistrService {
    //催收分单接口
    public void add(ClltDistr... obj) throws Exception;	
	
	public void update(ClltDistr obj) throws Exception;
	
	public void updateOnlyChanged(ClltDistr obj) throws Exception;
		
	public void delete(Object... obj) throws Exception;

	public int queryCount(Map<String, Object> map) throws Exception;
	
	public List<ClltDistr> queryList(Map<String, Object> map) throws Exception;

	public ClltDistr queryByKey(Object obj) throws Exception;
	
	public ClltDistr queryByConId(Object obj) throws Exception;
	
	public void updateByConId(Object obj) throws Exception;

	public PageList<ClltDistr> queryPageList(Map<String, Object> params);
}
