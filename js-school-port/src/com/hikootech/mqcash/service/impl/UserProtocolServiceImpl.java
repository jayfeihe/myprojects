package com.hikootech.mqcash.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.UserProtocolDAO;
import com.hikootech.mqcash.po.UserProtocol;
import com.hikootech.mqcash.service.UserProtocolService;

@Service("userProtocolService")
public class UserProtocolServiceImpl implements UserProtocolService {

	@Autowired
	private UserProtocolDAO userProtocolDAO;
	
	@Override
	public void addUserProtocol(UserProtocol userProtocol) {

		userProtocolDAO.addUserProtocol(userProtocol);
	}

	@Override
	public List<UserProtocol> queryUserProtocolByInstalmentId(Map<String, Object> queryMap) {

		return userProtocolDAO.queryUserProtocolByInstalmentId(queryMap);
	}

}
