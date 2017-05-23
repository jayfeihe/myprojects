package com.tera.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.credit.model.CreditContact;
import com.tera.credit.model.CreditContactHistory;

/**
 * 
 * 信用借款申请联系人表DAO
 * <b>功能：</b>CreditContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:22:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CreditContactDao {
		
	public void add(CreditContact obj);	
	
	public void update(CreditContact obj);
	
	public void updateOnlyChanged(CreditContact obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditContact> queryList(Map<String, Object> map);

	public List<CreditContact> queryListOrderByRelation(Map<String, Object> map);
	
	public List<CreditContactHistory> getRelationList(Object map); 

	public CreditContact queryByKey(Object obj);
}
