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
import com.tera.sys.model.User;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.UserService;
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
@RequestMapping("/sys/org") 
public class OrgController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(OrgController.class);
	
	/**
	 * OrgService
	 */
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	@Autowired
	private UserService userService;
	
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
		return "sys/org/orgQuery";
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
		return "sys/org/orgList";
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
		return "sys/org/orgUpdate";
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
			
			this.orgService.saveOrUpdate(bean);
			
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
	 * 删除机构信息表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void orgDelete(String orgId, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			orgService.deleteSelfAndChlidren(orgId);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "删除失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 跳转到查看机构信息表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String orgRead(int id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Org bean = null;
		// 如果存在
		if (id!=0) {
			bean = this.orgService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "sys/org/orgRead";
	}
	
	/**
	 * 根据 loginid 查询 用户所属机构列表
	 * @param loginid
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/userOrg.do")
	public void userOrgRead(String loginid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		List<Org> orgs=new ArrayList<Org>();
		// 如果存在
		if (loginid!=null&&!loginid.equals("")) {
			User bean = this.userService.getUser(loginid);
			if(bean!=null){
				if(bean.getIsAdmin()==1){
					orgs=this.orgService.queryList(null);
				}else{
					orgs=this.orgService.getOrgByLoginId(loginid);
				}
			}
		}
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(JsonUtil.object2json(orgs));
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
		return "sys/org/orgList";
	}
}
