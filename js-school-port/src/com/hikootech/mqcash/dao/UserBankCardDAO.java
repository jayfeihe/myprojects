package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.InstalmentValidateCardLimit;
import com.hikootech.mqcash.po.RequestValidateBankCardLog;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.po.UserValidateCardLimit;

public interface UserBankCardDAO {
	
	/** 
	* queryUserBankCardByCardNumber<br/> 
	*  TODO(查询银行卡信息根据用户身份证和卡号) 
	* @param idCard
	* @param cardNumber
	* @return
	* @throws Exception UserBankCard
	* @author zhaohefei
	* @2015年12月15日
	* @return UserBankCard	返回类型 
	*/
	public UserBankCard queryUserBankCardByCardNumber(@Param("idCard")String idCard, @Param("cardNumber")String cardNumber) throws Exception;
	
	/** 
	* insertUserBankCard<br/> 
	*  TODO(增加用户银行卡) 
	* @param bankCardVO
	* @throws Exception void
	* @author zhaohefei
	* @2015年12月15日
	* @return void	返回类型 
	*/
	public void insertUserBankCard(UserBankCardDTO bankCardVO) throws Exception;

	/** 
	* queryBankInfoByUser<br/> 
	*  TODO(查询用户所有绑定的有效的银行卡) 
	* @param userIdCard
	* @return
	* @throws Exception List<UserBankCardDTO>
	* @author zhaohefei
	* @2015年12月15日
	* @return List<UserBankCardDTO>	返回类型 
	*/
	public List<UserBankCardDTO> queryBankInfoByUser(Map<String,Object> queryMap)throws Exception;
	

	/** 
	* queryValidateCardLimit<br/> 
	*  TODO(查询限制绑定次数) 
	* @param bankCardVO
	* @return
	* @throws Exception UserValidateCardLimit
	* @author zhaohefei
	* @2015年12月15日
	* @return UserValidateCardLimit	返回类型 
	*/
	public UserValidateCardLimit queryValidateCardLimit(UserBankCardDTO bankCardVO)throws Exception;

	/** 
	* insertValidateCardLimit<br/> 
	*  TODO(增加绑定限制信息) 
	* @param cardLimit
	* @throws Exception void
	* @author zhaohefei
	* @2015年12月15日
	* @return void	返回类型 
	*/
	public void insertValidateCardLimit(UserValidateCardLimit cardLimit)throws Exception;

	/** 
	* updateValidateCardLimit<br/> 
	*  TODO(修改绑定银行卡限制) 
	* @param cardLimit
	* @throws Exception void
	* @author zhaohefei
	* @2015年12月15日
	* @return void	返回类型 
	*/
	public void updateValidateCardLimit(UserValidateCardLimit cardLimit)throws Exception;

	/** 
	* insertRequestValidateBankCardLog<br/> 
	*  TODO(增加绑定银行卡日志) 
	* @param bankCardlog
	* @throws Exception void
	* @author zhaohefei
	* @2015年12月15日
	* @return void	返回类型 
	*/
	public void insertRequestValidateBankCardLog(RequestValidateBankCardLog bankCardlog)throws Exception;

	/** 
	* queryBankCardByKey<br/> 
	*  TODO(根据主键和用户id查询银行卡信息) 
	* @param paramMap
	* @return UserBankCardDTO
	* @author zhaohefei
	* @2016年1月27日
	* @return UserBankCardDTO	返回类型 
	*/
	public UserBankCard queryBankCardByKey(Map<String, Object> paramMap);

	public UserBankCard queryBankCardByCardNumber(UserBankCardDTO bankCardVo) throws Exception;

	/**  
	 * queryBindStatus(查询绑卡状态)  
	 * @param bankCardDTO
	 * @return   
	 * UserBankCard 
	 * @create time： 2016年6月16日 下午5:19:06 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserBankCard queryBindStatus(UserBankCardDTO bankCardDTO);

	/**  
	 * queryInstalmentValidateCardLimit(查询绑卡限制)  
	 * @param bankCardDTO
	 * @return   
	 * InstalmentValidateCardLimit 
	 * @create time： 2016年6月16日 下午6:39:53 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public InstalmentValidateCardLimit queryInstalmentValidateCardLimit(UserBankCardDTO bankCardDTO);

	/**  
	 * insertInstalmentValidateCardLimit(插入分期单绑卡限制表)  
	 * @param cardLimit
	 * @return   
	 * void 
	 * @create time： 2016年6月16日 下午6:43:58 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertInstalmentValidateCardLimit(InstalmentValidateCardLimit cardLimit);

	/**  
	 * updateInstalmentValidateCardLimit(修改绑卡次数)  
	 * @param cardLimit   
	 * void 
	 * @create time： 2016年6月16日 下午6:48:51 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void updateInstalmentValidateCardLimit(InstalmentValidateCardLimit cardLimit);

	/**  
	 * updateRequestValidateBankCardLog(修改绑卡记录)  
	 * @param bankCardlog   
	 * void 
	 * @create time： 2016年6月17日 下午2:52:51 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void updateRequestValidateBankCardLog(RequestValidateBankCardLog bankCardlog);

	/**  
	 * updateRequestBindBankCardLog(更新绑卡日志表)  
	 * @param bankCardlog   
	 * void 
	 * @create time： 2016年6月20日 下午4:32:37 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void updateRequestBindBankCardLog(RequestValidateBankCardLog bankCardlog);
}
