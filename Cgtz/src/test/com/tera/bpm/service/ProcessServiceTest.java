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
		
/*		List<User> users = userService.getUserByRole( new String[]{"营业部经理"});
		if (users.size() > 0) {
			//随机获取一个用户
			User user = users.get(new Random().nextInt(users.size()));
			task = processService.goNext(task, "营业部经理审核", user.getLoginId());
		}
		log.info(task);*/
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
		/*List<User> users = userService.getUserByRole( new String[]{"总部审批人"});
		if (users.size() > 0) {
			//随机获取一个用户
			User user = users.get(new Random().nextInt(users.size()));
			task = processService.goNext(task, "总部审核", user.getLoginId());
		}
		log.info(task);*/
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

		/*List<User> users = userService.getUserByRole( new String[]{"风险专员"});
		if (users.size() > 0) {
			//随机获取一个用户
			User user = users.get(new Random().nextInt(users.size()));
			task = processService.goNext(task, "风险专员初核", user.getLoginId());
		}
		log.info(task);*/
	}

	@Test
	@Rollback(false)
	public void getRolesTest() {
		String processName = "HD抵押借款流程";
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
		String processName = "HD抵押借款流程";
		String bizKey = "B860100010001201406060011";
		BpmTask task = processService.startProcessInstanceByName(processName, bizKey,"");
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
		List<BpmTask> list = processService.getProcessInstanceByBizKey("HD抵押借款流程","L860100010001201406060006");
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
		BpmTask task = processService.startProcessInstanceByName(processName1, incidentId,"");
		List<BpmLog> list = processService.getProcessHistoryLog(task, "排除");
		for (BpmLog bpmLog : list) {
			System.out.println(bpmLog);
		}
	}
}
