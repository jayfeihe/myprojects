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
import com.tera.report.model.LoanManBaseQBean;
import com.tera.report.service.ILoanManBaseService;
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
 * 分公司成交量统计表Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/report/loanManBase")
public class LoanManBaseController {

	private static final Logger log = Logger.getLogger(LoanManBaseController.class);
	
	@Autowired
	private ILoanManBaseService loanManBaseService;
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	@Autowired(required=false) //自动注入
	private RoleDataRelService roleDataRelService;
	
	
	/**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping("query.do")
	public String query(HttpServletRequest request,ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		//如果是分公司需要用于页面显示
		map.put("loginOrgName",loginOrg.getOrgName());
		log.info(thisMethodName+":end");
		return "report/loanManBase/loanManBaseQuery";
	}
	
	/**
	 * 跳转到列表页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list.do")
	public String list(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanManBaseQBean.class, request);
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
		PageList<LoanManBaseQBean> loanManBaseList = this.loanManBaseService.queryPageList(queryMap);
		pm.setData(loanManBaseList);
		pm.initRowsCount(loanManBaseList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/loanManBase/loanManBaseList";
	}
	
	/**
	 * 导出
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("export.do")
	public ModelAndView export(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		LoanManBaseQBean bean = (LoanManBaseQBean) RequestUtils.getRequestBean(LoanManBaseQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制
				queryMap.put("orgs",orgs);
		List<LoanManBaseQBean> list = this.loanManBaseService.queryList(queryMap);
		
		String title = "借款人基本情况表";
		String[] head = new String[] { "序号", "线上编号", "线下编号", "项目名称 ", "上线时间", "开始时间", "结束时间",
				"合同金额/元 ", "债权人 ", "借款人", "线下利率", "线上利率 ", "应收本金/元 ", "实收本金/元", "项目类型 ",
				"应收利息 /元", "实收利息 /元", "线上垫付利息/元","收到时间", "备注" };
		Object[][] obj = new Object[list.size()][head.length];
		for (int i = 0; i < list.size(); i++) {
			LoanManBaseQBean tmpBean = list.get(i);
			Object[] values = new Object[head.length];
			values[0]=i+1;
			values[1] = null != tmpBean.getOnlineConId() ? tmpBean.getOnlineConId() : "";
			values[2] = null != tmpBean.getContractId() ? tmpBean.getContractId() : "";
			values[3] = null != tmpBean.getProjectName() ? tmpBean.getProjectName() : "";
			values[4] = null != tmpBean.getOnlineStartDate() ? DateUtils.formatDate(tmpBean.getOnlineStartDate(),"yyyy/MM/dd") : "";
			values[5] = null != tmpBean.getStartDate() ? DateUtils.formatDate(tmpBean.getStartDate(),"yyyy/MM/dd") : "";
			values[6] = null != tmpBean.getEndDate() ? DateUtils.formatDate(tmpBean.getEndDate(),"yyyy/MM/dd") : "";
			values[7] = tmpBean.getLoanAmt();
			values[8] = null != tmpBean.getLendUser() ? tmpBean.getLendUser() : "";
			values[9] = null != tmpBean.getName() ? tmpBean.getName() : "";
			values[10] = tmpBean.getRate()+"%";
			values[11] = tmpBean.getOnlineRateOut()+"%";
			values[12] = tmpBean.getPlanCapital();
			values[13] = tmpBean.getRealCapital();
			values[14] =  null != tmpBean.getIsRenew() ? tmpBean.getIsRenew() : "";
			values[15] = tmpBean.getPlanInterest();
			values[16] = tmpBean.getRealInterest();
			values[17] = tmpBean.getPayOutInterest();
			values[18] = null != tmpBean.getCollectDate() ? DateUtils.formatDate(tmpBean.getCollectDate(),"yyyy/MM/dd") : "";
			values[19] = null != tmpBean.getRemark() ? tmpBean.getRemark() : "";
			obj[i] = values;
		}
		
		// 条件
		List<String> conditionList = new ArrayList<String>();
		
		StringBuffer range = new StringBuffer();
		if (StringUtils.isNotNullAndEmpty(bean.getStartDateMin())) {
			range.append("合同开始时间：");
			range.append(DateUtils.formatDate(DateUtils.getDate(bean.getStartDateMin()), "yyyy/MM/dd"));
		}
		if (StringUtils.isNotNullAndEmpty(bean.getStartDateMax())) {
			range.append(" - "+DateUtils.formatDate(DateUtils.getDate(bean.getStartDateMax()), "yyyy/MM/dd"));
		}
		
		if (StringUtils.isNotNullAndEmpty(bean.getEndDateMin())) {
			range.append("\n合同结束时间：");
			range.append(DateUtils.formatDate(DateUtils.getDate(bean.getEndDateMin()), "yyyy/MM/dd"));
		}
		if (StringUtils.isNotNullAndEmpty(bean.getEndDateMax())) {
			range.append(" - "+DateUtils.formatDate(DateUtils.getDate(bean.getEndDateMax()), "yyyy/MM/dd"));
		}
		
		conditionList.add(range.toString());
		
		if (StringUtils.isNotNullAndEmpty(bean.getOrgName())) {
			String org = "分公司："+bean.getOrgName();
			conditionList.add(org);
		}
		
		String[] condition = conditionList.toArray(new String[conditionList.size()]);
		
		ExcelReportTable report = new ExcelReportTable(title, condition, head, obj);
		
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("借款人基本情况表.xls"), map);
	}
}
