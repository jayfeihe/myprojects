package com.tera.audit.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.loan.model.LoanApp;

/**
 * 
 * T_LOAN_APPDAO
 * <b>功能：</b>LoanAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:52:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LoanAppDao {
		
	public void add(LoanApp obj);	
	
	public void update(LoanApp obj);
	
	public void updateOnlyChanged(LoanApp obj);
		
	public void delete(Object obj);
	
	public void deleteCommonLoan(String loanId);

	public int queryCount(Map<String, Object> map);
	
	public List<LoanApp> queryList(Map<String, Object> map);

	public LoanApp queryByKey(Object obj);
}
