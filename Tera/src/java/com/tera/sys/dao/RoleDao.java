package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.sys.model.Org;
import com.tera.sys.model.Role;
import com.tera.sys.model.RoleType;

/**
 * 
 * <br>
 * <b>功能：</b>RoleDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-26 14:09:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface RoleDao{	
		
	/**
	 * 根据ids获取角色
	 * @param map map ID列表
	 * @return List<Role>
	 */
	List<Role> getRoleByIDs(Map<String, Object> map);
	/**
	 * 添加角色
	 * @param role role
	 */
	void addRole(Role role);
	
	/**
	 * @return 角色总数
	 */
	int getRoleCount();
	/**
	 * 获取查询记录数
	 * @param beanMap map
	 * @return int
	 */
	int getRoleCount(Map<String, Object> beanMap);
	/**
	 * 删除角色
	 * @param map ID列表
	 */
	void deleteRoleByIDs(Map<String, Object> map);
	

	/**
	 * 获取查询记录 map中有分页信息
	 * @param beanMap map
	 * @return list
	 */
	List<Role> queryRole(Map<String, Object> beanMap);
	/**
	 * 获取查询记录
	 * @param beanMap map
	 * @return list
	 */
	List<Role> exportRole(Map<String, Object> beanMap);
	/**
	 * get role by id
	 * @param id id
	 * @return role
	 */
	Role getRoleById(int id);
	/**
	 * 更新Role
	 * @param role role
	 */
	void updateRole(Role role);
	/**
	 * 删除user role之间的关联
	 * @param id userid
	 */
	void removeRoleRealById(int id);
	/**
	 * 添加user role之间的关联
	 * @param beanMap map
	 */
//	void addRoleReal(Map<String, Object> beanMap);
	/**
	 * 获取所有system role
	 * @return list
	 */
	List<Role> getAllRoles();
	/**
	 * @param id user id
	 * @return list
	 */
	List<Role> getAllRoleByUserId(int id);
	/**
	 * @param id user id
	 * @return list
	 */
	List<Role> getRoleByUserId(int id);

	public void delete(Role t);

	List<Role> getOrgRoleByUserId(int userId);

	/**
	 * 根据用户ID 机构代码ID获取用户在机构下的权限
	 * @param beanMap 用户登录ID:loginId  机构代码ID:orgId
	 * @return List<Role>
	 */
	List<Role> getRoleByOrgLoginId(Map<String, Object> beanMap);

}
