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

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.sys.constant.BusinessConstants;
import com.tera.sys.dao.UserDao;
import com.tera.sys.model.Org;
import com.tera.sys.model.RoleDataRel;
import com.tera.sys.model.User;
import com.tera.sys.model.UserExt;
import com.tera.util.PasswordUtil;
import com.tera.util.StringUtils;


/**
 * @author wallace chu
 *
 */
@Service("userService")
public class UserService extends MybatisBaseService<User>{

	/**
	 * UserDao
	 */
	@Autowired
	private UserDao userDaoMapper;
	@Autowired
	private UserExtService userExtService;
	@Autowired
	private RoleDataRelService roleDataRelService;
	@Autowired
	private OrgService orgService;

	/** (non-Javadoc)
	 * @see com.tera.sys.service.UserService#getUserByID(int)
	 * @param ids ids
	 * @return 用户
	 */
	public List<User> getUserByIDs(int[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return userDaoMapper.getUserByIDs(map);
	}

	/** (non-Javadoc)
	 * @see com.tera.sys.service.UserService#addUser(com.tera.sys.model.User)
	 * @param user user
	 */
	@Transactional
	public void addUser(User user) throws Exception {
		userDaoMapper.addUser(user);
	}

	/** (non-Javadoc)
	 * @see com.tera.sys.service.UserService#deleteUserByIDs(int[])
	 * @param ids ids
	 * @throws Exception 
	 */
	@Transactional
	public void deleteUserByIDs(int[] ids) throws Exception {
		for (int i : ids) {
			User user=userDaoMapper.getUserById(i);
			userExtService.delete(user.getLoginId());
			roleDataRelService.delete(user.getLoginId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		userDaoMapper.deleteUserByIDs(map);
		//删除
	}

	/** (non-Javadoc)
	 * @see com.tera.sys.service.UserService#getUser(java.lang.String)
	 * @param loginId loginId
	 * @return 用户
	 */
	public User getUser(String loginId) {
		return userDaoMapper.getUser(loginId);
	}

	/** (non-Javadoc)
	 * @see com.tera.sys.service.UserService#updateUser(com.tera.sys.model.User)
	 * @param user user
	 */
	@Transactional
	public void updateUser(User user) {
		userDaoMapper.updateUser(user);
	}

	/** (non-Javadoc)
	 * @param map map
	 * @see com.tera.sys.service.UserService#getUserCount()
	 * @return int 用户总数
	 */
	public int getUserCount(Map<String, Object> map) {
		return userDaoMapper.getUserCount(map);
	}

	/**(non-Javadoc)
	 * @param map map
	 * @see com.tera.sys.service.UserService#queryUser(Map<String, Object> map)
	 * @return list
	 */
	public List<User> queryUser(Map<String, Object> map) {	
		return userDaoMapper.queryUserList(map);
	}
	
	public PageList<User> queryPageList(Map<String, Object> params) {
		return this.selectPageList(UserDao.class, "queryUserList", params);
	}
	
	/**(non-Javadoc)
	 * @see com.tera.sys.service.UserService#getUserById(int)
	 * @param id id
	 * @return User user
	 */
	public User getUserById(int id) {
		return userDaoMapper.getUserById(id);
	}
	/**
	 * @param map map
	 * @return list
	 */
	public List<User> exportUser(Map<String, Object> map) {
		return userDaoMapper.queryUserList(map);
	}
	/**
	 * @param user user
	 */
	@Transactional
	public void updateUserMyself(User user) {
		this.userDaoMapper.updateUserMyself(user);
	}

	/**
	 * 根据机构和角色获取用户
	 * @param orgId
	 * @param roleNames
	 * @return
	 */
	public List<User> getUserByOrgAndRole(String orgId, String[] roleNames,String[] dataOrgIds) {
		if(orgId.length()>4){//权限只控制到分公司
			orgId=orgId.substring(0, 4);
		}
		if (dataOrgIds != null && dataOrgIds.length > 0) {
			for (String dataOrgId : dataOrgIds) {
				if(dataOrgId.length()>4){//权限只控制到分公司
					dataOrgId=dataOrgId.substring(0, 4);
				}
			}
		}
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("orgId", orgId);
		queryMap.put("roleNames", roleNames);
		queryMap.put("dataOrgIds", dataOrgIds);
		queryMap.put("dataFlag", "1"); // 有数据权限的角色
		queryMap.put("states", new String[]{"1"}); // 启用的
		return userDaoMapper.getUserByOrgAndRole(queryMap);
	}
	
	public User checkUser(String loginId, String password) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("loginId", loginId);
		map.put("password", password);
		return userDaoMapper.checkUser(map);
	}
	
	/**
	 * 添加用户及关联信息
	 * @param user
	 * @param roleIds 角色id
	 * @param orgIds 机构id
	 * @param deptIds 部门id
	 * @throws Exception
	 */
	@Transactional
	public void addUser(User user, String[] roleIds, String[] orgIds,String[] deptIds) throws Exception {

		if (StringUtils.isNotNullAndEmpty(user.getPassword())) {
			user.setPassword(PasswordUtil.md5(user.getPassword()));//加密
		}
		
		// 保存和更新用户信息
		if (0 == user.getId()) {
			this.addUser(user);
		} else {
			User serUser = this.getUserById(user.getId());
			user.setPassword(serUser.getPassword());
			this.updateUser(user);
		}
		
//		boolean check = this.check(roleIds,orgIds);
//		if (!check) {
//			return;
//		}
		
		String roleLevel = user.getRoleLevel();
		
		if (StringUtils.isNotNullAndEmpty(roleLevel)) {
			
			this.userExtService.delete(user.getLoginId());
			this.roleDataRelService.delete(user.getLoginId());
			
			String roleId = null,deptId = null,orgId = null;
			
			// 部门id只有一个
			if (null != deptIds && deptIds.length > 0) {
				for (String tmpDeptId : deptIds) {
					if (StringUtils.isNotNullAndEmpty(tmpDeptId)) {
						deptId = tmpDeptId;
						break;
					}
				} 
			}
			// 角色最少一个，可以多个
//			if (roleIds.length > 0) {
//				for (String tmpRoleId : roleIds) {
//					if (StringUtils.isNotNullAndEmpty(tmpRoleId)) {
//						roleId = tmpRoleId;
//						break;
//					}
//				} 
//			}
			
			for (String roleIdTmp : roleIds) {
				if (StringUtils.isNullOrEmpty(roleIdTmp)) {
					continue;
				}
				//判断角色是否重复
				
				Map<String, Object> queryMap = new HashMap<String,Object>();
				queryMap.put("loginId", user.getLoginId());
				queryMap.put("roleId", roleIdTmp);
				List<UserExt>listUserExts=userExtService.queryList(queryMap);
				if (listUserExts!=null&&listUserExts.size()>0) {
					continue;
				}
				
				roleId=roleIdTmp;
				
			
				// 总部
				if ("1".equals(roleLevel)) {
					if (StringUtils.isNotNullAndEmpty(deptId)) {
						UserExt ext = new UserExt(user.getLoginId(), BusinessConstants.ORG_CODE, Integer.parseInt(deptId),
								Integer.parseInt(roleId));
						this.userExtService.add(ext);
					} else {
						UserExt ext = new UserExt(user.getLoginId(), BusinessConstants.ORG_CODE, Integer.parseInt(roleId));
						this.userExtService.add(ext);
					}
					if (null != orgIds && orgIds.length > 0) {
						for (String oId : orgIds) {
							if (StringUtils.isNotNullAndEmpty(oId)) {
								// 数据权限
								RoleDataRel dataRel = new RoleDataRel(user.getLoginId(), oId, roleId);
								this.roleDataRelService.add(dataRel);
								// 查找分公司下所有的
								List<Org> subOrgs = this.orgService.getSubOrg(oId, null);
								if (subOrgs != null && subOrgs.size() > 0) {
									// 为分公司下所有营业部添加权限
									for (Org sub : subOrgs) {
										RoleDataRel subDataRel = new RoleDataRel(user.getLoginId(), sub.getOrgId(), roleId);
										this.roleDataRelService.add(subDataRel);
									}
								}
							}
						}
					}
				} else {
					
					if (null != orgIds && orgIds.length > 0) {
						for (String tmporgtId : orgIds) {
							if (StringUtils.isNotNullAndEmpty(tmporgtId)) {
								orgId = tmporgtId;
								break;
							}
						} 
					}
					if (StringUtils.isNotNullAndEmpty(deptId)) {
						UserExt ext = new UserExt(user.getLoginId(), orgId, Integer.parseInt(deptId),Integer.parseInt(roleId));
						this.userExtService.add(ext);
					} else {
						UserExt ext = new UserExt(user.getLoginId(), orgId, Integer.parseInt(roleId));
						this.userExtService.add(ext);
					}
				} 
			}
		} 
	}

	/**
	 * 检查是否选择权限设置
	 * @param roleIds
	 * @param orgIds
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean check(String[] roleIds, String[] orgIds) {
		boolean checkRole = false;
		if (roleIds.length > 0) {
			for (String rId : roleIds) {
				if (StringUtils.isNullOrEmpty(rId)) {
					continue;
				} else {
					checkRole = true;
				}
			}
		}
		
		boolean checkOrg = false;
		if (orgIds.length > 0) {
			for (String oId : orgIds) {
				if (StringUtils.isNullOrEmpty(oId)) {
					continue;
				} else {
					checkOrg = true;
				}
			}
		}
		
		if (checkOrg && checkRole) {
			return true;
		} else {
			return false;
		}
	}
}
