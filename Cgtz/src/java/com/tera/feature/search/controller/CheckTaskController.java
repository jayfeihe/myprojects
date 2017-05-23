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
import com.tera.audit.retplan.model.RetPlan;
import com.tera.audit.retplan.service.IRetPlanService;
import com.tera.feature.search.model.AfterLoanSearchBean;
import com.tera.feature.search.service.IAfterLoanSearchService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 *功能:CheckTaskController  稽查任务控制器
 *时间:2016年3月7日上午10:43:29
 *@author Ldh
 */
@Controller
@RequestMapping("/search/task")
public class CheckTaskController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CheckTaskController.class);
	
	@Autowired(required=false) //自动注入
	private IAfterLoanSearchService afterLoanSearchService;
	
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	
	@Autowired(required=false) //自动注入
	private IRetPlanService retPlanService;
	
	/**
	 * 跳转到稽查任务查询条件页面
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
		return "search/checkTaskQuery";
	}

	/**
	 * 显示稽查任务查询列表
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
		List<AfterLoanSearchBean> taskList=this.afterLoanSearchService.queryTaskList(queryMap);;
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<AfterLoanSearchBean> searchList = this.afterLoanSearchService.queryTaskPageList(queryMap);
		pm.setData(searchList);
		pm.initRowsCount(searchList.getPaginator().getTotalCount());	
		//当期逾期总金额
		double sumRetInterest=0;
		double sumRetCapital=0;
		//历史逾期总金额
		/*double sumInterestBe=0;*/
		for (AfterLoanSearchBean afterLoanSearchBean : taskList) {
			sumRetInterest=MathUtils.add(sumRetInterest,afterLoanSearchBean.getRetInterest());
			sumRetCapital=MathUtils.add(sumRetCapital,afterLoanSearchBean.getRetCapital());
			/*sumInterestBe=MathUtils.add(sumInterestBe,afterLoanSearchBean.getSumInterestBe());*/
		}
		map.put("sumRetInterest", sumRetInterest);
		map.put("sumRetCapital", sumRetCapital);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "search/checkTaskList";
	}
}
