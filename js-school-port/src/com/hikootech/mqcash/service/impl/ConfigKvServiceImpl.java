package com.hikootech.mqcash.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.context.CommonKVContext;
import com.hikootech.mqcash.dao.ConfigKvDAO;
import com.hikootech.mqcash.po.ConfigKv;
import com.hikootech.mqcash.service.ConfigKvService;
@Service("configKvService")
public class ConfigKvServiceImpl implements ConfigKvService {

	@Autowired
	private  ConfigKvDAO configKvDAO;
	
	@Override
	public List<ConfigKv> queryAll() {
		return configKvDAO.queryAll();
	}

	@Override
	public String get(String key) {
		return CommonKVContext.get(key);
	}

}
