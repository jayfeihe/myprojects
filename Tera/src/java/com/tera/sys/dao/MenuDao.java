package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.Menu;

/**
 * 
 * <br>
 * <b>功能：</b>MenuDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-23 14:14:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface MenuDao {	
	
	/**
	 * 获取计数
	 * @param beanMap map
	 * @return 计数
	 */
	int getMenuCount(Map<String, Object> beanMap);
	
	/**
	 * 获取所有菜单
	 * @return 所有菜单
	 */
	List<Menu> getAllMenu();
	
	/**
	 * 获取所有可用的菜单
	 * @return 所有可用的菜单
	 */
	List<Menu> getAvailableMenu();
	
	/**
	 * 获取与用户相关的
	 * @param userId id
	 * @return menu
	 */
//	List<Menu> getMenuByUserId(int userId);
	
	List<Menu> getMenuByOrgAndUser(Map<String, Object> beanMap);
	
//	/**
//	 * 获取与用户不相关的
//	 * @param userId id
//	 * @return menu
//	 */
//	List<Menu> getNoMenuByUserId(int userId);
	/**
	 * @param menu menu
	 */
	void addMenu(Menu menu);
	/**
	 * @param map map
	 */
	void deleteMenuByIDs(Map<String, Object> map);
	
	/**
	 * @param map map
	 */
	void deleteMenu(Map<String, Object> map);
	
	/**
	 * @param beanMap map
	 * @return list
	 */
	List<Menu> exportMenu(Map<String, Object> beanMap);
	/**
	 * @param id id
	 * @return menu
	 */
	Menu getMenuById(int id);
	/**
	 * @param beanMap map
	 * @return list
	 */
	List<Menu> queryMenu(Map<String, Object> beanMap);
//	/**
//	 * @param beanMap map
//	 * @return list
//	 */
//	List<Menu> queryMenuTree(Map<String, Object> beanMap);
	
	/**
	 * @param menu menu
	 */
	void updateMenu(Menu menu);
	/**
	 * @return list
	 */
	List<Menu> getAllParentMenus();
	/**
	 * @param id id
	 * @return list
	 */
	List<Menu> getMenuByRoleId(int id);
	/**
	 * @param id roleId
	 */
	void removeMenuRealById(int id);
	/**
	 * @param beanMap map
	 */
	void addMenuReal(Map<String, Object> beanMap);
	/**
	 * @param parentId parent id
	 * @return list
	 */
	List<Menu> getMenusByParentId(int parentId);
	/**
	 * @param parentId parent id
	 * @return list
	 */
	List<Menu> getParentMenusById(int id);
	
	/**
	 * @param beanMap map
	 * @return list
	 */
	List<Menu> getLevelNab(Map<String, Object> beanMap);
	
	

}
