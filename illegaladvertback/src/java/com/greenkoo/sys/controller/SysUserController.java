package com.greenkoo.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.sys.model.Pager;
import com.greenkoo.sys.model.SysJsonMsg;
import com.greenkoo.sys.model.SysUser;
import com.greenkoo.sys.service.ISysUserService;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.SecurityUtil;
import com.greenkoo.utils.StringUtil;

/**
 * 系统用户Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ISysUserService sysUserService;
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String userQuery() {
		return "sys/user/userQuery";
	}
	
	/**
	 * 用户列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String userList(HttpServletRequest request, Model model) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 条件-状态有效
		paramMap.put("status", CommonConstants.STATUS_ON);
		
		Pager pager = new Pager();
		int totalCount = this.sysUserService.queryCount(paramMap);
		pager.init(totalCount);
		paramMap.put("pageSize", pager.getPageSize());
		paramMap.put("pageOffset", pager.getPageOffset());
		List<SysUser> datas = this.sysUserService.queryList(paramMap);
		pager.setDatas(datas);
		model.addAttribute("pager", pager);
		return "sys/user/userList";
	}
	
	/**
	 * 用户个人信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userCenter", method = RequestMethod.GET)
	public String userCenter(HttpServletRequest request, Model model) {
		SysUser loginUser = (SysUser) request.getSession().getAttribute(CommonConstants.LOGIN_USER);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 条件-用户账户
		paramMap.put("account", loginUser.getAccount());
		// 条件-状态有效
		paramMap.put("status", CommonConstants.STATUS_ON);
		
		List<SysUser> datas = this.sysUserService.queryList(paramMap);
		model.addAttribute("datas", datas);
		return "sys/user/userCenter";
	}
	
	/**
	 * 判断原始密码是否正确
	 * 
	 * @param userId
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value = "/queryOldPwd", method = RequestMethod.GET)
	@ResponseBody
	public SysJsonMsg queryOldPwd(String userId,String pwd) {
		SysUser sysUser = this.sysUserService.queryByUserId(userId);
		if (StringUtil.isNotEmpty(pwd) && pwd.equals(sysUser.getPwd())) {
			return new SysJsonMsg(true, "原始密码正确");
		} else {
			return new SysJsonMsg(false, "原始密码不正确");
		}
	}
	
	/**
	 * 更改密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
	@ResponseBody
	public SysJsonMsg updatePwd(SysUser user, HttpServletRequest request) {
		try {
			user.setPwd(SecurityUtil.md5Str(user.getPwd()));
			user.setUpdateTime(DateUtil.getCurDate());
			int count = this.sysUserService.updatePwd(user);
			
			if (count > 0) {
				// 保存操作日志
				request.setAttribute(CommonConstants.OPERATION_RECORD_FLAG, CommonConstants.SUCCESS_FLAG);
				request.setAttribute(CommonConstants.OPERATION_RECORD_DESC, "修改个人密码成功");
				request.setAttribute(CommonConstants.OPERATION_RECORD_TYPE, 30);
				return new SysJsonMsg(true, "修改密码成功");
			} else {
				return new SysJsonMsg(true, "修改密码失败");
			}
		} catch (Exception e) {
			logger.error("修改个人密码发生错误，userId:" + user.getUserId() + "," + e.getMessage(), e);
			return new SysJsonMsg(false, "修改密码失败");
		}
	}
}
