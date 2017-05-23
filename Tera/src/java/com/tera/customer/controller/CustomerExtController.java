/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.customer.controller;

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

import com.tera.customer.model.CustomerExt;
import com.tera.customer.service.CustomerExtService;

import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * <br>
 * <b>功能：</b>CustomerExtController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-29 18:35:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/customerExt") 
public class CustomerExtController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CustomerExtController.class);
	
	/**
	 * CustomerExtService
	 */
	@Autowired(required=false) //自动注入
	private CustomerExtService<CustomerExt> customerExtService;
	
	/**
	 * 跳转到财富客户扩展表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String customerExtQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "customer/customerQuery";
	}

	/**
	 * 显示财富客户扩展表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String customerExtList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(CustomerExt.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.customerExtService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CustomerExt> customerExtList = this.customerExtService.queryList(queryMap);
		pm.setData(customerExtList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "customer/customerList";
	}

	/**
	 * 跳转到更新财富客户扩展表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String customerExtUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		CustomerExt bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.customerExtService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "customer/customerUpdate";
	}

	/**
	 * 保存财富客户扩展表数据
	 * @param request request
	 * @param map map
	 * @return string
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public String customerExtSave(HttpServletRequest request, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		CustomerExt bean = (CustomerExt) RequestUtils.getRequestBean(CustomerExt.class, request);
		//如果存在
		if (bean.getCustomerId() != 0) {
			this.customerExtService.update(bean);
		} else { //如果不存在
			this.customerExtService.add(bean);
		}
		log.info(thisMethodName+":end");
		return "customer/customerQuery";
	}

	/**
	 * 删除财富客户扩展表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void customerExtDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.customerExtService.delete(id);
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
	 * 跳转到查看财富客户扩展表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String customerExtRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		CustomerExt bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.customerExtService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "customer/customerRead";
	}

}
