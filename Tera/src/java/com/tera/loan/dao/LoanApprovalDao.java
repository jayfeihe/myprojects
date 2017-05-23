package com.tera.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.loan.model.LoanApproval;

/**
 * 
 * <br>
 * <b>功能：</b>LoanApprovalDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-09 15:36:08<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LoanApprovalDao {	
		
	public void add(LoanApproval t);	
	
	public void update(LoanApproval t);
	
	public void updateOnlyChanged(LoanApproval t);
		
	public void delete(String addId);	

	public int queryCount(Map<String, Object> map);
	
	public List<LoanApproval> queryList(Map<String, Object> map);

	public LoanApproval queryByKey(String addId);

}
