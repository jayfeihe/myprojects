package com.hikootech.mqcash.dao;


import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.po.UserTotalInfo;

import java.util.Map;

public interface UserDAO {

	/**  
	 * queryUserTotalInfo(查询临时表用户信息)  
	 * @param userTotalInfo
	 * @return   
	 * UserTotalInfo 
	 * @create time： Nov 20, 2015 7:07:52 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserTotalInfo queryUserTotalInfo(UserTotalInfo userTotalInfo);


	/**  
	 * updateUserTotalInfoCredit(用户征信，修改用户信息)  
	 * @param userTotalInfo
	 * @return   
	 * int 
	 * @create time： Dec 2, 2015 1:21:20 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public int updateUserTotalInfoCredit(UserTotalInfo userTotalInfo);

	/**  
	 * queryExistUserTotalInfo(查询用户信息是否存在)  
	 * @param userTotalInfo
	 * @return   
	 * int 
	 * @create time： Dec 2, 2015 3:33:10 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public int queryExistUserTotalInfo(UserTotalInfo userTotalInfo);
	
	
	/** 
	* queryUserThirdPartCreditStatus<br/> 
	*  TODO(查询业务表第三方征信结果，如果是空默认为0初始化状态) 
	* @param infoId
	* @return Integer
	* @author zhaohefei
	* @2015年12月27日
	* @return Integer	返回类型 
	*/
	public Integer queryUserThirdPartCreditStatus(String infoId);

	/** 
	* modifyUserThirdPartCreditStatus<br/> 
	*  TODO(根据主键id,修改业务表第三方征信结果) 
	* @param map
	* @return Integer
	* @author zhaohefei
	* @2015年12月27日
	* @return Integer	返回类型 
	*/
	public Integer modifyUserThirdPartCreditStatus(Map<String, Object> map);
}
