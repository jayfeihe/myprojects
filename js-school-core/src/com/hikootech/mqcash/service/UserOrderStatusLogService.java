package com.hikootech.mqcash.service;

import com.hikootech.mqcash.po.UserOrderStatusLog;

/** 
* @ClassName UserOrderStatusLogService 
* @Description TODO(用户订单状态变更日志表) 
* @author HAI DA
* @date Dec 31, 2015 2:21:15 PM 
*  
*/
public interface UserOrderStatusLogService {
	
	/** 
	* @Title insertUserOrderStatusLog 
	* @Description TODO(插入用户订单状态变更日志表) 
	* @param 参数列表
	* @param orderStatusLog
	* @throws Exception 设定文件 
	* @return int	返回类型 
	* @throws 
	*/
	public int insertUserOrderStatusLog(UserOrderStatusLog orderStatusLog) throws Exception;

}
