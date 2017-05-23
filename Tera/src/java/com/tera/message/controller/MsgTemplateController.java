/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.message.controller;

import java.io.PrintWriter;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.message.model.MsgTemplate;
import com.tera.message.service.MsgTemplateService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.sys.service.DataDictionaryService;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 模板表控制器
 * <b>功能：</b>TemplateController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-30 11:58:07<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/message/template")
public class MsgTemplateController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(MsgTemplateController.class);
	
	/**
	 * TemplateService
	 */
	@Autowired(required=false) //自动注入
	private MsgTemplateService msgTemplateService;
	
	/**
	 * DataDictionaryService
	 */
	@Autowired(required=false) //自动注入
	private DataDictionaryService<DataDictionary> dataDictionaryService;
	
	/**
	 * 跳转到模板表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String templateQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "message/template/templateQuery";
	}

	/**
	 * 显示模板表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String templateList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(MsgTemplate.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		String remindTime = request.getParameter("remindTime");
		queryMap.put("remindTime", remindTime);
		int rowsCount = this.msgTemplateService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<MsgTemplate> templateList = this.msgTemplateService.queryList(queryMap);
		pm.setData(templateList);
		map.put("pm", pm);

		//字典名称
		queryMap.clear();
		queryMap.put("keyName", "validstate");
		List<DataDictionary> validstateList = this.dataDictionaryService.queryList(queryMap);
		map.put("validstateList", validstateList);
		queryMap.clear();
		queryMap.put("keyName", "templatetype");
		List<DataDictionary> templatetypeList = this.dataDictionaryService.queryList(queryMap);
		map.put("templatetypeList", templatetypeList);
		
		log.info(thisMethodName+":end");
		return "message/template/templateList";
	}

	/**
	 * 跳转到更新模板表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String templateUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		MsgTemplate bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.msgTemplateService.queryByKey(id);
		} else {
			bean = new MsgTemplate();
			bean.setRemindTime(new Timestamp(1970, 1, 1, 8, 0, 0, 0));
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "message/template/templateUpdate";
	}

	/**
	 * 保存模板表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void templateSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			//TODO service操作 需要修改
			MsgTemplate bean = (MsgTemplate) RequestUtils.getRequestBean(MsgTemplate.class, request);
			String remindTime = URLDecoder.decode(request.getParameter("remindTime"),"utf-8");
			String[] remindTimes = remindTime.split(":");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(remindTimes[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(remindTimes[1]));
			cal.set(Calendar.SECOND, Integer.parseInt(remindTimes[2]));
			bean.setRemindTime(new Timestamp(cal.getTimeInMillis()));
			//如果存在
			if (bean.getId() != 0) {
				bean.setUpdateUid(loginId);
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				this.msgTemplateService.updateOnlyChanged(bean);
			} else { //如果不存在
				bean.setCreateUid(loginId);
				bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
				this.msgTemplateService.add(bean);
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
	 * 删除模板表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void templateDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.msgTemplateService.delete(id);
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
	 * 跳转到查看模板表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String templateRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		MsgTemplate bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.msgTemplateService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "message/template/templateRead";
	}

}
