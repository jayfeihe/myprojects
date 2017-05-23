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
 * 诉讼复核Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/law/loanlaw")
public class LoanLawAuditController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanLawAuditController.class);
	
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
	public String loanLawAuditQuery(String loanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId", loanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "law/loanlaw/loanLawAuditQuery";
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
	public String loanLawAuditList(String loanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanLawQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		queryMap.put("loanId", loanId);
		
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
		return "law/loanlaw/loanLawAuditList";
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
	public String loanLawAuditUpdate(String id,String loanId,String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
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
		return "law/loanlaw/loanLawAuditUpdate";
	}

	/**
	 * 保存质押、抵押物信息数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanLawAuditSave(String type, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		try {
			// 申请
			if ("1".equals(type)) {
				LoanApp bean = (LoanApp) RequestUtils.getRequestBean(LoanApp.class, request);
				LoanApp loanApp = this.loanAppService.queryByKey(bean.getId());
				loanApp.setLawCheckState(bean.getLawCheckState());
				loanApp.setLawCheckRemark(bean.getLawCheckRemark());
				loanApp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				this.loanAppService.update(loanApp);
			}
			// 担保
			if ("2".equals(type)) {
				LoanGuar bean = (LoanGuar) RequestUtils.getRequestBean(LoanGuar.class, request);
				LoanGuar loanGuar = this.loanGuarService.queryByKey(bean.getId());
				loanGuar.setLawCheckState(bean.getLawCheckState());
				loanGuar.setLawCheckRemark(bean.getLawCheckRemark());
				loanGuar.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				this.loanGuarService.update(loanGuar);
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
	}
}
