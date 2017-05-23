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

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.TelecomConstants;
import com.hikootech.mqcash.po.PartnerUserOrder;
import com.hikootech.mqcash.po.UserCreditRecord;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.service.UserCreditDataService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.HkEncryptUtils;


/**  
 *   
 * CreditJudgeController  
 *   
 * @function:(征信判断引擎)  
 * @create time:Oct 9, 2015 10:44:30 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
@Controller
public class CreditJudgeController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(CreditJudgeController.class);
	
	@Autowired
	private UserCreditDataService userCreditDataService;
	@Autowired
	private SysAlarmService sysAlarmService;
	
	/**  
	 * getCoreCreditData(国政通以及网厅数据通过企信进行评分，判断征信是否通过)  
	 * @param reponse   
	 * void 
	 * @create time： Nov 18, 2015 4:23:27 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	@RequestMapping("/getCoreCreditData.do")
	@ResponseBody
	public String getCoreCreditData(HttpServletResponse reponse){
		 String enc = ConfigUtils.getProperty("inner_mq_plat_enc");
		 String desKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");//双方约定的密钥
		 
		logger.info("征信判断接口开始-->没有未完成的分期单，可以进行征信请求。");
		String params = getRequest().getParameter("params");
		logger.info("获取参数：params：" + params );
		
		//对业务参数进行解密并解析到Map对象中
		Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(params, desKey, enc);
		String infoId = busMap.get("infoId");				//业务表主键
		String userName = busMap.get("userName");			//入网信息中的用户姓名
		String idCard = busMap.get("idCard");				//入网信息中的用户身份证
		String idCardAddress = busMap.get("idCardAddress");	//入网信息中的用户姓名
		String telNumber = busMap.get("telNumber");			//入网信息中的用户联系电话
		String custIp = busMap.get("custIp");				//身份证对应的客户id（Cust_id）
		String custId = busMap.get("custId");				//身份证对应的客户id（Cust_id）
		String pdInstId = busMap.get("pdInstId");			//联系电话对应的产品实例id（Pd_id）
		String source = busMap.get("source");				//合作伙伴渠道（0-网厅，1-掌厅）
		String productId = busMap.get("productId");			//商品ID，商品在合作伙伴的唯一标识
		String productName = busMap.get("productName");		//商品名称（手机名称）
		String description = busMap.get("productDesc");		//商品描述
		String productPrice = busMap.get("productPrice");	//商品价格（总价格=手机价格+号码价格） 价格单位为：分
		String productCount = busMap.get("productCount");	//商品数量（手机数量，默认1）
		String planType = busMap.get("planType");			//默认为手机合约机（0-手机合约机）
		String planPrice = busMap.get("planPrice");			//套餐价格
		
		logger.info("征信判断接口开始-->四元组信息：userName:" + userName + "|idCard:" + idCard + "|idCardAddress：" + idCardAddress + "|telNumber：" + telNumber);
		logger.info("征信判断接口开始-->其他信息：infoId：" + infoId + "|custId:" + custId  + "|custIp:" + custIp+ "|pdInstId:" + pdInstId + "|source:" + source + "|productId:" + productId + "|productName:" + productName +
				   "|description:" + description + "|productPrice:" + productPrice + "|productCount:" + productCount + "|planType:" + planType + "|planPrice:" + planPrice );
				
		/**************处理业务逻辑开始********/

		Map<String, String> map = new HashMap<String, String>();
		
		
		// 设置四元组信息
		PartnerUserOrder partnerUserOrder = new PartnerUserOrder();
		partnerUserOrder.setName(userName);
		partnerUserOrder.setIdCard(idCard);
		partnerUserOrder.setContactPhone(telNumber);
		partnerUserOrder.setIdCardAddress(idCardAddress);

		//插入征信结果记录表
		UserCreditRecord creditRecord = new UserCreditRecord();
		creditRecord.setCreditId(GenerateKey.getId(CommonConstants.CREDIT_RECORD_ID_PREFIX, ConfigUtils.getProperty("db_id")));
		creditRecord.setIdCard(idCard);
		creditRecord.setName(userName);
		creditRecord.setIdCardAddress(idCardAddress);
		creditRecord.setCustIp(custIp);
		creditRecord.setContactPhone(telNumber);
		creditRecord.setSource(Integer.parseInt(source));
		creditRecord.setOperator(CommonConstants.DEFAULT_OPERATOR);
		 
		try {
			logger.info("调用企信征信数据开始。");
			Map<String, String> returnMap  = userCreditDataService.returnCreditResult(creditRecord,partnerUserOrder,busMap);
			 logger.info("征信判断接口结束");
			 return responseParams(returnMap,reponse);
				 
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
				map.put("resultCode",TelecomConstants.API_SYSTEM_EXCEPTION);
				map.put("desc", TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
				return responseParams(map, reponse);
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
	public static String responseParams(Map<String, String> retMap,HttpServletResponse reponse) {
			

		String enc = ConfigUtils.getProperty("inner_mq_plat_enc");
		String desKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");//双方约定的密钥
		String md5Key = ConfigUtils.getProperty("inner_mq_plat_md5_key");
		String partnerId = ConfigUtils.getProperty("inner_mq_plat_partner_id");
		String version = ConfigUtils.getProperty("inner_mq_plat_data_version");

		String params = "";
		try {
			// 生成业务加密串
			String busParams = HkEncryptUtils.createEncryptBusParams(retMap,desKey, enc);
					

			logger.info("parseByte2HexStr : " + busParams);

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
