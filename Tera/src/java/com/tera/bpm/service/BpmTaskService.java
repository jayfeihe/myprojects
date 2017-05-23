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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.dao.BpmTaskDao;
import com.tera.bpm.model.BpmTask;

@Service
public class BpmTaskService {
	
	/**
	 * bpmTaskMapper
	 */
	@Autowired(required=false)
	BpmTaskDao bpmTaskDao;
	
	/**
	 * 根据流程定义ID，创建流程实例
	 * @param task task
	 */
	public void addBpmTask(BpmTask task) {
		bpmTaskDao.addBpmTask(task);
	}
	/**
	 * 根据流程id获取流程实例
	 * @param id 流程id
	 * @return 启动的流程
	 */
	public BpmTask getBpmTaskById(int id) {
		return bpmTaskDao.getBpmTaskById(id);
	}

	/**
	 * 更新流程实例
	 * @param task 流程实例
	 */
	@Transactional
	public void updateBpmTask(BpmTask task) {
		bpmTaskDao.updateBpmTask(task);
	}

	/**
	 * 删除流程实例
	 * @param id 流程id
	 */
	@Transactional
	public void deleteBpmTaskById(int id) {
		bpmTaskDao.deleteBpmTaskById(id);
	}

	/**
	 * 根据流程定义id批量删除流程实例
	 * @param defId 流程defId
	 */
	@Transactional
	public void deleteBpmTaskByDefId(int defId) {
		bpmTaskDao.deleteBpmTaskByDefId(defId);
	}

	/**
	 * 根据事故号获取流程实例列表
	 * @param bizKey 外部引用ID
	 * @return 启动的流程
	 */
	public List<BpmTask> getBpmTaskByBizKey(String bizKey) {
		return bpmTaskDao.getBpmTaskByBizKey(bizKey);
	}

	/**
	 * 获取用户的任务列表
	 * @param user 用户
	 * @return 任务列表
	 */
	public List<BpmTask> getBpmTaskByUser(String user) {
		return bpmTaskDao.getBpmTaskByUser(user);
	}

	/**
	 * 获取用户的某状态的任务列表
	 * @param map map(key:id, user, state, bizKey, processName)
	 * @return 任务列表
	 */
	public List<BpmTask> getBpmTask(Map<String, Object> map) {
		return bpmTaskDao.getBpmTask(map);
	}
	
	
	/**
	 * 查询待处理人为  null 或  '' 的流程实例列表
	 * @param map
	 * 			key
	 * 				states
	 * 				bizKey
	 * 				processName
	 * @return
	 */
	public List<BpmTask> getBpmTaskPool(Map<String, Object> map) {
		return bpmTaskDao.getBpmTaskPool(map);
	}

}
