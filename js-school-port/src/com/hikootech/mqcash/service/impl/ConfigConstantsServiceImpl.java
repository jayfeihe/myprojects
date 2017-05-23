package com.hikootech.mqcash.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.ConfigBankDAO;
import com.hikootech.mqcash.dto.BankDTO;
import com.hikootech.mqcash.dto.ConfigBankDTO;
import com.hikootech.mqcash.service.ConfigConstantsService;

/**
 * @author yuwei 2015年8月6日 配置表数据缓存（应该缓存在memcached或者redis中）:目前为了方便，直接缓存到内存中
 *         可以在Filter中初始化
 */
@Service("configConstantsService")
public class ConfigConstantsServiceImpl implements ConfigConstantsService {

	private static Logger log = LoggerFactory.getLogger(ConfigConstantsServiceImpl.class);

	@Autowired
	private ConfigBankDAO configBankDAO;


	@Override
	public List<BankDTO> getBankList(ConfigBankDTO bankVO) {
		
		try {
			return configBankDAO.getBankList(bankVO);
		} catch (Exception e) {
			 log.error("获取银行卡列表失败",e);
			 throw new RuntimeException("获取银行卡列表失败",e);
		}
	 
	}

 

	@Override
	public BankDTO getBankById(String relationBankId) {
		
		try {
			return configBankDAO.getBankById(relationBankId);
		} catch (Exception e) {
			 log.error("根据主键查询银行卡相关信息失败",e);
			 throw new RuntimeException("根据主键查询银行卡相关信息失败",e);
		}
	}

}
