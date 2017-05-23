package com.greenkoo.sys.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.sys.model.SysMenu;
import com.greenkoo.sys.model.form.MenuVo;
import com.greenkoo.sys.service.ISysMenuService;

/**
 * @author QYANZE
 *
 */
@Controller
public class CommonController {

	@Autowired
	private ISysMenuService sysMenuService;
	
	@RequestMapping({"/","/main"})
	public String index(Model model) {
		//登录成功，查询所有菜单
		List<SysMenu> l2Menu = sysMenuService.queryMenuByLevel(2,CommonConstants.STATUS_ON);
		List<SysMenu> l3Menu = sysMenuService.queryMenuByLevel(3,CommonConstants.STATUS_ON);
		
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
		
		return "main";
	}
}
