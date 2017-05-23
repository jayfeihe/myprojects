package com.hikootech.mqcash.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.UserQhzxVerifyDAO;
import com.hikootech.mqcash.po.UserQhzxVerify;
import com.hikootech.mqcash.service.UserQhzxVerifyService;

@Service("userQhzxVerifyService")
public class UserQhzxVerifyServiceImpl implements UserQhzxVerifyService {

	@Autowired
	private UserQhzxVerifyDAO userQhzxVerifyDAO;
 

	@Override
	public void addQhzxVerify(UserQhzxVerify userQhzxVerify) {
		userQhzxVerifyDAO.addQhzxVerify(userQhzxVerify);
		
	}

	@Override
	public List<UserQhzxVerify> queryCountByInfo(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return userQhzxVerifyDAO.queryCountByInfo(queryMap);
	}


}
