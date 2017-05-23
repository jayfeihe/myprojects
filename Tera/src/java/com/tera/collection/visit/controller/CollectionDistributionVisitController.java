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


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
 

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
 
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.collection.constant.CollectionConstant;
import com.tera.collection.phone.model.CollectionDistributionCount;



import com.tera.collection.visit.model.VisitDistribution;
import com.tera.collection.visit.model.VisitQbean;
 
import com.tera.collection.visit.service.CollectionDistributionVisitServer; 
import com.tera.sys.service.RoleService;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
 
import com.tera.sys.model.User;
import com.tera.util.ObjectUtils;
import com.tera.util.DateUtils;
 

import com.tera.util.RequestUtils;

 

/**
 * 
 * 催收信息基本表控制器
 * <b>功能：</b>CollectionBaseController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:36:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collection/visit/detail")
public class CollectionDistributionVisitController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CollectionDistributionVisitController.class);
	
	
	
	/**
	 * CollectionDistributionVisitServer
	 */
	@Autowired(required=false) //自动注入
	private CollectionDistributionVisitServer collectionDetailsVisitService;
	
	
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	/**
	 * 跳转到催收信息基本表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String collectionBaseQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "collection/visit/visitDetailsQuery";
	}

	/**
	 * 显示催收信息基本表的查询列表
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
		Object bean =  RequestUtils.getRequestBean(VisitQbean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		for(String key:queryMap.keySet())
		{
			System.out.println("key===="+key+"============value========="+queryMap.get(key));
		}
		
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Map<String, Object> queryMap1=new HashMap<String,Object>();
		queryMap1.put("loginId",user.getLoginId());
		queryMap1.put("orgId",org.getOrgId());
		List<Role> roles=roleService.getRoleByOrgLoginId(queryMap1);
		
		 
		
		queryMap.put("loginId",user.getLoginId());
		if("".equals(queryMap.get("orgId").toString())||queryMap.get("orgId")==null){//如果查询条件为空 则为当前登录人的orgId 不为空则不用赋值
			queryMap.put("orgId",org.getOrgId());
		}
		if(1 == user.getIsAdmin())//管理员看全部
		{
			queryMap.put("roleName","");
		}
		else if(roles.get(0).getName().equals(CollectionConstant.COLLECTION_ROLE_VISIT_LDZG))//主管看全部 
		{
			queryMap.put("roleName","");
		}
		else if(roles.get(0).getName().equals(CollectionConstant.COLLECTION_ROLE_VISIT_LDZY))//专员查看自己名下催收单信息
		{
			queryMap.put("roleName",roles.get(0).getName());
		}
		else
		{
			pm.init(request, 0, null, bean);
			pm.setData(new ArrayList());
			map.put("pm", pm);
			log.info(thisMethodName+":end");
			return "collection/visit/visitDetailsList";
		}
		/*System.out.println("==========="+queryMap.get("orgId")+"========");
		System.out.println("==========="+queryMap.get("loginId")+"========");
		System.out.println("==========="+queryMap.get("roleName")+"========");*/
		int rowsCount = this.collectionDetailsVisitService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<VisitDistribution> visitDistributionList = this.collectionDetailsVisitService.queryList(queryMap);
		pm.setData(visitDistributionList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "collection/visit/visitDetailsList";
	}
	
	/**
	 * 跳转到催收分单统计页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/count/query.do")
	public String distributionCountQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		map.put("orgId",org.getOrgId());
		log.info(thisMethodName+":end");
		return "collection/visit/visitCountQuery";
	}
	/**
	 * 显示催收信息分单统计的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/count/list.do")
	public String distributionCountList( HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean =  RequestUtils.getRequestBean(VisitQbean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER); 
		queryMap.put("loginId",user.getLoginId());
		if("".equals(queryMap.get("orgId"))||queryMap.get("orgId")==null){
			queryMap.put("orgId",org.getOrgId());
		}
		if("".equals(queryMap.get("distributionDateMin"))||queryMap.get("distributionDateMin")==null){
			queryMap.put("distributionDateMin",DateUtils.formatDate(new Date()));
		}
		if("".equals(queryMap.get("distributionDateMax"))||queryMap.get("distributionDateMax")==null){
			queryMap.put("distributionDateMax",DateUtils.formatDate(new Date()));
		}
		int rowsCount = this.collectionDetailsVisitService.queryTaskNumCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CollectionDistributionCount> visitDistributionCountList = this.collectionDetailsVisitService.queryTaskNumList(queryMap);
		pm.setData(visitDistributionCountList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "collection/visit/visitCountList";
	}
	
}
