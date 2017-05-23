package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.InstalmentInfoDTO;
import com.hikootech.mqcash.dto.UserPaymentOrderDTO;
import com.hikootech.mqcash.dto.UserPaymentOrderDetailDTO;

public interface InstalmentInfoDAO {
	 
	/**根据限定条件查询近期符合条件的账单
	 * 
	 * 获取顶部标线用 
	 * **/
	public List<InstalmentInfoDTO> getInstalmentlistByUserId(Map<String,Object> queryMap) throws Exception;
	 
	/** 
	* getInsMsgByUserId<br/> 
	*  TODO(根据账单状态、用户id获取账单与订单表的关联信息) 
	* @param queryMap
	* @return 
	* @author zhaohefei
	* @2015年12月14日
	* @return List<InstalmentInfoDTO>	返回类型 
	*/
	public List<InstalmentInfoDTO> getInsMsgByUserId(Map<String, Object> queryMap)throws Exception;
	 
	 /**根据 账单关联的产品信息**/
	 public  InstalmentInfoDTO queryInstalmentAndProductInfoByUserId(Map<String,Object> queryMap)throws Exception;
	/**  
	 * queryUserPaymentOrderList(查询已还款订单信息)  
	 * @param queryMap
	 * @return   
	 * @create time： Sep 18, 2015 11:44:13 AM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserPaymentOrderDTO> queryUserPaymentOrderList(Map<String, Object> queryMap)throws Exception;
	
	/**  
	 * queryUserPaymentOrderDetailList(查询已还款订单详细信息)  
	 * @param queryMap
	 * @return   
	 * @create time： Sep 18, 2015 3:11:16 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public List<UserPaymentOrderDetailDTO> queryUserPaymentOrderDetailList(Map<String, Object> queryMap)throws Exception;
	/**  
	 * queryUserPaymentOrderInfo(查询单个还款信息)  
	 * @param userPaymentOrderDTO
	 * @return   
	 * UserPaymentOrderDTO 
	 * @create time： Sep 18, 2015 4:49:06 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public UserPaymentOrderDTO queryUserPaymentOrderInfo(UserPaymentOrderDTO userPaymentOrderDTO)throws Exception;
	/**  
	 * queryUserPaymentOrderTotalVO(查询已经还款的订单总数)  
	 * @param queryMap
	 * @return   
	 * int 
	 * @create time： Sep 18, 2015 6:40:57 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public int queryUserPaymentOrderTotalVO(Map<String, Object> queryMap)throws Exception;



			
			
}
