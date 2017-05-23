package com.greenkoo.record.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.record.dao.AdPicDao;
import com.greenkoo.record.model.AdPic;
import com.greenkoo.record.service.IAdPicService;

@Service("adPicService")
public class AdPicServiceImpl implements IAdPicService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AdPicDao dao;
	
	@Transactional
	@Override
	public void add(AdPic pic) throws Exception {
		try {
			this.dao.add(pic);
		} catch (Exception e) {
			logger.error("保存图片下载信息失败");
			throw e;
		}
	}

}
