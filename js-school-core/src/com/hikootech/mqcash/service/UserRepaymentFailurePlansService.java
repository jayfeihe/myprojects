package com.hikootech.mqcash.service;

import java.util.Date;

import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.UserRepaymentFailurePlans;

/** 
* @ClassName UserRepaymentFailurePlansService 
* @Description 还款失败还款计划列表业务服务
* @author 余巍 yuweiqwe@126.com 
* @date 2016年2月26日 上午11:06:26 
*  
*/
public interface UserRepaymentFailurePlansService {
	
	/** 
	* @Title insertUserRepaymentFailurePlans 
	* @Description 把还款失败还款计划插入到列表中去
	* @param 参数列表 
	* @param failurePlans
	* @throws Exception 
	* @return 返回类型 void	
	*/
	public void insertUserRepaymentFailurePlans(UserRepaymentFailurePlans failurePlans) throws MQException;
	
	/** 
	* @Title insertUserRepaymentFailurePlans 
	* @Description 把还款失败还款计划插入到列表中去
	* @param 参数列表 
	* @param failurePlans
	* @throws Exception 
	* @return 返回类型 void	
	*/
	public void insertUserRepaymentFailurePlans(String repaymentPlansId, Date failureTime) throws MQException;

}
