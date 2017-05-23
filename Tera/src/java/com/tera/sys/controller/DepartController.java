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
import java.util.ArrayList;
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

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.Depart;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.form.DepartTreeBean;
import com.tera.sys.service.DepartService;
import com.tera.sys.service.OrgService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 组织管理控制器
 * <b>功能：</b>DepartController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-10-22 18:05:02<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/sys/depart")
public class DepartController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(DepartController.class);
	
	/**
	 * DepartService
	 */
	@Autowired(required=false) //自动注入
	private DepartService departService;
	
	@Autowired(required=false) //自动注入
	private OrgService orgService;

	/**
	 * 跳转到组织管理的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String departQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "sys/depart/departQuery";
	}
	
	/**
	 * 显示组织管理的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String toList(Depart depart, String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "sys/depart/departList";
	}

	/**
	 * 显示组织管理的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/listData.do")
	public void departList(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Object bean = RequestUtils.getRequestBean(Depart.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
//		Map<String, Object> queryMap = new HashMap<String, Object>();
//		queryMap.put("departCode", depart.getDepartCode());
//		queryMap.put("departName", depart.getDepartName());
//		queryMap.put("level", depart.getLevel());
//		queryMap.put("parentId", depart.getParentId());
		queryMap.put("state", "1");
		if(null != id && !"".equals(id))
			queryMap.put("noId", Integer.parseInt(id));
		List<Depart> departList = this.departService.queryListByQuery(queryMap, id);
//		System.out.println(JsonUtil.object2json(departList));
		writer.print(JsonUtil.object2json(departList));			
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 显示组织管理的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/listDataUseForSales.do")
	public void listDataUseForSales(Depart depart, String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Map<String, Object> queryMap = ObjectUtils.describe(depart);
		if(null != id && !"".equals(id))
			queryMap.put("noId", Integer.parseInt(id));
		List<Depart> departList = this.departService.queryListByOrgId(queryMap, id);
		writer.print(JsonUtil.object2json(departList));			
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/listDataByLevel.do")
	public void listDataByLevel(String level, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("level", level);
		List<DepartTreeBean> departList = this.departService.queryTreeByLevel(queryMap);
		writer.print(JsonUtil.object2json(departList));
//		FileUtils.writeStringToFile(new File("e://json.txt"), JsonUtil.object2json(departList));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	@RequestMapping("/listDataByLevelAndOrgId.do")
	public void listDataByLevelAndOrgId(String level, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("level", level);
		queryMap.put("orgId", org.getOrgId());
		List<DepartTreeBean> departList = this.departService.queryTreeByLevelAndOrgId(queryMap);
		writer.print(JsonUtil.object2json(departList));
//		FileUtils.writeStringToFile(new File("e://json.txt"), JsonUtil.object2json(departList));
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到更新组织管理的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String departUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Depart bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.departService.queryByKey(id);
			Org org = orgService.queryByOrgId(bean.getOrgId());
			bean.setOrgId(org.getId() + "");
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sys/depart/departUpdate";
	}

	/**
	 * 保存组织管理数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void departSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			//TODO service操作 需要修改
			Depart bean = (Depart) RequestUtils.getRequestBean(Depart.class, request);
			departService.save(bean, loginId);
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
	 * 删除组织管理数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void departDelete(String id, HttpServletResponse response, HttpServletRequest request) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Depart bean = this.departService.queryByKey(id);
			Map<String, Object> queryMap = ObjectUtils.describe(bean);
			this.departService.deleteOneselfAndChildren(queryMap);
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
	 * 验证部门名称是否重复
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/checkRepeatDepartName.do")
	public void checkRepeatDepartName(String id, String departName, String parentId, HttpServletResponse response, HttpServletRequest request) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("departName", departName);
			queryMap.put("state", "1");
			int count = 0;
			Depart bean = this.departService.queryByKey(parentId);
			queryMap.put("parentId", bean.getId());
			queryMap.put("level", Integer.parseInt(bean.getLevel()) + 1 + "");
			if("0".equals(id)){//新增时
				count = departService.queryCount(queryMap);
			}else{//修改时
				queryMap.put("noId", id);
				count = departService.queryCount(queryMap);				
			}
			if(count == 0)
				writer.print(JsonUtil.object2json(new JsonMsg(true, "没有重复！")));				
			else
				writer.print(JsonUtil.object2json(new JsonMsg(false, "您输入的组织名称在同一级组织里有重名, 请重新填写！")));
				
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看组织管理的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String departRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Depart bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.departService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sys/depart/departRead";
	}

	@RequestMapping("/getSaleTeam.do")
	public void getSaleTeam(String orgId,HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("orgId", orgId);
		queryMap.put("level", "4");
		List<Depart> saleTeamList = new ArrayList<Depart>();
		try {
			List<Depart> list = departService.queryList(queryMap);
			if (list != null && list.size() > 0) {
				queryMap.put("parentId", list.get(0).getId());
				queryMap.put("level", "5");
				saleTeamList = departService.queryList(queryMap);
			}
			writer.print(JsonUtil.object2json(saleTeamList));
		} catch (Exception e) {
			e.printStackTrace();
			writer.print(JsonUtil.object2json(saleTeamList));
		} finally {
			writer.flush();
			writer.close();
		}
		log.info(thisMethodName+":end");
	}
}
