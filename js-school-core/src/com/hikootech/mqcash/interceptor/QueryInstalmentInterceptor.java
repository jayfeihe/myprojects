package com.hikootech.mqcash.interceptor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.TelecomConstants;
import com.hikootech.mqcash.dto.RecordPartnerOrderStatisticDTO;
import com.hikootech.mqcash.service.RecordPartnerOrderStatiticService;
import com.hikootech.mqcash.service.UserCreditDataService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.ValidateAPIPlatParamsTools;

public class QueryInstalmentInterceptor implements HandlerInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(QueryInstalmentInterceptor.class);
	
	@Autowired
	private UserCreditDataService userCreditDataService;
	@Autowired
	private RecordPartnerOrderStatiticService recordPartnerOrderStatiticService;
	
	//初始化用户每日访问的日期
	private static String isExisted = "";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse reponse, Object arg2) {
		log.info("征信判断接口-->进入拦截器开始");
		String enc = ConfigUtils.getProperty("inner_mq_plat_enc");
		String desKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");
		String md5Key = ConfigUtils.getProperty("inner_mq_plat_md5_key");
		String partnerId = ConfigUtils.getProperty("inner_mq_plat_partner_id");
		String version = ConfigUtils.getProperty("inner_mq_plat_data_version");
		
		Map<String, String> responseMap = new HashMap<String, String>();
		Map<String, String> busMap = ValidateAPIPlatParamsTools.validateParams(request,md5Key , desKey ,enc, partnerId, version);
		
		if (EntityUtils.isNotEmpty(busMap.get("resultCode"))) {
			 log.info("征信判断接口-->参数验证未通过：" + busMap.get("desc").toString());
			
			 responseMap.put("resultCode", busMap.get("resultCode"));
			 responseMap.put("desc", busMap.get("desc"));
			 responseMap.put("code",  "");		
			 responseMap.put("message", "");	
			 responseParams(responseMap,reponse);
			 return false;
		}	
		
		//验证参数正确性
		Map<String, Object>  validateMap = ValidateAPIPlatParamsTools.validateBusinessParams(busMap);
				
		//验证不通过
		if (StatusConstants.PARTNER_CALL_SUCCESS != validateMap.get("result_code") ) {
			 log.info("征信判断接口-->参数验证未通过：" + validateMap.get("error_code").toString());

			 responseMap.put("resultCode",TelecomConstants.API_PARAMS_NULL);
			 responseMap.put("desc", validateMap.get("error_code").toString());
			 responseMap.put("isBound", "");
			 responseMap.put("bankCardNumber", "");
			 responseParams(responseMap,reponse);
			 return false;
		}
				
	    log.info("征信判断接口开始-->参数验证通过：");
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
	
		log.info("征信判断接口开始-->四元组信息：userName:" + userName + "|idCard:" + idCard + "|idCardAddress：" + idCardAddress + "|telNumber：" + telNumber);
		log.info("征信判断接口开始-->其他信息：infoId：" + infoId + "|custId:" + custId  + "|custIp:" + custIp+ "|pdInstId:" + pdInstId + "|source:" + source + "|productId:" + productId + "|productName:" + productName +
				   "|description:" + description + "|productPrice:" + productPrice + "|productCount:" + productCount + "|planType:" + planType + "|planPrice:" + planPrice );
		
		//查询该用户是否有未完成的分期
		Map<String, Object> instalmentMap = new  HashMap<String,Object>();
		instalmentMap.put("idCard", idCard);
		instalmentMap.put("contactPhone", telNumber);
		instalmentMap.put("date", new Date());
		boolean isAllow = userCreditDataService.isAllowInstalment(instalmentMap);
		if (!isAllow) {
			 responseMap.put("resultCode",  TelecomConstants.CRE_REPEAT_INST);
			 responseMap.put("desc",  TelecomConstants.CRE_REPEAT_INST_DESC);
			 responseParams(responseMap,reponse);
			 return false;
		}
			
		//插入合作伙伴下单限制信息
		try {
			isExisted(request.getParameter("partnerId"));
		} catch (Exception e) {
			e.printStackTrace();
			
			log.info("征信判断接口开始-->插入合作伙伴下单限制信息异常：" + request.getParameter("partnerId"));
			responseMap.put("resultCode",  TelecomConstants.API_SYSTEM_EXCEPTION);
			responseMap.put("desc",  TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
			responseParams(responseMap,reponse);
			return false;
			 
		}
		
		log.info("征信判断接口-->进入拦截器结束");
		 
		return true;
			
	}
	
	/**  
	 * isExisted(初始化各渠道来源下单成功的分期单统计记录)  
	 * @param partnerId   
	 * void 
	 * @create time： Nov 4, 2015 3:28:14 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	private void isExisted(String partnerId) throws Exception{
		
		log.info("初始化各渠道来源下单成功的分期单统计记录");
		if(isExisted.equals(DateUtil.formatDate(new Date(), "yyyyMMdd"))){
			return;
		}else{
			synchronized (isExisted) {
				if(isExisted.equals(DateUtil.formatDate(new Date(), "yyyyMMdd"))){
					return;
				}
				
				//查询t_record_partner_order_statistic是否存在对应partnerId
				RecordPartnerOrderStatisticDTO recordPartnerOrderStatistic = new RecordPartnerOrderStatisticDTO();
				String configPartnerId = recordPartnerOrderStatiticService.queryPartner2Id(partnerId);
				
				recordPartnerOrderStatistic.setConfigPartnerId(configPartnerId);
				recordPartnerOrderStatistic.setStatisticTime(DateUtil.transStrToDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"));
				recordPartnerOrderStatistic = recordPartnerOrderStatiticService.queryRecordPartnerOrderStatistic(recordPartnerOrderStatistic);
				
				//不存在，新增
				if(EntityUtils.isEmpty(recordPartnerOrderStatistic)){
					log.info(DateUtil.transStrToDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd") + "日第一次下单！");
					
					RecordPartnerOrderStatisticDTO recordPartnerOrderStatisticDTO = new RecordPartnerOrderStatisticDTO();
					recordPartnerOrderStatisticDTO.setRecordId(GenerateKey.getId(CommonConstants.RECORD_PARTNER_ORDER_STATISTIC_ID_PREFIX, ConfigUtils.getProperty("db_id")));
					recordPartnerOrderStatisticDTO.setConfigPartnerId(configPartnerId);
					recordPartnerOrderStatisticDTO.setSuccessOrderNumber(new BigDecimal(0));
					recordPartnerOrderStatisticDTO.setStatisticTime(DateUtil.transStrToDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"));
					recordPartnerOrderStatisticDTO.setCreateTime(new Date());
					recordPartnerOrderStatisticDTO.setUpdateTime(new Date());
					recordPartnerOrderStatisticDTO.setOperator(CommonConstants.DEFAULT_OPERATOR);
					int result  = recordPartnerOrderStatiticService.insertRecordPartnerOrderStatistic(recordPartnerOrderStatisticDTO);
					if (1 == result ) {
						log.info("插入QueryInstalmentInterceptor--》isExisted--》查询t_record_partner_order_statistic成功,partnerId:" + partnerId);
						isExisted = DateUtil.formatDate(new Date(), "yyyyMMdd");
					}else{
						log.info("插入QueryInstalmentInterceptor--》isExisted--》查询t_record_partner_order_statistic失败,partnerId:" + partnerId);

					}

				}else{
					isExisted = DateUtil.formatDate(new Date(), "yyyyMMdd");
					log.info(isExisted);
				}
			}
		}
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
		public static String  responseParams(Map<String, String> retMap,HttpServletResponse reponse) {
			
			 
			String enc = ConfigUtils.getProperty("inner_mq_plat_enc");
			String desKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");//双方约定的密钥
			String md5Key = ConfigUtils.getProperty("inner_mq_plat_md5_key");
			String partnerId = ConfigUtils.getProperty("inner_mq_plat_partner_id");
			String version = ConfigUtils.getProperty("inner_mq_plat_data_version");

			String params = "";
			try {
				// 生成业务加密串
				String busParams = HkEncryptUtils.createEncryptBusParams(retMap,desKey, enc);
						
				log.info("parseByte2HexStr : " + busParams);

				// 协议参数
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
				paramMap.put("partnerId", partnerId);
				paramMap.put("version", version);
				paramMap.put("params", busParams);

				// 生成验签sign
				String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
				paramMap.put("sign", sign);
				params = HkEncryptUtils.mapToString(paramMap);
				log.info("征信返回API平台数据组装参数：" + params);
				reponse.getWriter().write(params);
				reponse.getWriter().close();
				
				return null;
			} catch (Exception e1) {
				e1.printStackTrace();
				log.error("征信返回数据,生成参数异常", e1);
				throw new RuntimeException("征信返回数据，生成参数异常", e1);
			}
		}
		
		@Override
		public void afterCompletion(HttpServletRequest arg0,
				HttpServletResponse arg1, Object arg2, Exception arg3)
				throws Exception {
			
		}

		@Override
		public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
				Object arg2, ModelAndView arg3) throws Exception {
			
		}
		
}
