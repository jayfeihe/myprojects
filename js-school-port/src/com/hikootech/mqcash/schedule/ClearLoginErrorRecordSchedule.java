package com.hikootech.mqcash.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hikootech.mqcash.util.UserLoginErrorUtils;

/**
 * @author yuwei
 * 2015年9月8日
 * 清除用户登陆出错信息记录
 */
public class ClearLoginErrorRecordSchedule {
	
	private static Logger log = LoggerFactory.getLogger(ClearLoginErrorRecordSchedule.class);
	
	public void service(){
		log.info("启动调度清除用户登陆出错信息记录");
		UserLoginErrorUtils.clear();
	}
	
	public static void main(String[] args) {
		
	}

}
