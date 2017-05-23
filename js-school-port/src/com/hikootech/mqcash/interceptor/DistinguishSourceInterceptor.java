package com.hikootech.mqcash.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserInstalment;
import com.hikootech.mqcash.service.UserInstalmentService;
import com.hikootech.mqcash.service.UserService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DigestUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.IpUtils;
import com.hikootech.mqcash.util.ThreeDES;
import com.hikootech.mqcash.util.UserUtils;

/**
 * @author yuwei
 * 2015年8月21日
 * 区分用户来源
 */
public class DistinguishSourceInterceptor implements HandlerInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(DistinguishSourceInterceptor.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserInstalmentService userInstalmentService;
	
	private String checkUri;
	private String errorPage;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		
		String uri = request.getRequestURI();
		String ip = IpUtils.getRemoteHost(request);
		
		/*
		UserInfo userInfo = UserUtils.getUserInfoFromCache(request.getSession());
		if(EntityUtils.isNotEmpty(userInfo)){
			log.info("用户已登陆，免登录");
		}else{
			log.info("来自秒趣分期网站访问，免登录");
			String idCard = request.getParameter("idCard");
			String verify = request.getParameter("verify");
			userInfo = userService.queryUserInfoByIdCard(idCard);
			if(EntityUtils.isEmpty(userInfo)){
				log.error("找不到对应用户, idCard : " + idCard);
				RequestDispatcher rd = request.getRequestDispatcher(errorPage);
				rd.forward(request, response);
				return false;
			}
			
			UserInstalment instalment = userInstalmentService.queryLastestSuccessInstalmentByIdCard(idCard);
			if(EntityUtils.isEmpty(instalment)){
				log.error("找不到对应用户, idCard : " + idCard);
				RequestDispatcher rd = request.getRequestDispatcher(errorPage);
				rd.forward(request, response);
				return false;
			}
			
			//生成联合登陆使用：秒趣下单时间+身份证号md5
			String _verify = DigestUtil.getKeyedDigest(userInfo.getIdCard() + instalment.getCreateTime(), 
					ConfigUtils.getProperty("mqcash_port_login_key"));
			if(_verify.equals(verify)){
				UserUtils.cacheUserInfo(request.getSession(), userInfo);
			}
			
		}
		*/
		String params = request.getParameter("params");
		if(EntityUtils.isNotEmpty(params)){
			String desKey = ConfigUtils.getProperty("mqcash_port_login_3des_key");
			
			byte[] desString = ThreeDES.encryptMode(desKey.getBytes("utf-8"), params.getBytes("utf-8"));
			String busParams = HkEncryptUtils.parseByte2HexStr(desString);
			request.setAttribute("busParams", busParams);
		}
		return true;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getCheckUri() {
		return checkUri;
	}

	public void setCheckUri(String checkUri) {
		this.checkUri = checkUri;
	}

}
