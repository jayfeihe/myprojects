/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.dao.BpmLogDao;
import com.tera.bpm.model.BpmLog;


/**
 * 流程日志Service
 *
 */
@Service("bpmLogService")
public class BpmLogService {

	@Autowired(required=false)
	private BpmLogDao bpmLogDao;
	
	/**
	 * 根据bizKey获取流程日志记录
	 * @param bizKey
	 * @return
	 */
	public List<BpmLog> getBpmLogsByBizKey(String bizKey) {
		Map<String, Object> logMap = new HashMap<String,Object>();
		logMap.put("bizKey", bizKey);
		List<BpmLog> list = bpmLogDao.getBpmLog(logMap);
		return list;
	}
	
	/**
	 * 流程跳转时，根据流程ID记录流程日志
	 * @param bpmLog 跳转的日志
	 */
	@Transactional
	public void addBpmLog(BpmLog bpmLog) {
		bpmLogDao.addBpmLog(bpmLog);
	}

	/**
	 * 流程跳转时，根据流程ID记录流程日志
	 * @param map 跳转的日志
	 */
	@Transactional
	public void addBpmLogMap(Map<String, Object> map) {
		bpmLogDao.addBpmLogMap(map);
	}

	/**
	 * 根据流程ID，获取流程实例的处理日志
	 * @param taskId 流程ID
	 * @return BpmLog列表
	 */
	public List<BpmLog> getBpmLogByTaskId(int taskId) {
		return bpmLogDao.getBpmLogByTaskId(taskId);
	}

	/**
	 * 删除流程日志
	 * @param id 日志id
	 */
	@Transactional
	public void deleteBpmLogById(int id) {
		bpmLogDao.deleteBpmLogById(id);
	}

	/**
	 * 根据流程实例id批量删除流程日志
	 * @param taskId 流程taskId
	 */
	@Transactional
	public void deleteBpmLogByTaskId(int taskId) {
		bpmLogDao.deleteBpmLogByTaskId(taskId);
	}

	/**
	 * 获取某状态的日志列表
	 * @param map map(key: taskId, state, bizKey, processName)
	 * @return 任务列表
	 */
	public List<BpmLog> getBpmLog(Map<String, Object> map) {
		return bpmLogDao.getBpmLog(map);
	}
	
	/**
	 * 获取某状态的日志列表,没有倒叙排列
	 * @param map map(key: taskId, state, bizKey, processName)
	 * @return 任务列表
	 */
	public List<BpmLog> getBpmLogNoDesc(Map<String, Object> map) {
		List<BpmLog> list = bpmLogDao.getBpmLog(map);
		Collections.reverse(list);
		return list;
	}

	/**
	 * 获取某流程实例的最后一个日志
	 * @param map map(key: taskId, state, bizKey, processName)
	 * @return 任务列表
	 */
	public BpmLog getLastBpmLog(Map<String, Object> map) {
		List<BpmLog> list = bpmLogDao.getBpmLog(map);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更新日志
	 * @param bpmLog 日志
	 */
	@Transactional
	public void updateBpmLog(BpmLog bpmLog) {
		bpmLogDao.updateBpmLog(bpmLog);
	}

	/**
	 * 更新日志
	 * @param map 日志
	 */
	@Transactional
	public void updateBpmLogMap(Map<String, Object> map) {
		bpmLogDao.updateBpmLogMap(map);
	}

}
