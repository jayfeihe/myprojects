package com.tera.audit.retplan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.audit.retplan.model.RetPlan;
import com.tera.audit.retplan.service.IRetPlanCalculateService;
import com.tera.sys.controller.BaseController;

/**
 * 还款计划计算Controller (放款前展示)
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/retplancalc")
public class RetPlanCalculateController extends BaseController {

	private static final Logger log = Logger.getLogger(RetPlanCalculateController.class);
	
	@Autowired
	private IRetPlanCalculateService retPlanCalculateService;
	
	
	@RequestMapping("/query.do")
	public String query(String loanId, HttpServletRequest req, HttpServletResponse resp, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		map.put("loanId", loanId);
		
		log.info(thisMethodName+":end");
		return "retplancalc/retPlanCalculateQuery";
	}
	
	/**
	 * 根据申请编号和合同开始日期计算还款计划
	 * 
	 * @param loanId 申请编号
	 * @param startDate 合同开始日期
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String list(String loanId, String startDate, HttpServletRequest req, HttpServletResponse resp, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		List<RetPlan> retList = this.retPlanCalculateService.createRetPlan(loanId, startDate);
		
		map.put("retList", retList);
		
		log.info(thisMethodName+":end");
		
		return "retplancalc/retPlanCalculateList";
	}
}
