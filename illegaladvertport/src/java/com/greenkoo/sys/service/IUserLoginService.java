package com.greenkoo.sys.service;

import java.util.List;

import com.greenkoo.sys.model.UserLogin;

/**
 * 用戶登錄日志
 * 
 * @author QYANZE
 *
 */
public interface IUserLoginService {

	void add(UserLogin userLogin);

	List<UserLogin> queryList();
}
