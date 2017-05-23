package com.greenkoo.account.service;

import java.util.List;
import java.util.Map;

import com.greenkoo.account.model.UserAccount;
import com.greenkoo.account.model.form.UserAccountVo;

/**
 * 用户账户Service接口
 * 
 * @author QYANZE
 *
 */
public interface IUserAccountService {

	/**
	 * 添加
	 * 
	 * @param account
	 * @throws Exception
	 */
	void add(UserAccount account) throws Exception;
	
	/**
	 * 更新
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	int update(UserAccount account) throws Exception;
	
	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	UserAccount queryById(String id);
	
	/**
	 * 根据账号查询
	 * 
	 * @param account
	 * @return
	 */
	UserAccount queryByAccount(String account);
	
	/**
	 * 查询列表个数
	 * 
	 * @param paramMap
	 * @return
	 */
	int queryCount(Map<String, Object> paramMap);

	/**
	 * 查询列表
	 * 
	 * @param paramMap
	 * @return
	 */
	List<UserAccountVo> queryList(Map<String, Object> paramMap);

}
