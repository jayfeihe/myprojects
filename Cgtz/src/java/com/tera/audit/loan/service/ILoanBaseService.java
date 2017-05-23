package com.tera.audit.loan.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;

public interface ILoanBaseService {

	void add(LoanBase... objs) throws Exception;

	void update(LoanBase obj) throws Exception;

	void updateOnlyChanged(LoanBase obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<LoanBase> queryList(Map<String, Object> map) throws Exception;

	LoanBase queryByKey(Object id) throws Exception;

	LoanBase queryByLoanId(Object loanId) throws Exception;

	int queryBusinessCount(Map<String, Object> map) throws Exception;

	List<LoanBase> queryBusinessList(Map<String, Object> map) throws Exception;
	
	PageList<LoanBase> queryPageList(Map<String, Object> params);

	/**
	 * 申请操作
	 * @param formBean
	 * @param loginId
	 * @param orgId
	 * @throws Exception 
	 */
	LoanBaseJsonMsg loanAppProcess(AppFormBean formBean, String loginId, String orgId) throws Exception;

}