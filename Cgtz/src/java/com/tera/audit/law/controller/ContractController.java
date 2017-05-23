/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.audit.law.controller;

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

import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.retplan.model.RetPlan;
import com.tera.audit.retplan.service.IRetPlanService;
import com.tera.feature.lenduser.model.LendUser;
import com.tera.feature.lenduser.service.ILendUserService;
import com.tera.util.StringUtils;

/**
 * 
 * 线下线上合同关联表控制器
 * <b>功能：</b>ContractOnlineController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 15:03:28<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/contract")
public class ContractController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ContractController.class);
	
	/**
	 * ContractOnlineService
	 */
	@Autowired(required=false) //自动注入
	private IContractService contractService;
	@Autowired
	private ILendUserService lendUserService;
	@Autowired
	private IRetPlanService retPlanService;
	@Autowired
	private ILoanBaseService loanBaseService;
	
	/**
	 * 跳转到合同查看的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String contractRead(String contractId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Contract bean = null;
		// 如果存在
		if (null != contractId && !"".equals(contractId)) {
			bean = this.contractService.queryByContractId(contractId);
			// 借款人
			LoanBase loanBase = this.loanBaseService.queryByLoanId(bean.getLoanId());
			map.put("loanUser", loanBase.getName());
			// 出借人
			String lendUId = bean.getLendUserId();
			if (StringUtils.isNotNullAndEmpty(lendUId)) {
				LendUser lendUser = this.lendUserService.queryByKey(lendUId);
				map.put("lendUser", lendUser.getName());
			}
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "contract/contractRead";
	}
	
	/**
	 * 跳转到还款计划查看页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/repayPlanList.do")
	public String repayPlanList(String contractId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		Map<String, Object> planMap = new HashMap<String,Object>();
		planMap.put("contractId", contractId);
		List<RetPlan> planList = this.retPlanService.queryList(planMap );
		map.put("planList", planList);
		log.info(thisMethodName+":end");
		return "contract/repayPlanList";
	}

}
