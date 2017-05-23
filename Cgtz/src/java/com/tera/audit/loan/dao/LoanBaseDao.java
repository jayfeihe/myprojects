package com.tera.audit.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.loan.model.LoanBase;

/**
 * 
 * T_LOAN_BASEDAO
 * <b>功能：</b>LoanBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:52:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LoanBaseDao {
		
	public void add(LoanBase obj);	
	
	public void update(LoanBase obj);
	
	public void updateOnlyChanged(LoanBase obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<LoanBase> queryList(Map<String, Object> map);

	public LoanBase queryByKey(Object obj);

	public int queryBusinessCount(Map<String, Object> map);
	
	public List<LoanBase> queryBusinessList(Map<String, Object> map);

	public LoanBase queryByLoanId(Object loanId);
}
