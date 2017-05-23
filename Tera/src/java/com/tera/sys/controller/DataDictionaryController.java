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

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.sys.service.DataDictionaryService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * <br>
 * <b>功能：</b>DataDictionaryController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 16:38:18<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/sys/datadictionary")
public class DataDictionaryController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(DataDictionaryController.class);
	
	/**
	 * DataDictionaryService
	 */
	@Autowired(required=false) //自动注入
	private DataDictionaryService dataDictionaryService;
	
	
	/**
	 * 跳转到数据字典的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String dataDictionaryQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/datadictionary/dataDictionaryQuery";
	}

	/**
	 * 显示数据字典的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String dataDictionaryList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(DataDictionary.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.dataDictionaryService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<DataDictionary> dataDictionaryList = this.dataDictionaryService.queryList(queryMap);
		pm.setData(dataDictionaryList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "sys/datadictionary/dataDictionaryList";
	}

	/**
	 * 通过keyName和parentKeyProp查询数据字典
	 * @param keyName 字典名称
	 * @param parentKeyProp 字典父属性
	 * @param request
	 * @param map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listjason.do")
	public void dataDictionaryListJson(String keyName, String parentKeyProp,
			HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info("keyName:"+keyName);
		log.info("parentKeyProp:"+parentKeyProp);
		List<DataDictionary> list = null;
		//字典名称
		/*Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("keyName", keyName);
		queryMap.put("parentKeyProp", parentKeyProp);
		list = this.dataDictionaryService.queryList(queryMap);*/
		Map<String, List<DataDictionary>> dictMap = (Map<String, List<DataDictionary>>) request
				.getSession().getServletContext().getAttribute("DATADICTS");
		
		if (StringUtils.isNullOrEmpty(parentKeyProp)) {
			list = dictMap.get(keyName.toLowerCase());
		} else {
			list = dictMap.get((keyName+","+parentKeyProp).toLowerCase());
		}
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(list));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	/**
	 * 跳转到更新数据字典的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String dataDictionaryUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		DataDictionary bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.dataDictionaryService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sys/datadictionary/dataDictionaryUpdate";
	}

	/**
	 * 保存数据字典数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void dataDictionarySave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			DataDictionary bean = (DataDictionary) RequestUtils.getRequestBean(DataDictionary.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.dataDictionaryService.updateOnlyChanged(bean);
			} else { //如果不存在
				this.dataDictionaryService.add(bean);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
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
	 * 删除数据字典数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void dataDictionaryDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.dataDictionaryService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
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
	 * 跳转到查看数据字典的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String dataDictionaryRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		DataDictionary bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.dataDictionaryService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sys/datadictionary/dataDictionaryRead";
	}

}
