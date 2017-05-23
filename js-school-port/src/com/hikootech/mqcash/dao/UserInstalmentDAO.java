package com.hikootech.mqcash.dao;

import java.util.Map;

import com.hikootech.mqcash.po.UserInstalment;

public interface UserInstalmentDAO {
	
	
	/** 
	* queryInstalmentById<br/> 
	*  TODO(根据账单id和用户id查询账单信息) 
	* @param queryMap
	* @return 
	* @author zhaohefei
	* @2015年12月14日
	* @return UserInstalment	返回类型 
	*/
	public UserInstalment queryInstalmentById(Map<String,Object> queryMap)throws Exception;
	
	/** 
	* modifyInstalmentStatus<br/> 
	*  TODO(根据分期单id ，修改分期单状态) 
	* @param map
	* @return int
	* @author zhaohefei
	* @2015年12月16日
	* @return int	返回类型 
	*/
	public int modifyInstalmentStatus(Map<String,Object> map);
}
