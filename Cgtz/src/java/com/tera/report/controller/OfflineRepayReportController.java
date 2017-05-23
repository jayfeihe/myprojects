package com.tera.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tera.report.model.interform.RepayData;
import com.tera.report.model.interform.RepayParam;
import com.tera.report.model.interform.ResultInfo;
import com.tera.report.service.IOfflineRepayReportService;
import com.tera.sys.constant.ReportConstants;
import com.tera.sys.model.PageModel;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.DateUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 线下还本计划报表Controller
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/report/offlinerepay")
public class OfflineRepayReportController {

private static final Logger log = Logger.getLogger(OfflineRepayReportController.class);
	
	@Autowired
	private IOfflineRepayReportService offlineRepayReportService;
	
	/**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping("/query.do")
	public String query() {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "report/offlineRepay/offlineRepayQuery";
	}
	
	/**
	 * 跳转到列表页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		RepayData bean = (RepayData) RequestUtils.getRequestBean(RepayData.class, request);
		
		RepayParam param = new RepayParam();

		param.setProject_id(bean.getProject_id());
		param.setRate_start_time(bean.getRateTimeMin());
		param.setRate_end_time(bean.getRateTimeMax());
		param.setDebt_type(bean.getDebt_type());
		param.setExport("0");
		ResultInfo resultInfo = this.offlineRepayReportService.getInfos(param );
		
		if (resultInfo != null) {
			int rowsCount = resultInfo.getCount();
			pm.init(request, rowsCount, null, bean);
			
			param.setPage(pm.getCurPage());
			param.setPage_size(pm.getPageSize());
			ResultInfo resultInfoPage = this.offlineRepayReportService.getInfos(param );
			List<RepayData> data = resultInfoPage.getData();
			pm.setData(data);
		}
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "report/offlineRepay/offlineRepayList";
	}
	
	/**
	 * 导出
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/export.do")
	public ModelAndView export(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		RepayData bean = (RepayData) RequestUtils.getRequestBean(RepayData.class, request);
		
		RepayParam param = new RepayParam();

		param.setProject_id(bean.getProject_id());
		param.setDebt_type(bean.getDebt_type());
		param.setRate_start_time(bean.getRateTimeMin());
		param.setRate_end_time(bean.getRateTimeMax());
		param.setExport("1");
		
		ResultInfo resultInfo = this.offlineRepayReportService.getInfos(param );
		
		if (resultInfo != null) {
			List<RepayData> list = resultInfo.getData();
			
			String title = "线下还本计划表";
			String[] head = new String[] { "序号", "项目编号", "线下债权编号", "项目标题", "合同开始时间", "合同结束时间", "项目开始时间", "已销售金额", "债权人",
					"区域", "借款人", "项目天数", "本期天数", "利率", "付息期数", "类型", "应付息时间", "应付息金额", "应付本金额" };
			Object[][] obj = new Object[list.size()][head.length];
			for (int i = 0; i < list.size(); i++) {
				RepayData tmpBean = list.get(i);
				Object[] values = new Object[head.length];
				values[0]=i+1;
				values[1] = null != tmpBean.getProject_id() ? tmpBean.getProject_id() :"";          
				values[2] = null != tmpBean.getSerial_number() ? tmpBean.getSerial_number():"";        
				values[3] = null != tmpBean.getTitle() ? tmpBean.getTitle():"";                
				values[4] = null != tmpBean.getStart_time() ?  tmpBean.getStart_time():"";          
				values[5] = null != tmpBean.getEnd_time()  ? tmpBean.getEnd_time():"";            
				values[6] = null != tmpBean.getOnline_time() ?  tmpBean.getOnline_time() :"";        
				values[7] = tmpBean.getSale_amout_money();      
				values[8] = null != tmpBean.getOriginal_creditor_name() ?tmpBean.getOriginal_creditor_name():"";
				values[9] = null != tmpBean.getArea() ? tmpBean.getArea() :"";                  
				values[10] = null != tmpBean.getBorrower_people_name() ? tmpBean.getBorrower_people_name() :"";    
				values[11] = null != tmpBean.getProject_days() ? tmpBean.getProject_days()  :"";         
				values[12] = null != tmpBean.getCurrent_period() ? tmpBean.getCurrent_period() :"";        
				values[13] = null != tmpBean.getCheck_annualized_rate() ? tmpBean.getCheck_annualized_rate() :""; 
				values[14] = null != tmpBean.getDebt_interest_no() ? tmpBean.getDebt_interest_no()  :"";     
				values[15] = null != tmpBean.getDebt_type() ? ("0".equals(tmpBean.getDebt_type())?"债权转让":("1".equals(tmpBean.getDebt_type())?"直投":""))  :"";            
				values[16] = null != tmpBean.getRate_end_time() ? tmpBean.getRate_end_time() :"";
				values[17] = tmpBean.getSum_payable_interest();
				values[18] = tmpBean.getPayable_principal();
				obj[i] = values;
			}
			
			// 条件
			List<String> conditionList = new ArrayList<String>();
			StringBuffer range = new StringBuffer();
			if (StringUtils.isNotNullAndEmpty(bean.getRateTimeMin())) {
				range.append("应付息时间：");
				range.append(DateUtils.formatDate(DateUtils.getDate(bean.getRateTimeMin()), "yyyy/MM/dd")+" - ");
			}
			if (StringUtils.isNotNullAndEmpty(bean.getRateTimeMax())) {
				range.append(DateUtils.formatDate(DateUtils.getDate(bean.getRateTimeMax()), "yyyy/MM/dd"));
			}
			
			conditionList.add(range.toString());
			
			if (StringUtils.isNotNullAndEmpty(bean.getProject_id())) {
				String projectId = "项目编号："+bean.getProject_id();
				conditionList.add(projectId);
			}
			
			if (StringUtils.isNotNullAndEmpty(bean.getDebt_type())) {
				String debtType = null;
				if ("0".equals(bean.getDebt_type())) {
					debtType = "类型：债权转让";
				} else if ("1".equals(bean.getDebt_type())) {
					debtType = "类型：直投";
				}
				conditionList.add(debtType);
			}
			String[] condition = conditionList.toArray(new String[conditionList.size()]);
			
			ExcelReportTable report = new ExcelReportTable(title, condition, head, obj);
			
			map.addAttribute(ReportConstants.REPORT, report);
		}
		
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("线下还本计划表.xls"), map);
	}
}
