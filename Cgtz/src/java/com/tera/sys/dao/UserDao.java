package com.tera.sys.dao;

import java.util.List;
import java.util.Map;

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
	 * 根据机构和角色获取用户
	 * @param queryMap
	 * @return
	 */
	List<User> getUserByOrgAndRole(Map<String, Object> queryMap);
}
