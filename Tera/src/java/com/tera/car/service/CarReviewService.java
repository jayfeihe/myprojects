package com.tera.car.service;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.car.model.CarApp;

/**
 * 
 * 信用贷款复核服务类
 * <b>功能：</b>CarReviewDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-25 10:37:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carReviewService")
public class CarReviewService {

	private final static Logger log = Logger.getLogger(CarReviewService.class);
	

	@Autowired(required=false) //决策表服务
	CarDecisionService carDecisionService;

	@Autowired(required=false) //申请表服务类
	CarAppService carAppService;
	
	public void reviewBpm(String appState,int id ,String nextState,String operator,
			String processer,String logContent1,String msg,String logContent5)throws Exception{
		CarApp bean=carAppService.queryByKey(id);
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		bean.setOperator(operator);
		bean.setUpdateTime(ts);
		bean.setState(appState);
		//更新 申请 状态
		carAppService.updateOnlyChanged(bean);
		//跳转流程
		carDecisionService.decision(bean.getAppId(), nextState,operator, processer,logContent1,msg, null, null, logContent5, null, null, null, null, null);
		
	}
	
	
}
