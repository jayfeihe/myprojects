package com.hikootech.mqcash.dao;

import com.hikootech.mqcash.po.UserRepaymentOrder;

/** 
* @ClassName UserRepaymentOrderDAO 
* @Description TODO(用户还款成功的订单DAO) 
* @author 余巍 yuweiqwe@126.com 
* @date 2016年2月22日 下午4:55:02 
*  
*/
public interface UserRepaymentOrderDAO {
	
	/** 
	* @Title addUserRepaymentOrderFromPayment 
	* @Description 从paymentOrder表中查询并插入repaymentOrder中
	* @param 参数列表 
	* @param userRepaymentOrder 
	* @return 返回类型 void	
	*/
	public void addUserRepaymentOrderFromPayment(UserRepaymentOrder userRepaymentOrder);
	
}
