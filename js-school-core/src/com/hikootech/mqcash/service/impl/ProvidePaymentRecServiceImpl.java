package com.hikootech.mqcash.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.ProvidePaymentRecDAO;
import com.hikootech.mqcash.po.ProvidePaymentRecord;
import com.hikootech.mqcash.po.UserOrderStatusLog;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.ProvidePaymentRecService;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.service.UserOrderStatusLogService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.HttpClientUtil;

@Service("providePaymentRecService")
public class ProvidePaymentRecServiceImpl implements ProvidePaymentRecService {
	private static Logger logger = LoggerFactory.getLogger(SmOrderServiceImpl.class);

    public static final int RESP_SUCC = 200;

	@Autowired
	private ProvidePaymentRecDAO  providePaymentRecDAO;
	@Autowired
	private ConfigKvService  configKvService;
	@Autowired
	private UserOrderStatusLogService  userOrderStatusLogService;
	@Autowired
	private SysAlarmService  sysAlarmService;
	
	@Override
	public void reqProvidePaymentRec() {
		
		Map<String, String> requestMap = new HashMap<String, String>();
		
		String enc = ConfigUtils.getProperty("outer_api_plat_enc");
		String desKey = ConfigUtils.getProperty("outer_api_plat_3des_key");
		String md5Key = ConfigUtils.getProperty("outer_api_plat_md5_key");
		String partnerId = ConfigUtils.getProperty("outer_api_plat_partner_id");
		String version = ConfigUtils.getProperty("outer_api_plat_data_version");
		String url = ConfigUtils.getProperty("outter_provider_payment_url"); //打款地址
		
		//扫描t_provide_payment_record表，状态为打款中且扫描次数小于配置的次数的数据进行打款发送
		ProvidePaymentRecord ppr = new ProvidePaymentRecord();
		//获取配置扫描次数
		String sendTimes = configKvService.get(CommonConstants.PROVIDER_MAX_SEND_TIMES);
		ppr.setSendTimes(Integer.parseInt(sendTimes));
		ppr.setPaymentStatus(StatusConstants.PAYMENT_STATUS_PAYING);
		List<ProvidePaymentRecord> queryRecordList = providePaymentRecDAO.queryProvidePaymentRec(ppr);
		
		if (queryRecordList == null) {
			logger.info("打款通知接口-->没有符合通知资方打款的订单。");
			return ;
		}
		//调用打款接口
		String params = "";
			
			for (ProvidePaymentRecord queryRecord : queryRecordList) {
				try {
					//通过资方id查询资方名称
					String providerName = providePaymentRecDAO.queryProviderNameById(queryRecord.getProviderId());
					requestMap.put("orderId", queryRecord.getInstalmentId());
					requestMap.put("partnerOrderId", queryRecord.getPartnerOrderId());
					requestMap.put("orderPrice", queryRecord.getPaymentAmount().multiply(new BigDecimal(100)).toString());
					requestMap.put("paymentOrderSeq", queryRecord.getPaymentSeq());
					requestMap.put("provider", providerName);
					requestMap.put("account", queryRecord.getProviderAccountNumber());
					
					//生成业务加密串
					String busParams = HkEncryptUtils.createEncryptBusParams(requestMap, desKey, enc);
					logger.info("打款通知接口-->parseByte2HexStr : " + busParams);
					
					//协议参数
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("timestamp",String.valueOf( System.currentTimeMillis()));
					paramMap.put("partnerId", partnerId);
					paramMap.put("version",version);
					paramMap.put("params", busParams);
					
					//生成验签sign
					String sign = HkEncryptUtils.createMd5Sign(paramMap, md5Key, enc);
					paramMap.put("sign", sign);
					params = HkEncryptUtils.mapToString(paramMap);
					logger.info("打款通知接口-->组装参数：" + params);
					
					//将参数传给电渠
					HttpClientUtil clientUtil = new HttpClientUtil();
					String retParams = null;
					try {
						logger.info("打款通知接口-->url:" + url + ",params:" + params);
						retParams = clientUtil.doPost(url, params);
						logger.info("打款通知接口-->调用api返回参数:" + retParams);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("打款通知接口-->调用api网络异常", e);
					}
					
					//转换为Map
					Map<String,String> resultMap = HkEncryptUtils.stringToMap(retParams);
					
					String bus_params_ret =  resultMap.get("params");
					String partner_id_ret = resultMap.get("partnerId");
					String sign_ret = resultMap.get("sign") ;
					String timestamp_ret = resultMap.get("timestamp");
					String version_ret = resultMap.get("version");
					logger.info("打款通知接口-->api返回协议结果："  + ",params:" + bus_params_ret + ",partnerId" + partner_id_ret + 
							",sign" + sign_ret + ",timestamp" + timestamp_ret + 
							",version" + version_ret );
					
					//验签参数
					Map<String,String> signMap = new HashMap<String,String>();
					signMap.put("params", bus_params_ret);
					signMap.put("partnerId", partner_id_ret);
					signMap.put("timestamp", timestamp_ret);
					signMap.put("version", version_ret);
					
					String _sign = HkEncryptUtils.createMd5Sign(signMap, md5Key, enc);
					
					logger.info("_sign:" + _sign);
					
					if(sign_ret.equals(_sign)){
						logger.info("打款通知接口-->api返回参数验签通过");
						Map<String, String> busMap = HkEncryptUtils.createDecryptBusMap(bus_params_ret, desKey, enc);
							
						if (busMap == null) {
							logger.error("打款通知接口-->busMap == null" + ",instalmentId:" + queryRecord.getInstalmentId());
							throw new RuntimeException("打款通知接口-->busMap == null" + ",instalmentId:" + queryRecord.getInstalmentId());
						}
						
						//将打款中的订单修改为打款成功
						ProvidePaymentRecord paymentResult = new ProvidePaymentRecord();
						Date date = new Date();
						paymentResult.setInstalmentId(queryRecord.getInstalmentId());
						paymentResult.setPaymentSeq(queryRecord.getPaymentSeq());
						paymentResult.setPartnerOrderId(queryRecord.getPartnerOrderId());
						paymentResult.setUpdateTime(date);
						paymentResult.setOperator(CommonConstants.DEFAULT_OPERATOR);
						
						boolean resultCode =false;
						try {
							resultCode = Boolean.parseBoolean(busMap.get("success"));
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("打款通知接口-->获取打款处理结果异常" + ",instalmentId:" + queryRecord.getInstalmentId() + ",api返回打款处理结果；" + busMap.get("result_code"));
							throw new RuntimeException("打款通知接口-->获取打款处理结果异常" + ",instalmentId:" + queryRecord.getInstalmentId()+ ",api返回打款处理结果；" + busMap.get("result_code"),e);
						}
						
						logger.info("打款通知接口-->获取打款处理结果：success:" + busMap.get("success") + ",errorCode:" + busMap.get("errorCode") + ",errorMsg" + busMap.get("errorMsg"));
						
						if (resultCode) {
								logger.info("打款通知接口-->api返回打款成功：" + busMap.get("result_code") + ",结果描述：" + busMap.get("desc"));
								paymentResult.setSendTimes(queryRecord.getSendTimes() + 1);
								paymentResult.setPaymentTime(date);//打款时间
								paymentResult.setPaymentStatus(StatusConstants.PAYMENT_STATUS_PAYED);//已打款
						}else{
								logger.info("打款通知接口-->api返回打款结果：" + busMap.get("result_code") + ",结果描述：" + busMap.get("desc"));
								paymentResult.setSendTimes(queryRecord.getSendTimes() + 1);
								paymentResult.setPaymentTime(date);//打款时间
								paymentResult.setPaymentStatus(StatusConstants.PAYMENT_STATUS_PAYFAIL);//打款失败
						}
						
						providePaymentRecDAO.updateProvidePaymentRecord(paymentResult);
						logger.info("打款通知接口结束");
						
					}else{
						logger.error("打款通知接口-->返回打款验签不通过,获得验签参数：" + sign_ret + ",生成验签参数：" + _sign);
						throw new RuntimeException("通知api打款调度-->返回打款验签不通过,获得验签参数：" + sign_ret + ",生成验签参数：" + _sign + ",instalmentId:" + queryRecord.getInstalmentId());
					}
				
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("打款通知接口-->生成参数异常，instalmentId:" + queryRecord.getInstalmentId(), e1);
					throw new RuntimeException("打款通知接口-->生成参数异常，instalmentId:" + queryRecord.getInstalmentId(), e1);
				}
			}

	}
	
	
	
	@Override
	public void reqProvidePaymentRecT() {
		Date startTime = new Date();
		
		Map<String, String> requestMap = new HashMap<String, String>();
		
		String url = ConfigUtils.getProperty("outter_provider_payment_url"); //打款地址
		
		//扫描t_provide_payment_record表，状态为打款中且扫描次数小于配置的次数的数据进行打款发送
		ProvidePaymentRecord ppr = new ProvidePaymentRecord();
		//获取配置扫描次数
		String sendTimes = configKvService.get(CommonConstants.PROVIDER_MAX_SEND_TIMES);
		ppr.setSendTimes(Integer.parseInt(sendTimes));
		ppr.setPaymentStatus(StatusConstants.PAYMENT_STATUS_PAYING);
		List<ProvidePaymentRecord> queryRecordList = providePaymentRecDAO.queryProvidePaymentRec(ppr);
		
		if (queryRecordList == null) {
			logger.info("打款通知接口-->没有符合通知资方打款的订单。");
			return ;
		}
		//调用打款接口
			for(ProvidePaymentRecord queryRecord : queryRecordList){
				try {
			    	com.alibaba.fastjson.JSONObject requestJSON = new com.alibaba.fastjson.JSONObject();

					//通过资方id查询资方名称
					String providerName = providePaymentRecDAO.queryProviderNameById(queryRecord.getProviderId());
					requestJSON.put("transId", queryRecord.getRecordId());
					requestJSON.put("otherOrderId", queryRecord.getInstalmentId());
					requestJSON.put("orderId", queryRecord.getPartnerOrderId());
					requestJSON.put("orderCost", queryRecord.getPaymentAmount().multiply(new BigDecimal(100)).toString());
					requestJSON.put("payTrandId", queryRecord.getPaymentSeq());
					requestJSON.put("provider", providerName);
					requestJSON.put("account", queryRecord.getProviderAccountNumber());
					
					logger.info("打款通知接口-->组装参数：" + requestJSON.toJSONString());
					
					//将参数传给电渠
					JSONArray jsonArray;
					boolean result = false;
					String errorCode = "";
					String errorMsg = "";
					
					Client client = ClientBuilder.newClient();
			        WebTarget target = client.target(url);
			        Response response = target.request().post(Entity.entity(requestJSON.toString(), MediaType.APPLICATION_JSON_TYPE));

			        try {
			        	logger.info("打款通知接口-->调用打款接口，返回状态：" + response.getStatus());
			            if (response.getStatus() == RESP_SUCC) {
			                String retParams = response.readEntity(String.class);
			                
			                try {
								retParams = retParams.replace("{", "[{").replace("}", "}]");
								jsonArray = new JSONArray(retParams);
								int iSize = jsonArray.length();
									for (int i = 0; i < iSize; i++) {
										JSONObject jsonObj = jsonArray.getJSONObject(0);
										 result = (boolean) jsonObj.get("success");
										 errorCode = (String) jsonObj.get("errorCode");
										 errorMsg = (String) jsonObj.get("errorMsg");
										
										logger.info("通知返回结果，success : " + result + ",errorCode=" + errorCode + ",errorMsg=" + errorMsg);
										
										Map<String, Object> paramMap = new HashMap<String, Object>();

										ProvidePaymentRecord paymentResult = new ProvidePaymentRecord();
										Date date = new Date();
										paymentResult.setInstalmentId(queryRecord.getInstalmentId());
										paymentResult.setPaymentSeq(queryRecord.getPaymentSeq());
										paymentResult.setPartnerOrderId(queryRecord.getPartnerOrderId());
										paymentResult.setUpdateTime(date);
										paymentResult.setOperator(CommonConstants.DEFAULT_OPERATOR);
										
										if (result) {
											logger.info("打款接口返回成功");
											
											logger.info("打款通知接口-->获取打款处理结果：success:" + result + ",errorCode:" + errorCode + ",errorMsg" + errorMsg);
											
											paymentResult.setSendTimes(queryRecord.getSendTimes() + 1);
											paymentResult.setPaymentTime(date);//打款时间
											paymentResult.setPaymentStatus(StatusConstants.PAYMENT_STATUS_PAYED);//已打款
											paramMap.put("providerStatus", StatusConstants.INSTALMENT_PROVIDER_STATUS_PAY_SUCCESS);//打款成功-->已打款
											
										}else{
											logger.info("打款接口结果返回失败");
											logger.info("打款通知接口-->api返回打款结果：" + result + ",errorCode:" + errorCode + ",errorMsg" + errorMsg);
											String alarmContent = "" ;
											paymentResult.setPaymentTime(date);//打款时间
											if (10 == queryRecord.getSendTimes().intValue()) {
												paymentResult.setPaymentStatus(StatusConstants.PAYMENT_STATUS_PAYFAIL);//打款失败
												alarmContent=queryRecord.getInstalmentId() + "：第" + queryRecord.getSendTimes().intValue() + "次打款失败，api返回打款结果：" + result + ",errorCode:" + errorCode + ",errorMsg" + errorMsg;
											}else{
												paymentResult.setPaymentStatus(StatusConstants.PAYMENT_STATUS_PAYING);//打款中
												alarmContent=queryRecord.getInstalmentId() + "：第" + queryRecord.getSendTimes().intValue() + "次打款失败，api返回打款结果：" + result + ",errorCode:" + errorCode + ",errorMsg" + errorMsg;

											}
											paymentResult.setSendTimes(queryRecord.getSendTimes() + 1);
											paramMap.put("providerStatus", StatusConstants.INSTALMENT_PROVIDER_STATUS_COMPLETE_BATCH);//打款失败-->26融资完成 

											sysAlarmService.alarm(alarmContent);
										}
										
										providePaymentRecDAO.updateProvidePaymentRecord(paymentResult);
										paramMap.put("instalmentId", queryRecord.getInstalmentId());
										paramMap.put("updateTime", date);
										providePaymentRecDAO.updateProvideStatus(paramMap);
										
										//记录打款状态变化和时间
										String id  = GenerateKey.getId(CommonConstants.USER_ORDER_STATUS_LOG_ID_PREFIX, ConfigUtils.getProperty("db_id"));
										String desp = "打款接口返回结果：" + result + ",errorCode:" + errorCode + ",errorMsg" + errorMsg;
										String actionType = StatusConstants.STEP_TRANSFER_ACTION_TYPE.toString();
										Date endTime = new Date();
										String operator = CommonConstants.DEFAULT_OPERATOR;
										
										logger.info("插入" + desp + ",参数：id:" + id + ",instalmentId:" + queryRecord.getInstalmentId() + 
												",totalInfoId:" + queryRecord.getInfoId()  + ",actionType:" +actionType + 
												",startTime:"  + startTime + ",endTime:"  + endTime +
												",operator:"  + operator + ",desc:"  + desp);
										
										UserOrderStatusLog orderStatusLog = new UserOrderStatusLog();
										orderStatusLog.setId(id);
										orderStatusLog.setInstalmentId(queryRecord.getInstalmentId());
										orderStatusLog.setTotalInfoId(queryRecord.getInfoId());
										orderStatusLog.setActionType(actionType);
										orderStatusLog.setStartTime(startTime);
										orderStatusLog.setEndTime(endTime);
										orderStatusLog.setCreateTime(endTime);
										orderStatusLog.setOperator(CommonConstants.DEFAULT_OPERATOR);
										orderStatusLog.setDescp(desp);
										if (1 == userOrderStatusLogService.insertUserOrderStatusLog(orderStatusLog)) {
											logger.info("插入" + desp + "成功");
										}else{
											logger.info("插入" + desp + "失败" );
										}
									
									}
							} catch (Exception e) {
								logger.error("打款通知接口-->解析api返回打款信息异常", e);
								throw new RuntimeException("打款通知接口-->解析api返回打款信息异常",e);
							}
							logger.info("打款通知接口结束");
			                
			            }else{
			            	logger.info("打款通知接口-->调用打款接口，返回状态：" + response.getStatus());
			            }
			        } finally {
			            response.close();
			            client.close();
			        }
			        
	
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("打款通知接口-->生成参数异常，instalmentId:" + queryRecord.getInstalmentId(), e1);
				}
			}
	}
	
	public static void main(String[] args) {
		Random random = new Random();
		String ranStr = "" ;
		for (int i = 0; i < 6; i++) {
			ranStr = ranStr + random.nextInt(10) ;
		}
		String strCurDate = DateUtil.formatDate(new Date(), DateUtil.FORMAT_SS_NO_SYMBOL);
		System.out.println(ranStr + strCurDate);
	}

}
