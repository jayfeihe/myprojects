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
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;

/**
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class ProcessServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ProcessServiceTest.class);

	/**
	 * UserDao
	 */
	@Autowired
	@Qualifier("processService")
	private ProcessService processService;
	
	@Autowired
	private UserService userService;

	@Test
	@Rollback(false)
	public void goNextTest() {
		BpmTask task = processService.getProcessInstanceById(9);
//		task.setVariable("logContent1", "不通过");
//		task.setVariable("logContent2", "不通过原因是：测试");
		//本次事件的实际操作人，即session里面的用户id
		task.setOperator("wallace");
		//根据当前机构编码和角色名称查询用户
		
		List<User> users = userService.getUserByOrgAndRoleAndDepart("860100010001", new String[]{"营业部经理"},null);
		if (users.size() > 0) {
			//随机获取一个用户
			User user = users.get(new Random().nextInt(users.size()));
			task = processService.goNext(task, "营业部经理审核", user.getLoginId());
		}
		log.info(task);
	}
	
	@Test
	@Rollback(false)
	public void goNextTest2() {
		BpmTask task = processService.getProcessInstanceById(9);
//		task.setVariable("logContent1", "不通过");
//		task.setVariable("logContent2", "不通过原因是：测试");
		//本次事件的实际操作人，即session里面的用户id
		task.setOperator("wallace");
		//根据当前机构编码和角色名称查询用户
		List<User> users = userService.getUserByOrgAndRoleAndDepart("86", new String[]{"总部审批人"},null);
		if (users.size() > 0) {
			//随机获取一个用户
			User user = users.get(new Random().nextInt(users.size()));
			task = processService.goNext(task, "总部审核", user.getLoginId());
		}
		log.info(task);
	}
	
	@Test
	@Rollback(false)
	public void goNextTestBack() {
		BpmTask task = processService.getProcessInstanceById(83);
		task.setVariable("logContent1", "不通过");
		task.setVariable("logContent2", "不通过原因是：测试");
		//本次事件的实际操作人，即session里面的用户id
		task.setOperator("wallace");
		//根据当前机构编码和角色名称查询用户

		List<User> users = userService.getUserByOrgAndRoleAndDepart("860100010001", new String[]{"风险专员"},null);
		if (users.size() > 0) {
			//随机获取一个用户
			User user = users.get(new Random().nextInt(users.size()));
			task = processService.goNext(task, "风险专员初核", user.getLoginId());
		}
		log.info(task);
	}

	@Test
	@Rollback(false)
	public void getRolesTest() {
		String processName = "抵押借款流程";
		List<String> roles = processService.getRoles(processName, "营业部经理审核");
		for (String role : roles) {
			log.info(role);
		}
	}

	/**
	 * addBpmDefineTest
	 */
	@Test
	@Rollback(false)
	public void startProcessInstanceByNameTest() {
		String processName = "并行分支流程";
		String bizKey = "C20150001";
		BpmTask task = processService.startProcessInstanceByName(processName, bizKey);
		log.info(task);
		task = processService.goNext(task, "录入申请", "admin");
		log.info(task);
	}

	@Test
	@Rollback(false)
	public void getProcessInstanceByIdTest() {
		BpmTask task = processService.getProcessInstanceById(2);
		log.info(task);
	}

	@Test
	@Rollback(false)
	public void getProcessInstanceByBizKeyTest() {
		List<BpmTask> list = processService.getProcessInstanceByBizKey("抵押借款流程","L860100010001201406060006");
		log.info(list.get(0));
	}

	/**
	 * deleteProcessDefineCascadeTest
	 */
	@Test
	@Rollback(false)
	public void deleteProcessDefineCascadeTest() {
		String processName1 = "测试流程";
		processService.deleteProcessDefineCascade(processName1);
	}

	/**
	 * getProcessHistoryLogTest
	 */
	@Test
	public void getProcessHistoryLogTest() {
		String processName1 = "调查流程";
		String incidentId = "9160020121019000266";
		BpmTask task = processService.startProcessInstanceByName(processName1, incidentId);
		List<BpmLog> list = processService.getProcessHistoryLog(task, "排除");
		for (BpmLog bpmLog : list) {
			System.out.println(bpmLog);
		}
	}
	
	@Test
	@Rollback(false)
	public void gateWayTest() throws Exception {
		String processName = "并行分支流程";
		String bizKey = "C20150006";
		BpmTask task = processService.startProcessInstanceByName(processName, bizKey);
		log.info(task);
		Thread.sleep(1000);
		task = processService.goNext(task, "录入申请", "admin");
		Thread.sleep(1000);
		task = processService.goNext(task, "审核", "admin");
		Thread.sleep(1000);
		task = processService.goNext(task, "并行审批开始", "admin");
		Thread.sleep(1000);
		log.info(task);
		String[] subBizKeys = new String[]{ bizKey+"-1",bizKey+"-2",bizKey+"-3"};
		String[] nextStates = new String[]{"审批1","审批2","审批3"};
		String[] nextUsers = new String[]{"admin审批1","admin审批2","admin审批3"};
		List<BpmTask> subTasks = processService.goNext(task, subBizKeys, nextStates, nextUsers);
		for (BpmTask bpmTask : subTasks) {
			System.out.println(bpmTask);
		}
		
		BpmTask task1 = processService.getProcessInstanceByBizKey(processName, bizKey+"-1").get(0);
		task1 = processService.goNext(task1, "审批1复核", "admin");
		Thread.sleep(1000);
		log.info(task1);
		task1 = processService.goNext(task1, "并行审批结束", "admin");
		Thread.sleep(1000);
		log.info(task1);
		
		BpmTask task3 = processService.getProcessInstanceByBizKey(processName, bizKey+"-3").get(0);
		task3 = processService.goNext(task3, "并行审批结束", "admin");
		Thread.sleep(1000);
		log.info(task3);
		
		BpmTask task2 = processService.getProcessInstanceByBizKey(processName, bizKey+"-2").get(0);
		task2 = processService.goNext(task2, "并行审批结束", "admin");
		Thread.sleep(1000);
		log.info(task2);
		
		task = processService.getProcessInstanceByBizKey(processName, bizKey).get(0);
		task = processService.goNext(task, "签约", "admin");
		processService.reAssignTask(task, "sysauto");
		Thread.sleep(1000);
		log.info(task);
		task = processService.goNext(task, "结束", "admin");
		log.info(task);
	}
	
	@Test
	@Rollback(false)
	public void processParameterTest() {
		String processName = "并行分支流程";
		String bizKey = "C20150006";
		
		BpmTask currentTask = processService.getProcessInstanceByBizKey(processName, bizKey+"-1").get(0);
		String instanceParamName = "instanceParam1";
		String instanceParamValue = "instanceParam1Value";
		processService.setProcessInstanceParameter(currentTask, instanceParamName, instanceParamValue);
		
		String paramName = "param1";
		String paramValue = "param1Value";
		processService.setProcessTaskParameter(currentTask, paramName, paramValue);
		
		List<String> instanceParamValues = 
				processService.getProcessInstanceParameter(currentTask, instanceParamName);
		
		for (String string : instanceParamValues) {
			System.out.println(string);
		}
		
		List<String> taskParamValues = 
				processService.getProcessTaskParameter(currentTask, paramName);
		
		for (String string : taskParamValues) {
			System.out.println(string);
		}
	}

}
