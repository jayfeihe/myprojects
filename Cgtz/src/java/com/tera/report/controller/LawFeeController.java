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
import com.tera.audit.retplan.model.RetPlan;
import com.tera.report.model.LawFee;
import com.tera.report.model.ReportOverdue;
import com.tera.report.service.ILawFeeService;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleDataRelService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.DateUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 法律服务费统计表
 * <b>功能：</b>LawFeeController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/report/lawFee")
public class LawFeeController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LawFeeController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private ILawFeeService lawFeeService;
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
	
	
	/**
	 * 跳转到法律服务费查询条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String reportOverdueQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "report/lawFee/lawFeeQuery";
	}

	/**
	 * 显示法律服务费的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String reportOverdueList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LawFee.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
		List<String> orgs=this.roleDataRelService.queryList(request);
		//orgs用于权限控制
		queryMap.put("orgs",orgs);
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果分配给分公司,则根据登录者所在分公司查询
		if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
			queryMap.put("org",loginOrg.getOrgId());
		}
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		queryMap.put("num", 1);
		PageList<LawFee> lawFeeList = this.lawFeeService.queryPageList(queryMap);
		pm.setData(lawFeeList);
		pm.initRowsCount(lawFeeList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/lawFee/lawFeeList";
	}
	
	@RequestMapping("/excel.do")
	public ModelAndView sysUserExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Object user = (LawFee) RequestUtils.getRequestBean(LawFee.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(user);
		beanMap.put("num", 1);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制
				beanMap.put("orgs",orgs);
				Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
				//如果分配给分公司,则根据登录者所在分公司查询
				if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
					beanMap.put("org",loginOrg.getOrgId());
				}
		List<LawFee> users = this.lawFeeService.queryList(beanMap);
		String title = "法律服务费统计表";
		String[] head = new String[]{"申请编号","合同编号","期数","当月应收法律服务费/元","当月实收法律服务费/元","合同开始日期"};
		Object[][] obj = new Object[users.size()][head.length];
		for (int i = 0; i < users.size(); i++) {
			LawFee myUser = users.get(i);
			Object[] values = new Object[head.length];
			values[0] = null != myUser.getLoanId() ? myUser.getLoanId() : "";
			values[1] = null != myUser.getContractId() ? myUser.getContractId() : "";
			values[2] =  myUser.getNum();
			values[3] =  myUser.getPlanLawFee();
			values[4] =  myUser.getRealLawFee();
			values[5] = null != myUser.getRetDate() ?DateUtils.formatDate(myUser.getRetDate(),"yyyy/MM/dd") : "";
			
			obj[i] = values;
		}
		ExcelReportTable report = new ExcelReportTable(title, head, obj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("法律服务费统计表.xls"), map);
	}
}
