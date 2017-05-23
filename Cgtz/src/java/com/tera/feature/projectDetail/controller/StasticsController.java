package com.tera.feature.projectDetail.controller;

/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */


import java.util.ArrayList;
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
import com.tera.feature.projectDetail.model.StasticsBean;
import com.tera.feature.projectDetail.service.IStasticsService;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleDataRelService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * StasticsController控制器
 * <b>功能：</b>StasticsController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-05 10:51:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/stastics")
public class StasticsController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(StasticsController.class);
	
	/**
	 * projectInfoService
	 */
	@Autowired(required=false) //自动注入
	private IStasticsService stasticsService;
	
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
//=========分公司每月成交量统计控制器=============//	
	/**
	 * 跳转到T_PROJECT_DETAIL的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/monDeal/query.do")
	public String staticsMonDealQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "stastics/monDeal/monDealQuery";
	}

	/**
	 * 显示T_projectDetail的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/monDeal/list.do")
	public String staticsMonDealList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(StasticsBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制`
				queryMap.put("orgs",orgs);
				Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
				//如果分配给分公司,则根据登录者所在分公司查询
				if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
					queryMap.put("org",loginOrg.getOrgId());
				}		
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		/*//分公司查自己的
		User loginUser=(User)request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if(loginUser.getIsAdmin()==0&&loginUser.getState().equals("1")){
			queryMap.put("org",loginOrg.getOrgId());
		}*/
		PageList<StasticsBean> projectDetailList = this.stasticsService.queryMonDealsPageList(queryMap);
		pm.setData(projectDetailList);
		pm.initRowsCount(projectDetailList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "stastics/monDeal/monDealList";
	}

	/**
	 * 导出录入的历史数据excel
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/monDeal/excel.do")
	public ModelAndView staticsMonDealExport(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		StasticsBean bean = (StasticsBean) RequestUtils.getRequestBean(StasticsBean.class, request);
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
		List<StasticsBean> list = this.stasticsService.queryMonDealsList(queryMap);
		
		String title = "分公司每月成交量历史统计";
		String[] head = new String[] { "序号","分公司", "年月", "笔数/笔", "金额/元"};
		Object[][] obj = new Object[list.size()][head.length];
		for (int i = 0; i < list.size(); i++) {
			StasticsBean tmpBean = list.get(i);
			Object[] values = new Object[head.length];
			values[0]=i+1;
			values[1] = null != tmpBean.getOrgName() ? tmpBean.getOrgName() : "";
			values[2] = null != tmpBean.getMon() ? tmpBean.getMon() : "";
			values[3] = tmpBean.getCounts();
			values[4] = tmpBean.getLoanAmt();	
			obj[i] = values;
		}
		
		// 条件
		List<String> conditionList = new ArrayList<String>();
		String org = null;
		
		/*StringBuffer range = new StringBuffer();
		if (bean.getMinStartDate()!=null) {
			range.append("统计区间：");
			range.append(DateUtils.getDate(DateUtils.formatDate((bean.getMinStartDate())), "yyyy/MM/dd"));
		}
		if (bean.getMaxStartDate()!=null) {
			range.append(" - "+DateUtils.getDate(DateUtils.formatDate((bean.getMaxStartDate())), "yyyy/MM/dd"));
		}
		
		conditionList.add(range.toString());*/
		
		if (StringUtils.isNotNullAndEmpty(bean.getOrg())) {
			org = "分公司："+this.orgService.queryByOrgId(bean.getOrg()).getOrgName();
			conditionList.add(org);
		}
		
		
		String[] condition = conditionList.toArray(new String[conditionList.size()]);
		
		// 统计数据
		double totalLoanAmt = 0.0;//总金额
		int counts=0;//总成交数
		for (int i = 0; i < obj.length; i++) {
			double loanAmt = (Double) obj[i][4];
			int count=(Integer)obj[i][3];
			totalLoanAmt = MathUtils.add(totalLoanAmt, loanAmt);
			counts=count+counts;
		}
		
		Object[][] statisticData = new Object[1][head.length];
		for (int i = 0; i < statisticData[0].length; i++) {
			if (i == 0) {
				statisticData[0][i] = "合计";
			}else if (i == 3) {
				statisticData[0][i] = counts;
			}else if (i == 4) {
				statisticData[0][i] = totalLoanAmt;
			} else {
				statisticData[0][i] = "";
			}
		}
		
		ExcelReportTable report = new ExcelReportTable(title, condition, head, obj, statisticData);
		
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("分公司每月成交量历史统计.xls"), map);
	}


	
	
//=========分公司成交量统计控制器=============//	
/**
 * 跳转到T_PROJECT_DETAIL的查询条件页面
 * @param request request
 * @param map map
 * @throws Exception exception
 * @return string
 */
@RequestMapping("/deals/query.do")
public String staticsDealsQuery(HttpServletRequest request, ModelMap map) throws Exception {
	String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	log.info(thisMethodName+":start");
	Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
	//如果是分公司需要用于页面显示
	map.put("loginOrgName",loginOrg.getOrgName());
	log.info(thisMethodName+":end");
	return "stastics/deals/dealsQuery";
}

/**
 * 显示T_projectDetail的查询列表
 * @param request request
 * @param map map
 * @throws Exception exception
 * @return string
 */
@RequestMapping("/deals/list.do")
public String staticsDealsList(HttpServletRequest request, ModelMap map) throws Exception {
	String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	log.info(thisMethodName+":start");
	PageModel pm = new PageModel();
	Object bean = RequestUtils.getRequestBean(StasticsBean.class, request);
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
	/*//分公司查自己的
	User loginUser=(User)request.getSession().getAttribute(SysConstants.LOGIN_USER);
	Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
	if(loginUser.getIsAdmin()==0&&loginUser.getState().equals("1")){
		queryMap.put("org",loginOrg.getOrgId());
	}*/
	PageList<StasticsBean> projectDetailList = this.stasticsService.queryDealsPageList(queryMap);
	pm.setData(projectDetailList);
	pm.initRowsCount(projectDetailList.getPaginator().getTotalCount());
	map.put("pm", pm);
	log.info(thisMethodName+":end");
	return "stastics/deals/dealsList";
}

/**
 * 导出录入的历史数据excel
 * @param request request
 * @param response response
 * @param map map
 * @throws Exception exception
 * @return string
 */
@RequestMapping("/deals/excel.do")
public ModelAndView staticsMonthDealExport(HttpServletRequest request, ModelMap map) throws Exception {
	String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	log.info(thisMethodName+":start");
	
	StasticsBean bean = (StasticsBean) RequestUtils.getRequestBean(StasticsBean.class, request);
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
	List<StasticsBean> list = this.stasticsService.queryDealsList(queryMap);
	
	String title = "分公司成交量历史统计";
	String[] head = new String[] { "序号","分公司","项目", "笔数/笔", "金额/元"};
	Object[][] obj = new Object[list.size()][head.length];
	for (int i = 0; i < list.size(); i++) {
		StasticsBean tmpBean = list.get(i);
		Object[] values = new Object[head.length];
		values[0]=i+1;
		values[1] = null != tmpBean.getOrgName() ? tmpBean.getOrgName() : "";
		values[2] = null != tmpBean.getType() ? tmpBean.getType() : "";
		values[3] = tmpBean.getCounts();
		values[4] = tmpBean.getLoanAmt();	
		obj[i] = values;
	}
	
	// 条件
	List<String> conditionList = new ArrayList<String>();
	String org = null;
	
	/*StringBuffer range = new StringBuffer();
	if (bean.getMinStartDate()!=null) {
		range.append("统计区间：");
		range.append(DateUtils.getDate(DateUtils.formatDate((bean.getMinStartDate())), "yyyy/MM/dd"));
	}
	if (bean.getMaxStartDate()!=null) {
		range.append(" - "+DateUtils.getDate(DateUtils.formatDate((bean.getMaxStartDate())), "yyyy/MM/dd"));
	}
	
	conditionList.add(range.toString());*/
	
	if (StringUtils.isNotNullAndEmpty(bean.getOrg())) {
		org = "分公司："+this.orgService.queryByOrgId(bean.getOrg()).getOrgName();
		conditionList.add(org);
	}
	
	
	String[] condition = conditionList.toArray(new String[conditionList.size()]);
	
	// 统计数据
	double totalLoanAmt = 0.0;//总金额
	int counts=0;//总成交数
	for (int i = 0; i < obj.length; i++) {
		double loanAmt = (Double) obj[i][4];
		int count=(Integer)obj[i][3];
		totalLoanAmt = MathUtils.add(totalLoanAmt, loanAmt);
		counts=count+counts;
	}
	
	Object[][] statisticData = new Object[1][head.length];
	for (int i = 0; i < statisticData[0].length; i++) {
		if (i == 0) {
			statisticData[0][i] = "合计";
		}else if (i == 3) {
			statisticData[0][i] = counts;
		}else if (i == 4) {
			statisticData[0][i] = totalLoanAmt;
		} else {
			statisticData[0][i] = "";
		}
	}
	
	ExcelReportTable report = new ExcelReportTable(title, condition, head, obj, statisticData);
	
	map.addAttribute(ReportConstants.REPORT, report);
	log.info(thisMethodName+":end");
	return new ModelAndView(new ExcelView("分公司成交量历史统计.xls"), map);
}	

//=========分公司存量统计控制器=============//	
	/**
	 * 跳转到T_PROJECT_DETAIL的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/saveDeals/query.do")
	public String staticsSaveDealsQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "stastics/saveDeals/saveDealsQuery";
	}

	/**
	 * 显示T_projectDetail的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/saveDeals/list.do")
	public String staticsSaveDealsList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(StasticsBean.class, request);
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
		/*//分公司查自己的
		User loginUser=(User)request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if(loginUser.getIsAdmin()==0&&loginUser.getState().equals("1")){
			queryMap.put("org",loginOrg.getOrgId());
		}*/
		PageList<StasticsBean> projectDetailList = this.stasticsService.querySaveDealsPageList(queryMap);
		pm.setData(projectDetailList);
		pm.initRowsCount(projectDetailList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "stastics/saveDeals/saveDealsList";
	}

	/**
	 * 导出录入的历史数据excel
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/saveDeals/excel.do")
	public ModelAndView staticsSaveDealsExport(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		StasticsBean bean = (StasticsBean) RequestUtils.getRequestBean(StasticsBean.class, request);
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
		List<StasticsBean> list = this.stasticsService.querySaveDealsList(queryMap);
		
		String title = "分公司存量历史统计";
		String[] head = new String[] { "序号","分公司","产品","笔数/笔", "金额/元"};
		Object[][] obj = new Object[list.size()][head.length];
		for (int i = 0; i < list.size(); i++) {
			StasticsBean tmpBean = list.get(i);
			Object[] values = new Object[head.length];
			values[0]=i+1;
			values[1] = null != tmpBean.getOrgName() ? tmpBean.getOrgName() : "";
			values[2] = null != tmpBean.getType() ? tmpBean.getType() : "";
			values[3] = tmpBean.getCounts();
			values[4] = tmpBean.getLoanAmt();	
			obj[i] = values;
		}
		
		// 条件
		List<String> conditionList = new ArrayList<String>();
		String org = null;
		
		/*StringBuffer range = new StringBuffer();
		if (bean.getMinStartDate()!=null) {
			range.append("统计区间：");
			range.append(DateUtils.getDate(DateUtils.formatDate((bean.getMinStartDate())), "yyyy/MM/dd"));
		}
		if (bean.getMaxStartDate()!=null) {
			range.append(" - "+DateUtils.getDate(DateUtils.formatDate((bean.getMaxStartDate())), "yyyy/MM/dd"));
		}
		
		conditionList.add(range.toString());*/
		
		if (StringUtils.isNotNullAndEmpty(bean.getOrg())) {
			org = "分公司："+this.orgService.queryByOrgId(bean.getOrg()).getOrgName();
			conditionList.add(org);
		}
		
		
		String[] condition = conditionList.toArray(new String[conditionList.size()]);
		
		// 统计数据
		double totalLoanAmt = 0.0;//总金额
		int counts=0;//总成交数
		for (int i = 0; i < obj.length; i++) {
			double loanAmt = (Double) obj[i][4];
			int count=(Integer)obj[i][3];
			totalLoanAmt = MathUtils.add(totalLoanAmt, loanAmt);
			counts=count+counts;
		}
		
		Object[][] statisticData = new Object[1][head.length];
		for (int i = 0; i < statisticData[0].length; i++) {
			if (i == 0) {
				statisticData[0][i] = "合计";
			}else if (i == 3) {
				statisticData[0][i] = counts;
			}else if (i == 4) {
				statisticData[0][i] = totalLoanAmt;
			} else {
				statisticData[0][i] = "";
			}
		}
		
		ExcelReportTable report = new ExcelReportTable(title, condition, head, obj, statisticData);
		
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("分公司存量历史统计.xls"), map);
	}		
		
	
}
