package com.greenkoo.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.greenkoo.sys.model.SysJsonMsg;
import com.greenkoo.sys.model.SysMenu;
import com.greenkoo.sys.model.SysUser;
import com.greenkoo.sys.model.form.MenuVo;
import com.greenkoo.sys.service.ISysMenuService;
import com.greenkoo.utils.DateUtil;

/**
 * 菜单Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/sys/menu")
public class SysMenuController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
 	
	@Autowired
	private ISysMenuService sysMenuService;
	
	@RequestMapping("/list")
	public String menuList(Model model) {
		
		List<SysMenu> l2Menu = sysMenuService.queryMenuByLevel(2,null);
		List<SysMenu> l3Menu = sysMenuService.queryMenuByLevel(3,null);
		
		List<MenuVo> menuList=new ArrayList<MenuVo>();
		for (SysMenu m2 : l2Menu) {
			MenuVo menu2=new MenuVo(m2);
			//当前菜单的子级菜单集合
			List<MenuVo> list2=new ArrayList<MenuVo>();
			for (SysMenu m3 : l3Menu) {
				if (m3.getParentMenuId().equals(m2.getMenuId())) {
					MenuVo menu3 = new MenuVo(m3);
					list2.add(menu3);
				}
			}
			if(list2!=null&&list2.size()>0){
				menu2.setSubMenuList(list2);
			}
			menuList.add(menu2);
		}
		
		model.addAttribute("sysMenu",menuList);
		
		return "sys/menu/menuList";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public SysJsonMsg menuAdd(MenuVo menuVo, HttpServletRequest request) {
		SysUser loginUser = (SysUser) request.getSession().getAttribute(CommonConstants.LOGIN_USER);
		
		SysMenu menu = new SysMenu(menuVo);
		Date nowTime = DateUtil.getCurDate();
		menu.setUpdateTime(nowTime);
		menu.setOperator(loginUser.getAccount());
		menu.setCreateTime(nowTime);
		try {
			// 新增
			this.sysMenuService.add(menu);
			// 保存操作日志
			request.setAttribute(CommonConstants.OPERATION_RECORD_FLAG, CommonConstants.SUCCESS_FLAG);
			request.setAttribute(CommonConstants.OPERATION_RECORD_DESC, "添加菜单成功");
			request.setAttribute(CommonConstants.OPERATION_RECORD_TYPE, 10);
			
			return new SysJsonMsg(true, "添加菜单成功");
		} catch (Exception e) {
			logger.error("增加菜单发生错误:" + e.getMessage(), e);
			return new SysJsonMsg(false, "添加菜单失败");
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public SysJsonMsg menuUpdate(MenuVo menuVo, HttpServletRequest request) {
		SysUser loginUser = (SysUser) request.getSession().getAttribute(CommonConstants.LOGIN_USER);
		
		SysMenu menu = new SysMenu(menuVo);
		menu.setMenuId(Integer.parseInt(menuVo.getMenuId()));
		Date nowTime = DateUtil.getCurDate();
		menu.setUpdateTime(nowTime);
		menu.setOperator(loginUser.getAccount());
		
		try {
			// 修改菜单
			int updateCount = this.sysMenuService.update(menu);
			if (updateCount > 0) {
				// 保存操作日志
				request.setAttribute(CommonConstants.OPERATION_RECORD_FLAG, CommonConstants.SUCCESS_FLAG);
				request.setAttribute(CommonConstants.OPERATION_RECORD_DESC, "修改菜单成功");
				request.setAttribute(CommonConstants.OPERATION_RECORD_TYPE, 30);
			}
			return new SysJsonMsg(true, "修改菜单成功");
		} catch (Exception e) {
			logger.error("修改菜单发生错误:" + e.getMessage(), e);
			return new SysJsonMsg(false, "修改菜单失败");
		}
	}
}
