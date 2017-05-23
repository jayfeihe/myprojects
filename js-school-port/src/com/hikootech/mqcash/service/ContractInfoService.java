package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.BankDTO;
import com.hikootech.mqcash.dto.ConfigBankDTO;
import com.hikootech.mqcash.dto.ContractInfoDTO;

public interface ContractInfoService {
	
	/** 
	* modifyBankCardOfInstalment<br/> 
	*  TODO(修改账单对应的银行卡) 
	* @param queryMap
	* @return 
	* @author zhaohefei
	* @2015年12月13日
	* @return int	返回类型 
	*/
	public int modifyBankCardOfInstalment(Map<String, Object> queryMap);
	
	/** 
	* queryInstalmentOrderInfoList<br/> 
	*  TODO(查询合同信息) 
	* @param queryMap
	* @return 
	* @author zhaohefei
	* @2015年12月13日
	* @return List<ContractInfoDTO>	返回类型 
	*/
	public List<ContractInfoDTO> queryInstalmentOrderInfoList(Map<String, Object> queryMap);
}
