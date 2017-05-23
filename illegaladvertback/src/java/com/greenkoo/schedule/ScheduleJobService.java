package com.greenkoo.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.inter.service.IAdFeedBackInterService;
import com.greenkoo.record.service.IAdPicService;
import com.greenkoo.utils.DateUtil;

/**
 * 调度任务服务类
 * 
 * @author QYANZE
 *
 */
@Service("taskJob")
public class ScheduleJobService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAdPicService adPicService;
	@Autowired
	private IAdFeedBackInterService adFeedBackInterService;
	
	/**
	 * 图片下载任务
	 */
	public void adPicJob() {
		logger.info("图片下载任务开始..." + DateUtil.getCurDateStr(DateUtil.FORMAT_SS));
		
		this.adPicService.adPicProccess();
		
		logger.info("图片下载任务结束..." + DateUtil.getCurDateStr(DateUtil.FORMAT_SS));
	}
	
	/**
	 * 整改反馈任务
	 */
	public void feedBackJob() {
		logger.info("整改反馈任务开始..." + DateUtil.getCurDateStr(DateUtil.FORMAT_SS));
		
		this.adFeedBackInterService.feedBack();
		
		logger.info("整改反馈任务结束..." + DateUtil.getCurDateStr(DateUtil.FORMAT_SS));
	}
}
