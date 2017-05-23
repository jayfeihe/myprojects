package com.hikootech.mqcash.service;

import java.util.Date;
import java.util.Map;

/**
	* 此类描述的是：门户登陆业务层
	* @author: zhaohefei
	* @version: 2015年12月10日 上午11:29:40
	*/
	
public interface LoginService {
	
	/** 
	* loginBySM<br/> 
	*  TODO(手机密码方式登陆，若处理成功，最终返回值中包含curUserInfo对象在retMap中) 
	* @param smPwdCache 缓存短信密码
	* @param _mobileNumCache 缓存手机号
	* @param smValiCodeCache 缓存图片验证码
	* @param pwdStartTimeCache 缓存短信密码起始时间
	* @param smValiCodeStartTimeCache 缓存图片验证码起始时间
	* @param mobileNumber  前台传递手机号
	* @param validationCode 前台传递图片验证码
	* @param password 前台传递短信密码
	* @return 
	* @author zhaohefei
	* @2015年12月10日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> loginBySM(String smPwdCache,String _mobileNumCache,String smValiCodeCache,Date pwdStartTimeCache,Date smValiCodeStartTimeCache,
			String mobileNumber, String validationCode,String password) ;
	
	
	/** 
	* loginByPwd<br/> 
	*  TODO(密码方式登陆，若处理成功，最终返回值中包含curUserInfo对象在retMap中) 
	* @param mobileNumber 前台传递手机号
	* @param validationCode 前台传递图片验证码
	* @param pwdValiCodeCache 缓存图片验证码
	* @param pwdValiCodeStartTimeCache 缓存图片验证码的起始时间
	* @param password 前台传递的密码
	* @return 
	* @author zhaohefei
	* @2015年12月10日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> loginByPwd(String mobileNumber,String validationCode,String pwdValiCodeCache,Date pwdValiCodeStartTimeCache,String password) ;
	
	
	/** 
	* sendLoginSm<br/> 
	*  TODO(请求云通讯发送短信) 
	* @param mobileNumber 前台传递手机号
	* @param smPwdCache 缓存中短信密码
	* @param _mobileNumCache 缓存中手机号
	* @param smPwdStartTimeCache 缓存短信密码的起始时间
	* @return 
	* @author zhaohefei
	* @2015年12月10日
	* @return Map<String,Object>	返回类型 
	*/
	public  Map<String,Object> sendLoginSm(String mobileNumber,String smPwdCache,String _mobileNumCache,Date smPwdStartTimeCache  );
	
}
