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
import com.tera.report.model.BranchStockQBean;
import com.tera.report.service.IBranchStockService;
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
 * 分公司存量统计表Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/report/branchStock")
public class BranchStockController {

	private static final Logger log = Logger.getLogger(BranchStockController.class);
	
	@Autowired
	private IBranchStockService branchStockService;
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
		return "report/branchStock/branchStockQuery";
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
		Object bean = RequestUtils.getRequestBean(BranchStockQBean.class, request);
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
		PageList<BranchStockQBean> branchStockList = this.branchStockService.queryPageList(queryMap);
		pm.setData(branchStockList);
		pm.initRowsCount(branchStockList.getPaginator().getTotalCount());
		map.put("pm", pm);
		
		int noExpireTotalNum=0,expireTotalNum=0,totalTotalNum=0,tranTotalNum=0;
		double noExpireTotalAmt=0d,expireTotalAmt=0d,totalTotalAmt=0d,tranTotalAmt=0d;
		if (branchStockList != null && !branchStockList.isEmpty()) {
			for (BranchStockQBean b : branchStockList) {
				noExpireTotalNum = noExpireTotalNum + b.getNoExpireNum();
				noExpireTotalAmt = MathUtils.add(noExpireTotalAmt, b.getNoExpireAmt());
				
				expireTotalNum = expireTotalNum + b.getExpireNum();
				expireTotalAmt = MathUtils.add(expireTotalAmt, b.getExpireAmt());
				
				totalTotalNum = totalTotalNum + b.getTotalNum();
				totalTotalAmt = MathUtils.add(totalTotalAmt, b.getTotalAmt());
				
				tranTotalNum = tranTotalNum + b.getTranNum();
				tranTotalAmt = MathUtils.add(tranTotalAmt, b.getTranAmt());
			}
		}
		
		map.put("noExpireTotalNum", noExpireTotalNum);
		map.put("noExpireTotalAmt", noExpireTotalAmt);
		map.put("expireTotalNum", expireTotalNum);
		map.put("expireTotalAmt", expireTotalAmt);
		map.put("totalTotalNum", totalTotalNum);
		map.put("totalTotalAmt", totalTotalAmt);
		map.put("tranTotalNum", tranTotalNum);
		map.put("tranTotalAmt", tranTotalAmt);
		
		log.info(thisMethodName+":end");
		return "report/branchStock/branchStockList";
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
		
		BranchStockQBean bean = (BranchStockQBean) RequestUtils.getRequestBean(BranchStockQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		//查询权限机构用于sql注入
				List<String> orgs=this.roleDataRelService.queryList(request);
				//orgs用于权限控制
				queryMap.put("orgs",orgs);
		List<BranchStockQBean> list = this.branchStockService.queryList(queryMap);
		
		String title = "分公司存量统计表";
		String[] head = new String[] { "序号", "分公司", "产品", "未到期未收回笔数", "未到期未收回金额/元", "到期未收回笔数", "到期未收回金额/元", "债权总笔数", "债权总金额/元",
				"转贷笔数", "转贷金额/元", "转贷比", "转贷次数小于等于3次的金额/元", "转贷次数大于3次的金额/元" };
		Object[][] obj = new Object[list.size()][head.length];
		for (int i = 0; i < list.size(); i++) {
			BranchStockQBean tmpBean = list.get(i);
			Object[] values = new Object[head.length];
			values[0]=i+1;
			values[1] = null != tmpBean.getOrgName() ? tmpBean.getOrgName() : "";
			values[2] = null != tmpBean.getProductName() ? tmpBean.getProductName() : "";
			values[3] = tmpBean.getNoExpireNum();
			values[4] = tmpBean.getNoExpireAmt();
			values[5] = tmpBean.getExpireNum();
			values[6] = tmpBean.getExpireAmt();
			values[7] = tmpBean.getTotalNum();
			values[8] = tmpBean.getTotalAmt();
			values[9] = tmpBean.getTranNum();
			values[10] = tmpBean.getTranAmt();
			values[11] = tmpBean.getTranRate();
			values[12] = tmpBean.getLow3Amt();
			values[13] = tmpBean.getHigh3Amt();
			obj[i] = values;
		}
		
		// 条件
		List<String> conditionList = new ArrayList<String>();
		
		StringBuffer range = new StringBuffer("统计时点：");
		if (StringUtils.isNotNullAndEmpty(bean.getCreateDate())) {
			range.append(DateUtils.formatDate(DateUtils.getDate(bean.getCreateDate()), "yyyy/MM/dd"));
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
		return new ModelAndView(new ExcelView("分公司存量统计表.xls"), map);
	}
}
