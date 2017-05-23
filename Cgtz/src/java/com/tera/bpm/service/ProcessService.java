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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.dao.BpmDefineDao;
import com.tera.bpm.dao.BpmLogDao;
import com.tera.bpm.dao.BpmTaskDao;
import com.tera.bpm.model.BpmDefine;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.util.ObjectUtils;

/**
 * @author Administrator
 */
/**
 * @author Administrator
 *
 */
@Service
public class ProcessService {

	/**
	 * 日志
	 */
	private static Logger log = Logger.getLogger(ProcessService.class);

	/**
	 * defineDaoMapper
	 */
	@Autowired
	BpmDefineDao bpmDefineDao;

	/**
	 * bpmTaskMapper
	 */
	@Autowired
	BpmTaskDao bpmTaskDao;

	/**
	 * bpmLogMapper
	 */
	@Autowired
	BpmLogDao bpmLogDao;

	/**
	 * 根据流程名称启动流程示例
	 * @param processName 流程名称
	 * @param bizKey 外部引用ID
	 * @return 启动的流程
	 */
	@Transactional
	public BpmTask startProcessInstanceByName(String processName, String bizKey,String processer) {
		if (null == processName || "".equals(processName)) {
			throw new IllegalArgumentException("ProcessServiceImpl.startProcessInstanceByName,流程名称为空！");
		}
		//判断当前业务号是否已经启动名称为processName的流程
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizKey", bizKey);
		map.put("processName", processName);
		List<BpmTask> taskList = bpmTaskDao.getBpmTask(map);
		if (taskList.size() > 0) {
			log.info("ProcessServiceImpl.startProcessInstanceByName,外部引用ID：(" + bizKey + "),流程名称为(" + processName + ")的流程任务已经启动！");
			return taskList.get(0);
		}
		BpmDefine bpmDefine = bpmDefineDao.getBpmDefineByName(processName);
		if (null == bpmDefine) {
			throw new IllegalArgumentException("ProcessServiceImpl.startProcessInstanceByName,流程在数据库中未定义！");
		}
		BpmTask startTask = new BpmTask();
		//根据BpmDefine查找初始化BpmFactory中的流程定义
		BpmFactory.initBpm(bpmDefine);
		String startName = BpmFactory.getStartStateName(processName);
		//开始流程节点
		startTask.setDefId(bpmDefine.getId());
		startTask.setBizKey(bizKey);
		startTask.setState(startName);
		startTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		startTask.setProcesser(processer);
		bpmTaskDao.addBpmTask(startTask);
		//startTask和数据库同步
		startTask = bpmTaskDao.getBpmTaskById(startTask.getId());
		log.info("ProcessServiceImpl.startProcessInstanceByName,流程开始插入流程任务！");
		//插入流程日志
		bpmLogDao.addBpmLog(BpmFactory.getBpmLog(startTask));
		log.info("ProcessServiceImpl.startProcessInstanceByName,流程开始插入流程日志！");
		return startTask;
	}

	/**
	 * 根据流程id获取流程实例
	 * @param id 流程id
	 * @return 启动的流程
	 */
	public BpmTask getProcessInstanceById(int id) {
		return bpmTaskDao.getBpmTaskById(id);
	}

	/**
	 * 根据业务号获取流程实例列表
	 * @param processName 流程名称
	 * @param bizKey 外部引用ID
	 * @return 启动的流程列表
	 */
	public List<BpmTask> getProcessInstanceByBizKey(String processName, String bizKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizKey", bizKey);
		map.put("processName", processName);
		return bpmTaskDao.getBpmTask(map);
	}
//
//	/**
//	 * 根据业务号获取流程实例列表
//	 * @param bizKey 外部引用ID
//	 * @return 启动的流程列表
//	 */
//	public List<BpmTask> getProcessInstanceByBizKey(String bizKey) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("bizKey", bizKey);
//		return bpmTaskDao.getBpmTask(map);
//	}

	/**
	 * 流程任务重新分配
	 * @param currentTask  流程实例
	 * @param nextUser 分配用户
	 */
	public void reAssignTask(BpmTask currentTask, String nextUser) {
		if (null == currentTask) {
			return;
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.reAssignTask,任务对象没有和数据库同步！");
		}

		//判断当前流程是否结束
		if (currentTask.getEndFlag().equals("1")) {
			log.info("ProcessServiceImpl.reAssignTask,流程任务已经结束！");
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程任务已经结束！");
		}
		//防止更新的时候有些字段没有值
		currentTask = bpmTaskDao.getBpmTaskById(currentTask.getId());
//		if (null != nextUser && !"".equals(nextUser)) {
//			currentTask.setProcesser(nextUser);
//			bpmTaskDao.updateBpmTask(currentTask);
//		}
		String lastUser=currentTask.getProcesser();
		currentTask.setProcesser(nextUser);  //不判断传入的用户
		bpmTaskDao.updateBpmTask(currentTask);
		//更改log中的处理人
		Map<String, Object> mapQ = new HashMap<String, Object>();
		mapQ.put("taskId", currentTask.getId());
		BpmLog lastBpmLog = bpmLogDao.getLastBpmLog(mapQ);
		lastBpmLog.setProcesser(nextUser);
		lastBpmLog.setLogContent3("更换当前处理人："+nextUser+"代替"+lastUser);
		bpmLogDao.updateBpmLog(lastBpmLog);
		}

	/**
	 * 获取用户的任务列表
	 * @param processName 流程名称
	 * @param user 用户
	 * @return 任务列表
	 */
	public List<BpmTask> getProcessInstanceByUser(String processName, String user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("processName", processName);
		return bpmTaskDao.getBpmTask(map);
	}

	/**
	 * 根据任务状态获取用户的任务列表
	 * @param processName 流程名称
	 * @param user 用户
	 * @param state 流程状态
	 * @return 任务列表
	 */
	public List<BpmTask> getProcessInstanceByUser(String processName, String user, String state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("processName", processName);
		map.put("state", state);
		return bpmTaskDao.getBpmTask(map);
	}

	/**
	 * 根据流程名称，获取流程节点的角色
	 * @param processName processName
	 * @param stateName stateName
	 * @return 流程节点的角色列表
	 */
	public List<String> getRoles(String processName, String stateName) {
		if (null == processName || "".equals(processName)) {
			throw new IllegalArgumentException("ProcessServiceImpl.getRoles,流程名称为空！");
		}
		BpmDefine bpmDefine = bpmDefineDao.getBpmDefineByName(processName);
		if (null == bpmDefine) {
			throw new IllegalArgumentException("ProcessServiceImpl.getRoles,流程在数据库中未定义！");
		}
		//根据BpmDefine查找初始化BpmFactory中的流程定义
		BpmFactory.initBpm(bpmDefine);
		List<String> roles = BpmFactory.getStateRoles(processName, stateName);
		return roles;
	}

//	/**
//	 * 当前任务跳转到下一个状态，并写日志
//	 * @param currentTask 当前任务
//	 * @param nextUser 下一状态的用户
//	 * @return 当前任务的下一个状态
//	 */
//	@Transactional
//	public BpmTask goNext(BpmTask currentTask, String nextUser) {
//		if (null == currentTask) {
//			throw new IllegalArgumentException("ProcessServiceImpl.goNext,参数错误！");
//		}
//		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
//			throw new IllegalArgumentException("ProcessServiceImpl.goNext,任务对象没有和数据库同步！");
//		}
//		//判断当前流程是否结束
//		if (currentTask.getEndFlag().equals("1")) {
//			log.info("ProcessServiceImpl.goNext,流程任务已经结束！");
//			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程任务已经结束！");
//		}
//		//遵循：update上一个节点日志，跳转，记录下一步日志的顺序。
//		//如果先记录日志，再跳转，会出现流程节点参数会出现在上一个节点的问题；
//		//如果先跳转，再记录流程日志，会出现日志中当前用户错误的问题；
//
//		//update上一个节点日志
//		Map<String, Object> mapQ = new HashMap<String, Object>();
//		mapQ.put("taskId", currentTask.getId());
//		BpmLog lastBpmLog = bpmLogDao.getLastBpmLog(mapQ);
//		//记录实际用户,解决实际处理人和操作人不一致的问题
//		if (null == currentTask.getOperator() || "".equals(currentTask.getOperator())) {
//			lastBpmLog.setOperator(lastBpmLog.getProcesser());
//		} else {
//			lastBpmLog.setOperator(currentTask.getOperator());
//		}
//		lastBpmLog.setOuttime(new Timestamp(System.currentTimeMillis()));
//		Map<String, Object> lastLogMap = new HashMap<String, Object>();
//		lastLogMap = ObjectUtils.describe(lastBpmLog);
//		lastLogMap.putAll(currentTask.getVariables());
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("更新日志Map: [");
//		Iterator<String> iterator = lastLogMap.keySet().iterator();
//		while (iterator.hasNext()) {
//			String key = iterator.next();
//			Object value = lastLogMap.get(key);
//			buffer.append(key + ": ").append(value).append(", ");
//		}
//		buffer.append("]");
//		log.info(buffer.toString());
//		bpmLogDao.updateBpmLogMap(lastLogMap);
//		log.info("ProcessServiceImpl.goNext,更新流程日志(" + lastBpmLog.getState() + ")成功！");
//
//		//准备跳转节点数据
//		BpmDefine bpmDefine = getProcessDefine(currentTask);
//		List<String> transitionNames = BpmFactory.getTransitionStates(bpmDefine.getProcessName(),
//				currentTask.getState());
//		//判断当前流程是否结束
//		if (transitionNames == null) {
//			log.info("ProcessServiceImpl.goNext,流程任务已经结束！");
//			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程任务已经结束！");
//		}
//		currentTask.setState(transitionNames.get(0));
////		if (null != nextUser && !"".equals(nextUser)) {
////			currentTask.setProcesser(nextUser);
////		}
//		currentTask.setProcesser(nextUser); //不判断传入的用户
//		//判断下一节点是否结束
//		if (isProcessInstanceEnded(currentTask)) {
//			currentTask.setEndFlag("1");
//		}
//		currentTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//		bpmTaskDao.updateBpmTask(currentTask);
//		log.info("ProcessServiceImpl.goNext,跳转到(" + transitionNames.get(0) + ")更新流程任务成功！");
//		//插入流程日志
//		BpmLog bpmLog = BpmFactory.getBpmLog(currentTask);
//		//清空log的实际操作人
//		bpmLog.setOperator("");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map = ObjectUtils.describe(bpmLog);
//		bpmLogDao.addBpmLogMap(map);
//		//清除Task的流程变量
//		currentTask.clearVariables();
//		//清空Task的实际操作人
//		currentTask.setOperator("");
//		return currentTask;
//	}

	/**
	 * 当前任务跳转到下一个指定的状态，并写日志
	 * @param currentTask 当前任务
	 * @param nextState 下一个指定状态
	 * @param nextUser 下一状态的用户
	 * @return 当前任务的下一个指定状态
	 */
	@Transactional
	public BpmTask goNext(BpmTask currentTask, String nextState, String nextUser) {
		if (null == currentTask || null == nextState || "".equals(nextState)) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,任务对象没有和数据库同步！");
		}

		//判断当前流程是否结束
		if (currentTask.getEndFlag().equals("1")) {
			log.info("ProcessServiceImpl.goNext,流程任务已经结束！");
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程任务已经结束！");
		}
		//遵循：update上一个节点日志，跳转，记录下一步日志的顺序。
		//如果先记录日志，再跳转，会出现流程节点参数会出现在上一个节点的问题；
		//如果先跳转，再记录流程日志，会出现日志中当前用户错误的问题；

		//update上一个节点日志
		Map<String, Object> mapQ = new HashMap<String, Object>();
		mapQ.put("taskId", currentTask.getId());
		BpmLog lastBpmLog = bpmLogDao.getLastBpmLog(mapQ);
		//记录实际用户,解决实际处理人和操作人不一致的问题
		if (null == currentTask.getOperator() || "".equals(currentTask.getOperator())) {
			lastBpmLog.setOperator(lastBpmLog.getProcesser());
		} else {
			lastBpmLog.setOperator(currentTask.getOperator());
		}
		lastBpmLog.setDecision(currentTask.getDecision());
		lastBpmLog.setRemark(currentTask.getRemark());
		lastBpmLog.setOuttime(new Timestamp(System.currentTimeMillis()));
		Map<String, Object> lastLogMap = new HashMap<String, Object>();
		lastLogMap = ObjectUtils.describe(lastBpmLog);
		lastLogMap.putAll(currentTask.getVariables());
		StringBuffer buffer = new StringBuffer();
		buffer.append("更新日志Map: [");
		Iterator<String> iterator = lastLogMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = lastLogMap.get(key);
			buffer.append(key + ": ").append(value).append(", ");
		}
		buffer.append("]");
		log.info(buffer.toString());
		bpmLogDao.updateBpmLogMap(lastLogMap);
		log.info("ProcessServiceImpl.goNext,更新流程日志(" + lastBpmLog.getState() + ")成功！");

		//准备跳转节点数据
		BpmDefine bpmDefine = getProcessDefine(currentTask);
		List<String> transitionNames = BpmFactory.getTransitionStates(bpmDefine.getProcessName(),
				currentTask.getState());
		//判断当前流程是否结束
		if (transitionNames == null) {
			log.info("ProcessServiceImpl.goNext,流程任务已经结束！");
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程任务已经结束！");
		}
		boolean hasState = transitionNames.contains(nextState);
		if (!hasState) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程定义中没有(" + nextState + ")节点！");
		}
		currentTask.setState(nextState);
//		if (null != nextUser && !"".equals(nextUser)) {
//			currentTask.setProcesser(nextUser);
//		}
		currentTask.setProcesser(nextUser); //不判断传入的用户
		//判断下一节点是否结束
		if (isProcessInstanceEnded(currentTask)) {
			currentTask.setEndFlag("1");
		}
		currentTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		bpmTaskDao.updateBpmTask(currentTask);
		log.info("ProcessServiceImpl.goNext,跳转到(" + nextState + ")更新流程任务成功！");
		//插入流程日志
		BpmLog bpmLog = BpmFactory.getBpmLog(currentTask);
		//清空log的实际操作人
		bpmLog.setOperator("");
		Map<String, Object> map = new HashMap<String, Object>();
		map = ObjectUtils.describe(bpmLog);
		bpmLogDao.addBpmLogMap(map);
		//清除Task的流程变量
		currentTask.clearVariables();
		//清空Task的实际操作人
		currentTask.setOperator("");
		currentTask.setDecision("");
		currentTask.setRemark("");
		return currentTask;
	}

	/**
	 * 当前任务从任意状态跳转到下一个指定的状态，并写日志
	 * @param currentTask 当前任务
	 * @param nextState 下一个指定状态
	 * @param nextUser 下一状态的用户
	 * @return 当前任务的下一个指定状态
	 */
	@Transactional
	public BpmTask jumpNext(BpmTask currentTask, String nextState, String nextUser) {
		if (null == currentTask || null == nextState || "".equals(nextState)) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,任务对象没有和数据库同步！");
		}

		//判断当前流程是否结束
		if (currentTask.getEndFlag().equals("1")) {
			log.info("ProcessServiceImpl.goNext,流程任务已经结束！");
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程任务已经结束！");
		}
		//遵循：update上一个节点日志，跳转，记录下一步日志的顺序。
		//如果先记录日志，再跳转，会出现流程节点参数会出现在上一个节点的问题；
		//如果先跳转，再记录流程日志，会出现日志中当前用户错误的问题；

		//update上一个节点日志
		Map<String, Object> mapQ = new HashMap<String, Object>();
		mapQ.put("taskId", currentTask.getId());
		BpmLog lastBpmLog = bpmLogDao.getLastBpmLog(mapQ);
		//记录实际用户,解决实际处理人和操作人不一致的问题
		if (null == currentTask.getOperator() || "".equals(currentTask.getOperator())) {
			lastBpmLog.setOperator(lastBpmLog.getProcesser());
		} else {
			lastBpmLog.setOperator(currentTask.getOperator());
		}
		lastBpmLog.setOuttime(new Timestamp(System.currentTimeMillis()));
		Map<String, Object> lastLogMap = new HashMap<String, Object>();
		lastLogMap = ObjectUtils.describe(lastBpmLog);
		lastLogMap.putAll(currentTask.getVariables());
		StringBuffer buffer = new StringBuffer();
		buffer.append("更新日志Map: [");
		Iterator<String> iterator = lastLogMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = lastLogMap.get(key);
			buffer.append(key + ": ").append(value).append(", ");
		}
		buffer.append("]");
		log.info(buffer.toString());
		bpmLogDao.updateBpmLogMap(lastLogMap);
		log.info("ProcessServiceImpl.goNext,更新流程日志(" + lastBpmLog.getState() + ")成功！");

		//准备跳转节点数据
		BpmDefine bpmDefine = getProcessDefine(currentTask);
		List<String> allStats = BpmFactory.getAllStates(bpmDefine.getProcessName());
		boolean hasState = allStats.contains(nextState);
		if (!hasState) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程定义中没有(" + nextState + ")节点！");
		}
		currentTask.setState(nextState);
//		if (null != nextUser && !"".equals(nextUser)) {
//			currentTask.setProcesser(nextUser);
//		}
		currentTask.setProcesser(nextUser); //不判断传入的用户
		//判断下一节点是否结束
		if (isProcessInstanceEnded(currentTask)) {
			currentTask.setEndFlag("1");
		}
		currentTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		bpmTaskDao.updateBpmTask(currentTask);
		log.info("ProcessServiceImpl.goNext,跳转到(" + nextState + ")更新流程任务成功！");
		//插入流程日志
		BpmLog bpmLog = BpmFactory.getBpmLog(currentTask);
		//清空log的实际操作人
		bpmLog.setOperator("");
		Map<String, Object> map = new HashMap<String, Object>();
		map = ObjectUtils.describe(bpmLog);
		bpmLogDao.addBpmLogMap(map);
		//清除Task的流程变量
		currentTask.clearVariables();
		//清空Task的实际操作人
		currentTask.setOperator("");
		return currentTask;
	}

	/**
	 * 判断当前流程实例是否结束
	 * @param currentTask 流程实例
	 * @return 是否结束
	 */
	public boolean isProcessInstanceEnded(BpmTask currentTask) {
		if (null == currentTask) {
			throw new IllegalArgumentException("ProcessServiceImpl.isProcessInstanceEnded,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.isProcessInstanceEnded,任务对象没有和数据库同步！");
		}
		BpmDefine bpmDefine = getProcessDefine(currentTask);
		List<String> endStates = BpmFactory.getEndStateNames(bpmDefine.getProcessName());
		for (String state : endStates) {
			if (state.equals(currentTask.getState())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据流程实例，获取流程定义
	 * @param currentTask 流程实例
	 * @return 流程定义
	 */
	public BpmDefine getProcessDefine(BpmTask currentTask) {
		if (null == currentTask) {
			throw new IllegalArgumentException("ProcessServiceImpl.getProcessDefine,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.getProcessDefine,任务对象没有和数据库同步！");
		}
		BpmDefine bpmDefine = bpmDefineDao.getBpmDefineById(currentTask.getDefId());
		//根据BpmDefine查找初始化BpmFactory中的流程定义
		BpmFactory.initBpm(bpmDefine);
		return bpmDefine;
	}

	/**
	 * 根据流程名称级联删除流程定义
	 * @param processName 流程名称
	 */
	@Transactional
	public void deleteProcessDefineCascade(String processName) {
		if (null == processName || "".equals(processName)) {
			return;
		}
		bpmDefineDao.deleteBpmDefineByName(processName);
	}

	/**
	 * 根据流程实例获取流程在某个状态的日志列表
	 * @param currentTask 流程实例
	 * @param state 状态
	 * @return 日志列表
	 */
	public List<BpmLog> getProcessHistoryLog(BpmTask currentTask, String state) {
		if (null == currentTask) {
			throw new IllegalArgumentException("ProcessServiceImpl.getHistoryProcessLog,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.getHistoryProcessLog,任务对象没有和数据库同步！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", currentTask.getId());
		map.put("state", state);
		List<BpmLog>  list = bpmLogDao.getBpmLog(map);
		return list;
	}

	/**
	 * 根据流程实例获取流程日志列表
	 * @param currentTask 流程实例
	 * @return 日志列表
	 */
	public List<BpmLog> getProcessHistoryLog(BpmTask currentTask) {
		if (null == currentTask) {
			throw new IllegalArgumentException("ProcessServiceImpl.getHistoryProcessLog,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.getHistoryProcessLog,任务对象没有和数据库同步！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", currentTask.getId());
		List<BpmLog>  list = bpmLogDao.getBpmLog(map);
		return list;
	}

	/**
	 * 根据业务号获取流程日志列表
	 * @param bizKey 外部引用ID
	 * @return 流程日志列表
	 */
	public List<BpmLog> getProcessHistoryLog(String bizKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizKey", bizKey);
		List<BpmLog>  list = bpmLogDao.getBpmLog(map);
		return list;
	}
	
	/**
	 * 根据业务号获取流程日志列表
	 * @param bizKey 外部引用ID
	 * @return 流程日志列表
	 */
	public List<BpmLog> getProcessHistoryLogNoDesc(String bizKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizKey", bizKey);
		List<BpmLog>  list = bpmLogDao.getBpmLogNoDesc(map);
		return list;
	}
	
	/**
	 * 根据业务号获取流程日志列表
	 * @param bizKey 外部引用ID
	 * @param state 状态
	 * @return 流程日志列表
	 */
	public List<BpmLog> getProcessHistoryLog(String bizKey, String state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizKey", bizKey);
		map.put("state", state);
		List<BpmLog>  list = bpmLogDao.getBpmLog(map);
		return list;
	}
/**
 * 参数：流程名称，节点状态。统计人员的任务量。
 * @param map
 * @return
 */
	public	List<BpmTask> queryTaskNum(Map<String, Object> map){
		return bpmTaskDao.queryTaskNum(map);
	}
	
}
