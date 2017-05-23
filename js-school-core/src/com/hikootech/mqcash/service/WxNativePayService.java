package com.hikootech.mqcash.service;

import java.util.Map;

/**
 * 微信扫一扫支付Service接口
 * 
 * @author QYANZE
 *
 */
public interface WxNativePayService {

	/**
	 * 获取二维码链接--模式一
	 * 用户自己根据微信url规则先生成
	 * 
	 * @param product_id 业务商品id
	 * @return
	 */
	String getQRCodeUrlModel1(String product_id) throws Exception;
	
	/**
	 * 获取二维码生成链接url-- 模式二
	 * 通过调用接口回去二维码地址
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	String getQRCodeUrlModel2(Map<String, String> paramMap) throws Exception;

	/**
	 * 获取订单相关信息
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	Map<String, String> queryOrderInfo(Map<String, String> paramMap) throws Exception;

	/**
	 * 微信支付成功异步通知处理
	 * 
	 * @param receiveXml
	 * @return
	 * @throws Exception
	 */
	String notifyProcess(String receiveXml) throws Exception;;
}
