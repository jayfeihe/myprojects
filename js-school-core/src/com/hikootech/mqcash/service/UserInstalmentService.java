package com.hikootech.mqcash.service;

import java.util.Map;

import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.UserInstalment;

public interface UserInstalmentService {
	
	public UserInstalment queryUserInstalmentById(String instalmentId) throws MQException;
	
	public Long updateInstalmentStatus(Map<String, Object> paramMap) throws MQException;
	
	public Map<String, Object> judgeUserInstalmentOverdueStatus(String instalmentId) throws MQException;
	
}
