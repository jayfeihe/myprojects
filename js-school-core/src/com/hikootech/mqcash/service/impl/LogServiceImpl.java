package com.hikootech.mqcash.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.LogDAO;
import com.hikootech.mqcash.po.SmLog;
import com.hikootech.mqcash.po.UserOverDueLog;
import com.hikootech.mqcash.service.LogService;

@Service("logService")
public class LogServiceImpl implements LogService {

	private static final Logger logger=LoggerFactory.getLogger(LogServiceImpl.class);
	@Autowired
	private LogDAO logDAO;
	
	@Override
	public void addOverDueErrorLog(UserOverDueLog overDueLog) {
		logDAO.addOverDueErrorLog(overDueLog);
	}
	
	 
	@Override
	public void insertSmLog(SmLog smLog) {
		// TODO Auto-generated method stub
		try {
			logDAO.insertSmLog(smLog);
		} catch (Exception e) {
			logger.error("增加短信记录发生错误："+e.getMessage()); 
		}
	}
}
