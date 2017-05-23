package com.tera.audit.loan.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.dao.LoanAppDao;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.sys.service.MybatisBaseService;
import com.tera.util.MathUtils;

/**
 * 
 * T_LOAN_APP服务类
 * <b>功能：</b>LoanAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:52:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("loanAppService")
public class LoanAppServiceImpl extends MybatisBaseService<LoanApp> implements ILoanAppService {

	private final static Logger log = Logger.getLogger(LoanAppServiceImpl.class);

	@Autowired(required=false)
    private LoanAppDao dao;

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#add(com.tera.audit.loan.model.LoanApp)
	 */
	@Override
	@Transactional
	public void add(LoanApp... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(LoanApp obj : objs ){
			dao.add(obj);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#update(com.tera.audit.loan.model.LoanApp)
	 */
	@Override
	@Transactional
	public void update(LoanApp obj)  throws Exception {
		dao.update(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#updateOnlyChanged(com.tera.audit.loan.model.LoanApp)
	 */
	@Override
	@Transactional
	public void updateOnlyChanged(LoanApp obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#delete(java.lang.Object)
	 */
	@Override
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#deleteCommonLoan(java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteCommonLoan(String loanId) throws Exception {
		dao.deleteCommonLoan(loanId);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#queryCount(java.util.Map)
	 */
	@Override
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#queryList(java.util.Map)
	 */
	@Override
	public List<LoanApp> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#queryPageList(java.util.Map)
	 */
	@Override
	public PageList<LoanApp> queryPageList(Map<String, Object> params) {
		return this.selectPageList(LoanAppDao.class, "queryList", params);
	}

	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#queryByKey(java.lang.Object)
	 */
	@Override
	public LoanApp queryByKey(Object id) throws Exception {
		return (LoanApp)dao.queryByKey(id);
	}
	
	/* (non-Javadoc)
	 * @see com.tera.audit.loan.service.ILoanAppService#saveCommonLoan(com.tera.audit.loan.model.form.AppFormBean, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public LoanBaseJsonMsg saveCommonLoan(AppFormBean formBean, String loginId, String orgId) throws Exception {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		LoanApp commonLoan = formBean.getAppTypeLoan();
		if (commonLoan != null) {
//			commonLoan.setYearIncome(MathUtils.mul(commonLoan.getYearIncome(), 10000.00)); // 年收入单位转换
			if (0 == commonLoan.getId()) {
				commonLoan.setMainFlag("0"); // 共同借款人
				commonLoan.setCreateTime(nowTime);
				commonLoan.setUpdateTime(nowTime);
				this.add(commonLoan);
			} else {
				commonLoan.setUpdateTime(nowTime);
				this.updateOnlyChanged(commonLoan);
			}
		}
		return new LoanBaseJsonMsg(commonLoan.getId(), commonLoan.getLoanId(), true, "操作成功");
	}
}
