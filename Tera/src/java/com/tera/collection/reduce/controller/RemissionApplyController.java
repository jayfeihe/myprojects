/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.collection.reduce.controller;

import java.io.PrintWriter;
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

import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.service.CollectionBaseService;
import com.tera.collection.reduce.constants.RemissionConstants;
import com.tera.collection.reduce.model.CollectionRemission;
import com.tera.collection.reduce.model.form.RemissionJsonMsg;
import com.tera.collection.reduce.model.form.RemissionQBean;
import com.tera.collection.reduce.service.CollectionRemissionService;
import com.tera.payment.model.LoanRepayPlan;
import com.tera.payment.service.LoanRepayPlanService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 减免申请审批表控制器
 * <b>功能：</b>CollectionRemissionController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:48:41<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collection/reduce/apply")
public class RemissionApplyController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(RemissionApplyController.class);
	
	/**
	 * CollectionRemissionService
	 */
	@Autowired(required=false) //自动注入
	private CollectionRemissionService collectionRemissionService;
	@Autowired(required=false) //自动注入
	private LoanRepayPlanService loanRepayPlanService;
	@Autowired(required=false) //自动注入
	private CollectionBaseService collectionBaseService;
	
	/**
	 * 跳转到减免申请的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String remissionApplyQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "collection/reduce/apply/remissionApplyQuery";
	}

	/**
	 * 显示减免申请查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String remissionApplyList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		RemissionQBean bean = (RemissionQBean) RequestUtils.getRequestBean(RemissionQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		// 待处理
		if (RemissionConstants.REMISSION_PROCESS_WAIT.equals(bean
				.getProcessStatus())) {
			queryMap.put("states",new String[] { RemissionConstants.REMISSION_STATE_REVIEW });
		}
		// 已处理
		if (RemissionConstants.REMISSION_PROCESS_OFF.equals(bean
				.getProcessStatus())) {
			queryMap.put("states", new String[] {
					RemissionConstants.REMISSION_STATE_APPROVAL,
					RemissionConstants.REMISSION_STATE_H_APPROVAL,
					RemissionConstants.REMISSION_STATE_PASS,
					RemissionConstants.REMISSION_STATE_N_PASS });
		}
		
		int rowsCount = this.collectionRemissionService
				.queryRemissionApplyCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<RemissionQBean> remissionList = this.collectionRemissionService
				.queryRemissionApplyList(queryMap);
		pm.setData(remissionList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "collection/reduce/apply/remissionApplyList";
	}

	/**
	 * 跳转到更新减免申请审批表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String remissionApplyUpdate(String contractNo, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("contractNo", contractNo);
		CollectionBase bean = collectionBaseService.queryList(queryMap).get(0);
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "collection/reduce/apply/remissionApplyUpdate";
	}

	/**
	 * 保存减免申请审批表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void remissionApplySave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org loginOrg = (Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String orgId = loginOrg.getOrgId();
		try {
			CollectionRemission remissionApply = (CollectionRemission) RequestUtils
					.getRequestBean(CollectionRemission.class, request);
			RemissionJsonMsg jsonMsg = collectionRemissionService
					.submitRemissionApply(remissionApply, loginId, orgId);
			writer.print(JsonUtil.object2json(jsonMsg));
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
	 * 删除减免申请审批表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void collectionRemissionDelete(String id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.collectionRemissionService.delete(id);
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
	 * 跳转到查看减免申请审批表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String remissionApplyRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollectionRemission bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.collectionRemissionService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "collection/reduce/apply/remissionApplyRead";
	}

	@RequestMapping("/repayPlanDetail.do")
	public String repayPlanDetailView(String contractNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("contractNo", contractNo);
		List<LoanRepayPlan> repayPlanList = loanRepayPlanService
				.queryFyLoanStateList(queryMap);

		if (null != repayPlanList && repayPlanList.size() > 0) {
			for (LoanRepayPlan loanRepayPlan : repayPlanList) {
				if (!"2".equals(loanRepayPlan.getState())
						&& loanRepayPlan.getPenaltyReceivable() > 0) {
					loanRepayPlan.setYqts(DateUtils.getDayRange(
							loanRepayPlan.getRepaymentDate(), new Date()));
				}
				double dyyshj = MathUtils.add(loanRepayPlan
						.getDelayReceivable(), MathUtils.add(loanRepayPlan
						.getPenaltyReceivable(), MathUtils.add(loanRepayPlan
						.getInterestReceivable(), MathUtils.add(
						loanRepayPlan.getSreviceFeeReceivable(),
						loanRepayPlan.getPrincipalReceivable()))));
				loanRepayPlan.setDyyshj(MathUtils.roundUp(dyyshj, 2));
				double dyshhj = MathUtils
						.add(loanRepayPlan.getDelayReceived(), MathUtils.add(
								loanRepayPlan.getPenaltyReceived(),
								MathUtils.add(loanRepayPlan
										.getInterestReceived(), MathUtils.add(
										loanRepayPlan.getSreviceFeeReceived(),
										loanRepayPlan.getPrincipalReceived()))));
				loanRepayPlan.setDyshhj(MathUtils.roundUp(dyshhj, 2));
				if ("1".equals(loanRepayPlan.getState())) {
					loanRepayPlan.setHkzt("未还");
				}
				if ("2".equals(loanRepayPlan.getState())) {
					loanRepayPlan.setHkzt("已还清");
				}
				if ("3".equals(loanRepayPlan.getState())) {
					loanRepayPlan.setHkzt("未还清");
				}
				if ("1".equals(loanRepayPlan.getState())
						|| "3".equals(loanRepayPlan.getState())) {
					loanRepayPlan.setBqyhkhj(MathUtils.sub(MathUtils.sub(
							MathUtils.sub(dyyshj, dyshhj),
							loanRepayPlan.getPenaltyReduce()), loanRepayPlan
							.getDelayReduce()));
				} else {
					loanRepayPlan.setBqyhkhj(0.00);
				}
			}
		}
		map.put("repayPlanList", repayPlanList);
		
		log.info(thisMethodName + ":end");
		return "collection/reduce/apply/repayPlanDetail";
	}
}
