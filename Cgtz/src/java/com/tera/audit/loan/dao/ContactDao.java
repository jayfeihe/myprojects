package com.tera.audit.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.loan.model.Contact;

/**
 * 
 * T_CONTACTDAO
 * <b>功能：</b>ContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 11:10:05<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ContactDao {
		
	public void add(Contact obj);	
	
	public void update(Contact obj);
	
	public void updateOnlyChanged(Contact obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Contact> queryList(Map<String, Object> map);

	public Contact queryByKey(Object obj);

	public List<Contact> queryByLoanId(String loanId);

}
