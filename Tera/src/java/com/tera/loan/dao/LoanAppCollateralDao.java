package com.tera.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.loan.model.LoanAppCollateral;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppCollateralDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-07 12:18:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LoanAppCollateralDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);

	public T queryByKey(Object t);
	
	public List<LoanAppCollateral> queryListByAppId(Map<String, Object> map);

	


}
