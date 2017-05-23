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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.NumberFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.model.ReportDeal;
import com.tera.report.model.ReportOverdue;
import com.tera.report.service.IReportOverdueService;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleDataRelService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.MathUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 分公司逾期统计表
 * <b>功能：</b>CollateralController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/report/reportOverdue")
public class ReportOverdueController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ReportOverdueController.class);
	
	/**
	 * CollateralService
	 */
	@Autowired(required=false) //自动注入
	private IReportOverdueService reportOverdueService;
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
	/**
	 * 逾期统计查询条件页面
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
		return "report/reportOverdue/reportOverdueQuery";
	}

	/**
	 * 显示逾期统计的查询列表
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
		Object bean = RequestUtils.getRequestBean(ReportOverdue.class, request);
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
		PageList<ReportOverdue> collateralList = this.reportOverdueService.queryPageList(queryMap);
		pm.setData(collateralList);
		pm.initRowsCount(collateralList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/reportOverdue/reportOverdueList";
	}
	
	@RequestMapping("/excel.do")
	public ModelAndView sysUserExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		ReportOverdue user = (ReportOverdue) RequestUtils.getRequestBean(ReportOverdue.class, request);
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
		List<ReportOverdue> users = this.reportOverdueService.queryList(beanMap);
		String title = "分公司逾期统计表";
		String[] head = new String[]{"地区","逾期类型","交易笔数/笔","项目数/笔","逾期金额/元","本金逾期率","A(0＜X≤14天)金额/元","A(0＜X≤14天)比例","B(14天＜X≤30天)金额/元","B(14天＜X≤30天)比例","C(30天＜X≤90天)金额/元","C(30天＜X≤90天)比例","D(90天＜X≤180天)金额/元","D(90天＜X≤180天)比例","E(180天＜X)金额/元","E(180天＜X)比例","当天存量/元"};
		Object[][] obj = new Object[users.size()][head.length];
		for (int i = 0; i < users.size(); i++) {
			ReportOverdue myUser = users.get(i);
			Object[] values = new Object[head.length];
			values[0] = null != myUser.getOrgName() ? myUser.getOrgName() : "";
			values[1] = null != myUser.getType() ? getOverTypeStr(myUser.getType()) : "";
			values[2] =  myUser.getDealNum();
			values[3] =  myUser.getProjNum();
			values[4] =  myUser.getOverdueAmt();
			values[5] =  myUser.getType().equals("2")?percentFormat(myUser.getPrinRate()):"";
			values[6] =  myUser.getAmt_14();
			values[7] =  myUser.getRate_14()==0?0:percentFormat(myUser.getRate_14());
			values[8] =  myUser.getAmt_30();
			values[9] =  myUser.getRate_30()==0?0:percentFormat(myUser.getRate_30());	
			values[10] =  myUser.getAmt_90();
			values[11] =  myUser.getRate_90()==0?0:percentFormat(myUser.getRate_90());
			values[12] =  myUser.getAmt_180();
			values[13] =  myUser.getRate_180()==0?0:percentFormat(myUser.getRate_180());	
			values[14] =  myUser.getAmtMore();
			values[15] =  myUser.getRateMore()==0?0:percentFormat(myUser.getRateMore());
			values[16] =  myUser.getLoanAmt();			
			obj[i] = values;
		}
		//合计:交易笔数、项目数、逾期金额、各个范围金额、存量合计
		int sumDealNum=0;
		int sumProjNum=0;
		double sumRet=0;
		double sumAmt14=0;
		double sumAmt30=0;
		double sumAmt90=0;
		double sumAmt180=0;
		double sumAmtMore=0;
		double sumLoanAmt=0;
		for (int i = 0; i < obj.length; i++) {
			sumDealNum=sumDealNum+(Integer)obj[i][2];
			sumProjNum=sumProjNum+(Integer)obj[i][3];
			sumRet=MathUtils.add(sumRet,(Double)obj[i][4]);
			sumAmt14=MathUtils.add(sumAmt14,(Double)obj[i][6]);
			sumAmt30=MathUtils.add(sumAmt30,(Double)obj[i][8]);
			sumAmt90=MathUtils.add(sumAmt90,(Double)obj[i][10]);
			sumAmt180=MathUtils.add(sumAmt180,(Double)obj[i][12]);
			sumAmtMore=MathUtils.add(sumAmtMore,(Double)obj[i][14]);
			sumLoanAmt=MathUtils.add(sumLoanAmt,(Double)obj[i][16]);
		}
		Object[][] sumObj=new Object[1][head.length];
		for (int i = 0; i < sumObj[0].length; i++) {
			if(i==0){
				sumObj[0][i]="合计";
			}else if(i==2){
				sumObj[0][i]=sumDealNum;
			}else if(i==3){
				sumObj[0][i]=sumProjNum;
			}else if(i==4){
				sumObj[0][i]=sumRet;
			}else if(i==6){
				sumObj[0][i]=sumAmt14;
			}else if(i==8){
				sumObj[0][i]=sumAmt30;
			}else if(i==10){
				sumObj[0][i]=sumAmt90;
			}else if(i==12){
				sumObj[0][i]=sumAmt180;
			}else if(i==14){
				sumObj[0][i]=sumAmtMore;
			}else if(i==16){
				sumObj[0][i]=sumLoanAmt;
			}else{
				sumObj[0][i]="";
			}
		}
		ExcelReportTable report = new ExcelReportTable(title,null,head,obj,sumObj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("分公司逾期统计表.xls"), map);
	}
	public String getOverTypeStr(String overdueType){
		if(overdueType.equals("1")){
			return "利息逾期";
		}else if(overdueType.equals("2")){
			return "本金逾期";
		}else{
			return "";
		}
	}
	//格式化百分比
	public String percentFormat(double percent){
		DecimalFormat df=new DecimalFormat("####.00");
		return df.format(100*percent)+"%";
	}
}
