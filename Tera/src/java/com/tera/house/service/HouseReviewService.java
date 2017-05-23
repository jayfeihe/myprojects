package com.tera.house.service;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.house.model.HouseApp;

/**
 * 
 * 信用贷款复核服务类
 * <b>功能：</b>HouseReviewDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-25 10:37:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("houseReviewService")
public class HouseReviewService {

	private final static Logger log = Logger.getLogger(HouseReviewService.class);
	

	@Autowired(required=false) //决策表服务
	HouseDecisionService houseDecisionService;

	@Autowired(required=false) //申请表服务类
	HouseAppService houseAppService;
	
	public void reviewBpm(String appState,int id ,String nextState,String operator,
			String processer,String logContent1,String msg,String logContent5)throws Exception{
		HouseApp bean=houseAppService.queryByKey(id);
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		bean.setOperator(operator);
		bean.setUpdateTime(ts);
		bean.setState(appState);
		//更新 申请 状态
		houseAppService.updateOnlyChanged(bean);
		//跳转流程
		houseDecisionService.decision(bean.getAppId(), nextState,operator, processer,logContent1,msg, null, null, logContent5, null, null, null, null, null);
		
	}
	
	
}
