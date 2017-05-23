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
import com.tera.report.model.BranchDealQBean;
import com.tera.report.service.IBranchDealService;
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
 * 分公司成交量统计表Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/report/branchDeal")
public class BranchDealController {

	private static final Logger log = Logger.getLogger(BranchDealController.class);
	
	@Autowired
	private IBranchDealService branchDealService;
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
		return "report/branchDeal/branchDealQuery";
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
		Object bean = RequestUtils.getRequestBean(BranchDealQBean.class, request);
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
		PageList<BranchDealQBean> branchDealList = this.branchDealService.queryPageList(queryMap);
		pm.setData(branchDealList);
		pm.initRowsCount(branchDealList.getPaginator().getTotalCount());
		map.put("pm", pm);
		
		int newTotalNum=0,renewTotalNum=0,totalTotalNum=0;  
		double newTotalAmt=0d,renewTotalAmt=0d,totalTotalAmt=0d;
		
		if (branchDealList != null && !branchDealList.isEmpty()) {
			for (BranchDealQBean tmpBean : branchDealList) {
				newTotalNum = newTotalNum + tmpBean.getNewNum();
				newTotalAmt = MathUtils.add(newTotalAmt, tmpBean.getNewAmt());

				renewTotalNum = renewTotalNum + tmpBean.getRenewNum();
				renewTotalAmt = MathUtils.add(renewTotalAmt, tmpBean.getRenewAmt());

				totalTotalNum = totalTotalNum + tmpBean.getTotalNum();
				totalTotalAmt = MathUtils.add(totalTotalAmt, tmpBean.getTotalAmt());
			} 
		}
		map.put("newTotalNum", newTotalNum);
		map.put("renewTotalNum", renewTotalNum);
		map.put("totalTotalNum", totalTotalNum);
		
		map.put("newTotalAmt", newTotalAmt);
		map.put("renewTotalAmt", renewTotalAmt);
		map.put("totalTotalAmt", totalTotalAmt);
		
		log.info(thisMethodName+":end");
		return "report/branchDeal/branchDealList";
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
		
		BranchDealQBean bean = (BranchDealQBean) RequestUtils.getRequestBean(BranchDealQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制
				queryMap.put("orgs",orgs);
		List<BranchDealQBean> list = this.branchDealService.queryList(queryMap);
		
		String title = "分公司成交量统计表";
		String[] head = new String[] { "序号","分公司 ", "项目", "续贷项目笔数/笔", "续贷项目金额/元", "新增项目笔数/笔", "新增项目金额/元", "总项目笔数/笔", "总项目金额/元" };
		Object[][] obj = new Object[list.size()][head.length];
		for (int i = 0; i < list.size(); i++) {
			BranchDealQBean tmpBean = list.get(i);
			Object[] values = new Object[head.length];
			values[0]=i+1;
			values[1] = null != tmpBean.getOrgName() ? tmpBean.getOrgName() : "";
			values[2] = null != tmpBean.getProductName() ? tmpBean.getProductName() : "";
			values[3] = tmpBean.getRenewNum();
			values[4] = tmpBean.getRenewAmt();
			values[5] = tmpBean.getNewNum();
			values[6] = tmpBean.getNewAmt();
			values[7] = tmpBean.getTotalNum();
			values[8] = tmpBean.getTotalAmt();
			obj[i] = values;
		}
		
		// 条件
		List<String> conditionList = new ArrayList<String>();
		
		StringBuffer range = new StringBuffer("统计区间：");
		if (StringUtils.isNotNullAndEmpty(bean.getLoanTimeMin())) {
			range.append(DateUtils.formatDate(DateUtils.getDate(bean.getLoanTimeMin()), "yyyy/MM/dd"));
		}
		if (StringUtils.isNotNullAndEmpty(bean.getLoanTimeMax())) {
			range.append(" - "+DateUtils.formatDate(DateUtils.getDate(bean.getLoanTimeMax()), "yyyy/MM/dd"));
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
		return new ModelAndView(new ExcelView("分公司成交量统计表.xls"), map);
	}
}
