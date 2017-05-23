/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.audit.loan.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.form.CollateralJsonMsg;
import com.tera.audit.loan.model.form.LoanLawQBean;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanLawService;
import com.tera.feature.loanguar.model.LoanGuar;
import com.tera.feature.loanguar.service.ILoanGuarService;

import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 质押、抵押物信息控制器
 * <b>功能：</b>CollateralController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/law")
public class LoanLawController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanLawController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private ILoanLawService loanLawService;
	@Autowired(required=false) //自动注入
	private ILoanAppService loanAppService;
	@Autowired(required=false) //自动注入
	private ILoanGuarService loanGuarService;
	
	/**
	 * 跳转到诉讼查询条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public String loanLawQuery(String loanId,String origLoanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId", loanId);
		map.put("origLoanId", origLoanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "loan/law/loanLawQuery";
	}

	/**
	 * 显示诉讼信息的查询列表
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String loanLawList(String loanId,String origLoanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanLawQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		queryMap.put("loanId", loanId);
		if (StringUtils.isNotNullAndEmpty(origLoanId)) {
			queryMap.put("origLoanId", origLoanId);
		}
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LoanLawQBean> loanLawList = this.loanLawService.queryPageList(queryMap);
		pm.setData(loanLawList);
		pm.initRowsCount(loanLawList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("loanId", loanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "loan/law/loanLawList";
	}

	/**
	 * 跳转到诉讼更新页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String loanLawUpdate(String id,String loanId,String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		if (StringUtils.isNotNullAndEmpty(id)) {
			// 申请
			if ("1".equals(type)) {
				LoanApp bean = this.loanAppService.queryByKey(id);
				map.put("bean", bean);
			}
			// 担保
			if ("2".equals(type)) {
				LoanGuar bean = this.loanGuarService.queryByKey(id);
				map.put("bean", bean);
			}
		}
		map.put("loanId", loanId);
		map.put("type", type);
		
		log.info(thisMethodName+":end");
		return "loan/law/loanLawUpdate";
	}

	/**
	 * 保存质押、抵押物信息数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanLawSave(String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		CollateralJsonMsg jsonMsg = null;
		try {
			// 申请
			if ("1".equals(type)) {
				LoanApp bean = (LoanApp) RequestUtils.getRequestBean(LoanApp.class, request);
				LoanApp loanApp = this.loanAppService.queryByKey(bean.getId());
				loanApp.setLawState(bean.getLawState());
				loanApp.setLawRemark(bean.getLawRemark());
				loanApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				this.loanAppService.update(loanApp);
				jsonMsg = new CollateralJsonMsg(bean.getId(), bean.getLoanId(), type, true, "成功");
			}
			// 担保
			if ("2".equals(type)) {
				LoanGuar bean = (LoanGuar) RequestUtils.getRequestBean(LoanGuar.class, request);
				LoanGuar loanGuar = this.loanGuarService.queryByKey(bean.getId());
				loanGuar.setLawState(bean.getLawState());
				loanGuar.setLawRemark(bean.getLawRemark());
				loanGuar.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				this.loanGuarService.update(loanGuar);
				jsonMsg = new CollateralJsonMsg(bean.getId(), bean.getLoanId(), type, true, "成功");
			}
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
	 * 跳转到诉讼查看的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String loanLawRead(String id,String loanId,String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		if (StringUtils.isNotNullAndEmpty(id)) {
			// 申请
			if ("1".equals(type)) {
				LoanApp bean = this.loanAppService.queryByKey(id);
				map.put("bean", bean);
			}
			// 担保
			if ("2".equals(type)) {
				LoanGuar bean = this.loanGuarService.queryByKey(id);
				map.put("bean", bean);
			}
		}
		map.put("loanId", loanId);
		map.put("type", type);
		
		log.info(thisMethodName+":end");
		return "loan/law/loanLawRead";
	}
}
