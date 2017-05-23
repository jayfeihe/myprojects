package com.tera.match.dao;

import java.util.List;
import java.util.Map;

import com.tera.match.model.Lend2match;
import com.tera.match.model.Loan2match;

/**
 * 
 * <br>
 * <b>功能：</b>Loan2matchDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-16 17:37:6<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface Loan2matchDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);
	
	public List<T> queryLockList(Map<String, Object> map);
	
	//杨长收添加
	public int queryLoan2MatchByConditionCount(Map<String, Object> map);
	
	//杨长收添加
	public List<T> queryLoan2MatchByConditionList(Map<String, Object> map);
	
	//杨长收添加(人工撮合审批时)
	public int queryManualMatchVerifyCount(Map<String, Object> map);
	
	//杨长收添加(人工撮合审批时)
	public List<T> queryManualMatchVerifyList(Map<String, Object> map);
	

	public T queryByKey(Object t);

	public List<Loan2match> getLoan2matchByAppId(Map<String, Object> map);
	
	public List<T> queryBasicLockList(Map<String, Object> map);
	
	
	public void updateToLock();
	
	public void updateToUnLock();
	
	public void updateMatchTimes();
	
	
	
	/**
	 * 
	 * @param map
	 * @return
	 * ssx
	 */
	public List<Loan2match> queryMatchManageList(Map<String, Object> map);
	public int queryMatchManageCount(Map<String, Object> map);

}
