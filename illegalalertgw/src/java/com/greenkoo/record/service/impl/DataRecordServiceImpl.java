package com.greenkoo.record.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.record.dao.DataRecordDao;
import com.greenkoo.record.model.DataRecord;
import com.greenkoo.record.service.IDataRecordService;

@Service("dataRecordService")
public class DataRecordServiceImpl implements IDataRecordService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataRecordDao dao;

	@Transactional
	@Override
	public void add(DataRecord dataRecord) throws Exception {
		try {
			this.dao.add(dataRecord);
		} catch (Exception e) {
			logger.error("保存创意信息失败");
			throw e;
		}
	}
}
