package com.greenkoo.echarts.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenkoo.company.model.UserCompany;
import com.greenkoo.echarts.model.EchartResult;
import com.greenkoo.echarts.model.Legend;
import com.greenkoo.echarts.model.Series;
import com.greenkoo.echarts.model.XAxis;
import com.greenkoo.echarts.model.YAxis;
import com.greenkoo.echarts.model.form.CountBean;
import com.greenkoo.echarts.service.IEChartService;
import com.greenkoo.record.model.DataRecord;
import com.greenkoo.sys.constants.CommonConstants;
import com.greenkoo.utils.DateUtil;
import com.greenkoo.utils.MathUtil;
import com.greenkoo.utils.StringUtil;

/**
 * 图表控制器
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/echarts")
public class EchartController {

	@Autowired
	private IEChartService eChartService;

	/**
	 * 异步获取首页图表数据
	 * 
	 * @param dateChange
	 *            快捷日期
	 * @param confirmTimeMin
	 *            开始日期选择
	 * @param confirmTimeMax
	 *            结束日期选择
	 * @param eType
	 *            两个比较类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/dataCount", method = RequestMethod.POST)
	@ResponseBody
	private EchartResult loadIndexDataCount(String dateChange, String confirmTimeMin, String confirmTimeMax,
			String eType,HttpServletRequest request) {
		
		Legend legend = null;
		XAxis[] xAxis = null;
		YAxis[] yAxis = null;
		Series[] series = null;
		EchartResult result = null;
		// Y轴数据格式显示
		String formatterL = "{value}",formatterR = "{value}";
		
		if (StringUtil.isEmpty(eType)) {
			legend = new Legend(new String[]{});
			xAxis = new XAxis[]{new XAxis(new String[]{})};
			yAxis = new YAxis[]{new YAxis("", 0, 0,formatterL),new YAxis("", 0, 0,formatterR)};
			series = new Series[]{new Series("", new String[]{})};
			result = new EchartResult(legend, xAxis, yAxis, series);
			return result;
		}
		
		String[] eTypeArray = eType.split(",");
		if (eTypeArray.length == 0) {
			legend = new Legend(new String[]{});
			xAxis = new XAxis[]{new XAxis(new String[]{})};
			yAxis = new YAxis[]{new YAxis("", 0, 0,formatterL),new YAxis("", 0, 0,formatterR)};
			series = new Series[]{new Series("", new String[]{})};
			result = new EchartResult(legend, xAxis, yAxis, series);
			return result;
		}

		// 条件map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		UserCompany loginCompany = (UserCompany) request.getSession().getAttribute(CommonConstants.LOGIN_COMPANY);
		// 广告主登录
		if (UserCompany.TYPE_ADVERTER == loginCompany.getType()) {
			paramMap.put("advertiserUrl", loginCompany.getCompanyUrl());
		}
		// 媒体登录
		if (UserCompany.TYPE_MEDIA == loginCompany.getType()) {
			paramMap.put("mediaUrl", loginCompany.getCompanyUrl());
		}
		
		// 标识我方已整改
		paramMap.put("roleUrl", loginCompany.getCompanyUrl());
		
		// 有時間段，按时间段操作
		if (StringUtil.isNotEmpty(confirmTimeMin) && StringUtil.isNotEmpty(confirmTimeMax)) {
			paramMap.put("confirmTimeMin", confirmTimeMin);
			paramMap.put("confirmTimeMax", confirmTimeMax);
		} else {
			// 快捷日期
			if (StringUtil.isEmpty(dateChange)) {
				// 默认当天
				confirmTimeMin = DateUtil.formatDate(DateUtil.getCurDate(), DateUtil.FORMAT_DATE);
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

		// 所有x轴时间
		List<String> allTime = null;
		if (confirmTimeMin.equals(confirmTimeMax)) {
			allTime = Arrays.asList(new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","23"});
		} else {
			allTime = DateUtil.getAllDate(confirmTimeMin, confirmTimeMax, true);
		}

		Map<String, String> dataMap = new HashMap<String, String>();
		
		// 左边数据
		String etypeL = eTypeArray[0];
		// 获取Y轴类目比较显示
		String eTypeLText = this.getLabelByEType(etypeL);
		Map<String, Object> mapL = this.getEChartDataByEType(etypeL, paramMap);
		Integer maxL = mapL.get("max") == null ? 0 : (Integer) mapL.get("max");
		List<CountBean> echartDatasL = (List<CountBean>) mapL.get("eDatas");
		// 时间所对应的y轴数量
		List<String> allCountL = new ArrayList<String>();
		
		// 格式化左边数据
		if ("S5".equals(etypeL)||"C5".equals(etypeL)||"L5".equals(etypeL)) {
			formatterL = "{value}%";
			if (echartDatasL != null && echartDatasL.size() > 0) {
				for (CountBean c : echartDatasL) {
					if (MathUtil.isInteger(c.getProp())) {
						dataMap.put(c.getConfirmTime(), String.valueOf(Math.round(c.getProp())));
					} else {
						dataMap.put(c.getConfirmTime(), String.valueOf(c.getProp()));
					}
				}
				
			}
		} else {
			if (echartDatasL != null && echartDatasL.size() > 0) {
				for (CountBean c : echartDatasL) {
					dataMap.put(c.getConfirmTime(), String.valueOf(c.getCount()));
				}
			}
		}
		
		// 添加数据
		if (!dataMap.isEmpty()) {
			for (String t : allTime) {
				if (dataMap.containsKey(t)) {
					allCountL.add(dataMap.get(t));
				} else {
					allCountL.add("0");
				}
			}
		} else {
			for (int i=0;i<allTime.size();i++) {
				allCountL.add("0");
			}
		}
		
		YAxis yAxisR = null;
		Series seriesR = null;
		// 两个全选的情况，显示右边的
		if (eTypeArray.length == 2) {
			// 右边数据
			String etypeR = eTypeArray[1];
			// 获取Y轴类目比较显示
			String eTypeRText = this.getLabelByEType(etypeR);
			Map<String, Object> mapR = this.getEChartDataByEType(etypeR, paramMap);
			Integer maxR = mapR.get("max") == null ? 0 : (Integer) mapR.get("max");
			List<CountBean> echartDatasR = (List<CountBean>) mapR.get("eDatas");
			// 时间所对应的y轴数量
			List<String> allCountR = new ArrayList<String>();
			
			// 格式化右边数据
			dataMap.clear();
			if ("S5".equals(etypeR)||"C5".equals(etypeR)||"L5".equals(etypeR)) {
				formatterR = "{value}%";
				if (echartDatasR != null && echartDatasR.size() > 0) {
					for (CountBean c : echartDatasR) {
						if (MathUtil.isInteger(c.getProp())) {
							dataMap.put(c.getConfirmTime(), String.valueOf(Math.round(c.getProp())));
						} else {
							dataMap.put(c.getConfirmTime(), String.valueOf(c.getProp()));
						}
					}
				}
			} else {
				if (echartDatasR != null && echartDatasR.size() > 0) {
					for (CountBean c : echartDatasR) {
						dataMap.put(c.getConfirmTime(), String.valueOf(c.getCount()));
					}
				}
			}
			
			// 添加数据
			if (!dataMap.isEmpty()) {
				for (String t : allTime) {
					if (dataMap.containsKey(t)) {
						allCountR.add(dataMap.get(t));
					} else {
						allCountR.add("0");
					}
				}
			} else {
				for (int i=0;i<allTime.size();i++) {
					allCountR.add("0");
				}
			}
			
			legend = new Legend(new String[]{eTypeLText, eTypeRText});
			yAxisR = new YAxis(eTypeRText, 0, getMaxNum(maxR, 5), formatterR);
			seriesR = new Series(eTypeRText, (String[]) allCountR.toArray(new String[allCountR.size()]));
		} else {
			legend = new Legend(new String[]{eTypeLText, ""});
			yAxisR = new YAxis("", 0, getMaxNum(maxL, 5), formatterR);
			seriesR = new Series("", new String[]{});
		}
		
		YAxis yAxisL = new YAxis(eTypeLText, 0, getMaxNum(maxL, 5), formatterL);
		Series seriesL = new Series(eTypeLText, (String[]) allCountL.toArray(new String[allCountL.size()]));
		
		// 创建图表所需属性数据
		xAxis = new XAxis[]{new XAxis((String[])allTime.toArray(new String[allTime.size()]))};
		yAxis = new YAxis[]{yAxisL, yAxisR};
		series = new Series[]{seriesL, seriesR};
		
		result = new EchartResult(legend, xAxis, yAxis, series);
		
		return result;
	}

	/**
	 * 根据前端勾选的项进行查询计算
	 * 
	 * @param eType
	 * @param paramMap
	 * @return
	 */
	private Map<String, Object> getEChartDataByEType(String eType, Map<String, Object> paramMap) {
		Integer max = 0;
		List<CountBean> echartDatas = null;
		paramMap.remove("level");
		paramMap.remove("propLevel");
		switch (eType) {
		case "S1": // 严重违法广告活动
			paramMap.put("level", DataRecord.LEVEL_SEVERE);
			max = this.eChartService.queryMaxAdvertCount(paramMap);
			echartDatas = this.eChartService.queryAdvertCountList(paramMap);
			break;

		case "S2": // 严重违法广告创意
			paramMap.put("level", DataRecord.LEVEL_SEVERE);
			max = this.eChartService.queryMaxCreativeCount(paramMap);
			echartDatas = this.eChartService.queryCreativeCountList(paramMap);
			break;

		case "S3": // 严重违法媒体
			paramMap.put("level", DataRecord.LEVEL_SEVERE);
			max = this.eChartService.queryMaxMediaCount(paramMap);
			echartDatas = this.eChartService.queryMediaCountList(paramMap);
			break;
			
		case "S4": // 严重违法媒体
			paramMap.put("level", DataRecord.LEVEL_SEVERE);
			max = this.eChartService.queryMaxAdvertiserCount(paramMap);
			echartDatas = this.eChartService.queryAdvertiserCountList(paramMap);
			break;

		case "S5": // 严重违法广告活动占比
			paramMap.put("propLevel", DataRecord.LEVEL_SEVERE);
			max = this.eChartService.queryMaxPropByLevel(paramMap);
			echartDatas = this.eChartService.queryPropByLevel(paramMap);
			break;
		
		case "S6": // 总体已整改严重违法广告创意
			paramMap.put("level", DataRecord.LEVEL_SEVERE);
			max = this.eChartService.queryMaxAllCorrectCount(paramMap);
			echartDatas = this.eChartService.queryAllCorrectCountList(paramMap);
			break;
			
		case "S7": // 我方已整改严重违法广告创意
			paramMap.put("level", DataRecord.LEVEL_SEVERE);
			max = this.eChartService.queryMaxSelfCorrectCount(paramMap);
			echartDatas = this.eChartService.querySelfCorrectCountList(paramMap);
			break;
			
		case "C1": // 一般违法广告活动
			paramMap.put("level", DataRecord.LEVEL_COMMON);
			max = this.eChartService.queryMaxAdvertCount(paramMap);
			echartDatas = this.eChartService.queryAdvertCountList(paramMap);
			break;

		case "C2": // 一般违法广告创意
			paramMap.put("level", DataRecord.LEVEL_COMMON);
			max = this.eChartService.queryMaxCreativeCount(paramMap);
			echartDatas = this.eChartService.queryCreativeCountList(paramMap);
			break;

		case "C3": // 一般违法媒体
			paramMap.put("level", DataRecord.LEVEL_COMMON);
			max = this.eChartService.queryMaxMediaCount(paramMap);
			echartDatas = this.eChartService.queryMediaCountList(paramMap);
			break;
			
		case "C4": // 一般违法媒体
			paramMap.put("level", DataRecord.LEVEL_COMMON);
			max = this.eChartService.queryMaxAdvertiserCount(paramMap);
			echartDatas = this.eChartService.queryAdvertiserCountList(paramMap);
			break;

		case "C5": // 一般违法广告活动占比
			paramMap.put("propLevel", DataRecord.LEVEL_COMMON);
			max = this.eChartService.queryMaxPropByLevel(paramMap);
			echartDatas = this.eChartService.queryPropByLevel(paramMap);
			break;

		case "C6": // 总体已整改一般违法广告创意
			paramMap.put("level", DataRecord.LEVEL_COMMON);
			max = this.eChartService.queryMaxAllCorrectCount(paramMap);
			echartDatas = this.eChartService.queryAllCorrectCountList(paramMap);
			break;
			
		case "C7": // 我方已整改一般违法广告创意
			paramMap.put("level", DataRecord.LEVEL_COMMON);
			max = this.eChartService.queryMaxSelfCorrectCount(paramMap);
			echartDatas = this.eChartService.querySelfCorrectCountList(paramMap);
			break;
			
		case "L1": // 轻微违法广告活动
			paramMap.put("level", DataRecord.LEVEL_LIGHT);
			max = this.eChartService.queryMaxAdvertCount(paramMap);
			echartDatas = this.eChartService.queryAdvertCountList(paramMap);
			break;

		case "L2": // 轻微违法广告创意
			paramMap.put("level", DataRecord.LEVEL_LIGHT);
			max = this.eChartService.queryMaxCreativeCount(paramMap);
			echartDatas = this.eChartService.queryCreativeCountList(paramMap);
			break;

		case "L3": // 轻微违法媒体
			paramMap.put("level", DataRecord.LEVEL_LIGHT);
			max = this.eChartService.queryMaxMediaCount(paramMap);
			echartDatas = this.eChartService.queryMediaCountList(paramMap);
			break;

		case "L4": // 一般违法媒体
			paramMap.put("level", DataRecord.LEVEL_LIGHT);
			max = this.eChartService.queryMaxAdvertiserCount(paramMap);
			echartDatas = this.eChartService.queryAdvertiserCountList(paramMap);
			break;
			
		case "L5": // 轻微违法广告活动占比
			paramMap.put("propLevel", DataRecord.LEVEL_LIGHT);
			max = this.eChartService.queryMaxPropByLevel(paramMap);
			echartDatas = this.eChartService.queryPropByLevel(paramMap);
			break;
			
		case "L6": // 总体已整改轻微违法广告创意
			paramMap.put("level", DataRecord.LEVEL_LIGHT);
			max = this.eChartService.queryMaxAllCorrectCount(paramMap);
			echartDatas = this.eChartService.queryAllCorrectCountList(paramMap);
			break;
			
		case "L7": // 我方已整改轻微违法广告创意	
			paramMap.put("level", DataRecord.LEVEL_LIGHT);
			max = this.eChartService.queryMaxSelfCorrectCount(paramMap);
			echartDatas = this.eChartService.querySelfCorrectCountList(paramMap);
			break;
			
		default:
			break;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("max", max);
		resultMap.put("eDatas", echartDatas);

		return resultMap;
	}

	/**
	 * 根据类型获取名称
	 * 
	 * @param eType
	 * @return
	 */
	private String getLabelByEType(String eType) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("S1", "严重违法广告活动");
		map.put("C1", "一般违法广告活动");
		map.put("L1", "轻微违法广告活动");
		
		map.put("S2", "严重违法广告创意");
		map.put("C2", "一般违法广告创意");
		map.put("L2", "轻微违法广告创意");
		
		map.put("S3", "严重违法涉及媒体");
		map.put("C3", "一般违法涉及媒体");
		map.put("L3", "轻微违法涉及媒体");
		
		map.put("S4", "严重违法广告主");
		map.put("C4", "一般违法广告主");
		map.put("L4", "轻微违法广告主");
		
		map.put("S5", "严重违法广告活动占比");
		map.put("C5", "一般违法广告活动占比");
		map.put("L5", "轻微违法广告活动占比");
		
		map.put("S6", "总体已整改严重违法广告创意");
		map.put("C6", "总体已整改一般违法广告创意");
		map.put("L6", "总体已整改轻微违法广告创意");
		
		map.put("S7", "我方已整改严重违法广告创意");
		map.put("C7", "我方已整改一般违法广告创意");
		map.put("L7", "我方已整改轻微违法广告创意");
		
		return map.get(eType);
	}
	
	private static int getMaxNum(int num,int div) {
		BigDecimal bd = new BigDecimal(num);
		BigDecimal divide = bd.divide(new BigDecimal(div), 0, BigDecimal.ROUND_UP);
		if(num%div == 0) return num;
		else return div*divide.intValue();
	}
	
	public static void main(String[] args) {
		System.out.println(getMaxNum(0, 5));
	}
}
