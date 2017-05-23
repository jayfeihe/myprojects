package com.tera.product.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.product.dao.ProductDao;
import com.tera.product.model.Product;

/**
 * 
 * <br>
 * <b>功能：</b>ProductDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-08 08:57:47<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("productService")
public class ProductService<T> {

	private final static Logger log = Logger.getLogger(ProductService.class);

	@Autowired(required=false)
    private ProductDao<T> dao;

	@Transactional
	public void add(T... ts)  throws Exception {
		if(ts == null || ts.length < 1){
			return;
		}
		for(T t : ts ){
			dao.add(t);
		}
	}
	
	@Transactional
	public void update(T t)  throws Exception {
		dao.update(t);
	}
	
	@Transactional
	public void updateOnlyChanged(T t)  throws Exception {
		dao.updateOnlyChanged(t);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<T> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public T queryByKey(Object id) throws Exception {
		return (T)dao.queryByKey(id);
	}
	public Product queryByName(String name){
		return dao.queryByName(name);
	}
	
	public Product queryProductByLendAppId(String lendAppId){
		return dao.queryProductByLendAppId(lendAppId);
	}

	public List<Product> queryListByCooperation(Map<String, Object> map) {
		return dao.queryListByCooperation(map);
	} 
}
