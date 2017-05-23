package com.tera.feature.afterloanquery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.afterloanquery.dao.AfterLoanQueryDao;
import com.tera.feature.afterloanquery.model.AfterLoanQueryBean;
import com.tera.feature.afterloanquery.service.IAfterLoanQueryService;
import com.tera.sys.service.MybatisBaseService;

@Service("afterLoanQueryService")
public class AfterLoanQueryServiceImpl extends MybatisBaseService<AfterLoanQueryBean> implements IAfterLoanQueryService{

	@Autowired(required=false)
	private AfterLoanQueryDao dao;
	@Override
	public int queryCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryCount(map);
	}

	@Override
	public List<AfterLoanQueryBean> queryList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryList(map);
	}

	@Override
	public PageList<AfterLoanQueryBean> queryPageList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.selectPageList(AfterLoanQueryDao.class, "queryList", params);
	}
}
