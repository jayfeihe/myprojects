/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.check.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
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

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.feature.check.model.CheckOverDue;
import com.tera.feature.check.model.SalesCheck;
import com.tera.feature.check.service.ISalesCheckService;
import com.tera.feature.overdue.model.SalesCheckLog;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.sys.model.UserExt;
import com.tera.sys.service.OrgService;
import com.tera.sys.service.RoleService;
import com.tera.sys.service.UserExtService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 业务员，稽查人员催收跟进记录表控制器
 * <b>功能：</b>SalesCheckLogController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 14:02:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/check/salesCheck")
public class SalesCheckController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(SalesCheckController.class);
	
	/**
	 * SalesCheckLogService
	 */
	@Autowired(required=false) //自动注入
	private ISalesCheckService salesCheckService;
	
	@Autowired(required=false) //自动注入
	private IContractService contractService;
	
	@Autowired(required=false) //自动注入
	private UserExtService userExtService;
	
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	
	@Autowired(required=false) //自动注入
	private OrgService orgService;
	
	/**
	 * 跳转到业务员，稽查人员催收跟进记录表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String salesCheckLogQuery(String loanId,String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId",loanId);
		map.put("contractId", contractId);
		log.info(thisMethodName+":end");
		return "check/salesCheck/salesCheckLogQuery";
	}

	/**
	 * 显示业务员，稽查人员催收跟进记录表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String salesCheckLogList(String loanId,String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(SalesCheck.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<SalesCheck> assetList = this.salesCheckService.queryPageList(queryMap);
		pm.setData(assetList);
		pm.initRowsCount(assetList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "check/salesCheck/salesCheckLogList";
	}

	/**
	 * 跳转到更新业务员，稽查人员催收跟进记录表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String salesCheckLogUpdate(String id,String loanId,String contractId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		SalesCheck bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.salesCheckService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("loanId",loanId);
		map.put("contractId",contractId);
		log.info(thisMethodName+":end");
		return "check/salesCheck/salesCheckLogUpdate";
	}

	/**
	 * 保存业务员，稽查人员催收跟进记录表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void salesCheckLogSave(String loanId,String contractId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//跟进人机构，所属角色
			Org org=(Org)request.getSession().getAttribute(SysConstants.LOGIN_ORG);
            User user=(User)request.getSession().getAttribute(SysConstants.LOGIN_USER);
            String loginid=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);
            String role=null;
            Map<String,Object> roleMap=new HashMap<String, Object>();
            roleMap.put("loginId",loginid);
            if(userExtService.queryList(roleMap).size()>0){
            	int roleId=userExtService.queryList(roleMap).get(0).getRoleId();
            	role=roleService.getRoleById(roleId).getName();
            }
			//TODO service操作 需要修改
			SalesCheck bean = (SalesCheck) RequestUtils.getRequestBean(SalesCheck.class, request);
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			bean.setCreateTime(nowTime);
			bean.setLoanId(loanId);
			bean.setContractId(contractId);
			bean.setFollowUid(loginid);
			bean.setOrgId(org.getOrgId());
			bean.setRole(role);
			//如果存在
			if (bean.getId() != 0) {
				this.salesCheckService.update(bean);
			} else { //如果不存在
				//更改合同表的check_state
				Contract contract=contractService.queryByContractId(contractId);
				contract.setCheckState(bean.getCheckState());
				contract.setUpdateTime(nowTime);
				contractService.update(contract);
				this.salesCheckService.add(bean);
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
	 * 跳转到查看业务员，稽查人员催收跟进记录表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String salesCheckLogRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		SalesCheck bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.salesCheckService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "check/salesCheck/salesCheckLogRead";
	}

}
