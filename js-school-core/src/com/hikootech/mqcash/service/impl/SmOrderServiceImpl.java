package com.hikootech.mqcash.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.SmOrderDAO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.exception.MQExceptionConstants;
import com.hikootech.mqcash.po.SmLog;
import com.hikootech.mqcash.po.SmOrder;
import com.hikootech.mqcash.service.LogService;
import com.hikootech.mqcash.service.SmOrderService;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.SmUtils;

@Service("smOrderService")
public class SmOrderServiceImpl implements SmOrderService {
	
	private static Logger log = LoggerFactory.getLogger(SmOrderServiceImpl.class);

	@Autowired
	private SmOrderDAO smOrderDAO;
	@Autowired
	private LogService logService;
	
	@Override
	public void insertSmOrder(SmOrder smOrder) {
		// TODO Auto-generated method stub
		smOrderDAO.insertSmOrder(smOrder);
	}
	@Override
	public List<SmOrder> querySmOrderToSend(Map<String,Object> queryMap) {
		// TODO Auto-generated method stub
		return smOrderDAO.querySmOrderToSend(queryMap);
	}
	@Override
	public int querySmOrderToSendNum(Map<String,Object> queryMap) {
		// TODO Auto-generated method stub
		return smOrderDAO.querySmOrderToSendNum(queryMap);
	}
	@Override
	public void updateSmList(List<SmOrder> smList) {
		// TODO Auto-generated method stub
		for (SmOrder smOrder : smList) {
			int ret =this.updateSmOrderById(smOrder);
		}
	}
	@Override
	public int updateSmOrderById(SmOrder smOrder) {
		// TODO Auto-generated method stub
		return smOrderDAO.updateSmOrderById(smOrder);
	}
	@Override
	public void deleteAllSmOrder() {
		// TODO Auto-generated method stub
		smOrderDAO.deleteAllSmOrder();
	}
	@Override
	public long querySmOrderTotalRow(Map<String, Object> paramMap)
			throws MQException {
		// TODO Auto-generated method stub
		try {
			return smOrderDAO.querySmOrderTotalRow(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询今日需要发送短信总数出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}
	@Override
	public List<SmOrder> querySmOrderList(Map<String, Object> paramMap)
			throws MQException {
		// TODO Auto-generated method stub
		try {
			return smOrderDAO.querySmOrderList(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询今日需要发送短信出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}
	@Override
	public Map<String, Object> send(SmOrder smOrder) throws MQException {
		// TODO Auto-generated method stub
		log.info("发送短信，短信ID：" + smOrder.getSmOrderId());
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		boolean ret = SmUtils.sendMsg(smOrder.getMobileNumber(),
				smOrder.getData().split(CommonConstants.SM_DATA_SPLIT_SEPARATOR), 
				smOrder.getTemplateId());
		
		if(!ret){
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, "短信发送失败，短信ID：" + smOrder.getSmOrderId());
			return retMap;
		}

		smOrder.setSendStatus(ret ? StatusConstants.SEND_SMS_SUCCESS : StatusConstants.SEND_SMS_FAILED);
		smOrder.setSendTime(DateUtil.getCurDate());
		smOrder.setUpdateTime(DateUtil.getCurDate());
		
		smOrderDAO.updateSmOrderById(smOrder);
		
		SmLog smLog = new SmLog();
		smLog.setSmOrderId(smOrder.getSmOrderId());
		smLog.setBookTime(smOrder.getBookTime());
		smLog.setCreateTime(DateUtil.getCurDate());
		smLog.setData(smOrder.getData());
		smLog.setMobileNumber(smOrder.getMobileNumber());
		smLog.setOperator(smOrder.getOperator());
		smLog.setSendStatus(smOrder.getSendStatus());
		smLog.setSendTime(smOrder.getSendTime());
		smLog.setSendType(smOrder.getSendType());
		smLog.setTemplateId(smOrder.getTemplateId());
		smLog.setUpdateTime(smOrder.getUpdateTime());
		
		logService.insertSmLog(smLog);
		
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "短信发送成功，短信ID：" + smOrder.getSmOrderId());
		
		return retMap;
	}
	
	
}
