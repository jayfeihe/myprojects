package com.tera.feature.search.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.search.dao.AfterLoanSearchDao;
import com.tera.feature.search.model.AfterLoanSearchBean;
import com.tera.feature.search.service.IAfterLoanSearchService;
import com.tera.sys.service.MybatisBaseService;

/**
 *功能:AfterLoanSearchServiceImpl  逾期相关服务
 *时间:2016年3月7日上午10:48:18
 *@author Ldh
 */
@Service("afterLoanSearchService")
public class AfterLoanSearchServiceImpl extends MybatisBaseService<AfterLoanSearchBean> implements IAfterLoanSearchService{

	@Autowired(required=false)
	private AfterLoanSearchDao dao;
	@Override
	public int queryCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryCount(map);
	}

	@Override
	public List<AfterLoanSearchBean> queryList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryList(map);
	}

	@Override
	public PageList<AfterLoanSearchBean> queryPageList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.selectPageList(AfterLoanSearchDao.class, "queryList", params);
	}

	@Override
	public List<AfterLoanSearchBean> queryTaskList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryTaskList(map);
	}
	
	@Override
	public PageList<AfterLoanSearchBean> queryTaskPageList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.selectPageList(AfterLoanSearchDao.class, "queryTaskList", params);
	}

	@Override
	public List<AfterLoanSearchBean> queryRecordList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryRecordList(map);
	}

	@Override
	public PageList<AfterLoanSearchBean> queryRecordPageList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.selectPageList(AfterLoanSearchDao.class, "queryRecordList", params);
	}

	@Override
	public List<AfterLoanSearchBean> queryDetailList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryDetailList(map);
	}

	@Override
	public PageList<AfterLoanSearchBean> queryDetailPageList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.selectPageList(AfterLoanSearchDao.class, "queryDetailList", params);
	}

	@Override
	public int queryLateConCount(Map<String, Object> map)
			throws Exception {
		return dao.queryLateConCount(map);
	}
}
