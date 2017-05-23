/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.search.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.retplan.model.RetPlan;
import com.tera.audit.retplan.service.IRetPlanService;
import com.tera.feature.search.model.AfterLoanSearchBean;
import com.tera.feature.search.service.IAfterLoanSearchService;
import com.tera.report.model.BillAcctFlow;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
/**
 * 
 *功能:AfterLoanSearchController  稽查清单控制器
 *时间:2016年3月7日上午10:10:05
 *@author Ldh
 */
@Controller
@RequestMapping("/search/afterloan")
public class AfterLoanSearchController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(AfterLoanSearchController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private IAfterLoanSearchService afterLoanSearchService;
	
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	
	@Autowired(required=false) //自动注入
	private IRetPlanService retPlanService;
	
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	
	/**
	 * 跳转到贷后查询条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String collateralQuery(String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "search/afterLoanQuery";
	}

	/**
	 * 显示贷后查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String collateralList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		
		AfterLoanSearchBean bean =(AfterLoanSearchBean) RequestUtils.getRequestBean(AfterLoanSearchBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);	
		if (0 == loginUser.getIsAdmin()) {
			Map<String, Object> roleMap = new HashMap<String,Object>();
			roleMap.put("loginId",loginId);
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
			if(!"86".equals(loginOrg.getOrgId())&&"86"!=loginOrg.getOrgId()){
				queryMap.put("org", loginOrg.getOrgId());
			}		
		}
		List<AfterLoanSearchBean> searchList=this.afterLoanSearchService.queryList(queryMap);
		//逾期合同笔数
		map.put("conNum",this.afterLoanSearchService.queryLateConCount(queryMap));
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<AfterLoanSearchBean> collateralList = this.afterLoanSearchService.queryPageList(queryMap);
		pm.setData(collateralList);
		pm.initRowsCount(collateralList.getPaginator().getTotalCount());
		//逾期总额
		double sumRetInterest=0;
		double sumRetCapital=0;
		for (AfterLoanSearchBean afterLoanSearchBean : searchList) {
			sumRetInterest=MathUtils.add(sumRetInterest,afterLoanSearchBean.getRetInterest());
			sumRetCapital=MathUtils.add(sumRetCapital,afterLoanSearchBean.getRetCapital());
		}
		map.put("sumRetInterest", sumRetInterest);
		map.put("sumRetCapital", sumRetCapital);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "search/afterLoanList";
	}
	//生成稽查任务/清单报表
	@RequestMapping("/excel.do")
	public ModelAndView sysUserExcel(String type,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		AfterLoanSearchBean user = (AfterLoanSearchBean) RequestUtils.getRequestBean(AfterLoanSearchBean.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(user);
		List<AfterLoanSearchBean> users=new ArrayList<AfterLoanSearchBean>();
		String title=null;
		int coNum=0;
		if(StringUtils.isNotNullAndEmpty(type)&&type.equals("task")){
			users=this.afterLoanSearchService.queryTaskList(beanMap);
			title="稽查任务";
		}else{
			users = this.afterLoanSearchService.queryList(beanMap);
			//逾期笔数
			coNum=this.afterLoanSearchService.queryLateConCount(beanMap);
			title = "稽查清单";
		}	 
		String[] head = new String[]{"线下编号","项目名称","分公司/部门名称","开始时间","应收时间","类别","项目金额/元","逾期金额/元","业务经办人","借款人","处理情况(最新)","处理说明","是否提交报告","是否受理"};
		Object[][] obj = new Object[users.size()][head.length];
		for (int i = 0; i < users.size(); i++) {
			AfterLoanSearchBean myUser = users.get(i);
			Object[] values = new Object[head.length];
			values[0] = null != myUser.getContractId() ? myUser.getContractId() : "";
			values[1] = null != myUser.getProductType() ? myUser.getProductType() : "";
			values[2] = null != myUser.getOrgName() ? myUser.getOrgName() : "";
			values[3] = null != myUser.getStartDate() ? DateUtils.formatDate(myUser.getStartDate(),"yyyy/MM/dd") : "";
			values[4] = null != myUser.getRetDate() ?  DateUtils.formatDate(myUser.getRetDate(),"yyyy/MM/dd") : "";
			values[5] = null != myUser.getOverType() ? myUser.getOverType() : "";
			values[6] = myUser.getLoanAmt();
			values[7] = myUser.getRetInterest()+myUser.getRetCapital();	
			values[8] = null != myUser.getSaleName() ? myUser.getSaleName() : "";
			values[9] = null != myUser.getLoanName() ? myUser.getLoanName() : "";
			values[10] = null != myUser.getCsState() ? myUser.getCsState() : "";
			values[11] = null != myUser.getRemark() ? myUser.getRemark() : "";
			values[12] = null != myUser.getCrState() ? myUser.getCrState().equals("未提交")?"否":"是":"";
			values[13] = null != myUser.getAcceptState() ? myUser.getAcceptState().equals("未受理")?"否":"是":"";
			obj[i] = values;
		}
		// 条件
		List<String> conditionList = new ArrayList<String>();
		String orgName=null;
		//分公司
		if (StringUtils.isNotNullAndEmpty(user.getOrg())) {
			orgName = "分公司："+orgService.queryByOrgId(user.getOrg()).getOrgName();
			conditionList.add(orgName);
		}
		//逾期金额统计:逾期笔数/逾期总额
		double sumRetAll=0;
		for (int i = 0; i < obj.length; i++) {
			sumRetAll=MathUtils.add(sumRetAll,(Double)obj[i][7]);
		}
		Object[][] sumObj=new Object[2][head.length];
		for (int i = 0; i < sumObj[0].length; i++) {
			if(i==0){
				sumObj[0][i]="逾期金额合计/元";
			}else if(i==1){
				sumObj[0][i]=sumRetAll;
			}else{
				sumObj[0][i]="";
			}
		}
		if(title.equals("稽查清单")){
			for (int i = 0; i < sumObj[1].length; i++) {
				if(i==0){
					sumObj[1][i]="逾期笔数合计/笔";
				}else if(i==1){
					sumObj[1][i]=coNum;
				}else{
					sumObj[1][i]="";
				}
			}
		}	
		String[] condition = conditionList.toArray(new String[conditionList.size()]);
		ExcelReportTable report = new ExcelReportTable(title,condition, head, obj,sumObj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView(title+".xls"), map);
	}	
	
}
