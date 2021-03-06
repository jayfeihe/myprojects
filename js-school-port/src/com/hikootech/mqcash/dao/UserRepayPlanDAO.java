package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.UserRepaymentPlans;

public interface UserRepayPlanDAO {


	/** 
	* queryRepayPlansByInstalmentId<br/> 
	*  TODO(根据账单id和用户id查询还款计划信息) 
	* @param queryMap
	* @return 
	* @author zhaohefei
	* @2015年12月14日
	* @return List<UserRepaymentPlans>	返回类型 
	*/
	public List<UserRepaymentPlans> queryRepayPlansByInstalmentId(Map<String, Object> queryMap)throws Exception;
	
	
	/**queryRepayPlanByKey
	* 此方法描述的是：根据主键和用户id查询计划
	* @author: zhaohefei
	* @version: 2015年11月3日 下午7:46:21
	*/
	public UserRepaymentPlans queryRepayPlanByKey(Map<String,Object> queryMap)throws Exception;
	
	
	 
	
	/**currPlanUnLock
	* 此方法描述的是：根据分期id去掉代收锁
	* @author: zhaohefei
	* @version: 2015年11月18日 下午6:04:01
	*/
	public int currInstalmentUnLock(String instalmentId)throws Exception;
	
	
	/**queryMiddleOverDueNum
	* 此方法描述的是：根据分期id查询是否还有中度逾期的计划
	* @author: zhaohefei
	* @version: 2015年11月18日 下午6:09:34
	*/
	public int queryMiddleOverDueNum(Map<String,Object> map)throws Exception;
	
	
	/**queryRepayPlanByPaymentOrderId
	* 此方法描述的是：根据还款计划单号查询响应还款计划
	* @author: zhaohefei
	* @version: 2015年11月28日 下午4:09:35
	*/
	public List<UserRepaymentPlans> queryRepayPlanByPaymentOrderId(Map<String,Object> map)throws Exception;
	
	

	/**updateRepaymentPlansRealPay
	* 此方法描述的是：修改还款计划实收信息以及状态
	* @author: zhaohefei
	* @version: 2015年11月28日 下午5:52:24
	*/
	
	public int  updateRepaymentPlansRealPay(UserRepaymentPlans plan)throws Exception;
}
