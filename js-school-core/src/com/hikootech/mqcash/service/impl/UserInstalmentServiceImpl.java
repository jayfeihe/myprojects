package com.hikootech.mqcash.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.UserInstalmentDAO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.exception.MQExceptionConstants;
import com.hikootech.mqcash.po.UserInstalment;
import com.hikootech.mqcash.service.UserInstalmentService;
import com.hikootech.mqcash.service.UserRepaymentPlansService;
import com.hikootech.mqcash.util.EntityUtils;

@Service("userInstalmentService")
public class UserInstalmentServiceImpl implements UserInstalmentService {
	
	private static Logger logger = LoggerFactory.getLogger(UserInstalmentServiceImpl.class);
	
	@Autowired
	private UserInstalmentDAO userInstalmentDAO;
	@Autowired
	private UserRepaymentPlansService userRepaymentPlansService;

	@Override
	public UserInstalment queryUserInstalmentById(String instalmentId)
			throws MQException {
		// TODO Auto-generated method stub
		try {
			return userInstalmentDAO.queryUserInstalmentById(instalmentId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询支付订单总数出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public Long updateInstalmentStatus(Map<String, Object> paramMap)
			throws MQException {
		// TODO Auto-generated method stub
		try {
			return userInstalmentDAO.updateInstalmentStatus(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询支付订单总数出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public Map<String, Object> judgeUserInstalmentOverdueStatus(
			String instalmentId) throws MQException {
		// TODO Auto-generated method stub
		logger.info("判断是否需要修改把逾期分期订单状态修改为待还款！分期订单ID：" + instalmentId);
		
		UserInstalment instalment = this.queryUserInstalmentById(instalmentId);
		if(EntityUtils.isEmpty(instalment)){
			logger.error("找不到对应的分期订单，订单ID：" + instalmentId);
			throw new MQException(MQExceptionConstants.MQ_UNKNOWN_EXCEPTION, MQExceptionConstants.MQ_UNKNOWN_EXCEPTION_DESC);
		}
		
		if(instalment.getInstalmentStatus() != StatusConstants.INSTALMENT_STATUS_OVERDUE
				|| instalment.getInstalmentStatus() != StatusConstants.INSTALMENT_STATUS_AWAIT_PAY){
			logger.info("分期订单状态不为20待还款或30已逾期，分期状态有误，无需修改，分期订单ID：" + instalmentId + "，分期订单状态：" + instalment.getInstalmentStatus());
			return null;
		}
		
		if(instalment.getInstalmentStatus() == StatusConstants.INSTALMENT_STATUS_AWAIT_PAY){
			logger.info("分期订单状态不为20待还款，无需修改，分期订单ID：" + instalmentId + "，分期订单状态：" + instalment.getInstalmentStatus());
			return null;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("instalmentId", instalmentId);
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE);
		
		long count = userRepaymentPlansService.queryOverdueUserRepaymentPlansTotalRow(paramMap);
		
		if(count != 0){
			logger.info("还有逾期的还款计划没有还款，数量：" + count + "，无法修改分期订单状态为待还款！分期订单ID：" + instalmentId);
		}else{
			logger.info("没有逾期的还款计划未还款，修改分期订单状态为待还款！分期订单ID：" + instalmentId);
			Map<String, Object> iParamMap = new HashMap<String, Object>();
			iParamMap.put("instalmentId", instalmentId);
			iParamMap.put("instalmentStatus", StatusConstants.INSTALMENT_STATUS_AWAIT_PAY);
			iParamMap.put("beforeInstalmentStatus", StatusConstants.INSTALMENT_STATUS_OVERDUE);
			
			this.updateInstalmentStatus(iParamMap);
		}
		
		return null;
	}
	
}
