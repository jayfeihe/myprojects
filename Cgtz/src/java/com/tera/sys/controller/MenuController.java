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

import com.tera.sys.constant.ReportConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Menu;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.form.MenuQBean;
import com.tera.sys.service.MenuService;
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

public class MenuController {
	/**
	 * menuService
	 */
	@Autowired
	private MenuService menuService;

	/**
	 * 日志
	 */
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(MenuController.class);
	/**
	 * 打开查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/sys/menu/query.do")
	public String sysMenuQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Menu> list = new ArrayList<Menu>();
		menuService.sort(this.menuService.getAllParentMenus(), list, null, 0);
		map.put("pmenus", this.resetMenuName(list));
		log.info(thisMethodName+":end");
		return "sys/menu/sysMenuQuery";
	}
	/**
	 * 打开数据列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return menu list
	 */
	@SuppressWarnings(value = { "unchecked" })
	@RequestMapping("/sys/menu/list.do")
	public String sysMenuList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//String isAllDataChk = request.getParameter("isAllDataChk");
		String parentId = request.getParameter("parentId");
		if (null != parentId && !"".equals(parentId)) {
			//在parentId下查询
			PageModel pm = new PageModel();
			MenuQBean menuQBean = (MenuQBean) RequestUtils.getRequestBean(MenuQBean.class, request);
			
			List<Menu> newMenus=new ArrayList<Menu>();
			Menu meMenu = this.menuService.getMenuById(Integer.parseInt(parentId));
			if(meMenu!=null){
				List<Menu> menus = this.menuService.getAllMenu();
				menuService.sort(menus, newMenus, meMenu, 0);
			}
			resetMenuName(newMenus);
			pm.setData(newMenus);
			map.put("pm", pm);
			log.info(thisMethodName+":end");
			return "sys/menu/sysMenuList";

		} else {
			//全数据显示
			String meId = request.getParameter("meId");
			String option = request.getParameter("option");
			//序号调整
			if (null != meId && null != option) {
				this.menuService.changeMenuOrderNum(Integer.parseInt(meId), option);
			}
			PageModel pm = new PageModel();
			MenuQBean menuQBean = (MenuQBean) RequestUtils.getRequestBean(MenuQBean.class, request);
			Map<String, Object> beanMap = ObjectUtils.describe(menuQBean);
			List<Menu> newMenus=new ArrayList<Menu>();
			if(menuQBean.getName()!=null&&!menuQBean.getName().equals("")){
				List<Menu> me=this.menuService.queryMenuTree(beanMap);
				Menu meMenu= me!=null&&me.size()>0?me.get(0):null;
				if(meMenu!=null){
					List<Menu> menus = this.menuService.getAllMenu();
					menuService.sort(menus, newMenus, meMenu,0);
				}
			}else{
				List<Menu> menus = this.menuService.getAllMenu();
				menuService.sort(menus, newMenus, null,0);
			}
			resetMenuName(newMenus);
			pm.setData(newMenus);
			map.put("pm", pm);
			return "sys/menu/sysMenuTreeList";
		}
	}
	

	
	
	/**
	 * 打开添加数据页面
	 * @param request request
	 * @param map map
	 * @return add menu
	 */
	@RequestMapping("/sys/menu/add.do")
	public String sysMenuAdd(HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		String parentId=request.getParameter("parentId");
		if(parentId!=null&&!parentId.equals("")){
			Menu menu = this.menuService.getMenuById(Integer.parseInt(parentId));
			map.put("parentMenu", menu);
			
		}
		List<Menu> list = new ArrayList<Menu>();;
		menuService.sort(this.menuService.getAllParentMenus(), list, null,0);
		map.put("pmenus", this.resetMenuName(list));
		log.info(thisMethodName+":end");
		return "sys/menu/sysMenuAdd";
	}
	/**
	 * 添加数据到数据库
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/menu/addaction.do")
	public void sysMenuAddAction(HttpServletRequest request,
			HttpServletResponse response, ModelMap map)throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Menu menu = (Menu) RequestUtils.getRequestBean(Menu.class, request);
		List<Menu> menus = this.menuService.getMenusByParentId(menu.getParentId());
		int od=0;
		if (null != menus && menus.size() > 0) {
			od=menus.get(0).getOrdernum()+1;
			
		}
		menu.setOrdernum(od);
		this.menuService.addMenu(menu);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JsonMsg jm=new JsonMsg(true,"添加成功");
		writer.print(JsonUtil.object2json(jm));
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
	@RequestMapping("/sys/menu/delect.do")
	public void sysDelMenu(String ids, HttpServletResponse response,
			HttpServletRequest request, ModelMap map,String state) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		int[] idArray = StringUtils.tranStrToIntArray(ids, ",");

		int prepareId = idArray[0];
		String errMsg="";
		String succMsg="";
		String excMsg="";
		if(state.equals("1")){
			succMsg="禁用成功!";
			errMsg="存在可用菜单，禁用失败";
			excMsg="所禁用数据下存在可用菜单";
		}else{
			succMsg="开启成功!";
			errMsg="请先开启父菜单,开启失败";
			excMsg="所开启数据其父菜单为禁用";
		}
		Boolean bool=false;
		List<Menu> menus = this.menuService.getMenusByParentId(prepareId);
		if (null != menus && menus.size() > 0) {
			for (int i = 0; i < menus.size(); i++) {
				Menu menu=menus.get(i);
				//state 1 可用，，进行禁用操作 子菜单可用时，父菜单不能禁用
				if(state.equals("1")&&menu.getState().equals("1")){
					bool=true;
				}
			}
			//this.menuService.deleteMenuByIDs(idArray);
		}
		if (bool) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, errMsg)));
			writer.flush();
			writer.close();
			throw new Exception(excMsg);
		}
		//state 0 不可用   进行启用操作 父菜单禁用时，子菜单不能开启
		if(state.equals("0")){
			//获取其父菜单
			List<Menu> menuList=this.menuService.getParentMenusById(idArray[0]);
			Menu menu=menuList.get(0);
			//父菜单不可用
			if(menu.getState().equals("0")){
				writer.print(JsonUtil.object2json(new JsonMsg(false, errMsg)));
				writer.flush();
				writer.close();
				throw new Exception(excMsg);
			}
		}
		this.menuService.deleteMenu(idArray);
		writer.print(JsonUtil.object2json(new JsonMsg(true, succMsg)));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	/**
	 * 打开更新两面
	 * @param request request
	 * @param map map
	 * @param id 菜单ID
	 * @return update menu
	 */
	@RequestMapping("/sys/menu/update.do")
	public String sysMenuUpdate(int id, HttpServletRequest request, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Menu menu = this.menuService.getMenuById(id);
		List<Menu> list = new ArrayList<Menu>();;
		menuService.sort(this.menuService.getAllParentMenus(), list, null,0);
		map.put("pmenus", this.resetMenuName(list));
		map.put("data", menu);
		log.info(thisMethodName+":end");
		return "sys/menu/sysMenuUpdate";
	}
	/**
	 * 更新到数据库
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/menu/updateaction.do")
	public void sysMenuUpdateAction(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Menu menu = (Menu) RequestUtils.getRequestBean(Menu.class, request);
		if (null == menu.getUrl() || "".equals(menu.getUrl().trim())) {
			menu.setUrl(null);
		}
		this.menuService.updateMenu(menu);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(new JsonMsg(true, "修改成功")));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 调整菜单的序号
	 * change orderNum
	 * @param meId id
	 * @param otherId id
	 * @param request request
	 * @param response response
	 * @throws Exception exception
	 */
	@RequestMapping("/sys/menu/changeorder.do")
	public void sysMenuChangeOrderNum(int meId, int otherId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		this.menuService.changeMenuOrderNum(meId, otherId);

		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print("true");
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
	@RequestMapping("/sys/menu/excel.do")
	public ModelAndView sysMenuExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Menu menu = (Menu) RequestUtils.getRequestBean(Menu.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(menu);
		List<Menu> menus = this.menuService.exportMenu(beanMap);

		String title = "菜单表";
		String[] head = new String[]{"名称", "URL", "描述", "序号"};

		Object[][] obj = new Object[menus.size()][4];
		for (int i = 0; i < menus.size(); i++) {
			Menu myMenu = menus.get(i);
			Object[] values = new Object[4];
			values[0] = null != myMenu.getName() ? myMenu.getName() : "";
			values[1] = null != myMenu.getUrl() ? myMenu.getUrl() : "";
			values[2] = null != myMenu.getDescription() ? myMenu.getDescription() : "";
			values[3] = 0 != myMenu.getOrdernum() ? myMenu.getOrdernum() + "" : "";
			obj[i] = values;
		}
		ExcelReportTable report = new ExcelReportTable(title, head, obj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("data.xls"), map);
	}

	/**
	 * 添加缩进
	 * @param menus list
	 * @return list
	 */
	protected List<Menu> resetMenuName(List<Menu> menus) {
			for (Menu menu : menus) {
				menu.setName(StringUtils.generateRepeatString(menu.getMenuLevel(), "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;") + menu.getName());
			}
			return menus;
	}

}
