/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.credit.controller;

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

import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.model.form.CreditQBean;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.credit.service.CreditLendingService;
import com.tera.credit.service.CreditReviewService;
import com.tera.payment.model.Payment;
import com.tera.payment.service.PaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.ResultObj;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;

/**
 * 
 * 信用贷款放款控制器
 * <b>功能：</b>CreditAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/credit/lending")
public class CreditLendingController  extends BaseController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CreditLendingController.class);
	
	/**
	 * CreditAppService
	 */
	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	CreditReviewService creditReviewService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //决策表服务
	CreditDecisionService creditDecisionService;

	@Autowired(required=false)
    private ContractService contractService;
	
	@Autowired(required=false)
    private CreditLendingService creditLendingService;

	@Autowired(required=false)
	ProductService<Product> productService;

	@Autowired(required=false)
	PaymentService<Payment> paymentService;
	
	/**
	 * 跳转到信用贷款申请表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String creditSignQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "credit/lending/lendingQuery";
	}

	/**
	 * 显示信用贷款申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String creditSignList(CreditQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		
		queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		queryMap.put("nonStates", new String[]{"0"}); //状态
		
		
		
		if("waitTask".equals(qBean.getStateTask())){
//			queryMap.put("operator", loginId); //Session 操作员
			queryMap.put("processer", loginId); //状态
			if("".equals(queryMap.get("processer"))||user.getIsAdmin()==1){
				queryMap.remove("processer");
			}
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_LENDING});//查询录入流程定义为放款的
			queryMap.put("states", new String[]{"19"});  
		}else if("inTask".equals(qBean.getStateTask())){
			// 进行中  申请的状态 为20 就是处理中
			queryMap.put("processer", loginId); //状态
			if("".equals(queryMap.get("processer"))||user.getIsAdmin()==1){
				queryMap.remove("processer");
			}
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_LENDING});//查询录入流程定义为放款的
			queryMap.put("states", new String[]{"20"});  
		}else if("offTask".equals(qBean.getStateTask())){
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_END_APP});
		}
		int rowsCount = this.creditAppService.queryBpmAppAndContractCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CreditApp> creditAppList = this.creditAppService.queryBpmAppAndContractList(queryMap);
		pm.setData(creditAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "credit/lending/lendingList";
	}

	/**
	 * 确认放款、退回   执行操作
	 * @param buttonType
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void creditAppSave(String buttonType,String id, String msg, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);

		CreditApp bean  = this.creditAppService.queryByKey(id);
		Map<String, Object> fmap=new HashMap<String, Object>();
		fmap.put("loanAppId", bean.getAppId());
		fmap.put("contractClass", "03");		//信用借款合同
		fmap.put("contractType", "01");			//线上合同
		fmap.put("state", "1");
		Contract contract=contractService.queryList(fmap).get(0);
		
		fmap=new HashMap<String, Object>();
		fmap.put("appId", bean.getAppId());
		fmap.put("state", "1");
		fmap.put("type", "0");
		//最终决策
		CreditDecision creditDecision = creditDecisionService.queryList(fmap).get(0);
		
		try {
			if("pass".equals(buttonType)){
				
				Map<String, Object> paMap=new HashMap<String, Object>();
				paMap.put("appId",bean.getAppId());
				paMap.put("state", "1");
				paMap.put("type", "0");
				List<CreditDecision> creditDecisionList = creditDecisionService.queryList(paMap);
				CreditDecision decision=creditDecisionList.get(0);
				Product pro = productService.queryByName(decision.getProduct());
				
				//调用放款服务
				ResultObj rtObj=creditLendingService.confirmLending(bean, contract, creditDecision, loginId, org.getOrgId());
				writer.print(JsonUtil.object2json(rtObj));
				
				/*if(pro.getName().indexOf("JM")>0){//特殊流程  JM 合作
//					creditLendingService.jimuBoxLending(bean.getAppId());
//					writer.print(JsonUtil.object2json(new JsonMsg(false, "积木盒子工程师正在玩命开发中。。。")));
				}else{
					creditLendingService.confirmLending(bean, contract, creditDecision, loginId, org.getOrgId());
					writer.print(JsonUtil.object2json(new JsonMsg(true, "确认放款操作成功。")));
				}	*/			
			}else{
				creditLendingService.backReview(bean,loginId, msg);
//				writer.print(JsonUtil.object2json(new JsonMsg(true, "退回复核操作成功。")));
				writer.print(JsonUtil.object2json(new ResultObj("退回复核操作成功。", null, true)));
			}
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
//			writer.print(JsonUtil.object2json(new JsonMsg(false, "操作失败，程序异常。")));
			writer.print(JsonUtil.object2json(new ResultObj("操作失败，程序异常。", null, false)));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}

	
	/**
	 * 积木盒子 确认放款，出错时  
	 * @param buttonType
	 * @param id
	 * @param msg
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/jmErrorPass.do")
	public void creditAppJMErrorPass(String id,String jmId,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		if(user.getIsAdmin()==1){
			String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			CreditApp bean  = this.creditAppService.queryByKey(id);
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("loanAppId", bean.getAppId());
			fmap.put("contractClass", "03");		//信用借款合同
			fmap.put("contractType", "01");			//线上合同
			fmap.put("state", "1");
			Contract contract=contractService.queryList(fmap).get(0);
			fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "0");
			//最终决策
			CreditDecision creditDecision = creditDecisionService.queryList(fmap).get(0);
			Map<String, Object> paMap=new HashMap<String, Object>();
			paMap.put("appId",bean.getAppId());
			paMap.put("state", "1");
			paMap.put("type", "0");
			List<CreditDecision> creditDecisionList = creditDecisionService.queryList(paMap);
			CreditDecision decision=creditDecisionList.get(0);
			Product pro = productService.queryByName(decision.getProduct());
//			if(pro.getName().indexOf("JM")>0){//特殊流程  JM 合作
			if("JM".equals(pro.getBelongChannel())){//特殊流程  JM 合作
				creditLendingService.jMErrorconfirmLending(bean, contract, creditDecision, loginId, org.getOrgId(),jmId);
				writer.print(JsonUtil.object2json("操作成功。"));
			}else{
				writer.print("不是JM产品。");
			}
		}else{
			writer.print("没有访问权限。");
		}
		
	}
	
	/**
	 *  积木盒子 手动确认 放款成功 
	 * @param contractNo
	 * @param lendDate
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/jmLendOk.do")
	public void creditAppJMLendOk(String contractNo,String lendDate,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		if(user.getIsAdmin()==1){
			String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
			Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
			
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("contractNo", contractNo);
			Contract contract=contractService.queryList(fmap).get(0);
			
			Map<String, Object> paMap=new HashMap<String, Object>();
			paMap.put("appId",contract.getLoanAppId());
			paMap.put("state", "1");
			paMap.put("type", "0");
			List<CreditDecision> creditDecisionList = creditDecisionService.queryList(paMap);
			CreditDecision decision=creditDecisionList.get(0);
			
//			if(contract.getLoanProduct().indexOf("JM")>0){//特殊流程  JM 合作
			if("JM".equals(contract.getChannelType())){//特殊流程  JM 合作
				creditLendingService.manualJMLendOk(contract, decision, DateUtils.getDate(lendDate), org.getOrgId(), loginId);
				writer.print(JsonUtil.object2json("操作成功。"));
			}else{
				writer.print("不是JM产品。");
			}
		}else{
			writer.print("没有访问权限。");
		}
	}
	/**
	 * 积木盒子 放款成功  成功后 只更新 还款时间
	 * @param contractNo
	 * @param lendDate
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/jmLendUpdate.do")
	public void creditAppJMLendUpdate(String contractNo,String lendDate,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		User user = (User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		if(user.getIsAdmin()==1){
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("contractNo", contractNo);
			Contract contract=contractService.queryList(fmap).get(0);
			
//			if(contract.getLoanProduct().indexOf("JM")>0){//特殊流程  JM 合作
			if("JM".equals(contract.getChannelType())){//特殊流程  JM 合作
				contract.setChannelTime(new Timestamp(DateUtils.getDate(lendDate).getTime()));
				contract.setState("2");
		    	contract.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		    	contractService.update(contract);
				//更新 还款计划 与 支付信息状态
				paymentService.paymentSuccessUpdate(contract.getContractNo(), DateUtils.getDate(lendDate));
				writer.print(JsonUtil.object2json("操作成功。"));
			}else{
				writer.print("不是JM产品。");
			}
		}else{
			writer.print("没有访问权限。");
		}
	}
	
}
