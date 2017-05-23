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
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * T_LOAN_BASE控制器
 * <b>功能：</b>LoanBaseController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:52:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/common")
public class LoanCommonController extends BaseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanCommonController.class);
	
	/**
	 * LoanBaseService
	 */
	@Autowired(required=false) //自动注入
	private ILoanBaseService loanBaseService;
	
	@Autowired
	private ILoanAppService loanAppService;
	
	/**
	 * 跳转到T_LOAN_BASE的查询条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String loanCommonQuery(String loanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId", loanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "loan/common/loanCommonQuery";
	}

	/**
	 * 显示T_LOAN_BASE的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String loanCommonList(String loanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanApp.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		queryMap.put("loanId",loanId);
		queryMap.put("mainFlag","0");
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LoanApp> loanAppList = this.loanAppService.queryPageList(queryMap);
		pm.setData(loanAppList);
		pm.initRowsCount(loanAppList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("loanId", loanId);
		map.put("opt", opt);
		log.info(thisMethodName+":end");
		return "loan/common/loanCommonList";
	}

	/**
	 * 跳转到更新T_LOAN_BASE的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String loanCommonUpdate(Integer id,String loanId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApp loanApp = null;
		//如果存在
		if (id != null && 0 != id) {
			loanApp = this.loanAppService.queryByKey(id);
		}
		map.put("appTypeLoan", loanApp);
		map.put("loanId", loanId);
		log.info(thisMethodName+":end");
		return "loan/common/loanCommonUpdate";
	}

	/**
	 * 保存T_LOAN_BASE数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanCommonSave(AppFormBean formBean, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			LoanBaseJsonMsg jsonMsg = this.loanAppService.saveCommonLoan(formBean,loginId,loginOrg.getOrgId());
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
	 * 删除T_LOAN_BASE数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void loanCommonDelete(String id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.loanBaseService.delete(id);
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
	 * 跳转到查看T_LOAN_BASE的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String loanCommonRead(Integer id, String loanId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanApp loanApp = null;
		//如果存在
		if (id != null && 0 != id) {
			loanApp = this.loanAppService.queryByKey(id);
		}
		map.put("appTypeLoan", loanApp);
		map.put("loanId", loanId);
		log.info(thisMethodName+":end");
		return "loan/common/loanCommonRead";
	}

}
