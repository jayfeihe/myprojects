package com.hikootech.mqcash.service.impl;

import java.util.Calendar;
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
import com.hikootech.mqcash.po.LogUserInfo;
import com.hikootech.mqcash.po.SmLog;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.service.LogService;
import com.hikootech.mqcash.service.UpdateUserInfoService;
import com.hikootech.mqcash.service.UserService;
import com.hikootech.mqcash.util.CommonUtils;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.DesUtil;
import com.hikootech.mqcash.util.DigestUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.SmUtils;
import com.hikootech.mqcash.util.UserUtils;
import com.hikootech.mqcash.util.ValidateTools;

import net.sf.json.JSONObject;

@Service("updateUserInfoService")
public class UpdateUserInfoServiceImpl implements UpdateUserInfoService {

	private static Logger log = LoggerFactory.getLogger(UpdateUserInfoServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	@Override
	public void validateValid(String smCodeCache, Date smCodeStartTimeCache, String upValiCodeCache,
			Date upValiCodeStartTimeCache, String smCode, String mobileNumber, String validationCode) {

		//获取参数配置
		int smCodeEffectTime = 1;
		int upValiCodeEffectTime = 1;
		try {
			smCodeEffectTime = Integer.parseInt(ConfigUtils.getProperty("mqcash_mobile_login_pwd_effective_time"))
					/ 60;
		} catch (NumberFormatException e) {
			
			log.error("获取手机验证码有效时间失败", e);
		}
		try {
			upValiCodeEffectTime = Integer
					.parseInt(ConfigUtils.getProperty("mqcash_mobile_login_validation_code_effective_time")) / 60;
		} catch (NumberFormatException e) {
			log.error("获取验证码有效时间失败", e);
		}

		Date now = new Date();

		//校验短信验证码
		if (smCode != null) {
			Date pwdExpireTime = DateUtil.addDate(smCodeStartTimeCache, Calendar.MINUTE, smCodeEffectTime);
			if (now.compareTo(pwdExpireTime) > 0) {
				throw new RuntimeException("动态验证码过期，请重新获取！");
			} else if (!smCodeCache.equals(mobileNumber.trim() + ",,," + smCode.trim())) {
				throw new RuntimeException("动态验证码错误！");
			}
		}
		
		//校验图片验证码
		if (validationCode != null) {
			Date validationCodeExpireTime = DateUtil.addDate(upValiCodeStartTimeCache, Calendar.MINUTE,
					upValiCodeEffectTime);
			if (now.compareTo(validationCodeExpireTime) > 0) {
				throw new RuntimeException("图形验证码过期，请重新输入！");
			} else if (!upValiCodeCache.equalsIgnoreCase(validationCode)) {
				throw new RuntimeException("图形验证码错误！");
			}
		}
	}

	@Override
	public Map<String, Object> modifyInfoSub(UserInfo userInfoSession, UserInfo userInfo) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		// ===================将缓存信息装入 日志类start============================
		LogUserInfo logUserInfo = new LogUserInfo(userInfoSession);
		logUserInfo.setLogId(GenerateKey.getId(CommonConstants.LOG_USER_INFO_PREFIX, ConfigUtils.getProperty("db_id")));
		logUserInfo.setOperationType(CommonConstants.LOG_USER_INFO_UP_BASE);
		// ====================将缓存信息装入 日志类 end===========================
		
		//用于记录日志
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("companyName", userInfo.getCompanyName());
		userMap.put("interesting", userInfo.getInteresting());
		userMap.put("provinceId", userInfo.getProvinceId());
		userMap.put("cityId", userInfo.getCityId());
		userMap.put("areaId", userInfo.getAreaId());
		userMap.put("homeAddress", userInfo.getHomeAddress());
		userMap.put("companyProvinceId", userInfo.getCompanyProvinceId());
		userMap.put("companyCityId", userInfo.getCompanyCityId());
		userMap.put("companyAreaId", userInfo.getCompanyAreaId());
		userMap.put("companyAddress", userInfo.getCompanyAddress());
		userMap.put("headImgUrl", userInfo.getHeadImgUrl());
		logUserInfo.setOperationContent(JSONObject.fromObject(userMap).toString().replace(" ", ""));

		//修改个人信息
		try {
			userService.updateUserBaseInfo(userInfo);
			
			//记录操作日志
			try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_SUCCESS);
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e) {
				log.error("插入用户操作记录失败：", e);
			}
			
		} catch (Exception e) {
			log.error("提交修改用户信息失败:" , e);
			
			//记录操作日志
			try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_FAIL);
				logUserInfo.setDescp(EntityUtils.isEmpty(e.toString()) ? "提交修改用户信息失败": "提交修改用户信息失败:" + e.toString().substring(0,e.toString().length() < 100 ? e.toString().length() : 100));
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e2) {
				log.error("插入用户操作记录失败：" ,e2);
			}
			
			//返回错误信息
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return retMap;
		}
		
		//返回成功信息
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "修改用户信息成功！");
		return retMap;
	}

	@Override
	public Map<String, Object> updatePwdByMobile(String newPwd,UserInfo userInfo) {
		Map<String, Object> retMap=new HashMap<String,Object>();
		
		//获取原等级
		int pwdLevel=userInfo.getPwdLevel();
		
		//===================将缓存信息装入 日志类start============================
		LogUserInfo logUserInfo=new LogUserInfo(userInfo);
		logUserInfo.setLogId(GenerateKey.getId(CommonConstants.LOG_USER_INFO_PREFIX, ConfigUtils.getProperty("db_id")));
		logUserInfo.setOperationType(CommonConstants.LOG_USER_INFO_UP_PSW);
		//====================将缓存信息装入 日志类 end===========================
		
		String mobileNumber = userInfo.getBindMobile();
		
		//des解密
		newPwd=DesUtil.getDesUtil().strDec(newPwd);
		
		//验证密码是否正确
		if(!ValidateTools.validateUserPwd(newPwd)){
			log.error("通过 手机方式修改密码，密码没有通过后台验证");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请按照规则填写密码！");
			return retMap;
		}
		
		//md5加密
		newPwd=DigestUtil.getKeyedDigest(newPwd, "");
		
		//校验无误后，修改密码
		try {
			//直接修改为高级
			userInfo.setPwdLevel(2);
			userService.updateUserPwd(userInfo.getIdCard(), newPwd,userInfo.getPwdLevel());
			
			//插入操作日志
			try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_SUCCESS);
				logUserInfo.setDescp("手机方式修改密码成功");
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e) {
				log.error("插入用户操作记录表失败：",e);
			}
		
		} catch (Exception e) {
			
			//更改密码失败，修改为原来标志
			userInfo.setPwdLevel(pwdLevel);
			
			//插入操作日志
			try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_SUCCESS);
				logUserInfo.setDescp(EntityUtils.isEmpty(e.getMessage())?"手机方式修改密码失败":"手机方式修改密码失败:"+e.getMessage().substring(0,e.getMessage().length()<100?e.getMessage().length():100));
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e2) {
				log.error("插入用户操作记录表失败：",e2);
			}
			
			log.error("修改密码失败，",e);
			
			//返回失败信息
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "修改密码失败！");
			return retMap;
			
		}
		
		//返回成功信息
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "修改密码成功！");
		return retMap;
	}

	@Override
	public Map<String, Object> updatePwdByPwd(String oldPwd,String newPwd,UserInfo userInfo) {
		Map<String, Object> retMap=new HashMap<String,Object>();
		
		//获取原等级
		int pwdLevel=userInfo.getPwdLevel();
		
		//===================将缓存信息装入 日志类start============================
		LogUserInfo logUserInfo=new LogUserInfo(userInfo);
		logUserInfo.setLogId(GenerateKey.getId(CommonConstants.LOG_USER_INFO_PREFIX, ConfigUtils.getProperty("db_id")));
		logUserInfo.setOperationType(CommonConstants.LOG_USER_INFO_UP_PSW);
		//====================将缓存信息装入 日志类 end===========================
		 
		//des解密
		newPwd=DesUtil.getDesUtil().strDec(newPwd);
		
		//验证密码是否正确
		if(!ValidateTools.validateUserPwd(newPwd)){
			log.error("通过 手机方式修改密码，密码没有通过后台验证");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请按照规则填写密码！");
			return retMap;
		}
		
		//md5加密
		newPwd=DigestUtil.getKeyedDigest(newPwd, ""); 
		
		//校验无误后,修改密码
		try {
			//直接修改为高级
			userInfo.setPwdLevel(2);
			 userService.updateUserPwd(userInfo.getIdCard(), newPwd,oldPwd,userInfo.getPwdLevel());
			
			 //记录操作日志
			 try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_SUCCESS);
				logUserInfo.setDescp("原密码方式修改密码成功");
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e) {
				log.error("插入用户操作记录表失败：",e);
			}
		} catch (Exception e) {
			//更改密码失败，修改为原来标志
			userInfo.setPwdLevel(pwdLevel);
			log.error("通过 密码验证方式修改密码发生错误：",e);
			 
			//记录操作日志
			try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_SUCCESS);
				logUserInfo.setDescp(EntityUtils.isEmpty(e.getMessage())?"原密码方式修改密码失败":"原密码方式修改密码失败:"+e.getMessage().substring(0,e.getMessage().length()<100?e.getMessage().length():100));
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e1) {
				log.error("插入用户操作记录表失败：",e1);
			}
			
			//返回失败信息
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "修改密码失败！");
			return retMap;
		}
		
		//返回成功信息
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "修改密码成功！");
		return retMap;
	}

	@Override
	public Map<String, Object> updateUserBingMobile(String smCode,String mobileNumberNew,UserInfo userInfo) {
		
		Map<String, Object> retMap=new HashMap<String,Object>();
		
		//===================将缓存信息装入 日志类start============================
				
		LogUserInfo logUserInfo=new LogUserInfo(userInfo);
		logUserInfo.setLogId(GenerateKey.getId(CommonConstants.LOG_USER_INFO_PREFIX, ConfigUtils.getProperty("db_id")));
		logUserInfo.setOperationType(CommonConstants.LOG_USER_INFO_UP_MOB);
		//====================将缓存信息装入 日志类 end===========================
		
		String mobileNumberOld =userInfo.getBindMobile();
				
		//将新手机号存入数据库
		userInfo.setBindMobile(mobileNumberNew);
		logUserInfo.setOperationContent("新手机号为："+mobileNumberNew);
		
		try {
			
			//根据新手机号查询库中是否已经存在绑定该手机的有效用户
			UserInfo userInfoTemp=userService.queryEffectiveUserInfoByBindMobile(mobileNumberNew);
			
			//如果用户存在则应先将其状态置为无效
			if(EntityUtils.isNotEmpty(userInfoTemp)){
				log.info("有效用户已经存在，准备将其置为无效,userid为："+userInfoTemp.getUserId());
				userService.invalidUser(userInfoTemp.getIdCard());
			}
			
			//修改手机号
			userService.updateBindMobile(userInfo);
			
			//插入操作记录
			try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_SUCCESS);
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e) {
				
				log.error("插入用户操作记录表失败：",e);
			}
			
		} catch (Exception e) {
			
			log.error("修改绑定手机号发生错误：",e);
			//更改失败时，将手机号置为原来手机号
			userInfo.setBindMobile(mobileNumberOld);

			//插入操作记录
			try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_FAIL);
				logUserInfo.setDescp(EntityUtils.isEmpty(e.getMessage())?"绑定新手机失败":"绑定新手机失败:"+e.getMessage().substring(0,e.getMessage().length()<100?e.getMessage().length():100));
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e1) {
				
				log.error("插入用户操作记录表失败：",e1);
			}
			
			//返回错误信息
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "绑定新手机失败！");
			return retMap;
		}
		
		//返回成功信息
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "新绑定手机成功。");
		return retMap;
	}

	@Override
	public Map<String,Object> sendSmsCodeForUpdate(String smCodeCache,String mobileNumCache,Date smCodeStartTimeCache,int smCodeEffectiveTime,
			int interval,String mobileNumber,String templateId,String customerServicePhoneNumber ) {
		
		Map<String,Object> retMap=new HashMap<String,Object>();
		Date now = new Date();
		
		int length =  CommonConstants.MQCASH_MOBILE_LOGIN_PWD_LENGTH;;
		
		//判断发送短信验证码是否距离上次发送时间在60s内，若在则不发送返回错误，超出60重发
		if(EntityUtils.isEmpty(smCodeCache) && EntityUtils.isEmpty(mobileNumCache)){
			log.info("第一次获取动态密码");
			smCodeCache = CommonUtils.randomNumber(length);
			smCodeStartTimeCache = now;
		}else{
			Date expireTime = DateUtil.addDate(smCodeStartTimeCache, Calendar.MINUTE, smCodeEffectiveTime);
			Date nextTime = DateUtil.addDate(smCodeStartTimeCache, Calendar.MINUTE, interval);
			
			if(now.compareTo(nextTime) < 0){
				log.info("请求发送短信验证码频率过快，请60s后尝试！");
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "请求发送短信验证码频率过快，请稍后再尝试！");
				return retMap;
			}
			if(!mobileNumCache.equals(mobileNumber)){
				log.info("换一个账号登陆");
				smCodeCache = CommonUtils.randomNumber(length);
				smCodeStartTimeCache = now;
			}
			if(now.compareTo(expireTime) > 0){
				log.error("秒趣分期手机验证码过期！");
				smCodeCache = CommonUtils.randomNumber(length);
				smCodeStartTimeCache = now;
			}else{
				log.error("绑定手机号登录短信验证码未过期！");
				smCodeCache = CommonUtils.randomNumber(length);
				smCodeStartTimeCache = now;
			}
		}
		
		
		log.info("发送短信给用户，验证码：" + smCodeCache);
//		String[] sendContent=new String[]{smCodeCache, smCodeEffectiveTime + "",mobileNumber,customerServicePhoneNumber};
		String[] sendContent=new String[]{smCodeCache, smCodeEffectiveTime + "",mobileNumber};
		//调用云通讯发送
		boolean isSucces = SmUtils.sendMsg(mobileNumber, sendContent, templateId);
		try {
			SmLog smLog=new SmLog();
			smLog.setSmOrderId(GenerateKey.getId(CommonConstants.SM_ORDER_ID_PREFIX, ConfigUtils.getProperty("db_id")));
			smLog.setMobileNumber(mobileNumber);
			smLog.setData(CommonUtils.join(sendContent, CommonConstants.SM_DATA_SEPARATOR));
			smLog.setSendType(StatusConstants.SEND_SMS_TYPE_NOW);
			smLog.setBookTime(DateUtil.getCurDate());
			smLog.setSendTime(DateUtil.getCurDate());
			smLog.setSendStatus(isSucces?StatusConstants.SEND_SMS_SUCCESS:StatusConstants.SEND_SMS_FAILED);
			smLog.setTemplateId(templateId);
			smLog.setCreateTime(DateUtil.getCurDate());
			smLog.setUpdateTime(DateUtil.getCurDate());
			smLog.setOperator(CommonConstants.DEFAULT_OPERATOR);
			logService.insertSmLog(smLog);
		} catch (Exception e) {
			log.error("向短信记录表中插入记录失败：",e);
		}

		//返回错误信息
		if(!isSucces){
			log.error("发送短信失败，云通讯返回结果为false！！");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "验证码发送失败，请重试！");
		}
		
		//返回成功信息
		retMap.put("smCode", smCodeCache);
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "动态验证码发送成功。");
		return retMap;
	}

	@Override
	public Map<String, Object> updateInitPwdByPwd(String oldPwd, String newPwd, UserInfo userInfo) {
		Map<String, Object> retMap=new HashMap<String,Object>();
		
		//获取原等级
		int pwdLevel=userInfo.getPwdLevel();
		
		//===================将缓存信息装入 日志类start============================
		LogUserInfo logUserInfo=new LogUserInfo(userInfo);
		logUserInfo.setLogId(GenerateKey.getId(CommonConstants.LOG_USER_INFO_PREFIX, ConfigUtils.getProperty("db_id")));
		logUserInfo.setOperationType(CommonConstants.LOG_USER_INFO_UP_PSW);
		//====================将缓存信息装入 日志类 end===========================
		 
		//des解密
		newPwd=DesUtil.getDesUtil().strDec(newPwd);
		
		//验证密码是否正确
		if(!ValidateTools.validateUserPwd(newPwd)){
			log.error("修改初始密码，密码没有通过后台验证");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请按照规则填写密码！");
			return retMap;
		}
		
		//md5加密
		newPwd=DigestUtil.getKeyedDigest(newPwd, ""); 
		
		//校验无误后,修改密码
		try {
			//直接修改为高级
			userInfo.setPwdLevel(2);
			 userService.updateUserInitPwd(userInfo.getIdCard(), newPwd,oldPwd,userInfo.getPwdLevel());
			
			 //记录操作日志
			 try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_SUCCESS);
				logUserInfo.setDescp("初始密码修改成功");
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e) {
				log.error("插入用户操作记录表失败：",e);
			}
		} catch (Exception e) {
			//更改密码失败，修改为原来标志
			userInfo.setPwdLevel(pwdLevel);
			log.error("修改初始密码发生错误：",e);
			 
			//记录操作日志
			try {
				logUserInfo.setOperationResult(CommonConstants.LOG_USER_INFO_UP_SUCCESS);
				logUserInfo.setDescp(EntityUtils.isEmpty(e.getMessage())?"修改初始密码失败":"修改初始密码失败:"+e.getMessage().substring(0,e.getMessage().length()<100?e.getMessage().length():100));
				logService.addLogUserInfo(logUserInfo);
			} catch (Exception e1) {
				log.error("插入用户操作记录表失败：",e1);
			}
			
			//返回失败信息
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "修改初始密码失败！");
			return retMap;
		}
		
		//返回成功信息
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "修改初始密码成功！");
		return retMap;
	}
}
