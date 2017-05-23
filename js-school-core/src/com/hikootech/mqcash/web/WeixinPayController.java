package com.hikootech.mqcash.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.service.WxNativePayService;
import com.hikootech.mqcash.util.EntityUtils;

/**
 * 微信支付Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/wxPay")
public class WeixinPayController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxNativePayService wxNativePayService;

	/**
	 * 获取二维码地址-模式一（先生成二维码，然后生成订单）
	 * 
	 * product_id--商品id
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getQRCodeUrlModel1.do")
	public void getQRCodeUrlModel1(HttpServletRequest request) {
		Map<String, String> busMap = (Map<String, String>) request.getAttribute("busMap");
		logger.info("核心获取二维码url（模式一）接口接收参数：" + busMap);

		Map<String, String> retBusMap = new HashMap<String, String>();
		try {
			String code_url = wxNativePayService.getQRCodeUrlModel1(busMap.get("product_id"));
			retBusMap.put("code_url", code_url);
		} catch (Exception e) {
			logger.error("核心获取二维码url（模式一）接口异常:" + e.getMessage(), e);
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "获取二维码url（模式一）异常");
		}
		request.setAttribute("retBusMap", retBusMap);
	}

	/**
	 * 获取二维码地址-模式二（生成订单获取二维码地址）
	 * 
	 * body--商品描述                   
     * detail--商品详情-非必需               
     * attach--附加数据 -非必需              
     * out_trade_no--商户订单号                  
     * fee_type--货币类型-非必需               
     * total_fee--总金额                   
     * time_start--交易起始时间-非必需             
     * time_expire--交易结束时间-非必需             
     * goods_tag--商品标记-非必需               
     * notify_url--通知地址                   
     * product_id--商品ID-NATIVE(扫一扫支付)必需   
     * limit_pay--指定支付方式-非必需             
     * openid--用户标识-非必需               
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getQRCodeUrlModel2.do")
	public void getQRCodeUrlModel2(HttpServletRequest request) {
		Map<String, String> busMap = (Map<String, String>) request.getAttribute("busMap");
		logger.info("核心获取二维码url（模式二）接口接收参数：" + busMap);

		Map<String, String> retBusMap = new HashMap<String, String>();
		try {
			String code_url = wxNativePayService.getQRCodeUrlModel2(busMap);
			retBusMap.put("code_url", code_url);
		} catch (Exception e) {
			logger.error("核心获取二维码url（模式二）接口异常:" + e.getMessage(), e);
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "获取二维码url（模式二）异常");
		}
		request.setAttribute("retBusMap", retBusMap);
	}

	/**
	 * 通过微信查询订单
	 * 
	 * transaction_id--微信订单号
	 * out_trade_no--商户订单号
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryOrder.do")
	public void queryOrder(HttpServletRequest request) {
		Map<String, String> busMap = (Map<String, String>) request.getAttribute("busMap");
		logger.info("核心查询微信订单接口接收参数：" + busMap);
		
		String transaction_id = busMap.get("transaction_id"); // 微信订单号
		String out_trade_no = busMap.get("out_trade_no"); // 商户订单号
		Map<String, String> retBusMap = new HashMap<String, String>();
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			if (EntityUtils.isNotEmpty(transaction_id)) {
				paramMap.put("transaction_id", transaction_id);
			} else {
				paramMap.put("out_trade_no", out_trade_no);
			}
			retBusMap = this.wxNativePayService.queryOrderInfo(paramMap);
		} catch (Exception e) {
			logger.error("核心查询微信订单接口异常:" + e.getMessage(), e);
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "查询微信订单异常");
		}
		
		request.setAttribute("retBusMap", retBusMap);
	}
	
	/**
	 * 微信支付成功后，微信异步通知地址
	 * 
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = "/notify.do", produces = "application/xml;charset=UTF-8")
	@ResponseBody
	public String notify(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("微信支付成功回调");
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(req.getInputStream(), Charset.forName("utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}

			String receiveXml = buffer.toString();
			logger.info("微信支付成功异步通知回调接受参数：" + receiveXml);

			String resultXml = this.wxNativePayService.notifyProcess(receiveXml);
			logger.info("微信支付成功异步通知回调返回参数：" + resultXml);

			return resultXml;
		} catch (Exception e) {
			logger.error("微信支付成功异步通知回调返回交数据包异常：" + e.getMessage(), e);
		}
		return null;
	}
}
