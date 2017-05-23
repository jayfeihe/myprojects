package com.hikootech.mqcash.interceptor;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.po.LogUserInfo;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserLoginRecord;
import com.hikootech.mqcash.service.LogService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.IpUtils;
import com.hikootech.mqcash.util.UserLoginErrorUtils;
import com.hikootech.mqcash.util.UserUtils;

public class LoginInterceptor implements HandlerInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Autowired
	private LogService logService;
	
	private String errorPage;

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
			
		//获取用户所在ip
		String ipAddress = IpUtils.getRemoteHost(request);
		
		//获取返回的结果
		Map<String,Object> retMapLog=(Map<String, Object>) request.getAttribute("retMapLog");
		String mobileNumber = request.getParameter("mobileNumber");
		 
		//根据uri确定登陆方式
		if("/passwdport/loginBySM.jhtml".equals(request.getRequestURI())){
				
			recordLog(mobileNumber,ipAddress,CommonConstants.LOGIN_METHOD_SM,retMapLog.get("code"),retMapLog.get("desc"));
			
			return;
		}
		if("/passwdport/loginByPwd.jhtml".equals(request.getRequestURI())){
			
			recordLog(HkEncryptUtils.decode(mobileNumber),ipAddress,CommonConstants.LOGIN_METHOD_PWD,retMapLog.get("code"),retMapLog.get("desc"));
			UserInfo userInfo = UserUtils.getUserInfoFromCache(request.getSession());
			if(EntityUtils.isEmpty(userInfo)){
				UserLoginErrorUtils.increase(mobileNumber);
			}
			return;
		}
		
//		if("/updateUserInfo/modifyInfoSub".equals(request.getRequestURI())){
//			
//			recordLog(mobileNumber,ipAddress,CommonConstants.LOGIN_METHOD_PWD,retMapLog.get("code"),retMapLog.get("desc"));
//			UserInfo userInfo = UserUtils.getUserInfoFromCache(request.getSession());
//			if(EntityUtils.isNotEmpty(userInfo)){
//				//===================将缓存信息装入 日志类start============================
//				LogUserInfo logUserInfo=new LogUserInfo(userInfo);
//				logUserInfo.setLogId(GenerateKey.getId(CommonConstants.LOG_USER_INFO_PREFIX, ConfigUtils.getProperty("db_id")));
//				logUserInfo.setOperationType(CommonConstants.LOG_USER_INFO_UP_BASE);
//				//====================将缓存信息装入 日志类 end===========================
//			}
//			return;
//		}
		
		
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	/** 
	* recordLog<br/> 
	*  TODO(根据传参生成用户登陆记录) 
	* @param mobileNum 登陆手机号
	* @param ipAddress 登陆ip
	* @param loginMethod 登陆方式
	* @param loginResult 登陆结果
	* @param desc  错误描述信息
	* @author zhaohefei
	* @2015年12月10日
	* @return void	返回类型 
	*/
	public void recordLog(String mobileNum,String ipAddress,Integer loginMethod,Object loginResult,Object desc){
		try {
			UserLoginRecord record =null;
			
			//返回结果code存在，且其值为成功，则证明登路成功
			if(EntityUtils.isNotEmpty(loginResult)&& String.valueOf(loginResult).equals(ResponseConstants.SUCCESS)){
				 record = new UserLoginRecord(GenerateKey.getId(CommonConstants.LOGIN_RECORD_ID_PREFIX, ConfigUtils.getProperty("db_id")), 
					mobileNum, ipAddress, 
					loginMethod,CommonConstants.LOGIN_RESULT_SUCCESS,null ,new Date(),new Date());
			}else{
				record = new UserLoginRecord(GenerateKey.getId(CommonConstants.LOGIN_RECORD_ID_PREFIX, ConfigUtils.getProperty("db_id")), 
						mobileNum, ipAddress, 
						loginMethod,CommonConstants.LOGIN_RESULT_FAIL,EntityUtils.isEmpty(desc)?"":desc.toString() ,new Date(),new Date());
			}
			logService.addUserLoginRecord(record);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("记录用户登录历史出错", e);
		}
	}

}
