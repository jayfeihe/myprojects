package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.dao.BranchStockDao;
import com.tera.report.model.BranchStockQBean;
import com.tera.report.service.IBranchStockService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 分公司存量统计Service
 * @author QYANZE
 *
 */
@Service("branchStockService")
public class BranchStockServiceImpl extends MybatisBaseService<BranchStockQBean> implements IBranchStockService {

	@Autowired
	private BranchStockDao dao;
	
	/** (non-Javadoc)
	 * @see com.tera.report.service.impl.IBranchStockService#queryList(java.util.Map)
	 */
	@Override
	public List<BranchStockQBean> queryList(Map<String,Object> queryMap) {
		return dao.queryList(queryMap);
	}
	
	/** (non-Javadoc)
	 * @see com.tera.report.service.impl.IBranchStockService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<BranchStockQBean> queryPageList(Map<String,Object> params) {
		return this.selectPageList(BranchStockDao.class, "queryList", params);
	}
}
