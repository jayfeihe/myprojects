package com.greenkoo.record.dao;

import java.util.List;

import com.greenkoo.record.model.AdFeedBack;

public interface AdFeedBackDao {

	List<AdFeedBack> queryFeedbackDataByTime(String createTime);
}
