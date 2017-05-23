package com.hikootech.mqcash.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.UserRepaymentPlansDTO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserPaymentRecordItem;
import com.hikootech.mqcash.po.UserRepaymentPlans;

/** 
* @ClassName UserPaymentOrderService 
* @Description 用户支付订单业务层
* @author 余巍 yuweiqwe@126.com 
* @date 2016年1月21日 上午10:57:59 
*  
*/
public interface UserPaymentOrderService {
	
	/** 
	* <br/>1.检查逾期罚息、代收总金额
	* <br/>2.生成支付订单入库
	* @Title createRepay 
	* @Description 生成支付订单
	* @param 参数列表
	* @param repaymentPlansDTO
	* @param repaymentTime
	* @throws Exception 
	* @return void	返回类型 
	*/
	public void createPayOrder(UserRepaymentPlansDTO repaymentPlansDTO, Date repaymentTime) throws MQException;
	
	/** 
	* 1.检查校验
	* 2.请求中金
	* 3.处理返回结果 
	* @Title requestCpcnToPayOrder 
	* @Description 请求中金代扣
	* @param 参数列表
	* @param paymentOrder
	* @param isSendSMS
	* @throws MQException 
	* @return Map<String,Object>	返回类型 
	*/
	public Map<String, Object> requestCpcnToPayOrder(UserPaymentOrder paymentOrder, boolean isSendSMS) throws MQException;
	
	/** 
	* @Title queryUserPaymentOrderTotalRow 
	* @Description 根据查询条件查询支付订单对象总数
	* @param 参数列表
	* @param paramMap
	* @throws MQException 
	* @return Long	返回类型 
	*/
	public Long queryUserPaymentOrderTotalRow(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title queryUserPaymentOrderList 
	* @Description 根据查询条件查询支付订单对象
	* @param 参数列表
	* @param paramMap
	* @throws MQException 
	* @return List<UserPaymentOrder>	返回类型 
	*/
	public List<UserPaymentOrder> queryUserPaymentOrderList(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title updatePaymentOrderById 
	* @Description 更新支付订单
	* @param 参数列表 
	* @param paramMap 
	* @return
	* @throws MQException 
	* @return 返回类型 Long	
	*/
	public Long updatePaymentOrderById(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title updatePaymentOrderByIdAndPaymentStatus 
	* @Description 更新支付订单
	* @param 参数列表 
	* @param paramMap 
	* @return
	* @throws MQException 
	* @return 返回类型 Long	
	*/
	public Long updatePaymentOrderByIdAndPaymentStatus(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title updatePaymentOrderToPaying 
	* @Description 更新支付订单状态，请求状态：请求成功，支付状态：支付中，更新修改时间
	* @param 参数列表 
	* @param paymentOrderId
	* @param beforeStatus
	* @param bankTxTime
	* @param msg
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void updatePaymentOrderToPaying(String paymentOrderId, Date bankTxTime, String msg) throws MQException;
	
	/** 
	* @Title queryPaymentOrderById 
	* @Description 根据支付订单id（支付流水号）查询支付订单
	* @param 参数列表 
	* @param paymentOrderId
	* @throws MQException 
	* @return 返回类型 UserPaymentOrder	
	*/
	public UserPaymentOrder queryPaymentOrderById(String paymentOrderId) throws MQException;
	
	/** 
	* @Title updatePaymentOrderToPayFailed 
	* @Description 修改支付订单状态为支付失败
	* @param 参数列表 
	* @param paymentOrderId
	* @param beforeStatus
	* @param bankTxTime
	* @param errorMsg
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void updatePaymentOrderToPayFailed(String paymentOrderId, Integer beforeStatus, Date bankTxTime, String msg) throws MQException;
	
	/** 
	* <br/>1.判断支付订单是否已经处理完毕
	* <br/>2.根据还款计划状态处理支付订单和还款计划：0待还款(主动支付)、3还款中（被动支付--代扣）、1已还清、2重复还款
	* <br/>3.处理支付订单：修改支付订单的支付状态为支付失败
	* <br/>4.处理还款计划：修改还款计划的计划状态为待还款
	* <br/>5.发送代收失败短信
	* @Title dealPaymentOrderOfPayFailed 
	* @Description 处理支付失败的支付订单
	* @param 参数列表 
	* @param paymentOrder
	* @param isSendSMS
	* @param bankTxTime
	* @param msg
	* @throws MQException 
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, Object> dealPaymentOrderOfPayFailed(UserPaymentOrder paymentOrder, boolean isSendSMS, Date bankTxTime, String msg) throws MQException;
	
	/** 
	* @Title updatePaymentOrderToPaySuccess 
	* @Description 修改支付订单的支付状态为支付成功
	* @param 参数列表 
	* @param paymentOrderId
	* @param beforeStatus
	* @param bankTxTime
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void updatePaymentOrderToPaySuccess(UserPaymentOrder paymentOrder, Date bankTxTime, String msg, List<String> againList) throws MQException;
	
	/** 
	* <br/>1.判断支付订单是否已经处理完毕
	* <br/>2.根据还款计划状态处理支付订单和还款计划：0待还款(主动支付)、3还款中（被动支付--代扣）、1已还清、2重复还款
	* <br/>3.处理支付订单：修改支付订单的支付状态为支付成功或者重复付款、支付订单项状态为支付成功或者重复付款
	* <br/>4.处理还款计划：修改还款计划的计划状态为支付成功
	* <br/>5.处理还款计划：检查还款计划是否上锁（主动支付），如果上锁了，解锁
	* <br/>6.处理分期：检查还款计划是否是最后一期，最后一期还款成功，分期订单状态-->分期结束
	* @Title dealPaymentOrderOfPaySuccess 
	* @Description 处理支付成功的支付订单
	* @param 参数列表 
	* @param paymentOrder
	* @param bankTxTime
	* @param msg
	* @throws MQException 
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, Object> dealPaymentOrderOfPaSuccess(UserPaymentOrder paymentOrder, Date bankTxTime, String msg) throws MQException;
	
	/** 
	* @Title queryUserPaymentRecordItemListByPaymentOrderId 
	* @Description 根据支付订单号查询支付订单项列表
	* @param 参数列表 
	* @param paymentOrderId
	* @throws Exception 
	* @return 返回类型 List<UserPaymentRecordItem>	
	*/
	public List<UserPaymentRecordItem> queryUserPaymentRecordItemListByPaymentOrderId(String paymentOrderId) throws MQException;
	
	/** 
	* @Title releaseUserRepaymentPlansLock 
	* @Description 处理代收锁（主动支付）
	* @param 参数列表 
	* @param paymentOrder
	* @param instalmentId
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void releaseUserRepaymentPlansLock(UserPaymentOrder paymentOrder, String instalmentId) throws MQException;
	
	/** 
	* @Title releaseUserRepaymentPlansLock 
	* @Description 处理代收锁（主动支付）
	* @param 参数列表 
	* @param paymentOrder
	* @param plan
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void releaseUserRepaymentPlansLock(UserPaymentOrder paymentOrder, UserRepaymentPlans plan) throws MQException;
	
	/** 
	* @Title sendRepaymentFailedSMS 
	* @Description 根据还款计划ID，发送代收失败短信
	* @param 参数列表 
	* @param plansId
	* @throws MQException 
	* @return 返回类型 boolean	
	*/
	public boolean sendRepaymentFailedSMS(String plansId) throws MQException;
	
	
	/**
	 * 主动代收指定分期单当前所有逾期的分期计划
	 * @param instalmentId
	 * @param isSendSMS
	 * @return
	 * @throws MQException
	 */
	public Map<String, Object> requestCpcnToPayInstalment(String instalmentId, boolean isSendSMS) throws MQException;
	 
}
