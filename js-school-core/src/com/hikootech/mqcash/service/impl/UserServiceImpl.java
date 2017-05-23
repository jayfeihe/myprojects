package com.hikootech.mqcash.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.UserDAO;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserTotalInfo;
import com.hikootech.mqcash.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserTotalInfo queryUserTotalInfo(UserTotalInfo userTotalInfo) {
		return userDAO.queryUserTotalInfo(userTotalInfo);
	}
}
