/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Dept;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Menu;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.RoleDataRel;
import com.tera.sys.model.User;
import com.tera.sys.model.UserExt;
import com.tera.sys.model.form.UserQBean;
import com.tera.sys.service.DeptService;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleDataRelService;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserExtService;
import com.tera.sys.service.UserService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.PasswordUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * @author Wallace chu
 *
 */
@Controller

public class UserController {
	
	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(UserController.class);
	
	/**
	 * userService
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private UserExtService userExtService;
	
	/**
	 * roleService
	 */
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private DeptService deptService;

	@Autowired
	private OrgService orgService;
	
	@Autowired
	private RoleDataRelService roleDataRelService;
	
	
	/**
	 * 打开查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/sys/user/query.do")
	public String sysUserQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/user/sysUserQuery";
	}
	/**
	 * 打开数据列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return user list
	 */
	@RequestMapping("/sys/user/list.do")
	public String sysUserList(HttpServletRequest request, ModelMap map,String orgId,String roleId) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		UserQBean userQBean = (UserQBean) RequestUtils.getRequestBean(UserQBean.class, request);
		Map<String, Object> queryMap = null;
		queryMap = ObjectUtils.describe(userQBean);
		
		PageModel pm = new PageModel();
		pm.init(request, null, userQBean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<User> users = this.userService.queryPageList(queryMap);
		pm.setData(users);
		pm.initRowsCount(users.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "sys/user/sysUserList";
	}
	/**
	 * 打开添加数据页面
	 * @param request request
	 * @param map map
	 * @return add user
	 */
	@RequestMapping("/sys/user/add.do")
	public String sysUserAdd(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		//获取机构
		List<Org> subOrgs = this.orgService.getSubOrg(BusinessConstants.ORG_CODE,"1");
		map.put("orgs", subOrgs);
		
		log.info(thisMethodName+":end");
		return "sys/user/sysUserAdd";
	}
	/**
	 * 添加数据到数据库
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/user/addaction.do")
	public void sysUserAddAction(String[] roleId,String[] orgId,String[] deptId,HttpServletRequest request,
			HttpServletResponse response, ModelMap map)throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user = (User) RequestUtils.getRequestBean(User.class, request);
		
		this.userService.addUser(user,roleId,orgId,deptId);
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	/**
	 * 删除数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param ids ids
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/user/deluser.do")
	public void sysDelUser(String ids, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		int[] idArray = StringUtils.tranStrToIntArray(ids, ",");
		try {
			this.userService.deleteUserByIDs(idArray);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	/**
	 * 打开更新两面
	 * @param request request
	 * @param map map
	 * @param id 用户ID
	 * @return update user
	 * @throws Exception 
	 */
	@RequestMapping("/sys/user/update.do")
	public String sysUserUpdate(int id,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//获取机构
		List<Org> subOrgs = this.orgService.getSubOrg(BusinessConstants.ORG_CODE,"1");
		map.put("orgs", subOrgs);
		
		User user = this.userService.getUserById(id);
		
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("loginId", user.getLoginId());
		List<UserExt> userExts = this.userExtService.queryList(queryMap );
		String rolestr="";
		if (userExts != null && userExts.size() > 0) {
			for (UserExt userExt : userExts) {
				if ("".equals(rolestr)) {
					rolestr=String.valueOf(userExt.getRoleId());
				}else {
					rolestr=rolestr+","+String.valueOf(userExt.getRoleId());
				}
			}
			Role role = this.roleService.getRoleById(userExts.get(0).getRoleId());
			user.setRoleLevel(role.getLever());
			Dept dept = this.deptService.queryByKey(userExts.get(0).getDeptId());
			String deptId = null;
			if (dept != null) {
				deptId = String.valueOf(dept.getId());
			}
			map.put("zbRoleId",rolestr);
			if (role != null) {
				// 总部
				if ("1".equals(role.getLever())) {
					//map.put("zbRoleId",rolestr);
					map.put("zbDeptId", deptId);

					//queryMap.put("roleId", role.getId());
					List<RoleDataRel> tmpDatas = this.roleDataRelService.queryList(queryMap);
					List<String> orgIds = new ArrayList<String>(tmpDatas.size());
					for (RoleDataRel data : tmpDatas) {
						orgIds.add(data.getOrgId());
					}
					map.put("zbOrgIds", StringUtils.collectionToDelimitedString(orgIds, ","));
				}
				// 分公司
				if ("2".equals(role.getLever())) {
					//map.put("fgsRoleId", role.getId());
					map.put("fgsDeptId", deptId);

					//queryMap.put("roleId", role.getId());
					List<UserExt> tmpExts = this.userExtService.queryList(queryMap);
//					List<String> orgIds = new ArrayList<String>(tmpExts.size());
//					for (UserExt userExt : tmpExts) {
//						orgIds.add(userExt.getOrgId());
//					}
					map.put("fgsOrgIds", tmpExts.get(0).getOrgId());
				}
				// 自定义
				if ("3".equals(role.getLever())) {
					//map.put("cusRoleId", role.getId());
					map.put("cusDeptId", deptId);

					//queryMap.put("roleId", role.getId());
					List<UserExt> tmpExts = this.userExtService.queryList(queryMap);
//					List<String> orgIds = new ArrayList<String>(tmpExts.size());
//					for (UserExt userExt : tmpExts) {
//						orgIds.add(userExt.getOrgId());
//					}
					map.put("cusOrgIds", tmpExts.get(0).getOrgId());
				} 
			}
		}
		map.put("data", user);
		log.info(thisMethodName+":end");
		if(StringUtils.isNotNullAndEmpty(opt)&&opt.equals("pwd")){
			return "sys/user/sysUserUpdatePwd";
		}
		return "sys/user/sysUserUpdate";
	}
	/**
	 * 更新到数据库
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/user/updateaction.do")
	public void sysUserUpdateAction(String[] roleId,String[] orgId,String[] deptId,HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();;
		try {
			User user = (User) RequestUtils.getRequestBean(User.class, request);
			this.userService.addUser(user, roleId, orgId,deptId);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	/**
	 * 修改密码
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/user/updatePwd.do")
	public void sysUserUpdatePwd(String loginId,HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();;
		try {
			User user = (User) RequestUtils.getRequestBean(User.class, request);
			User updateUser=this.userService.getUser(loginId);
			updateUser.setPassword(PasswordUtil.md5(user.getPassword()));
			this.userService.updateUser(updateUser);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 验证用户名loginId是否重复
	 * @param loginID loginID
	 * @param request requst
	 * @param response response
	 */
	@RequestMapping("/sys/user/userbyusername.do")
	public void sysQueryUserByUsername(String loginId, HttpServletRequest request, HttpServletResponse response) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PrintWriter out = null;
	    String origindata = request.getParameter("origindata");
		try {
			response.setContentType("application/json;charset=UTF-8");
//	        String flag = "false";
	        out = response.getWriter();
	        //如果是更新操作　且没有改变　验证通过
	        if (null != origindata && !"".equals(origindata) && loginId.equals(origindata)) {
//	        	flag = "true";
	        	out.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
	        } else {
	        	User user = this.userService.getUser(loginId);
	        	if (user != null) {
	        		out.print(JsonUtil.object2json(new JsonMsg(false, "登录ID已存在，请重新输入")));
	        	} else {
//	        		flag = "true";
	        		out.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
	        	}
	        }
	    } catch (Exception e) {
	    	log.error(thisMethodName+":error",e);
	    	e.printStackTrace();
	    } finally {
	        out.close();
	    }
	    log.info(thisMethodName+":end");
	}
	/**
	 * 修改用户自己的信息
	 * @param request request
	 * @param response response
	 * @param map map
	 * @return string
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/user/updatemyself.do")
	public String updateMyself(HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User sessionUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		User user = this.userService.getUserById(sessionUser.getId());

		map.put("data", user);
		log.info(thisMethodName+":end");
		return "sys/user/sysUserUpdateMyself";

	}
	/**
	 * 修改用户自己的信息
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/user/updatemyselfaction.do")
	public void updateMyselfAction(HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			User user = (User) RequestUtils.getRequestBean(User.class, request);
			user.setPassword(PasswordUtil.md5(user.getPassword()));//加密
			this.userService.updateUser(user);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "修改成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return ModelAndView ModelAndView
	 */
	@RequestMapping("/sys/user/excel.do")
	public ModelAndView sysUserExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user = (User) RequestUtils.getRequestBean(User.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(user);
		List<User> users = this.userService.exportUser(beanMap);

		String title = "用户表";
		String[] head = new String[]{"姓名", "性别", "登录名", "电邮", "电话", "移动电话"};

		Object[][] obj = new Object[users.size()][6];
		for (int i = 0; i < users.size(); i++) {
			User myUser = users.get(i);
			Object[] values = new Object[6];
			values[0] = null != myUser.getName() ? myUser.getName() : "";
			values[1] = null != myUser.getGender() ? myUser.getGender() : "";
			values[2] = null != myUser.getLoginId() ? myUser.getLoginId() : "";
			values[3] = null != myUser.getEmail() ? myUser.getEmail() : "";
			values[4] = null != myUser.getPhone() ? myUser.getPhone() : "";
			values[5] = null != myUser.getMobile() ? myUser.getMobile() : "";
			obj[i] = values;
		}
		ExcelReportTable report = new ExcelReportTable(title, head, obj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("data.xls"), map);
	}
	
	/**根据机构和角色名称查询用户列表
	 * @param orgId
	 * @param roleNames
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/sys/user/listUserByOrgAndRole.do")
	public void sysListUserByOrgAndRole(String orgId, String[] roleNames, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		/*Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if("".equals(orgId)||orgId==null){
			orgId=org.getOrgId();
		}*/
		List<User> users = userService.getUserByOrgAndRole(orgId, roleNames,null);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(users));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 机构岗位树
	 * @param id
	 * @param roleType
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sys/user/listOrgTree.do")
	public void sysUserListOrgTree(String loginId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PrintWriter writer = null;
		try {
			response.setContentType("application/json;charset=UTF-8");
			writer = response.getWriter();
			
			Object bean = RequestUtils.getRequestBean(Org.class, request);
			Map<String, Object> queryMap = ObjectUtils.describe(bean);
			
			// 获取用户
			User setUser = null;
			if (StringUtils.isNotNullAndEmpty(loginId)) {
				setUser = this.userService.getUser(loginId);
			}
			
			List<Org> allOrgs = this.orgService.queryList(queryMap);
			
			// 如果不是管理员，进行匹配角色
			if (setUser != null && 1 != setUser.getIsAdmin()) {
				if (allOrgs != null && allOrgs.size() > 0) {
					List<Role> userOwnRoles = null;
					Map<String, Object> roleMap = new HashMap<String, Object>();
					roleMap.put("loginId", loginId);
					for (Org tmpOrg : allOrgs) {
						List<String> roleNames = new ArrayList<String>();
						StringBuilder idBuild = new StringBuilder();
						roleMap.put("orgId", tmpOrg.getOrgId());
						// 当前用户所在机构拥有的角色
						userOwnRoles = this.roleService.getRoleByOrgLoginId(roleMap);
						if (userOwnRoles != null && userOwnRoles.size() > 0) {
							for (Role role : userOwnRoles) {
								roleNames.add(role.getName());
								idBuild.append(tmpOrg.getId() + "_" + role.getId() + ",");
							}
							tmpOrg.setRoleNames(roleNames);
							String orgAndRoleIds = "";
							orgAndRoleIds = idBuild.toString();
							if (StringUtils.isNotNullAndEmpty(orgAndRoleIds)) {
								orgAndRoleIds = orgAndRoleIds.substring(0, orgAndRoleIds.lastIndexOf(","));
							}
							tmpOrg.setOrgAndRoleIds(orgAndRoleIds);
						}
					}
				} 
			}
			// 用于机构树显示查询
			List<Org> orgTreeList = this.orgService.queryListTree(allOrgs);
			writer.print(JsonUtil.object2json(orgTreeList));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			writer.print(JsonUtil.object2json(new JsonMsg(false, "加载失败")));
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 添加缩进
	 * @param menus list
	 * @return list
	 */
	protected List<Menu> resetMenuName(List<Menu> menus) {
		for (Menu menu : menus) {
			menu.setName(StringUtils.generateRepeatString(menu.getMenuLevel(), "&nbsp;&nbsp;&nbsp;") + menu.getName());
		}
		return menus;
	}
}
