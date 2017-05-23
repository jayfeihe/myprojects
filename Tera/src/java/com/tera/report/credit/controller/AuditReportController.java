package com.tera.report.credit.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.report.credit.model.AuditReportBean;
import com.tera.report.credit.service.AuditReportService;
import com.tera.report.excel.ExcelConstants;
import com.tera.report.excel.ExcelMatrix;
import com.tera.report.excel.ExcelRegion;
import com.tera.report.excel.ExcelView;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**信审统计Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/report/credit/auditReport")
public class AuditReportController extends BaseController{

	private final static Logger log = Logger.getLogger(AuditReportController.class);
	
	@Autowired(required=false)
	private AuditReportService auditReportService;
	
	/**
	 * 跳到信审统计页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public String auditReportQuery() throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "report/credit/auditReportQuery";
	}
	
	@RequestMapping("/list.do")
	public String auditReportList(AuditReportBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		pm.init(request,null, qBean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<AuditReportBean> auditReportList = this.auditReportService.queryPageList(queryMap);
		pm.setData(auditReportList);
		pm.initRowsCount(auditReportList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/credit/auditReportList";
	}
	
	/**
	 * 导出
	 * @param qBean
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportExcel.do")
	public ModelAndView excelExport(AuditReportBean qBean,HttpServletRequest request,  ModelMap model) throws Exception{
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		List<AuditReportBean> list = this.auditReportService.queryList(queryMap);
		
		/*String title = "信审统计";
		String[] head = new String[] {"序号",  "信审类型", "总量", "通过量","拒贷量","其他量","决策人"};
		Object[][] obj = new Object[list.size()][7];
		for(int i = 0; i < list.size(); i++){
			AuditReportBean c = list.get(i);
			
			if("1".equals(qBean.getAuditType())) c.setAuditType("审核");
			if("2".equals(qBean.getAuditType())) c.setAuditType("审批");
			
			Object[] values = new Object[7];
			values[0] = i+1;
			values[1] = null != c.getAuditType() ?  c.getAuditType() : "";
			values[2] = null != c.getTotalAmount() ?  c.getTotalAmount() : "";
			values[3] = null != c.getPassAmount() ? c.getPassAmount() : "";
			values[4] = null != c.getDenyAmount() ? c.getDenyAmount() : "";
			values[5] = null != c.getOtherAmount() ? c.getOtherAmount() : "";
			values[6] = null != c.getAuditUser() ? c.getAuditUser() : "";
			obj[i] = values;
		}
		String[] condition = new String[] {
				"信审日期：" + DateUtils.formatDate(DateUtils.getDate(qBean.getAuditDateStart()), "yyyy/MM/dd") + "-"
						+ DateUtils.formatDate(DateUtils.getDate(qBean.getAuditDateEnd()), "yyyy/MM/dd") };
		ExcelReportTable report = new ExcelReportTable(title, condition, head, obj);
		model.addAttribute("report", report);*/
		
		ExcelMatrix excelMatrix = new ExcelMatrix();
		excelMatrix.bean2Matrix(list, AuditReportBean.class, true);
		
		if (StringUtils.isNotNullAndEmpty(qBean.getAuditDateStart())
				&& StringUtils.isNotNullAndEmpty(qBean.getAuditDateEnd())) {
			ExcelRegion header = new ExcelRegion(0, 0, 5, 1,
					"信审日期：" + DateUtils.formatDate(DateUtils.getDate(qBean.getAuditDateStart()), "yyyy/MM/dd") + "-"
							+ DateUtils.formatDate(DateUtils.getDate(qBean.getAuditDateEnd()), "yyyy/MM/dd"));
			excelMatrix.setHeader(header);
		}
		model.addAttribute(ExcelConstants.SHEET_NAME,"信审统计");
		model.addAttribute(ExcelConstants.EXCEL_MATRIX,excelMatrix);
		
		return new ModelAndView(new ExcelView("data.xls"),model);
	}
	
	/**
	 * 获取信审统计报表数据
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/getEchartsData.do")
	public void getCreditAuditReportData(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Object bean = RequestUtils.getRequestBean(AuditReportBean.class, request);
			Map<String, Object> queryMap = ObjectUtils.describe(bean);
			AuditReportBean echartData = this.auditReportService.getAuditEchartData(queryMap);
			writer.print(JsonUtil.object2json(echartData));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "数据加载失败")));
		} finally {
			writer.flush();
			writer.close();
			log.info(thisMethodName+":end");
		}
	}
}
