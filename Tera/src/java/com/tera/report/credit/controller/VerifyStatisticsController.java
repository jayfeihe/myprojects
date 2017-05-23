package com.tera.report.credit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.credit.constant.Constants;
import com.tera.report.credit.model.VerifyStatisticsBean;
import com.tera.report.credit.service.VerifyStatisticsService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 审核统计Service
 * @author QYANZE
 * @date 2015-5-15
 */
@Controller
@RequestMapping("/report")
public class VerifyStatisticsController {

	@Autowired(required=false)
	private VerifyStatisticsService verifyStatisticsService;
	
	@Autowired(required=false)
	private UserService userService;
	
	@RequestMapping("/verifyStatisticsQuery.do")
	public String verifyStatisticsQuery() throws Exception {
		return "report/verifyStatisticsQuery";
	}
	
	@RequestMapping("/verifyStatisticsList.do")
	public String approvalStatisticsList(HttpServletRequest request, Model model) throws Exception {
		VerifyStatisticsBean bean = (VerifyStatisticsBean) RequestUtils
				.getRequestBean(VerifyStatisticsBean.class, request);
		Map<String, Object> map = ObjectUtils.describe(bean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("loginId", loginId);
		tmpMap.put("roleNames", new String[]{Constants.ROLE_SHZY});
		List<User> shzyUser = userService.queryUserByOrgAndRoleAndDepart(tmpMap);
		// 登录角色是审核专员只能查询自己的
		if (shzyUser != null && shzyUser.size() > 0) {
			map.put("loginId", loginId);
		}
		List<VerifyStatisticsBean> statisticsList = verifyStatisticsService
				.queryStatisticsList(map);
		model.addAttribute("statisticsList", statisticsList);
		return "report/verifyStatisticsList";
	}
}
