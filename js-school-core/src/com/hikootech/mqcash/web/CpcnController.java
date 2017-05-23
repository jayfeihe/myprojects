package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.service.CpcnService;
import com.hikootech.mqcash.service.UserBankCardService;
import com.hikootech.mqcash.service.UserPaymentOrderService;
import com.hikootech.mqcash.util.EntityUtils;

@RequestMapping("/cpcn")
@Controller
public class CpcnController extends BaseController {
	
	private static Logger log = LoggerFactory.getLogger(CpcnController.class);
	
	@Autowired
	private CpcnService cpcnService;
	@Autowired
	private UserBankCardService userBankCardService;
	
	@Autowired
	private UserPaymentOrderService userPaymentOrderService;
	
	@RequestMapping("/createPaymentURL.do")
	@ResponseBody
	public void createPaymentURL(){
		log.info("调用中金tx1311生成：市场订单支付（直通车）的支付URL和参数");
		Map<String, String> busMap = (Map<String, String>)getRequest().getAttribute("busMap");
		
		Map<String, String> retBusMap = new HashMap<String, String>();
		getRequest().setAttribute("retBusMap", retBusMap);
		
		String orderNo = busMap.get("orderNo");
		String paymentNo = busMap.get("paymentNo");
		String amount = busMap.get("amount");
		String fee = busMap.get("fee");
		String payerID = busMap.get("payerID");
		String payerName = busMap.get("payerName");
		String usage = busMap.get("usage");
		String remark = busMap.get("remark");
		String payees = busMap.get("payees");
		String bankID = busMap.get("bankID");
		String notificationURL = busMap.get("notificationURL");
		
		if(EntityUtils.isEmpty(orderNo)){
			log.error("订单号参数为空！");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "订单号参数为空！");
			return;
		}else if(EntityUtils.isEmpty(paymentNo)){
			log.error("支付交易流水号参数为空！");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "支付交易流水号参数为空！");
			return;
		}else if(EntityUtils.isEmpty(amount)){
			log.error("支付金额参数为空！");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "支付金额参数为空！");
			return;
		}else if(EntityUtils.isEmpty(bankID)){
			log.error("付款银行标识参数为空！");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "付款银行标识参数为空！");
			return;
		}else if(EntityUtils.isEmpty(notificationURL)){
			log.error("通知成功地址参数为空！");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "通知成功地址参数为空！");
			return;
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("orderNo", orderNo);
		paramMap.put("paymentNo", paymentNo);
		paramMap.put("amount", amount);
		paramMap.put("fee", fee);
		paramMap.put("payerID", payerID);
		paramMap.put("payerName", payerName);
		paramMap.put("usage", usage);
		paramMap.put("remark", remark);
		paramMap.put("payees", payees);
		paramMap.put("bankID", bankID);
		paramMap.put("notificationURL", notificationURL);
		
		Map<String, String> retMap = null;
		try {
			retMap = cpcnService.request1311(paramMap);
		} catch (MQException e) {
			// TODO Auto-generated catch block
			log.info("调用中金tx1311生成：市场订单支付（直通车）的支付URL和参数出错", e);
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_SYSTEM_EXCEPTION);
			retBusMap.put(ResponseConstants.RETURN_DESC, "调用中金tx1311生成：市场订单支付（直通车）的支付URL和参数出错");
			return;
		}
		
		retBusMap.putAll(retMap);
	}
	
	@RequestMapping("/queryPaymentOrder.do")
	@ResponseBody
	public void queryPaymentOrder(){
		log.info("调用中金tx1320生成：市场订单支付查询");
		Map<String, String> busMap = (Map<String, String>)getRequest().getAttribute("busMap");
		
		Map<String, String> retBusMap = new HashMap<String, String>();
		getRequest().setAttribute("retBusMap", retBusMap);
		
		String paymentNo = busMap.get("paymentNo");
		
		if(EntityUtils.isEmpty(paymentNo)){
			log.error("支付交易流水号参数为空！");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "支付交易流水号参数为空！");
			return;
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("paymentNo", paymentNo);
		
		Map<String, String> retMap = null;
		try {
			retMap = cpcnService.request1320(paramMap);
		} catch (MQException e) {
			// TODO Auto-generated catch block
			log.info("调用中金tx1320生成：市场订单支付查询出错", e);
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_SYSTEM_EXCEPTION);
			retBusMap.put(ResponseConstants.RETURN_DESC, "调用中金tx1320生成：市场订单支付查询出错");
			return;
		}
		
		retBusMap.putAll(retMap);
	}
	
	/** 
	* queryBankCardBindStatusByKey<br/> 
	*  TODO(根据用户银行卡id查询绑定状态)  void
	* @author zhaohefei
	* @2016年1月27日
	* @return void	返回类型 
	 * @throws Exception 
	*/
	@RequestMapping("/queryStatusByKey.do")
	@ResponseBody
	public void queryBankCardBindStatusByKey(){
		Map<String, String> busMap = (Map<String, String>)getRequest().getAttribute("busMap");
		
		Map<String, String> retBusMap = new HashMap<String, String>();
		getRequest().setAttribute("retBusMap", retBusMap);
		
		String bankCardId = busMap.get("bankCardId");
		log.info("请求查询银行卡状态，bankCardId：" + bankCardId);

		Map<String, String> retMap = new HashMap<String, String>();
		try {
			// 先根据id查询银行卡信息
			UserBankCard bankCardInfo = userBankCardService
					.queryBankCardByKey(bankCardId);

			if (EntityUtils.isEmpty(bankCardInfo)) {
				throw new RuntimeException("未根据传入bankCardId查询到银行卡信息，bankCardId为" + bankCardId);
			}

			// 如果不是绑定中，直接返回数据库状态
			if (bankCardInfo.getBindStatus() != StatusConstants.USER_BANK_CARD_BIND_DOING.intValue()) {
				retMap.put("bindStatus", bankCardInfo.getBindStatus().toString());
				retMap.put("desc", "数据库中状态已经修改，不用请求中金");
			} else {
				// 如果不是成功失败，则向中金发起请求，将中金返回结果反馈出去
				retMap = userBankCardService.ensureBankCardStatus(bankCardInfo);
			}

			retMap.put("code", "0");
			retMap.put("test", StatusConstants.REQUEST_STATUS_AWAIT.toString());
		} catch (Exception e) {
			log.error("请求查询银行卡状态发生错误，bankCardId：" + bankCardId, e);
			retMap.put("code", "1");
			retMap.put("desc", e.getMessage());
		}
		
		retBusMap.putAll(retMap);
	}
	
	/**
	 * 主动代收逾期分期单
	 */
	@RequestMapping("/paymentOverdueInstalment.do")
	@ResponseBody
	public void paymentOverdueInstalment(){
		log.info("主动代收逾期分期单");
		Map<String, String> busMap = (Map<String, String>)getRequest().getAttribute("busMap");
		String instalmentId=busMap.get("instalmentId");
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			retMap = userPaymentOrderService.requestCpcnToPayInstalment(instalmentId, true);
			log.info("主动代收逾期controller:code="+retMap.get(ResponseConstants.RETURN_CODE)+",desc="+retMap.get(ResponseConstants.RETURN_DESC));
		} catch (MQException e) {
			log.error("主动代收逾期分期单出错:", e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_SYSTEM_EXCEPTION);
			retMap.put(ResponseConstants.RETURN_DESC, "主动代收逾期分期单出错");
		}
		getRequest().setAttribute("retBusMap", retMap);
	}

}
