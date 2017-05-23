package com.greenkoo.record.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenkoo.company.model.UserCompany;
import com.greenkoo.record.constants.AdvertConstants;
import com.greenkoo.record.model.DataRecord;
import com.greenkoo.record.model.form.RelatedBean;
import com.greenkoo.record.service.IDataRecordService;
import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.sys.model.Pager;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.StringUtil;

/**
 * 确认违法列表Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/confirmIllegal")
public class ConfirmIllegalController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDataRecordService dataRecordService;
	
	/**
	 * 跳到确认违法列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String confirmIllegal(String keyWord, HttpServletRequest request, Model model) {
		model.addAttribute("keyWord", keyWord);
		return "advert/confirmIllegal/confirmIllegalList";
	}
	
	/**
	 * 违法广告列表
	 * 
	 * @return
	 */
	@RequestMapping("/list/advert")
	public String advertList(HttpServletRequest request, Model model) {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		
		UserCompany uc = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		
		// 媒体登录
		if (UserCompany.TYPE_MEDIA == uc.getType()) {
			paramMap.put("mediaUrl", uc.getCompanyUrl());
		} 
		// 广告主登录
		if (UserCompany.TYPE_ADVERTER == uc.getType()) {
			paramMap.put("advertiserUrl", uc.getCompanyUrl());
		}
		try {
			// 广告名称查询条件
			String keyWord = request.getParameter("keyWord");
			if (StringUtil.isNotEmpty(keyWord))
				keyWord = URLDecoder.decode(keyWord, "UTF-8");
			
			// 终端类型条件
			String terminalType = request.getParameter("terminalType");
			if (StringUtil.isNotEmpty(terminalType))
				terminalType = URLDecoder.decode(terminalType, "UTF-8");
			
			// 违法程度条件
			String level = request.getParameter("level");
			if (StringUtil.isNotEmpty(level))
				level = URLDecoder.decode(level, "UTF-8");
			
			// 状态
			String status = request.getParameter("status");
			if (StringUtil.isNotEmpty(status))
				status = URLDecoder.decode(status, "UTF-8");
			
			paramMap.put("adName", keyWord);
			paramMap.put("terminalType", terminalType);
			paramMap.put("level", level);
			paramMap.put("status", status);
			
			// 时间条件
			this.addTimeParams(paramMap, request);
		} catch (Exception e) {
			logger.error("URL解码参数出错：" + e.getMessage(), e);
		};

		logger.info("【"+uc.getCompanyUrl()+"】查询自己的创意传递参数：" + paramMap);
		
		Pager pager = new Pager();
		int totalCount = this.dataRecordService.queryAdvertCount(paramMap);
		pager.init(totalCount);
		paramMap.put("pageSize", pager.getPageSize());
		paramMap.put("pageOffset", pager.getPageOffset());
		List<DataRecord> advertDatas = this.dataRecordService.queryAdvertList(paramMap);
		pager.setDatas(advertDatas);
		model.addAttribute("pager", pager);
//		model.addAttribute("advertDatas", advertDatas);
		return "advert/confirmIllegal/advertList";
	}
	
	/**
	 * 违法广告创意列表
	 * 
	 * @return
	 */
	@RequestMapping("/list/creative")
	public String creativeList(HttpServletRequest request, Model model) {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		
		UserCompany uc = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		
		// 媒体登录
		if (UserCompany.TYPE_MEDIA == uc.getType()) {
			paramMap.put("mediaUrl", uc.getCompanyUrl());
		} 
		// 广告主登录
		if (UserCompany.TYPE_ADVERTER == uc.getType()) {
			paramMap.put("advertiserUrl", uc.getCompanyUrl());
		}
		try {
			// 广告名称查询条件
			String keyWord = request.getParameter("keyWord");
			if (StringUtil.isNotEmpty(keyWord))
				keyWord = URLDecoder.decode(keyWord, "UTF-8");
			
			// 终端类型条件
			String terminalType = request.getParameter("terminalType");
			if (StringUtil.isNotEmpty(terminalType))
				terminalType = URLDecoder.decode(terminalType, "UTF-8");
			
			// 违法程度条件
			String level = request.getParameter("level");
			if (StringUtil.isNotEmpty(level))
				level = URLDecoder.decode(level, "UTF-8");
			
			// 状态
			String status = request.getParameter("status");
			if (StringUtil.isNotEmpty(status))
				status = URLDecoder.decode(status, "UTF-8");
			
			paramMap.put("creativeName", keyWord);
			paramMap.put("terminalType", terminalType);
			paramMap.put("level", level);
			paramMap.put("status", status);
			
			// 时间条件
			this.addTimeParams(paramMap, request);
		} catch (Exception e) {
			logger.error("URL解码参数出错：" + e.getMessage(), e);
		};

		logger.info("【"+uc.getCompanyUrl()+"】查询自己的创意传递参数：" + paramMap);
		
		Pager pager = new Pager();
		int totalCount = this.dataRecordService.queryCount(paramMap);
		pager.init(totalCount);
		paramMap.put("pageSize", pager.getPageSize());
		paramMap.put("pageOffset", pager.getPageOffset());
		List<DataRecord> creativeDatas = this.dataRecordService.queryList(paramMap);
		pager.setDatas(creativeDatas);
		model.addAttribute("pager", pager);
		
//		model.addAttribute("creativeDatas", creativeDatas);
		return "advert/confirmIllegal/creativeList";
	}
	
	/**
	 * 涉及广告主或投放媒体统计列表
	 * 
	 * @return
	 */
	@RequestMapping("/list/related")
	public String confirmIllegalRelatedList(HttpServletRequest request, Model model) {
		
		UserCompany uc = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		
		String keyWord = request.getParameter("keyWord");
		if (StringUtil.isNotEmpty(keyWord)) {
			try {
				keyWord = URLDecoder.decode(keyWord, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("URL解码参数出错：" + e.getMessage(), e);
			}
		}
		
		List<RelatedBean> datas = null;
		// 媒体登录
		if (UserCompany.TYPE_MEDIA == uc.getType()) {
			String mediaUrl = uc.getCompanyUrl();
			
			datas = this.dataRecordService.queryRelatedAdvertiserList(mediaUrl,keyWord);
		} 
		// 广告主登录
		if (UserCompany.TYPE_ADVERTER == uc.getType()) {
			String advertiserUrl = uc.getCompanyUrl();
			
			// 投放媒体列表
			datas = this.dataRecordService.queryRelatedMediaList(advertiserUrl, null, keyWord);
		}
		model.addAttribute("datas", datas);
		
		return "advert/confirmIllegal/relatedList";
	}
	
	/**
	 * 田间时间参数
	 * 
	 * @param paramMap
	 * @param request
	 * @throws Exception 
	 */
	private void addTimeParams(Map<String, Object> paramMap,HttpServletRequest request) throws Exception {
		String confirmTimeMin = request.getParameter("confirmTimeMin");
		String confirmTimeMax = request.getParameter("confirmTimeMax");
		if (StringUtil.isNotEmpty(confirmTimeMin))
			confirmTimeMin = URLDecoder.decode(confirmTimeMin, "utf-8");
		if (StringUtil.isNotEmpty(confirmTimeMax))	
			confirmTimeMax = URLDecoder.decode(confirmTimeMax, "utf-8");
		if (StringUtil.isNotEmpty(confirmTimeMin) 
				&& StringUtil.isNotEmpty(confirmTimeMax)) {
			paramMap.put("confirmTimeMin", confirmTimeMin);
			paramMap.put("confirmTimeMax", confirmTimeMax);
		} else {
			String dateChange = request.getParameter("dateChange");
			if (StringUtil.isNotEmpty(dateChange))
				dateChange = URLDecoder.decode(dateChange, "utf-8");
			if (StringUtil.isEmpty(dateChange)) {
				// 查90天内的
				confirmTimeMin = DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, AdvertConstants.SHOW_DAYS), DateUtil.FORMAT_DATE);
				confirmTimeMax = DateUtil.formatDate(DateUtil.getCurDate(), DateUtil.FORMAT_DATE);
			} else {
				if ("昨日".equals(dateChange)) {
					confirmTimeMin = DateUtil.formatDate(
							DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE);
					confirmTimeMax = DateUtil.formatDate(
							DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE);	
				}
				if ("过去7天".equals(dateChange)) {
					confirmTimeMin = DateUtil.formatDate(
							DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -7), DateUtil.FORMAT_DATE);
					confirmTimeMax = DateUtil.formatDate(
							DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE);
				}
				if ("过去15天".equals(dateChange)) {
					confirmTimeMin = DateUtil.formatDate(
							DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -15), DateUtil.FORMAT_DATE);
					confirmTimeMax = DateUtil.formatDate(
							DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE);
				}
				if ("过去30天".equals(dateChange)) {
					confirmTimeMin = DateUtil.formatDate(
							DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -30), DateUtil.FORMAT_DATE);
					confirmTimeMax = DateUtil.formatDate(
							DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE);
				}
				if ("上周".equals(dateChange)) {
					confirmTimeMin = DateUtil.getPreviousWeekMonday();
					confirmTimeMax = DateUtil.getPreviousWeekSunday();
				}
				if ("上月".equals(dateChange)) {
					confirmTimeMin = DateUtil.getPreviousMonthFirst();
					confirmTimeMax = DateUtil.getPreviousMonthEnd();
				}
				if ("本月".equals(dateChange)) {
					confirmTimeMin = DateUtil.getFirstDayOfMonth();
					confirmTimeMax = DateUtil.formatDate(DateUtil.getCurDate(), DateUtil.FORMAT_DATE);
				}
			}

			paramMap.put("confirmTimeMin", confirmTimeMin);
			paramMap.put("confirmTimeMax", confirmTimeMax);
		}
	}
}
