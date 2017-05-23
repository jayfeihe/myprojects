/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.feature.overdue.controller;

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

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.service.IContractService;
import com.tera.feature.overdue.model.OverdueReport;
import com.tera.feature.overdue.model.SalesMenCllt;
import com.tera.feature.overdue.service.IOverdueReportService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 逾期报告控制器
 * <b>功能：</b>OverdueReportController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/overdue/report")
public class OverdueReportController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(OverdueReportController.class);
	
	/**
	 * OverdueReportService
	 */
	@Autowired(required=false) //自动注入
	private IOverdueReportService overdueReportService;
	
	@Autowired(required=false) //自动注入
	private IContractService contractService;
	
	/**
	 * 跳转到逾期报告的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String overdueReportQuery(String loanId,String contractId,String lateNum,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId",loanId);
		map.put("contractId",contractId);
		map.put("lateNum", lateNum);
		log.info(thisMethodName+":end");
		return "overdue/report/overdueReportQuery";
	}

	/**
	 * 显示逾期报告的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String overdueReportList(String loanId,String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		OverdueReport bean = (OverdueReport)RequestUtils.getRequestBean(OverdueReport.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		map.put("lateNum",queryMap.get("num"));
		queryMap.remove("num");
		PageList<OverdueReport> assetList = this.overdueReportService.queryPageList(queryMap);
		pm.setData(assetList);
		pm.initRowsCount(assetList.getPaginator().getTotalCount());
		map.put("pm", pm);
		map.put("loanId",loanId);
		map.put("contractId",contractId);
		log.info(thisMethodName+":end");
		return "overdue/report/overdueReportList";
	}

	/**
	 * 跳转到更新逾期报告的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String overdueReportUpdate(String lateNum,String id,String loanId,String contractId, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		OverdueReport bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.overdueReportService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("loanId", loanId);
		map.put("contractId",contractId);
		map.put("lateNum",lateNum);
		log.info(thisMethodName+":end");
		return "overdue/report/overdueReportUpdate";
	}

	/**
	 * 保存逾期报告数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void overdueReportSave(String id,String loanId,String contractId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			OverdueReport bean = (OverdueReport) RequestUtils.getRequestBean(OverdueReport.class, request);
			//提交时间
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			//提交人
			String userId=(String)request.getSession().getAttribute(SysConstants.LOGIN_ID);
			bean.setSubmitTme(nowTime);
			bean.setSubmitUid(userId);
		  if(id!=null &&!id.equals("")){
			//如果存在,业务员修改逾期报告
				if (Integer.parseInt(id)> 0) {
					OverdueReport report=(OverdueReport)overdueReportService.queryByKey(Integer.parseInt(id));
					report.setSubmitTme(bean.getSubmitTme());
					report.setSubmitRemark(bean.getSubmitRemark());
					this.overdueReportService.update(report);
				}   
		  }else { //如果不存在
				bean.setLoanId(loanId);
				bean.setContractId(contractId);
				//更改合同表中的报告提交状态
				Contract contract=this.contractService.queryByContractId(bean.getContractId());
				contract.setCheckReportState("1");
				contract.setUpdateTime(nowTime);
				contractService.update(contract);
				this.overdueReportService.add(bean);
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
	 * 删除逾期报告数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void overdueReportDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.overdueReportService.delete(id);
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
		OverdueReport bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.overdueReportService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "overdue/report/overdueReportRead";
	}

}
