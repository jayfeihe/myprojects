package com.tera.report.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.CollateralAccountBean;

public interface ICollateralAccountBeanService {
    
	public int queryCount(Map<String, Object> map);
	
	public List<CollateralAccountBean> queryList(Map<String, Object> map) throws Exception;
    
	public PageList<CollateralAccountBean> queryPageList(Map<String, Object> params) throws Exception;
}
