/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.service.ProcessService;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>ContractController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-20 14:25:57<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/bpm")
public class BpmController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(BpmController.class);
	
	/**
	 * ContractService
	 */
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	private UserService userService;
	
	
	/*@RequestMapping("/updateProcesser.do")
	public String updateProcesser(String bizKey,String roleName,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//系统登录用户
//		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		List<User> users = userService.getUserByRole(new String[]{roleName});

		
		//得到待处理人
		List<BpmTask> bpms=processService.getProcessInstanceByBizKey(bizKey);
		BpmTask bpmTask=bpms.get(0);
		//待处理人
		String processer =bpmTask.getProcesser();
		
		map.put("processer", processer);
		map.put("users", users);
		map.put("bizKey", bizKey);
		
		System.out.println(bizKey+"_"+roleName);
		
		log.info(thisMethodName+":end");
		return "bpm/userList";
	}*/
//	
//	@RequestMapping("/reAssignTask.do")
//	public void reAssignTask(String bizKey,String userLoginId,HttpServletRequest request,HttpServletResponse response) throws Exception {
//		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//		log.info(thisMethodName+":start");
//		response.setContentType("application/json;charset=UTF-8");
//		PrintWriter writer = response.getWriter();
//		//得到流程节点
//		List<BpmTask> bpms=processService.getProcessInstanceByBizKey(bizKey);
//		BpmTask bpmTask=bpms.get(0);
//		//更改流程 待处理人
//		processService.reAssignTask(bpmTask, userLoginId);
//		writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
//		writer.flush();
//		writer.close();
//		log.info(thisMethodName+":end");
//	}
	
	@RequestMapping("/getBpmLogs.do")
	public String bpmLogList(String bizKey,ModelMap map) {
		List<BpmLog> bpmLogs = this.processService.getProcessHistoryLogNoDesc(bizKey);
		map.put("bpmLogs", bpmLogs);
		return "bpm/bpmLogList";
	}
}
