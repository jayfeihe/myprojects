package com.tera.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.credit.model.CreditInterview;

/**
 * 
 * 信用贷款面审调查表DAO
 * <b>功能：</b>CreditInterviewDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:54:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CreditInterviewDao {
		
	public void add(CreditInterview obj);	
	
	public void update(CreditInterview obj);
	
	public void updateOnlyChanged(CreditInterview obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditInterview> queryList(Map<String, Object> map);

	public CreditInterview queryByKey(Object obj);

}
