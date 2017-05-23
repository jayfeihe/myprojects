package com.hikootech.mqcash.dao;

import com.hikootech.mqcash.po.LogUserInfo;
import com.hikootech.mqcash.po.SmLog;
import com.hikootech.mqcash.po.UserLoginRecord;

public interface LogDAO {

	/** 
	* addLogUserInfo<br/> 
	*  TODO(修改个人信息日志) 
	* @param logUserInfo 
	* @author zhaohefei
	* @return void	返回类型 
	*/
	public void addLogUserInfo(LogUserInfo logUserInfo)throws Exception;
	
	/**  
	 * insertSmOrder(插入短信记录表)  
	 * @param smsMap   
	 * void 
	 * @create time： Nov 16, 2015 1:50:04 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertSmLog(SmLog smLog)throws Exception;
	
	/** 
	* addUserLoginRecord<br/> 
	*  TODO(增加登陆日志) 
	* @param userLoginRecord 
	* @author zhaohefei
	* @return void	返回类型 
	*/
	public void addUserLoginRecord(UserLoginRecord userLoginRecord)throws Exception;
	
}
