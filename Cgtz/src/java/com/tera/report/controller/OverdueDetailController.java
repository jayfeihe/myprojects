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
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleDataRelService;
import com.tera.sys.service.RoleService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.DateUtils;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;


/**
 *功能:OverdueDetailController  逾期明细控制器
 *时间:2016年3月7日上午10:57:10
 *@author Ldh
 */
@Controller
@RequestMapping("/report/overdueDetail")
public class OverdueDetailController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(OverdueDetailController.class);
	
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
	
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
	
	/**
	 * 跳转到逾期明细查询条件页面
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
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "report/overdueDetail/overdueDetailQuery";
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
		
		/*User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);*/	
		/*if (0 == loginUser.getIsAdmin()) {
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
		}*/
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
		PageList<AfterLoanSearchBean> searchList = this.afterLoanSearchService.queryDetailPageList(queryMap);
		pm.setData(searchList);
		pm.initRowsCount(searchList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/overdueDetail/overdueDetailList";
	}
	//生成逾期明细报表
		@RequestMapping("/excel.do")
		public ModelAndView sysUserExcel(String type,HttpServletRequest request, ModelMap map) throws Exception {
			String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			log.info(thisMethodName+":start");
			AfterLoanSearchBean user = (AfterLoanSearchBean) RequestUtils.getRequestBean(AfterLoanSearchBean.class, request);
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
			List<AfterLoanSearchBean> users=new ArrayList<AfterLoanSearchBean>();
			users=this.afterLoanSearchService.queryTaskList(beanMap);
			String title="逾期明细表";
			String[] head = new String[]{"线下编号","项目名称","分公司/部门名称","开始时间","应收时间","类别","项目金额/元","逾期金额/元","业务经办人","借款人"};
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
				obj[i] = values;
			}
			// 条件
			List<String> conditionList = new ArrayList<String>();
			String orgName=null;
			String overdueType=null;
			//逾期区间
			StringBuffer sbu=new StringBuffer("(应收)统计区间：");
			if(user.getMinRetDate()!=null){
				sbu.append(DateUtils.formatDate(user.getMinRetDate(),"yyyy/MM/dd"));
			}
			if(user.getMaxRetDate()!=null){
				sbu.append("-"+DateUtils.formatDate(user.getMaxRetDate(),"yyyy/MM/dd"));
			}
			conditionList.add(sbu.toString());
			//分公司
			if (StringUtils.isNotNullAndEmpty(user.getOrg())) {
				orgName = "分公司："+orgService.queryByOrgId(user.getOrg()).getOrgName();
				conditionList.add(orgName);
			}
			//逾期类别
			if(StringUtils.isNotNullAndEmpty(user.getOverdueType())){
				overdueType=user.getOverdueType().equals("1")?"利息逾期":user.getOverdueType().equals("2")?"本金逾期":"";
			}
			conditionList.add("逾期类型:"+overdueType);
			//逾期金额统计
			double sumRet=0;
			for (int i = 0; i < obj.length; i++) {
				sumRet=MathUtils.add(sumRet,(Double)obj[i][7]);
			}
			Object[][] sumObj=new Object[1][head.length];
			for (int i = 0; i < sumObj[0].length; i++) {
				if(i==0){
					sumObj[0][i]="逾期金额合计";
				}else if(i==7){
					sumObj[0][i]=sumRet;
				}else{
					sumObj[0][i]="";
				}
			}	
			String[] condition = conditionList.toArray(new String[conditionList.size()]);
			ExcelReportTable report = new ExcelReportTable(title,condition, head, obj,sumObj);
			map.addAttribute(ReportConstants.REPORT, report);
			log.info(thisMethodName+":end");
			return new ModelAndView(new ExcelView(title+".xls"), map);
		}	
}
