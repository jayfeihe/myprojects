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

import com.tera.sys.dao.RoleDao;
import com.tera.sys.dao.UserDao;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
/**
 * @author Wallace chu
 *
 */
@Service
public class RoleService {

	/**
	 * RoleDao
	 */
	@Autowired
	private RoleDao roleDaoMapper;
	
	@Autowired(required=false)
    private UserDao userdao;

	/* (non-Javadoc)
	 * @see com.tera.sys.service.RoleService#getRoleTypes()
	 */
	
//	public List<RoleType> getRoleTypes() {
//		return roleDaoMapper.getRoleTypes();
//	}

	/** (non-Javadoc)
	 * @see com.tera.sys.service.RoleService#getRoleByIDs(int[])
	 * @param ids ids ID列表
	 * @return List<Role>
	 */
	public List<Role> getRoleByIDs(int[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return roleDaoMapper.getRoleByIDs(map);
	}

	/** (non-Javadoc)
	 * @see com.tera.sys.service.RoleService#addRole(com.tera.sys.model.Role)
	 * @param role role
	 */
	@Transactional
	public void addRole(Role role) {
		roleDaoMapper.addRole(role);
	}

	/** (non-Javadoc)
	 * @see com.tera.sys.service.RoleService#getRoleCount()
	 * @return 角色总数
	 */
	public int getRoleCount() {
		return roleDaoMapper.getRoleCount();
	}

	/** (non-Javadoc)
	 * @see com.tera.sys.service.RoleService#deleteRoleByIDs(int[])
	 * @param ids ID列表
	 */
	@Transactional
	public void deleteRoleByIDs(int[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		roleDaoMapper.deleteRoleByIDs(map);
	}

	/**(non-Javadoc)
	 * @see com.tera.sys.service.RoleService#
	 * @param beanMap map
	 * @return list
	 */
	public List<Role> exportRole(Map<String, Object> beanMap) {
		return this.roleDaoMapper.exportRole(beanMap);
	}

	/**(non-Javadoc)
	 * @see com.tera.sys.service.RoleService#
	 * @param id id
	 * @return Role role
	 */
	public Role getRoleById(int id) {
		return this.roleDaoMapper.getRoleById(id);
	}

	/**(non-Javadoc)
	 * @see com.tera.sys.service.RoleService#
	 * @param beanMap map
	 * @return int
	 */
	public int getRoleCount(Map<String, Object> beanMap) {
		return this.roleDaoMapper.getRoleCount(beanMap);
	}

	/**(non-Javadoc)
	 * @see com.tera.sys.service.RoleService#
	 * @param beanMap map
	 * @return list
	 */
	public List<Role> queryRole(Map<String, Object> beanMap) {
		return this.roleDaoMapper.queryRole(beanMap);
	}

	/**(non-Javadoc)
	 * @see com.tera.sys.service.RoleService#
	 * @param role role
	 */
	@Transactional
	public void updateRole(Role role) {
		this.roleDaoMapper.updateRole(role);
	}
	/**
	 * 更新user role之间的关联
	 * @param id user id
	 * @param roleIds role
	 */
//	public void updateRoleByUserId(int id, int[] roleIds) {
//		this.roleDaoMapper.removeRoleRealById(id);
//		if (null == roleIds || roleIds.length == 0) {
//			return;
//		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("roleIds", roleIds);
//		map.put("id", id);
//
//		this.roleDaoMapper.addRoleReal(map);
//	}
	/**
	 * @return list
	 */
	public List<Role> getAllRoles() {

		return this.roleDaoMapper.getAllRoles();
	}
	/**
	 * @param id user id
	 * @param type role type
	 * @return list
	 */
	public List<Role> getRoleByUserId(int id) {
		return this.roleDaoMapper.getAllRoleByUserId(id);
	}

	public String getOrgRoleByUserId(int userId){
		StringBuffer jg=new StringBuffer();
		List<Role> rlist= this.roleDaoMapper.getOrgRoleByUserId(userId);
		for (Role role : rlist) {
			jg.append(role.getName());
			jg.append("_");
			jg.append(role.getId());
			jg.append(",");
		}
		return jg.toString();
	}

	/**
	 * 根据用户ID 机构代码ID获取用户在机构下的权限
	 * @param beanMap 用户登录ID:loginId  机构代码ID:orgId
	 * @return List<Role>
	 */
	public List<Role> getRoleByOrgLoginId(Map<String, Object> beanMap) {
		String loginId=(String) beanMap.get("loginId");
		//如果所查询用户 为 超级管理员  将能够所有角色
		if(loginId!=null){
			User us=userdao.getUser(loginId);
			if(us!=null&&us.getIsAdmin()==1){
				beanMap.remove("loginId");
				beanMap.remove("orgId");
			}
		}
		return this.roleDaoMapper.getRoleByOrgLoginId(beanMap);
	}

}
