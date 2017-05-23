package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.CreditReport;

/**
 * 
 * 信用贷款申请人行信息DAO
 * <b>功能：</b>CreditReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:53:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseCreditReportDao {
		
	public void add(CreditReport obj);	
	
	public void update(CreditReport obj);
	
	public void updateOnlyChanged(CreditReport obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditReport> queryList(Map<String, Object> map);

	public CreditReport queryByKey(Object obj);

}
