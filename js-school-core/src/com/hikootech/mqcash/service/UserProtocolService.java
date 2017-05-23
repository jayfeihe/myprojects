package com.hikootech.mqcash.service;

import com.hikootech.mqcash.exception.MQException;

public interface UserProtocolService {
	
	public long updateUserProtocolEffectiveToCompleted(String instalmentId) throws MQException;

}
