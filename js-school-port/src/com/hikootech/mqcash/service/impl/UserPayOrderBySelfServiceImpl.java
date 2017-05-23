/**
 * 
 */
package com.hikootech.mqcash.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.ZhongjinConstants;
import com.hikootech.mqcash.dao.UserInstalmentDAO;
import com.hikootech.mqcash.dao.UserPaymentOrderDAO;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.UserPayOrderBySelfService;
import com.hikootech.mqcash.service.UserRepayPlanService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.HttpClientUtil;

import net.sf.json.JSONObject;
import payment.api.tx.marketorder.Tx1311Request;

/**
 * @author afei
 *
 */
@Service("userPayOrderBySelfService")
public class UserPayOrderBySelfServiceImpl implements UserPayOrderBySelfService {

	private static Logger log = LoggerFactory.getLogger(UserPayOrderBySelfServiceImpl.class);

	@Autowired
	private UserPaymentOrderDAO userPaymentOrderDAO;
	@Autowired
	private UserRepayPlanService userRepayPlanService;
	@Autowired
	private UserInstalmentDAO userInstalmentDAO;
	@Autowired
	private ConfigKvService configKvService ;

	private String innerEnc = ConfigUtils.getProperty("inner_mq_plat_enc");
	private String innerDesKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");
	private String innerMd5Key = ConfigUtils.getProperty("inner_mq_plat_md5_key");
	private String innerPartnerId = ConfigUtils.getProperty("inner_mq_plat_partner_id");
	private String innerVersion = ConfigUtils.getProperty("inner_mq_plat_data_version");
	private String inner_mq_self_payment_status_url = ConfigUtils.getProperty("inner_mq_self_payment_status_url");
	
	
	
	@Override
	public Map<String, Object> UserRequestPay(UserPaymentOrder paymentOrder, Map<String, Object> retMap) {

		// 1.创建交易请求对象

		Tx1311Request tx1311Request = new Tx1311Request();

		// 执行报文处理
		try {
			tx1311Request.setInstitutionID(ConfigUtils.getProperty("mqcash_institution_id"));
			tx1311Request.setOrderNo(paymentOrder.getPaymentOrderNo());
			tx1311Request.setPaymentNo(paymentOrder.getUserPaymentOrderId());// 市场交易流水号
			tx1311Request.setAmount(paymentOrder.getPaymentAmount().multiply(new BigDecimal(100)).longValue());
			tx1311Request.setNotificationURL("http://"+configKvService.get(CommonConstants.MQ_CASH_PORT_URL)+ConfigUtils.getProperty("mqcash_institution_1311_success_url"));
			tx1311Request.setBankID(paymentOrder.getBankId());
			tx1311Request.setAccountType(ZhongjinConstants.ACCOUNT_TYPE_PRIVATE);
			tx1311Request.process();

		} catch (Exception e) {
			log.error("主动付款请求，处理准备向中金发送的信息时，发生错误：", e);
			throw new RuntimeException("主动付款请求，处理准备向中金发送的信息时，发生错误：", e);
		}

		log.info("原请求xml : ");
		log.debug(tx1311Request.getRequestPlainText());
		log.debug("请求message : ");
		log.debug(tx1311Request.getRequestMessage());
		log.debug("请求signature : ");
		log.debug(tx1311Request.getRequestSignature());

		retMap.put("message", tx1311Request.getRequestMessage());
		retMap.put("signature", tx1311Request.getRequestSignature());
		return retMap;
	}

	@Override
	public Map<String,Object> queryPayOrderSelfResult(String paymentOrderId ) throws Exception {

			log.info("请求核心查询支付订单支付状态"); 
			Map<String,Object> retMap=new HashMap<String,Object>();
			
			//将参数传给http工具类
			HttpClientUtil http = new HttpClientUtil(10000);
			Map<String, String> requestMap=new HashMap<String,String>();
			requestMap.put("paymentNo", paymentOrderId);
			
			// 生成业务加密串
			String busParams = HkEncryptUtils.createEncryptBusParams(requestMap,innerDesKey, innerEnc);

			// 协议参数
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
			paramMap.put("partnerId", innerPartnerId);
			paramMap.put("version", innerVersion);
			paramMap.put("params", busParams);

			// 生成验签sign
			String sign_credit = HkEncryptUtils.createMd5Sign(paramMap, innerMd5Key, innerEnc);
			paramMap.put("sign", sign_credit);
			
			String retMsg=http.doPost(inner_mq_self_payment_status_url, paramMap);
			log.info("请求中金返回消息："+retMsg);
			//转换为Map
			Map<String,String> resultMap = HkEncryptUtils.stringToMap(retMsg);
			
			String retSign = resultMap.get("sign");
			resultMap.remove("sign");
			
			String _sign = HkEncryptUtils.createMd5Sign(resultMap, innerMd5Key, innerEnc);
			 
			
			if(retSign.equals(_sign)){
				log.info("验签通过");
				Map<String, String> retBusMap = HkEncryptUtils.createDecryptBusMap(resultMap.get("params"), innerDesKey, innerEnc);
				 
				String retCode=retBusMap.get("resultCode");
				String retBindStatus=retBusMap.get("status");
				String retDesc=retBusMap.get("desc");
				
				if(EntityUtils.isEmpty(retCode)||EntityUtils.isEmpty(retBindStatus)){
					log.error("核心系统返回信息参数为空：resultCode："+retCode+",status:"+retBindStatus);
					 retMap.put("code", ResponseConstants.FAIL);
					 retMap.put("desc", "系统错误！");
					 return retMap;
				}
				
				// 返回结果，code为查询成功时  
				if(retCode.equals(ResponseConstants.SUCCESS)){
					//返回已经绑定，则直接返回支付成功
					if( Integer.parseInt(retBindStatus)==StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS.intValue()){
						 log.info("核心系统返回支付成功 " );
						 retMap.put("code", ResponseConstants.SUCCESS);
						 retMap.put("desc", "支付成功");
						 return retMap;
					 }
					 
					 //剩余状态认为绑定失败
					 log.info("核心系统返回状态为不是成功,status："+retBindStatus+",描述信息："+retDesc);
					 retMap.put("code", ResponseConstants.FAIL);
					 retMap.put("desc", "支付失败！");
					 return retMap;
				}

				 //返回code不是成功
				 log.info("核心系统返回查询结果resultCode不是成功，resultCode："+retCode+",描述信息："+retDesc);
				 retMap.put("code", ResponseConstants.FAIL);
				 retMap.put("desc", "系统错误！");
				 return retMap;
				
			}else{
				log.info("验签不通过");
				 retMap.put("code", ResponseConstants.FAIL);
				 retMap.put("desc", "系统错误！");
				 return retMap;
			}
	}

}
