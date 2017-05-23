/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.channeltotal.controller;

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

import com.tera.channeltotal.model.ChannelTotal;
import com.tera.channeltotal.service.ChannelTotalService;

import com.tera.product.model.Product;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;

/**
 * 
 * 渠道汇总管理表控制器
 * <b>功能：</b>ChannelTotalController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-30 15:35:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/channeltotal")
public class ChannelTotalController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ChannelTotalController.class);
	
	/**
	 * ChannelTotalService
	 */
	@Autowired(required=false) //自动注入
	private ChannelTotalService channelTotalService;
	
	/**
	 * 跳转到渠道汇总管理表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String channelTotalQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "channeltotal/channelTotalQuery";
	}

	/**
	 * 显示渠道汇总管理表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String channelTotalList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(ChannelTotal.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.channelTotalService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<ChannelTotal> channelTotalList = this.channelTotalService.queryList(queryMap);
		pm.setData(channelTotalList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "channeltotal/channelTotalList";
	}

	/**
	 * 显示渠道下拉了列表信息
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/selectList.do")
	public void selectList(String state,HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Object bean = RequestUtils.getRequestBean(ChannelTotal.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.channelTotalService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", 0);
		queryMap.put("rowE", rowsCount);
		queryMap.put("state", state);
		List<ChannelTotal> channelTotalList = this.channelTotalService.queryList(queryMap);
		writer.print(JsonUtil.object2json(channelTotalList));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	/**
	 * 跳转到更新渠道汇总管理表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String channelTotalUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		ChannelTotal bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.channelTotalService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "channeltotal/channelTotalUpdate";
	}

	/**
	 * 保存渠道汇总管理表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void channelTotalSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			ChannelTotal bean = (ChannelTotal) RequestUtils.getRequestBean(ChannelTotal.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.channelTotalService.updateOnlyChanged(bean);
			} else { //如果不存在
				bean.setState("1");
				this.channelTotalService.add(bean);
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
	 * 删除渠道汇总管理表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void channelTotalDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.channelTotalService.delete(id);
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
	 * up or down挂起与解除
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/upordown.do")
	public String channelTotalUpordown(int id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			ChannelTotal channel = channelTotalService.queryByKey(id);
				if (channel.getId() != 0) {
				if("1".equals(channel.getState())){
					channel.setState("2");
				}else if("2".equals(channel.getState())){
					channel.setState("1");
				}
				this.channelTotalService.update(channel);
			}
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			throw e;
		}
		log.info(thisMethodName+":end");
		return "channeltotal/channelTotalQuery";
	}

	@RequestMapping("/listjason.do")
	public void dataDictionaryListJson(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		Object bean = RequestUtils.getRequestBean(ChannelTotal.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		List<ChannelTotal> channelList = this.channelTotalService.queryList(queryMap);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(channelList));
		writer.flush();
		writer.close();
	}
}
