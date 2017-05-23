package com.greenkoo.account.service;

import com.greenkoo.account.model.UserAccount;
import com.greenkoo.account.model.form.UserAccountVo;

/**
 * 用户账户Service接口
 * 
 * @author QYANZE
 *
 */
public interface IUserAccountService {

	void add(UserAccount account) throws Exception;
	
	int update(UserAccount account) throws Exception;
	
	UserAccount queryById(String id);
	
	UserAccountVo queryByAccountAndPwd(String account, String pwd);
}
