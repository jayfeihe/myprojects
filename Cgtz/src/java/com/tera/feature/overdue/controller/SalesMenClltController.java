/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.overdue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.feature.overdue.model.SalesMenCllt;
import com.tera.feature.overdue.service.ISalesMenClltService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 *功能:SalesMenClltController  业务员、分公司逾期处理控制器
 *时间:2016年3月7日上午10:49:33
 *@author Ldh
 */
@Controller
@RequestMapping("/overdue")
public class SalesMenClltController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(SalesMenClltController.class);
	
	/**
	 * OverdueReportService
	 */
	@Autowired(required=false) //自动注入
	private ISalesMenClltService salesMenClltService;
	
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	
	/**
	 * 跳转到逾期报告的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String overdueReportQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "overdue/salesMenClltQuery";
	}

	/**
	 * 显示逾期报告的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String overdueReportList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(SalesMenCllt.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//判断机构和角色
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);	
		if (0 == loginUser.getIsAdmin()) {
			Map<String, Object> roleMap = new HashMap<String,Object>();
			roleMap.put("loginId",loginId);
			roleMap.put("orgId", loginOrg.getOrgId());
			List<Role> loginRoles = this.roleService.getRoleByOrgLoginId(roleMap);
			if (loginRoles != null && loginRoles.size() > 0) {
				for (Role role : loginRoles) {
					// 是业务员自己看自己的
					if (CommonConstant.ROLE_SALESMAN.equals(role.getName()) && "1".equals(role.getFlag())) {
						queryMap.put("salesman",loginId);
						map.put("roleSale",CommonConstant.ROLE_SALESMAN);
					}
				}
			}
			queryMap.put("org", loginOrg.getOrgId());
			map.put("admin","0");
		}else{
			map.put("admin","1");
		}
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<SalesMenCllt> assetList = this.salesMenClltService.queryPageList(queryMap);
		pm.setData(assetList);
		pm.initRowsCount(assetList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "overdue/salesMenClltList";
	}
	
}
