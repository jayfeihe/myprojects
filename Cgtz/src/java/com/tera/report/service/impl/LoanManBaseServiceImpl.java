package com.tera.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.dao.LoanManBaseDao;
import com.tera.report.model.LoanManBaseQBean;
import com.tera.report.service.ILoanManBaseService;
import com.tera.sys.service.MybatisBaseService;

/**
 * 借款人基本情况表Service
 * @author QYANZE
 *
 */
@Service("loanManBaseService")
public class LoanManBaseServiceImpl extends MybatisBaseService<LoanManBaseQBean> implements ILoanManBaseService {

	@Autowired
	private LoanManBaseDao dao;
	
	/** (non-Javadoc)
	 * @see com.tera.report.service.ILoanManBaseService#queryList(java.util.Map)
	 */
	@Override
	public List<LoanManBaseQBean> queryList(Map<String,Object> queryMap) {
		return dao.queryList(queryMap);
	}
	
	/** (non-Javadoc)
	 * @see com.tera.report.service.ILoanManBaseService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<LoanManBaseQBean> queryPageList(Map<String, Object> params) {
		return this.selectPageList(LoanManBaseDao.class, "queryList", params);
	}
}
