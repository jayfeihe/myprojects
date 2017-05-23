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

import com.tera.bpm.constant.BpmConstants;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.loan.model.Lending;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanApproval;
import com.tera.loan.service.LoanAppService;
import com.tera.loan.service.LoanLendingService;
import com.tera.payment.model.Payment;
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
 * <b>功能：</b>AccountDetailController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-13 11:27:41<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/lending")
public class LoanLendingController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanLendingController.class);
	
	/**
	 * AccountDetailService
	 */
	@Autowired(required=false) //自动注入
	private LoanLendingService loanLendingService;
	
	@Autowired(required=false) //自动注入
	private ContractService contractService;
	
	@Autowired(required=false) //自动注入
	private LoanAppService<LoanApp> loanAppService;
	
	
	/**
	 * 跳转放款查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String lendingQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "loan/lending/lendingQuery";
	}

	/**
	 * 显示放款的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String lendingList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//系统登录用户
		String sessionLoginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);

		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Lending.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//设置该申请的状态为"放款"
		queryMap.put("processer", sessionLoginId);
		queryMap.put("subject", "放款");

		//组合查询处于放款状态的合同数量
		int rowsCount = this.loanLendingService.queryLendingCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		//组合查询处于放款状态的合同的详细信息
		List<Lending> accountDetailList = this.loanLendingService.queryLendingList(queryMap);
		pm.setData(accountDetailList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loan/lending/lendingList";
	}
	/**
	 * 确认放款
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/ConfirmLending.do")
	public void ConfirmLending(String contractNo,String approvalAmount, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		//系统登录用户
		String sessionLoginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		System.out.println("contractno="+contractNo);
		System.out.println("approvalAmount="+approvalAmount);
		try {
			map.put("loginid", sessionLoginId);
			map.put("contractNo", contractNo);
			map.put("orgId", org.getOrgId());
			this.loanLendingService.ConfirmLending(map);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
		
	}
	/**
	 * 退回复核
	 * @param contractNo
	 * @param subbpm
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/ReturnCheck.do")
	public void ReturnCheck(String contractNo,String subbpm,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			// TODO service操作 需要修改
		
			LoanApp bean=loanAppService.getLoanListByContractNo(contractNo).get(0);
			String loginid = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			// 如果存在
			if (bean.getAppId() != null) {
				// 提交 走流程
				if ("trueSubbpm".equals(subbpm)) {
					this.loanLendingService.bpmNext(loginid, bean);
					writer.print(JsonUtil.object2json(new JsonMsg(true,
							"审核已提交！")));
				} else {
					writer.print(JsonUtil
							.object2json(new JsonMsg(true, "保存成功！")));
				}
			} else {
				writer.print(JsonUtil.object2json(new JsonMsg(true,
						"保存失败，申请ID不存在")));
			}
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	

}
