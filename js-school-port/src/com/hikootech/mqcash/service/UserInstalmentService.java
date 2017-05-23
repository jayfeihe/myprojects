package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.UserInstalment;

/**
 * @author yuwei
 * 2015年8月6日
 * 分期贷款借口
 */
public interface UserInstalmentService {
	
	/** 
	* queryInstalmentById<br/> 
	*  TODO(根据账单id和用户id查询账单信息) 
	* @param instalmentId
	* @return 
	* @author zhaohefei
	* @2015年12月13日
	* @return UserInstalment	返回类型 
	*/
	public UserInstalment queryInstalmentById(Map<String,Object> queryMap) ;
	
}
