package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.SmOrder;

public interface SmOrderService {
	
	/**  
	 * insertSmOrder(插入短信记录表)  
	 * @param smsMap   
	 * void 
	 * @create time： Nov 16, 2015 1:50:04 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	public void insertSmOrder(SmOrder smOrder);

	
		/**querySmOrderToSend
		* 此方法描述的是：查询今日发送短信
		* @author: zhaohefei
		* @version: 2015年11月17日 上午11:16:09
		*/
	public List<SmOrder> querySmOrderToSend(Map<String,Object> queryMap);
	
	
		/**querySmOrderToSendNum
		* 此方法描述的是：查询今日发送短信数量
		* @author: zhaohefei
		* @version: 2015年11月17日 上午11:15:47
		*/
	public int querySmOrderToSendNum(Map<String,Object> queryMap);
	
	
		/**updateSmOrder
		* 此方法描述的是：修改发送短信数据
		* @author: zhaohefei
		* @version: 2015年11月17日 上午11:16:29
		*/
		
	public int updateSmOrderById( SmOrder  smOrder);
	
	
		/**updateSmList
		* 此方法描述的是：修改发送短信数据集合
		* @author: zhaohefei
		* @version: 2015年11月17日 上午11:16:45
		*/
		
	public void updateSmList(List<SmOrder> smList);
	
	public void deleteAllSmOrder();
	
	public long querySmOrderTotalRow(Map<String,Object> paramMap) throws MQException;
	
	public List<SmOrder> querySmOrderList(Map<String,Object> paramMap) throws MQException;
	
	public Map<String, Object> send(SmOrder smOrder) throws MQException;
	
}
