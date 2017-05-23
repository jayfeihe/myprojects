package com.tera.report.credit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.report.credit.model.ApprovalStatisticsBean;
import com.tera.report.credit.service.ApprovalStatisticsService;

/**
 * 审批统计Service
 * @author QYANZE
 * @date 2015-5-15
 */
@Controller
@RequestMapping("/report")
public class ApprovalStatisticsController {

	@Autowired(required=false)
	private ApprovalStatisticsService approvalStatisticsService;
	
	@RequestMapping("/approvalStatisticsQuery.do")
	public String approvalStatisticsQuery() throws Exception {
		return "report/approvalStatisticsQuery";
	}
	
	@RequestMapping("/approvalStatisticsList.do")
	public String approvalStatisticsList(String loginId,HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginId", loginId);
		List<ApprovalStatisticsBean> statisticsList = approvalStatisticsService
				.queryStatisticsList(map);
		model.addAttribute("statisticsList", statisticsList);
		return "report/approvalStatisticsList";
	}
}
