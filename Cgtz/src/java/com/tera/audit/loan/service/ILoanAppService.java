package com.tera.audit.loan.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;

public interface ILoanAppService {

	void add(LoanApp... objs) throws Exception;

	void update(LoanApp obj) throws Exception;

	void updateOnlyChanged(LoanApp obj) throws Exception;

	void delete(Object... ids) throws Exception;

	/**
	 * 删除共同借款人
	 * @param loanId
	 * @throws Exception
	 */
	void deleteCommonLoan(String loanId) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<LoanApp> queryList(Map<String, Object> map) throws Exception;
	
	PageList<LoanApp> queryPageList(Map<String, Object> params);

	LoanApp queryByKey(Object id) throws Exception;

	/**
	 * 保存共同借款人信息
	 * @param formBean
	 * @param loginId
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	LoanBaseJsonMsg saveCommonLoan(AppFormBean formBean, String loginId, String orgId) throws Exception;

}