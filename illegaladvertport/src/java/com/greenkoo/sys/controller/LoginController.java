package com.greenkoo.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenkoo.account.model.UserAccount;
import com.greenkoo.account.model.form.UserAccountVo;
import com.greenkoo.account.service.IUserAccountService;
import com.greenkoo.company.model.UserCompany;
import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.sys.model.SysJsonMsg;
import com.greenkoo.sys.model.UserLogin;
import com.greenkoo.sys.service.IUserLoginService;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.IpUtil;
import com.greenkoo.utils.StringUtil;

/**
 * 登录Controller
 * 
 * @author QYANZE
 *
 */
@Controller
public class LoginController {

	@Autowired
	private IUserAccountService userAccountService;
	@Autowired
	private IUserLoginService userLoginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/loginAuth", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public SysJsonMsg loginAuth(String account, String pwd, HttpServletRequest request) {
		// 登录日志信息
		UserLogin userLogin = new UserLogin();
		userLogin.setAccount(account);
		userLogin.setIpAddress(IpUtil.getRemoteHost(request));
		userLogin.setLoginTime(DateUtil.getCurDate());
		userLogin.setCreateTime(DateUtil.getCurDate());
		
		if (StringUtil.isEmpty(account)) {
			return new SysJsonMsg(false, "账户不能为空！");
		}
		if (StringUtil.isEmpty(pwd)) {
			return new SysJsonMsg(false, "密码不能为空！");
		}
		
		//验证账号
		UserAccountVo formBean = this.userAccountService.queryByAccountAndPwd(account, pwd);
		if (formBean == null) {
			// 保存登录日志
			userLogin.setLoginResult(UserLogin.LOGIN_RESULT_FAIL);
			userLogin.setLoginDescription("用户【"+account+"】登录，无此账户信息");
			this.userLoginService.add(userLogin );
			
			return new SysJsonMsg(false, "账户或密码错误！"); 
		}
		
		// 用户所在公司信息
		UserCompany userCompany = formBean.getUserCompany();
		// 用户信息
		UserAccount userAccount = formBean.getUserAccount();
		
		// 用户不存在
		if (userAccount == null) {
			// 保存登录日志
			userLogin.setLoginResult(UserLogin.LOGIN_RESULT_FAIL);
			userLogin.setLoginDescription("用户【"+account+"】登录，无此账户信息");
			this.userLoginService.add(userLogin );
			
			return new SysJsonMsg(false, "账户或密码错误！"); 
		}
		
		// 判断公司是否存在或者状态无效时候返回信息
		if (userCompany == null || CommonConstants.STATUS_OFF == userCompany.getStatus()) {
			// 保存登录日志
			userLogin.setLoginResult(UserLogin.LOGIN_RESULT_FAIL);
			userLogin.setLoginDescription("用户【"+account+"】登录，所在公司不存在或状态无效");
			this.userLoginService.add(userLogin );
			
			return new SysJsonMsg(false, "账户或密码错误！");
		}
		
		// 判断账户状态无效时候返回信息
		if (CommonConstants.STATUS_OFF == userAccount.getStatus()) {
			// 保存登录日志
			userLogin.setLoginResult(UserLogin.LOGIN_RESULT_FAIL);
			userLogin.setLoginDescription("用户【"+account+"】登录，用户账号状态无效");
			this.userLoginService.add(userLogin );
			
			return new SysJsonMsg(false, "账户或密码错误！");
		}
		
		// 将登录账户信息放入session中
		request.getSession().setAttribute(CommonConstants.LOGIN_COMPANY, userCompany);
		request.getSession().setAttribute(CommonConstants.LOGIN_USER, userAccount);
		
		// 保存登录日志
		userLogin.setLoginResult(UserLogin.LOGIN_RESULT_SUCCESS);
		userLogin.setLoginDescription("用户【"+account+"】登录成功");
		this.userLoginService.add(userLogin );
		
		return new SysJsonMsg(true, "登录成功"); 
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserAccount loginUser = (UserAccount) session.getAttribute(CommonConstants.LOGIN_USER);
		if (loginUser != null) {
			session.removeAttribute(CommonConstants.LOGIN_USER);
			session.removeAttribute(CommonConstants.LOGIN_COMPANY);
			session.invalidate();
		}
		return "redirect:login";
	}
}
