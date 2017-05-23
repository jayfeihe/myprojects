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
import com.tera.report.model.ProjectDetailQBean;
import com.tera.report.service.IProjectDetailService;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
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
 * 项目明细表Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/report/projectDetail")
public class ProjectDetailController {

	private static final Logger log = Logger.getLogger(ProjectDetailController.class);
	
	@Autowired
	private IProjectDetailService projectDetailService;
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
		return "report/projectDetail/projectDetailQuery";
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
		Object bean = RequestUtils.getRequestBean(ProjectDetailQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
		List<String> orgs=this.roleDataRelService.queryList(request);
		//orgs用于权限控制
		//如果分配给分公司,则根据登录者所在分公司查询
		Org loginOrg=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
				if(loginOrg!=null&&!loginOrg.getOrgId().equals("86")){
					queryMap.put("orgId",loginOrg.getOrgId());
				}
		queryMap.put("orgs",orgs);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<ProjectDetailQBean> projectDetailList = this.projectDetailService.queryPageList(queryMap);
		pm.setData(projectDetailList);
		pm.initRowsCount(projectDetailList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/projectDetail/projectDetailList";
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
		
		ProjectDetailQBean bean = (ProjectDetailQBean) RequestUtils.getRequestBean(ProjectDetailQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制
				queryMap.put("orgs",orgs);
		List<ProjectDetailQBean> list = this.projectDetailService.queryList(queryMap);
		
		String title = "项目明细表";
		String[] head = new String[] { "序号","债权线上编号", "债权线下编号", "项目名称", "合同开始时间", "合同结束时间", "上线时间", "合同实际结束时间", "合同天数",
				"合同状态", "借款金额/元", "原始债权人", "借款人", "借款利率", "线上利率", "还款方式", "贷款类别", "所属分公司" };
		Object[][] obj = new Object[list.size()][head.length];
		for (int i = 0; i < list.size(); i++) {
			ProjectDetailQBean tmpBean = list.get(i);
			Object[] values = new Object[head.length];
			values[0]=i+1;
			values[1] = null != tmpBean.getOnlineConId() ? tmpBean.getOnlineConId() : "";
			values[2] = null != tmpBean.getContractId() ? tmpBean.getContractId() : "";
			values[3] = null != tmpBean.getProjectName() ? tmpBean.getProjectName() : "";
			values[4] = null != tmpBean.getStartDate() ? DateUtils.formatDate(tmpBean.getStartDate(),"yyyy/MM/dd") : "";
			values[5] = null != tmpBean.getEndDate() ? DateUtils.formatDate(tmpBean.getEndDate(),"yyyy/MM/dd") : "";
			values[6] = null != tmpBean.getOnlineStartDate() ? DateUtils.formatDate(tmpBean.getOnlineStartDate(),"yyyy/MM/dd") : "";
			values[7] = null != tmpBean.getRealEndDate() ? DateUtils.formatDate(tmpBean.getRealEndDate(),"yyyy/MM/dd") : "";
			values[8] = null != tmpBean.getContractDays() ? tmpBean.getContractDays() : "";
			values[9] = null != tmpBean.getContractState() ? (tmpBean.getContractState().equals("1")?"未生效":(tmpBean.getContractState().equals("2")?"合同中":(tmpBean.getContractState().equals("3")?"合同结清":(tmpBean.getContractState().equals("4")?"被拆分":"")))) : ""; // 1未生效2合同中3合同结清4被拆分
			values[10]= tmpBean.getLoanAmt();
			values[11] = null != tmpBean.getLendUser() ? tmpBean.getLendUser() : "";
			values[12] = null != tmpBean.getLoanUser() ? tmpBean.getLoanUser() : "";
			values[13] = tmpBean.getLoanRate()+"%";
			values[14] = tmpBean.getOnlineRate()+"%";
			values[15] = null != tmpBean.getOnlineRetway() ? tmpBean.getOnlineRetway() : "";
			values[16] = null != tmpBean.getOnlineType() ? tmpBean.getOnlineType() : "";
			values[17] = null != tmpBean.getOrgName() ? tmpBean.getOrgName() : "";
//			values[18] = null != tmpBean.getGetLoanWay() ? (tmpBean.getGetLoanWay().equals("1")?"直投":(tmpBean.getGetLoanWay().equals("2")?"债权转让":(tmpBean.getGetLoanWay().equals("3")?"线下":""))) : ""; // 1直投2债权转让3线下
//			values[19] = null != tmpBean.getLoanType() ? (tmpBean.getLoanType().equals("01")?"个人":(tmpBean.getLoanType().equals("02")?"公司":"")) : ""; //01个人 02公司
			
			obj[i] = values;
		}
		
		// 条件
		List<String> conditionList = new ArrayList<String>();
		String org = null;
		String onlineType = null;
		
		StringBuffer range = new StringBuffer();
		if (StringUtils.isNotNullAndEmpty(bean.getStartDateMin())) {
			range.append("统计区间：");
			range.append(DateUtils.formatDate(DateUtils.getDate(bean.getStartDateMin()), "yyyy/MM/dd"));
		}
		if (StringUtils.isNotNullAndEmpty(bean.getStartDateMax())) {
			range.append(" - "+DateUtils.formatDate(DateUtils.getDate(bean.getStartDateMax()), "yyyy/MM/dd"));
		}
		
		conditionList.add(range.toString());
		
		if (StringUtils.isNotNullAndEmpty(bean.getOrgName())) {
			org = "分公司："+bean.getOrgName();
			conditionList.add(org);
		}
		
		/*if (StringUtils.isNotNullAndEmpty(bean.getLoanType())) {
			if ("01".equals(bean.getLoanType())) {
				loanType = "贷款类别：个人";
			} else if ("02".equals(bean.getLoanType())) {
				loanType = "贷款类别：公司";
			}
			conditionList.add(loanType);
		}*/
		
		if (StringUtils.isNotNullAndEmpty(bean.getOnlineType())) {
			conditionList.add(onlineType);
		}
		
		String[] condition = conditionList.toArray(new String[conditionList.size()]);
		
		// 统计数据
		double totalLoanAmt = 0.0;
		
		for (int i = 0; i < obj.length; i++) {
			double loanAmt = (Double) obj[i][10];
			totalLoanAmt = MathUtils.add(totalLoanAmt, loanAmt);
		}
		
		Object[][] statisticData = new Object[1][head.length];
		for (int i = 0; i < statisticData[0].length; i++) {
			if (i == 0) {
				statisticData[0][i] = "合计";
			} else
			if (i == 10) {
				statisticData[0][i] = totalLoanAmt;
			} else {
				statisticData[0][i] = "";
			}
		}
		
		ExcelReportTable report = new ExcelReportTable(title, condition, head, obj, statisticData);
		
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("项目明细表.xls"), map);
	}
}
