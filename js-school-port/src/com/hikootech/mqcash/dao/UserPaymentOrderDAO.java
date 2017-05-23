package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserPaymentRecordItem;

public interface UserPaymentOrderDAO {
	
	
	/** 
	* insertUserPaymentOrder<br/> 
	*  TODO(增加还款订单) 
	* @param paymentOrder
	* @throws Exception void
	* @author zhaohefei
	* @2015年12月15日
	* @return void	返回类型 
	*/
	public void insertUserPaymentOrder(UserPaymentOrder paymentOrder) throws Exception;


	/**  
	 * updateUserPaymentRecordItem(增加还款订单子项)  
	 * @param userPaymentRecordItem   
	 * void 
	 * @create time： Sep 15, 2015 1:15:42 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertUserPaymentRecordItem(UserPaymentRecordItem userPaymentRecordItem)throws Exception;
	
	
		/**queryPaymentOrderBySeq
		* 此方法描述的是：根据对象主键和用户id获取还款订单对象
		* @author: zhaohefei
		* @version: 2015年11月3日 下午2:14:25
		*/
	public UserPaymentOrder queryPaymentOrderById(Map<String,Object> queryMap)throws Exception;
	
	
	/**modifyPaymentOrder
	* 此方法描述的是：根据主键修改主动支付订单状态
	* @author: zhaohefei
	* @version: 2015年11月3日 下午4:39:56
	*/
	public int modifyPaymentOrderById(UserPaymentOrder paymentOrder)throws Exception;
	
	/**modifyPaymentOrderItem
	* 此方法描述的是：根据主键修改主动支付订单子项状态
	* @author: zhaohefei
	* @version: 2015年11月3日 下午4:39:56
	*/
	public int modifyPaymentOrderItem(UserPaymentRecordItem userPaymentRecordItem)throws Exception;
	/** 
	* queryByPaymentOrderId<br/> 
	*  TODO(根据订单id查询还款子项表) 
	* @param paymentOrderId
	* @return
	* @throws Exception List<UserPaymentRecordItem>
	* @author zhaohefei
	* @2015年12月15日
	* @return List<UserPaymentRecordItem>	返回类型 
	*/
	public List<UserPaymentRecordItem> queryByPaymentOrderId(String paymentOrderId)throws Exception;
			

}
