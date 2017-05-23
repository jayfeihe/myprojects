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
import java.util.ArrayList;
import java.util.Date;
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

import com.tera.lend.controller.LendAppController;
import com.tera.lend.model.LendApp;
import com.tera.lend.model.form.Lend2MatchQBean;
import com.tera.lend.model.form.LendQBean;
import com.tera.lend.model.form.Loan2MatchQBean;
import com.tera.lend.model.form.TrustProductQBean;
import com.tera.lend.service.LendAppService;
import com.tera.lend.service.ManualMatchService;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanApproval;
import com.tera.loan.model.form.LoanQBean;
import com.tera.loan.service.LoanAppService;
import com.tera.loan.service.LoanApprovalService;
import com.tera.loan.service.ManualMatchVerifyService;
import com.tera.match.model.Lend2match;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchModel;
import com.tera.match.model.MatchResult;
import com.tera.match.service.Lend2matchService;
import com.tera.match.service.Loan2matchService;
import com.tera.match.service.MatchResultService;

import com.tera.product.model.TrustProduct;
import com.tera.product.service.TrustProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;

/**
 * 
 * <br>
 * <b>功能：</b>LendAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-18 11:08:58<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
//@RequestMapping("/lend/manualmatchverify")
@RequestMapping("/loan/manualverify")
public class LoanManualVerifyController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LendAppController.class);
	
	/**
	 * ManualMatchService
	 */
	@Autowired(required=false) //自动注入
	private ManualMatchService<LendApp> manualMatchService;
	
	@Autowired(required=false) //自动注入
	private LendAppService lendAppService;
	
	@Autowired(required=false) //自动注入
	private Loan2matchService loan2matchService;
	
	@Autowired(required=false) //自动注入
	private TrustProductService trustProductService;
	
	@Autowired(required=false) //自动注入
	private Lend2matchService lend2matchService;
	
	@Autowired(required=false) //自动注入
	private MatchResultService matchResultService;
	
	@Autowired(required=false) //自动注入
	private ManualMatchVerifyService manualMatchVerifyService;
	
	@Autowired(required=false) //自动注入
	private LoanAppService<LoanApp> loanAppService;
	
	@Autowired(required=false) //自动注入
	private LoanApprovalService loanApprovalService;
	
	
	
	
	
	
	
	
	
	/**
	 * 跳转到财富端申请表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String lendAppQuery(HttpServletRequest request, ModelMap map) throws Exception {
		return "loan/manualverify/manualVerifyQuery";
	}

	
	//选择出所有t_loan_2match表中并且存在于t_bpm_task表中State处于“人工撮合审批”状态，且PROCESSER为当前登录用户（admin可看全部）的记录
//	@RequestMapping("/loan2matchList.do")
	@RequestMapping("/list.do")
	public String loan2matchList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Loan2MatchQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		queryMap.put("processer", loginId);
		queryMap.put("bpmstate", "人工撮合审批");
		queryMap.put("loan2matchstate", "2");
		int rowsCount = this.loan2matchService.queryManualMatchVerifyCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE()); 
		List<Loan2MatchQBean> loan2matchList = this.loan2matchService.queryManualMatchVerifyList(queryMap);
		pm.setData(loan2matchList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loan/manualverify/manualVerifyList";
	}
	
	

	/**
	 * 跳转到更新财富端申请表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String lendAppUpdate( String id,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApp bean =new LoanApp();
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.loanAppService.queryByKey(id);
		}
		
		//根据loanApp的loanAppId查询出loan2match中该申请的id
		List<Loan2match> loan2matchList = this.loan2matchService.getLoan2matchByAppId(bean.getAppId());
		//取得loan2match的id
		int loan2matchId = loan2matchList.get(0).getId();
		
		// 根据appId查询审批结果
		LoanApproval loanApproval = loanApprovalService.queryByKey(bean.getAppId());
		map.put("bean", bean);
		map.put("id", id);
		map.put("loanApproval", loanApproval);
		// ---------------------------------------------------
		// 显示该申请号对应的撮合结果，显示所有借助人信息列表
		PageModel pm = new PageModel();
		LoanQBean qBean = (LoanQBean) RequestUtils.getRequestBean(
				LoanQBean.class, request);
		Map<String, Object> beanMap = ObjectUtils.describe(qBean);
		beanMap.put("loanAppId", bean.getAppId());
		pm.init(request, 0, null, qBean);
		beanMap.put("rowS", pm.getRowS());
		beanMap.put("rowE", pm.getRowE());
		beanMap.put("state", "1");
		List<MatchResult> results = this.matchResultService.queryListInSign(beanMap);

		pm.setData(results);
		map.put("pm", pm);
		map.put("loan2matchId", loan2matchId);
		log.info(thisMethodName+":end");
		return "loan/manualverify/manualVerifyUpdate";
	}

	/**
	 * 跳转到更新财富端申请表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/lend2matchListupdate.do")
	public String lend2matchList(String manualVerifyloan2matchId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		Loan2match loan2match = (Loan2match) loan2matchService.queryByKey(manualVerifyloan2matchId);
		
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Lend2MatchQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		//设置参数为loan2match的loanAppId
		queryMap.put("loan2matchId", loan2match.getLoanAppId());
		queryMap.put("matchresultstate", '1');
		int rowsCount = this.lend2matchService.queryManualMatchVerifyLend2MatchCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE()); 
		List<Lend2MatchQBean> loan2matchList = this.lend2matchService.queryManualMatchVerifyLend2MatchList(queryMap);
		pm.setData(loan2matchList);
		map.put("pm", pm);
		map.put("bean", loan2match);
		//writer.print(JsonUtil.object2json(new JsonMsg(true, "查询成功")));
		//writer.flush();
		//writer.close();
		log.info(thisMethodName+":end");
		return "loan/manualverify/lend2matchList";
	}

	/**
	 * 删除财富端申请表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void lendAppDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.lendAppService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(new JsonMsg(false, "关联数据，不能删除！"));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 人工撮合审批通过
	 * @param id
	 * @param response
	 * @param request
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/passManualMatchVerify.do")
	public void passManualMatchVerify(String loan2matchId_matchresult, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		Loan2match loan2match = (Loan2match) loan2matchService.queryByKey(loan2matchId_matchresult);
		
		try {
			
			manualMatchVerifyService.bpmGoNext(loan2match);
			//this.manualMatchService.delete();
			writer.print(JsonUtil.object2json(new JsonMsg(true, "审批通过！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(new JsonMsg(false, "执行失败！"));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 人工撮合审批不通过
	 * @param id
	 * @param response
	 * @param request
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/denyManualMatchVerify.do")
	public void denyManualMatchVerify(String loan2matchId_matchresult,String denyReason, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		Loan2match loan2match = (Loan2match) loan2matchService.queryByKey(loan2matchId_matchresult);
		
		try {
			//执行不通过方法
			manualMatchVerifyService.denyManualMatchVerify(loan2match.getLoanAppId(), denyReason);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "审批不通过成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(new JsonMsg(false, "执行失败！"));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看财富端申请表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String lendAppRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LendApp bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.lendAppService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "lend/manualmatch/lendAppRead";
	}

}
