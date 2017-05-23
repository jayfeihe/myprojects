package com.hikootech.mqcash.interceptor;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.exception.MQExceptionConstants;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.HkEncryptUtils;

/** 
* @ClassName InnerInterfaceSignInterceptor 
* @Description 秒趣核心内部接口验证签名拦截器
* @author 余巍 yuweiqwe@126.com 
* @date 2016年2月2日 下午4:47:42 
*  
*/
public class InnerInterfaceSignInterceptor implements HandlerInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(InnerInterfaceSignInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		log.info("InnerInterfaceSignInterceptor:postHandle:"+request.getRequestURI());
		Map<String, Object> rbMap = request.getAttribute("retBusMap") == null ? null : (Map<String, Object>) request.getAttribute("retBusMap");
		Map<String,String> retBusMap=new HashMap<String,String>();
		if(rbMap == null || rbMap.size() == 0){
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_SYSTEM_EXCEPTION);
			retBusMap.put(ResponseConstants.RETURN_DESC, "内部接口调用返回业务参数为空，系统异常！");
		}
		else{
			for(Map.Entry entry:rbMap.entrySet()){
				retBusMap.put((String)entry.getKey(), entry.getValue().toString());
			}
		}
		log.info("InnerInterfaceSignInterceptor:postHandle:code="+retBusMap.get(ResponseConstants.RETURN_CODE));
		response(retBusMap, response);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String params = request.getParameter("params");
		String partnerId = request.getParameter("partnerId");
		String sign = request.getParameter("sign");
		String timestamp = request.getParameter("timestamp");
		String version = request.getParameter("version");
		
		log.info("partnerId : " + partnerId + ", sign : " + sign + ", version : " + version + ", timestamp : " + timestamp + ", params : " + params);
		
		Map<String, String> retBusMap = new HashMap<String, String>();
		//非空检查
		if(EntityUtils.isEmpty(params)){
			log.error("业务参数为空");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "业务参数为空");
			response(retBusMap, response);
			return false;
		}else if(EntityUtils.isEmpty(partnerId)){
			log.error("合作伙伴参数为空");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "合作伙伴参数为空");
			response(retBusMap, response);
			return false;
		}else if(EntityUtils.isEmpty(sign)){
			log.error("摘要签名参数为空");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "摘要签名参数为空");
			response(retBusMap, response);
			return false;
		}else if(EntityUtils.isEmpty(timestamp)){
			log.error("访问时间戳参数为空");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "访问时间戳参数为空");
			response(retBusMap, response);
			return false;
		}else if(EntityUtils.isEmpty(version)){
			log.error("版本号参数为空");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "版本号参数为空");
			response(retBusMap, response);
			return false;
		}
		
		String _partnerId = ConfigUtils.getProperty("inner_mq_plat_partner_id");
		String _version = ConfigUtils.getProperty("inner_mq_plat_data_version");
		String enc = ConfigUtils.getProperty("inner_mq_plat_enc");
		String md5Key = ConfigUtils.getProperty("inner_mq_plat_md5_key");
		String desKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");
		
		//解密
		String decryptStr = HkEncryptUtils.decrypt(params, desKey, enc);
		log.info("通讯业务参数明文字符串：" + decryptStr);
		Map<String, String> busMap = HkEncryptUtils.stringToMapWithEncodeValue(decryptStr, enc);
		
		if(busMap == null || busMap.size() == 0){
			log.error("内部接口调用业务参数为空！");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "内部接口调用业务参数为空！");
			response(retBusMap, response);
			return false;
		}
		
		if(!partnerId.equals(_partnerId)){
			log.error("合作伙伴参数错误，partnerId ：" + partnerId);
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_VALIDATION);
			retBusMap.put(ResponseConstants.RETURN_DESC, "合作伙伴参数错误");
			response(retBusMap, response);
			return false;
		}else if(!version.equals(_version)){
			log.error("版本号参数错误，version：" + version);
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_VALIDATION);
			retBusMap.put(ResponseConstants.RETURN_DESC, "版本号参数错误");
			response(retBusMap, response);
			return false;
		}
		
		//验签
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("timestamp", timestamp);
		paramMap.put("partnerId", partnerId);
		paramMap.put("version", version);
		paramMap.put("params", params);
		
		String _sign = HkEncryptUtils.createMd5Sign(paramMap, 
				md5Key, 
				CommonConstants.DEFAULT_CHARSET);
		
		if(!sign.equals(_sign)){
			log.error("验签不通过,原sign : " + sign + " ,生成sign : " + _sign);
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_SIGN_FAILED);
			retBusMap.put(ResponseConstants.RETURN_DESC, "验签不通过");
			
			response(retBusMap, response);
			return false;
		}
		
		log.info("验签通过,sign : " + sign);
		
		//解密
//		Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(params, 
//				desKey, 
//				enc);
		
		request.setAttribute("busMap", busMap);
		
		return true;
	}
	
	private String response(Map<String, String> busMap, HttpServletResponse response){
		String _partnerId = ConfigUtils.getProperty("inner_mq_plat_partner_id");
		String _version = ConfigUtils.getProperty("inner_mq_plat_data_version");
		String enc = ConfigUtils.getProperty("inner_mq_plat_enc");
		String md5Key = ConfigUtils.getProperty("inner_mq_plat_md5_key");
		String desKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");
		log.info("_partnerId="+_partnerId+",_version="+_version+",enc="+enc+",md5Key="+md5Key+",desKey="+desKey);
		
		String busParams = HkEncryptUtils.createEncryptBusParams(busMap, 
				desKey, 
				enc);
		log.info("busParams="+busParams);
		
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("timestamp", DateUtil.formatDate(new Date(), DateUtil.FORMAT_SS_NO_SYMBOL));
		retMap.put("partnerId", _partnerId);
		retMap.put("version", _version);
		retMap.put("params", busParams);
		
		String retSign = HkEncryptUtils.createMd5Sign(retMap, 
				md5Key, 
				enc);
		log.info("retSign="+retSign);
		
		retMap.put("sign", retSign);
		
		String retString = HkEncryptUtils.mapToString(retMap);
		log.info("response result:"+retString);
		try {
			response.getWriter().write(retString);
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("秒趣核心内部接口验证签名拦截器返回信息流写入失败，IO异常！", e);
			throw new MQException(MQExceptionConstants.MQ_IO_EXCEPTION, MQExceptionConstants.MQ_IO_EXCEPTION_DESC);
		}
		
		return retString;
	}
}
