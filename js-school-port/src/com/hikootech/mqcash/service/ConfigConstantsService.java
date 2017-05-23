package com.hikootech.mqcash.service;

import java.util.List;

import com.hikootech.mqcash.dto.BankDTO;
import com.hikootech.mqcash.dto.ConfigBankDTO;

public interface ConfigConstantsService {
	
	
	/**获取有效的银行列表
	 * @param bankVO
	 * @return
	 */
	public List<BankDTO> getBankList(ConfigBankDTO bankVO) ; 
	
	 
	 
	/***
	 * 根据第三方银行id获取银行信息
	 * **/
	public BankDTO getBankById(String relationBankId) ;
	
}
