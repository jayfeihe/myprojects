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
import com.tera.rule.model.common.AssignUserInfoBean;
import com.tera.sys.dao.UserDao;
import com.tera.sys.model.User;


/**
 * @author wallace chu
 *
 */
@Service("userService")
public class UserService extends MybatisBaseService<User> {

	/**
	 * UserDao
	 */
	@Autowired
	private UserDao userDaoMapper;

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
	public void addUser(User user) {
		userDaoMapper.addUser(user);
	}

	/** (non-Javadoc)
	 * @see com.tera.sys.service.UserService#deleteUserByIDs(int[])
	 * @param ids ids
	 */
	@Transactional
	public void deleteUserByIDs(int[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		userDaoMapper.deleteUserByIDs(map);
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
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
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

	public User checkUser(String loginId, String password) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("loginId", loginId);
		map.put("password", password);
		return userDaoMapper.checkUser(map);
	}
	
	/**
	 * 根据 机构编码 与岗位名称 得到 用户列表
	 * @param orgId		岗位编码
	 * @param roleName	角色名称
	 * @return
	 */
	public List<User> getUserByOrgAndRoleAndDepart(String orgId,String[] roleName,String[] departNames) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("roleNames", roleName);
		map.put("departNames", departNames);
		map.put("states", new String[]{"1","2"});
		return this.queryUserByOrgAndRoleAndDepart(map);
	}
	/**
	 * 得到用户列表
	 * @param map
	 * 			KEY
	 * 				loginId 登陆id
	 * 				name 姓名
	 * 				states 状态[0,1] 0离职，1正常，2挂起
	 * 				orgId 机构id
	 * 				roleNames 角色名称["",""]
	 * 				rowS
	 * 				rowE
	 * @return
	 */
	public List<User> queryUserByOrgAndRoleAndDepart(Map<String, Object> map){
		return userDaoMapper.queryUserByOrgAndRoleAndDepart(map);
	}
	public int queryUserByOrgAndRoleAndDepartCount(Map<String, Object> map){
		return userDaoMapper.queryUserByOrgAndRoleAndDepartCount(map);
	}
	
	/**
	 * 规则分单使用 用于查选 当前节点 处理人处理单子信息
	 * 	bpmState	流程节点
	 * 	states		用户是否在岗 状态   0：离职  1：在岗    2：休息 
	 * 	roleNames   用户角色数组
	 */
	public List<AssignUserInfoBean> queryAssignUserInfo(String bpmState,String[] states,String[] roleNames ){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("bpmState", bpmState);
		map.put("states", states);
		map.put("roleNames", roleNames);
		return userDaoMapper.queryAssignUserInfo(map);
	}
	/**
	 *根据机构和角色查询用户 
	 *@author wangyongliang 20150610
	 */
	/*public  List<User> queryUserByGroup(Map<String, Object> map){
			return userDaoMapper.queryUserByGroup(map);
	}*/
}
