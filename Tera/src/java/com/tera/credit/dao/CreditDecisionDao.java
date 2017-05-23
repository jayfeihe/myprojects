package com.tera.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.credit.model.CreditDecision;

/**
 * 
 * 信用贷款决策表DAO
 * <b>功能：</b>CreditDecisionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:02:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CreditDecisionDao {
		
	public void add(CreditDecision obj);	
	
	public void update(CreditDecision obj);
	
	public void updateOnlyChanged(CreditDecision obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditDecision> queryList(Map<String, Object> map);

	public CreditDecision queryByKey(Object obj);

}
