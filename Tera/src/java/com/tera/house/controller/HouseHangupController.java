/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.house.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.house.constant.Constants;
import com.tera.house.service.HouseAssignService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.model.form.UserQBean;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserService;
import com.tera.util.ObjectUtils;

/**
 * 
 * 用户信息控制器
 * <b>功能：</b>UserController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-22 11:30:06<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/house/hangup")
public class HouseHangupController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(HouseHangupController.class);
	
	/**
	 * UserService
	 */
	@Autowired(required=false) //自动注入
	UserService userService;
	
	@Autowired(required=false) //自动注入
	HouseAssignService houseAssignService;
	
	@Autowired(required=false) //自动注入
	RoleService roleService;
	
	/**
	 * 跳转到用户信息的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String houseHangupQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		log.info(thisMethodName+":end");
		return "house/hangup/houseHangupQuery";
	}

	/**
	 * 显示用户信息的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String houseHangupList(UserQBean userQBean,BindingResult bingding,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		log.info(thisMethodName+":start");
		
		Map<String, Object> beanMap = ObjectUtils.describe(userQBean);
		int isAdmin = userService.getUser(loginId).getIsAdmin();
		if(isAdmin == 0){
			Map<String, Object> roleMap = new HashMap<String, Object>();
			roleMap.put("loginId", loginId);
			List<Role> roleList = roleService.getRoleByOrgLoginId(roleMap);
			
			String roleName = "";
			for (Role role : roleList) {
				if(Constants.ROLE_SHZZ.equals(role.getName())){
					roleName = roleName+Constants.ROLE_SHZY+",";
				}else if(Constants.ROLE_SPZZ.equals(role.getName())){
					roleName = roleName+Constants.ROLE_SPZY+",";
				}else if(Constants.ROLE_FHZZ.equals(role.getName())){
					roleName = roleName+Constants.ROLE_FHZY+",";
				}
			}
//			roleName.substring(0, roleName.length()-1);
			beanMap.put("roleNames", roleName.split(","));
			
		}else if(isAdmin == 1){
			beanMap.put("roleNames", new String[]{Constants.ROLE_SHZY,Constants.ROLE_SPZY,Constants.ROLE_FHZY});
		}
		
		beanMap.put("states", userQBean.getStates().split(","));
		beanMap.put("orgId","86");
		
		int rowsCount = userService.queryUserByOrgAndRoleAndDepartCount(beanMap);

		PageModel pm = new PageModel();
		pm.init(request, rowsCount, null, userQBean);
		beanMap.put("rowS", pm.getRowS());
		beanMap.put("rowE", pm.getRowE()); 
		
		List<User> users = userService.queryUserByOrgAndRoleAndDepart(beanMap);
		pm.setData(users);
		map.put("pm", pm);
		
		log.info(thisMethodName+":end");
		return "house/hangup/houseHangupList";
	}

	/**
	 * 跳转到更新用户信息的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	 //挂起里暂时用不到这个方法
	@RequestMapping("/update.do")
	public String houseHangupUpdate(int id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		User user = this.userService.getUserById(id);
		map.put("data", user);
		
		log.info(thisMethodName+":end");
		return "house/hangup/houseHangupUpdate";
	}

	/**
	 * up or down挂起与解除
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/upordown.do")
	public String houseHangupUpordown(int id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			User user = userService.getUserById(id);
//			User user = (User) RequestUtils.getRequestBean(User.class, request);
			if (user.getId() != 0) {
				if("1".equals(user.getState())){
					user.setState("2");
				}else if("2".equals(user.getState())){
					user.setState("1");
				}
				this.userService.updateUser(user);
			}
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			throw e;
		}
		log.info(thisMethodName+":end");
		return "house/hangup/houseHangupQuery";
	}

}
