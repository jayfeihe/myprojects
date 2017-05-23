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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.dao.BpmDefineDao;
import com.tera.bpm.model.BpmAssignLog;
import com.tera.bpm.model.BpmDefine;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmParameter;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.model.XmlProcess;
import com.tera.bpm.model.XmlState;
import com.tera.bpm.model.XmlTransition;
import com.tera.util.ObjectUtils;

/**
 * @author Administrator
 */
@Service
public class ProcessService {

	/**
	 * 日志
	 */
	private static Logger log = Logger.getLogger(ProcessService.class);

	/**
	 * defineService
	 */
	@Autowired(required=false)
	BpmDefineDao bpmDefineService;

	/**
	 * bpmTaskService
	 */
	@Autowired(required=false)
	BpmTaskService bpmTaskService;

	/**
	 * bpmLogService
	 */
	@Autowired(required=false)
	BpmLogService bpmLogService;
	
	/**
	 * bpmParameterService
	 */
	@Autowired(required=false)
	BpmParameterService bpmParameterService;
	
	@Autowired(required=false)
	BpmAssignLogService bpmAssignLogService;

	/**
	 * 根据流程名称启动流程示例
	 * @param processName 流程名称
	 * @param bizKey 外部引用ID
	 * @return 启动的流程
	 */
	@Transactional
	public BpmTask startProcessInstanceByName(String processName, String bizKey) {
		if (null == processName || "".equals(processName)) {
			throw new IllegalArgumentException("ProcessServiceImpl.startProcessInstanceByName,流程名称为空！");
		}
		//判断当前业务号是否已经启动名称为processName的流程
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizKey", bizKey);
		map.put("processName", processName);
		List<BpmTask> taskList = bpmTaskService.getBpmTask(map);
		if (taskList.size() > 0) {
			log.info("ProcessServiceImpl.startProcessInstanceByName,外部引用ID：(" + bizKey + "),流程名称为(" + processName + ")的流程任务已经启动！");
			return taskList.get(0);
		}
		BpmDefine bpmDefine = bpmDefineService.getBpmDefineByName(processName);
		if (null == bpmDefine) {
			throw new IllegalArgumentException("ProcessServiceImpl.startProcessInstanceByName,流程在数据库中未定义！");
		}
		BpmTask startTask = new BpmTask();
		//根据BpmDefine查找初始化BpmFactory中的流程定义
		XmlProcess process = BpmFactory.getBpmInstance(bpmDefine);
		String startName = process.getStartStateName();
		//开始流程节点
		startTask.setDefId(bpmDefine.getId());
		startTask.setBizKey(bizKey);
		startTask.setParentBizKey(bizKey);//付任务的bizKey和子任务的bizKey相同
		startTask.setState(startName);
		startTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		startTask.setProcesser(BpmConstants.SYSTEM_SYS);
		bpmTaskService.addBpmTask(startTask);
		//startTask和数据库同步
		startTask = bpmTaskService.getBpmTaskById(startTask.getId());
		log.info("ProcessServiceImpl.startProcessInstanceByName,流程开始插入流程任务！");
		//插入流程日志
		BpmLog bpmLog = ProcessService.getBpmLog(startTask);
		bpmLogService.addBpmLog(bpmLog);
		log.info(bpmLog);
		log.info("ProcessServiceImpl.startProcessInstanceByName,流程开始插入流程日志！");
		return startTask;
	}

	/**
	 * 根据流程id获取流程实例
	 * @param id 流程id
	 * @return 启动的流程
	 */
	public BpmTask getProcessInstanceById(int id) {
		return bpmTaskService.getBpmTaskById(id);
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
		return bpmTaskService.getBpmTask(map);
	}

	/**
	 * 根据业务号获取流程实例列表
	 * @param bizKey 外部引用ID
	 * @return 启动的流程列表
	 */
	public List<BpmTask> getProcessInstanceByBizKey(String bizKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizKey", bizKey);
		return bpmTaskService.getBpmTask(map);
	}

	/**
	 * 流程任务重新分配
	 * @param currentTask  流程实例
	 * @param nextUser 分配用户
	 */
	@Transactional
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
		currentTask = bpmTaskService.getBpmTaskById(currentTask.getId());
//		if (null != nextUser && !"".equals(nextUser)) {
//			currentTask.setProcesser(nextUser);
//			bpmTaskDao.updateBpmTask(currentTask);
//		}
		String lastUser=currentTask.getProcesser();
		currentTask.setProcesser(nextUser);  //不判断传入的用户
		bpmTaskService.updateBpmTask(currentTask);
		//更改log中的处理人
		Map<String, Object> mapQ = new HashMap<String, Object>();
		mapQ.put("taskId", currentTask.getId());
		BpmLog lastBpmLog = bpmLogService.getLastBpmLog(mapQ);
		lastBpmLog.setProcesser(nextUser);
//		lastBpmLog.setLogContent10("更换当前处理人："+nextUser+"代替"+lastUser);
		bpmLogService.updateBpmLog(lastBpmLog);
		//记录任务分配日志
		BpmAssignLog assignLog = new BpmAssignLog();
		assignLog.setDefId (currentTask.getDefId());
		assignLog.setTaskId (currentTask.getId());
		assignLog.setBizKey (currentTask.getBizKey());
		assignLog.setState (currentTask.getState());
		assignLog.setFromProcesser(lastUser);
		assignLog.setToProcesser(nextUser);
		assignLog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		bpmAssignLogService.add(assignLog);
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
		return bpmTaskService.getBpmTask(map);
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
		return bpmTaskService.getBpmTask(map);
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
		BpmDefine bpmDefine = bpmDefineService.getBpmDefineByName(processName);
		if (null == bpmDefine) {
			throw new IllegalArgumentException("ProcessServiceImpl.getRoles,流程在数据库中未定义！");
		}
		//根据BpmDefine查找初始化BpmFactory中的流程定义
		XmlProcess process = BpmFactory.getBpmInstance(bpmDefine);
		List<String> roles = process.getStateRoles(stateName);
		return roles;
	}

	/**
	 * 当前任务跳转到下一个状态，并写日志
	 * @param currentTask 当前任务
	 * @param nextUser 下一状态的用户
	 * @return 当前任务的下一个状态
	 */
	@Transactional
	public BpmTask goNext(BpmTask currentTask, String nextUser) {
		return goNext(currentTask, null, nextUser);
	}

	/**
	 * 当前任务跳转到下一个指定的状态，并写日志
	 * @param currentTask 当前任务
	 * @param nextState 下一个指定状态
	 * @param nextUser 下一状态的用户
	 * @return 当前任务的下一个指定状态
	 */
	@Transactional
	public BpmTask goNext(BpmTask currentTask, String nextState, String nextUser) {
		if (null == currentTask) {
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
		BpmLog lastBpmLog = bpmLogService.getLastBpmLog(mapQ);
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
		bpmLogService.updateBpmLogMap(lastLogMap);
		log.info("ProcessServiceImpl.goNext,更新流程日志(" + lastBpmLog.getState() + ")成功！");

		//准备跳转节点数据
		BpmDefine bpmDefine = getProcessDefine(currentTask);
		XmlProcess process = BpmFactory.getBpmInstance(bpmDefine);
		List<String> transitionNames = process.getTransitionStates(currentTask.getState());
		//判断有没有跳转节点
		if (transitionNames == null) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程任务没有跳转节点！");
		}
		if(transitionNames.size() > 1) {//多个跳转节点
			boolean hasState = transitionNames.contains(nextState);
			if (!hasState) {
				throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程定义中没有(" + nextState + ")节点！");
			}
			currentTask.setState(nextState);
		} else {//唯一跳转节点
			currentTask.setState(transitionNames.get(0));
		}
//		if (null != nextUser && !"".equals(nextUser)) {
//			currentTask.setProcesser(nextUser);
//		}
		currentTask.setProcesser(nextUser); //不判断传入的用户
		XmlState next = process.getXmlState(currentTask.getState());//获取下一步的state
		//主任务跳转和非汇聚的子任务跳转逻辑一样
		if (!currentTask.isSubTask() || !"gateway".equalsIgnoreCase(next.getState())) {
			//判断主任务下一节点是否结束
			if(isProcessInstanceEnded(currentTask)) {
				currentTask.setEndFlag("1");
			}
			currentTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			bpmTaskService.updateBpmTask(currentTask);
			log.info("ProcessServiceImpl.goNext,跳转到(" + nextState + ")更新流程任务成功！");
			//插入流程日志
			BpmLog bpmLog = ProcessService.getBpmLog(currentTask);
			//清空log的实际操作人
			bpmLog.setOperator("");
			Map<String, Object> map = new HashMap<String, Object>();
			map = ObjectUtils.describe(bpmLog);
			bpmLogService.addBpmLogMap(map);
		}
		//子任务的gateway汇聚必须等所有的子任务都走完才能结束,单个任务到gateway时需要记日志
		if(currentTask.isSubTask() && "gateway".equalsIgnoreCase(next.getState())) {
			//子任务的下一个跳转是gateway则子任务结束
			currentTask.setEndFlag("1");
			currentTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			bpmTaskService.updateBpmTask(currentTask);
			log.info("ProcessServiceImpl.goNext,子任务跳转到(" + nextState + ")更新流程任务成功！");
			//插入流程日志
			BpmLog bpmLog = ProcessService.getBpmLog(currentTask);
			//清空log的实际操作人
			bpmLog.setOperator("");
			Map<String, Object> map = new HashMap<String, Object>();
			map = ObjectUtils.describe(bpmLog);
			bpmLogService.addBpmLogMap(map);
			//如果同级子任务都结束，主任务跳转到gateway
			//获取当前子任务的同级任务
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("processName", currentTask.getProcessName());
			map1.put("parentId", currentTask.getParentId());
			List<BpmTask> subTasks = bpmTaskService.getBpmTask(map1);
			boolean isFinish = true;//子流程全部结束标志
			for (BpmTask bpmTask : subTasks) {
				if(bpmTask.getEndFlag().equals("0")) {
					isFinish = false;
				}
			}
			//子流程全部结束
			if(isFinish) {
				//主任务跳转到gateway
				BpmTask parentTask = getProcessInstanceById(currentTask.getParentId());
				parentTask.setState(currentTask.getState());
				parentTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				bpmTaskService.updateBpmTask(parentTask);
				//插入流程日志
				BpmLog parentTaskBpmLog = ProcessService.getBpmLog(parentTask);
				//log的实际操作人
				parentTaskBpmLog.setOperator(BpmConstants.SYSTEM_SYS);
				Map<String, Object> pmap = new HashMap<String, Object>();
				pmap = ObjectUtils.describe(parentTaskBpmLog);
				bpmLogService.addBpmLogMap(pmap);
				log.info("ProcessServiceImpl.goNext,主任务跳转到(" + currentTask.getState() + ")更新流程任务成功！");
			}
		}
		//清除Task的流程变量
		currentTask.clearVariables();
		//清空Task的实际操作人
		currentTask.setOperator("");
		return currentTask;
	}
	
	/**
	 * 当前任务跳转到下一个gateway指定的状态，并写日志
	 * @param currentTask 当前任务
	 * @param subBizKeys 下一个流程的外部引用ID数组，通常和父节点的外部引用ID相同
	 * @param nextStates 下一个指定状态数组
	 * @param nextUsers 下一状态的用户数组
	 * @return 当前任务的下一个指定状态
	 */
	@Transactional
	public List<BpmTask> goNext(BpmTask currentTask, String[] subBizKeys, String[] nextStates, String[] nextUsers) {
		if (null == currentTask || null == nextStates) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,参数错误！");
		}
		if (null != nextUsers && (nextStates.length != nextUsers.length)) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程结点和用户个数不相等！");
		}
		if (null != subBizKeys && (subBizKeys.length != nextStates.length)) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程结点和外部引用ID个数不相等！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,任务对象没有和数据库同步！");
		}
		//判断当前节点是否是gateway
		BpmDefine bpmDefine = getProcessDefine(currentTask);
		XmlProcess process = BpmFactory.getBpmInstance(bpmDefine);
		XmlState state = process.getXmlState(currentTask.getState());
		if (!"gateway".equalsIgnoreCase(state.getState())) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,当前流程节点不是gateway！");
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
		BpmLog lastBpmLog = bpmLogService.getLastBpmLog(mapQ);
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
		bpmLogService.updateBpmLogMap(lastLogMap);
		log.info("ProcessServiceImpl.goNext,更新流程日志(" + lastBpmLog.getState() + ")成功！");

		//准备跳转节点数据
		List<XmlTransition> transitons = state.getTransitions();
		//判断有没有跳转节点
		if (transitons == null) {
			throw new IllegalArgumentException("ProcessServiceImpl.goNext,流程任务没有跳转节点！");
		}
		List<BpmTask> subTasks = new ArrayList<BpmTask>();
		for (XmlTransition transition : transitons) {
			String to = transition.getTo();
			for (int i = 0; i < nextStates.length; i++) {
				String nextState = nextStates[i];
				if(to.equalsIgnoreCase(nextState)) {
					BpmTask subTask = new BpmTask();
					subTask.setDefId(currentTask.getDefId());
					subTask.setParentBizKey(currentTask.getBizKey());
					subTask.setState(nextState);
					if(null != subBizKeys) {
						subTask.setBizKey(subBizKeys[i]);
					}
					if (null != nextUsers) {
						subTask.setProcesser(nextUsers[i]);
					}
					subTask.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					subTask.setParentId(currentTask.getId());
					bpmTaskService.addBpmTask(subTask);
					subTask = bpmTaskService.getBpmTaskById(subTask.getId());
					//插入流程日志
					bpmLogService.addBpmLog(ProcessService.getBpmLog(subTask));
					subTasks.add(subTask);
					log.info("ProcessServiceImpl.goNext,子任务跳转到(" + nextState + ")更新流程任务成功！");
				}
			}
		}
		//清除Task的流程变量
		currentTask.clearVariables();
		//清空Task的实际操作人
		currentTask.setOperator("");
		return subTasks;
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
		BpmLog lastBpmLog = bpmLogService.getLastBpmLog(mapQ);
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
		bpmLogService.updateBpmLogMap(lastLogMap);
		log.info("ProcessServiceImpl.goNext,更新流程日志(" + lastBpmLog.getState() + ")成功！");

		//准备跳转节点数据
		BpmDefine bpmDefine = getProcessDefine(currentTask);
		XmlProcess process = BpmFactory.getBpmInstance(bpmDefine);
		List<String> allStats = process.getAllStates();
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
		bpmTaskService.updateBpmTask(currentTask);
		log.info("ProcessServiceImpl.goNext,跳转到(" + nextState + ")更新流程任务成功！");
		//插入流程日志
		BpmLog bpmLog = ProcessService.getBpmLog(currentTask);
		//清空log的实际操作人
		bpmLog.setOperator("");
		Map<String, Object> map = new HashMap<String, Object>();
		map = ObjectUtils.describe(bpmLog);
		bpmLogService.addBpmLogMap(map);
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
		XmlProcess process = BpmFactory.getBpmInstance(bpmDefine);
		List<String> endStates = process.getEndStateNames();
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
		BpmDefine bpmDefine = bpmDefineService.getBpmDefineById(currentTask.getDefId());
		//根据BpmDefine查找初始化BpmFactory中的流程定义
		BpmFactory.getBpmInstance(bpmDefine);
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
		bpmDefineService.deleteBpmDefineByName(processName);
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
		List<BpmLog>  list = bpmLogService.getBpmLog(map);
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
		List<BpmLog>  list = bpmLogService.getBpmLog(map);
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
		List<BpmLog>  list = bpmLogService.getBpmLog(map);
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
		List<BpmLog>  list = bpmLogService.getBpmLog(map);
		return list;
	}
	
	/**
	 * 设置流程参数
	 * @param currentTask
	 * @param paramName
	 * @param paramValue
	 */
	@Transactional
	public void setProcessInstanceParameter(BpmTask currentTask, String paramName,String paramValue) {
		if (null == currentTask) {
			throw new IllegalArgumentException("ProcessServiceImpl.setBpmParameter,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.getHistoryProcessLog,任务对象没有和数据库同步！");
		}
		BpmParameter parameter = new BpmParameter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", currentTask.getId());
		map.put("paramName", paramName);
		map.put("type", "1");//1,流程实例变量；2,流程节点变量
		map.put("bizKey", currentTask.getBizKey());
		List<BpmParameter>  list = bpmParameterService.getBpmParameter(map);
		if(list != null && list.size() > 0) {
			parameter = list.get(0);
			parameter.setParamValue(paramValue);
			bpmParameterService.update(parameter);
		} else {
			parameter = new BpmParameter();
			parameter.setType("1");
			parameter.setDefId(currentTask.getDefId());
			parameter.setTaskId(currentTask.getId());
			parameter.setBizKey(currentTask.getBizKey());
			parameter.setParamName(paramName);
			parameter.setParamValue(paramValue);
			bpmParameterService.add(parameter);
		}
	}
	
	/**
	 * 设置流程参数
	 * @param currentTask
	 * @param paramName
	 * @param paramValue
	 */
	@Transactional
	public void setProcessTaskParameter(BpmTask currentTask, String paramName,String paramValue) {
		if (null == currentTask) {
			throw new IllegalArgumentException("ProcessServiceImpl.setBpmParameter,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.getHistoryProcessLog,任务对象没有和数据库同步！");
		}
		BpmParameter parameter = new BpmParameter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", currentTask.getId());
		map.put("paramName", paramName);
		map.put("type", "2");//1,流程实例变量；2,流程节点变量
		map.put("bizKey", currentTask.getBizKey());
		map.put("state", currentTask.getState());
		List<BpmParameter>  list = bpmParameterService.getBpmParameter(map);
		if(list != null && list.size() > 0) {
			parameter = list.get(0);
			parameter.setParamValue(paramValue);
			bpmParameterService.update(parameter);
		} else {
			parameter = new BpmParameter();
			parameter.setType("2");
			parameter.setDefId(currentTask.getDefId());
			parameter.setTaskId(currentTask.getId());
			parameter.setBizKey(currentTask.getBizKey());
			parameter.setState(currentTask.getState());
			parameter.setParamName(paramName);
			parameter.setParamValue(paramValue);
			bpmParameterService.add(parameter);
		}
	}
	
	/**
	 * 获取流程实例参数
	 * @param currentTask
	 * @param paramName
	 * @return
	 */
	public List<String> getProcessInstanceParameter(BpmTask currentTask, String paramName) {
		if (null == currentTask) {
			throw new IllegalArgumentException("ProcessServiceImpl.setBpmParameter,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.getHistoryProcessLog,任务对象没有和数据库同步！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", currentTask.getId());
		map.put("type", "1");//1,流程实例变量；2,流程节点变量
		map.put("bizKey", currentTask.getBizKey());
		map.put("paramName", paramName);
		List<BpmParameter>  list = bpmParameterService.getBpmParameter(map);
		List<String> values = new ArrayList<String>();
		for (BpmParameter parameter : list) {
			values.add(parameter.getParamValue());
		}
		return values;
	}
	
	/**
	 * 获取流程节点参数
	 * @param currentTask
	 * @param paramName
	 * @return
	 */
	public List<String> getProcessTaskParameter(BpmTask currentTask, String paramName) {
		if (null == currentTask) {
			throw new IllegalArgumentException("ProcessServiceImpl.setBpmParameter,参数错误！");
		}
		if (currentTask.getId() == 0 || currentTask.getDefId() == 0) {
			throw new IllegalArgumentException("ProcessServiceImpl.getHistoryProcessLog,任务对象没有和数据库同步！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", currentTask.getId());
		map.put("state", currentTask.getState());
		map.put("type", "2");//1,流程实例变量；2,流程节点变量
		map.put("bizKey", currentTask.getBizKey());
		map.put("paramName", paramName);
		List<BpmParameter>  list = bpmParameterService.getBpmParameter(map);
		List<String> values = new ArrayList<String>();
		for (BpmParameter parameter : list) {
			values.add(parameter.getParamValue());
		}
		return values;
	}
	
	/**
	 * 根据任务，获取任务的流程日志
	 * @param task task
	 * @return BpmLog
	 */
	public static BpmLog getBpmLog(BpmTask task) {
		if (null == task) {
			throw new IllegalArgumentException("getBpmLog,任务对象为空！");
		}
		if (task.getId() == 0) {
			throw new IllegalArgumentException("getBpmLog,任务对象没有和数据库同步！");
		}
		BpmLog bpmLog = new BpmLog();
		bpmLog.setTaskId(task.getId());
		bpmLog.setBizKey(task.getBizKey());
		bpmLog.setParentBizKey(task.getParentBizKey());
		bpmLog.setState(task.getState());
		bpmLog.setProcesser(task.getProcesser());
		bpmLog.setOperator(task.getOperator());
		bpmLog.setIntime(new Timestamp(System.currentTimeMillis()));
		return bpmLog;
	}


}
