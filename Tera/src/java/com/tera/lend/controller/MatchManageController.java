/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.lend.controller;
 
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.lend.model.LendApp;
import com.tera.lend.model.form.Lend2MatchQBean;
import com.tera.lend.model.form.LendQBean;
import com.tera.lend.model.form.Loan2MatchQBean;
import com.tera.lend.model.form.MatchManageFBean;
import com.tera.lend.model.form.MatchManageQBean;
import com.tera.lend.model.form.TrustProductQBean;
import com.tera.lend.service.LendAppService;
import com.tera.lend.service.ManualMatchService;
import com.tera.loan.model.LoanApp;
import com.tera.loan.service.LoanAppService;
import com.tera.match.model.Lend2match;
import com.tera.match.model.Loan2match;
import com.tera.match.model.MatchModel;
import com.tera.match.service.Lend2matchService;
import com.tera.match.service.Loan2matchService;
import com.tera.match.service.MatchResultService;

import com.tera.product.model.TrustProduct;
import com.tera.product.service.TrustProductService;
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
 * <b>功能：</b>LendAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-18 11:08:58<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/lend/matchmanage")
public class MatchManageController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LendAppController.class);


	@Autowired(required=false) //自动注入
	private Loan2matchService<Loan2match> loan2matchService;

	@Autowired(required=false) //自动注入
	private Lend2matchService<Lend2match> lend2matchService;
	

	/**
	 * 跳转到财富端申请表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String lendAppQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "lend/matchmanage/matchManageQuery";
	}

	/**
	 * 显示财富端申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String matchManageList(String matchState,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(MatchManageFBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		Org org=(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		if("lend".equals(matchState)){
			int rowsCount = this.lend2matchService.queryMatchManageCount(queryMap);
			pm.init(request, rowsCount, null, bean);
			queryMap.put("rowS", pm.getRowS());
			queryMap.put("rowE", pm.getRowE());
			List<MatchManageQBean> matchManageList = this.lend2matchService.queryMatchManageList(queryMap);
			pm.setData(matchManageList);
			map.put("pm", pm);
		}else{
			int rowsCount = this.loan2matchService.queryMatchManageCount(queryMap);
			pm.init(request, rowsCount, null, bean);
			queryMap.put("rowS", pm.getRowS());
			queryMap.put("rowE", pm.getRowE());
			List<MatchManageQBean> matchManageList = this.loan2matchService.queryMatchManageList(queryMap);
			pm.setData(matchManageList);
			map.put("pm", pm);
		}
		map.put("matchState",matchState);
		log.info(thisMethodName+":end");
		return "lend/matchmanage/matchManageList";
	}
	/**
	 * 显示财富端申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/changeMatchType.do")
	public void changeMatchType(String id,String matchState,HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		try {
			String msg=lend2matchService.matchManage(id, matchState, loginId);
			writer.print(JsonUtil.object2json(new JsonMsg(true, msg)));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "操作出错了！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}


}
