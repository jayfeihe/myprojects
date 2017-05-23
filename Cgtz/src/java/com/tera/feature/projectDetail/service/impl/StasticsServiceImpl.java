package com.tera.feature.projectDetail.service.impl;



import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.feature.projectDetail.dao.ProjectInfoDetailDao;
import com.tera.feature.projectDetail.dao.StasticsDao;
import com.tera.feature.projectDetail.model.ProjectDetail;
import com.tera.feature.projectDetail.model.StasticsBean;
import com.tera.feature.projectDetail.service.IStasticsService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 
 * T_ProjectDetail服务类
 * <b>功能：</b>ProjectDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("stasticsService")
public class StasticsServiceImpl extends MybatisBaseService<StasticsBean> implements IStasticsService{

	private final static Logger log = Logger.getLogger(StasticsServiceImpl.class);

	@Autowired(required=false)
    private StasticsDao dao;
	
	public PageList<StasticsBean> queryMonDealsPageList(Map<String, Object> params) {
		return this.selectPageList(StasticsDao.class, "queryMonDealsList", params);
	}
	
	public PageList<StasticsBean> queryDealsPageList(Map<String, Object> params) {
		return this.selectPageList(StasticsDao.class, "queryDealsList", params);
	}
	
	public PageList<StasticsBean> querySaveDealsPageList(Map<String, Object> params) {
		return this.selectPageList(StasticsDao.class, "querySaveDealsList", params);
	}

	@Override
	public List<StasticsBean> queryMonDealsList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryMonDealsList(map);
	}

	@Override
	public List<StasticsBean> queryDealsList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryDealsList(map);
	}

	@Override
	public List<StasticsBean> querySaveDealsList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.querySaveDealsList(map);
	}
	
}
