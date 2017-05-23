package com.greenkoo.record.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.record.dao.AdFeedBackDao;
import com.greenkoo.record.model.AdFeedBack;
import com.greenkoo.record.model.AdFeedBackLog;
import com.greenkoo.record.model.form.AdFeedBackBean;
import com.greenkoo.record.service.IAdFeedBackLogService;
import com.greenkoo.record.service.IAdFeedBackService;
import com.greenkoo.utils.DateUtil;

@Service("adFeedBackService")
public class AdFeedBackServiceImpl implements IAdFeedBackService {

	@Autowired
	private AdFeedBackDao dao;
	@Autowired
	private IAdFeedBackLogService adFeedBackLogService;
	
	@Transactional
	@Override
	public int update(AdFeedBack feedBack) throws Exception {
		return dao.update(feedBack);
	}

	@Override
	public AdFeedBack getByInfoIdAndRoleUrl(String infoId, String roleUrl) {
		return dao.getByInfoIdAndRoleUrl(infoId,roleUrl);
	}
	
	@Override
	public List<AdFeedBack> getByInfoIdAndNoRoleUrl(String infoId, String roleUrl) {
		return dao.getByInfoIdAndNoRoleUrl(infoId,roleUrl);
	}

	@Transactional
	@Override
	public void correct(AdFeedBackBean feedBackBean, String roleUrl,String operator) throws Exception {
		// 修改我方反馈信息
		AdFeedBack feedBack = this.getByInfoIdAndRoleUrl(feedBackBean.getInfoId(), roleUrl);
		feedBack.setCorrectionTime(feedBackBean.getCorrectionDate());
		feedBack.setRemark(feedBackBean.getRemark());
		feedBack.setStatus(AdFeedBack.STATUS_CORRECTED);
		feedBack.setUpdateTime(DateUtil.getCurDate());
		this.update(feedBack);
		
		// 插入操作日志
		AdFeedBackLog feedBackLog = new AdFeedBackLog();
		feedBackLog.setCorrectionId(feedBack.getCorrectionId());
		feedBackLog.setCorrectionTime(feedBack.getCorrectionTime());
		feedBackLog.setRemark(feedBack.getRemark());
		feedBackLog.setStatus(feedBack.getStatus());
		feedBackLog.setCreateTime(DateUtil.getCurDate());
		feedBackLog.setOperator(operator);
		
		this.adFeedBackLogService.add(feedBackLog);
	}
}
