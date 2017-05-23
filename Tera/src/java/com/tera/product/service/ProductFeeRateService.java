package com.tera.product.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.product.dao.ProductFeeRateDao;
import com.tera.product.model.ProductFeeRate;

/**
 * 
 * 产品服务费率表服务类
 * <b>功能：</b>ProductFeeRateDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-11 14:28:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("productFeeRateService")
public class ProductFeeRateService {

	private final static Logger log = Logger.getLogger(ProductFeeRateService.class);

	@Autowired(required=false)
    private ProductFeeRateDao dao;

	@Transactional
	public void add(ProductFeeRate... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(ProductFeeRate obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(ProductFeeRate obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(ProductFeeRate obj)  throws Exception {
		dao.updateOnlyChanged(obj);
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
	
	public List<ProductFeeRate> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public ProductFeeRate queryByKey(Object id) throws Exception {
		return (ProductFeeRate)dao.queryByKey(id);
	}
	
}
