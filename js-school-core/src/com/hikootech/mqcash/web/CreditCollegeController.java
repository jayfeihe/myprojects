package com.hikootech.mqcash.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.TelecomConstants;
import com.hikootech.mqcash.service.CreditCollegeService;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.HkEncryptUtils;

/**  
 *   
 * CreditCollegeController  
 *   
 * @function:(江苏校园征信Controller)  
 * @create time:2016年7月12日 下午8:12:07   
 * @version 1.0.0  
 * @author:张海达    
 */

@Controller
public class CreditCollegeController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(CreditCollegeController.class);

	@Autowired
	private CreditCollegeService creditCollegeService ;
	@Autowired
	private SysAlarmService sysAlarmService ;
	
	private String innerEnc = ConfigUtils.getProperty("inner_college_enc");
	private String innerDesKey = ConfigUtils.getProperty("inner_college_3des_key");//双方约定的密钥
	private String enc = ConfigUtils.getProperty("inner_college_enc");
	private String desKey = ConfigUtils.getProperty("inner_college_3des_key");
	private String md5Key = ConfigUtils.getProperty("inner_college_md5_key");
	private String partnerId = ConfigUtils.getProperty("inner_college_partner_id");
	private String version = ConfigUtils.getProperty("inner_college_version");

	/**  
	 * collegeCredit(校园征信接口)  
	 * @param reponse   
	 * void 
	 * @create time： Nov 18, 2015 4:23:27 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	@RequestMapping("/collegeCredit.do")
	@ResponseBody
	public void collegeCredit(HttpServletResponse reponse){
		logger.info("江苏校园征信判断接口开始-->进行征信请求。");
		Map<String, String> retBusMap = new HashMap<String, String>();
		
		//拦截器中返回给调用接口处理
		getRequest().setAttribute("retBusMap", retBusMap);
		
		Map<String, String> busMap = getRequest().getAttribute("busMap") == null ? null : (Map<String, String>) getRequest().getAttribute("busMap");
		if(busMap == null || busMap.size() == 0){
			logger.error("内部接口调用业务参数为空！");
			retBusMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, "内部接口调用业务参数为空！");
			return ;
		}
		
		//验证参数正确性
		Map<String, Object>  validateMap = creditCollegeService.validateSchoolCreditParams(busMap);
		//验证不通过
		if (StatusConstants.PARTNER_CALL_SUCCESS != validateMap.get("result_code") ) {
			logger.info("校园征信判断接口-->参数验证未通过：" + validateMap.get("error_code").toString());

			retBusMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.API_PARAMS_NULL);
			retBusMap.put(ResponseConstants.RETURN_DESC, validateMap.get("error_code").toString());
			return ;
		}
		
		String busId = busMap.get("busId");					//业务表主键
		String userName = busMap.get("userName");			//用户姓名
		String idCard = busMap.get("idCard");				//用户身份证
		String idCardAddress = busMap.get("idCardAddress");	//身份证地址
		String telNumber = busMap.get("telNumber");			//联系电话
		String channelId = busMap.get("channelId");			//渠道id,用户所使用的平台（1：微信 2：APP 3：web）
		String custIp = busMap.get("custIp");				//用户IP地址
		String schoolName = busMap.get("schoolName");		//所在院校--院校全称
		String department = busMap.get("department");		//所在院系--院系全称
		
		logger.info("江苏校园征信判断接口开始-->四元组信息：userName:" + userName + "|idCard:" + idCard + "|idCardAddress：" + idCardAddress + "|telNumber：" + telNumber);
		logger.info("江苏校园征信判断接口开始-->其他信息：busId：" + busId + "|channelId:" + channelId + "|custIp:" + custIp + "|schoolName:" + schoolName + "|department:" + department );
				
		/**************处理业务逻辑开始********/

		try {
			 logger.info("判断征信数据开始。");
			 Map<String, String> retMap = creditCollegeService.requestCredit(busMap);
			 logger.info("江苏校园征信判断接口结束");
			 retBusMap.putAll(retMap);
			 return;
				 
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("征信判断接口开始-->查询用户信息异常：" + "|idCard:" + idCard +  "|telNumber：" + telNumber + e.getMessage());
				//插入告警表
				String alarmContent = "核心征信系统发生异常,idCard："+ idCard  + ",userName:" + userName ;
				try {
					sysAlarmService.alarm(alarmContent);
				} catch (Exception e1) {
					logger.error("查询用户信息异常：idCard："+ idCard + ",contactPhone:" + telNumber + ",idCardAddress:" + idCardAddress + ",userName:" + userName ,e1);
				}
				retBusMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.API_SYSTEM_EXCEPTION);
				retBusMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
				return ;
			}
			/**************处理业务逻辑结束********/
	}
	
	/**
	 * 请求前海好信度接口
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/collegeQhCredit.do")
	@ResponseBody
	public void collegeQhCredit(HttpServletResponse reponse){
		logger.info("江苏校园请求前海信度接口开始-->未绑卡，进行前海好信度请求。");
		Map<String, String> busMap = (Map<String, String>)getRequest().getAttribute("busMap");
		
		Map<String, String> retBusMap = new HashMap<String, String>();
		getRequest().setAttribute("retBusMap", retBusMap);//拦截器会做加密并且返回相应值
		
		//对业务参数进行解密并解析到Map对象中
		String busId = busMap.get("busId");				//业务表主键
		String userName = busMap.get("userName");			//用户姓名
		String idCard = busMap.get("idCard");				//用户身份证
		String idCardAddress = busMap.get("idCardAddress");	//身份证地址
		String telNumber = busMap.get("telNumber");			//联系电话
		String bankCardNumber = busMap.get("bankCardNumber");	//银行卡号
		
		logger.info("江苏校园请求前海信度接口-->四元组信息：userName:" + userName + "|idCard:" + idCard + "|idCardAddress：" + idCardAddress + "|telNumber：" + telNumber);
		logger.info("江苏校园请求前海信度接口-->其他信息：busId：" + busId + "|bankCardNumber:" + bankCardNumber + "|busId:" + busId );
				
		/**************处理业务逻辑开始********/
		try {
			logger.info("请求前海信度接口开始。");
			Map<String, String> retMap = creditCollegeService.catchQhCreditData(busMap);
			retBusMap.putAll(retMap);
			logger.info("请求前海信度接口结束");
			} catch (Exception e) {
				logger.error("江苏校园请求前海信度接口-->查询用户信息异常：" + "|idCard:" + idCard +  "|telNumber：" + telNumber + e.getMessage(), e);
				//插入告警表
				String alarmContent = "核心征信系统发生异常,idCard："+ idCard  + ",userName:" + userName ;
				try {
					sysAlarmService.alarm(alarmContent);
				} catch (Exception e1) {
					logger.error("查询用户信息异常：idCard："+ idCard + ",contactPhone:" + telNumber + ",idCardAddress:" + idCardAddress + ",userName:" + userName ,e1);
				}
				retBusMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.API_SYSTEM_EXCEPTION);
				retBusMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
			}
	    /**************处理业务逻辑结束********/
	}
	
	/**
	 * 请求学籍核查接口
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/collegeCheckSchool.do")
	@ResponseBody
	public void collegeCheckSchool(HttpServletResponse reponse){
		logger.info("江苏校园请求学籍核查信息接口开始--");
		Map<String, String> busMap = (Map<String, String>)getRequest().getAttribute("busMap");
		
		Map<String, String> retBusMap = new HashMap<String, String>();
		getRequest().setAttribute("retBusMap", retBusMap);//拦截器会做加密并且返回相应值
		
//		Map<String, String> busMap = new HashMap<String, String>();
//		busMap.put("busId", "BID20160722161808010020");
//		busMap.put("userName", "孙航");
//		busMap.put("documentNo", "41142219960515153X");
//		busMap.put("collegeLevel", "BK");
//		busMap.put("startTime", "2014");
//		busMap.put("college", "河南工业大学");
//		busMap.put("telNumber", "18741258751");
		
		//对业务参数进行解密并解析到Map对象中
		String busId = busMap.get("busId");				  //业务表主键
		String userName = busMap.get("userName");		  //用户姓名
		String idCard = busMap.get("documentNo");		  //用户身份证
		String telNumber = busMap.get("telNumber");		  //联系电话
		String collegeLevel = busMap.get("collegeLevel"); //入学年份
		String startTime = busMap.get("startTime");       //入学年份
		String college = busMap.get("college"); 	      //学校名称
		
//		logger.info("江苏校园学籍核查信息接口-->四元组信息：userName:" + userName + "|idCard:" + idCard + "|idCardAddress：" + idCardAddress + "|telNumber：" + telNumber);
//		logger.info("江苏校园学籍核查信息接口-->其他信息：busId：" + busId + "|bankCardNumber:" + bankCardNumber + "|busId:" + busId );
				
		/**************处理业务逻辑开始********/
		try {
			logger.info("学籍核查信息接口开始。");
			Map<String, String> retMap = creditCollegeService.requestCheckSchool(busMap);
			retBusMap.putAll(retMap);
			logger.info("学籍核查信息接口结束");
			} catch (Exception e) {
				logger.error("江苏校园请求前海信度接口-->查询用户信息异常：" + "|idCard:" + idCard +  "|telNumber：" + telNumber + e.getMessage(), e);
				//插入告警表
				String alarmContent = "核心征信系统发生异常,idCard："+ idCard  + ",userName:" + userName ;
				try {
					sysAlarmService.alarm(alarmContent);
				} catch (Exception e1) {
					logger.error("查询用户信息异常：idCard："+ idCard + ",contactPhone:" + telNumber + ",userName:" + userName ,e1);
				}
				retBusMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.API_SYSTEM_EXCEPTION);
				retBusMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
			}
	    /**************处理业务逻辑结束********/
	}
	
	/**  
	 * responseParams(将处理结果返回MQ平台)  
	 * @param retMap
	 * @return   
	 * String 
	 * @create time： Oct 21, 2015 5:09:14 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public  String responseParams(Map<String, String> retMap,HttpServletResponse reponse) {
			

		String params = "";
		try {
			// 生成业务加密串
			String busParams = HkEncryptUtils.createEncryptBusParams(retMap,desKey, enc);
			logger.info("parseByte2HexStr : " + busParams);
			
			Map<String, String> param = HkEncryptUtils.createDecryptBusMap(busParams,desKey,enc);
			logger.info("返回参数明文： " + param);
			

			// 协议参数
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("timestamp", DateUtil.getCurDateStr("yyyyMMddHHmmss"));
			paramMap.put("partnerId", partnerId);
			paramMap.put("version", version);
			paramMap.put("params", busParams);

			// 生成验签sign
			String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
			paramMap.put("sign", sign);
			params = HkEncryptUtils.mapToString(paramMap);
			logger.info("征信返回API平台数据组装参数：" + params);
			reponse.getWriter().write(params);
			reponse.getWriter().flush();
			reponse.getWriter().close();

			return null;
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("征信返回数据,生成参数异常", e1);
			throw new RuntimeException("征信返回数据，生成参数异常", e1);
		}
	}
}
