package com.greenkoo.record.service;

import com.greenkoo.record.model.AdFeedBackLog;

/**
 * 整改反馈日志记录Service
 * 
 * @author QYANZE
 *
 */
public interface IAdFeedBackLogService {
	
	void add(AdFeedBackLog feedBackLog) throws Exception;
}
