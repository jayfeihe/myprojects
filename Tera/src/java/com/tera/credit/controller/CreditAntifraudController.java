/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.credit.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.service.ProcessService;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditAntifraud;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.model.form.ApprovalFBean;
import com.tera.credit.model.form.CreditQBean;
import com.tera.credit.service.CreditAntifraudService;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditContactService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.credit.service.CreditExtService;
import com.tera.img.service.ImgService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 信用贷款反欺诈表控制器
 * <b>功能：</b>CreditAntifraudController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:07:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/credit/antifraud")
public class CreditAntifraudController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CreditAntifraudController.class);
	
	/**
	 * CreditAntifraudService
	 */
	@Autowired(required=false) //自动注入
	CreditAntifraudService creditAntifraudService;
	@Autowired(required=false) //自动注入
	CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	CreditContactService creditContactService;
	@Autowired(required=false) //自动注入
	CreditExtService creditExtService;
	@Autowired(required=false) //自动注入
	ProcessService processService;
	@Autowired(required=false) //自动注入
	CreditDecisionService creditDecisionService;
	
	
	/**
	 * 跳转到信用贷款反欺诈表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String creditAntifraudQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "credit/antifraud/creditAntifraudQuery";
	}

	/**
	 * 显示信用贷款反欺诈表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String creditVerifyList(CreditQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);  
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		if(queryMap.get("orgId")==null || "".equals(queryMap.get("orgId"))){
			queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		}
		if(user.getIsAdmin()!=1){
			queryMap.put("processer", loginId); // 审核人
		}
		if("".equals(queryMap.get("processer"))){
			queryMap.remove("processer");
		}
//		queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_ANTIFRAUD});//查询录入流程定义为反欺诈的
		
		if("waitTask".equals(qBean.getStateTask())){
//			queryMap.put("operator", loginId); //Session 操作员
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_ANTIFRAUD});//查询录入流程定义为录入申请的
		}else if("inTask".equals(qBean.getStateTask())){
			queryMap.put("nonBpmStates", new String[]{Constants.PROCESS_STATE_APP,Constants.PROCESS_END_APP});
		}else if("offTask".equals(qBean.getStateTask())){
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_END_APP});
		}
		
		int rowsCount = this.creditAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CreditApp> creditAppList = this.creditAppService.queryBpmLoanAppList(queryMap);
		pm.setData(creditAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "credit/antifraud/creditAntifraudList";
	}

	/**
	 * 跳转到更新信用贷款反欺诈表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String creditAntifraudUpdate(String appId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		//如果存在
		if (null != appId && !"".equals(appId)) {
			Map<String, Object> antimap=new HashMap<String, Object>();
			
			antimap.put("appId", appId);
			List<CreditApp> CreditAppList = creditAppService.queryList(antimap);
			CreditApp creditApp = null;
			if(CreditAppList!=null&&CreditAppList.size()>0){
				creditApp = CreditAppList.get(0);
			}
			int id = creditApp.getId();
			map.put("bean", creditApp);

			antimap.put("state", "1");
			List<CreditAntifraud> creditAntifraud = creditAntifraudService.queryList(antimap);
			if(creditAntifraud!=null&&creditAntifraud.size()>0){
				map.put("creditAntifraud", creditAntifraud.get(0));
			}
			
			antimap.put("type", "1");
			List<CreditDecision> creditDecision = creditDecisionService.queryList(antimap);
			if(creditDecision!=null&&creditDecision.size()>0&&id!=0){
				map.put("creditVerify", creditDecision.get(0));//审核信息
			}
			
		}

		log.info(thisMethodName+":end");
		return "credit/antifraud/creditAntifraudUpdate";
	}

	/**
	 * 保存信用贷款反欺诈表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void creditAntifraudSave(CreditAntifraud creditAntifraud,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			CreditAntifraud bean = (CreditAntifraud) RequestUtils.getRequestBean(CreditAntifraud.class, request);

			String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
            Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
            Timestamp time=new Timestamp(System.currentTimeMillis());
//			CreditAntifraud creditAntifraud = null;
//			creditAntifraud = new CreditAntifraud();
			creditAntifraud.setAppId(bean.getAppId());
			creditAntifraud.setSubmitOperator(loginId);
			creditAntifraud.setOrgId(sessionOrg.getOrgId());
			creditAntifraud.setCreateTime(time);
			creditAntifraud.setSubmitInfo(bean.getSubmitInfo());
			creditAntifraud.setState("1");
			creditAntifraud.setCreateTime(time);
			this.creditAntifraudService.add(creditAntifraud);
			creditDecisionService.antifraudUpdate(creditAntifraud, loginId, sessionOrg.getOrgId());

			map.put("creditAntifraud", creditAntifraud);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 删除信用贷款反欺诈表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void creditAntifraudDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.creditAntifraudService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
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
	 * 跳转到查看信用贷款反欺诈表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String creditAntifraudRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CreditAntifraud bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.creditAntifraudService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "credit/antifraud/creditAntifraudRead";
	}

	/**
	 * 解除反欺诈的代码
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/relieveAntifraud.do")
	public void creditRelieveAntifraud(CreditAntifraud creditAntifraud, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			CreditAntifraud bean = (CreditAntifraud) RequestUtils.getRequestBean(CreditAntifraud.class, request);
			
			String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
	        Timestamp time=new Timestamp(System.currentTimeMillis());
	        
	        Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			List<CreditAntifraud> creditAntifraudList = creditAntifraudService.queryList(fmap);
			
			if(creditAntifraudList!=null && !"".equals(creditAntifraudList)){
				creditAntifraud = creditAntifraudList.get(0);
				creditAntifraud.setOperator(loginId);
				creditAntifraud.setUpdateTime(time);
				creditAntifraud.setState("2");
				creditAntifraud.setResult(bean.getResult());
				creditAntifraud.setResultInfo(bean.getResultInfo());
				creditAntifraudList.add(creditAntifraud);
				this.creditAntifraudService.updateOnlyChanged(creditAntifraud);
				creditAntifraud.setButtonType(bean.getButtonType());
				creditDecisionService.antifraudUpdate(creditAntifraud, loginId, sessionOrg.getOrgId());

				map.put("creditAntifraud", creditAntifraud);
			}

			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
	
		log.info(thisMethodName+":end");
	//	return "credit/antifraud/creditAntifraudRead";
	}
}