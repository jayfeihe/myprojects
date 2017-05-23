/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.sys.dao.MenuDao;
import com.tera.sys.model.Menu;
import com.tera.util.ObjectUtils;

/**
 * @author Wallace chu
 *
 */
@Service
public class MenuService {

	/**
	 * RoleDao
	 */
	@Autowired
	private MenuDao menuDaoMapper;
	/**
	 * get all Available menu
	 *@return list
	 */
	public List<Menu> getAvailableMenu() {
		return menuDaoMapper.getAvailableMenu();
	}
	/**
	 * get all menu
	 *@return list
	 */
	public List<Menu> getAllMenu() {
		return menuDaoMapper.getAllMenu();
	}
	/**
	 * get some user's menus
	 * @param userId id
	 * @return list
	 */
//	public List<Menu> getMenuByUserId(int userId) {
//		return menuDaoMapper.getMenuByUserId(userId);
//	}
	
	/**
	 *  根据机构  与用户得到  用户菜单
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public List<Menu> getMenuByOrgAndUser(int userId,int orgId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("orgId", orgId);
		return menuDaoMapper.getMenuByOrgAndUser(map);
	}
	/**
	 *  去除没有可用页面的模块
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public List<Menu> getAllAvailableMenu(List<Menu> menus) {
		Menu menu=new Menu();
		Boolean bool=true;
		for (int i = 0; i < menus.size(); i++) {
			menu=menus.get(i);
			if(menu.getType()==0){
				for (int j = 0; j < menus.size(); j++) {
					if(menu.getId()==menus.get(j).getParentId()){
						bool=false;
						break;
					}
				}
				if(bool){
					menus.remove(i);
				}
				bool=true;
			}
		}
		return menus;
	}
	
	/**
	 * @param menu menu
	 */
	@Transactional
	public void addMenu(Menu menu) {
		this.menuDaoMapper.addMenu(menu);
	}
	/**
	 * @param idArray idArray
	 */
	@Transactional
	public void deleteMenuByIDs(int[] idArray) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", idArray);
		this.menuDaoMapper.deleteMenu(map);
	}
	/**
	 * @param idArray idArray
	 */
	@Transactional
	public void deleteMenu(int[] idArray) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < idArray.length; i++) {
			Menu menu=this.menuDaoMapper.getMenuById(idArray[i]);
			map.put("id", idArray[i]);
			if(menu.getState().equals("1")){
				map.put("state", "0");
			}else{
				map.put("state", "1");
			}
			this.menuDaoMapper.deleteMenu(map);
		}
	}
	
	public List<Menu> exportMenu(Map<String, Object> beanMap) {
		return this.menuDaoMapper.exportMenu(beanMap);
	}
	
	public Menu getMenuById(int id) {
		return this.menuDaoMapper.getMenuById(id);
	}
	
	public int getMenuCount(Map<String, Object> beanMap) {
		return this.menuDaoMapper.getMenuCount(beanMap);
	}
	
	public int getMenuTreeCount(Map<String, Object> beanMap) {
		return this.menuDaoMapper.getMenuCount(beanMap);
	}
	
	public List<Menu> queryMenu(Map<String, Object> beanMap) {
		return this.menuDaoMapper.queryMenu(beanMap);
	}
	
	public List<Menu> queryMenuTree(Map<String, Object> beanMap) {
		return this.menuDaoMapper.queryMenu(beanMap);
	}
	/**
	 * @param menu menu
	 */
	@Transactional
	public void updateMenu(Menu menu) {
		this.menuDaoMapper.updateMenu(menu);
	}
	/**
	 * 过去所有父菜单
	 * @return list
	 */
	public List<Menu> getAllParentMenus() {
		return this.menuDaoMapper.getAllParentMenus();

	}
	
	public List<Menu> getMenuByRoleId(int id) {
		return this.menuDaoMapper.getMenuByRoleId(id);
	}
	/**
	 * @param menuIds ids
	 * @param id roleid
	 */
	@Transactional
	public void updateMenuByRoleId(int id, int[] menuIds) {
		this.menuDaoMapper.removeMenuRealById(id);
		if(menuIds!=null && menuIds.length>0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("menuIds", menuIds);
			map.put("id", id);
			this.menuDaoMapper.addMenuReal(map);
		}
	}
	/**
	 * 通过父菜单id，获取父菜单下所有的子菜单
	 * @param parentId id
	 * @return list
	 */
	public List<Menu> getMenusByParentId(int parentId) {
		return this.menuDaoMapper.getMenusByParentId(parentId);
	}
	/**
	 * 通过父菜单id，获取父菜单下所有的子菜单
	 * @param parentId id
	 * @return list
	 */
	public List<Menu> getParentMenusById(int Id) {
		return this.menuDaoMapper.getParentMenusById(Id);
	}
	
//	public List<Menu> getNoEntranceString(int userId) {
//		return  this.menuDaoMapper.getNoMenuByUserId(userId);
//	}
	/**
	 * @param meId id
	 * @param otherId id
	 */
	@Transactional
	public void changeMenuOrderNum(int meId, int otherId) {
		Menu meMenu = this.menuDaoMapper.getMenuById(meId);
		Menu otherMenu = this.menuDaoMapper.getMenuById(otherId);

		int temp = meMenu.getOrdernum();
		meMenu.setOrdernum(otherMenu.getOrdernum());
		otherMenu.setOrdernum(temp);

		this.menuDaoMapper.updateMenu(meMenu);
		this.menuDaoMapper.updateMenu(otherMenu);
	}
	/**
	 * @param meId id
	 * @param option up down
	 * @throws Exception exception
	 */
	@Transactional
	@SuppressWarnings(value = { "unchecked" })
	public void changeMenuOrderNum(int meId, String option) throws Exception {
		Menu meMenu = this.menuDaoMapper.getMenuById(meId);
		Map<String, Object> map = ObjectUtils.describe(meMenu);
		map.put("option", option);
		List<Menu> menus = this.menuDaoMapper.getLevelNab(map);
		Menu nabMenu = null;
		if ("up".equals(option) && menus.size() >= 1) {
			nabMenu = menus.get(menus.size() - 1);
		}
		if ("down".equals(option) && menus.size() >= 1) {
			nabMenu = menus.get(0);
		}
		if (null == nabMenu) {
			return;
		}

		int temp = meMenu.getOrdernum();
		meMenu.setOrdernum(nabMenu.getOrdernum());
		nabMenu.setOrdernum(temp);

		this.menuDaoMapper.updateMenu(meMenu);
		this.menuDaoMapper.updateMenu(nabMenu);

	}

	/**
	 *  排序 菜单对象集合
	 * @param menus		等待排序对象
	 * @param newMenus	结果对象          必须New一个
	 * @param menu		当前 的排序 父对象 可以为 null
	 * @param cj		表示 起始 为0
	 */
	public void sort(List<Menu> menus, List<Menu> newMenus, Menu menu,
			int cj) {
		if (menu == null) {
			for (int j = 0; j < menus.size(); j++) {
				Menu mu = menus.get(j);
				if (mu.getId() == 1) {
					mu.setMenuLevel(cj);
					newMenus.add(mu);
					sort(menus, newMenus, mu, cj);
					break;
				}
			}
			return;
		}
		if (newMenus.size() == 0) {
			newMenus.add(menu);
		}
		cj++;
		for (Menu mu : menus) {
			if (menu.getId() == mu.getParentId()) {
				mu.setMenuLevel(cj);
				newMenus.add(mu);
				sort(menus, newMenus, mu, cj);
			}
		}
	}

	

}
