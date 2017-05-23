package com.greenkoo.account.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenkoo.account.model.UserAccount;
import com.greenkoo.account.service.IUserAccountService;
import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.sys.model.SysJsonMsg;
import com.greenkoo.utils.SecurityUtil;

/**
 * 用户账户Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/userAccount")
public class UserAccountController {

	@Autowired
	private IUserAccountService userAccountService;
	
	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public String center() {
		return "user/center";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public SysJsonMsg update(UserAccount account, HttpServletRequest request) {
		
		try {
			UserAccount sessionAccount = (UserAccount) request.getSession().getAttribute(CommonConstants.LOGIN_USER);
			sessionAccount.setEmail(account.getEmail());
			sessionAccount.setFax(account.getFax());
			sessionAccount.setMobile(account.getMobile());
			sessionAccount.setQq(account.getQq());
			sessionAccount.setTelephone(account.getTelephone());
			sessionAccount.setUserName(account.getUserName());
			this.userAccountService.update(sessionAccount);
			
			// 更新session
			request.getSession().setAttribute(CommonConstants.LOGIN_USER, sessionAccount);
			
			return new SysJsonMsg(true, "修改成功！");
		} catch (Exception e) {
			return new SysJsonMsg(false, "系统出错！");
		}
	}
	
	@RequestMapping("/updatePwd")
	@ResponseBody
	public SysJsonMsg updatePwd(String password, String newpassword, HttpServletRequest request) {
		UserAccount sessionAccount = (UserAccount) request.getSession().getAttribute(CommonConstants.LOGIN_USER);
		// 验证原密码
		if (!sessionAccount.getPwd().equals(SecurityUtil.md5Str(password))) {
			return new SysJsonMsg(false, "原密码不正确！");
		}
		
		try {
			sessionAccount.setPwd(SecurityUtil.md5Str(newpassword));
			this.userAccountService.update(sessionAccount);
			
			return new SysJsonMsg(true, "修改成功！");
		} catch (Exception e) {
			return new SysJsonMsg(false, "系统出错！");
		}
	}
}
