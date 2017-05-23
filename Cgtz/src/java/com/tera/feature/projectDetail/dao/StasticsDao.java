package com.tera.feature.projectDetail.dao;


import java.util.List;
import java.util.Map;

import com.tera.feature.projectDetail.model.StasticsBean;

/**
 * 
 * Stastics
 * <b>功能：</b>StasticsDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface StasticsDao {
		
	public List<StasticsBean> queryMonDealsList(Map<String, Object> map);
	
	public List<StasticsBean> queryDealsList(Map<String, Object> map);
	
	public List<StasticsBean> querySaveDealsList(Map<String, Object> map);

}
