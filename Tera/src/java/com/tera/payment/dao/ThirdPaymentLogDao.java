package com.tera.payment.dao;

import java.util.List;
import java.util.Map;
import com.tera.payment.model.ThirdPaymentLog;
/**
 * 
 * <br>
 * <b>功能：</b>ThirdPaymentLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-20 14:54:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ThirdPaymentLogDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);

	public T queryByKey(Object t);
	
	public ThirdPaymentLog queryByOrderNo(String orderNo);
	
	public List<T> queryUnfinishedList();

}
