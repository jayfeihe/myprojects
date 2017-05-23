package com.greenkoo.record.controller;

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
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.SecurityUtil;
import com.greenkoo.utils.StringUtil;

/**
 * 广告详情Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/advert")
public class AdvertDetailController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDataRecordService dataRecordService;
	
	/**
	 * 跳转活动详情页
	 * 
	 * @return
	 */
	@RequestMapping("/detail")
	public String advertDetail(String id, HttpServletRequest request) {
		String ad_id = SecurityUtil.BASE64Encode(id);
		String url = "detail/media?ad_id="+ad_id;
		
		UserCompany uc = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		
		// 媒体登录
		if (UserCompany.TYPE_MEDIA == uc.getType()) {
			url = "detail/media?ad_id="+ad_id;
		} 
		// 广告主登录
		if (UserCompany.TYPE_ADVERTER == uc.getType()) {
			url = "detail/adverter?ad_id="+ad_id;
		}
		
		return "redirect:" + url;
	}
	
	/**
	 * 媒体详情页
	 * 
	 * @return
	 */
	@RequestMapping("/detail/media")
	public String mediaDetail(String ad_id, HttpServletRequest request, Model model) {
		String id = SecurityUtil.BASE64Decode(ad_id);
		logger.info("媒体登录进入广告创意详情页，创意id:" + id);
		
		DataRecord record = this.dataRecordService.queryById(id);
		model.addAttribute("record", record);
		
//		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
//		
//		Map<String, Object> queryMap = new HashMap<String,Object>();
//		// 根据广告和媒体查找所有创意
//		queryMap.put("landingUrl", record.getLandingUrl());
//		queryMap.put("mediaUrl", loginCompany.getCompanyUrl());
		
		return "advert/detail/media";
	}
	
	/**
	 * 广告主详情页
	 * 
	 * @return
	 */
	@RequestMapping("/detail/adverter")
	public String adverterDetail(String ad_id, HttpServletRequest request, Model model) {
		String id = SecurityUtil.BASE64Decode(ad_id);
		logger.info("广告主登录进入广告创意详情页，创意id:" + id);
		DataRecord record = this.dataRecordService.queryById(id);
		model.addAttribute("record", record);
		
		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		String advertiserUrl = loginCompany.getCompanyUrl();
		String landingUrl = record.getLandingUrl();
		
		String keyWord = request.getParameter("keyWord");
		// 涉及媒体数
		int mediaCount = this.dataRecordService.queryRelatedMediaCount(advertiserUrl, landingUrl, keyWord );
		model.addAttribute("mediaCount", mediaCount);
		
		// 查询涉及媒体列表
		List<RelatedBean> datas = this.dataRecordService.queryRelatedMediaList(advertiserUrl, landingUrl,keyWord);
		model.addAttribute("datas", datas);
		return "advert/detail/adverter";
	}
	
	/**
	 * 媒体所有的违法广告创意
	 * 
	 * @return
	 */
	@RequestMapping("/detail/mediaIllegalList")
	public String mediaIllegal(String id, HttpServletRequest request, Model model) {
		// 登录媒体
		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		// 根据创意id查找所属广告
		DataRecord dataRecord = this.dataRecordService.queryById(id);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 根据广告和媒体查找所有创意
		paramMap.put("landingUrl", dataRecord.getLandingUrl());
		paramMap.put("mediaUrl", loginCompany.getCompanyUrl());
		
		try {
			// 终端类型条件
			String terminalType = request.getParameter("terminalType");
			if (StringUtil.isNotEmpty(terminalType))
				terminalType = URLDecoder.decode(terminalType, "UTF-8");
			// 状态
			String status = request.getParameter("status");
			if (StringUtil.isNotEmpty(status))
				status = URLDecoder.decode(status, "UTF-8");
			
			paramMap.put("terminalType", terminalType);
			paramMap.put("status", status);
			
			// 时间条件
			this.addTimeParams(paramMap, request);
		} catch (Exception e) {
			logger.error("URL解码时间参数出错：" + e.getMessage(), e);
		}
		
		logger.info("媒体【"+loginCompany.getCompanyUrl()+"】查询自己的创意传递参数：" + paramMap);
		
//		Pager pager = new Pager();
//		int totalCount = this.dataRecordService.queryCount(paramMap);
//		pager.init(totalCount);
//		paramMap.put("pageSize", pager.getPageSize());
//		paramMap.put("pageOffset", pager.getPageOffset());
		List<DataRecord> mediaDatas = this.dataRecordService.queryList(paramMap);
//		pager.setDatas(datas);
//		model.addAttribute("pager", pager);
		model.addAttribute("mediaDatas", mediaDatas);
		return "advert/detail/mediaIllegalList";
	}
	
	/**
	 * 广告主所有的违法广告创意
	 * 
	 * @return
	 */
	@RequestMapping("/detail/adverterIllegalList")
	public String adverterIllegal(String id, HttpServletRequest request, Model model) {
		// 广告主
		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		// 根据创意id查找所属广告
		DataRecord dataRecord = this.dataRecordService.queryById(id);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 根据广告和广告主查找所有创意
		paramMap.put("landingUrl", dataRecord.getLandingUrl());
		paramMap.put("advertiserUrl", loginCompany.getCompanyUrl());
		try {
			// 终端类型条件
			String terminalType = request.getParameter("terminalType");
			if (StringUtil.isNotEmpty(terminalType))
				terminalType = URLDecoder.decode(terminalType, "UTF-8");
			
			// 状态
			String status = request.getParameter("status");
			if (StringUtil.isNotEmpty(status))
				status = URLDecoder.decode(status, "UTF-8");
			
			paramMap.put("terminalType", terminalType);
			paramMap.put("status", status);
			
			// 添加时间条件
			this.addTimeParams(paramMap, request);
		} catch (Exception e) {
			logger.error("URL解码参数出错：" + e.getMessage(), e);
		}
		
		logger.info("广告主【"+loginCompany.getCompanyUrl()+"】查询自己的创意传递参数：" + paramMap);
		
//		Pager pager = new Pager();
//		int totalCount = this.dataRecordService.queryCount(paramMap);
//		pager.init(totalCount);
//		paramMap.put("pageSize", pager.getPageSize());
//		paramMap.put("pageOffset", pager.getPageOffset());
		List<DataRecord> advertiserDatas = this.dataRecordService.queryList(paramMap);
//		pager.setDatas(datas);
//		model.addAttribute("pager", pager);
		model.addAttribute("advertiserDatas", advertiserDatas);
		return "advert/detail/adverterIllegalList";
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
