package com.greenkoo.sys.service;

import java.util.List;
import java.util.Map;

import com.greenkoo.sys.model.SysOperationRecord;

/**
 * 后台操作记录Service
 * 
 * @author QYANZE
 *
 */
public interface ISysOperationRecordService {

	void add(SysOperationRecord record) throws Exception;

	int queryCount(Map<String, Object> paramMap);

	List<SysOperationRecord> queryList(Map<String, Object> paramMap);
}
