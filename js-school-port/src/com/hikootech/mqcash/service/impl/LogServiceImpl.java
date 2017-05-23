package com.hikootech.mqcash.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.LogDAO;
import com.hikootech.mqcash.po.LogUserInfo;
import com.hikootech.mqcash.po.SmLog;
import com.hikootech.mqcash.po.UserLoginRecord;
import com.hikootech.mqcash.service.LogService;

@Service("logService")
public class LogServiceImpl implements LogService {

	private static  Logger log=LoggerFactory.getLogger(LogServiceImpl.class);
	@Autowired
	private LogDAO logDAO;
	@Override 
	public void addLogUserInfo(LogUserInfo logUserInfo) {
		try {
			logDAO.addLogUserInfo(logUserInfo);
		} catch (Exception e) {
			log.error("记录修改个人信息日志失败",e);
		}
	}
	@Override
	public void insertSmLog(SmLog smLog) {
		try {
			logDAO.insertSmLog(smLog);
		} catch (Exception e) {
			log.error("插入短信记录表失败",e);
		}		
	}
	@Override
	public void addUserLoginRecord(UserLoginRecord userLoginRecord) {
		try {
			logDAO.addUserLoginRecord(userLoginRecord);
		} catch (Exception e) {
			log.error("增加登陆日志失败",e);
		}		
	}

}
