package com.greenkoo.account.dao;

import java.util.List;
import java.util.Map;

import com.greenkoo.account.model.UserAccount;
import com.greenkoo.account.model.form.UserAccountVo;

public interface UserAccountDao {

	void add(UserAccount account) throws Exception;
	
	int update(UserAccount account) throws Exception;
	
	UserAccount queryById(String id);
	
	UserAccount queryByAccount(String account);

	int queryCount(Map<String, Object> paramMap);

	List<UserAccountVo> queryList(Map<String, Object> paramMap);
}
