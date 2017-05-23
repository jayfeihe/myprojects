package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

import com.tera.rule.model.common.AssignUserInfoBean;
import com.tera.sys.model.User;

/**
 * 
 * <br>
 * <b>功能：</b>UserDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-23 10:47:00<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface UserDao {	
		
	/**
	 * 新增用户
	 * @param user user
	 */
	void addUser(User user);	
	
	/**
	 * 登录验证
	 * @param map 用户名,password 密码
	 * @return 是否合法用户
	 */
	User checkUser(Map<String, Object> map);
	/**
	 * 获取用户
	 * @param map ID列表
	 * @return 用户
	 */
	public List<User> queryUserList(Map<String, Object> map);
	
	/**
	 * 通过用户ID获取用户
	 * @param loginId loginId
	 * @return User
	 */
	User getUser(String loginId);
	/**
	 * 系统ID
	 * @param id id
	 * @return user
	 */
	User getUserById(int id);
	/**
	 * 获取用户
	 * @param map ID列表
	 * @return 用户
	 */
	List<User> getUserByIDs(Map<String, Object> map);
	/**
	 * 更新User
	 * @param user user
	 */
	void updateUser(User user);
	/**
	 * 删除用户
	 * @param map ID列表
	 */
	void deleteUserByIDs(Map<String, Object> map);
	/**
	 * 删除用户
	 * @param User 
	 */
	public void delete(User t);	

	/**
	 * @param map map
	 * @return 用户总数
	 */
	int getUserCount(Map<String, Object> map);

	/**
	 * 用户修改自己信息
	 * @param user user
	 */
	void updateUserMyself(User user);

	/**
	 * 得到用户列表
	 * @param map
	 * 			KEY
	 * 				loginId
	 * 				name
	 * 				states
	 * 				orgId
	 * 				roleNames
	 * 				rowS
	 * 				rowE
	 * @return
	 */
	public List<User> queryUserByOrgAndRoleAndDepart(Map<String, Object> map);
	
	int queryUserByOrgAndRoleAndDepartCount(Map<String, Object> map);
	
	/**
	 * 规则分单使用 用于查选 当前节点 处理人处理单子信息
	 * @param map
	 * 			KEY
	 * 				states		用户是否在岗 状态   0：离职  1：在岗    2：休息         类型：String[]
	 * 				bpmState	流程节点    类型：String
	 * 				roleNames   用户角色数组    类型：String[]
	 * @return
	 */
	public List<AssignUserInfoBean> queryAssignUserInfo(Map<String, Object> map);
	/**
	 *通过角色id筛选出所需角色类型的用户 
	 *@author wangyongliang 20150610
	 */
//	public List<User> queryUserByGroup(Map<String, Object> map);
}
