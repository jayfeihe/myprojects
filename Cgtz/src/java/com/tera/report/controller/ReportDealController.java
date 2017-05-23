/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.report.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.NetFunds;
import com.tera.report.model.ReportDeal;
import com.tera.report.service.IReportDealService;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleDataRelService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 分公司每月成交量控制器
 * <b>功能：</b>CollateralController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/report/reportDeal")
public class ReportDealController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ReportDealController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private IReportDealService reportDealService;
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
	/**
	 * 分公司每月成交量查询条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String reportDealQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "report/reportDeal/reportDealQuery";
	}

	/**
	 * 分公司每月成交量的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String reportDealList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(ReportDeal.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
		List<String> orgs=this.roleDataRelService.queryList(request);
		//orgs用于权限控制
		queryMap.put("orgs",orgs);
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果分配给分公司,则根据登录者所在分公司查询
		if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
			queryMap.put("orgId",loginOrg.getOrgId());
		}
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<ReportDeal> collateralList = this.reportDealService.queryPageList(queryMap);
		pm.setData(collateralList);
		pm.initRowsCount(collateralList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/reportDeal/reportDealList";
	}
	
	@RequestMapping("/excel.do")
	public ModelAndView sysUserExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		ReportDeal user = (ReportDeal) RequestUtils.getRequestBean(ReportDeal.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(user);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制
				beanMap.put("orgs",orgs);
				Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
				//如果分配给分公司,则根据登录者所在分公司查询
				if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
					beanMap.put("org",loginOrg.getOrgId());
				}
		List<ReportDeal> users = this.reportDealService.queryList(beanMap);
		String title = "分公司每月成交量统计表";
		String[] head = new String[]{"分公司","新增/续贷","年月","比数","金额/元"};
		Object[][] obj = new Object[users.size()][head.length];
		for (int i = 0; i < users.size(); i++) {
			ReportDeal myUser = users.get(i);
			Object[] values = new Object[head.length];
			values[0] = null != myUser.getOrgName() ? myUser.getOrgName() : "";
			values[1] = null != myUser.getType() ? myUser.getType() : "";
			values[2] = null != myUser.getMon() ? myUser.getMon() : "";
			values[3] = myUser.getCount();
			values[4] = myUser.getAmt();			
			obj[i] = values;
		}
		ExcelReportTable report = new ExcelReportTable(title, head, obj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("分公司每月成交量统计表.xls"), map);
	}
}
