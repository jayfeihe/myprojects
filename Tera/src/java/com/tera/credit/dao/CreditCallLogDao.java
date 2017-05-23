package com.tera.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.credit.model.CreditCallLog;

/**
 * 
 * 信用贷款面审调查通话记录DAO
 * <b>功能：</b>CreditCallLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:55:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CreditCallLogDao {
		
	public void add(CreditCallLog obj);	
	
	public void update(CreditCallLog obj);
	
	public void updateOnlyChanged(CreditCallLog obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditCallLog> queryList(Map<String, Object> map);

	public CreditCallLog queryByKey(Object obj);

}
