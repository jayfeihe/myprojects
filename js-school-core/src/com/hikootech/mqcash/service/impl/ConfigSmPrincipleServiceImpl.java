package com.hikootech.mqcash.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.ConfigSmPrincipleDAO;
import com.hikootech.mqcash.po.ConfigSmPrinciple;
import com.hikootech.mqcash.service.ConfigSmPrincipleService;

@Service("configSmPrincipleService")
public class ConfigSmPrincipleServiceImpl implements ConfigSmPrincipleService {

	@Autowired
	private  ConfigSmPrincipleDAO configSmPrincipleDAO;
	@Override
	public List<ConfigSmPrinciple> querySmConfigByTargetType(int targetType) {
		// TODO Auto-generated method stub
		return configSmPrincipleDAO.querySmConfigByTargetType(targetType);
	}

}
