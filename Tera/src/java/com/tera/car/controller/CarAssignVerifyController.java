/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.car.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.car.constant.Constants;
import com.tera.car.model.CarApp;
import com.tera.car.model.form.CarQBean;
import com.tera.car.service.CarAppService;
import com.tera.car.service.CarContactService;
import com.tera.car.service.CarExtService;
import com.tera.img.service.ImgService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.SysLogService;
import com.tera.util.JsonUtil;
import com.tera.util.NetUtils;
import com.tera.util.ObjectUtils;

/**
 * 
 * 信用贷款申请表控制器
 * <b>功能：</b>CarAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/car/assign/verify")
public class CarAssignVerifyController  extends BaseController{

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CarAssignVerifyController.class);
	
	/**
	 * carAppService
	 */
	@Autowired(required=false) //自动注入
	private CarAppService carAppService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	CarContactService carContactService;
	@Autowired(required=false) //自动注入
	CarExtService carExtService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 跳转到信用贷款申请表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String assignVerifyQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "car/assign/verify/assignQuery";
	}

	/**
	 * 显示信用贷款申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String assignVerifyList(CarQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
//		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
//		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if(queryMap.get("orgId")==null || "".equals(queryMap.get("orgId"))){
			queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		}
		//未分配 已分配 判断
		if("no".equals(qBean.getStateTask())){
			queryMap.put("processer", "");  	//未分配的时候  处理人 为  ""
		}else if("ok".equals(qBean.getStateTask())){
			queryMap.put("nonProcesser", "");	//已分配的时候  处理人不 为  ""
			if("".equals(queryMap.get("processer"))){
				queryMap.put("processer",null);
			}
		}
		queryMap.put("nonStates", new String[]{"0"}); //状态
		queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_VERIFY});//查询录入流程定义为录入申请的
		int rowsCount = this.carAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CarApp> carAppList = this.carAppService.queryBpmLoanAppList(queryMap);
		pm.setData(carAppList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "car/assign/verify/assignList";
	}

	/**
	 * 删除信用贷款申请表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/update.do")
	public void assignVerifyUpdates(String[] appIds, String newProcesser, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			String ipAddress = NetUtils.getIpAddr(request);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			for (String appId : appIds) {
				List<BpmTask> bpms=processService.getProcessInstanceByBizKey(appId);
				if(bpms!=null&&bpms.size()>0&&appId!=null){
					String operator = null != bpms.get(0).getProcesser() ? bpms.get(0).getProcesser() : "";//上一处理人
					if(!"".equals(operator)){
						operator = "由" + operator;
					}
					this.sysLogService.addSysLog(new SysLog(ipAddress, user, org.getOrgId(), "审核分单", bpms.get(0).getBizKey(), operator + "审核分单给" + newProcesser));
					processService.reAssignTask(bpms.get(0), newProcesser);
				}
			}
			
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "系统异常！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
}
