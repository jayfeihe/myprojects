package com.hikootech.mqcash.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.TelecomConstants;
import com.hikootech.mqcash.dao.SysAlarmDAO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.exception.MQExceptionConstants;
import com.hikootech.mqcash.po.SysAlarm;
import com.hikootech.mqcash.po.SysAlarmRule;
import com.hikootech.mqcash.service.SysAlarmService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
@Service("sysAlarmService")
public class SysAlarmServiceImpl implements SysAlarmService {
	private static Logger log = LoggerFactory.getLogger(SysAlarmServiceImpl.class);

	@Autowired
	private SysAlarmDAO sysAlarmDAO;
	@Override
	public int insertSysAlarmPo(String alarmSystem,String alarmLevel,String alarmNoticeType ,String alarmContent ,int emailType,int sendStatus) throws Exception {
		
		SysAlarm sysAlarm = new SysAlarm();
		String  alramId  = GenerateKey.getId(CommonConstants.SYS_ALARM_ID_PREFIX, ConfigUtils.getProperty("db_id"));
		sysAlarm.setAlarmId(alramId);
		sysAlarm.setAlarmSystem(alarmSystem);
		sysAlarm.setAlarmLevel(alarmLevel);
		sysAlarm.setAlarmNoticeType(alarmNoticeType);
		sysAlarm.setAlarmContent(alarmContent);
		sysAlarm.setEmailType(emailType);
		sysAlarm.setSendStatus(sendStatus);
		sysAlarm.setSendTime(null);
		sysAlarm.setCreateTime(new Date());
		return sysAlarmDAO.insertSysAlarmPo(sysAlarm);
	}
	public SysAlarmRule querySysAlarmRule(int ruleType) throws Exception {
		return sysAlarmDAO.querySysAlarmRule(ruleType);
	}

	@Override
	public String queryCreditSucList(Map<String, Object> map) {
		return sysAlarmDAO.queryCreditSucList(map);
	}
	@Override
	public String queryBindingCardList(Map<String, Object> map) {
		return sysAlarmDAO.queryBindingCardList(map);
	}
	@Override
	public String queryOrderFinishList(Map<String, Object> map) {
		return sysAlarmDAO.queryOrderFinishList(map);
	}
	
	@Override
	public Map<String, String>  exceptionAlarm(String alarmContent){
		
		// 插入告警表
		try {
			insertSysAlarmPo(StatusConstants.ALARM_SYSTEM_CORE, StatusConstants.ALARM_LEVEL_HIGH,
					StatusConstants.ALARM_NOTICE_EMAIL_TYPE, alarmContent, StatusConstants.SEND_SYS_ALARM_EMAIL,StatusConstants.SEND_STATUS_NO);
		} catch (Exception e1) {
			log.error(alarmContent, e1);
		}
			
		Map<String, String> returnMap = new HashMap<String,String>();
		returnMap.put(ResponseConstants.RETURN_CODE,TelecomConstants.API_SYSTEM_EXCEPTION);
		returnMap.put(ResponseConstants.RETURN_DESC, TelecomConstants.API_SYSTEM_EXCEPTION_DESC);
		return returnMap; 
	}
	
	@Override
	public void alarm(String alarmContent){
		// 插入告警表
		try {
			insertSysAlarmPo(StatusConstants.ALARM_SYSTEM_CORE, StatusConstants.ALARM_LEVEL_HIGH,
					StatusConstants.ALARM_NOTICE_EMAIL_TYPE, alarmContent, StatusConstants.SEND_SYS_ALARM_EMAIL, StatusConstants.SEND_STATUS_NO);
		} catch (Exception e1) {
			log.error(alarmContent, e1);
		}
	}
	
	@Override
	public void insertSysAlarm(SysAlarm sysAlarm) throws MQException {
		// TODO Auto-generated method stub
		try {
			sysAlarmDAO.insertSysAlarmPo(sysAlarm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增告警出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public Map<String, Object> inwardCollectionAlarm(
			Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		log.info("代收告警");
		if(EntityUtils.isEmpty(paramMap.get("failureCount"))){
			log.error("代收告警参数为空");
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC);
		}
		
		Integer failureCount = (Integer) paramMap.get("failureCount");
		String alarmContent = DateUtil.transDateToStr(new Date()) + "重复代收失败，总共" + failureCount + "条记录扣款失败!";
		
		SysAlarm sysAlarm = new SysAlarm();
		sysAlarm.setAlarmId(GenerateKey.getId(CommonConstants.SYS_ALARM_ID_PREFIX, ConfigUtils.getProperty("db_id")));
		sysAlarm.setAlarmSystem(StatusConstants.ALARM_SYSTEM_CORE);
		sysAlarm.setAlarmLevel(StatusConstants.ALARM_LEVEL_HIGH);
		sysAlarm.setAlarmNoticeType(StatusConstants.ALARM_NOTICE_EMAIL_TYPE);
		sysAlarm.setAlarmContent(alarmContent);
		sysAlarm.setEmailType(StatusConstants.EMAIL_TYPE_BUS_ALARM);
		sysAlarm.setSendStatus(StatusConstants.SEND_STATUS_NO);
		sysAlarm.setCreateTime(new Date());
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = this.Alarm(sysAlarm);
		
		return retMap;
	}

	@Override
	public Map<String, Object> Alarm(SysAlarm sysAlarm)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("告警");
		if(EntityUtils.isEmpty(sysAlarm) 
				|| EntityUtils.isEmpty(sysAlarm.getAlarmSystem())
				|| EntityUtils.isEmpty(sysAlarm.getAlarmLevel())
				|| EntityUtils.isEmpty(sysAlarm.getAlarmNoticeType())
				){
			log.error("告警参数为空");
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC);
		}
		
		if(StatusConstants.ALARM_NOTICE_EMAIL_TYPE.equals(sysAlarm.getAlarmNoticeType())){
			//发邮件
			
		}else if(StatusConstants.ALARM_NOTICE_SMS_TYPE.equals(sysAlarm.getAlarmNoticeType())){
			//发短信
			
		}else if(StatusConstants.ALARM_NOTICE_EMS_TYPE.equals(sysAlarm.getAlarmNoticeType())){
			//发邮件和短信
			
		}
		
		//入库
		this.insertSysAlarm(sysAlarm);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "告警成功");
		
		return retMap;
	}
	@Override
	public Map<String, Object> calOverdueFailedAlarm(
			Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		log.info("新增计算逾期罚息失败告警");
		if(EntityUtils.isEmpty(paramMap.get("plansId")) 
				|| EntityUtils.isEmpty(paramMap.get("instalmentId"))
				|| EntityUtils.isEmpty(paramMap.get("planRepaymentTime"))){
			log.error("新增计算逾期罚息失败告警参数为空");
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC);
		}
		
		String plansId = (String) paramMap.get("plansId");
		String instalmentId = (String) paramMap.get("instalmentId");
		Date planRepaymentTime = (Date) paramMap.get("planRepaymentTime");
		String alarmContent = "计算逾期罚息失败，分期订单ID" + instalmentId + "分期还款计划ID：" + plansId + "计划还款时间" + DateUtil.transDateToStr(planRepaymentTime);
		
		SysAlarm sysAlarm = new SysAlarm();
		sysAlarm.setAlarmId(GenerateKey.getId(CommonConstants.SYS_ALARM_ID_PREFIX, ConfigUtils.getProperty("db_id")));
		sysAlarm.setAlarmSystem(StatusConstants.ALARM_SYSTEM_CORE);
		sysAlarm.setAlarmLevel(StatusConstants.ALARM_LEVEL_HIGH);
		sysAlarm.setAlarmNoticeType(StatusConstants.ALARM_NOTICE_EMAIL_TYPE);
		sysAlarm.setAlarmContent(alarmContent);
		sysAlarm.setEmailType(StatusConstants.EMAIL_TYPE_BUS_ALARM);
		sysAlarm.setSendStatus(StatusConstants.SEND_STATUS_NO);
		sysAlarm.setCreateTime(new Date());
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = this.Alarm(sysAlarm);
		
		return retMap;
	}
	
}
