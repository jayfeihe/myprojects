/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.collection.reduce.controller;

import java.io.PrintWriter;
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

import com.tera.collection.reduce.constants.RemissionConstants;
import com.tera.collection.reduce.model.CollectionRemission;
import com.tera.collection.reduce.model.form.RemissionJsonMsg;
import com.tera.collection.reduce.model.form.RemissionQBean;
import com.tera.collection.reduce.service.CollectionRemissionService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 减免申请审批表控制器
 * <b>功能：</b>CollectionRemissionController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:48:41<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/collection/reduce/approval")
public class RemissionApprovalController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(RemissionApprovalController.class);
	
	/**
	 * CollectionRemissionService
	 */
	@Autowired(required=false) //自动注入
	private CollectionRemissionService collectionRemissionService;
	
	/**
	 * 跳转到减免审批的查询条件页面
	 * @param scope 标明是审批角色，还是
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String remissionApprovalQuery(String scope, HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.addAttribute("scope", scope);
		log.info(thisMethodName+":end");
		return "collection/reduce/approval/remissionApprovalQuery";
	}

	/**
	 * 显示减免审批查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String remissionApprovalList(String scope, HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		map.put("scope", scope);
		PageModel pm = new PageModel();
		RemissionQBean bean = (RemissionQBean) RequestUtils.getRequestBean(RemissionQBean.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		
		if (0 == loginUser.getIsAdmin()) {
			queryMap.put("loginId", loginUser.getLoginId());
		}
		
		// 减免审批
		if (RemissionConstants.REMISSION_SCOPE_APPROVAL.equals(scope)) {
			// 待处理
			if (RemissionConstants.REMISSION_PROCESS_WAIT.equals(bean
					.getProcessStatus())){
				queryMap.put("states",new String[] { RemissionConstants.REMISSION_STATE_APPROVAL });
				queryMap.put("bpmStates",new String[] { RemissionConstants.REMISSION_BPM_STATE_APPROVAL });
			}
			// 已处理
			if (RemissionConstants.REMISSION_PROCESS_OFF.equals(bean
					.getProcessStatus())) {
				queryMap.put("states", new String[] {
						RemissionConstants.REMISSION_STATE_PASS,
						RemissionConstants.REMISSION_STATE_N_PASS,
						RemissionConstants.REMISSION_STATE_APPROVAL_N_PASS });
				queryMap.put("remark", RemissionConstants.REMISSION_REMARK_APPROVAL);
			}
		}
		// 减免高级审批
		if (RemissionConstants.REMISSION_SCOPE_H_APPROVAL.equals(scope)) {
			// 待处理
			if (RemissionConstants.REMISSION_PROCESS_WAIT.equals(bean
					.getProcessStatus())) {
				queryMap.put("states",new String[] { RemissionConstants.REMISSION_STATE_H_APPROVAL });
				queryMap.put("bpmStates",new String[] { RemissionConstants.REMISSION_BPM_STATE_H_APPROVAL });
			}
			// 已处理
			if (RemissionConstants.REMISSION_PROCESS_OFF.equals(bean
					.getProcessStatus())) {
				queryMap.put("states", new String[] {
						RemissionConstants.REMISSION_STATE_PASS,
						RemissionConstants.REMISSION_STATE_N_PASS,
						RemissionConstants.REMISSION_STATE_APPROVAL_N_PASS });
				queryMap.put("remark", RemissionConstants.REMISSION_REMARK_H_APPROVAL);
			}
		}
		int rowsCount = this.collectionRemissionService.queryRemissionApprovalCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<RemissionQBean> collectionRemissionList = this.collectionRemissionService.queryRemissionApprovalList(queryMap);
		pm.setData(collectionRemissionList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "collection/reduce/approval/remissionApprovalList";
	}

	/**
	 * 跳转到更新减免审批的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String remissionApprovalUpdate(String id, String scope,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		RemissionQBean bean = null;
		if (StringUtils.isNotNullAndEmpty(id)) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("id", id);
			CollectionRemission collectionRemission = collectionRemissionService.queryByKey(id);
			bean = collectionRemissionService.queryRemissionApprovalList(queryMap).get(0);
			bean.setCollectionRemission(collectionRemission);
		}
		map.put("bean", bean);
		map.put("scope", scope);
		log.info(thisMethodName+":end");
		return "collection/reduce/approval/remissionApprovalUpdate";
	}

	/**
	 * 保存减免申请审批表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void remissionApprovalSave(String scope, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org loginOrg = (Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String orgId = loginOrg.getOrgId();
		
		try {
			CollectionRemission bean = (CollectionRemission) RequestUtils
					.getRequestBeanList("collectionRemission",CollectionRemission.class, request).get(0);
			RemissionJsonMsg jsonMsg = null;
			// 审批
			if (RemissionConstants.REMISSION_SCOPE_APPROVAL.equals(scope)) {
				jsonMsg = collectionRemissionService.submitWorkFlow(bean, loginId, orgId,
						RemissionConstants.REMISSION_OPERATE_TYPE_APPROVAL);
			}
			// 高级审批
			if (RemissionConstants.REMISSION_SCOPE_H_APPROVAL.equals(scope)) {
				jsonMsg = collectionRemissionService.submitWorkFlow(bean, loginId, orgId,
						RemissionConstants.REMISSION_OPERATE_TYPE_H_APPROVAL);
			}
			writer.print(JsonUtil.object2json(jsonMsg));
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
	 * 删除减免申请审批表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void collectionRemissionDelete(String id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.collectionRemissionService.delete(id);
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
	 * 跳转到查看减免申请审批表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String remissionApprovalRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CollectionRemission bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.collectionRemissionService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "collection/reduce/approval/remissionApprovalRead";
	}

}
