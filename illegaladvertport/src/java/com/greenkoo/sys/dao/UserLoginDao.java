package com.greenkoo.sys.dao;

import java.util.List;

import com.greenkoo.sys.model.UserLogin;

public interface UserLoginDao {

	void add(UserLogin userLogin) throws Exception;
	
	List<UserLogin> queryList();
}
