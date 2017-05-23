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
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.constant.BpmConstants;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanApproval;
import com.tera.loan.model.form.LoanQBean;
import com.tera.loan.service.LoanAppService;
import com.tera.loan.service.LoanApprovalService;
import com.tera.loan.service.LoanCheckAgainService;
import com.tera.loan.service.LoanSpecialService;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.service.UserService;

/**
 * 
 * <br>
 * <b>功能：</b>LoanApprovalController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-09 15:36:08<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/checkagain")
public class LoanCheckAgainController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanApprovalController.class);
	
	/**
	 * LoanApprovalService
	 */
	@Autowired(required=false) //自动注入
	private LoanCheckAgainService loanCheckAgainService;
	@Autowired(required=false) //自动注入
	private LoanAppService<LoanApp> loanAppService;
	
	/**
	 * 跳转到借款申请审批表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String loanSpecialQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "loan/checkagain/loanCheckAgainQuery";
	}

	/**
	 * 显示借款申请审批表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String loanCheckAgainList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		PageModel pm = new PageModel();
		LoanQBean bean = (LoanQBean) RequestUtils.getRequestBean(LoanQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);

		queryMap.put("bpmStates", new String[]{"复核"});		//流程节点
		queryMap.put("userLoginId", loginId);	//待处理人 登录ID
		queryMap.put("states", new String[]{"1","2","3"});	//状态，正常(1),复核退回到风险初审再提到签约再到复核（此时状态时"3"）
		
		if(bean.getOrgId()==null||bean.getOrgId().equals("")){
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			String orgId=org.getOrgId();
			queryMap.put("orgId", orgId);			//当前机构
		}
		int rowsCount = this.loanAppService.queryBpmLoanAppCount(queryMap);	//查询总数
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<LoanApp> loanApprovalList = this.loanAppService.queryBpmLoanAppList(queryMap);//查询 分页集合
		pm.setData(loanApprovalList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loan/checkagain/loanCheckAgainList";
		
	}

	/**
	 * 跳转到更新借款申请审批表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String loanCheckAgainUpdate(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApproval bean = null;
		//如果存在
		if (null != appId && !"".equals(appId)) {
			bean  = this.loanCheckAgainService.queryByKey(appId);
		}
		map.put("bean", bean);
		map.put("appId", appId);
		log.info(thisMethodName+":end");
		return "loan/checkagain/loanCheckAgainUpdate";
	}

	/**
	 * 保存借款申请审批表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanCheckAgainSave(String subbpm,String auditResult,String denyToRole,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
	//public void loanCheckAgainSave(String approvalAmount,String subbpm,String auditResult,String denyToRole,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			LoanApproval bean = (LoanApproval) RequestUtils.getRequestBean(LoanApproval.class, request);
			Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			String loginid=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			String auditText=request.getParameter("auditText");
			auditText=URLDecoder.decode(auditText, "UTF-8");
			//如果存在
			if (bean.getAppId() != null) {
				/*LoanApproval gbean =this.loanCheckAgainService.queryByKey(bean.getAppId());
				if(gbean!=null){
					this.loanCheckAgainService.updateOnlyChanged(bean);
				}else{
					this.loanCheckAgainService.add(bean);
				}*/
				// 提交 走流程
				if("trueSubbpm".equals(subbpm)){
					//this.loanCheckAgainService.bpmNext(loginid, auditText,denyToRole, auditResult, bean,org,approvalAmount);
					this.loanCheckAgainService.bpmNext(loginid, auditText,denyToRole, auditResult, bean,org);
					writer.print(JsonUtil.object2json(new JsonMsg(true, "审核已通过！")));
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

	/**
	 * 删除借款申请审批表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void loanCheckAgainDelete(String[] appId, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.loanCheckAgainService.delete(appId);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看借款申请审批表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String loanCheckAgainRead(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApproval bean = null;
		// 如果存在
		if (null != appId && !"".equals(appId)) {
			bean = this.loanCheckAgainService.queryByKey(appId);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "loan/checkagain/loanCheckAgainRead";
	}
	
	

}
