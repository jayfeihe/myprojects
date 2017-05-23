package com.greenkoo.record.service;

import java.util.List;

import com.greenkoo.record.model.AdFeedBack;
import com.greenkoo.record.model.form.AdFeedBackBean;

/**
 * 整改反馈Service
 * 
 * @author QYANZE
 *
 */
public interface IAdFeedBackService {

	int update(AdFeedBack feedBack) throws Exception;

	AdFeedBack getByInfoIdAndRoleUrl(String infoId, String roleUrl);
	
	List<AdFeedBack> getByInfoIdAndNoRoleUrl(String infoId, String roleUrl);

	void correct(AdFeedBackBean feedBackBean, String roleUrl, String operator) throws Exception;
}
