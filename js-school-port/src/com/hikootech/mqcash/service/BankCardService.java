package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.UserInfo;

public interface BankCardService {


	/** 
	* bindBankCard<br/> 
	*  TODO(调用中金绑定银行卡接口，返回处理结果) 
	* @param bankCardDTO 前台传值对象
	* @param cacheBankCardVO 缓存发送短信的银行卡
	* @param userInfo 缓存用户
	* @return 
	* @author zhaohefei
	* @2015年12月14日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> bindBankCard(UserBankCardDTO bankCardDTO, UserBankCardDTO cacheBankCardVO, UserInfo userInfo);
	
	/** 
	* validateBindMobileNumber<br/> 
	*  TODO(调用中金发送绑定短信接口，返回结果) 
	* @param bankCardVO  前台传值对象
	* @param userInfo 缓存用户
	* @return 
	* @author zhaohefei
	* @2015年12月14日
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> validateBindMobileNumber(UserBankCardDTO bankCardVO, UserInfo userInfo);
}
