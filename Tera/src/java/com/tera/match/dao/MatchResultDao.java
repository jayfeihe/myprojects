package com.tera.match.dao;

import java.util.List;
import java.util.Map;

import com.tera.match.model.MatchResult;

/**
 * 
 * <br>
 * <b>功能：</b>MatchResultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-18 15:22:35<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface MatchResultDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);
	
	public List<T> queryListInSign(Map<String, Object> map);

	public T queryByKey(Object t);
	
	public List<MatchResult> getResultByAppId(Map<String, Object> map);

	public List<MatchResult> queryByLendAppId(String lendAppId);
	
	public List<MatchResult> queryByLoanAppId(String loanAppId);
	
	public List<MatchResult> queryListByLoanAppId(Map<String, Object> map);
	

}
