package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.dao.BranchDealDao;
import com.tera.report.model.BranchDealQBean;
import com.tera.report.service.IBranchDealService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 分公司成交量Service
 * @author QYANZE
 *
 */
@Service("branchDealService")
public class BranchDealServiceImpl extends MybatisBaseService<BranchDealQBean> implements IBranchDealService {

	@Autowired
	private BranchDealDao dao;
	
	/** (non-Javadoc)
	 * @see com.tera.report.service.impl.IBranchDealService#queryList(java.util.Map)
	 */
	@Override
	public List<BranchDealQBean> queryList(Map<String,Object> queryMap) {
		return dao.queryList(queryMap);
	}
	
	/** (non-Javadoc)
	 * @see com.tera.report.service.impl.IBranchDealService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<BranchDealQBean> queryPageList(Map<String,Object> params) {
		return this.selectPageList(BranchDealDao.class, "queryList", params);
	}
}
