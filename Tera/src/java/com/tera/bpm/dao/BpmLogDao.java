/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tera.bpm.model.BpmLog;

/**
 * @author Administrator
 */
@Repository
public interface BpmLogDao {
	/**
	 * 流程跳转时，根据流程ID记录流程日志
	 * @param bpmLog 跳转的日志
	 */
	void addBpmLog(BpmLog bpmLog);

	/**
	 * 流程跳转时，根据流程ID记录流程日志
	 * @param map 跳转的日志
	 */
	void addBpmLogMap(Map<String, Object> map);

	/**
	 * 根据流程ID，获取流程实例的处理日志
	 * @param taskId 流程ID
	 * @return BpmLog列表
	 */
	List<BpmLog> getBpmLogByTaskId(int taskId);

	/**
	 * 删除流程日志
	 * @param id 日志id
	 */
	void deleteBpmLogById(int id);

	/**
	 * 根据流程实例id批量删除流程日志
	 * @param taskId 流程taskId
	 */
	void deleteBpmLogByTaskId(int taskId);

	/**
	 * 获取某状态的日志列表
	 * @param map map(key: taskId, state, bizKey, processName)
	 * @return 任务列表
	 */
	List<BpmLog> getBpmLog(Map<String, Object> map);

	/**
	 * 更新日志
	 * @param bpmLog 日志
	 */
	void updateBpmLog(BpmLog bpmLog);

	/**
	 * 更新日志
	 * @param map 日志
	 */
	void updateBpmLogMap(Map<String, Object> map);

}
