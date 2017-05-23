package com.greenkoo.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.sys.dao.SysOperationRecordDao;
import com.greenkoo.sys.model.SysOperationRecord;
import com.greenkoo.sys.service.ISysOperationRecordService;

@Service("sysOperationRecordService")
public class SysOperationRecordServiceImpl implements ISysOperationRecordService {

	@Autowired
	private SysOperationRecordDao dao;
	
	@Transactional
	@Override
	public void add(SysOperationRecord record) throws Exception {
		this.dao.add(record);
	}

	@Override
	public int queryCount(Map<String, Object> paramMap) {
		return this.dao.queryCount(paramMap);
	}

	@Override
	public List<SysOperationRecord> queryList(Map<String, Object> paramMap) {
		return this.dao.queryList(paramMap);
	}

}
