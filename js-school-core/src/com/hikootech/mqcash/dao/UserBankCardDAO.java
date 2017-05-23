package com.hikootech.mqcash.dao;

import java.util.List;

import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserValidateCardLimit;

public interface UserBankCardDAO {
	
	public List<UserBankCard> queryUserBankCard(UserBankCardDTO bankCardDTO) throws Exception;
	
	public int updateUserBankCardBindStatus(UserBankCardDTO bankCardDTO) throws Exception;
	
	public void updateValidateCardLimit(UserValidateCardLimit cardLimit) throws Exception;

	/** 
	* queryBankCardByKey<br/> 
	*  TODO(根据主键查询银行卡信息) 
	* @param userInfo
	* @return UserBankCard
	* @author zhaohefei
	* @2016年1月27日
	* @return UserBankCard	返回类型 
	*/
	public UserBankCard queryBankCardByKey(String  bankCardId);

}
