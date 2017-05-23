package com.hikootech.mqcash.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.service.LoginService;
import com.hikootech.mqcash.util.CommonUtils;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.UserUtils;
import com.hikootech.mqcash.util.ValidateTools;
import com.sun.xml.internal.stream.Entity;

@RequestMapping("/passwdport")
@Controller
public class LoginController extends BaseController {
	
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/toLogin.jhtml")
	public ModelAndView toLogin(){
		log.info("用户登录");
		
		UserInfo userInfo = getUserInfo();
		
		if(EntityUtils.isNotEmpty(userInfo)){
			return new ModelAndView("redirect:/instalmentManage/instalmentBill.jhtml");
		}
		
		String _redirectUrl = getRequest().getParameter("redirectUrl");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("redirectUrl", _redirectUrl);
		
		return new ModelAndView("login/user_login", retMap);
	}
	
	@RequestMapping("/loginBySM.jhtml")
	@ResponseBody
	public Map<String, Object> loginBySM(){
		
		log.info("用户根据手机号短信密码登录");
		Map<String, Object> retMap = new HashMap<String, Object>();
		getRequest().setAttribute("retMapLog", retMap);		
		
		String redirectUrl = "/instalmentManage/instalmentBill.jhtml";;
		
		//session中用户信息不为空，说明已经登陆
		UserInfo userInfo = getUserInfo();
		if(EntityUtils.isNotEmpty(userInfo)){
			log.info("用户已经登录，跳转到制定URL ：" + redirectUrl);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "用户已经登录。");
			retMap.put("redirectUrl", redirectUrl);
			return retMap;
		}
		
		//获取传递参数
		String mobileNumber = getRequest().getParameter("mobileNumber");
		String validationCode = getRequest().getParameter("validationCode");
		String password = getRequest().getParameter("password");
		String _redirectUrl = getRequest().getParameter("redirectUrl");
		
		//如果有重定向URL，则不往默认首页跳转
		if(EntityUtils.isNotEmpty(_redirectUrl)){
			try {
				redirectUrl = URLDecoder.decode(_redirectUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				log.error("重定向redirectUrl解码出错：" + redirectUrl);
			}
		}
		
		//简单校验
		if(EntityUtils.isEmpty(mobileNumber)){
			log.error("手机号不能为空。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入手机号码！");
			return retMap;
		}else if(EntityUtils.isEmpty(password)){
			log.error("短信验证码不能为空。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入短信验证码！");
			return retMap;
		}else if(EntityUtils.isEmpty(validationCode)){
			log.error("验证码不能为空。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入图形验证码！");
			return retMap;
		}
		
		mobileNumber = CommonUtils.trim(mobileNumber);
		password = CommonUtils.trim(password);
		
		//手机号规则校验：1开头、11位数字
		if(!ValidateTools.validateMobileNumber(mobileNumber)){
			log.error("手机号不正确。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "手机号码错误。");
			return retMap;
		}
		
		//从缓存中获取手机号，校验手机号是否为发送短信的手机号
		String _mobileNumCache = UserUtils.getMobileLoginMobileNumberFromCache(getRequest().getSession());
		
		//从缓存中获取验证码、手机登录密码以及它们的过期时间
		String smValiCodeCache = UserUtils.getMobileLoginValidationCodeFromCache(getRequest().getSession());
		Date smValiCodeStartTimeCache = UserUtils.getMobileLoginValidationCodeEffectiveTimeFromCache(getRequest().getSession());
		String smPwdCache = UserUtils.getMobileLoginPwdFromCache(getRequest().getSession());
		Date pwdStartTimeCache = UserUtils.getMobileLoginPwdEffectiveTimeFromCache(getRequest().getSession());
		
		//进行业务逻辑处理
		Map<String, Object> dealResultMap=new HashMap<String, Object>();
		try {
			dealResultMap = loginService.loginBySM( smPwdCache,  _mobileNumCache,smValiCodeCache,  pwdStartTimeCache,  smValiCodeStartTimeCache,  mobileNumber,
					  validationCode,  password);
		} catch (Exception e) {
			log.error("用户根据手机号短信密码登录,发生错误",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC,  "发生错误，请联系客服处理！");
			return retMap;
		}
		
		
		if(dealResultMap.get(ResponseConstants.RETURN_CODE).toString().equals(ResponseConstants.FAIL)){
			return dealResultMap;
		}
		//若处理成功，则返回值中user对象不为空，缓存
		userInfo=(UserInfo) dealResultMap.get("curUserInfo");
		if(EntityUtils.isNotEmpty(userInfo)){
			UserUtils.cacheUserInfo(getRequest().getSession(), userInfo);
		}
		//成功则将信息缓存，返回页面
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC,  "");
		retMap.put("redirectUrl", redirectUrl);
		return retMap;
	}
	
	
	
	@RequestMapping("/loginByPwd.jhtml")
	@ResponseBody
	public Map<String, Object> loginByPwd(){
		
		log.info("用户根据实名账号登录");
		Map<String, Object> retMap = new HashMap<String, Object>();
		getRequest().setAttribute("retMapLog", retMap);
		
		String redirectUrl = "/instalmentManage/instalmentBill.jhtml";
		//用户首次登录需强制修改密码
		String redirecUpdateInitPwdtUrl = "/updateUserInfo/updateInitPwd.jhtml";
		
		//session中用户信息不为空，说明已经登陆
		UserInfo userInfo = getUserInfo();
		if(EntityUtils.isNotEmpty(userInfo)){
			log.info("用户已经登录，跳转到制定URL ：" + redirectUrl);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, "用户已经登录。");
			retMap.put("redirectUrl", redirectUrl);
			return retMap;
		}
		
		//获取传递参数
		String mobileNumber = getRequest().getParameter("mobileNumber");
		String password = getRequest().getParameter("password");
		String validationCode = getRequest().getParameter("validationCode");
		String _redirectUrl = getRequest().getParameter("redirectUrl");
		
		//如果有重定向URL，则不往默认首页跳转
		if(EntityUtils.isNotEmpty(_redirectUrl)){
			try {
				redirectUrl = URLDecoder.decode(_redirectUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				log.error("重定向redirectUrl解码出错：" + redirectUrl);
			}
		}
		
		//简单校验
		if(EntityUtils.isEmpty(mobileNumber)){
			log.error("手机号不能为空。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入手机号码！");
			return retMap;
		}else if(EntityUtils.isEmpty(password)){
			log.error("密码不能为空。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "请输入密码！");
			return retMap;
		}
		
		mobileNumber = CommonUtils.trim(HkEncryptUtils.decode(mobileNumber));
		password = CommonUtils.trim(password);
		
		//手机号规则校验：1开头、11位数字
		if(!ValidateTools.validateMobileNumber(mobileNumber)){
			log.error("手机号不正确。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "手机号码错误！");
			return retMap;
		}
		
		//从缓存中获取验证码、手机登录密码以及它们的过期时间
		String pwdValiCodeCache = UserUtils.getMobileLoginValidationCodeFromCache(getRequest().getSession());
		Date pwdValiCodeStartTimeCache = UserUtils.getMobileLoginValidationCodeEffectiveTimeFromCache(getRequest().getSession());
		
		//进行业务逻辑处理
		Map<String, Object> dealResultMap=new HashMap<String, Object>();
		try {
			dealResultMap = loginService.loginByPwd( mobileNumber, validationCode, pwdValiCodeCache, pwdValiCodeStartTimeCache, password);
		} catch (Exception e) {
			log.error("用户根据实名账号登录,发生错误",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC,  "发生错误，请联系客服！");
			return retMap;
		}


		if(dealResultMap.get(ResponseConstants.RETURN_CODE).toString().equals(ResponseConstants.FAIL)){
			return dealResultMap;
		}
		//若处理成功，则返回值中user对象不为空，缓存
		userInfo=(UserInfo) dealResultMap.get("curUserInfo");
		if(EntityUtils.isNotEmpty(userInfo)){
			UserUtils.cacheUserInfo(getRequest().getSession(), userInfo);
		}
		//判断用户是否修改过密码，如果没有修改过密码，则跳转到修改密码页面
		if (EntityUtils.isEmpty(userInfo.getPwdModSts()) ||
				CommonConstants.USER_PWD_UNMODIFY_STS.intValue() == userInfo.getPwdModSts() ) {
			retMap.put("redirectUrl", redirecUpdateInitPwdtUrl);
		}else{
			retMap.put("redirectUrl", redirectUrl);
		}
		//成功则将信息缓存，返回页面
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC,  "");
		
		return retMap;
	}
	
	@RequestMapping("/logout.jhtml")
	public ModelAndView logout(){
		log.info("用户登出");
		UserInfo userInfo = UserUtils.getUserInfoFromCache(getRequest().getSession());
		
		if(EntityUtils.isNotEmpty(userInfo)){
			log.info("用户：" + userInfo.getBindMobile());
			UserUtils.clearUserInfoFromCache(getRequest().getSession());
			getRequest().getSession().invalidate();
		}
		
		return new ModelAndView("redirect:/passwdport/toLogin.jhtml");
	}
	
	
	@RequestMapping("/sendLoginSM.jhtml")
	@ResponseBody
	public Map<String, Object> sendLoginSM(){
		log.info("发送动态短信登录密码");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String mobileNumber = getRequest().getParameter("mobileNumber");
		
		//手机号规则校验：1开头、11位数字
		if(!ValidateTools.validateMobileNumber(mobileNumber)){
			log.error("手机号不正确。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "手机号码错误。");
			return retMap;
		}
		
		UserInfo userInfo = getUserInfo();
		
		if(EntityUtils.isNotEmpty(userInfo)){
			log.error("用户已登录，不需要发送动态密码。");
			retMap.put("mobileNumber", mobileNumber);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "用户已登录，不需要发送动态密码。");
			return retMap;
		}
		//从缓存中获取短信验证码、它们的过期时间、缓存手机号
		String smPwdCache = UserUtils.getMobileLoginPwdFromCache(getRequest().getSession());
		Date smPwdStartTimeCache = UserUtils.getMobileLoginPwdEffectiveTimeFromCache(getRequest().getSession());
		String _mobileNumCache = UserUtils.getMobileLoginMobileNumberFromCache(getRequest().getSession());
		
		Map<String, Object> dealResultMap=new HashMap<String, Object>();
		try {
			dealResultMap = loginService.sendLoginSm(  mobileNumber,  smPwdCache,  _mobileNumCache,  smPwdStartTimeCache  );
		} catch (Exception e) {
			log.error("发送动态短信登录密码,发生错误",e);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC,  "发生错误，请联系客服！");
			return retMap;
		}

		if(dealResultMap.get(ResponseConstants.RETURN_CODE).toString().equals(ResponseConstants.FAIL)){
			return dealResultMap;
		}
		
		//缓存新手机号
		UserUtils.cacheMobileLoginMobileNumber(getRequest().getSession(), mobileNumber);
		//缓存短信验证码和验证码发送时间
		UserUtils.cacheMobileLoginPwd(getRequest().getSession(), (String)dealResultMap.get("curSmPwd"));
		UserUtils.cacheMobileLoginPwdEffectiveTime(getRequest().getSession(), (Date)dealResultMap.get("smPwdStartTime"));
		
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC,  "");
		return retMap;
	}
	
	@RequestMapping("/generateValidationCode.jhtml")
	@ResponseBody
	public void generateValidationCode(HttpServletResponse response){
		// 设置不缓存图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的相应图片
		response.setContentType("image/jpeg");
		
		//调用生成验证码函数
		List<Object> listValiCode=constructValidationCode();
		
		//获取图片信息
		BufferedImage image = (BufferedImage) listValiCode.get(1);
		//获取生成验证码
		String validationCode = (String) listValiCode.get(0);
		log.debug("validationCode" + validationCode);
		
		//保存验证码到session中
		HttpSession session = getRequest().getSession();
		UserUtils.cacheMobileLoginValidationCode(session, validationCode);
		UserUtils.cacheMobileLoginValidationCodeEffectiveTime(session, new Date());
		
		//写入流
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
