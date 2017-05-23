package com.greenkoo.record.service;

import com.greenkoo.record.model.DataRecord;

/**
 * 告警记录Service接口
 * 
 * @author QYANZE
 *
 */
public interface IDataRecordService {

	int update(DataRecord dataRecord) throws Exception;
}
