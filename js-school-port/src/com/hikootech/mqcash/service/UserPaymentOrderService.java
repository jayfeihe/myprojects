package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserPaymentRecordItem;

public interface UserPaymentOrderService {
	
	/** 
	* savePaymentOrder<br/> 
	*  TODO(保存还款订单) 
	* @param paymentOrder
	* @param list
	* @author zhaohefei
	* @2015年12月13日
	* @return void	返回类型 
	*/
	public void savePaymentOrder(UserPaymentOrder paymentOrder,List<UserPaymentRecordItem> list,String userId) ;

	
	/**queryPaymentOrderBySeq
	* 此方法描述的是：根据主键 和用户id查询UserPaymentOrder还款订单对象
	* @author: zhaohefei
	* @version: 2015年11月3日 下午2:16:33
	*/
	public UserPaymentOrder queryPaymentOrderById(Map<String,Object> queryMap);


	/**modifyPaymentOrderBySeq
	* 此方法描述的是：根据对象的主键修改主动支付订单状态
	* @author: zhaohefei
	* @version: 2015年11月3日 下午4:49:04
	*/
	public int modifyPaymentOrderById(UserPaymentOrder paymentOrder);
	
	
	/** 
	* queryByPaymentOrderId<br/> 
	*  TODO(根据还款订单查询还款子项) 
	* @param paymentOrderId
	* @return 
	* @author zhaohefei
	* @2015年12月13日
	* @return List<UserPaymentRecordItem>	返回类型 
	*/
	public List<UserPaymentRecordItem> queryByPaymentOrderId(String paymentOrderId);
}
