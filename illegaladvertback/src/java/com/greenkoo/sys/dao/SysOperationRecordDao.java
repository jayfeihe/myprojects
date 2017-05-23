package com.greenkoo.sys.dao;

import java.util.List;
import java.util.Map;

import com.greenkoo.sys.model.SysOperationRecord;

public interface SysOperationRecordDao {

	void add(SysOperationRecord record) throws Exception;

	int queryCount(Map<String, Object> paramMap);

	List<SysOperationRecord> queryList(Map<String, Object> paramMap);
}
