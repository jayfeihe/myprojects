package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.ContractInfoDTO;

public interface ContractInfoDAO {

	
	/** 
	* modifyBankCardOfInstalment<br/> 
	*  TODO(修改账单绑定的银行卡) 
	* @param queryMap
	* @return
	* @throws Exception int
	* @author zhaohefei
	* @2015年12月15日
	* @return int	返回类型 
	*/
	public int modifyBankCardOfInstalment(Map<String, Object> queryMap) throws Exception; 

	/** 
	* queryInstalmentOrderInfoList<br/> 
	*  TODO(查询合同信息) 
	* @param queryMap
	* @return
	* @throws Exception List<ContractInfoDTO>
	* @author zhaohefei
	* @2015年12月15日
	* @return List<ContractInfoDTO>	返回类型 
	*/
	public List<ContractInfoDTO> queryInstalmentOrderInfoList(Map<String, Object> queryMap) throws Exception; 
}
