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
import com.tera.report.credit.model.ViolateReportBean;
import com.tera.report.credit.service.ViolateReportService;
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

/**
 * 违约统计Controller
 */
@Controller
@RequestMapping("/report/credit/violateReport")
public class ViolateReportController extends BaseController {

	private final static Logger log = Logger.getLogger(ViolateReportController.class);

	@Autowired(required = false)
	private ViolateReportService violateReportService;

	/**
	 * 跳到违约统计页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public String violateReportQuery() throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		log.info(thisMethodName + ":end");
		return "report/credit/violateReportQuery";
	}

	@RequestMapping("/list.do")
	public String violateReportList(ViolateReportBean qBean, HttpServletRequest request, ModelMap map)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		pm.init(request, null, qBean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<ViolateReportBean> violateReportList = this.violateReportService.queryPageList(queryMap);
		pm.setData(violateReportList);
		pm.initRowsCount(violateReportList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName + ":end");
		return "report/credit/violateReportList";
	}

	/**
	 * 导出
	 * 
	 * @param qBean
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportExcel.do")
	public ModelAndView excelExport(ViolateReportBean qBean, HttpServletRequest request, ModelMap model)
			throws Exception {
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		List<ViolateReportBean> list = this.violateReportService.queryList(queryMap);
		
		ExcelMatrix excelMatrix = new ExcelMatrix();
		excelMatrix.bean2Matrix(list, ViolateReportBean.class, true);
		
		if (StringUtils.isNotNullAndEmpty(qBean.getViolateDateStart())
				&& StringUtils.isNotNullAndEmpty(qBean.getViolateDateEnd())) {
			ExcelRegion header = new ExcelRegion(0, 0, 5, 1,
					"还款日期：" + DateUtils.formatDate(DateUtils.getDate(qBean.getViolateDateStart()), "yyyy/MM/dd") + "-"
							+ DateUtils.formatDate(DateUtils.getDate(qBean.getViolateDateEnd()), "yyyy/MM/dd"));
			excelMatrix.setHeader(header);
		}
		
		model.addAttribute(ExcelConstants.SHEET_NAME,"违约统计");
		model.addAttribute(ExcelConstants.EXCEL_MATRIX,excelMatrix);
		
		return new ModelAndView(new ExcelView("data.xls"), model);
	}

	/**
	 * 获取违约统计报表数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/getEchartsData.do")
	public void getCreditViolateReportData(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName + ":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Object bean = RequestUtils.getRequestBean(ViolateReportBean.class, request);
			Map<String, Object> queryMap = ObjectUtils.describe(bean);
			ViolateReportBean echartData = this.violateReportService.getViolateEchartData(queryMap);
			writer.print(JsonUtil.object2json(echartData));
		} catch (Exception e) {
			log.error(thisMethodName + ":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "数据加载失败")));
		} finally {
			writer.flush();
			writer.close();
			log.info(thisMethodName + ":end");
		}
	}
}
