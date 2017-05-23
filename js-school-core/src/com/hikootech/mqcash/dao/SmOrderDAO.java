package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.SmOrder;

public interface SmOrderDAO {
	
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
	
	public void deleteAllSmOrder();
	
	/** 
	* @Title querySmOrderTotalRow 
	* @Description TODO(这里用一句话描述这个方法的作用) 
	* @param 参数列表 
	* @param paramMap
	* @throws Exception 
	* @return 返回类型 long	
	*/
	public long querySmOrderTotalRow(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title querySmOrderList 
	* @Description TODO(这里用一句话描述这个方法的作用) 
	* @param 参数列表 
	* @param paramMap
	* @throws Exception 
	* @return 返回类型 List<SmOrder>	
	*/
	public List<SmOrder> querySmOrderList(Map<String, Object> paramMap) throws Exception;

}
