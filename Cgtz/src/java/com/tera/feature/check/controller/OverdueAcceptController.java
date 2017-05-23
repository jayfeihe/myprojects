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
import com.tera.feature.check.model.OverdueAccept;
import com.tera.feature.check.service.IOverdueAcceptService;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * 逾期受理登记表控制器
 * <b>功能：</b>OverdueAcceptController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 19:46:37<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/check/overdueAccept")
public class OverdueAcceptController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(OverdueAcceptController.class);
	
	/**
	 * OverdueAcceptService
	 */
	@Autowired(required=false) //自动注入
	private IOverdueAcceptService overdueAcceptService;
	
	@Autowired(required=false) //自动注入
	private IContractService contractService;
	/**
	 * 跳转到逾期受理登记表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String overdueAcceptQuery(String loanId,String contractId,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		map.put("loanId",loanId);
		map.put("contractId",contractId);
		log.info(thisMethodName+":end");
		return "check/overdueAccept/overdueAcceptQuery";
	}

	/**
	 * 显示逾期受理登记表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/list.do")
	public String overdueAcceptList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(OverdueAccept.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		pm.init(request, null, bean);
		queryMap.put("curPage", pm.getCurPage());
		queryMap.put("pageSize", pm.getPageSize());
		map.put("loanId",queryMap.get("loanId"));
		map.put("contractId",queryMap.get("contractId"));
		PageList<OverdueAccept> overdueAcceptList = this.overdueAcceptService.queryPageList(queryMap);
		pm.setData(overdueAcceptList);
		pm.initRowsCount(overdueAcceptList.getPaginator().getTotalCount());
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "check/overdueAccept/overdueAcceptList";
	}

	/**
	 * 跳转到更新逾期受理登记表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String overdueAcceptUpdate(String id,String loanId,String contractId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		OverdueAccept bean = null;
		Map<String,Object> dueMap=new HashMap<String, Object>();
		/*dueMap.put("contractId", contractId);
		dueMap.put("num", lateNum);*/
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.overdueAcceptService.queryByKey(id);
		}
		map.put("bean", bean);
		map.put("loanId",loanId);
		map.put("contractId", contractId);
		log.info(thisMethodName+":end");
		return "check/overdueAccept/overdueAcceptUpdate";
	}

	/**
	 * 保存逾期受理登记表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void overdueAcceptSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			OverdueAccept bean = (OverdueAccept) RequestUtils.getRequestBean(OverdueAccept.class, request);
			//添加受理人,受理时间
			Timestamp nowTime=new Timestamp(System.currentTimeMillis());
			String loginid=(String)request.getSession().getAttribute("loginid");
			bean.setCreateUid(loginid);
			bean.setCreateTime(nowTime);
			//如果存在,并且状态为可以修改
			if (bean.getId() != 0 ) {
				//找到对应的申请
				OverdueAccept accept=this.overdueAcceptService.queryByKey(bean.getId());
				accept.setAmt(bean.getAmt());
				accept.setAuditMem(bean.getAuditMem());
				accept.setBusAdv(bean.getBusAdv());
				accept.setBusName(bean.getBusName());
				accept.setCheckAdv(bean.getCheckAdv());
				accept.setContractId(bean.getContractId());
				accept.setDept(bean.getDept());
				accept.setDeptAuditAdv(bean.getDeptAuditAdv());
				accept.setDeptOwner(bean.getDeptOwner());
				accept.setKeepAdv(bean.getKeepAdv());
				accept.setLateDate(bean.getLateDate());
				accept.setLawName(bean.getLawName());
				accept.setLeaderAdv(bean.getLeaderAdv());
				accept.setLoanId(bean.getLoanId());
				accept.setLoanInfo(bean.getLoanInfo());
				accept.setNum(bean.getNum());
				accept.setProperty(bean.getProperty());
				accept.setProType(bean.getProType());
				accept.setRegDate(bean.getRegDate());
				accept.setReportSummary(bean.getReportSummary());
				accept.setRiskName(bean.getRiskName());
				accept.setState(bean.getState());
				accept.setTel(bean.getTel());
				accept.setUpdateTime(nowTime);
				accept.setUpdateUid(loginid);
				this.overdueAcceptService.update(accept);
			} else { //如果不存在
				//更改合同表，is_accept
				//查找合同表
				Contract contract=contractService.queryByContractId(bean.getContractId());
				contract.setIsAccept("1");
				contract.setUpdateTime(nowTime);
				contractService.update(contract);
				this.overdueAcceptService.add(bean);
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
	 * 删除逾期受理登记表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void overdueAcceptDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.overdueAcceptService.delete(id);
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
	 * 跳转到查看逾期受理登记表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String overdueAcceptRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		OverdueAccept bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.overdueAcceptService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "check/overdueAccept/overdueAcceptRead";
	}
    
	@RequestMapping("/print.do")
	public String overdueAcceptPrint(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		OverdueAccept bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.overdueAcceptService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "check/overdueAccept/overdueAcceptPrint";
	}
}
