package com.greenkoo.sys.service;

import java.util.List;
import java.util.Map;

import com.greenkoo.sys.model.SysUser;

/**
 * 后台登录用戶Service接口
 * 
 * @author QYANZE
 *
 */
public interface ISysUserService {

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @throws Exception
	 */
	void add(SysUser user) throws Exception;

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int updatePwd(SysUser user) throws Exception;

	/**
	 * 根据主键查询
	 * 
	 * @param userId
	 * @return
	 */
	SysUser queryByUserId(String userId);
	
	/**
	 * 查询列表数量
	 * 
	 * @param paramMap
	 * @return
	 */
	int queryCount(Map<String, Object> paramMap);

	/**
	 * 查询用户列表
	 * @param paramMap 
	 * 
	 * @return
	 */
	List<SysUser> queryList(Map<String, Object> paramMap);

	/**
	 * 根据用户账号和密码查询用户
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	SysUser queryByAccountAndPwd(String account, String pwd);

}
