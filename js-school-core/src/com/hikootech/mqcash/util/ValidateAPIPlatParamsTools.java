package com.hikootech.mqcash.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.TelecomConstants;


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
	public static Map<String, String> validateParams(HttpServletRequest request,String md5Key ,String desKey ,String enc, String configPartnerId, String configVersion){
		Map<String, String> responseMap = new HashMap<String, String>();
		
		//验证协议参数完整性
		Map<String, Object>  protocolMap = validateProtocolParams(request, configPartnerId,configVersion);
		
		if (!StatusConstants.PARTNER_CALL_SUCCESS.equals(protocolMap.get("result_code"))) {
			 log.info("协议参数验证未通过：状态码：" + protocolMap.get("result_code") + "|错误信息：" + protocolMap.get("error_code").toString());

			 responseMap.put("resultCode",TelecomConstants.API_PARAMS_NULL);
			 responseMap.put("desc", TelecomConstants.API_PARAMS_NULL_DESC);
			 
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
		log.info("征信判断接口开始-->验签通过");
		 //解析参数
		Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(params, desKey, enc);
		if (busMap == null) {
			 log.info("参数格式错误");
			 responseMap.put("resultCode", TelecomConstants.API_PARAMS_WRONG);
			 responseMap.put("desc",TelecomConstants.API_PARAMS_WRONG_DESC);
			 return responseMap;
		}
				
		return busMap;
	
	}
	
		
	public static Map<String,Object> validateBusinessParams(Map<String, String> busMap){
		
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
		}else if(!Arrays.asList(CommonConstants.SOURCE.split(",")).contains(busMap.get("source"))){
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
		}else if(EntityUtils.isEmpty(busMap.get("infoId"))){
			retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
			retMap.put("error_code", "infoId is empty");
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
	
	/**  
	 * validateProtocolParams(验证协议参数完整性)  
	 * @param request
	 * @return   
	 * Map<String,Object> 
	 * @create time： Dec 1, 2015 1:25:10 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String, Object> validateProtocolParams(HttpServletRequest request,String configPartnerId,String configVersion) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		
			String params = request.getParameter("params");
			String partnerId = request.getParameter("partnerId");
			String sign = request.getParameter("sign");
			String timestamp = request.getParameter("timestamp");
			String version = request.getParameter("version");
		
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
	
	public static Map<String, Object> validateProtocolParams(Map<String,String> resultMap,String configPartnerId,String configVersion) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		
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

	
	/**  
	 * validateSendCodeParams(江苏校园发送验证码参数校验)  
	 * @param busMap
	 * @return   
	 * Map<String,Object> 
	 * @create time： 2016年7月20日 上午11:28:55 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static Map<String,Object> validateSchoolSendCodeParams(Map<String, String> busMap){
			
			Map<String, Object> retMap = new HashMap<String, Object>();
			//校验参数
			if(EntityUtils.isEmpty(busMap.get("busId"))){
				retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("error_code", "busId is empty");
			}else if(!Arrays.asList(CommonConstants.CHANNELID.split(",")).contains(busMap.get("channelId")) ){
				retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("error_code", "channelId is unKnown");
			}else if(EntityUtils.isEmpty(busMap.get("custIp"))){
				retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("error_code", "custIp is empty");
			}else if(!isIpv4(busMap.get("custIp"))){
				retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("error_code", "custIp is formatError");
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
			}else if(!validIdCardAddress(busMap.get("idCardAddress"))){
				retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("error_code", "idCardAddress is formatError");
			}else if(EntityUtils.isEmpty(busMap.get("telNumber"))){
				retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("error_code", "telNumber is empty");
			}else if(!validateMobileNumber(busMap.get("telNumber"))){
				retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("error_code", "telNumber is formatError");
			}else if(EntityUtils.isEmpty(busMap.get("schoolName"))){
				retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("error_code", "schoolName is empty");
			}else if(EntityUtils.isEmpty(busMap.get("department"))){
				retMap.put("result_code", StatusConstants.PARTNER_CALL_FAIL);
				retMap.put("error_code", "department is empty");
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
	
	/**  
	 * validIdCardAddress(验证身份证地址)  
	 * @param idCardAddress
	 * @return   
	 * boolean 
	 * @create time： 2016年7月20日 下午5:47:13 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public static  boolean validIdCardAddress(String idCardAddress){
		//验证通过后，进行身份证地址合法性校验
		//1.不少于8个字符
		//2.前两个字符必须为省级名称
		//出现以上情况，提示客户按照身份证地址格式输入
		if(idCardAddress.length() < 8){
			return false;
		}
		return true ;
	}
	
}
