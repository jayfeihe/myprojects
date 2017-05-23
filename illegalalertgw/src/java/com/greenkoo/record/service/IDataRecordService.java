package com.greenkoo.record.service;

import com.greenkoo.record.model.DataRecord;

/**
 * 告警记录Service接口
 * 
 * @author QYANZE
 *
 */
public interface IDataRecordService {

	void add(DataRecord dataRecord) throws Exception;
}
