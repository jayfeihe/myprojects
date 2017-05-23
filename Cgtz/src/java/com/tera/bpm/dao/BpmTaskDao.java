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

import com.tera.bpm.model.BpmTask;

/**
 * @author Administrator
 */
@Repository
public interface BpmTaskDao {

	/**
	 * 根据流程定义ID，创建流程实例
	 * @param task task
	 */
	void addBpmTask(BpmTask task);
	/**
	 * 根据流程id获取流程实例
	 * @param id 流程id
	 * @return 启动的流程
	 */
	BpmTask getBpmTaskById(long id);

	/**
	 * 更新流程实例
	 * @param task 流程实例
	 */
	void updateBpmTask(BpmTask task);

	/**
	 * 删除流程实例
	 * @param id 流程id
	 */
	void deleteBpmTaskById(int id);

	/**
	 * 根据流程定义id批量删除流程实例
	 * @param defId 流程defId
	 */
	void deleteBpmTaskByDefId(int defId);

	/**
	 * 根据事故号获取流程实例列表
	 * @param bizKey 外部引用ID
	 * @return 启动的流程
	 */
	List<BpmTask> getBpmTaskByBizKey(String bizKey);

	/**
	 * 获取用户的任务列表
	 * @param user 用户
	 * @return 任务列表
	 */
	List<BpmTask> getBpmTaskByUser(String user);

	/**
	 * 获取用户的某状态的任务列表
	 * @param map map(key:id, user, state, bizKey, processName)
	 * @return 任务列表
	 */
	List<BpmTask> getBpmTask(Map<String, Object> map);
	
	
	/**
	 * 查询待处理人为  null 或  '' 的流程实例列表
	 * @param map
	 * 			key
	 * 				states
	 * 				bizKey
	 * 				processName
	 * @return
	 */
	List<BpmTask> getBpmTaskPool(Map<String, Object> map);
	
	
	List<BpmTask> queryTaskNum(Map<String, Object> map);
	
	
	
}
