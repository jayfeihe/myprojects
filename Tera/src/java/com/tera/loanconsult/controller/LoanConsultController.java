/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.loanconsult.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.loanconsult.model.LoanConsult;
import com.tera.loanconsult.service.LoanConsultService;

import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;

import com.tera.util.ObjectUtils;
import com.tera.util.JsonUtil;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.service.DataDictionaryService;

/**
 * 
 * <br>
 * <b>功能：</b>LoanConsultController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-30 14:50:36<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/loanConsult") 
public class LoanConsultController {

	/**
	 * 日志
	 */
	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(LoanConsultController.class);
	
	/**
	 * LoanConsultService
	 */
	@Autowired(required=false) //自动注入
	private LoanConsultService<LoanConsult> loanConsultService;
	@Autowired(required=false) //自动注入
	private DataDictionaryService<DataDictionary> dataDictionaryService;
	
	/**
	 * 跳转到借款咨询的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String loanConsultQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "loanconsult/loanconsultQuery";
	}

	/**
	 * 显示借款咨询的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String loanConsultList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(LoanConsult.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		String sessionOperator = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		queryMap.put("operator", sessionOperator); //Session 操作员
		queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		queryMap.put("state", 1); //有效的
		int rowsCount = this.loanConsultService.queryCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<LoanConsult> loanConsultList = this.loanConsultService.queryList(queryMap);
		pm.setData(loanConsultList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "loanconsult/loanconsultList";
	}

	/**
	 * 跳转到更新借款咨询的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String loanConsultUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanConsult bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.loanConsultService.queryByKey(id);
		}
		Map<String, Object> hashMap= null;
		//新增查询用途列表
		List<DataDictionary> loanusageList = dataDictionaryService.queryList(hashMap);
		
		map.put("loanusageList", loanusageList);
		map.put("bean", bean);
		String param = request.getParameter("paramVal");
		if(bean ==null ){ // 类型不等于null ex：新增
			if(param.equals("group") ){ // 新增机构页面
				log.info(thisMethodName+":end");
				return "loanconsult/loanconsultUpdateOrg";
			}else{
				log.info(thisMethodName+":end");
				return "loanconsult/loanconsultUpdatePer";
			}
		}else{
			if(bean.getType() == "02" ){ // 修改机构页面
				log.info(thisMethodName+":end");
				return "loanconsult/loanconsultUpdateOrg";
			}else{
				log.info(thisMethodName+":end");
				return "loanconsult/loanconsultUpdatePer";
			}
		}
		
	}

	/**
	 * 保存借款咨询数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void loanConsultSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			LoanConsult bean = (LoanConsult) RequestUtils.getRequestBean(LoanConsult.class, request);
			String loginid = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			Org org =  (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			bean.setCustomerManager(loginid); //获取当前登录用户存入到客户经理
			//如果存在
			if (bean.getId() != 0) {
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				bean.setOperator(loginid);
				this.loanConsultService.updateOnlyChanged(bean);
			} else { //如果不存在
				bean.setState("1"); //默认有效
				bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
				bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				bean.setOperator(loginid);
				bean.setOrgId(org.getOrgId());
				this.loanConsultService.add(bean);
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
	 * 删除借款咨询数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void loanConsultDelete(String id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginid = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org =  (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			LoanConsult bean = null;
			//如果存在
			if (null != id && !"".equals(id)) {
				bean  = this.loanConsultService.queryByKey(id);
			}
			bean.setState("0"); //失效
			bean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			bean.setOperator(loginid);
			this.loanConsultService.updateOnlyChanged(bean);
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
	 * 跳转到查看借款咨询的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String loanConsultRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		LoanConsult bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.loanConsultService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "loanconsult/loanconsultRead";
	}

}
