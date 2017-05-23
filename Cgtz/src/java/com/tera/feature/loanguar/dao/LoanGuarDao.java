package com.tera.feature.loanguar.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.loanguar.model.LoanGuar;

/**
 * 
 * T_LOAN_GUARDAO
 * <b>功能：</b>LoanGuarDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-06 13:51:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LoanGuarDao {
		
	public void add(LoanGuar obj);	
	
	public void update(LoanGuar obj);
	
	public void updateOnlyChanged(LoanGuar obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<LoanGuar> queryList(Map<String, Object> map);

	public LoanGuar queryByKey(Object obj);

	public List<LoanGuar> queryListByLoanId(Map<String, Object> map);
}
