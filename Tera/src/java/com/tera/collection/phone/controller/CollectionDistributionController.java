/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.collection.phone.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.tera.collection.phone.model.CollectionDistribution;
import com.tera.collection.phone.model.CollectionDistributionCount;
import com.tera.collection.phone.service.CollectionDistributionService;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;

import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;

/**
 * 
 * 催收分配表控制器
 * <b>功能：</b>CollectionDistributionController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:37:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collectionDis/phone")
public class CollectionDistributionController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollectionDistributionController.class);
	
	/**
	 * CollectionDistributionService
	 */
	@Autowired(required=false) //自动注入
	private CollectionDistributionService collectionDistributionService;
	
	/**
	 * 跳转到催收分配表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String collectionDistributionQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "collection/phone/collectionDistributionQuery";
	}

	/**
	 * 显示催收分配表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String collectionDistributionList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(CollectionDistribution.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.collectionDistributionService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CollectionDistribution> collectionDistributionList = this.collectionDistributionService.queryList(queryMap);
		pm.setData(collectionDistributionList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "collection/phone/collectionDistributionList";
	}

	/**
	 * 跳转到更新催收分配表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String collectionDistributionUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollectionDistribution bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.collectionDistributionService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "collection/phone/collectionDistributionUpdate";
	}

	/**
	 * 保存催收分配表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void collectionDistributionSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			CollectionDistribution bean = (CollectionDistribution) RequestUtils.getRequestBean(CollectionDistribution.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.collectionDistributionService.updateOnlyChanged(bean);
			} else { //如果不存在
				this.collectionDistributionService.add(bean);
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
	 * 删除催收分配表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void collectionDistributionDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.collectionDistributionService.delete(id);
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
	 * 跳转到查看催收分配表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String collectionDistributionRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollectionDistribution bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.collectionDistributionService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "collection/phone/collectionDistributionRead";
	}
	/**
	 * 电催统计页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 * autor wangyongliang 20120713
	 */
	@RequestMapping("/phonePartDetail.do")
	public String phonePartDetail(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
//		CollectionDistribution bean = null;
		// 如果存在
//		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "collection/phone/PhonePartDetail";
	}
	@RequestMapping("/phonePartDetailList.do")
	public String phonePartDetailList(String distributionMinDateStr,String distributionMaxDateStr,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean =(CollectionDistributionCount) RequestUtils.getRequestBean(CollectionDistributionCount.class, request);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		if(!"".equals(distributionMinDateStr)){
			Timestamp distributionMinDate=new Timestamp(format.parse(distributionMinDateStr).getTime());
			queryMap.put("distributionMinDate", distributionMinDate);
		}else{
			Timestamp distributionMinDate=new Timestamp(format.parse(format.format(new Date())).getTime());
			queryMap.put("distributionMinDate", distributionMinDate);
		}
		if(!"".equals(distributionMaxDateStr)){
			Timestamp distributionMaxDate=new Timestamp(format.parse(distributionMaxDateStr).getTime());
			queryMap.put("distributionMaxDate", distributionMaxDate);
		}else{
			Timestamp distributionMaxDate=new Timestamp(format.parse(format.format(new Date())).getTime());
			queryMap.put("distributionMaxDate", distributionMaxDate);
		}
		
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		queryMap.put("orgName", org.getOrgId());
		int rowsCount = this.collectionDistributionService.queryPartDetailCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List partDetailList=(List) this.collectionDistributionService.queryPartDetailList(queryMap);
		pm.setData(partDetailList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "collection/phone/PhonePartDetailList";
	}

}
