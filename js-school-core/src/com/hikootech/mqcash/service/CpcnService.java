package com.hikootech.mqcash.service;

import java.util.Map;

import payment.api.notice.Notice1318Request;
import payment.api.notice.Notice1363Request;

import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.UserPaymentOrder;

public interface CpcnService {
	
	/** 
	* @Title notice1318 
	* @Description 中金市场订单支付状态变更通知tx1318
	* @param 参数列表 
	* @param nr
	* @throws MQException 
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, Object> notice1318(Notice1318Request nr) throws MQException;
	
	/** 
	* @Title notice1363 
	* @Description 中金市场订单代收结果通知tx1363
	* @param 参数列表 
	* @param nr
	* @throws MQException 
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, Object> notice1363(Notice1363Request nr) throws MQException;
	
	/** 
	* @Title request1362 
	* @Description 市场订单单笔代收交易查询tx1362 
	* @param 参数列表 
	* @param paymentOrder
	* @return
	* @throws MQException 
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, Object> request1362(UserPaymentOrder paymentOrder) throws MQException;
	
	/** 
	* @Title request1311 
	* @Description 调用中金tx1311生成：市场订单支付（直通车）的支付URL和参数
	* @param 参数列表 
	* @param paramMap
	* @return
	* @throws MQException 
	* @return 返回类型 Map<String,String>	
	*/
	public Map<String, String> request1311(Map<String, String> paramMap) throws MQException;
	
	/** 
	* @Title request1320 
	* @Description 市场订单支付查询tx1320
	* @param 参数列表 
	* @param paramMap
	* @return
	* @throws MQException 
	* @return 返回类型 Map<String,String>	
	*/
	public Map<String, String> request1320(Map<String, String> paramMap) throws MQException;
	
}
