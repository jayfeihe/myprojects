package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.po.UserValidateCardLimit;


/**
 * @author yuwei
 * 2015年8月11日
 * 绑定用户银行服务
 */
public interface UserBankCardService {
	
	public List<UserBankCard> queryBindingStatusBankCard(UserBankCardDTO bankCardDTO) throws Exception;
	
	/** 
	* ensureBankCardStatus<br/> 
	*  TODO(请求中金查询并更新银行卡绑定状态) 
	* @param userBankCard
	* @return
	* @throws Exception String
	* @author zhaohefei
	* @2016年1月27日
	* @return Map	返回类型 
	*/
	public Map<String,String> ensureBankCardStatus(UserBankCard userBankCard) throws Exception;


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
	
	/** 
	* @Title updateValidateCardLimit 
	* @Description TODO(更新绑卡次数) 
	* @param 参数列表
	* @param cardLimit
	* @throws Exception 设定文件 
	* @return void	返回类型 
	* @throws 
	*/
	public void updateValidateCardLimit(UserValidateCardLimit cardLimit)  throws Exception;
}
