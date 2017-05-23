package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserPaymentRecordItem;

public interface UserPaymentOrderDAO {
	
	public void insertUserPaymentOrder(UserPaymentOrder paymentOrder) throws Exception;
	
	public void insertUserPaymentRecordItem(UserPaymentRecordItem userPaymentRecordItem)  throws Exception;
	
	public Long queryUserPaymentOrderTotalRow(Map<String, Object> paramMap) throws Exception;
	
	public List<UserPaymentOrder> queryUserPaymentOrderList(Map<String, Object> paramMap) throws Exception;
	
	public Long updatePaymentOrderById(Map<String, Object> paramMap) throws Exception;
	
	public Long updatePaymentOrderByIdAndPaymentStatus(Map<String, Object> paramMap) throws Exception;
	
	public UserPaymentOrder queryPaymentOrderById(String paymentOrderId) throws Exception;
	
	public Long updatePaymentOrderItemById(UserPaymentRecordItem recordItem) throws Exception;
	
	public List<UserPaymentRecordItem> queryUserPaymentRecordItemListByPaymentOrderId(String paymentOrderId) throws Exception;

}
