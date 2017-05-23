package com.hikootech.mqcash.dao;


import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.UserForSendSmDTO;

/**  
 *   
 * HandleOverDueNoRepaymentDAO  
 *   
 * @function:(逾期未还款，发送短息DAO)  
 * @create time:Nov 16, 2015 11:41:10 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public interface HandleOverDueNoRepaymentDAO {

	/**  
	 * queryOverDueNoRepaymentList(查询逾期状态并且计划还款时间为2天前0点到今日0点)  
	 * @param userRepaymentPlans
	 * @return   
	 * List<UserRepaymentPlans> 
	 * @create time： Nov 16, 2015 11:40:54 AM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserForSendSmDTO> queryOverDueNoRepaymentList(Map<String, Object> paramMap);

}
