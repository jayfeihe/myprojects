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

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.loan.model.Contact;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.service.IBankBranchInfoService;
import com.tera.audit.loan.service.IContactService;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
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
@RequestMapping("/loan")
public class LoanBaseController extends BaseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanBaseController.class);
	
	/**
	 * LoanBaseService
	 */
	@Autowired(required=false) //自动注入
	private ILoanBaseService loanBaseService;
	
	@Autowired
	private ILoanAppService loanAppService;
	
	@Autowired
	private IContactService contactService;
	
	@Autowired
	private IBankBranchInfoService bankBranchInfoService;
	/**
	 * 跳转到T_LOAN_BASE的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String loanBaseQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "loan/loanBaseQuery";
	}

	/**
	 * 显示T_LOAN_BASE的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String loanBaseList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanBase.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		queryMap.put("bpmStates", new String[]{CommonConstant.PROCESS_A});
		if (0 == loginUser.getIsAdmin()) {
			queryMap.put("processer", loginUser.getLoginId());
			queryMap.put("orgId", loginOrg.getOrgId());
		}
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LoanBase> loanBaseList = this.loanBaseService.queryPageList(queryMap);
		pm.setData(loanBaseList);
		pm.initRowsCount(loanBaseList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loan/loanBaseList";
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
	public String loanBaseUpdate(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		List<LoanApp> loanApps = null;
		List<Contact> contacts = null;
		//如果存在
		if (id != null && 0 != id) {
			loanBase  = this.loanBaseService.queryByKey(id);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			String loanId = null;
			
			if ("0".equals(loanBase.getIsRenew())) {
				loanId = loanBase.getLoanId();
			} else 
			// 续贷
			if ("1".equals(loanBase.getIsRenew())) {
				loanId = loanBase.getOrigLoanId();
				
				LoanBase origLoanBase = this.loanBaseService.queryByLoanId(loanId);
				map.put("origLoanBase", origLoanBase);
			}
			
			queryMap.put("loanId", loanId);
			queryMap.put("mainFlag", "1"); // 主借款人
			loanApps = this.loanAppService.queryList(queryMap );
			contacts = this.contactService.queryByLoanId(loanId);
		}
		map.put("loanBase", loanBase);
		if (loanApps != null && loanApps.size() > 0) {
			// 个人
			if ("01".equals(loanBase.getLoanType())) {
				map.put("appTypeLoan", loanApps.get(0));
				map.put("sec","filesce1");
			}
			// 公司
			if ("02".equals(loanBase.getLoanType())) {
				map.put("comTypeLoan", loanApps.get(0));
				map.put("sec","filesce2");
			}
			// 主键id
			map.put("appId", loanApps.get(0).getId());
		}
		map.put("contacts", contacts);
		log.info(thisMethodName+":end");
		return "loan/loanBaseUpdate";
	}

	/**
	 * 保存T_LOAN_BASE数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanBaseSave(AppFormBean formBean, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			formBean.setLoanWay(LoanBase.LOAN_WAY_PC); // PC端
			//银行信息维护
			bankBranchInfoService.verifyBank(formBean.getLoanBase().getAcctBank(), request);
			LoanBaseJsonMsg jsonMsg = this.loanBaseService.loanAppProcess(formBean,loginId,loginOrg.getOrgId());
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
	public void loanBaseDelete(String id, HttpServletResponse response,
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
	public String loanBaseRead(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		List<LoanApp> loanApps = null;
		List<Contact> contacts = null;
		//如果存在
		if (id != null && 0 != id) {
			loanBase  = this.loanBaseService.queryByKey(id);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			
			String loanId = null;
			
			if ("0".equals(loanBase.getIsRenew()) || "2".equals(loanBase.getIsRenew())) {
				loanId = loanBase.getLoanId();
			} else 
			// 续贷
			if ("1".equals(loanBase.getIsRenew())) {
				loanId = loanBase.getOrigLoanId();
				
				LoanBase origLoanBase = this.loanBaseService.queryByLoanId(loanId);
				map.put("origLoanBase", origLoanBase);
			}
			
			queryMap.put("loanId", loanId);
			queryMap.put("mainFlag", "1"); // 主借款人
			loanApps = this.loanAppService.queryList(queryMap );
			contacts = this.contactService.queryByLoanId(loanId);
			
		}
		map.put("loanBase", loanBase);
		
		if (loanApps != null && loanApps.size() > 0) {
			// 个人
			if ("01".equals(loanBase.getLoanType()))
				map.put("appTypeLoan", loanApps.get(0));
			// 公司
			if ("02".equals(loanBase.getLoanType()))
				map.put("comTypeLoan", loanApps.get(0));
			// 主键id
			map.put("appId", loanApps.get(0).getId());
		}
		map.put("contacts", contacts);
		log.info(thisMethodName+":end");
		return "loan/loanBaseRead";
	}

	@RequestMapping("/allRead.do")
	public String loanBaseAllRead(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		List<LoanApp> loanApps = null;
		List<Contact> contacts = null;
		//如果存在
		if (id != null && 0 != id) {
			loanBase  = this.loanBaseService.queryByKey(id);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			
			// 非续贷
			if ("0".equals(loanBase.getIsRenew()) || "2".equals(loanBase.getIsRenew())) {
				queryMap.put("loanId", loanBase.getLoanId());
				contacts = this.contactService.queryByLoanId(loanBase.getLoanId());
			}
			// 续贷
			if ("1".equals(loanBase.getIsRenew())) {
				queryMap.put("loanId", loanBase.getOrigLoanId());
				contacts = this.contactService.queryByLoanId(loanBase.getOrigLoanId());
			}
			
			queryMap.put("mainFlag", "1"); // 主借款人
			loanApps = this.loanAppService.queryList(queryMap );
			
		}
		map.put("loanBase", loanBase);
		if (loanApps != null && loanApps.size() > 0) {
			// 个人
			if ("01".equals(loanBase.getLoanType())) {
				map.put("appTypeLoan", loanApps.get(0));
				map.put("sec","filesce1");
			}
			// 公司
			if ("02".equals(loanBase.getLoanType())) {
				map.put("comTypeLoan", loanApps.get(0));
				map.put("sec","filesce2");
			}
			// 主键id
			map.put("appId", loanApps.get(0).getId());
		}
		map.put("contacts", contacts);
		log.info(thisMethodName+":end");
		return "loan/loanBaseAllRead";
	}
	
	/**
	 * 跳转到查看T_LOAN_BASE的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/origRead.do")
	public String loanBaseOrigRead(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		List<LoanApp> loanApps = null;
		List<Contact> contacts = null;
		//如果存在
		if (id != null && 0 != id) {
			loanBase  = this.loanBaseService.queryByKey(id);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			
			String loanId = null;
			
			if ("0".equals(loanBase.getIsRenew()) || "2".equals(loanBase.getIsRenew())) {
				loanId = loanBase.getLoanId();
			} else 
			// 续贷
			if ("1".equals(loanBase.getIsRenew())) {
				loanId = loanBase.getOrigLoanId();
				
				LoanBase origLoanBase = this.loanBaseService.queryByLoanId(loanId);
				map.put("origLoanBase", origLoanBase);
			}
			
			queryMap.put("loanId", loanId);
			queryMap.put("mainFlag", "1"); // 主借款人
			loanApps = this.loanAppService.queryList(queryMap );
			contacts = this.contactService.queryByLoanId(loanId);
			
		}
		
		map.put("loanBase", loanBase);
		
		if (loanApps != null && loanApps.size() > 0) {
			// 个人
			if ("01".equals(loanBase.getLoanType())) {
				map.put("appTypeLoan", loanApps.get(0));
				map.put("sec","filesce1");
			}
			// 公司
			if ("02".equals(loanBase.getLoanType())) {
				map.put("comTypeLoan", loanApps.get(0));
				map.put("sec","filesce2");
			}
			// 主键id
			map.put("appId", loanApps.get(0).getId());
		}
		map.put("contacts", contacts);
		
		log.info(thisMethodName+":end");
		return "loan/loanBaseOrigRead";
	}
}
