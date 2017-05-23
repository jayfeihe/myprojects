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
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.ValidateAPIPlatParamsTools;

public class QueryCollegeInterceptor implements HandlerInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(QueryCollegeInterceptor.class);
	
	@Autowired
	private RecordPartnerOrderStatiticService recordPartnerOrderStatiticService;
	
	private String enc = ConfigUtils.getProperty("inner_college_enc");
	private String desKey = ConfigUtils.getProperty("inner_college_3des_key");
	private String md5Key = ConfigUtils.getProperty("inner_college_md5_key");
	private String partnerId = ConfigUtils.getProperty("inner_college_partner_id");
	private String version = ConfigUtils.getProperty("inner_college_version");
	
	//初始化用户每日访问的日期
	private static String isExisted = "";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse reponse, Object arg2) {
		log.info("校园征信判断接口-->进入拦截器开始");

		Map<String, String> responseMap = new HashMap<String, String>();
		Map<String, String> busMap = ValidateAPIPlatParamsTools.validateParams(request,md5Key , desKey ,enc, partnerId, version);
		
		if (EntityUtils.isNotEmpty(busMap.get("resultCode"))) {
			 log.info("校园征信判断接口-->参数验证未通过：" + busMap.get("desc").toString());
			
			 responseMap.put("resultCode", busMap.get("resultCode"));
			 responseMap.put("desc", busMap.get("desc"));
			 responseParams(responseMap,reponse);
			 return false;
		}	
		
	    log.info("校园征信判断接口开始-->参数验证通过：");
		
		String busId = busMap.get("busId");					//业务表主键
		String userName = busMap.get("userName");			//用户姓名
		String idCard = busMap.get("idCard");				//用户身份证
		String idCardAddress = busMap.get("idCardAddress");	//身份证地址
		String telNumber = busMap.get("telNumber");			//联系电话
		String channelId = busMap.get("channelId");			//渠道id,用户所使用的平台（1：微信 2：APP 3：web）
		String custIp = busMap.get("custIp");				//用户IP地址
		String schoolName = busMap.get("schoolName");		//所在院校--院校全称
		String department = busMap.get("department");		//所在院系--院系全称
	
		log.info("校园征信判断接口开始-->四元组信息：userName:" + userName + "|idCard:" + idCard + "|idCardAddress：" + idCardAddress + "|telNumber：" + telNumber);
		log.info("校园征信判断接口开始-->其他信息：busId：" + busId  + "|channelId:" + channelId + "|custIp:" + custIp + "|schoolName:" + schoolName + "|department:" + department );
				    
		//插入合作伙伴下单限制信息
		try {
			isExisted(request.getParameter("partnerId"));
		} catch (Exception e) {
			e.printStackTrace();
			
			log.info("校园征信判断接口开始-->插入合作伙伴下单限制信息异常：" + request.getParameter("partnerId"));
			responseMap.put("resultCode",  TelecomConstants.API_SYSTEM_EXCEPTION);
			responseMap.put("desc",  TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
			responseParams(responseMap,reponse);
			return false;
			 
		}
		
		log.info("校园征信判断接口-->进入拦截器结束");
		 
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
		public  String  responseParams(Map<String, String> retMap,HttpServletResponse reponse) {
			
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
