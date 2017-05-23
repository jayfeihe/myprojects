package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.dto.BankDTO;
import com.hikootech.mqcash.dto.ConfigBankDTO;

/**
 * @author yuwei
 * 2015年8月6日
 * 银行配置DAO层
 */
public interface ConfigBankDAO {
	
	/** 获取银行卡列表信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<BankDTO> getBankList(ConfigBankDTO bankVO) throws Exception; 
	
	/** 
	* getBankById<br/> 
	*  TODO(根据主键查询银行卡相关信息) 
	* @param relationBankId
	* @return
	* @throws Exception BankDTO
	* @author zhaohefei
	* @2015年12月15日
	* @return BankDTO	返回类型 
	*/
	public BankDTO getBankById(String relationBankId) throws Exception;

}
