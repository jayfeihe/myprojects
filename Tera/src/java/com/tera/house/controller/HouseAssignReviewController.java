/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.house.controller;

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
import com.tera.house.constant.Constants;
import com.tera.house.model.HouseApp;
import com.tera.house.model.form.HouseQBean;
import com.tera.house.service.HouseAppService;
import com.tera.house.service.HouseContactService;
import com.tera.house.service.HouseExtService;
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
 * 信用贷款审批分单控制器
 * <b>功能：</b>HouseAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/house/assign/review")
public class HouseAssignReviewController  extends BaseController{

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(HouseAssignReviewController.class);
	
	/**
	 * houseAppService
	 */
	@Autowired(required=false) //自动注入
	private HouseAppService houseAppService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	HouseContactService houseContactService;
	@Autowired(required=false) //自动注入
	HouseExtService houseExtService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 跳转到审批分单的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String assignReviewQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "house/assign/review/assignQuery";
	}

	/**
	 * 显示查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String assignReviewList(HouseQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		queryMap.put("nonStates", new String[]{"0"}); //状态
		queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_REVIEW});//查询流程为复核
		String stateTask = qBean.getStateTask(); //1已分配，0未分配
		if ("1".equalsIgnoreCase(stateTask)) {
			//processer 		流程待处理人
			//nonProcesser 	非流程待处理人
			queryMap.put("nonProcesser", "");
			if("".equals(queryMap.get("processer"))){
				queryMap.put("processer",null);
			}
		} else if ("0".equalsIgnoreCase(stateTask)) {
			queryMap.put("processer", "");
		}
		//是不是查询方法换成
		int rowsCount = this.houseAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<HouseApp> houseAppList = this.houseAppService.queryBpmLoanAppList(queryMap);
		pm.setData(houseAppList);
		map.put("loginId", loginId);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "house/assign/review/assignList";
	}

	/**
	 * 更新分配数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/update.do")
	public void assignReviewUpdate(String[] appIds, String newprocesser, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
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
				if (bpms!=null && bpms.size()>0 && appId!=null) {
					String operator = null != bpms.get(0).getProcesser() ? bpms.get(0).getProcesser() : "";//上一处理人
					if(!"".equals(operator)){
						operator = "由" + operator;
					}
					this.sysLogService.addSysLog(new SysLog(ipAddress, user, org.getOrgId(), "复核分单", bpms.get(0).getBizKey(), operator + "复核分单给" + newprocesser));
					processService.reAssignTask(bpms.get(0), newprocesser);
				}
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "分配失败，请联系系统管理员")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

}
