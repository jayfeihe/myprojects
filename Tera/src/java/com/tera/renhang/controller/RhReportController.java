/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.renhang.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.renhang.model.RhReport;
import com.tera.renhang.service.RhReportService;

import com.tera.sys.model.PageModel;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;

/**
 * 
 * 信用贷款人行报告控制器
 * <b>功能：</b>RhReportController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:05:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/renhang/report")
public class RhReportController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(RhReportController.class);
	
	/**
	 * RhReportService
	 */
	@Autowired(required=false) //自动注入
	private RhReportService rhReportService;
	
	/**
	 * 跳转到信用贷款人行报告的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String rhReportQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "renhang/report/rhReportQuery";
	}

	/**
	 * 显示信用贷款人行报告的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String rhReportList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(RhReport.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.rhReportService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<RhReport> rhReportList = this.rhReportService.queryList(queryMap);
		pm.setData(rhReportList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "renhang/report/rhReportList";
	}

	/**
	 * 跳转到更新信用贷款人行报告的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String rhReportUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		RhReport bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.rhReportService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "renhang/report/rhReportUpdate";
	}

	/**
	 * 保存信用贷款人行报告数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void rhReportSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			RhReport bean = (RhReport) RequestUtils.getRequestBean(RhReport.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.rhReportService.updateOnlyChanged(bean);
			} else { //如果不存在
				this.rhReportService.add(bean);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 删除信用贷款人行报告数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void rhReportDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.rhReportService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看信用贷款人行报告的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String rhReportRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		RhReport bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.rhReportService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "renhang/report/rhReportRead";
	}

}
