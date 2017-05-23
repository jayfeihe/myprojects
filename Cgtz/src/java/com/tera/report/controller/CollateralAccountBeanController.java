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
import com.tera.report.model.CollateralAccountBean;
import com.tera.report.service.ICollateralAccountBeanService;
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
 * 抵押物台账控制器
 * <b>功能：</b>CollateralAccountBeanController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/report/collateralAccount")
public class CollateralAccountBeanController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollateralAccountBeanController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private ICollateralAccountBeanService collateralAccountBeanService;
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
	public String collateralQuery(String loanId,String origLoanId,String opt,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "report/collateralAccount/collateralAccountQuery";
	}

	/**
	 * 显示抵押物台账的查询列表
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
		Object bean = RequestUtils.getRequestBean(CollateralAccountBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean); 
		/*Org org=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if(org.getOrgId()!="86"&&!org.getOrgId().equals("86")){
			queryMap.put("orgId",org.getOrgId());
		}*/
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
		PageList<CollateralAccountBean> collateralList = this.collateralAccountBeanService.queryPageList(queryMap);
		pm.setData(collateralList);
		pm.initRowsCount(collateralList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/collateralAccount/collateralAccountList";
	}
	
	@RequestMapping("/excel.do")
	public ModelAndView sysUserExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollateralAccountBean user = (CollateralAccountBean) RequestUtils.getRequestBean(CollateralAccountBean.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(user);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制
				beanMap.put("orgs",orgs);
				Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
				//如果分配给分公司,则根据登录者所在分公司查询
				if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
					beanMap.put("orgId",loginOrg.getOrgId());
				}
		List<CollateralAccountBean> users = this.collateralAccountBeanService.queryList(beanMap);
		String title = "抵押物台账";
		String[] head = new String[]{"项目类型", "线下合同编号", "分公司", "借款人", "出借人", "借款金额/万元","出借时间","还款时间","年化利率","车牌号","房产证号","房产/车库/仓库地址","评估价/万元","押品状态","备注"};

		Object[][] obj = new Object[users.size()][head.length];
		for (int i = 0; i < users.size(); i++) {
			CollateralAccountBean myUser = users.get(i);
			Object[] values = new Object[head.length];
			values[0] = null != myUser.getProduct() ? getProductName(myUser.getProduct()) : "";
			values[1] = null != myUser.getContractId() ? myUser.getContractId() : "";
			values[2] = null != myUser.getOrgName() ? myUser.getOrgName() : "";
			values[3] = null != myUser.getLoanName() ? myUser.getLoanName() : "";
			values[4] = null != myUser.getLendName() ? myUser.getLendName() : "";
			values[5] =myUser.getLoanAmt();
			values[6] = null != myUser.getStartDate() ?DateUtils.formatDate(myUser.getStartDate(),"yyyy/MM/dd") : "";
			values[7] = null != myUser.getEndDate() ? DateUtils.formatDate(myUser.getEndDate(),"yyyy/MM/dd") : "";
			values[8] = myUser.getRate()+"%";
			values[9] = null != myUser.getLicense() ? myUser.getLicense() : "";			
			values[10]= null != myUser.getHousePropertyCode() ? myUser.getHousePropertyCode() : "";			
			values[11] = null != myUser.getAssetAddr() ? myUser.getAssetAddr() : "";
			values[12] = myUser.getEvalPrice();
			values[13] = myUser.getColState();
			values[14] = null != myUser.getRemark() ? myUser.getRemark() : "";
			obj[i] = values;
		}
		ExcelReportTable report = new ExcelReportTable(title, head, obj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("抵押物台账.xls"), map);
	}
	public String getProductName(String product){
		String productName="";
		if(product.equals("01")){
			productName="车贷";
		}else if(product.equals("02")){
			productName="车商贷";
		}else if(product.equals("03")){
			productName="房贷";
		}else if(product.equals("04")){
			productName="红木贷";
		}else if(product.equals("05")){
			productName="海鲜贷";
		}else{
			productName="其他";
		}
		return productName;
	}

}
