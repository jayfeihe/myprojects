package com.hikootech.mqcash.service;



/**  
 *   
 * HandleOverDueNoRepaymentService  
 *   
 * @function:(逾期未还款，发送短息Service)  
 * @create time:Nov 16, 2015 11:34:27 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public interface HandleOverDueNoRepaymentService {

	/**  
	 * sendOverDueNoRepaymentSMS(查询逾期状态并且计划还款时间为2天前0点到今日0点，并发送短信)  
	 * @param paramMap   
	 * void 
	 * @create time： Nov 16, 2015 1:39:59 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void sendOverDueNoRepaymentSMS() throws Exception;
}
