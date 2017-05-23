package com.hikootech.mqcash.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.dao.ConfigCreditKvDAO;
import com.hikootech.mqcash.po.ConfigCreditKv;
import com.hikootech.mqcash.service.ConfigCreditKvService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.MemCachedUtils;
@Service("configCreditKvService")
public class ConfigCreditKvServiceImpl implements ConfigCreditKvService {
	private static Logger logger = LoggerFactory.getLogger(ConfigCreditKvServiceImpl.class);
	@Autowired
	private  ConfigCreditKvDAO configCreditKvDAO;
	
	@Override
	public List<ConfigCreditKv> queryAll() {
		return configCreditKvDAO.queryAll();
	}

	@Override
	public String get(String key) {
		return configCreditKvDAO.get(key);
	}

	@Override
	public String refresh() {
		
		//读取配置缓存时间
		String configMemcachedTime = ConfigUtils.getProperty("memcached_expiration");
		
		//查询数据库中所有数据
		List<ConfigCreditKv> list = configCreditKvDAO.queryAll();
		Map<String,String> map = new HashMap<String,String>();
		for(ConfigCreditKv kv : list){
			map.put(kv.getKvKey(), kv.getKvValue());
		}
		MemCachedUtils.set(CommonConstants.CONFIG_KV_CREDIT_KEY,Integer.parseInt(configMemcachedTime),map);
		logger.info("刷新征信配置表缓存完成！");
		return null;
	}

	@Override
	public Map<String, String> getAllCreditKv() throws Exception {
		// TODO Auto-generated method stub
		logger.info("获取所有征信配置项");
		Map<String,String> creditMap = MemCachedUtils.get(CommonConstants.CONFIG_KV_CREDIT_KEY);
		if(creditMap != null){
			return creditMap;
		}
		logger.info("从memcached中没有读取到征信配置项，开始从数据库中读取征信配置项");
		
		creditMap = new HashMap<String, String>();
		//查询数据库中所有数据
		List<ConfigCreditKv> list = configCreditKvDAO.queryAll();
		
		for(ConfigCreditKv kv : list){
			creditMap.put(kv.getKvKey(), kv.getKvValue());
		}
		
		return creditMap;
	}

}
