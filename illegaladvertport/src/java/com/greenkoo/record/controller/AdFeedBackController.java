package com.greenkoo.record.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenkoo.account.model.UserAccount;
import com.greenkoo.company.model.UserCompany;
import com.greenkoo.record.model.AdFeedBack;
import com.greenkoo.record.model.form.AdFeedBackBean;
import com.greenkoo.record.service.IAdFeedBackService;
import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.utils.DateUtil;

/**
 * 整改反馈Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/feedback")
public class AdFeedBackController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAdFeedBackService adFeedBackService;
	
	/**
	 * 获取其他主体方信息
	 * 
	 * @param infoId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOtherInfosByInfoId", method = RequestMethod.GET)
	@ResponseBody
	public List<AdFeedBack> getOtherInfosByInfoId(String infoId,HttpServletRequest request) {
		UserCompany company = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		String roleUrl = company.getCompanyUrl();
		List<AdFeedBack> feedBacks = adFeedBackService.getByInfoIdAndNoRoleUrl(infoId,roleUrl);
		
		return feedBacks;
	}
	
	/**
	 * 获取我方整改信息
	 * 
	 * @param infoId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSelfInfosByInfoId", method = RequestMethod.GET)
	@ResponseBody
	public AdFeedBack getSelfInfosByInfoId(String infoId,HttpServletRequest request) {
		UserCompany company = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		String roleUrl = company.getCompanyUrl();
		AdFeedBack feedBack = adFeedBackService.getByInfoIdAndRoleUrl(infoId,roleUrl);
		
		return feedBack;
	}
	
	/**
	 * 提交整改信息
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/correct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> correct(HttpServletRequest request) {
		Map<String, String> returnMap = new HashMap<String, String>();
		
		UserCompany company = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		String roleUrl = company.getCompanyUrl();
		UserAccount account = (UserAccount) request.getSession().getAttribute(CommonConstants.LOGIN_USER);
		String operator = account.getAccount();
		
		String infoId = request.getParameter("infoId");
		String remark = request.getParameter("remark");
		String correctionTime = request.getParameter("correctionTime");
		
		try {
			
			Date correctionDate = DateUtil.transStrToDate(correctionTime, DateUtil.FORMAT_HM);
			AdFeedBackBean feedBackBean = new AdFeedBackBean(infoId, correctionDate, remark);
			
			this.adFeedBackService.correct(feedBackBean,roleUrl,operator);
			
			returnMap.put("success", "true");
			
		} catch (NumberFormatException e) {
			logger.error("日期格式转换错误：" + e.getMessage(), e);
			returnMap.put("success", "false");
		} catch (Exception e) {
			logger.error("提交整改信息系统异常：" + e.getMessage() + ",infoId：" + infoId, e);
			returnMap.put("success", "false");
		}
		
		return returnMap;
	}
}
