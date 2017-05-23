package com.greenkoo.record.service;

import java.util.List;

import com.greenkoo.record.model.AdFeedBack;

/**
 * 信息整改发聩Service
 * 
 * @author QYANZE
 *
 */
public interface IAdFeedBackService {
	/**
	 * 查询已整改的数据情况
	 * 
	 * @param createTime 接收数据时间（同期反馈）
	 * @return
	 */
	List<AdFeedBack> queryFeedbackDataByTime(String createTime);
}
