package com.tera.feature.afterloanquery.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.afterloanquery.model.AfterLoanQueryBean;

/**
 * 
 *功能:AfterLoanQueryDao
 *时间:2016年3月7日上午10:13:43
 *@author Ldh
 */
 
public interface AfterLoanQueryDao {
    //贷后综合查询
	public int queryCount(Map<String, Object> map);
	
	public List<AfterLoanQueryBean> queryList(Map<String, Object> map);

}
