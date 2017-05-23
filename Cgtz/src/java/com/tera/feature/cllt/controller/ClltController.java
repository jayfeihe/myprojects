/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.cllt.controller;

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
import com.tera.audit.loan.service.IContactService;
import com.tera.audit.loan.service.ILoanAppService;
import com.tera.audit.loan.service.ILoanBaseService;
import com.tera.feature.cllt.model.Cllt;
import com.tera.feature.cllt.service.IClltService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserExtService;
import com.tera.sys.service.UserService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 逾期报告控制器
 * <b>功能：</b>OverdueReportController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/cllt")
public class ClltController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ClltController.class);
	
	/**
	 * ClltService
	 */
	@Autowired(required=false) //自动注入
	private IClltService clltService;
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	@Autowired(required=false) //自动注入
	private UserService userService;	
	@Autowired(required=false) //自动注入
	private UserExtService userExtService;
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	@Autowired(required=false) //自动注入
	private IContactService contactService;
	@Autowired(required=false) //自动注入
	private ILoanAppService loanAppService;
	@Autowired(required=false) //自动注入
	private ILoanBaseService loanBaseService;
	@Autowired(required=false) //自动注入
	private IContractService contractService;
	
	/**
	 * 跳转到当前用户负责的，催收信息的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String clltQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "cllt/clltQuery";
	}

	/**
	 * 显示催收分单后，贷后人员所负责的催收列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String clltList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Cllt.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//判断机构和角色
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);	
		//如果不是管理员
		if (0 == loginUser.getIsAdmin()) {
			Map<String, Object> roleMap = new HashMap<String,Object>();
			roleMap.put("loginId",loginId);
			roleMap.put("orgId", loginOrg.getOrgId());
			List<Role> loginRoles = this.roleService.getRoleByOrgLoginId(roleMap);
			if (loginRoles != null && loginRoles.size() > 0) {
				for (Role role : loginRoles) {
					// 催收专员看自己的
					if (CommonConstant.ROLE_CLLT_STAFF.equals(role.getName()) && "1".equals(role.getFlag())) {
						queryMap.put("clltUid", loginUser.getLoginId());
					}
				}
			}
			/*queryMap.put("org", loginOrg.getOrgId());*/
		}
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Cllt> clltList = this.clltService.queryPageList(queryMap);
		pm.setData(clltList);
		pm.initRowsCount(clltList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "cllt/clltList";
	}
	
	/**
	 * 跳转到当前用户负责的，催收信息的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/queryAll.do")
	public String clltQueryAll(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "cllt/allClltQuery";
	}

	/**
	 * 显示催收分单后，贷后人员所负责的催收列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/listAll.do")
	public String clltListAll(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Cllt.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<Cllt> clltList = this.clltService.queryPageList(queryMap);
		pm.setData(clltList);
		pm.initRowsCount(clltList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "cllt/allClltList";
	}
	@RequestMapping("/listOrg.do")
	public void listWarehouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Org> warehouses = this.orgService.queryList(null);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(warehouses));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	@RequestMapping("/user/listUserByOrgAndRole.do")
	public void sysListUserByOrgAndRole(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		/*Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if("".equals(orgId)||orgId==null){
			orgId=org.getOrgId();
		}*/
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		List<User> users = userService.getUserByOrgAndRole(org.getOrgId(), new String[]{CommonConstant.ROLE_CLLT_STAFF},null);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(users));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	@RequestMapping("/allRead.do")
	public String loanBaseAllRead(String loanId,String contractId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanBase loanBase = this.loanBaseService.queryByLoanId(loanId);
		List<LoanApp> loanApps = null;
		List<Contact> contacts = null;
		//如果存在
		if (loanId!=null&&!loanId.equals("")) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			// 非续贷
			if ("0".equals(loanBase.getIsRenew()) || "2".equals(loanBase.getIsRenew())) {
				queryMap.put("loanId", loanId);
				contacts = this.contactService.queryByLoanId(loanId);
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
		LoanBase origLoanBase=this.loanBaseService.queryByLoanId(loanBase.getOrigLoanId());
		map.put("origLoanBase", origLoanBase);
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
		map.put("contractId", contractId);
		
		log.info(thisMethodName+":end");
		return "cllt/clltAllRead";
	}
}
