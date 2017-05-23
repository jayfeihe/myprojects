package com.hikootech.mqcash.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.UserOrderStatusLogDAO;
import com.hikootech.mqcash.po.UserOrderStatusLog;
import com.hikootech.mqcash.service.UserOrderStatusLogService;
@Service("userOrderStatusLogService")
public class UserOrderStatusLogServiceImpl implements UserOrderStatusLogService {
	@Autowired
	private UserOrderStatusLogDAO userOrderStatusLogDAO;
	@Override
	public int insertUserOrderStatusLog(UserOrderStatusLog orderStatusLog) throws Exception {
			
		return userOrderStatusLogDAO.insertUserOrderStatusLog(orderStatusLog);
	}

}
