package com.greenkoo.record.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.record.dao.AdFeedBackDao;
import com.greenkoo.record.model.AdFeedBack;
import com.greenkoo.record.service.IAdFeedBackService;

@Service("adFeedBackService")
public class AdFeedBackServiceImpl implements IAdFeedBackService {

	@Autowired
	private AdFeedBackDao dao;
	
	@Override
	public List<AdFeedBack> queryFeedbackDataByTime(String createTime) {
		return this.dao.queryFeedbackDataByTime(createTime);
	}
}
