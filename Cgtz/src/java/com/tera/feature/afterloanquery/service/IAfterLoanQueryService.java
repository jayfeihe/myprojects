package com.tera.feature.afterloanquery.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.afterloanquery.dao.AfterLoanQueryDao;
import com.tera.feature.afterloanquery.model.AfterLoanQueryBean;

public interface IAfterLoanQueryService {

public int queryCount(Map<String, Object> map)throws Exception;
	
public List<AfterLoanQueryBean> queryList(Map<String, Object> map)throws Exception;


public PageList<AfterLoanQueryBean> queryPageList(Map<String, Object> params) throws Exception ;

	
	
}
