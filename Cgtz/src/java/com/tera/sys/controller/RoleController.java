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
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Menu;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.service.MenuService;
import com.tera.sys.service.RoleService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * @author Wallace chu
 *
 */
@Controller

public class RoleController {
	/**
	 * menRolevice
	 */
	@Autowired
	private RoleService roleService;
	/**
	 * menRolevice
	 */
	@Autowired
	private MenuService menuService;

	/**
	 * 日志
	 */
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(RoleController.class);
	/**
	 * 打开查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/sys/role/query.do")
	public String sysRoleQuery(HttpServletRequest request, ModelMap map) throws Exception {
		
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
//		List<RoleType> roleTypes = this.roleService.getRoleTypes();
//		map.put("roleTypes", roleTypes);
		return "sys/role/sysRoleQuery";
	}
	/**
	 * 打开数据列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return role list
	 */
	@RequestMapping("/sys/role/list.do")
	public String sysRoleList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Role role = (Role) RequestUtils.getRequestBean(Role.class, request);
		Map<String, Object> queryMap = null;
		queryMap = ObjectUtils.describe(role);
		
		pm.init(request, null, role);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Role> roles = this.roleService.queryPageList(queryMap);
		pm.setData(roles);
		pm.initRowsCount(roles.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "sys/role/sysRoleList";
	}
	/**
	 * 打开添加数据页面
	 * @param request request
	 * @param map map
	 * @return add role
	 */
	@RequestMapping("/sys/role/add.do")
	public String sysRoleAdd(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
//		List<RoleType> roleTypes = this.roleService.getRoleTypes();
//		map.put("roleTypes", roleTypes);
		return "sys/role/sysRoleAdd";
	}
	/**
	 * 添加数据到数据库
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/role/addaction.do")
	public void sysRoleAddAction(HttpServletRequest request,
			HttpServletResponse response, ModelMap map)throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Role role = (Role) RequestUtils.getRequestBean(Role.class, request);
		role.setFlag("0");//用户自定义默认
		this.roleService.addRole(role);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
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
	@RequestMapping("/sys/role/delRole.do")
	public void sysDelRole(String ids, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		int[] idArray = StringUtils.tranStrToIntArray(ids, ",");
		try {
			this.roleService.deleteRoleByIDs(idArray);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
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
	 * @param id 角色ID
	 * @return update role
	 */
	@RequestMapping("/sys/role/update.do")
	public String sysRoleUpdate(int id, HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Role role = this.roleService.getRoleById(id);
//		List<RoleType> roleTypes = this.roleService.getRoleTypes();
//		map.put("roleTypes", roleTypes);
		map.put("data", role);
		log.info(thisMethodName+":end");
		return "sys/role/sysRoleUpdate";
	}
	/**
	 * 更新到数据库
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/role/updateaction.do")
	public void sysRoleUpdateAction(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Role role = (Role) RequestUtils.getRequestBean(Role.class, request);
		this.roleService.updateRole(role);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	/**
	 * @param id role's id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return ExcelView ExcelView
	 */
	@RequestMapping("/sys/role/setmenu.do")
	public String sysSettleRole(int id, HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Menu> allMenus = this.menuService.getAllAvailableMenu(this.menuService.getAvailableMenu());
		allMenus.get(0).setParentId(-1);
		List<Menu> roleMenus = this.menuService.getMenuByRoleId(id);
		Role role = this.roleService.getRoleById(id);
		//{menudm:'0'},{menudm:'100'},{menudm:'790'},{menudm:'300'},{menudm:'800'},{menudm:'810'}
		StringBuffer sb = new StringBuffer();
		for (Menu menu : roleMenus) {
			sb.append(",{menudm:'" + menu.getId() + "'}");
		}
		sb.replace(0, 1, "");
		map.put("id", id);
		map.put("role", role);
		map.put("allMenus", allMenus);
		map.put("roleMenus", sb.toString());
		log.info(thisMethodName+":end");

		return "sys/role/sysRoleSetMenu";
	}
	/**
	 * @param id role's id
	 * @param request request
	 * @param map map
	 * @param menuIds menuIds
	 * @param response response
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/role/setmenuaction.do")
	public void sysSettleRoleAction(int id, String menuIds, HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//String menuIds = request.getParameterValue("dtreeCheck[]");

		int[] mIds = StringUtils.tranStrToIntArray(menuIds, ",");
		this.menuService.updateMenuByRoleId(id, mIds);

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
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
	@RequestMapping("/sys/role/excel.do")
	public ModelAndView sysRoleExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Role role = (Role) RequestUtils.getRequestBean(Role.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(role);
		List<Role> roles = this.roleService.exportRole(beanMap);

		String title = "角色表";
		String[] head = new String[]{"名称", "描述"};

		Object[][] obj = new Object[roles.size()][2];
		for (int i = 0; i < roles.size(); i++) {
			Role myRole = roles.get(i);
			Object[] values = new Object[6];
			values[0] = null != myRole.getName() ? myRole.getName() : "";
			values[1] = null != myRole.getDescription() ? myRole.getDescription() : "";
			obj[i] = values;
		}
		ExcelReportTable report = new ExcelReportTable(title, head, obj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("data.xls"), map);
	}

	
	@RequestMapping("/sys/role/querybyTypeList.do")
	public String sysRoleQueryList(String type,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
//		this.roleService.getRoleByType(type);
		return "sys/role/sysRoleQuery";
	}

	@RequestMapping("/sys/role/queryAllRole.do")
	public void queryAllRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Role> roles=roleService.getAllRoles();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(roles));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/sys/role/queryRoleByLever.do")
	public void queryRoleByLever(String lever,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Map<String, Object> beanMap = new HashMap<String,Object>();
		beanMap.put("lever", lever);
		List<Role> roles=roleService.queryRole(beanMap );
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(roles));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/sys/role/isDataAccess.do")
	public void isDataAccess(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		if (StringUtils.isNotNullAndEmpty(id)) {
			Role role = roleService.getRoleById(Integer.parseInt(id));
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print(JsonUtil.object2json(role));
			writer.flush();
			writer.close();
		}
		log.info(thisMethodName+":end");
	}
}
