package com.greenkoo.record.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenkoo.company.model.UserCompany;
import com.greenkoo.record.model.DataRecord;
import com.greenkoo.record.model.form.DataCountBean;
import com.greenkoo.record.service.IDataRecordService;
import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.MathUtil;
import com.greenkoo.utils.StringUtil;

/**
 * 数据统计Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/dataCount")
public class DataCountController {

	@Autowired
	private IDataRecordService dataRecordService;

	/**
	 * 严重违法统计
	 * 
	 * @return
	 */
	@RequestMapping("/severe")
	@ResponseBody
	public Map<String, Object> severeCount(HttpServletRequest request) {
		
		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		String dateChange = request.getParameter("dateChange");
		String confirmTimeMin = request.getParameter("confirmTimeMin");
		String confirmTimeMax = request.getParameter("confirmTimeMax");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("level", DataRecord.LEVEL_SEVERE);
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		// 媒体登录
		if (UserCompany.TYPE_MEDIA == loginCompany.getType()) {
			paramMap.put("mediaUrl", loginCompany.getCompanyUrl());
			// 违法广告主统计
			DataCountBean advertiserCountBean = this.countByType("3", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
			map.put("companyType", UserCompany.TYPE_MEDIA);
			map.put("advertiser", advertiserCountBean);
		}
		
		// 广告主登录
		if (UserCompany.TYPE_ADVERTER == loginCompany.getType()) {
			paramMap.put("advertiserUrl", loginCompany.getCompanyUrl());
			// 违法媒体统计
			DataCountBean mediaCountBean = this.countByType("4", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
			map.put("companyType", UserCompany.TYPE_ADVERTER);
			map.put("media", mediaCountBean);
		}

		// 违法广告活动统计
		DataCountBean advertCountBean = this.countByType("1", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
				
		// 违法广告创意统计
		DataCountBean creativeCountBean = this.countByType("2", dateChange, confirmTimeMin, confirmTimeMax, paramMap);

		// 总体已整改广告创意统计
		DataCountBean allCorrectCountBean = this.countByType("5", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
				
		// 我方已整改广告创意统计
		paramMap.put("roleUrl", loginCompany.getCompanyUrl());
		DataCountBean selfCorrectCountBean = this.countByType("6", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
		
		// 违法广告活动占比
		String countProp = this.countProportion(dateChange, confirmTimeMin, confirmTimeMax, paramMap);
		
		map.put("advert", advertCountBean);
		map.put("creative", creativeCountBean);
		map.put("allCorrect", allCorrectCountBean);
		map.put("selfCorrect", selfCorrectCountBean);
		map.put("countProp", countProp);
		return map;
	}

	/**
	 * 一般违法统计
	 * 
	 * @return
	 */
	@RequestMapping("/common")
	@ResponseBody
	public Map<String, Object> commonCount(HttpServletRequest request) {
		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		String dateChange = request.getParameter("dateChange");
		String confirmTimeMin = request.getParameter("confirmTimeMin");
		String confirmTimeMax = request.getParameter("confirmTimeMax");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("level", DataRecord.LEVEL_COMMON);
		
		Map<String, Object> map = new HashMap<String, Object>();

		// 媒体登录
		if (UserCompany.TYPE_MEDIA == loginCompany.getType()) {
			paramMap.put("mediaUrl", loginCompany.getCompanyUrl());
			// 违法广告主统计
			DataCountBean advertiserCountBean = this.countByType("3", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
			map.put("companyType", UserCompany.TYPE_MEDIA);
			map.put("advertiser", advertiserCountBean);
		}
		
		// 广告主登录
		if (UserCompany.TYPE_ADVERTER == loginCompany.getType()) {
			paramMap.put("advertiserUrl", loginCompany.getCompanyUrl());
			// 违法媒体统计
			DataCountBean mediaCountBean = this.countByType("4", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
			map.put("companyType", UserCompany.TYPE_ADVERTER);
			map.put("media", mediaCountBean);
		}
		
		// 违法广告活动统计
		DataCountBean advertCountBean = this.countByType("1", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
				
		// 违法广告创意统计
		DataCountBean creativeCountBean = this.countByType("2", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
		
		// 总体已整改广告创意统计
		DataCountBean allCorrectCountBean = this.countByType("5", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
				
		// 我方已整改广告创意统计
		paramMap.put("roleUrl", loginCompany.getCompanyUrl());
		DataCountBean selfCorrectCountBean = this.countByType("6", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
		
		// 违法广告活动占比
		String countProp = this.countProportion(dateChange, confirmTimeMin, confirmTimeMax, paramMap);

		map.put("advert", advertCountBean);
		map.put("creative", creativeCountBean);
		map.put("allCorrect", allCorrectCountBean);
		map.put("selfCorrect", selfCorrectCountBean);
		map.put("countProp", countProp);
		return map;
	}

	/**
	 * 轻微违法统计
	 * 
	 * @return
	 */
	@RequestMapping("/light")
	@ResponseBody
	public Map<String, Object> lightCount(HttpServletRequest request) {
		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		String dateChange = request.getParameter("dateChange");
		String confirmTimeMin = request.getParameter("confirmTimeMin");
		String confirmTimeMax = request.getParameter("confirmTimeMax");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("level", DataRecord.LEVEL_LIGHT);

		Map<String, Object> map = new HashMap<String, Object>();

		// 媒体登录
		if (UserCompany.TYPE_MEDIA == loginCompany.getType()) {
			paramMap.put("mediaUrl", loginCompany.getCompanyUrl());
			// 违法广告主统计
			DataCountBean advertiserCountBean = this.countByType("3", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
			map.put("companyType", UserCompany.TYPE_MEDIA);
			map.put("advertiser", advertiserCountBean);
		}
		
		// 广告主登录
		if (UserCompany.TYPE_ADVERTER == loginCompany.getType()) {
			paramMap.put("advertiserUrl", loginCompany.getCompanyUrl());
			// 违法媒体统计
			DataCountBean mediaCountBean = this.countByType("4", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
			map.put("companyType", UserCompany.TYPE_ADVERTER);
			map.put("media", mediaCountBean);
		}
		
		// 违法广告活动统计
		DataCountBean advertCountBean = this.countByType("1", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
				
		// 违法广告创意统计
		DataCountBean creativeCountBean = this.countByType("2", dateChange, confirmTimeMin, confirmTimeMax, paramMap);

		// 总体已整改广告创意统计
		DataCountBean allCorrectCountBean = this.countByType("5", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
				
		// 我方已整改广告创意统计
		paramMap.put("roleUrl", loginCompany.getCompanyUrl());
		DataCountBean selfCorrectCountBean = this.countByType("6", dateChange, confirmTimeMin, confirmTimeMax, paramMap);
		
		// 违法广告活动占比
		String countProp = this.countProportion(dateChange, confirmTimeMin, confirmTimeMax, paramMap);
		
		map.put("advert", advertCountBean);
		map.put("creative", creativeCountBean);
		map.put("allCorrect", allCorrectCountBean);
		map.put("selfCorrect", selfCorrectCountBean);
		map.put("countProp", countProp);
		return map;
	}

	/**
	 * 违法占比
	 * 
	 * @param dateChange
	 * @param paramMap
	 * @return
	 */
	private String countProportion(String dateChange, String confirmTimeMin, String confirmTimeMax,
			Map<String, Object> paramMap) {
		Integer level = (Integer) paramMap.get("level");
		// 违法量占比
		double countProp = 0F;

		if (StringUtil.isNotEmpty(confirmTimeMin) && StringUtil.isNotEmpty(confirmTimeMax)) {

			paramMap.put("confirmTimeMin", confirmTimeMin);
			paramMap.put("confirmTimeMax", confirmTimeMax);
			int curCount = this.dataRecordService.countAdvertProportion(paramMap);

			paramMap.remove("level");
			// 所有违法广告数
			int allCount = this.dataRecordService.countAdvertProportion(paramMap);

			if (allCount != 0) {
				countProp = MathUtil.div(curCount, allCount, 4) * 100;
			}
			
			if(MathUtil.isInteger(countProp)) return String.valueOf(Math.round(countProp));
			
			return String.format("%.2f", countProp);
		}

		// 沒有选择默认当天
		if (StringUtil.isEmpty(dateChange)) {
			paramMap.put("level", level);
			paramMap.put("confirmTime", DateUtil.formatDate(DateUtil.getCurDate(), DateUtil.FORMAT_DATE));
			// 当前违法广告数
			int curCount = this.dataRecordService.countAdvertProportion(paramMap);

			paramMap.remove("level");
			// 所有违法广告数
			int allCount = this.dataRecordService.countAdvertProportion(paramMap);
			
			if (allCount != 0) {
				countProp = MathUtil.div(curCount, allCount, 4) * 100;
			}
		}

		// 选择昨日
		if ("昨日".equals(dateChange)) {
			paramMap.put("level", level);
			paramMap.put("confirmTime", DateUtil.formatDate(
					DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE));
			// 当前违法广告数
			int curCount = this.dataRecordService.countAdvertProportion(paramMap);

			paramMap.remove("level");
			// 所有违法广告数
			int allCount = this.dataRecordService.countAdvertProportion(paramMap);
			
			if (allCount != 0) {
				countProp = MathUtil.div(curCount, allCount, 4) * 100;
			}
		}

		// 选择过去7天
		if ("过去7天".equals(dateChange)) {
			paramMap.put("level", level);
			paramMap.put("confirmTimeMin", DateUtil.formatDate(
					DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -7), DateUtil.FORMAT_DATE));
			paramMap.put("confirmTimeMax", DateUtil.formatDate(
					DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE));
			// 当前违法广告数
			int curCount = this.dataRecordService.countAdvertProportion(paramMap);

			paramMap.remove("level");
			// 所有违法广告数
			int allCount = this.dataRecordService.countAdvertProportion(paramMap);

			if (allCount != 0) {
				countProp = MathUtil.div(curCount, allCount, 4) * 100;
			}
		}

		// 选择过去15天
		if ("过去15天".equals(dateChange)) {
			paramMap.put("level", level);
			paramMap.put("confirmTimeMin", DateUtil.formatDate(
					DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -15), DateUtil.FORMAT_DATE));
			paramMap.put("confirmTimeMax", DateUtil.formatDate(
					DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE));
			// 当前违法广告数
			int curCount = this.dataRecordService.countAdvertProportion(paramMap);

			paramMap.remove("level");
			// 所有违法广告数
			int allCount = this.dataRecordService.countAdvertProportion(paramMap);
			
			if (allCount != 0) {
				countProp = MathUtil.div(curCount, allCount, 4) * 100;
			}
		}

		// 选择过去30天
		if ("过去30天".equals(dateChange)) {
			paramMap.put("level", level);
			paramMap.put("confirmTimeMin", DateUtil.formatDate(
					DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -30), DateUtil.FORMAT_DATE));
			paramMap.put("confirmTimeMax", DateUtil.formatDate(
					DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE));
			// 当前违法广告数
			int curCount = this.dataRecordService.countAdvertProportion(paramMap);

			paramMap.remove("level");
			// 所有违法广告数
			int allCount = this.dataRecordService.countAdvertProportion(paramMap);
			
			if (allCount != 0) {
				countProp = MathUtil.div(curCount, allCount, 4) * 100;
			}
		}

		// 选择上周
		if ("上周".equals(dateChange)) {
			paramMap.put("level", level);
			paramMap.put("confirmTimeMin", DateUtil.getPreviousWeekMonday());
			paramMap.put("confirmTimeMax", DateUtil.getPreviousWeekSunday());
			// 当前违法广告数
			int curCount = this.dataRecordService.countAdvertProportion(paramMap);

			paramMap.remove("level");
			// 所有违法广告数
			int allCount = this.dataRecordService.countAdvertProportion(paramMap);

			if (allCount != 0) {
				countProp = MathUtil.div(curCount, allCount, 4) * 100;
			}
		}

		// 选择上月
		if ("上月".equals(dateChange)) {
			paramMap.put("level", level);
			paramMap.put("confirmTimeMin", DateUtil.getPreviousMonthFirst());
			paramMap.put("confirmTimeMax", DateUtil.getPreviousMonthEnd());
			// 当前违法广告数
			int curCount = this.dataRecordService.countAdvertProportion(paramMap);

			paramMap.remove("level");
			// 所有违法广告数
			int allCount = this.dataRecordService.countAdvertProportion(paramMap);

			if (allCount != 0) {
				countProp = MathUtil.div(curCount, allCount, 4) * 100;
			}
		}

		// 本月
		if ("本月".equals(dateChange)) {
			paramMap.put("level", level);
			paramMap.put("confirmTimeMin", DateUtil.getFirstDayOfMonth());
			paramMap.put("confirmTimeMax", DateUtil.formatDate(DateUtil.getCurDate(), DateUtil.FORMAT_DATE));
			// 当前违法广告数
			int curCount = this.dataRecordService.countAdvertProportion(paramMap);

			paramMap.remove("level");
			// 所有违法广告数
			int allCount = this.dataRecordService.countAdvertProportion(paramMap);
			
			if (allCount != 0) {
				countProp = MathUtil.div(curCount, allCount, 4) * 100;
			}
		}
		
		if(MathUtil.isInteger(countProp)) return String.valueOf(Math.round(countProp));
		
		return String.format("%.2f", countProp);
	}
	
	/**
	 * 根据时间统计广告、广告创意、广告主
	 * 
	 * @param type 自定义类型 -1：广告、2：广告创意、3：广告主
	 * @param dateChange 昨日、过去7天、上月、上周、本月...
	 * @param confirmTimeMin 时间间隔开始
	 * @param confirmTimeMax 时间间隔结束
	 * @param paramMap
	 * @return
	 */
	private DataCountBean countByType(String type,String dateChange, String confirmTimeMin, String confirmTimeMax,
			Map<String, Object> paramMap) {
		// 数量
		Integer advertCount = 0;
		// 占比
		String timeProp = "";
		// 增量
		int increase = 0;
		
		// 待比较的量
		Integer tmpCount = 0;

		// 有日期区间查询直接进行日期筛选查询
		if (StringUtil.isNotEmpty(confirmTimeMin) && StringUtil.isNotEmpty(confirmTimeMax)) {
			paramMap.put("confirmTimeMin", confirmTimeMin);
			paramMap.put("confirmTimeMax", confirmTimeMax);
			
			// 广告
			if ("1".equals(type)) advertCount = this.dataRecordService.countAdvert(paramMap);
			
			// 创意
			if ("2".equals(type)) advertCount = this.dataRecordService.queryCount(paramMap);
			
			// 广告主
			if ("3".equals(type)) advertCount = this.dataRecordService.countAdvertiser(paramMap);
			
			// 媒体
			if ("4".equals(type)) advertCount = this.dataRecordService.countMedia(paramMap);
			
			// 总体已整改广告创意
			if ("5".equals(type)) advertCount = this.dataRecordService.countAllCorrect(paramMap);
			
			// 我方已整改广告创意
			if ("6".equals(type)) advertCount = this.dataRecordService.countSelfCorrect(paramMap);
			
			int daysBetween = DateUtil.daysBetween(confirmTimeMax, confirmTimeMin, true);
			
			// 区间为0的话，就和所在区间的上一天比
			if (daysBetween == 0) {
				try {
					paramMap.put("confirmTimeMin",
							DateUtil.formatDate(
									DateUtil.addDate(DateUtil.transStrToDate(confirmTimeMin, DateUtil.FORMAT_DATE),
											Calendar.DAY_OF_MONTH, -1),
									DateUtil.FORMAT_DATE));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				try {
					paramMap.put("confirmTimeMax",
							DateUtil.formatDate(
									DateUtil.addDate(DateUtil.transStrToDate(confirmTimeMax, DateUtil.FORMAT_DATE),
											Calendar.DAY_OF_MONTH, -1),
									DateUtil.FORMAT_DATE));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				try {
					paramMap.put("confirmTimeMin",
							DateUtil.formatDate(
									DateUtil.addDate(DateUtil.transStrToDate(confirmTimeMin, DateUtil.FORMAT_DATE),
											Calendar.DAY_OF_MONTH, -daysBetween),
									DateUtil.FORMAT_DATE));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				try {
					paramMap.put("confirmTimeMax",
							DateUtil.formatDate(
									DateUtil.addDate(DateUtil.transStrToDate(confirmTimeMax, DateUtil.FORMAT_DATE),
											Calendar.DAY_OF_MONTH, -daysBetween),
									DateUtil.FORMAT_DATE));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			// 广告
			if ("1".equals(type)) tmpCount = this.dataRecordService.countAdvert(paramMap);
			
			// 创意
			if ("2".equals(type)) tmpCount = this.dataRecordService.queryCount(paramMap);
			
			// 广告主
			if ("3".equals(type)) tmpCount = this.dataRecordService.countAdvertiser(paramMap);
			
			// 媒体
			if ("4".equals(type)) tmpCount = this.dataRecordService.countMedia(paramMap);
			
			// 总体已整改广告创意
			if ("5".equals(type)) tmpCount = this.dataRecordService.countAllCorrect(paramMap);
			
			// 我方已整改广告创意
			if ("6".equals(type)) tmpCount = this.dataRecordService.countSelfCorrect(paramMap);
			
			if (advertCount == null) advertCount = 0;
			if (tmpCount == null) tmpCount = 0;
			
			increase = (int) MathUtil.sub(advertCount, tmpCount);
			if (tmpCount != 0) {
				double tmpProp = MathUtil.div(increase, tmpCount, 4) * 100;
				if(MathUtil.isInteger(tmpProp)) timeProp = String.valueOf(tmpProp);
				else timeProp = String.format("%.2f", tmpProp);
			} else {
				timeProp = "∞";
			}

			return new DataCountBean(advertCount, timeProp, increase);
		}

		// 沒有选择默认当天
		if (StringUtil.isEmpty(dateChange)) {
			paramMap.put("confirmTime", DateUtil.formatDate(DateUtil.getCurDate(), DateUtil.FORMAT_DATE));
			
			// 广告
			if ("1".equals(type)) advertCount = this.dataRecordService.countAdvert(paramMap);
			
			// 创意
			if ("2".equals(type)) advertCount = this.dataRecordService.queryCount(paramMap);
			
			// 广告主
			if ("3".equals(type)) advertCount = this.dataRecordService.countAdvertiser(paramMap);
			
			// 媒体
			if ("4".equals(type)) advertCount = this.dataRecordService.countMedia(paramMap);
			
			// 总体已整改广告创意
			if ("5".equals(type)) advertCount = this.dataRecordService.countAllCorrect(paramMap);
			
			// 我方已整改广告创意
			if ("6".equals(type)) advertCount = this.dataRecordService.countSelfCorrect(paramMap);
			
			// 占比
			// 查询昨天的
			paramMap.put("confirmTime", DateUtil.formatDate(
					DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE));
			
			// 广告
			if ("1".equals(type)) tmpCount = this.dataRecordService.countAdvert(paramMap);
			
			// 创意
			if ("2".equals(type)) tmpCount = this.dataRecordService.queryCount(paramMap);
			
			// 广告主
			if ("3".equals(type)) tmpCount = this.dataRecordService.countAdvertiser(paramMap);
			
			// 媒体
			if ("4".equals(type)) tmpCount = this.dataRecordService.countMedia(paramMap);
			
			// 总体已整改广告创意
			if ("5".equals(type)) tmpCount = this.dataRecordService.countAllCorrect(paramMap);
			
			// 我方已整改广告创意
			if ("6".equals(type)) tmpCount = this.dataRecordService.countSelfCorrect(paramMap);
			
			
			if (advertCount == null) advertCount = 0;
			if (tmpCount == null) tmpCount = 0;
			
			increase = (int) MathUtil.sub(advertCount, tmpCount);
			
		} else {
			// 选择昨日
			if ("昨日".equals(dateChange)) {
				paramMap.put("confirmTime", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE));
				
				// 广告
				if ("1".equals(type)) advertCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) advertCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) advertCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) advertCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) advertCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) advertCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				// 占比
				// 查询前天的
				paramMap.put("confirmTime", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -2), DateUtil.FORMAT_DATE));
				// 广告
				if ("1".equals(type)) tmpCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) tmpCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) tmpCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) tmpCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) tmpCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) tmpCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				if (advertCount == null) advertCount = 0;
				if (tmpCount == null) tmpCount = 0;
				
				increase = (int) MathUtil.sub(advertCount, tmpCount);
			}
			
			// 选择过去7天
			if ("过去7天".equals(dateChange)) {
				paramMap.put("confirmTimeMin", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -7), DateUtil.FORMAT_DATE));
				paramMap.put("confirmTimeMax", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE));
				// 广告
				if ("1".equals(type)) advertCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) advertCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) advertCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) advertCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) advertCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) advertCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				// 占比
				// 查询过去7天的前七天
				paramMap.put("confirmTimeMin", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -14), DateUtil.FORMAT_DATE));
				paramMap.put("confirmTimeMax", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -8), DateUtil.FORMAT_DATE));
				// 广告
				if ("1".equals(type)) tmpCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) tmpCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) tmpCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) tmpCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) tmpCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) tmpCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				if (advertCount == null) advertCount = 0;
				if (tmpCount == null) tmpCount = 0;
				
				increase = (int) MathUtil.sub(advertCount, tmpCount);
			}
			
			// 选择过去15天
			if ("过去15天".equals(dateChange)) {
				paramMap.put("confirmTimeMin", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -15), DateUtil.FORMAT_DATE));
				paramMap.put("confirmTimeMax", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE));
				// 广告
				if ("1".equals(type)) advertCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) advertCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) advertCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) advertCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) advertCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) advertCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				// 占比
				// 查询过去15天的前15天
				paramMap.put("confirmTimeMin", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -30), DateUtil.FORMAT_DATE));
				paramMap.put("confirmTimeMax", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -16), DateUtil.FORMAT_DATE));
				
				// 广告
				if ("1".equals(type)) tmpCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) tmpCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) tmpCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) tmpCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) tmpCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) tmpCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				if (advertCount == null) advertCount = 0;
				if (tmpCount == null) tmpCount = 0;
				
				increase = (int) MathUtil.sub(advertCount, tmpCount);
			}
			
			// 选择过去30天
			if ("过去30天".equals(dateChange)) {
				paramMap.put("confirmTimeMin", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -30), DateUtil.FORMAT_DATE));
				paramMap.put("confirmTimeMax", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -1), DateUtil.FORMAT_DATE));
				// 广告
				if ("1".equals(type)) advertCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) advertCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) advertCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) advertCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) advertCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) advertCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				// 占比
				// 查询过去30天的前30天
				paramMap.put("confirmTimeMin", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -60), DateUtil.FORMAT_DATE));
				paramMap.put("confirmTimeMax", DateUtil.formatDate(
						DateUtil.addDate(DateUtil.getCurDate(), Calendar.DAY_OF_MONTH, -31), DateUtil.FORMAT_DATE));

				// 广告
				if ("1".equals(type)) tmpCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) tmpCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) tmpCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) tmpCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) tmpCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) tmpCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				if (advertCount == null) advertCount = 0;
				if (tmpCount == null) tmpCount = 0;

				increase = (int) MathUtil.sub(advertCount, tmpCount);
			}
			
			// 选择上周
			if ("上周".equals(dateChange)) {
				paramMap.put("confirmTimeMin", DateUtil.getPreviousWeekMonday());
				paramMap.put("confirmTimeMax", DateUtil.getPreviousWeekSunday());
				// 广告
				if ("1".equals(type)) advertCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) advertCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) advertCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) advertCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) advertCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) advertCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				// 占比
				// 查询上周的上周
				try {
					paramMap.put("confirmTimeMin", DateUtil.formatDate(
							DateUtil.addDate(DateUtil.transStrToDate(DateUtil.getPreviousWeekMonday(), DateUtil.FORMAT_DATE), Calendar.DAY_OF_MONTH, -7), DateUtil.FORMAT_DATE));
					paramMap.put("confirmTimeMax", DateUtil.formatDate(
							DateUtil.addDate(DateUtil.transStrToDate(DateUtil.getPreviousWeekSunday(), DateUtil.FORMAT_DATE), Calendar.DAY_OF_MONTH, -7), DateUtil.FORMAT_DATE));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				// 广告
				if ("1".equals(type)) tmpCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) tmpCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) tmpCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) tmpCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) tmpCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) tmpCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				if (advertCount == null) advertCount = 0;
				if (tmpCount == null) tmpCount = 0;
				
				increase = (int) MathUtil.sub(advertCount, tmpCount);
			}
			
			// 选择上月
			if ("上月".equals(dateChange)) {
				paramMap.put("confirmTimeMin", DateUtil.getPreviousMonthFirst());
				paramMap.put("confirmTimeMax", DateUtil.getPreviousMonthEnd());
				// 广告
				if ("1".equals(type)) advertCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) advertCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) advertCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) advertCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) advertCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) advertCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				// 占比
				// 查询上月的上月
				Calendar calendar = Calendar.getInstance();
				calendar .add(Calendar.MONTH, -2);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				paramMap.put("confirmTimeMin", DateUtil.formatDate(calendar.getTime(), DateUtil.FORMAT_DATE));
				calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.add(Calendar.DATE, -1);
				paramMap.put("confirmTimeMax", DateUtil.formatDate(calendar.getTime(), DateUtil.FORMAT_DATE));
				
				// 广告
				if ("1".equals(type)) tmpCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) tmpCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) tmpCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) tmpCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) tmpCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) tmpCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				if (advertCount == null) advertCount = 0;
				if (tmpCount == null) tmpCount = 0;
				
				increase = (int) MathUtil.sub(advertCount, tmpCount);
			}
			
			// 本月
			if ("本月".equals(dateChange)) {
				paramMap.put("confirmTimeMin", DateUtil.getFirstDayOfMonth());
				
				paramMap.put("confirmTimeMax", DateUtil.formatDate(DateUtil.getCurDate(), DateUtil.FORMAT_DATE));
				// 广告
				if ("1".equals(type)) advertCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) advertCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) advertCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) advertCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) advertCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) advertCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				
				// 占比
				// 查询上月
				paramMap.put("confirmTimeMin", DateUtil.getPreviousMonthFirst());
				paramMap.put("confirmTimeMax", DateUtil.getPreviousMonthEnd());
				
				// 广告
				if ("1".equals(type)) tmpCount = this.dataRecordService.countAdvert(paramMap);
				
				// 创意
				if ("2".equals(type)) tmpCount = this.dataRecordService.queryCount(paramMap);
				
				// 广告主
				if ("3".equals(type)) tmpCount = this.dataRecordService.countAdvertiser(paramMap);
				
				// 媒体
				if ("4".equals(type)) tmpCount = this.dataRecordService.countMedia(paramMap);
				
				// 总体已整改广告创意
				if ("5".equals(type)) tmpCount = this.dataRecordService.countAllCorrect(paramMap);
				
				// 我方已整改广告创意
				if ("6".equals(type)) tmpCount = this.dataRecordService.countSelfCorrect(paramMap);
				
				if (advertCount == null) advertCount = 0;
				if (tmpCount == null) tmpCount = 0;
				
				increase = (int) MathUtil.sub(advertCount, tmpCount);
			}
		}
		
		if (tmpCount != 0) {
			double tmpProp = MathUtil.div(increase, tmpCount, 4) * 100;
			if(MathUtil.isInteger(tmpProp)) timeProp = String.valueOf(tmpProp);
			else timeProp = String.format("%.2f", tmpProp);
		} else {
			timeProp = "∞";
		}
		
		return new DataCountBean(advertCount, timeProp, increase);
	}
}
