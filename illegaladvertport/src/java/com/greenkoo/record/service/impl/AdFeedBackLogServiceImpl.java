package com.greenkoo.record.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.record.dao.AdFeedBackLogDao;
import com.greenkoo.record.model.AdFeedBackLog;
import com.greenkoo.record.service.IAdFeedBackLogService;

@Service("adFeedBackLogService")
public class AdFeedBackLogServiceImpl implements IAdFeedBackLogService {

	@Autowired
	private AdFeedBackLogDao dao;
	
	@Override
	public void add(AdFeedBackLog feedBackLog) throws Exception {
		dao.add(feedBackLog);
	}
}
