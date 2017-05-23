package com.hikootech.mqcash.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.UserRepayPlanDAO;
import com.hikootech.mqcash.po.UserRepaymentPlans;
import com.hikootech.mqcash.service.UserRepayPlanService;

@Service("userRepayPlanService")
public class UserRepayPlanServiceImpl implements UserRepayPlanService {
	
	private static Logger log=LoggerFactory.getLogger(UserRepayPlanServiceImpl.class);
	@Autowired
	private UserRepayPlanDAO userRepayPlanDAO;
	

	@Override
	public List<UserRepaymentPlans> queryRepayPlansByInstalmentId(Map<String, Object> queryMap) {
		
		try {
			return userRepayPlanDAO.queryRepayPlansByInstalmentId(queryMap);
		} catch (Exception e) {
			log.error("根据账单id和用户id查询还款计划信息失败",e);
			throw new RuntimeException("根据账单id和用户id查询还款计划信息失败",e);
		}
	}
	
	

	@Override
	public UserRepaymentPlans queryRepayPlanByKey(Map<String, Object> queryMap) {
		
		try {
			return userRepayPlanDAO.queryRepayPlanByKey(queryMap);
		} catch (Exception e) {
			log.error("根据主键和用户id查询计划失败",e);
			throw new RuntimeException("根据主键和用户id查询计划失败",e);
		}
	}

 
	@Override
	public void currInstalmentUnLock(String instalmentId) {
		try {
			userRepayPlanDAO.currInstalmentUnLock(instalmentId);
		} catch (Exception e) {
			log.error("根据分期id去掉代收锁失败",e);
			throw new RuntimeException("根据分期id去掉代收锁失败",e);
		}
	}

	@Override
	public int queryMiddleOverDueNum(Map<String, Object> map) {
		try {
			return userRepayPlanDAO.queryMiddleOverDueNum(map);
		} catch (Exception e) {
			log.error("根据分期id查询是否还有中度逾期的计划失败",e);
			throw new RuntimeException("根据分期id查询是否还有中度逾期的计划失败",e);
		}
	}

	@Override
	public List<UserRepaymentPlans> queryRepayPlanByPaymentOrderId(Map<String,Object> map) {
		
		try {
			return userRepayPlanDAO.queryRepayPlanByPaymentOrderId(map);
		} catch (Exception e) {
			log.error("根据还款计划单号查询响应还款计划失败",e);
			throw new RuntimeException("根据还款计划单号查询响应还款计划失败",e);
		}
	}

	@Override
	public int updateRepaymentPlansRealPay(UserRepaymentPlans plan) {
		
		try {
			return userRepayPlanDAO.updateRepaymentPlansRealPay(plan);
		} catch (Exception e) {
			log.error("修改还款计划实收信息以及状态失败",e);
			throw new RuntimeException("修改还款计划实收信息以及状态失败",e);
		}
	}

}
