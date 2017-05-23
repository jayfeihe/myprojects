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
import com.tera.report.model.CollateralAccountBean;
import com.tera.report.model.NetFunds;
import com.tera.report.service.INetFundsService;
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
import com.tera.util.StringUtils;

/**
 * 
 * 网络下放资金控制器
 * <b>功能：</b>CollateralController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/report/netFunds")
public class NetFundsController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(NetFundsController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private INetFundsService netFundsService;
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
	
	
	/**
	 * 网络下放资金查询条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String netFundsQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "report/netFunds/netFundsQuery";
	}

	/**
	 * 网络下放资金的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String netFundsList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(NetFunds.class, request);
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
		PageList<NetFunds> collateralList = this.netFundsService.queryPageList(queryMap);
		pm.setData(collateralList);
		pm.initRowsCount(collateralList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/netFunds/netFundsList";
	}
	
	@RequestMapping("/excel.do")
	public ModelAndView sysUserExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		NetFunds user = (NetFunds) RequestUtils.getRequestBean(NetFunds.class, request);
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
		List<NetFunds> users = this.netFundsService.queryList(beanMap);
		String title = "网络下放资金明细表";
		String[] head = new String[]{"线上编号","线下编号","项目名称","借款人","放款时间","债权人","放款金额/元","卡号","银行","备注"};

		Object[][] obj = new Object[users.size()][head.length];
		for (int i = 0; i < users.size(); i++) {
			NetFunds myUser = users.get(i);
			Object[] values = new Object[head.length];
			values[0] = null != myUser.getOnlineConId() ? myUser.getOnlineConId() : "";
			values[1] = null != myUser.getContractId() ? myUser.getContractId() : "";
			values[2] = null != myUser.getProjectName() ? myUser.getProjectName() : "";
			values[3] = null != myUser.getLoanName() ? myUser.getLoanName() : "";
			values[4] = null != myUser.getCreateTime() ? DateUtils.formatDate(myUser.getCreateTime(),"yyyy/MM/dd") : "";
			values[5] = null != myUser.getLendName() ? myUser.getLendName() : "";
			values[6] =  myUser.getAmt();
			values[7] = null != myUser.getAcctCode() ? myUser.getAcctCode() : "";
			values[8] = null != myUser.getAcctBank() ? myUser.getAcctBank() : "";
			values[9] = null != myUser.getRemark() ? myUser.getRemark() : "";
			
			obj[i] = values;
		}
		// 条件
		List<String> conditionList = new ArrayList<String>();
		String acctCode=null;
		String loanType=null;
		StringBuffer range = new StringBuffer("统计区间：");
		if (StringUtils.isNotNullAndEmpty(user.getMinCreateTimeStr())) {
			range.append(DateUtils.formatDate(DateUtils.getDate(user.getMinCreateTimeStr()), "yyyy/MM/dd"));
		}
		if (StringUtils.isNotNullAndEmpty(user.getMaxCreateTimeStr())) {
			range.append("-"+DateUtils.formatDate(DateUtils.getDate(user.getMaxCreateTimeStr()), "yyyy/MM/dd"));
		}	
		conditionList.add(range.toString());
		//债权人
		if (StringUtils.isNotNullAndEmpty(user.getAcctCode())) {
			acctCode = "债权人："+user.getAcctCode();
			conditionList.add(acctCode);
		}
		//贷款类别
		if (StringUtils.isNotNullAndEmpty(user.getLoanType())) {
			if ("01".equals(user.getLoanType())) {
				loanType = "贷款类别：个人";
			} else if ("02".equals(user.getLoanType())) {
				loanType = "贷款类别：公司";
			}
			conditionList.add(loanType);
		}
		String[] condition = conditionList.toArray(new String[conditionList.size()]);
		
		ExcelReportTable report = new ExcelReportTable(title,condition, head, obj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("网络下放资金明细表.xls"), map);
	}
}
