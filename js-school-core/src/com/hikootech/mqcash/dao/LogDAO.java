package com.hikootech.mqcash.dao;

import com.hikootech.mqcash.po.SmLog;
import com.hikootech.mqcash.po.UserOverDueLog;

/**
	* 此类描述的是：日志记录Dao层
	* @author: zhaohefei
	* @version: 2015年12月12日 上午10:43:41
	*/
	
public interface LogDAO {
	
	
		/**addOverDueErrorLog
		* 此方法描述的是：记录逾期数据失败日志
		* @author: zhaohefei
		* @version: 2015年12月12日 上午10:44:21
		*/
	public void addOverDueErrorLog(UserOverDueLog overDueLog);

	/** 
	* insertSmLog<br/> 
	*  TODO(增加发送短息 记录日志) 
	* @param smLog void
	* @author zhaohefei
	* @2015年12月16日
	* @return void	返回类型 
	*/
	public	void insertSmLog(SmLog smLog);
}
