package com.tera.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.credit.model.CreditSummary;

/**
 * 
 * 信用贷款申请影像摘要DAO
 * <b>功能：</b>CreditSummaryDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CreditSummaryDao {
		
	public void add(CreditSummary obj);	
	
	public void update(CreditSummary obj);
	
	public void updateOnlyChanged(CreditSummary obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditSummary> queryList(Map<String, Object> map);

	public CreditSummary queryByKey(Object obj);

}
