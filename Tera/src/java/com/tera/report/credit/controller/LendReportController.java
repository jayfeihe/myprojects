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
import com.tera.report.credit.model.LendReportBean;
import com.tera.report.credit.service.LendReportService;
import com.tera.report.echarts.EchartsResult;
import com.tera.report.excel.ExcelConstants;
import com.tera.report.excel.ExcelMatrix;
import com.tera.report.excel.ExcelView;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**放款统计Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/report/credit/lendReport")
public class LendReportController extends BaseController {

	private final static Logger log = Logger.getLogger(LendReportController.class);
	
	@Autowired(required=false)
	private LendReportService lendReportService;
	
	/**
	 * 跳到放款统计页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query.do")
	public String lendReportQuery() throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "report/credit/lendReportQuery";
	}
	
	@RequestMapping("/list.do")
	public String lendReportList(LendReportBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		pm.init(request,null, qBean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<LendReportBean> lendReportList = this.lendReportService.queryPageList(queryMap);
		pm.setData(lendReportList);
		pm.initRowsCount(lendReportList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/credit/lendReportList";
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
	public ModelAndView excelExport(LendReportBean qBean,HttpServletRequest request,  ModelMap model) throws Exception{
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		List<LendReportBean> list = this.lendReportService.queryList(queryMap);
		
		/*String title = "信贷放款统计";
		String[] head = new String[] {"序号",  "申请编号", "产品","城市","放款金额","放款完成日期"};
		Object[][] obj = new Object[list.size()][6];
		for(int i = 0; i < list.size(); i++){
			LendReportBean c = list.get(i);
			
			Object[] values = new Object[6];
			values[0] = i+1;
			values[1] = null != c.getAppId() ?  c.getAppId() : "";
			values[2] = null != c.getProduct() ? c.getProduct() : "";
			values[3] = null != c.getCity() ? c.getCity() : "";
			values[4] = c.getLendAmount();
			values[5] = null != c.getLendDate() ? DateUtils.formatDate(c.getLendDate()) : "";
			obj[i] = values;
		}
		ExcelReportTable report = new ExcelReportTable(title, head, obj);
		model.addAttribute("report", report);*/
		
		ExcelMatrix excelMatrix = new ExcelMatrix();
		excelMatrix.bean2Matrix(list, LendReportBean.class, true);
		
		model.addAttribute(ExcelConstants.SHEET_NAME,"放款统计");
		model.addAttribute(ExcelConstants.EXCEL_MATRIX,excelMatrix);
		return new ModelAndView(new ExcelView("data.xls"));
	}
	
	/**
	 * 获取放款统计报表数据
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/getEchartsData.do")
	public void getLendReportData(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Object bean = RequestUtils.getRequestBean(LendReportBean.class, request);
			Map<String, Object> queryMap = ObjectUtils.describe(bean);
			EchartsResult echartData = this.lendReportService.getLendEchartData(queryMap);
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
