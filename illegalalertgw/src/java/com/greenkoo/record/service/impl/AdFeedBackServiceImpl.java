package com.greenkoo.record.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.record.dao.AdFeedBackDao;
import com.greenkoo.record.model.AdFeedBack;
import com.greenkoo.record.service.IAdFeedBackService;

@Service("adFeedBackService")
public class AdFeedBackServiceImpl implements IAdFeedBackService {

	@Autowired
	private AdFeedBackDao dao;

	@Transactional
	@Override
	public void add(AdFeedBack feedBack) throws Exception {
		this.dao.add(feedBack);
	}
}
