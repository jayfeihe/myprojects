package com.hikootech.mqcash.service;

import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserTotalInfo;

public interface UserService {
	
	/**  
	 * queryUserTotalInfo(查询临时表用户信息)  
	 * @param userTotalInfo
	 * @return   
	 * UserTotalInfo 
	 * @create time： Nov 20, 2015 7:05:36 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserTotalInfo queryUserTotalInfo(UserTotalInfo userTotalInfo);
	
}
