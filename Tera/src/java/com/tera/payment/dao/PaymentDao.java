package com.tera.payment.dao;

import java.util.List;
import java.util.Map;

import com.tera.payment.model.Payment;

/**
 * 
 * <br>
 * <b>功能：</b>PaymentDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-23 13:08:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface PaymentDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);

	public T queryByKey(Object t);
	
	public int getWaitingPaymentCount(Map<String, Object> map);
	
	public List<Payment> getWaitingPayment(Map<String, Object> map);
	
	/**
	 * 查询待支付列表，代收代付共（一定要排序，期数早的先支付）
	 * @param map 
	 * 			states 支付状态 //  1,待支付 2,已发盘 3,发盘失败 4,发盘成功 5,支付成功 6,支付失败 9,未确认 10,人工确认问题 
	 * 			inOut 收付标志  //1 收, 2 付
	 * 			repaymentDate 支付日期
	 * @return
	 */
	public List<Payment> queryPayList(Map<String, Object> map);
	
	public void updateByLendAppId(Map<String, Object> map);
	
	public int queryLendPaymentCount(Map<String, Object> map);
	
	public List<Payment> queryLendPaymentList(Map<String, Object> map);

	public List<Payment> queryDivestList(Map<String, Object> paymentMap);

	/**
	 * 根据合同号删除 支付列表
	 * @param contractNo
	 */
	public void deleteByContractNo(String contractNo);

}
