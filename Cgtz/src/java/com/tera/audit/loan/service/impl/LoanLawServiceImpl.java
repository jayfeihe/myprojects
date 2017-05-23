package com.tera.audit.loan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.dao.LoanLawDao;
import com.tera.audit.loan.model.form.LoanLawQBean;
import com.tera.audit.loan.service.ILoanLawService;
import com.tera.sys.service.MybatisBaseService;

/**	诉讼Service
 * @author QYANZE
 *
 */
@Service("loanLawService")
public class LoanLawServiceImpl extends MybatisBaseService<LoanLawQBean> implements ILoanLawService {

	@Autowired
	private LoanLawDao dao;
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanLawService#queryList(java.util.Map)
	 */
	@Override
	public List<LoanLawQBean> queryListByLoanId(Map<String, Object> map) {
		return dao.queryListByLoanId(map);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanLawService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<LoanLawQBean> queryPageList(Map<String, Object> params) {
		return this.selectPageList(LoanLawDao.class, "queryListByLoanId", params);
	}
}
