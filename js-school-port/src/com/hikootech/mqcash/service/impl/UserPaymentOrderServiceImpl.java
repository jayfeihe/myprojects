package com.hikootech.mqcash.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.dao.UserPaymentOrderDAO;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserPaymentRecordItem;
import com.hikootech.mqcash.service.UserPaymentOrderService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;

@Service("userPaymentOrderService")
public class UserPaymentOrderServiceImpl implements UserPaymentOrderService {
	
	private static Logger logger = LoggerFactory.getLogger(UserPaymentOrderServiceImpl.class);
	
	@Autowired
	private UserPaymentOrderDAO userPaymentOrderDAO;

	@Override
	public void savePaymentOrder(UserPaymentOrder paymentOrder,List<UserPaymentRecordItem> list,String userId) {
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("paymentOrderId", paymentOrder.getUserPaymentOrderId());
		queryMap.put("userId", userId);
		UserPaymentOrder upo=this.queryPaymentOrderById(queryMap);
		if(EntityUtils.isNotEmpty(upo)){
			logger.info("该笔还款订单已经存在，属于同一订单重复还款支付，不再做插入");
			return;
		}
		//新增还款订单
		try {
			userPaymentOrderDAO.insertUserPaymentOrder(paymentOrder);
		} catch (Exception e) {
			logger.error("增加还款订单失败",e);
			throw new RuntimeException("增加还款订单失败",e);
		}
		//新增还款订单项
		for (UserPaymentRecordItem userPaymentRecordItem : list ) {
	        userPaymentRecordItem.setUpRecordItemId(GenerateKey.getId(CommonConstants.PAYMENT_RECORD_ITEM_ID_PREFIX, ConfigUtils.getProperty("db_id")));// 还款记录项id

			try {
				userPaymentOrderDAO.insertUserPaymentRecordItem(userPaymentRecordItem);
			} catch (Exception e) {
				logger.error("增加还款订单项失败",e);
				throw new RuntimeException("增加还款订单项失败",e);
			}
		}
		
	}
	
	@Override
	public UserPaymentOrder queryPaymentOrderById(Map<String,Object> queryMap){
		
		try {
			return userPaymentOrderDAO.queryPaymentOrderById(queryMap);
		} catch (Exception e) {
			logger.error("根据对象主键和用户id获取还款订单对象失败",e);
			throw new RuntimeException("根据对象主键和用户id获取还款订单对象失败",e);
		}
		
	}

	@Override
	public int modifyPaymentOrderById(UserPaymentOrder paymentOrder) {
		
		try {
			return userPaymentOrderDAO.modifyPaymentOrderById(paymentOrder);
		} catch (Exception e) {
			logger.error("根据主键修改主动支付订单状态失败",e);
			throw new RuntimeException("根据主键修改主动支付订单状态失败",e);
		}
		
	}

	@Override
	public List<UserPaymentRecordItem> queryByPaymentOrderId(String paymentOrderId) {
		
		try {
			return userPaymentOrderDAO.queryByPaymentOrderId(paymentOrderId);
		} catch (Exception e) {
			logger.error("根据订单id查询还款子项表失败",e);
			throw new RuntimeException("根据订单id查询还款子项表失败",e);
		}
	}
	
	
}
