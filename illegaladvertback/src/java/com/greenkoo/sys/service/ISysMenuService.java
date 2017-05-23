package com.greenkoo.sys.service;

import java.util.List;

import com.greenkoo.sys.model.SysMenu;

/**
 * 后台系统菜单Service接口
 * 
 * @author QYANZE
 *
 */
public interface ISysMenuService {

	/**
	 * 添加菜单
	 * 
	 * @param user
	 * @throws Exception
	 */
	void add(SysMenu menu) throws Exception;

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	int update(SysMenu menu) throws Exception;
	
	/**
	 * 查菜单列表
	 * 
	 * @return
	 */
	List<SysMenu> queryList();
	
	/**
	 * 根据级别查询菜单
	 * 
	 * @param level 级别
	 * @param status 是否可用
	 * @return
	 */
	List<SysMenu> queryMenuByLevel(int level, Integer status);
}
