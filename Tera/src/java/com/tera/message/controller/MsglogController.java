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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.message.constant.MessageConstant;
import com.tera.message.model.Msglog;
import com.tera.message.model.MsgTemplate;
import com.tera.message.service.MsglogService;
import com.tera.message.service.MsgTemplateService;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.sys.service.DataDictionaryService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 短信日志表控制器
 * <b>功能：</b>MsglogController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-07-01 16:57:10<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/message/msglog")
public class MsglogController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(MsglogController.class);
	
	/**
	 * MsglogService
	 */
	@Autowired(required=false) //自动注入
	private MsglogService msglogService;
	@Autowired(required=false) //自动注入
	private MsgTemplateService templateService;
	@Autowired(required=false) //自动注入
	private DataDictionaryService dataDictionaryService;
	
	/**
	 * 跳转到短信日志表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String msglogQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "message/msglog/msglogQuery";
	}

	/**
	 * 显示短信日志表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String msglogList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Msglog.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.msglogService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Msglog> msglogList = this.msglogService.queryList(queryMap);
		pm.setData(msglogList);
		map.put("pm", pm);
		
		queryMap.clear();
		queryMap.put("keyName", "messagetype");
		List<DataDictionary> messagetypeList = dataDictionaryService.queryList(queryMap);
		map.put("messagetypeList", messagetypeList);
		log.info(thisMethodName+":end");
		return "message/msglog/msglogList";
	}

	/**
	 * 跳转到更新短信日志表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String msglogUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Msglog bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.msglogService.queryByKey(id);
		}
		msglogService.queryAndSendMessage(null);
		msglogService.renewMessageReceiveState();
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "message/msglog/msglogUpdate";
	}

	/**
	 * 保存短信日志表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void msglogSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			Msglog bean = (Msglog) RequestUtils.getRequestBean(Msglog.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.msglogService.updateOnlyChanged(bean);
			} else { //如果不存在
				this.msglogService.add(bean);
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
	 * 删除短信日志表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void msglogDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.msglogService.delete(id);
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
	 * 跳转到查看短信日志表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String msglogRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Msglog bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.msglogService.queryByKey(id);
			if (MessageConstant.SENDSTATE_WAIT.equals(bean.getSendState())) {
				bean.setSendState("待发送");
			} else if (MessageConstant.SENDSTATE_COMPLETE.equals(bean.getSendState())) {
				bean.setSendState("已发送");
			}
			if (MessageConstant.RECEIVESTATE_DELIVRD.equals(bean.getReceiveState())) {
				bean.setReceiveState("成功");
			} else if (MessageConstant.RECEIVESTATE_UNDELIV.equals(bean.getReceiveState())) {
				bean.setReceiveState("失败");
			}
			MsgTemplate template = templateService.queryByKey(bean.getTemplateId());
			bean.setTemplateName(template.getName());
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "message/msglog/msglogRead";
	}
}
