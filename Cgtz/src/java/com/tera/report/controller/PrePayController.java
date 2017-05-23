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
import com.tera.report.model.BillAcctFlow;
import com.tera.report.service.IBillAcctFlowService;
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
 * 线下线上垫付控制器
 * <b>功能：</b>CollateralController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/report/prepay")
public class PrePayController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(PrePayController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private IBillAcctFlowService billAcctFlowService;
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
	
	/**
	 * 跳转到抵押物台账查询条件页面
	 * @param loanId
	 * @param opt 只读页面标识（read）
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String prepayQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "report/prepay/prepayQuery";
	}

	/**
	 * 显示抵押物台账的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String prepayList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(BillAcctFlow.class, request);
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
		/*queryMap.put("subject","付线上垫付");*/
		PageList<BillAcctFlow> collateralList = this.billAcctFlowService.queryPageList(queryMap);
		pm.setData(collateralList);
		pm.initRowsCount(collateralList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/prepay/prepayList";
	}
	
	@RequestMapping("/excel.do")
	public ModelAndView sysUserExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		BillAcctFlow user = (BillAcctFlow) RequestUtils.getRequestBean(BillAcctFlow.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(user);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制
				beanMap.put("orgs",orgs);
		List<BillAcctFlow> users = this.billAcctFlowService.queryList(beanMap);
		String title = "线下线上垫付表";
		String[] head = new String[]{"线上编号","线下编号","项目名称","期数","类别","金额/元","债权人","凭证号","借款人","操作人","操作时间","备注"};

		Object[][] obj = new Object[users.size()][head.length];
		for (int i = 0; i < users.size(); i++) {
			BillAcctFlow myUser = users.get(i);
			Object[] values = new Object[head.length];
			values[0] = null != myUser.getOnlineConId() ? myUser.getOnlineConId() : "";
			values[1] = null != myUser.getContractId() ? myUser.getContractId() : "";
			values[2] = null != myUser.getProjectName() ? myUser.getProjectName() : "";
			values[3] = null != myUser.getNum() ? myUser.getNum() : "";
			values[4] = null != myUser.getSubject() ? myUser.getSubject() : "";
			values[5]= myUser.getAmt();		
			values[6] = null != myUser.getLendName() ? myUser.getLendName() : "";
			values[7] = null != myUser.getLoanName() ? myUser.getLoanName() : "";
			values[8] = null != myUser.getProof() ? myUser.getProof() : "";		
			values[9] = null != myUser.getOperName() ? myUser.getOperName() : "";
			values[10] = null != myUser.getOperTimeStr() ? myUser.getOperTimeStr() : "";
			values[11] = null != myUser.getRemark() ? myUser.getRemark() : "";
			obj[i] = values;
		}
		// 条件
		List<String> conditionList = new ArrayList<String>();
		String orgName=null;
		String contractId=null;
		String projectName=null;
		String proof=null;
		StringBuffer range = new StringBuffer("统计区间：");
		if (StringUtils.isNotNullAndEmpty(user.getMinOperTimeStr())) {
			range.append(DateUtils.formatDate(DateUtils.getDate(user.getMinOperTimeStr()), "yyyy/MM/dd"));
		}
		if (StringUtils.isNotNullAndEmpty(user.getMaxOperTimeStr())) {
			range.append("-"+DateUtils.formatDate(DateUtils.getDate(user.getMaxOperTimeStr()), "yyyy/MM/dd"));
		}	
		conditionList.add(range.toString());
		//分公司
		if (StringUtils.isNotNullAndEmpty(user.getOrgId())) {
			orgName = "分公司："+orgService.queryByOrgId(user.getOrgId()).getOrgName();
			conditionList.add(orgName);
		}
		//合同号
		if (StringUtils.isNotNullAndEmpty(user.getContractId())) {
			contractId = "合同号："+user.getContractId();
			conditionList.add(contractId);
		}
		//项目名称
		if (StringUtils.isNotNullAndEmpty(user.getProjectName())) {
			projectName = "项目名称："+user.getProjectName();
			conditionList.add(projectName);
		}
		//凭证号
		if (StringUtils.isNotNullAndEmpty(user.getProof())) {
			proof = "凭证号："+user.getProof();
			conditionList.add(proof);
		}
		String[] condition = conditionList.toArray(new String[conditionList.size()]);
		ExcelReportTable report = new ExcelReportTable(title,condition, head, obj);

		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("线下线上垫付.xls"), map);
	}
	public String getSubjectName(String subject){
		/*(1收利息2收本金3续贷本金转移4放款付5付保证金)
		 * */
		if(subject.equals("1")){
			return "收利息";
		}else if(subject.equals("2")){
			return "收本金";
		}else if(subject.equals("3")){
			return "续贷本金转移";
		}else if(subject.equals("4")){
			return "放款付";
		}else if(subject.equals("5")){
			return "付保证金";
		}else{
			return "";
		}
	}
}
