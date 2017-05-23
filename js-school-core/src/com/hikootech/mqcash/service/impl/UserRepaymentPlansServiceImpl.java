package com.hikootech.mqcash.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
import com.hikootech.mqcash.dao.UserInstalmentDAO;
import com.hikootech.mqcash.dao.UserRepaymentPlansDAO;
import com.hikootech.mqcash.dto.UserForSendSmDTO;
import com.hikootech.mqcash.dto.UserRepaymentPlansDTO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.exception.MQExceptionConstants;
import com.hikootech.mqcash.po.UserInstalment;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserRepaymentPlans;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.UserInstalmentService;
import com.hikootech.mqcash.service.UserProtocolService;
import com.hikootech.mqcash.service.UserRepaymentPlansService;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.InstalmentUtils;

@Service("userRepaymentPlansService")
public class UserRepaymentPlansServiceImpl implements UserRepaymentPlansService {
	
	private static Logger log = LoggerFactory.getLogger(UserRepaymentPlansServiceImpl.class);
	
	@Autowired
	private UserRepaymentPlansDAO userRepaymentPlansDAO;
	@Autowired
	private UserInstalmentDAO userInstalmentDAO;
	@Autowired
	private UserInstalmentService userInstalmentService;
	@Autowired
	private ConfigKvService configKvService;
	@Autowired
	private UserProtocolService userProtocolService;
	
	@Override
	public UserRepaymentPlansDTO queryUserRepaymentPlansById(String plansId)
			throws MQException {
		// TODO Auto-generated method stub
		try {
			return userRepaymentPlansDAO.queryUserRepaymentPlansById(plansId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询还款计划出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public Long queryUserRepaymentPlansTotalRow(Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userRepaymentPlansDAO.queryUserRepaymentPlansTotalRow(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询还款计划总数出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public List<UserRepaymentPlansDTO> queryUserRepaymentPlansList(Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userRepaymentPlansDAO.queryUserRepaymentPlansList(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询还款计划列表出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}
	
	@Override
	public Long queryOverdueUserRepaymentPlansTotalRow(
			Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userRepaymentPlansDAO.queryOverdueUserRepaymentPlansTotalRow(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询还款计划总数出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public List<UserRepaymentPlansDTO> queryOverdueUserRepaymentPlansList(
			Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userRepaymentPlansDAO.queryOverdueUserRepaymentPlansList(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询还款计划列表出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}
	
	@Override
	public void recoverUserRepaymentPlansDealingStatus(UserRepaymentPlans plan)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("当代收失败时，需要将计划状态为处理中的还款计划的计划状态恢复，恢复后状态可能为：待还款、已逾期，计划ID：" + plan.getRepaymentPlansId());
		if(EntityUtils.isEmpty(plan)){
			log.error("代收失败恢复还款计划状态，分期计划参数为空");
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC);
		}
		
		if(DateUtil.dayOfOrigin(new Date()).compareTo(plan.getPlanRepaymentTime()) > 0){
			log.warn("该还款计划代收失败，还款计划已经逾期，修改计划状态为：50已逾期，并且计算逾期罚息!");
			
			this.updateUserRepaymentPlansDealingToOverdue(plan.getRepaymentPlansId());
			
			UserRepaymentPlansDTO plansDTO = this.queryUserRepaymentPlansById(plan.getRepaymentPlansId());
			
			this.calOverdue(plansDTO);
		}else{
			log.info("该还款计划代收失败，还款计划没有逾期，修改计划状态为：20待还款，并且不需要计算逾期罚息!");
			this.updateUserRepaymentPlansDealingToWaiting(plan.getRepaymentPlansId());
		}
		
	}
	
	@Override
	public void updateUserRepaymentPlansDealingToOverdue(String plansId)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("将计划状态为处理中的还款计划的计划状态修改为：已逾期，计划ID：" + plansId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE);
		paramMap.put("updateTime", new Date());
		paramMap.put("repaymentPlansId", plansId);
		paramMap.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY);
		
		int row = 0;
		try {
			row = userRepaymentPlansDAO.updateUserRepaymentPlansStatus(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新还款计划状态出错，数据库异常！计划ID：" + plansId, e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(row != 1){
			log.error("更新还款计划状态失败！计划ID：" + plansId + "，修改行数：" + row);
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION_DESC);
		}
	}

	@Override
	public void updateUserRepaymentPlansDealingToWaiting(String plansId)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("将计划状态为处理中的还款计划的计划状态修改为：待还款，计划ID：" + plansId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY);
		paramMap.put("updateTime", new Date());
		paramMap.put("repaymentPlansId", plansId);
		paramMap.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY);
		
		int row = 0;
		try {
			row = userRepaymentPlansDAO.updateUserRepaymentPlansStatus(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新还款计划状态出错，数据库异常！计划ID：" + plansId, e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(row != 1){
			log.error("更新还款计划状态失败！计划ID：" + plansId + "，修改行数：" + row);
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION_DESC);
		}
	}

	@Override
	public void updateUserRepaymentPlansWaitingToOverdue(String plansId)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("将计划状态为待还款的还款计划的计划状态修改为：已逾期，计划ID：" + plansId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE);
		paramMap.put("updateTime", new Date());
		paramMap.put("repaymentPlansId", plansId);
		paramMap.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY);
		
		int row = 0;
		try {
			row = userRepaymentPlansDAO.updateUserRepaymentPlansStatus(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新还款计划状态出错，数据库异常！计划ID：" + plansId, e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(row != 1){
			log.error("更新还款计划状态失败！计划ID：" + plansId + "，修改行数：" + row);
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION_DESC);
		}
	}

	@Override
	public void updateUserRepaymentPlansDealingToPaySuccess(UserRepaymentPlans plan, UserPaymentOrder paymentOrder)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("将计划状态为处理中的还款计划的计划状态修改为：已还款，计划ID：" + plan.getRepaymentPlansId());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_COMPLETED);
		paramMap.put("updateTime", new Date());
		paramMap.put("repaymentPlansId", plan.getRepaymentPlansId());
		paramMap.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY);
		
		if(EntityUtils.isEmpty(paymentOrder.getPaymentAmount())){
			log.error("实际还款金额为0，还款金额异常!");
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION_DESC);
		}
		
//		if(paymentOrder.getPaymentAmount().compareTo(plan.getReceivableAmount()) != 0){
//			log.error("实际还款金额不等于应还金额，还款金额异常!实际还款额：" + paymentOrder.getPaymentAmount() + "，应还金额：" + plan.getReceivableAmount());
//			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION_DESC);
//		}
		
		paramMap.put("receivedAmount", paymentOrder.getPaymentAmount());
		paramMap.put("receivedPrincipal", plan.getReceivablePrincipal());
		paramMap.put("receivedService", plan.getReceivableService());
		paramMap.put("receivedOverdue", plan.getReceivableOverdue());
		paramMap.put("realRepaymentTime", plan.getRealRepaymentTime());
		paramMap.put("operator", CommonConstants.DEFAULT_OPERATOR);
		
		int row = 0;
		try {
			row = userRepaymentPlansDAO.updateUserRepaymentPlansStatus(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新还款计划状态出错，数据库异常！计划ID：" + plan.getRepaymentPlansId(), e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(row != 1){
			log.error("更新还款计划状态失败！计划ID：" + plan.getRepaymentPlansId() + "，修改行数：" + row);
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION_DESC);
		}
	}
	
	@Override
	public void updateUserRepaymentPlansOverdueToPaySuccess(UserRepaymentPlans plan, UserPaymentOrder paymentOrder)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("将计划状态为已逾期的还款计划的计划状态修改为：已还款，计划ID：" + plan.getRepaymentPlansId());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_COMPLETED);
		paramMap.put("updateTime", new Date());
		paramMap.put("repaymentPlansId", plan.getRepaymentPlansId());
		paramMap.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE);
		
		if(EntityUtils.isEmpty(paymentOrder.getPaymentAmount())){
			log.error("实际还款金额为0，还款金额异常!");
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION_DESC);
		}
		
//		if(paymentOrder.getPaymentAmount().compareTo(plan.getReceivableAmount()) != 0){
//			log.error("实际还款金额不等于应还金额，还款金额异常!实际还款额：" + paymentOrder.getPaymentAmount() + "，应还金额：" + plan.getReceivableAmount());
//			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION_DESC);
//		}
		
		paramMap.put("receivedAmount", paymentOrder.getPaymentAmount());
		paramMap.put("receivedPrincipal", plan.getReceivablePrincipal());
		paramMap.put("receivedService", plan.getReceivableService());
		paramMap.put("receivedOverdue", plan.getReceivableOverdue());
		paramMap.put("realRepaymentTime", plan.getRealRepaymentTime());
		paramMap.put("operator", CommonConstants.DEFAULT_OPERATOR);
		
		int row = 0;
		try {
			row = userRepaymentPlansDAO.updateUserRepaymentPlansStatus(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新还款计划状态出错，数据库异常！计划ID：" + plan.getRepaymentPlansId(), e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(row != 1){
			log.error("更新还款计划状态失败！计划ID：" + plan.getRepaymentPlansId() + "，修改行数：" + row);
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION_DESC);
		}
	}
	
	@Override
	public void updateUserRepaymentPlansWaitingToPaySuccess(UserRepaymentPlans plan, UserPaymentOrder paymentOrder)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("将计划状态为待还款的还款计划的计划状态修改为：已还款，计划ID：" + plan.getRepaymentPlansId());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_COMPLETED);
		paramMap.put("updateTime", new Date());
		paramMap.put("repaymentPlansId", plan.getRepaymentPlansId());
		paramMap.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY);
		
		if(EntityUtils.isEmpty(paymentOrder.getPaymentAmount())){
			log.error("实际还款金额为0，还款金额异常!");
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION_DESC);
		}
		
//		if(paymentOrder.getPaymentAmount().compareTo(plan.getReceivableAmount()) != 0){
//			log.error("实际还款金额不等于应还金额，还款金额异常!实际还款额：" + paymentOrder.getPaymentAmount() + "，应还金额：" + plan.getReceivableAmount());
//			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_PAY_AMOUNT_EXCEPTION_DESC);
//		}
		
		paramMap.put("receivedAmount", paymentOrder.getPaymentAmount());
		paramMap.put("receivedPrincipal", plan.getReceivablePrincipal());
		paramMap.put("receivedService", plan.getReceivableService());
		paramMap.put("receivedOverdue", plan.getReceivableOverdue());
		paramMap.put("realRepaymentTime", plan.getRealRepaymentTime());
		paramMap.put("operator", CommonConstants.DEFAULT_OPERATOR);
		
		int row = 0;
		try {
			row = userRepaymentPlansDAO.updateUserRepaymentPlansStatus(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新还款计划状态出错，数据库异常！计划ID：" + plan.getRepaymentPlansId(), e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(row != 1){
			log.error("更新还款计划状态失败！计划ID：" + plan.getRepaymentPlansId() + "，修改行数：" + row);
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_STATUS_EXCEPTION_DESC);
		}
	}

	@Override
	public List<UserRepaymentPlans> queryUserRepaymentPlansOfInstalment(
			Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userRepaymentPlansDAO.queryUserRepaymentPlansOfInstalment(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询还款计划出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public Long releaseUserRepaymentPlansLock(String instalmentId)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("解除分期订单对应还款计划的代收锁，分期订单ID：" + instalmentId);
		
		int overdueDays = Integer.parseInt(configKvService.get(CommonConstants.OVERDUE_DAYS_MIDDLE));
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		Date endTime = DateUtil.dayOfOrigin(DateUtil.addDate(new Date(), Calendar.DATE, -overdueDays));
		queryMap.put("endTime", endTime);
		queryMap.put("instalmentId", instalmentId);
		queryMap.put("plansStatusList", new Integer[]{StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY,
				StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY,
				StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE});
		queryMap.put("repayLock", StatusConstants.INSTALMENT_REPAYMENT_PLANS_REPAY_LOCK);
		
		List<UserRepaymentPlans> overduePlans = this.queryUserRepaymentPlansOfInstalment(queryMap);
		
		if(EntityUtils.isNotEmpty(overduePlans)){
			log.info("有超过" + overdueDays + "天已逾期分期还款计划未还款"); 
			for (UserRepaymentPlans plan : overduePlans) {
				if(plan.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_COMPLETED.intValue()){
					log.info("无法解除分期订单对应还款计划的代收锁，有逾期" + overdueDays + "天未还款的还款计划，还款计划ID：" + plan.getRepaymentPlansId() + "，状态：" + plan.getPlansStatus());
				}
			}
			
		}else{
			log.info("没有逾期" + overdueDays + "天未还款的还款计划，解除锁定开始，分期订单ID：" + instalmentId);
			try {
				return userRepaymentPlansDAO.releaseUserRepaymentPlansLock(instalmentId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("更新分期订单对应所有还款计划的代收锁为：不锁定。更新出错，数据库异常！", e);
				throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
			}
		}
		
		return 0l;
	}

	@Override
	public Long checkOverdue(Date startTime, Date endTime) throws MQException {
		// TODO Auto-generated method stub
		log.info("查询还款计划是否逾期，开始时间：" + startTime + "，结束时间：" + endTime);
		Date maxTime = DateUtil.dayOfOrigin(new Date());
		
		if(maxTime.getTime() < endTime.getTime() || startTime.compareTo(endTime) >= 0){
			log.error("还款计划逾期时间参数有误，检查开始时间不能大于结束时间，并且结束时间不能大于最大逾期时间（当前时间-1天）！");
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_OVERDUE_TIME_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_OVERDUE_TIME_EXCEPTION_DESC);
		}
		
		Map<String, Object> iParamMap = new HashMap<String, Object>();
		iParamMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY);
		iParamMap.put("endTime", endTime);
		iParamMap.put("startTime", startTime);
		
		List<String> idList = null;
		try {
			idList = userRepaymentPlansDAO.queryInstalmentIdFromRepaymentPlans(iParamMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("从还款计划中查询分期去重复的订单ID出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateTime", new Date());
		paramMap.put("operator", CommonConstants.DEFAULT_OPERATOR);
		paramMap.put("endTime", endTime);
		paramMap.put("startTime", startTime);
		long row = 0;
		try {
			row = userRepaymentPlansDAO.updateUserRepaymentPlansToOverdue(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新还款计划逾期标志。更新出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		log.info("今日逾期还款计划数：" + row);
		
		for (String instalmentId : idList) {
			Map<String, Object> updateParamMap = new HashMap<String, Object>();
			updateParamMap.put("instalmentId", instalmentId);
			updateParamMap.put("instalmentStatus", StatusConstants.INSTALMENT_STATUS_OVERDUE);
			updateParamMap.put("beforeInstalmentStatus", StatusConstants.INSTALMENT_STATUS_AWAIT_PAY);
			try {
				userInstalmentDAO.updateInstalmentStatus(updateParamMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("更新分期订单出错。更新出错，数据库异常！", e);
				throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
			}
		}
		
		return row;
	}

	@Override
	public void calOverdue(UserRepaymentPlansDTO plansDTO) throws MQException {
		// TODO Auto-generated method stub
		if(EntityUtils.isEmpty(plansDTO)){
			log.error("计算逾期手续费,参数为空！");
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC);
		}
		
		log.info("计算逾期手续费，还款计划ID：" + plansDTO.getRepaymentPlansId());
		log.info(",当前费率为：" + plansDTO.getOverdueRate() + ",当前本金为：" + plansDTO.getPartnerOrderAmount()
				+ ",当前还款时间为：" + DateUtil.formatDate(plansDTO.getPlanRepaymentTime(), "yyyy-MM-dd")
				+ ",当前还款计划状态：" + plansDTO.getPlansStatus());
		
		if(plansDTO.getPlansStatus() != StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE.intValue()){
			log.error("计算逾期手续费,当前还款计划的状态不正确，不为已逾期！");
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC);
		}
		
		BigDecimal receivableOverdue = new BigDecimal(0);
		try {
			receivableOverdue = InstalmentUtils.calOverdue(plansDTO.getPartnerOrderAmount(), plansDTO.getOverdueRate(), plansDTO.getPlanRepaymentTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("计算逾期服务费出错", e);
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_CAL_OVERDUE_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_CAL_OVERDUE_EXCEPTION_DESC);
		}
		plansDTO.setReceivableOverdue(receivableOverdue);//设置当前应收逾期罚息
		
		BigDecimal receivableAmount = InstalmentUtils.calcPayAmount(plansDTO);//计算应收总金额
		
		plansDTO.setReceivableAmount(receivableAmount);
		plansDTO.setUpdateTime(new Date());
		
		long row = 0;
		try {
			row = userRepaymentPlansDAO.updateRepaymentPlansOverdueAmount(plansDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新还款计划应收逾期罚息、应收总金额出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(row != 1){
			log.error("更新还款计划应收逾期罚息、应收总金额有误，更新条数不正确：" + row 
					+ "，还款计划ID：" + plansDTO.getRepaymentPlansId() 
					+ "，还款计划的计划状态：" + plansDTO.getPlansStatus());
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_OVERDUE_ROW_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_UPDATE_OVERDUE_ROW_EXCEPTION_DESC); 
		}
		
	}

	@Override
	public List<String> queryOverdueRepaymentPlansInstalmentIdList(
			Date startTime, Date endTime) throws MQException {
		// TODO Auto-generated method stub
		log.info("查询逾期未锁定未还款完成的还款计划对应分期ID列表，开始时间：" + startTime + "，结束时间：" + endTime);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		
		try {
			return userRepaymentPlansDAO.queryOverdueRepaymentPlansInstalmentIdList(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询逾期未锁定未还款完成的还款计划对应分期ID列表出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public void lockOverduePlans(String instalmentId)
			throws MQException {
		// TODO Auto-generated method stub
		log.info("锁定分期订单对应所有的还款计划，分期订单ID：" + instalmentId);
		
		if(EntityUtils.isEmpty(instalmentId)){
			log.error("锁定分期订单对应分期订单号为空");
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC); 
		}
		
		Date overdueEndTime = DateUtil.dayOfOrigin(DateUtil.addDate(new Date(), Calendar.DATE, -1));;//昨天 0点
		
		//逾期未锁定的还款计划
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("instalmentId", instalmentId);
		paramMap.put("endTime", overdueEndTime);
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE);
		paramMap.put("repayLock", StatusConstants.INSTALMENT_REPAYMENT_PLANS_REPAY_UNLOCK);
		
		List<UserRepaymentPlans> plans = this.queryUserRepaymentPlansOfInstalment(paramMap);
		
		if(EntityUtils.isEmpty(plans)){
			log.warn("该分期订单没有已逾期并且未锁定的还款计划，分期订单号：" + instalmentId);
			return;
		}
		
		UserInstalment instalment = userInstalmentService.queryUserInstalmentById(instalmentId);
		
		if(EntityUtils.isEmpty(instalment)){
			log.error("找不到对应的分期订单，分期订单号：" + instalmentId);
			throw new MQException(MQExceptionConstants.MQ_UNKNOWN_EXCEPTION, MQExceptionConstants.MQ_UNKNOWN_EXCEPTION_DESC);
		}
		
		if(instalment.getInstalmentStatus() != StatusConstants.INSTALMENT_STATUS_OVERDUE.intValue()){
			log.warn("分期订单状态有误，分期订单不为30已逾期，无法锁定该分期订单对应的还款计划，分期订单ID：" + instalmentId + "分期订单状态：" + instalment.getInstalmentStatus());
			return;
		}
		
		long row = 0;
		try {
			row = userRepaymentPlansDAO.updateRepayLockByInstalmentId(instalmentId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("锁定分期订单对应所有的还款计划出错，分期订单id：" + instalmentId, e);
			throw new MQException(MQExceptionConstants.MQ_REPAYMENT_PLANS_LOCK_OVERDUE_PLANS_EXCEPTION, MQExceptionConstants.MQ_REPAYMENT_PLANS_LOCK_OVERDUE_PLANS_EXCEPTION_DESC); 
		}
		
		log.info("锁定分期订单对应所有的还款计划成功，分期订单ID：" + instalmentId + "锁定还款计划数：" + row);
	}

	@Override
	public int queryPlansToRemindNum(Map<String, Object> paramMap)
			throws MQException {
		// TODO Auto-generated method stub
		try {
			return userRepaymentPlansDAO.queryPlansToRemindNum(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询用户状态是有效的，用户有待还款的、未逾期的、未代收锁的、计划还款时间一定范围的还款计划数目出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public List<UserForSendSmDTO> queryPlansToRemind(Map<String, Object> paramMap)
			throws MQException {
		// TODO Auto-generated method stub
		try {
			return userRepaymentPlansDAO.queryPlansToRemind(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询待提醒的短信信息出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public Map<String, Object> judgeInstalmentCompletedByPlans(
			UserRepaymentPlans plan) throws MQException {
		// TODO Auto-generated method stub
		log.info("判断还款计划对应分期的所有还款计划是否还款完成。分期单ID：" + plan.getInstalmentId() + "还款计划ID：" + plan.getRepaymentPlansId());
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		UserInstalment instalment = null;
		try {
			instalment = userInstalmentDAO.queryUserInstalmentById(plan.getInstalmentId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询分期订单出错，数据库异常！分期订单ID：" + plan.getInstalmentId(), e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(EntityUtils.isEmpty(instalment)){
			log.info("找不到还款计划对应的分期订单，未知错误！分期订单ID：" + plan.getInstalmentId());
			throw new MQException(MQExceptionConstants.MQ_UNKNOWN_EXCEPTION, MQExceptionConstants.MQ_UNKNOWN_EXCEPTION_DESC);
		}
		
		if(instalment.getInstalmentStatus() != StatusConstants.INSTALMENT_STATUS_AWAIT_PAY.intValue()
				&& instalment.getInstalmentStatus() != StatusConstants.INSTALMENT_STATUS_OVERDUE.intValue() ){
			String msg = "分期订单状态不为：20待还款 或 30已逾期，不能判断分期是否已经完成，系统未知异常，请检查分期订单状态！分期订单ID：" + instalment.getInstalmentId() + ",分期订单状态：" + instalment.getInstalmentStatus();
			log.error(msg);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, msg);
			return retMap;
		}
		
		//处理分期：检查还款计划是否是最后一期，最后一期还款成功，分期订单状态-->分期结束
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("instalmentId", plan.getInstalmentId());
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_COMPLETED);
		
		Long instalmentCount = this.queryUserRepaymentPlansTotalRow(paramMap);
		if(EntityUtils.isNotEmpty(instalmentCount) && instalmentCount.intValue() == plan.getInstalmentCount().intValue()){
			log.info("修改分期订单为：40已结清");
			Map<String, Object> iParamMap = new HashMap<String, Object>();
			iParamMap.put("instalmentId", plan.getInstalmentId());
			iParamMap.put("instalmentStatus", StatusConstants.INSTALMENT_STATUS_COMPLETED);
			iParamMap.put("beforeInstalmentStatus", instalment.getInstalmentStatus());
			long row = 0l;
			try {
				row = userInstalmentDAO.updateInstalmentStatus(iParamMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("更新分期订单为分期完成出错，数据库异常！分期订单ID：" + plan.getInstalmentId(), e);
				throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
			}
			
			if(row != 1){
				log.error("更新分期订单为分期完成有误，更新条数不正确! 分期订单ID：" + plan.getInstalmentId() + "，修改行数：" + row);
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_INSTALMENT_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_INSTALMENT_EXCEPTION_DESC);
			}
			
			log.info("修改合同协议状态，执行中-->结束");
			long pRow = 0l;
			try {
				pRow = userProtocolService.updateUserProtocolEffectiveToCompleted(plan.getInstalmentId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("修改合同协议状态，执行中-->结束 出错，数据库异常！分期订单ID：" + plan.getInstalmentId(), e);
				throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
			}
			if(pRow != 1){
				log.error("修改合同协议状态，执行中-->结束 有误，更新条数不正确! 分期订单ID：" + plan.getInstalmentId() + "，修改行数：" + row);
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_INSTALMENT_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_INSTALMENT_EXCEPTION_DESC);
			}
			
			String msg = ("分期还款完毕! 分期订单ID：" + plan.getInstalmentId() + "，分期数：" + instalmentCount);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, msg);
		}else{
			String msg = "分期订单对应的还款计划最后一个分期还款完毕，但是该分期订单还有其他还款计划未还款完毕！分期订单ID：" 
					+ plan.getInstalmentId() + "，总还款计划数：" + plan.getInstalmentCount().intValue() 
					+ "，实际还款数：" + instalmentCount;
			log.error(msg);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, msg);
		}
		
		return retMap;
	}

}
