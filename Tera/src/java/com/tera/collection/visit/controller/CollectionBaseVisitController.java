/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.collection.visit.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.weaver.NewParentTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

 
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.service.ProcessService;
import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.phone.model.CollectionBase;
import com.tera.collection.phone.model.CollectionDistribution;
 
 
 
import com.tera.collection.visit.model.VisitQbean;
import com.tera.collection.visit.service.CollectionBaseVisitService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.credit.service.CreditSignService;
 

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;

import com.tera.util.NetUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;

/**
 * 
 * 催收信息基本表控制器
 * <b>功能：</b>CollectionBaseController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collection/visit")
public class CollectionBaseVisitController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollectionBaseVisitController.class);
	
	/**
	 * CollectionBaseService
	 */
	@Autowired(required=false) //自动注入
	private CollectionBaseVisitService collectionBaseVisitService;

	
	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	
	@Autowired(required=false) //自动注入
	CreditDecisionService creditDecisionService;
	@Autowired(required=false) //自动注入
	CreditSignService creditSignService;
	
	
	/**
	 * 跳转到催收信息基本表分单的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String collectionBaseQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("------------------------------");
		log.info(thisMethodName+":start");
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		map.put("orgId",org.getOrgId());
		log.info(thisMethodName+":end");
		return "collection/visit/visitQuery";
	}

	/**
	 * 显示催收信息基本表分单的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String collectionBaseList( HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(VisitQbean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if("".equals(queryMap.get("orgId").toString())||queryMap.get("orgId")==null){//如果查询条件为空 则为当前登录人的orgId 不为空则不用赋值
			
			queryMap.put("orgId",org.getOrgId());
			
		}
		int rowsCount = this.collectionBaseVisitService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		
		List<CollectionBase> collectionBaseList = this.collectionBaseVisitService.queryList(queryMap);
		pm.setData(collectionBaseList);
		map.put("pm", pm);
		map.put("loginOrgId",org.getOrgId());
		log.info(thisMethodName+":end");
		return "collection/visit/visitList";
	}
	/**
	 * 催收信息基本表分单
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param Sting[] appIds
	 * @param String  newsProcesser
	 * @throws Exception exception
	 */
	@RequestMapping("/update.do")
	public void assignVerifyUpdates(String[] appIds, String newProcesser, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
			Map<String,Object> map=new HashMap<String,Object>();
			collectionBaseVisitService.updateVisitCollectionMap(appIds,newProcesser,user,map);
		
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "系统异常！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	/**
	 * 跳转到档案详情页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/credit/read.do")
	public String creditQueryRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CreditApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.creditAppService.queryByKey(id);
			map.put("bean", bean);
		}
		log.info(thisMethodName+":end");
		return "collection/visit/creditQueryRead";
	}

}
