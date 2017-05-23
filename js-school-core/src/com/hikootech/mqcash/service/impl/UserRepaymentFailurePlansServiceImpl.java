package com.hikootech.mqcash.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.UserRepaymentFailurePlansDAO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.exception.MQExceptionConstants;
import com.hikootech.mqcash.po.UserRepaymentFailurePlans;
import com.hikootech.mqcash.service.UserRepaymentFailurePlansService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.GenerateKey;

@Service("userRepaymentFailurePlansService")
public class UserRepaymentFailurePlansServiceImpl implements UserRepaymentFailurePlansService {
	
	private static Logger log = LoggerFactory.getLogger(UserRepaymentFailurePlansServiceImpl.class);
	
	@Autowired
	private UserRepaymentFailurePlansDAO userRepaymentFailurePlansDAO;

	@Override
	public void insertUserRepaymentFailurePlans(
			UserRepaymentFailurePlans failurePlans) throws MQException {
		// TODO Auto-generated method stub
		try {
			userRepaymentFailurePlansDAO.insertUserRepaymentFailurePlans(failurePlans);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增失败还款计划出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public void insertUserRepaymentFailurePlans(String repaymentPlansId,
			Date failureTime) throws MQException {
		// TODO Auto-generated method stub
		UserRepaymentFailurePlans failurePlans = new UserRepaymentFailurePlans();
//		failurePlans.setId(GenerateKey.getId(CommonConstants.REPAYMENT_FAILURE_PLANS_ID_PREFIX, ConfigUtils.getProperty("db_id")));
		failurePlans.setRepaymentPlansId(repaymentPlansId);
		failurePlans.setFailureTime(failureTime);
		failurePlans.setRepeatRepaymentTimes(0);
//		failurePlans.setStatus(StatusConstants.REPAYMENT_FAILURE_PLANS_FAILED);
		failurePlans.setCreateTime(new Date());
		failurePlans.setOperator(CommonConstants.DEFAULT_OPERATOR);
		this.insertUserRepaymentFailurePlans(failurePlans);
	}

}
