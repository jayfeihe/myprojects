/**
 * 
 */
package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.UserPaymentOrder;

/**
 * 用户主动还款服务接口(请求第三方)
 * @author zhaohefei
 *
 */
public interface UserPayOrderBySelfService {
	
	
	/**
	 * 用户请求发起还款
	 * @function:(说明)
	 * @create time:2015年9月18日
	 * @author zhaohefei
	 */
	public Map<String,Object> UserRequestPay(UserPaymentOrder  paymentOrder,Map<String,Object> retMap) ;
	
	
	/** 
	* queryPayOrderSelfResult<br/> 
	*  TODO(查询主动付款是否成功) 
	* @param paymentOrderParam
	* @return
	* @author zhaohefei
	* @2015年12月13日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String,Object> queryPayOrderSelfResult(String paymentOrderId )throws Exception;



}
