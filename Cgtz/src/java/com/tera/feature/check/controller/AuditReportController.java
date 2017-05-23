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
import com.tera.audit.common.constant.CommonConstant;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.feature.check.model.AuditReport;
import com.tera.feature.check.model.CheckOverDue;
import com.tera.feature.check.service.IAuditReportService;
import com.tera.feature.overdue.model.OverdueReport;
import com.tera.feature.overdue.service.IOverdueReportService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 逾期报告审核控制器
 * <b>功能：</b>OverdueReportController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/check/reportAudit")
public class AuditReportController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(AuditReportController.class);
	
	/**
	 * OverdueReportService
	 */
	@Autowired(required=false) //自动注入
	private IAuditReportService auditReportService;
	
	@Autowired(required=false) //自动注入
	private IContractService contractService;
	
	@Autowired(required=false) //自动注入
	private IOverdueReportService overdueReportService;
	
	@Autowired(required=false) //自动注入
	private RoleService roleService;
	/**
	 * 跳转到逾期报告的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String overdueReportQuery(String num,String loanId,String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId",loanId);
		map.put("contractId",contractId);
		log.info(thisMethodName+":end");
		return "check/reportAudit/reportAuditQuery";
	}

	/**
	 * 显示逾期报告的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String overdueReportList(String num,String loanId,String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		AuditReport bean = (AuditReport)RequestUtils.getRequestBean(AuditReport.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		PageList<AuditReport> assetList = this.auditReportService.queryPageList(queryMap);
		pm.setData(assetList);
		pm.initRowsCount(assetList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("loanId",loanId);
		map.put("contractId",contractId);
		log.info(thisMethodName+":end");
		return "check/reportAudit/reportAuditList";
	}

	/**
	 * 跳转到审核逾期报告的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String overdueReportUpdate(String id,String loanId,String contractId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		AuditReport bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.auditReportService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("loanId", loanId);
		map.put("contractId",contractId);
		//判断机构和角色
		User loginUser = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		Org loginOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		String loginId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);	
		if (0 == loginUser.getIsAdmin()) {
			Map<String, Object> roleMap = new HashMap<String,Object>();
			roleMap.put("loginId",loginId);
			roleMap.put("orgId", loginOrg.getOrgId());
			List<Role> loginRoles = this.roleService.getRoleByOrgLoginId(roleMap);
			if (loginRoles != null && loginRoles.size() > 0) {
				for (Role role : loginRoles) {
					// 是业务员自己看自己的
					if (CommonConstant.ROLE_CHECK_STAFF.equals(role.getName()) && "1".equals(role.getFlag())) {
						map.put("roleCheck",CommonConstant.ROLE_CHECK_STAFF);
					}
				}
			}
		}
		log.info(thisMethodName+":end");
		return "check/reportAudit/reportAudit";
	}

	/**
	 * 审核逾期报告操作
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void overdueReportSave(String id,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			AuditReport bean = (AuditReport) RequestUtils.getRequestBean(AuditReport.class, request);
			//补充参数
			//提交时间
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			//提交人
			String userId=(String)request.getSession().getAttribute("loginid");
		if(id!=null && !id.equals("")){					
			//如果存在
			if (Integer.parseInt(id) > 0) {
				//查找逾期报告
				OverdueReport report=(OverdueReport)(overdueReportService.queryByKey(Integer.parseInt(id)));
				//更改合同表中报告审核状态
				Contract contract=contractService.queryByContractId(bean.getContractId());
				if(StringUtils.isNotNullAndEmpty(bean.getAuditRemark())){
					report.setAuditTime(nowTime);
					report.setAuditUid(userId);
					report.setAuditRemark(bean.getAuditRemark());
					report.setAuditResult(bean.getAuditResult());
					contract.setCheckReportState(bean.getAuditResult());
					contract.setUpdateTime(nowTime);
				}
				if(StringUtils.isNotNullAndEmpty(bean.getOrgAuditRemark())){
					report.setOrgAuditTime(nowTime);
					report.setOrgAuditUid(userId);
					report.setOrgAuditRemark(bean.getOrgAuditRemark());
					report.setOrgAuditResult(bean.getOrgAuditResult());
					contract.setUpdateTime(nowTime);
				}			
				overdueReportService.update(report);
				contractService.update(contract);		
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));}
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
	 * 跳转到查看逾期报告的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String overdueReportRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		AuditReport bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.auditReportService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "check/reportAudit/reportAuditRead";
	}
	//打印页面
	@RequestMapping("/print.do")
	public String overdueReportPrint(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		AuditReport bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.auditReportService.printByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "check/reportAudit/reportAuditPrint";
	}

}
