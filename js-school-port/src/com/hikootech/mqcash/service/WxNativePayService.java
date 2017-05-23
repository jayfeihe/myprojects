package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.dto.WxUnifiedOrderDTO;

/**
 * 微信扫一扫支付接口
 * 
 * @author QYANZE
 *
 */
public interface WxNativePayService {

	/**
	 * 获取二维码链接--模式一
	 * 
	 * @param product_id
	 * @return
	 * @throws Exception
	 */
	String getQRCodeUrlModel1(String product_id) throws Exception;
	
	/**
	 * 获取二维码链接--模式二
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	String getQRCodeUrlModel2(WxUnifiedOrderDTO order) throws Exception;

	/**
	 * 查询订单
	 * 
	 * @param out_trade_no 商户订单号
	 * @return
	 */
	Map<String, String> queryOrder(String out_trade_no);
}
