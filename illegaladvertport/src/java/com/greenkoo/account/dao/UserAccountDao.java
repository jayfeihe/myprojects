package com.greenkoo.account.dao;

import org.apache.ibatis.annotations.Param;

import com.greenkoo.account.model.UserAccount;
import com.greenkoo.account.model.form.UserAccountVo;

public interface UserAccountDao {

	void add(UserAccount account) throws Exception;
	
	int update(UserAccount account) throws Exception;
	
	UserAccount queryById(String id);
	
	UserAccountVo queryByAccountAndPwd(@Param("account") String account, @Param("pwd") String pwd);
}
