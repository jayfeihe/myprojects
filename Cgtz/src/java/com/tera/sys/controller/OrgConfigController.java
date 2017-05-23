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

import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.service.OrgService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * <br>
 * <b>功能：</b>OrgController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-29 13:00:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/sys/orgconfig") 
public class OrgConfigController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(OrgConfigController.class);
	
	/**
	 * OrgService
	 */
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	
	/**
	 * 跳转到机构信息表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String orgQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/org/orgConfigQuery";
	}

	/**
	 * 显示机构信息表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String orgList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		/*PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Org.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		int rowsCount = this.orgService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Org> orgList = this.orgService.queryList(queryMap);
		pm.setData(orgList);
		map.put("pm", pm);*/
		log.info(thisMethodName+":end");
		return "sys/org/orgConfigList";
	}

	/**
	 * 跳转到更新机构信息表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String orgUpdate(String orgId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org bean = null;
		//如果存在
		if (StringUtils.isNotNullAndEmpty(orgId)) {
			bean  = this.orgService.queryByOrgId(orgId);
			bean.setParentOrgId(orgId.substring(0,orgId.length()-2));
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sys/org/orgConfigUpdate";
	}

	/**
	 * 保存机构信息表数据
	 * @param request request
	 * @param map map
	 * @return string
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void orgSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Org bean = (Org) RequestUtils.getRequestBean(Org.class, request);
			this.orgService.updateOnlyChanged(bean);
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
	 * 显示数据权限下拉集合
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 
	 */
	@RequestMapping("/selectList.do")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Object bean = RequestUtils.getRequestBean(Org.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		List<Org> allOrgs = this.orgService.querySelectList(queryMap);
		List<Org> orgList = this.orgService.queryListTree(allOrgs);
		writer.print(JsonUtil.object2json(orgList));			
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
		return "sys/org/orgConfigList";
	}
}
