/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.contract.controller;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.contract.model.Contract;
import com.tera.contract.model.form.ContractQueryBean;
import com.tera.contract.service.ContractService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.SysLog;
import com.tera.sys.model.User;
import com.tera.sys.service.SysLogService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;

/**
 * 
 * <br>
 * <b>功能：</b>ContractController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-20 14:25:57<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/contract")
public class ContractController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(ContractController.class);
	
	/**
	 * ContractService
	 */
	@Autowired(required=false) //自动注入
	private ContractService contractService;
	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	private CreditDecisionService creditDecisionService;
	@Autowired(required=false) //自动注入
	private SysLogService sysLogService;
	
	/*
	 * 跳转到合同表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String contractQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "contract/contractQuery";
	}

	/**
	 * 显示合同表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String contractList(ContractQueryBean queryBean, BindingResult bingding,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		
		Map<String, Object> queryMap = ObjectUtils.describe(queryBean);
		queryMap.put("contractStates", new String[]{"2"});
		queryMap.put("appStates", new String[]{"23"});
		
		int rowsCount = this.contractService.queryManageCount(queryMap);
		pm.init(request, rowsCount, null, queryBean);
		
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		
		List<Contract> contractList = this.contractService.queryManageList(queryMap);
		pm.setData(contractList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "contract/contractList";
	}

	/**
	 * 跳转到更新合同表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String contractUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Contract contract = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			contract  = this.contractService.queryByKey(id);
			
			Map<String, Object> qeryMap =new HashMap<String, Object>();
			qeryMap.put("appId", contract.getLoanAppId());
			
			List<CreditApp> appList=creditAppService.queryList(qeryMap);
			CreditApp creditApp=appList.get(0);

			qeryMap.put("type","0");
			qeryMap.put("state","1");
			List<CreditDecision> decisionList=creditDecisionService.queryList(qeryMap);
			CreditDecision decision=decisionList.get(0);
			
			map.put("creditApp", creditApp);
			map.put("decision", decision);
			map.put("contract", contract);
			
		}
		
		
		log.info(thisMethodName+":end");
		return "contract/contractUpdate";
	}

	/**
	 * 保存合同表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void contractSave(Contract fromBean, BindingResult bingding,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Contract isSameContract = contractService.queryByKey(fromBean.getId());
			
			if(isSameContract.getBankProvince().equals(fromBean.getBankProvince()) && 
					isSameContract.getBankCity().equals(fromBean.getBankCity()) && 
					isSameContract.getBankCode().equals(fromBean.getBankCode()) && 
					isSameContract.getBankBranch().equals(fromBean.getBankBranch()) && 
					isSameContract.getBankAccount().equals(fromBean.getBankAccount()) && 
					isSameContract.getBankMobile().equals(fromBean.getBankMobile())){
				//如果相同返回提示，如果不同进行修改
				writer.print(JsonUtil.object2json(new JsonMsg(true, "没有修改信息")));
			}else{
				//修改合同信息
				if (fromBean.getId() != 0) {
					fromBean.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					this.contractService.updateOnlyChanged(fromBean);
					
					User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
					Org org =(Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
					String ipAddress=(String) request.getSession().getAttribute(SysConstants.LOGIN_IP);
					sysLogService.addSysLog(new SysLog(ipAddress, user, org.getOrgName(), "修改合同："+fromBean.getContractNo()));
				}
				writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
			}
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
	 * 删除合同表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void contractDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.contractService.delete(id);
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
	 * 跳转到查看合同表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String contractRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");

		Contract bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.contractService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "contract/contractRead";
	}

}
