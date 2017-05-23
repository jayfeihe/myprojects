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
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Depart;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Menu;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;
import com.tera.sys.model.form.UserQBean;
import com.tera.sys.service.DepartService;
import com.tera.sys.service.MenuService;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.SysLogService;
import com.tera.sys.service.UserService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.JsonUtil;
import com.tera.util.NetUtils;
import com.tera.util.ObjectUtils;
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
	/**
	 * roleService
	 */
	@Autowired
	private RoleService roleService;
	/**
	 * menuService
	 */
	@Autowired
	private MenuService menuService;

	@Autowired
	private OrgService orgService;
	
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private DepartService departService;
	
	
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
	@SuppressWarnings(value = { "unchecked" })
	@RequestMapping("/sys/user/list.do")
	public String sysUserList(HttpServletRequest request, ModelMap map,String orgId,String roleId) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		UserQBean userQBean = (UserQBean) RequestUtils.getRequestBean(UserQBean.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(userQBean);
//		int rowsCount = userService.getUserCount(beanMap);

		PageModel pm = new PageModel();
		pm.init(request, null, userQBean);
		beanMap.put("curPage", pm.getCurPage());
		beanMap.put("pageSize", pm.getPageSize());
		
		PageList<User> users = this.userService.queryPageList(beanMap);
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
	public void sysUserAddAction(HttpServletRequest request,
			HttpServletResponse response, ModelMap map)throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user = (User) RequestUtils.getRequestBean(User.class, request);
		this.userService.addUser(user);
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
	 */
	@RequestMapping("/sys/user/update.do")
	public String sysUserUpdate(int id, HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user = this.userService.getUserById(id);
		map.put("data", user);
		log.info(thisMethodName+":end");
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
	public void sysUserUpdateAction(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user = (User) RequestUtils.getRequestBean(User.class, request);
		this.userService.updateUser(user);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
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
	        String flag = "false";
	        out = response.getWriter();
	        //如果是更新操作　且没有改变　验证通过
	        if (null != origindata && !"".equals(origindata) && loginId.equals(origindata)) {
	        	flag = "true";
	        	out.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
	        } else {
	        	User user = this.userService.getUser(loginId);
	        	if (user != null) {
	        		out.print(JsonUtil.object2json(new JsonMsg(false, "登录ID已存在，请重新输入")));
	        	} else {
	        		flag = "true";
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
			this.userService.updateUser(user);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
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
	@SuppressWarnings(value = { "unchecked" })
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
	
	
	
	
	
	/**
	 * 打开为用户设置机构页面
	 * @param id user's id
	 * @param roleType role type
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return ExcelView ExcelView
	 */
	/*	@RequestMapping("/sys/user/setOrg.do")
	public String sysUserSetOrg(int id, String roleType, HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Org> allOrgs = this.orgService.queryList(new HashMap());
		List<Org> userOrgs = this.orgService.getOrgByUserId(id);
		String orgAndRoles = this.roleService.getOrgRoleByUserId(id);
		User user = this.userService.getUserById(id);
		StringBuffer sb = new StringBuffer();
		for (Org org : userOrgs) {
			sb.append(",{menudm:'" + org.getOrgId() + "'}");
		}
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("type", roleType);
		List<Role> zhiwei = this.roleService.queryRole(map1);
		StringBuffer zw=new StringBuffer();
		zw.append("<em id=\"'+orgid+'\">");
		for (Role role : zhiwei) {
			zw.append("<input type=\"checkbox\" name=\"orgAndRole\" value=\"'+orgid+'_");
			zw.append(role.getId());
			zw.append("\">");
			zw.append(role.getName());
			zw.append("&nbsp;&nbsp;");
		}
		zw.append("</em>");
		sb.replace(0, 1, "");
		map.put("id", id);
		map.put("orgAndRoles", orgAndRoles);
		map.put("loginId", user.getLoginId());
		map.put("zhiwei", zw.toString());
		map.put("allOrgs", allOrgs);
		map.put("userOrgs", sb.toString());
		log.info(thisMethodName+":end");
		return "sys/user/sysUserSetOrg";
	}*/
	
	/**
	 * 跳转到机构岗位设置页面
	 * @param name
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sys/user/setOrg.do")
	public String sysUserSetOrgAndRole(String id,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		Map<String,Object> userMap = new HashMap<String,Object>();
		userMap.put("id", id);
		List<User> setUser = this.userService.queryUser(userMap);
		
		if (setUser != null && setUser.size() > 0) {
			log.info("============= 转到[" + setUser.get(0).getName() + "]岗位设置页面 ================");
			map.put("setUserLoginId", setUser.get(0).getLoginId());
			map.put("name", setUser.get(0).getName());
		}
		map.put("userId", id);
		log.info(thisMethodName+":end");
		return "sys/user/sysUserSetOrg";
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
			List<Org> orgTreeList = this.orgService.queryListByQuery(allOrgs);
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
	
	@RequestMapping("/sys/user/setRole.do")
	public String sysUserSetRole(String userId,String loginId,String orgId,String orgCode,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		// 所有角色
		List<Role> allRoles = this.roleService.getAllRoles();
		// 当前用户所在机构拥有的角色
		Map<String, Object> roleMap = new HashMap<String,Object>();
		roleMap.put("orgId", orgCode);
		roleMap.put("loginId", loginId);
		List<Role> userOwnRoles = this.roleService.getRoleByOrgLoginId(roleMap);
		StringBuilder roleBuild = new StringBuilder();
		if (userOwnRoles != null && userOwnRoles.size() > 0) {
			for (Role tmpRole : userOwnRoles) {
				roleBuild.append(tmpRole.getId()+",");
			}
			String roleIds = roleBuild.toString();
			roleIds = roleIds.substring(0, roleIds.lastIndexOf(","));
			map.put("roleIds", roleIds);
		}
		map.put("allRoles", allRoles);
		
		map.put("setUserloginId", loginId);
		map.put("userId", userId);
		map.put("orgId", orgId);
		
		log.info(thisMethodName+":end");
		return "sys/user/sysUserSetRole";
	}
	
	/**
	 * 设置角色
	 * @param id  id
	 * @param request request
	 * @param response response
	 * @param roleIds roleIds
	 * @param map map
	 * @return string
	 * @throws Exception excetption
	 */
	@RequestMapping("/sys/user/setOrgAction.do")
	public void sysUserSetOrgAction(int id,String loginId, String orgIds, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		String[] org_Role_ids = StringUtils.delimitedListToStringArray(orgIds, ",");
		this.orgService.updateOrgByUserId(id,loginId, org_Role_ids);
		response.setContentType("application/json;charset=UTF-8");
		
		//添加日志
		try {
			String ipAddress = NetUtils.getIpAddr(request);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			//已分配的机构和角色信息，加入remarkTempList
			List<String> remarkTempList = new ArrayList<String>();
			for(int i=0;i<org_Role_ids.length;i++){
				//循环，拆分org_Role_ids，得到org和role，分别加入list，汉字展示
				String[] orgAndRole = StringUtils.delimitedListToStringArray(org_Role_ids[i], "_");
				String orgTemp = orgService.queryByKey(orgAndRole[0]).getOrgName();
				String roleTemp = roleService.getRoleById(Integer.parseInt(orgAndRole[1])).getName();
				String remarkTemp = orgTemp + " 的 " + roleTemp;
				remarkTempList.add(remarkTemp);
			}
			//被分配的用户
			String userName = userService.getUserById(id).getName();
			//拼出备注标识
			String remark = "";
			if (remarkTempList.size() > 0) {
				remark = "变更 " + userName + " 为：" + remarkTempList.get(0);
				for (int i = 1; i < remarkTempList.size(); i++) {
					remark = remark + " ， " + remarkTempList.get(i);
				} 
				//插入日志
				sysLogService.addSysLog(new SysLog(ipAddress, user, org.getOrgId(), "岗位变更", "" ,remark));
			}

			PrintWriter writer = response.getWriter();
			writer.print(JsonUtil.object2json(new JsonMsg(true, "机构设置成功")));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			PrintWriter writer = response.getWriter();
			writer.print(JsonUtil.object2json(new JsonMsg(false, "系统异常！")));
			writer.flush();
			writer.close();
			throw e;
		}

//		PrintWriter writer = response.getWriter();
//		writer.print(JsonUtil.object2json(new JsonMsg(true, "机构设置成功")));
//		writer.flush();
//		writer.close();
		log.info(thisMethodName+":end");
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
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if("".equals(orgId)||orgId==null){
			orgId=org.getOrgId();
		}
		List<User> users = userService.getUserByOrgAndRoleAndDepart(orgId, roleNames,null);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(users));
		writer.flush();
		writer.close();
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
	
	/**
	 * 跳到组织机构设置页面
	 * @param id
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sys/user/setDepart.do")
	public String sysUserSetDepart(int id, HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User user = this.userService.getUserById(id);
		map.put("user", user);
		
		List<String> departIds = departService.queryUserDepartByLoginId(user.getLoginId());
		map.put("departIds", StringUtils.collectionToDelimitedString(departIds, ","));
		
		log.info(thisMethodName+":end");
		return "sys/user/sysUserSetDepart";
	}
	
	/**
	 * 设置用户组织机构
	 * @param loginId
	 * @param departIds
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/sys/user/setDepartAction.do")
	public void sysUserSetDepartAction(String loginId,String name, String departIds, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String ipAddress = NetUtils.getIpAddr(request);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		try {
			String[] depart_ids = StringUtils.delimitedListToStringArray(departIds, ",");
			this.departService.updateUserDepart(loginId, depart_ids);
			String remark = "";
			if (depart_ids.length > 0) {
				StringBuilder remarkBuild = new StringBuilder(name + "的组织机构变为：");
				for (String departId : depart_ids) {
					Depart depart = departService.queryByKey(departId);
					remarkBuild.append(depart.getDepartName() + "、");
				}
				remark = remarkBuild.toString();
				remark = remark.substring(0, remark.lastIndexOf("、"));
			} else {
				remark = "清空"+name+"的组织机构";
			}
			//插入日志
			sysLogService.addSysLog(new SysLog(ipAddress, user, org.getOrgId(), "组织机构变更", "" ,remark));
			writer.print(JsonUtil.object2json(new JsonMsg(true, "机构设置成功")));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "系统异常！")));
			writer.flush();
			writer.close();
			throw e;
		}
	}
	
	/**
	 * 通过角色id筛选出所需角色类型的用户
	 * @throws IOException 
	 * @author wangyongliang 20150610
	 */
	/*@RequestMapping("/sys/user/getPhoneUser.do")
	public void getPhoneUser(String roleName,String org_id,HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
//		int roleId=Integer.parseInt(roleName);
		Map<String, Object> mapUser=new HashMap<String, Object>();
		mapUser.put("roleName",roleName);
		mapUser.put("orgId",org_id);
		List<User> phoneUserList = this.userService.queryUserByGroup(mapUser);
		writer.print(JsonUtil.object2json(phoneUserList));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}*/
}
