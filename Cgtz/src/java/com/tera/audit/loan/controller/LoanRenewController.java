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
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.audit.loan.model.Contact;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.LoanBase;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.model.form.LoanRenewQBean;
import com.tera.audit.loan.service.IContactService;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.audit.loan.service.ILoanRenewService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 续贷控制器
 * <b>功能：</b>LoanBaseController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:52:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loan/renew")
public class LoanRenewController extends BaseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoanRenewController.class);
	
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
	private ILoanRenewService loanRenewService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private IContractService contractService;
	
	/**
	 * 跳转到续贷的查询条件页面
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
		return "loan/renew/loanRenewQuery";
	}

	/**
	 * 显示续贷的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String loanRenewList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanRenewQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		if (0 == loginUser.getIsAdmin()) {
			Map<String, Object> roleMap = new HashMap<String,Object>();
			roleMap.put("loginId", loginUser.getLoginId());
			roleMap.put("orgId", loginOrg.getOrgId());
			List<Role> loginRoles = this.roleService.getRoleByOrgLoginId(roleMap );
			if (loginRoles != null && loginRoles.size() > 0) {
				for (Role role : loginRoles) {
					// 是业务员自己看自己的
					if (CommonConstant.ROLE_SALESMAN.equals(role.getName()) && "1".equals(role.getFlag())) {
						queryMap.put("salesman", loginUser.getLoginId());
					}
				}
			}
			
			queryMap.put("orgId", loginOrg.getOrgId());
		}
		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LoanRenewQBean> loanRenewList = this.loanRenewService.queryPageList(queryMap);
		pm.setData(loanRenewList);
		pm.initRowsCount(loanRenewList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loan/renew/loanRenewList";
	}

	/**
	 * 跳转到更新续贷的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String loanRenewUpdate(Integer id,Double renewAmt,String contractId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		//如果存在
		if (id != null && 0 != id) {
			loanBase  = this.loanBaseService.queryByKey(id);
			/*
			// 个人
			if ("01".equals(loanBase.getLoanType())) {
				map.put("sec","filesce1");
			}
			// 公司
			if ("02".equals(loanBase.getLoanType())) {
				map.put("sec","filesce2");
			}
			
			// 续贷
			if ("1".equals(loanBase.getIsRenew())) {
				LoanBase origLoanBase = this.loanBaseService.queryByLoanId(loanBase.getOrigLoanId());
				map.put("origLoanBase", origLoanBase);
				map.put("isTgth", origLoanBase.getIsTgth());
			} else {
				map.put("isTgth", loanBase.getIsTgth());
			}*/
		}
		map.put("loanBase", loanBase);
		map.put("renewAmt", renewAmt);
		map.put("contractId", contractId);
		log.info(thisMethodName+":end");
		return "loan/renew/loanRenewUpdate";
	}

	/**
	 * 保存续贷数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanRenewSave(AppFormBean formBean, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			LoanBaseJsonMsg jsonMsg = this.loanRenewService.loanRenewProcess(formBean,loginId,loginOrg.getOrgId());
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
	 * 删除续贷数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void loanRenewDelete(String id, HttpServletResponse response,
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
	 * 跳转到续贷的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/readQuery.do")
	public String loanRenewReadQuery(String origLoanId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("origLoanId", origLoanId);
		log.info(thisMethodName+":end");
		return "loan/renew/loanRenewReadQuery";
	}
	
	/**
	 * 显示续贷的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/readList.do")
	public String loanRenewReadList(String origLoanId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = new HashMap<String,Object>();
		queryMap.put("origLoanId", origLoanId);
		
		pm.init(request, null, queryMap);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LoanBase> loanRenewReadList = this.loanBaseService.queryPageList(queryMap);
		pm.setData(loanRenewReadList);
		pm.initRowsCount(loanRenewReadList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loan/renew/loanRenewReadList";
	}
	
	/**
	 * 跳转到查看续贷的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String loanRenewRead(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		//如果存在
		if (id != null && 0 != id) {
			loanBase  = this.loanBaseService.queryByKey(id);
		}
		map.put("loanBase", loanBase);
		
		log.info(thisMethodName+":end");
		return "loan/renew/loanRenewRead";
	}

	@RequestMapping("/allRead.do")
	public String loanRenewAllRead(String id, String contractId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = null;
		List<LoanApp> loanApps = null;
		List<Contact> contacts = null;
		//如果存在
		if (StringUtils.isNotNullAndEmpty(id)) {
			loanBase  = this.loanBaseService.queryByKey(id);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("loanId", loanBase.getOrigLoanId());
			queryMap.put("mainFlag", "1"); // 主借款人
			loanApps = this.loanAppService.queryList(queryMap );
			contacts = this.contactService.queryByLoanId(loanBase.getOrigLoanId());
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
		
		// 合同信息
		Contract contract = this.contractService.queryByContractId(contractId);
		map.put("contract", contract);
		
		log.info(thisMethodName+":end");
		return "loan/renew/loanRenewAllRead";
	}
}
