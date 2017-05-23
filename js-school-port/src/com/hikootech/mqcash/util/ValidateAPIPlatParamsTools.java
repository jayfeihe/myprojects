package com.hikootech.mqcash.util;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.StatusConstants;

 

/**
 * @author yuwei
 * 2015年8月6日
 * 校验工具类
 */
public class ValidateAPIPlatParamsTools {
	
	private static Logger log = LoggerFactory.getLogger(ValidateAPIPlatParamsTools.class);


	/**  
	 * validateSignParams(验证接口传入参数)  
	 * @param bus_params
	 * @return   
	 * boolean 
	 * @create time： Nov 18, 2015 5:04:48 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static boolean validateSignParams(Map<String,String> signMap,String sign,String md5Key ,String enc){
		
		String _sign = HkEncryptUtils.createMd5Sign(signMap, md5Key, enc);
		
		log.info("_sign:" + _sign);
		
		if(EntityUtils.isNotEmpty(_sign) && EntityUtils.isNotEmpty(sign) && sign.equals(_sign)){
			log.info("API平台调用秒趣分期，验签通过");
			return true;
		}else{
			log.info("API平台调用秒趣分期，验签未通过：" + signMap.get("sign") + ",生成验签参数：" + _sign);
			return false;
		}
		
	}
	
	/**  
	 * validateParams(验证参数公共方法)  
	 * @param signMap
	 * @param sign
	 * @return   
	 * boolean 
	 * @create time： Dec 8, 2015 5:38:08 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String, String> validateParams(HttpServletRequest request,String md5Key,String desKey ,String enc,String configPartnerId,String configVersion){
		 log.info("解析参数开始：");
		 
		Map<String, String> responseMap = new HashMap<String, String>();
		
		//验证协议参数完整性
		Map<String, String>  protocolMap = validateProtocolParams(request,configPartnerId,configVersion);
		
		if (!StatusConstants.PARTNER_CALL_SUCCESS.equals(protocolMap.get("result_code"))) {
			 log.info("协议参数验证未通过：" + protocolMap.get("error_code").toString());

			 responseMap.put("resultCode",TelecomConstants.API_PARAMS_NULL);
			 responseMap.put("desc", protocolMap.get("error_code").toString());
			 
			 return responseMap;
		}
		
		String params = request.getParameter("params");
		String partnerId = request.getParameter("partnerId");
		String sign = request.getParameter("sign");
		String timestamp = request.getParameter("timestamp");
		String version = request.getParameter("version");
	
		log.info("获取参数：params=" + params + "&partnerId=" + partnerId + "&sign=" + sign + "&timestamp=" + timestamp + "&version=" + version);
		
		//验签参数
		Map<String,String> signMap = new HashMap<String,String>();
		signMap.put("params", params);
		signMap.put("partnerId", partnerId);
		signMap.put("timestamp", timestamp);
		signMap.put("version",  version);
		
		//判断验签是否通过
		boolean isSign = validateSignParams(signMap,sign,md5Key,enc);
		
		if (!isSign) {
		log.info("验签未通过");
		
		 responseMap.put("resultCode", TelecomConstants.API_PARAMS_SIGN_WRONG);
		 responseMap.put("desc", TelecomConstants.API_PARAMS_SIGN_WRONG_DESC);
		 return responseMap;
		}
		
		 //解析参数
		Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(params, desKey, enc);
		if (busMap == null) {
			 log.info("参数格式错误");
			 responseMap.put("resultCode", TelecomConstants.API_PARAMS_WRONG);
			 responseMap.put("desc",TelecomConstants.API_PARAMS_WRONG_DESC);
			 return responseMap;
		}
		
		log.info("解析参数结束：");
		return busMap;
	
	}
	
	/**  
	 * validateParams(验证参数公共方法)  
	 * @param signMap
	 * @param sign
	 * @return   
	 * boolean 
	 * @create time： Dec 8, 2015 5:38:08 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String, String> validateParams(Map<String,String> resultMap,String md5Key,String desKey ,String enc,String configPartnerId,String configVersion){
		log.info("解析参数开始：");
		
		Map<String, String> responseMap = new HashMap<String, String>();
		
		//验证协议参数完整性
		Map<String, String>  protocolMap = validateProtocolParams(resultMap, configPartnerId, configVersion);
		
		if (!StatusConstants.PARTNER_CALL_SUCCESS.equals(protocolMap.get("result_code"))) {
			 log.info("协议参数验证未通过：" + protocolMap.get("error_code").toString());

			 responseMap.put("code",TelecomConstants.API_PARAMS_NULL);
			 responseMap.put("desc", protocolMap.get("error_code").toString());
			 
			 return responseMap;
		}
		
		String params =  resultMap.get("params");
		String partnerId = resultMap.get("partnerId");
		String sign = resultMap.get("sign") ;
		String timestamp = resultMap.get("timestamp");
		String version = resultMap.get("version");
	
		log.info("获取参数：params=" + params + "&partnerId=" + partnerId + "&sign=" + sign + "&timestamp=" + timestamp + "&version=" + version);
		
		//验签参数
		Map<String,String> signMap = new HashMap<String,String>();
		signMap.put("params", params);
		signMap.put("partnerId", partnerId);
		signMap.put("timestamp", timestamp);
		signMap.put("version",  version);
		
		//判断验签是否通过
		boolean isSign = validateSignParams(signMap,sign,md5Key,enc);
		
		if (!isSign) {
		log.info("验签未通过");
		
		 responseMap.put("code", TelecomConstants.API_PARAMS_SIGN_WRONG);
		 responseMap.put("desc", TelecomConstants.API_PARAMS_SIGN_WRONG_DESC);
		 return responseMap;
		}
		
		 //解析参数
		Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(params, desKey, enc);
		if (busMap == null) {
			 log.info("参数格式错误");
			 responseMap.put("code", TelecomConstants.API_PARAMS_WRONG);
			 responseMap.put("desc",TelecomConstants.API_PARAMS_WRONG_DESC);
			 return responseMap;
		}
		
		log.info("解析参数结束：");		
		return busMap;
	
	}
	
	
		
	/**  
	 * validateBusinessBindMobileParams(验证绑定银行卡绑定手机号发送验证码参数)  
	 * @param busMap
	 * @return   
	 * Map<String,Object> 
	 * @create time： Nov 18, 2015 8:03:05 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String,Object> validateBusinessBindMobileParams(Map<String, String> busMap){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is empty");
		}else if(!checkChineseName(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is not Chinese");
		}else if(EntityUtils.isEmpty(busMap.get("idCard"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is empty");
		}else if(!validateIdCard(busMap.get("idCard").toLowerCase())){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("idCardAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCardAddress is empty");
		}else if(EntityUtils.isEmpty(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is empty");
		}else if(!validateMobileNumber(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is empty");
		}else if(!isIpv4(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("source"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is empty");
		}else if(!Arrays.asList(CommonConstants.SOURCE.split(",")).contains(busMap.get("source"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is unKnown");
		}else if(EntityUtils.isEmpty(busMap.get("bankCardNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "bankCardNumber is empty");
		}else if(EntityUtils.isEmpty(busMap.get("reserveMobile"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "reserveMobile is empty");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	/**  
	 * validateBusinessBindBankCardParams(绑定银行卡接口验证参数)  
	 * @param busMap
	 * @return   
	 * Map<String,Object> 
	 * @create time： Nov 18, 2015 8:05:38 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String,Object> validateBusinessBindBankCardParams(Map<String, String> busMap){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is empty");
		}else if(!checkChineseName(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is not Chinese");
		}else if(EntityUtils.isEmpty(busMap.get("idCard"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is empty");
		}else if(!validateIdCard(busMap.get("idCard").toLowerCase())){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("idCardAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCardAddress is empty");
		}else if(EntityUtils.isEmpty(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is empty");
		}else if(!validateMobileNumber(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is empty");
		}else if(!isIpv4(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("source"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is empty");
		}else if(!Arrays.asList(CommonConstants.SOURCE.split(",")).contains(busMap.get("source"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is unKnown");
		}else if(EntityUtils.isEmpty(busMap.get("bankCardNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "bankCardNumber is empty");
		}else if(EntityUtils.isEmpty(busMap.get("reserveMobile"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "reserveMobile is empty");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	
	/**  
	 * validateBusinessInstalmentConfirmParams(确认分期信息接口)  
	 * @param busMap
	 * @return   
	 * Map<String,Object> 
	 * @create time： Nov 18, 2015 8:05:38 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String,Object> validateBusinessInstalmentConfirmParams(Map<String, String> busMap){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is empty");
		}else if(!checkChineseName(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is not Chinese");
		}else if(EntityUtils.isEmpty(busMap.get("idCard"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is empty");
		}else if(!validateIdCard(busMap.get("idCard").toLowerCase())){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("idCardAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCardAddress is empty");
		}else if(EntityUtils.isEmpty(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is empty");
		}else if(!validateMobileNumber(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is empty");
		}else if(!isIpv4(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("source"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is empty");
		}else if(!Arrays.asList(CommonConstants.SOURCE.split(",")).contains(busMap.get("source")) ){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is unKnown");
		}else if(EntityUtils.isEmpty(busMap.get("bankCardId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "bankCardId is empty");
		}else if(EntityUtils.isEmpty(busMap.get("orderPrice"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "orderPrice is empty");
		}else if(EntityUtils.isEmpty(busMap.get("homeProvince"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "homeProvince is empty");
		}else if(EntityUtils.isEmpty(busMap.get("homeCity"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "homeCity is empty");
		}else if(EntityUtils.isEmpty(busMap.get("homeArea"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "homeArea is empty");
		}else if(EntityUtils.isEmpty(busMap.get("homeAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "homeAddress is empty");
		}else if(EntityUtils.isEmpty(busMap.get("companyName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "companyName is empty");
		}else if(EntityUtils.isEmpty(busMap.get("companyProvince"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "companyProvince is empty");
		}else if(EntityUtils.isEmpty(busMap.get("companyCity"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "companyCity is empty");
		}else if(EntityUtils.isEmpty(busMap.get("companyArea"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "companyArea is empty");
		}else if(EntityUtils.isEmpty(busMap.get("companyAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "companyAddress is empty");
		}else if(EntityUtils.isEmpty(busMap.get("bankCardNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "bankCardNumber is empty");
		}else if(EntityUtils.isEmpty(busMap.get("reserveMobile"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "reserveMobile is empty");
		}else if(EntityUtils.isEmpty(busMap.get("intalmentNum"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "intalmentNum is empty");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	/**  
	 * validateBusinessSyncOrderParams(验证同步订单业务参数)  
	 * @param busMap
	 * @return   
	 * Map<String,Object> 
	 * @create time： Nov 18, 2015 8:05:38 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String,Object> validateBusinessSyncOrderParams(Map<String, String> busMap){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("instalmentId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "instalmentId is empty");
		}else if(EntityUtils.isEmpty(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is empty");
		}else if(!checkChineseName(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is not Chinese");
		}else if(EntityUtils.isEmpty(busMap.get("idCard"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is empty");
		}else if(!validateIdCard(busMap.get("idCard").toLowerCase())){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("idCardAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCardAddress is empty");
		}else if(EntityUtils.isEmpty(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is empty");
		}else if(!validateMobileNumber(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is empty");
		}else if(!isIpv4(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("source"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is empty");
		}else if(!Arrays.asList(CommonConstants.SOURCE.split(",")).contains(busMap.get("source")) ){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is unKnown");
		}else if(EntityUtils.isEmpty(busMap.get("partnerOrderId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "partnerOrderId is empty");
		}else if(EntityUtils.isEmpty(busMap.get("partnerOrderPrice"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "partnerOrderPrice is empty");
		}else if(EntityUtils.isEmpty(busMap.get("partnerOrderUrl"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "partnerOrderUrl is empty");
		}else if(EntityUtils.isEmpty(busMap.get("partnerOrderTime"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "partnerOrderTime is empty");
		}else if(EntityUtils.isEmpty(busMap.get("receiver"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "receiver is empty");
		}else if(EntityUtils.isEmpty(busMap.get("receiverProvince"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "receiverProvince is empty");
		}else if(EntityUtils.isEmpty(busMap.get("receiverCity"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "receiverCity is empty");
		}else if(EntityUtils.isEmpty(busMap.get("receiverArea"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "receiverArea is empty");
		}else if(EntityUtils.isEmpty(busMap.get("receiverAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "receiverAddress is empty");
		}else if(EntityUtils.isEmpty(busMap.get("email"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "email is empty");
		}else if(EntityUtils.isEmpty(busMap.get("receiverMobile"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "receiverMobile is empty");
		}else if(EntityUtils.isEmpty(busMap.get("productUrl"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "productUrl is empty");
		}else if(EntityUtils.isEmpty(busMap.get("productId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "productId is empty");
		}else if(EntityUtils.isEmpty(busMap.get("productName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "productName is empty");
		}else if(EntityUtils.isEmpty(busMap.get("productPrice"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "productPrice is empty");
		}else if(EntityUtils.isEmpty(busMap.get("productCount"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "productCount is empty");
		}else if(EntityUtils.isEmpty(busMap.get("planType"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "planType is empty");
		}else if(EntityUtils.isEmpty(busMap.get("planPrice"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "planPrice is empty");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	
	
	/**  
	 * validateBusinessOrderRefundParams(退单接口验证参数)  
	 * @param busMap
	 * @return   
	 * Map<String,Object> 
	 * @create time： Nov 18, 2015 8:05:38 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String,Object> validateBusinessOrderStatusChangedParams(Map<String, String> busMap){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("requestSeq"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "requestSeq is empty");
		}else if(EntityUtils.isEmpty(busMap.get("partnerOrderId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "partnerOrderId is empty");
		}else if(EntityUtils.isEmpty(busMap.get("orderId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "orderId is empty");
		}else if(EntityUtils.isEmpty(busMap.get("changeType"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "changeType is empty");
		}else if(!Arrays.asList(CommonConstants.CHANGETYPE.split(",")).contains(busMap.get("changeType"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "changeType is unKnown");
		}else if((CommonConstants.CHANGETYPE.split(",")[0].equals(busMap.get("changeType")) || CommonConstants.CHANGETYPE.split(",")[1].equals(busMap.get("changeType")) )&& EntityUtils.isEmpty(busMap.get("refundReason"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "refundReason is empty");
		}else if((CommonConstants.CHANGETYPE.split(",")[2].equals(busMap.get("changeType")))&& EntityUtils.isNotEmpty(busMap.get("refundReason"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "refundReason must empty");
		}else if(EntityUtils.isNotEmpty(busMap.get("refundReason")) && !Arrays.asList(CommonConstants.REFUNDREASON.split(",")).contains(busMap.get("refundReason"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "refundReason is unKnown");
		}else if(EntityUtils.isEmpty(busMap.get("changeTime"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "changeTime is empty");
		}else if((CommonConstants.CHANGETYPE.split(",")[0].equals(busMap.get("changeType")) || CommonConstants.CHANGETYPE.split(",")[1].equals(busMap.get("changeType")) )&& EntityUtils.isEmpty(busMap.get("amount"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "amount is empty");
		}else if(CommonConstants.CHANGETYPE.split(",")[1].equals(busMap.get("changeType")) && EntityUtils.isEmpty(busMap.get("paymentOrderSeq"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "paymentOrderSeq is empty");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	
	/**  
	 * validateBusinessTransferParams(打款通知接口验证参数)  
	 * @param busMap
	 * @return   
	 * Map<String,Object> 
	 * @create time： Nov 18, 2015 8:05:38 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String,Object> validateBusinessTransferParams(Map<String, String> busMap){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("orderId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "orderId is empty");
		}else if(EntityUtils.isEmpty(busMap.get("partnerOrderId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "partnerOrderId is empty");
		}else if(EntityUtils.isEmpty(busMap.get("orderPrice"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "orderPrice is empty");
		}else if(EntityUtils.isEmpty(busMap.get("paymentOrderSeq"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "paymentOrderSeq is empty");
		}else if(EntityUtils.isEmpty(busMap.get("capitalSide"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "capitalSide is empty");
		}else if(EntityUtils.isEmpty(busMap.get("account"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "account is empty");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	
	/**  
	 * validateContractParams(电子合同验证)  
	 * @param busMap
	 * @return   
	 * Map<String,Object> 
	 * @create time： Nov 24, 2015 4:03:14 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String,Object> validateContractParams(Map<String, String> busMap){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is empty");
		}else if(!checkChineseName(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is not Chinese");
		}else if(EntityUtils.isEmpty(busMap.get("idCard"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is empty");
		}else if(!validateIdCard(busMap.get("idCard").toLowerCase())){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("idCardAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCardAddress is empty");
		}else if(EntityUtils.isEmpty(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is empty");
		}else if(!validateMobileNumber(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is empty");
		}else if(!isIpv4(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("type"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "type is empty");
		}else if(EntityUtils.isEmpty(busMap.get("source"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is empty");
		}else if(!Arrays.asList(CommonConstants.SOURCE.split(",")).contains(busMap.get("source")) ){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is unKnown");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	
	/**
	 * 校验手机号是否合法
	 * @param mobileNumber
	 * @return
	 */
	public static boolean validateMobileNumber(String mobileNumber){
		if(EntityUtils.isEmpty(mobileNumber)){
			return false;
		}
		String pattern = "^1\\d{10}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(mobileNumber);  
		return m.matches();
	}
	
	/**
	 * 身份证校验
	 * @param idCard
	 * @return
	 */
	public static boolean validateIdCard(String idCard){
		if(EntityUtils.isEmpty(idCard)){
			return false;
		}
		return EntityUtils.isEmpty(IDCard.IDCardValidate(idCard)) ? true : false;
	}
	
	/**  
	 * checkChineseName(校验姓名是否是汉字)  
	 * @param name
	 * @return   
	 * boolean 
	 * @create time： Oct 15, 2015 4:16:46 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static boolean checkChineseName(String name) {
        if (!name.matches("[\u4e00-\u9fa5]{2,10}")) {
            System.out.println("只能输入2到10个汉字");
            return false;
        }else return true;
    }

	public static Map<String, String> validateProtocolParams(HttpServletRequest request,String configPartnerId,String configVersion) {
		Map<String, String> retMap = new HashMap<String, String>();
		
			String params = EntityUtils.replaceNull(request.getParameter("params"));
			String partnerId = EntityUtils.replaceNull(request.getParameter("partnerId"));
			String sign = EntityUtils.replaceNull(request.getParameter("sign"));
			String timestamp = EntityUtils.replaceNull(request.getParameter("timestamp"));
			String version = EntityUtils.replaceNull(request.getParameter("version"));
			log.info("协议参数参数：params=" + params + "&partnerId=" + partnerId + "&sign=" + sign + "&timestamp=" + timestamp + "&version=" + version);

				//校验参数
				if(EntityUtils.isEmpty(params)){
					retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
					retMap.put("error_code", "params is empty");
				}else if(EntityUtils.isEmpty(partnerId)){
					retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
					retMap.put("error_code", "partnerId is empty");
				}else if(!partnerId.equals(configPartnerId)){
					retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
					retMap.put("error_code", "partnerId is error");
				}else if(EntityUtils.isEmpty(sign)){
					retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
					retMap.put("error_code", "sign is empty");
				}else if(EntityUtils.isEmpty(timestamp)){
					retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
					retMap.put("error_code", "timestamp is empty");
				}else if(EntityUtils.isEmpty(version)){
					retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
					retMap.put("error_code", "version is empty");
				}else if(!version.equals(configVersion)){
					retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
					retMap.put("error_code", "version is error");
				}else{
					retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
				}
		
				return retMap;
		
	}
	
	public static Map<String, String> validateProtocolParams(Map<String,String> resultMap,String configPartnerId ,String configVersion) {
		Map<String, String> retMap = new HashMap<String, String>();
		
		String params =  resultMap.get("params");
		String partnerId = resultMap.get("partnerId");
		String sign = resultMap.get("sign") ;
		String timestamp = resultMap.get("timestamp");
		String version = resultMap.get("version");
		
		//校验参数
		if(EntityUtils.isEmpty(params)){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "params is empty");
		}else if(EntityUtils.isEmpty(partnerId)){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "partnerId is empty");
		}else if(!partnerId.equals(configPartnerId)){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "partnerId is error");
		}else if(EntityUtils.isEmpty(sign)){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "sign is empty");
		}else if(EntityUtils.isEmpty(timestamp)){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "timestamp is empty");
		}else if(EntityUtils.isEmpty(version)){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "version is empty");
		}else if(!version.equals(configVersion)){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "version is error");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
		
	}
	
public static Map<String,Object> validateBusinessCreditParams(Map<String, String> busMap){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//校验参数
		if(EntityUtils.isEmpty(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is empty");
		}else if(!checkChineseName(busMap.get("userName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "userName is not Chinese");
		}else if(EntityUtils.isEmpty(busMap.get("idCard"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is empty");
		}else if(!validateIdCard(busMap.get("idCard").toLowerCase())){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCard is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("idCardAddress"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "idCardAddress is empty");
		}else if(EntityUtils.isEmpty(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is empty");
		}else if(!validateMobileNumber(busMap.get("telNumber"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "telNumber is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("source"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is empty");
		}else if(!Arrays.asList(CommonConstants.SOURCE.split(",")).contains(busMap.get("source")) ){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "source is unKnown");
		}else if(EntityUtils.isEmpty(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is empty");
		}else if(!isIpv4(busMap.get("custIp"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "custIp is formatError");
		}else if(EntityUtils.isEmpty(busMap.get("productId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "productId is empty");
		}else if(EntityUtils.isEmpty(busMap.get("productName"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "productName is empty");
		}else if(EntityUtils.isEmpty(busMap.get("productPrice"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "productPrice is empty");
		}else if(EntityUtils.isEmpty(busMap.get("productCount"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "productCount is empty");
		}else if(EntityUtils.isEmpty(busMap.get("planType"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "planType is empty");
		}else if(EntityUtils.isEmpty(busMap.get("planPrice"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "planPrice is empty");
		}else{
			retMap.put("result_code", StatusConstants.PARTNER_CALL_SUCCESS);
		}
		
		return retMap;
	}
	
	/**  
	 * isIpv4(验证ip地址是否有效)  
	 * @param ipAddress
	 * @return   
	 * boolean 
	 * @create time： Dec 1, 2015 4:11:12 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static boolean isIpv4(String ipAddress) {  
		  
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";  
  
        Pattern pattern = Pattern.compile(ip);  
        Matcher matcher = pattern.matcher(ipAddress);  
        return matcher.matches();  
  
    } 
	
 

}
