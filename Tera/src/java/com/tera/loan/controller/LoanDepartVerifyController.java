/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.loan.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.service.ProcessService;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.form.LoanQBean;
import com.tera.loan.service.LoanAppService;
import com.tera.loan.service.LoanDepartVerifyService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * <br>
 * <b>功能：</b>DepartVerifyController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 17:47:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/departverify")
public class LoanDepartVerifyController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanDepartVerifyController.class);
	

	/**
	 * DepartVerifyService
	 */
	@Autowired(required=false) //自动注入
	private LoanDepartVerifyService<LoanApp> departVerifyService;
	@Autowired(required=false) //自动注入
	private LoanAppService<LoanApp> loanAppService;
	@Autowired(required=false)
	private ProcessService processService;
	/**
	 * 跳转到营业部审批的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String departVerifyQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "loan/departverify/loanDepartVerifyQuery";
		
	}

	/**
	 * 显示营业部审批的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String departVerifyList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
	
		//获取当前登陆用户Id
		String currentLoginId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		PageModel pm = new PageModel();
		LoanQBean bean =(LoanQBean) RequestUtils.getRequestBean(LoanQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		//-----------获得当前登陆用户被分配的审核任务列表----------------
		//1.首先获得当前流程实例
		//2.获得当前状态即当前节点
		//3.获得当前登陆用户
		//4.获得当前登陆用户的机构
		//-----------根据以上四个条件，查询关联信息，才是当前登陆用户的代办工作。
		/**
		 * 1、取得该流程实例的当前的state状态
		 * state="营业部经理审核" or"营业部经理复议审核"
		 * 存入
		 */
		queryMap.put("bpmStates",new String[]{"营业部经理审核","营业部经理复议审核"});
		/**
		 * 2、取得当前登陆的用户ID
		 * 存入Processer="当前用户"
		 */
		queryMap.put("userLoginId", currentLoginId);
		/**
		 * 3、获得当前登陆用户的机构
		 * 存入
		 */
		if(bean.getOrgId()==null||bean.getOrgId().equals("")){
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			String orgId=org.getOrgId();
			queryMap.put("orgId", orgId);			
		}
		/**
		 * 4、状态=正常
		 */
		queryMap.put("states", new String[]{"1","2"});
		
		int rowsCount = this.loanAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<LoanApp> departVerifyList = this.loanAppService.queryBpmLoanAppList(queryMap);
		pm.setData(departVerifyList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loan/departverify/loanDepartVerifyList";
	}

	

	/**
	 * 跳转到查看营业部审批申请详细信息的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String departVerifyRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApp bean = null;
		if (null != id && !"".equals(id)) {
			bean = this.departVerifyService.queryByKey(id);
		}

		
		String zhlx="申请复议";
		List<BpmLog> bpmLogs=processService.getProcessHistoryLog(bean.getAppId(), zhlx);
		if (bpmLogs.size()>0) {
			BpmLog bm= bpmLogs.get(0);
			String logContent=bm.getLogContent2();
			System.out.println("----------------------"+bm.getLogContent2());
			map.put("logContent", logContent);
		}
		

		map.put("bean", bean);
		map.put("id", id);
		map.put("state", bean.getState());
		
		log.info(thisMethodName+":end");
		return "loan/departverify/loanDepartVerifyRead";
	}
	
	
	/**
	 * 营业部审批
	 * @param id
	 * @param response
	 * @param request
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void loanDepartVarifySave(String subbpm,String auditResult,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			
			LoanApp bean =departVerifyService.queryByKey(request.getParameter("id"));
			//当前登陆的人的ID
			String currentLoginUserID=request.getSession().getAttribute(SysConstants.LOGIN_ID).toString();
			String auditText=request.getParameter("auditText");
			auditText=URLDecoder.decode(auditText, "UTF-8");
			
			//如果存在
			if (bean.getAppId() != null) {
				// 提交 走流程
				if("trueSubbpm".equals(subbpm)){
					this.departVerifyService.bpmNext(currentLoginUserID, auditText, auditResult, bean);

					writer.print(JsonUtil.object2json(new JsonMsg(true, "审核成功！")));
				}else{
					writer.print(JsonUtil.object2json(new JsonMsg(true, "保存成功！")));
				}
			}else{
				writer.print(JsonUtil.object2json(new JsonMsg(true, "保存失败，申请ID不存在")));
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		log.info(thisMethodName+":end");
	}
}
