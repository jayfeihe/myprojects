package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.UserProtocol;

public interface UserProtocolService {

	/** 
	* addUserProtocol<br/> 
	*  TODO(插入合同信息) 
	* @param userProtocol void
	* @author zhaohefei
	* @2015年12月18日
	* @return void	返回类型 
	*/
	public void addUserProtocol(UserProtocol userProtocol);
	
	/** 
	* queryUserProtocolByInstalmentId<br/> 
	*  TODO(根据分期单号和用户id查询合同信息) 
	* @param queryMap
	* @return List<UserProtocol>
	* @author zhaohefei
	* @2015年12月18日
	* @return List<UserProtocol>	返回类型 
	*/
	public List<UserProtocol> queryUserProtocolByInstalmentId(Map<String,Object> queryMap);
}
