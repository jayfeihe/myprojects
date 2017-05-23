package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.InstalmentValidateCardLimit;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.po.UserValidateCardLimit;

/**
 * @author yuwei
 * 2015年8月11日
 * 绑定用户银行服务
 */
public interface UserBankCardService {
	
	/**
	 * 发送请求（预留手机号短信验证码）到中金，绑定银行卡,
	 * 成功，记录绑定关系校验通过，
	 * 新增绑定银行卡记录信息校验不通过，返回不通过原因
	 * @param bankCard
	 */
	public Map<String, Object> bindBankCard(UserBankCardDTO bankCardVO) ;
	
	/**请求第三绑定用户银行卡（发送短信校验）
	 * @param bankCardVO
	 * @return
	 */
	public Map<String, Object> requestBindBankCard(UserBankCardDTO bankCardVO) ;
	
	
	/** 
	* queryBankInfoByUser<br/> 
	*  TODO(查询当前用户已经绑定的银行卡信息) 
	* @param queryMap
	* @return List<UserBankCardDTO>
	* @author zhaohefei
	* @2016年1月29日
	* @return List<UserBankCardDTO>	返回类型 
	*/
	public List<UserBankCardDTO> queryBankInfoByUser(Map<String,Object> queryMap);
	
	 
	/** 
	* queryBankCardByCardNumber<br/> 
	*  TODO(根据用户id和银行卡号，查询绑定状态为已绑定或者处理中的银行卡信息) 
	* @param bankCardVo
	* @return UserBankCard
	* @author zhaohefei
	* @2016年1月28日
	* @return UserBankCard	返回类型 
	*/
	public UserBankCard queryBankCardByCardNumber(UserBankCardDTO bankCardVo);
	
	/**  
	 * queryValidateCardLimit(查询用户是否点过发送验证码)  
	 * @param bankCardVO
	 * @return   
	 * UserValidateCardLimit 
	 * @create time： Nov 3, 2015 3:44:33 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserValidateCardLimit queryValidateCardLimit(UserBankCardDTO bankCardVO);

	/**  
	 * insertValidateCardLimit(插入首次点击发送短信验证码次数表)  
	 * @param bankCardVO   
	 * void 
	 * @create time： Nov 3, 2015 7:31:34 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertValidateCardLimit(UserValidateCardLimit cardLimit);

	/**  
	 * updateValidateCardLimit(修改点击验证码次数)  
	 * @param bankCardVO   
	 * void 
	 * @create time： Nov 3, 2015 7:43:12 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void updateValidateCardLimit(UserValidateCardLimit cardLimit);
	
	/** 
	* queryBankCardByKey<br/> 
	*  TODO(根据主键和用户id查询银行卡信息) 
	* @param paramMap
	* @return UserBankCard
	* @author zhaohefei
	* @2016年1月27日
	* @return UserBankCard	返回类型 
	*/
	public UserBankCard queryBankCardByKey(Map<String, Object> paramMap);

	/**  
	 * queryBindStatus(查询绑卡状态)  
	 * @param bankCardDTO
	 * @return   
	 * UserBankCard 
	 * @create time： 2016年6月16日 下午5:18:11 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserBankCard queryBindStatus(UserBankCardDTO bankCardDTO);

	/**  
	 * queryInstalmentValidateCardLimit(查询绑卡限制)  
	 * @param bankCardVO
	 * @return   
	 * InstalmentValidateCardLimit 
	 * @create time： 2016年6月16日 下午6:40:19 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public InstalmentValidateCardLimit queryInstalmentValidateCardLimit(UserBankCardDTO bankCardVO);

	/**  
	 * insertInstalmentValidateCardLimit(插入分期单绑卡限制表)  
	 * @param cardLimit   
	 * void 
	 * @create time： 2016年6月16日 下午6:43:13 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertInstalmentValidateCardLimit(InstalmentValidateCardLimit cardLimit);

	/**  
	 * updateInstalmentValidateCardLimit(修改绑卡限制次数)  
	 * @param cardLimit   
	 * void 
	 * @create time： 2016年6月16日 下午6:48:19 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void updateInstalmentValidateCardLimit(InstalmentValidateCardLimit cardLimit);

	

}
