package com.hikootech.mqcash.service;

import java.util.Date;
import java.util.Map;

import com.hikootech.mqcash.po.UserInfo;

public interface UpdateUserInfoService {

	
	/** 
	* validateValid<br/> 
	*  TODO(校验修改信息时的手机验证码与图片验证码) 
	* @param smCodeCache  缓存短信密码以及手机号  用,,,分隔
	* @param smCodeStartTimeCache 缓存短信密码起始时间
	* @param upValiCodeCache  缓存图片验证码
	* @param upValiCodeStartTimeCache  缓存图片验证码的起始时间
	* @param smCode 前台传值短信密码
	* @param mobileNumber 前台传值手机号
	* @param validationCode 前台传值图片验证码
	* @author zhaohefei
	* @2015年12月11日
	* @return void	返回类型 
	*/
	public void validateValid(String smCodeCache,Date smCodeStartTimeCache,String upValiCodeCache, Date upValiCodeStartTimeCache,String smCode,String mobileNumber,
			String validationCode);
	
	
	/** 
	* modifyInfoSub<br/> 
	*  TODO(根据前台传值，修改用户信息) 
	* @param userInfoSession 缓存 中的用户信息
	* @param userInfo 修改后的用户信息
	* @return 
	* @author zhaohefei
	* @2015年12月11日
	* @return Map<String,Object>	返回类型 
	*/
	public  Map<String,Object> modifyInfoSub(UserInfo userInfoSession,UserInfo userInfo);
	
	
	/** 
	* updatePwdByMobile<br/> 
	*  TODO(根据手机号修改密码) 
	* @param newPwd 新密码
	* @param userInfo 缓存用户
	* @return 
	* @author zhaohefei
	* @2015年12月11日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> updatePwdByMobile(String newPwd,UserInfo userInfo);
	
	
	/** 
	* updatePwdByPwd<br/> 
	*  TODO(根据原密码修改密码) 
	* @param oldPwd 原密码
	* @param newPwd 新密码
	* @param userInfo 缓存用户
	* @return 
	* @author zhaohefei
	* @2015年12月11日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> updatePwdByPwd(String oldPwd,String newPwd,UserInfo userInfo);
	
	
	/** 
	* updateUserBingMobile<br/> 
	*  TODO(修改绑定手机) 
	* @param smCode 短信密码
	* @param mobileNumberNew 新手机号
	* @param userInfo 缓存用户
	* @return 
	* @author zhaohefei
	* @2015年12月11日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> updateUserBingMobile(String smCode,String mobileNumberNew,UserInfo userInfo) ;
	
	/** 
	* sendSmsCodeForUpdate<br/> 
	*  TODO(这里用一句话描述这个方法的作用) 
	* @param smCodeCache 缓存短信
	* @param mobileNumCache  缓存手机号
	* @param smCodeStartTimeCache 缓存手机号起始时间
	* @param smCodeEffectiveTime 短信密码有效时间
	* @param interval 短信密码间隔
	* @param mobileNumber 前台传值手机号
	* @param templateId 短信模板id
	* @param customerServicePhoneNumber  服务电话
	* @param sendContent  发送短信内容数组参数
	* @return 
	* @author zhaohefei
	* @2015年12月11日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String,Object> sendSmsCodeForUpdate(String smCodeCache,String mobileNumCache,Date smCodeStartTimeCache,int smCodeEffectiveTime,int interval,String mobileNumber,String templateId,String customerServicePhoneNumber );


	/**  
	 * updateInitPwdByPwd(修改初始密码)  
	 * @param oldPwd
	 * @param newPwd2
	 * @param userInfo
	 * @return   
	 * Map<String,Object> 
	 * @create time： 2016年4月23日 下午7:39:51 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public Map<String, Object> updateInitPwdByPwd(String oldPwd, String newPwd2, UserInfo userInfo);	
}
