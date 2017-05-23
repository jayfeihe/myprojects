package com.tera.product.dao;

import java.util.List;
import java.util.Map;

import com.tera.product.model.ProductFeeRate;

/**
 * 
 * 产品服务费率表DAO
 * <b>功能：</b>ProductFeeRateDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-11 14:28:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ProductFeeRateDao {
		
	public void add(ProductFeeRate obj);	
	
	public void update(ProductFeeRate obj);
	
	public void updateOnlyChanged(ProductFeeRate obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<ProductFeeRate> queryList(Map<String, Object> map);

	public ProductFeeRate queryByKey(Object obj);

}
