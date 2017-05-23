package com.greenkoo.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.sys.dao.UserLoginDao;
import com.greenkoo.sys.model.UserLogin;
import com.greenkoo.sys.service.IUserLoginService;

@Service("userLoginService")
public class UserLoginServiceImpl implements IUserLoginService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserLoginDao dao;
	
	@Transactional
	@Override
	public void add(UserLogin userLogin) {
		try {
			this.dao.add(userLogin);
		} catch (Exception e) {
			logger.error("保存登录日志异常：" + e.getMessage(), e);
		}
	}

	@Override
	public List<UserLogin> queryList() {
		return this.dao.queryList();
	}
}
