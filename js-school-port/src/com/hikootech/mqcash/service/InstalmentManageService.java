package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.po.UserInfo;

public interface InstalmentManageService {

	/** 
	* queryPlansInfo<br/> 
	*  TODO(根据账单id查询还款计划信息，已经当期计划，和未还期数等信息,（计划状态除了逾期状态，其他都是取自数据库状态）) 
	* @param instalmentId  账单id
	* @param planStatusArray  若不为null，则将查询到的还款计划list去除该参数传递值中的其他状态；若为null，则返回全部的
	* @return 
	* @author zhaohefei
	* @2015年12月13日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String,Object> queryPlansInfo(String instalmentId, String userId,int[] planStatusArray);
	
	/** 
	* payNow<br/> 
	*  TODO( 确认是否可以跳转到收银台画面) 
	* @param totalAmount 前台传值 还款总金额
	* @param payPlanIds 前台传值 还款计划id字符串  用逗号分隔 
	* @param instalmentId  前台传值 账单id
	* @param userInfo 缓存用户信息
	* @return 
	* @author zhaohefei
	* @2015年12月13日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> payNow(String totalAmount,String payPlanIds,String instalmentId,UserInfo userInfo);
	
	/** 
	* surePayBank<br/> 
	*  TODO(确认是否可以跳转至中金支付画面) 
	* @param relationBankId  关联银行id
	* @param paymentOrder  还款订单信息
	* @return
	* @author zhaohefei
	* @2015年12月13日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> surePayBank(String relationBankId,Map<String,Object> payInfoMap,UserInfo userInfo);

	/**
	 * 微信支付
	 * 
	 * @param payInfoMap
	 * @param userInfo
	 * @return
	 */
	public Map<String, Object> sureWxPay(Map<String,Object> payInfoMap,UserInfo userInfo);
}
