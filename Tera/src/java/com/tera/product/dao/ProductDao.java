package com.tera.product.dao;

import java.util.List;
import java.util.Map;

import com.tera.product.model.Product;

/**
 * 
 * <br>
 * <b>功能：</b>ProductDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-08 08:57:47<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ProductDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);

	public T queryByKey(Object t);
	
	public Product queryByName(String name);
	
	public Product queryProductByLendAppId(String lendAppId);

	public List<Product> queryListByCooperation(Map<String, Object> map);

}
