package com.tera.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.loan.model.LoanAppContact;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppContactDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-13 16:20:11<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LoanAppContactDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);

	public T queryByKey(Object t);

	List<LoanAppContact> queryCollList(Map<String, Object> map);
	
	void deleteByAppIdAndCollateralSeqFlag(Map<String, Object> map);
	
	
}
