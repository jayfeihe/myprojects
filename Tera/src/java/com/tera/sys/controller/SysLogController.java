/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tera.sys.constant.ReportConstants;
import com.tera.sys.model.Menu;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.form.SysLogQBean;
import com.tera.sys.service.SysLogService;
import com.tera.sys.view.ExcelReportTable;
import com.tera.sys.view.ExcelView;
import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * @author wy
 *
 */
@Controller

public class SysLogController {
	/**
	 * userService
	 */
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 日志
	 */
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(SysLogController.class);
	/**
	 * 打开查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/sys/log/query.do")
	public String sysLogQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/sysLog/sysLogQuery";
	}
	/**
	 * 打开数据列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return user list
	 */
	@SuppressWarnings(value = { "unchecked" })
	@RequestMapping("/sys/log/list.do")
	public String sysLogList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		SysLogQBean sysLogQBean = (SysLogQBean) RequestUtils.getRequestBean(SysLogQBean.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(sysLogQBean);
		int rowsCount = sysLogService.getSysLogCount(beanMap);

		PageModel pm = new PageModel();
		pm.init(request, rowsCount, null, sysLogQBean);
		beanMap.put("rowS", pm.getRowS());
		beanMap.put("rowE", pm.getRowE());
		List<SysLog> sysLogs = this.sysLogService.querySysLog(beanMap);
		pm.setData(sysLogs);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "sys/sysLog/sysLogList";
	}

	/**
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return ModelAndView ModelAndView
	 */
	@SuppressWarnings(value = { "unchecked" })
	@RequestMapping("/sys/log/excel.do")
	public ModelAndView sysUserExcel(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		SysLogQBean sysLogQBean = (SysLogQBean) RequestUtils.getRequestBean(SysLogQBean.class, request);
		Map<String, Object> beanMap = null;
		beanMap = ObjectUtils.describe(sysLogQBean);
		List<SysLog> sysLogs = this.sysLogService.exportSysLog(beanMap);

		String title = "日志表";
		String[] head = new String[]{"登陆ID", "姓名", "IP", "事件", "时间"};

		Object[][] obj = new Object[sysLogs.size()][5];
		for (int i = 0; i < sysLogs.size(); i++) {
			SysLog sysLog = sysLogs.get(i);
			Object[] values = new Object[5];
			values[0] = null != sysLog.getLoginId() ? sysLog.getLoginId() : "";
			values[1] = null != sysLog.getName() ? sysLog.getName() : "";
			values[2] = null != sysLog.getIpAddress() ? sysLog.getIpAddress() : "";
			values[3] = null != sysLog.getEvent() ? sysLog.getEvent() : "";
			values[4] = null != sysLog.getHappendDate() ? DateUtils.toString(sysLog.getHappendDate(), DateUtils.DEFAULT_TIME_PATTERN) : "";
			obj[i] = values;
		}
		ExcelReportTable report = new ExcelReportTable(title, head, obj);
		map.addAttribute(ReportConstants.REPORT, report);
		log.info(thisMethodName+":end");
		return new ModelAndView(new ExcelView("data.xls"), map);
	}

	/**
	 * 添加缩进
	 * @param menus list
	 * @return list
	 */
	protected List<Menu> resetMenuName(List<Menu> menus) {
			for (Menu menu : menus) {
				menu.setName(StringUtils.generateRepeatString(menu.getMenuLevel(), "&nbsp;&nbsp;&nbsp;") + menu.getName());
			}
			return menus;
	}

}
