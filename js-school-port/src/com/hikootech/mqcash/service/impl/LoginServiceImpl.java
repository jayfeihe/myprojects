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
import com.hikootech.mqcash.po.SmLog;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.LogService;
import com.hikootech.mqcash.service.LoginService;
import com.hikootech.mqcash.service.UserService;
import com.hikootech.mqcash.util.CommonUtils;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.SmUtils;
import com.hikootech.mqcash.util.UserLoginErrorUtils;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

	private static final Logger log=LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private LogService logService;
	@Autowired
	private ConfigKvService configKvService;

	@Override
	public Map<String, Object> loginBySM(String smPwdCache,String _mobileNumCache,String smValiCodeCache,Date pwdStartTimeCache,Date smValiCodeStartTimeCache,
			String mobileNumber, String validationCode,String password) {
		
		Map<String,Object> retMap=new HashMap<String, Object>();
		
		//缓存中不存在动态验证码，则证明没有点击获取短信
		if(EntityUtils.isEmpty(smPwdCache) || EntityUtils.isEmpty(pwdStartTimeCache)){
			log.error("请获取短信动态验证码后，再登录！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码错误！");
			return retMap;
		}
		
		//缓存中手机号与前台传值不一致，则证明发送短信后更改过手机号
		if(EntityUtils.isEmpty(_mobileNumCache) || !_mobileNumCache.equals(mobileNumber)){
			log.error("绑定手机号登录，手机号码不一致！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "手机号码错误！");
			return retMap;
		}
		
		//===============================超时校验start==============
		int _pwdEffectiveTime = 1;
		try {
			_pwdEffectiveTime = Integer.parseInt(ConfigUtils.getProperty("mqcash_mobile_login_pwd_effective_time"))/60;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			log.error("获取手机验证码有效时间出错", e);
		}
		Date pwdExpireTime = DateUtil.addDate(pwdStartTimeCache, Calendar.MINUTE, _pwdEffectiveTime);
		int _mobileLoginValidationCodeEffectiveTime = 1;
		try {
			_mobileLoginValidationCodeEffectiveTime = Integer.parseInt(ConfigUtils.getProperty("mqcash_mobile_login_validation_code_effective_time"))/60;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			log.error("获取手机验证码有效时间出错", e);
		}
		
		Date validationCodeExpireTime = DateUtil.addDate(smValiCodeStartTimeCache, Calendar.MINUTE, _mobileLoginValidationCodeEffectiveTime);
		Date now = new Date();
		
		if(now.compareTo(validationCodeExpireTime) > 0){
			log.error("绑定手机号登录，验证码过期！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "图形验证码过期！");
			return retMap;
		}

		if(now.compareTo(pwdExpireTime) > 0){
			log.error("绑定手机号登录，手机短信验证码过期！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码过期！");
			return retMap;
		}
		
		//===============================超时校验end==============
		
		//正误判断
		if(!smValiCodeCache.equalsIgnoreCase(validationCode.trim())){
			log.error("绑定手机号登录，验证码错误！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "图形验证码错误！");
			return retMap;
		}
		if(!smPwdCache.equalsIgnoreCase(password.trim())){
			log.error("绑定手机号登录，手机验证码错误！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码错误！");
			return retMap;
		}
		
		//根据手机号查询有效信息
		UserInfo userInfo=null;
		try {
			userInfo = userService.queryEffectiveUserInfoByBindMobile(mobileNumber);
		} catch (Exception e) {
			log.error("根据手机号查询用户信息发生错误：", e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "用户数据有误，请联系客服！");
			return retMap;
		}
		
		if(EntityUtils.isEmpty(userInfo)){
			log.error("用户不存在");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "账户名不存在！");
			return retMap;
		} 
		
		//用户信息存在则返回控制层，用于缓存
		retMap.put("curUserInfo",userInfo);
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "用户登录成功。");
		return retMap;
	}

	@Override
	public Map<String, Object> loginByPwd(String mobileNumber,String validationCode,String pwdValiCodeCache,Date pwdValiCodeStartTimeCache,String password) {
			
		Map<String,Object> retMap=new HashMap<String, Object>();
		
		//检查用户登录错误次数
		Integer times = UserLoginErrorUtils.get(mobileNumber);
		if(times >= 1 && EntityUtils.isEmpty(validationCode)){
			log.info("用户登录出错超过3次，需要验证码登录，mobileNumber ：" + mobileNumber);
			retMap.put("times", times);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入图片验证码！");
			return retMap;
		}
/*		else if(times < 3){
			validationCode = pwdValiCodeCache;
		}*/
		
		//缓存中值非空验证
		if(EntityUtils.isEmpty(pwdValiCodeCache) || EntityUtils.isEmpty(pwdValiCodeStartTimeCache)){
			log.error("请获取验证码后，再登录！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "图形验证码错误！");
			return retMap;
		}
		
		//超时校验
		int _mobilePwdLoginValidationCodeEffectiveTime = 1;
		try {
			_mobilePwdLoginValidationCodeEffectiveTime = Integer.parseInt(ConfigUtils.getProperty("mqcash_mobile_login_validation_code_effective_time"))/60;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			log.error("获取手机验证码有效时间出错", e);
		}
		Date validationCodeExpireTime = DateUtil.addDate(pwdValiCodeStartTimeCache, Calendar.MINUTE, _mobilePwdLoginValidationCodeEffectiveTime);
		Date now = new Date();
		if(now.compareTo(validationCodeExpireTime) > 0 && times >= 3){
			log.error("绑定手机号登录，验证码过期！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "图形验证码过期。");
			return retMap;
		}

		//正误判断
		if(!pwdValiCodeCache.equalsIgnoreCase(validationCode)){
			log.error("绑定手机号登录，验证码错误！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "图形验证码错误。");
			return retMap;
		}
		
		//根据手机号获取有效用户信息
		UserInfo userInfo=null;
		try {
			//这里用户密码，在浏览器段md5加密上传到服务端
			userInfo = userService.queryUserInfoByBindMobileAndPwd(mobileNumber, password);
		} catch (Exception e) {
			log.error("查询用户信息发生错误：", e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "用户数据有误，请联系客服！");
			return retMap;
		}
		
		if(EntityUtils.isEmpty(userInfo)){
			log.info("用户名或者密码错误，请重新输入！");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "用户名或者密码错误，请重新输入！");
			return retMap;
		} 
		
		//用户信息存在则返回控制层，用于缓存
		retMap.put("curUserInfo",userInfo);
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "用户登录成功。");
		return retMap;
	}

	@Override
	public  Map<String,Object> sendLoginSm(String mobileNumber,String smPwdCache,String _mobileNumCache,Date smPwdStartTimeCache  ) {
		
		Map<String,Object> retMap=new HashMap<String, Object>();
		
		//根据手机号查询有效用户信息
		UserInfo userInfo = null;
		try {
			userInfo = userService.queryUserInfoByBindMobile(mobileNumber);
		} catch (Exception e2) {
			log.error("查询用户信息发生错误：", e2);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "用户数据有误，请联系客服！");
			return retMap;
		}
		if(EntityUtils.isEmpty(userInfo)){
			log.error("用户不存在，请分期后再登录。");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "账户名不存在。");
			return retMap;
		}
		
		//短信密码超时校验
		int _mobileLoginPwdEffectiveTime = 1;
		try {
			_mobileLoginPwdEffectiveTime = Integer.parseInt(ConfigUtils.getProperty("mqcash_mobile_login_pwd_effective_time"))/60;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			log.error("获取手机短信验证码有效时间出错", e);
		}
		int interval = 1;
		try {
			interval = Integer.parseInt(ConfigUtils.getProperty("mqcash_sm_send_interval_time"))/60;
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int length = CommonConstants.MQCASH_MOBILE_LOGIN_PWD_LENGTH;
		
		//判断是否发送验证码（目前只有60秒内获取验证码是直接返回错误信息的）
		Date now = new Date();
		if(EntityUtils.isEmpty(smPwdCache) && EntityUtils.isEmpty(_mobileNumCache)){
			
			log.info("第一次获取动态密码。");
			smPwdCache = CommonUtils.randomNumber(length);
			smPwdStartTimeCache = now;
			
		}else{
			
			Date expireTime = DateUtil.addDate(smPwdStartTimeCache, Calendar.MINUTE, _mobileLoginPwdEffectiveTime);
			Date nextTime = DateUtil.addDate(smPwdStartTimeCache, Calendar.MINUTE, interval);
			
			if(now.compareTo(nextTime) < 0){
				log.info("请求发送短信验证码频率过快，请60s后尝试！");
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "请求发送短信验证码频率过快，请稍后再尝试！");
				return retMap;
			}else if(!_mobileNumCache.equals(mobileNumber)){
				log.info("换一个账号登录。");
				smPwdCache = CommonUtils.randomNumber(length);
				smPwdStartTimeCache = now;
			}else if(now.compareTo(expireTime) > 0){
				log.error("秒趣分期手机验证码过期！");
				smPwdCache = CommonUtils.randomNumber(length);
				smPwdStartTimeCache = now;
			}else{
				log.error("绑定手机号登录短信验证码未过期！");
				smPwdCache = CommonUtils.randomNumber(length);
				smPwdStartTimeCache = now;
			}
			
		}
		
		//将时间与短信验证码返回，用于缓存
		retMap.put("curSmPwd", smPwdCache);
		retMap.put("smPwdStartTime", smPwdStartTimeCache);
		
		//发送短信
		String templateId = ConfigUtils.getProperty("mobile_login_pwd_template_id");
		String customerServicePhoneNumber =configKvService.get(CommonConstants.CUSTOMER_SERVICE_TEL);
		log.info("发送短信:" + smPwdCache);
		boolean isSucces = SmUtils.sendMsg(mobileNumber, new String[]{smPwdCache, _mobileLoginPwdEffectiveTime + ""}, templateId);
		
		//发送完毕后，增加短信记录
		try {
			SmLog smLog=new SmLog();
			smLog.setSmOrderId(GenerateKey.getId(CommonConstants.SM_ORDER_ID_PREFIX, ConfigUtils.getProperty("db_id")));
			smLog.setMobileNumber(mobileNumber);
			smLog.setData(CommonUtils.join(new String[]{smPwdCache, _mobileLoginPwdEffectiveTime + ""}, CommonConstants.SM_DATA_SEPARATOR));
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
		
		//将有效信息返回
		if(isSucces){
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码发送成功。");
		}else{
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "动态验证码发送失败，请重试！");
		}
		
		return retMap;
	}

}
