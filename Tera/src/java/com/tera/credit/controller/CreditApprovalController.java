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

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.service.ProcessService;
import com.tera.cooperation.dinxuan.model.CeilingAmountQBean;
import com.tera.cooperation.dinxuan.service.CeilingAmountService;
import com.tera.credit.constant.BusinessConstants;
import com.tera.credit.constant.Constants;
import com.tera.credit.model.CreditAntifraud;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.model.form.ApprovalFBean;
import com.tera.credit.model.form.CreditQBean;
import com.tera.credit.service.CreditAntifraudService;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditContactService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.credit.service.CreditExtService;
import com.tera.credit.service.CreditSignService;
import com.tera.img.service.ImgService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.User;
import com.tera.sys.service.RoleService;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.StringUtils;

/**
 * 
 * 信用贷款审批 控制器
 * <b>功能：</b>CreditAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/credit/approval")
public class CreditApprovalController  extends BaseController{

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CreditApprovalController.class);
	
	/**
	 * CreditAppService
	 */
	@Autowired(required=false) //自动注入
	private CreditAppService creditAppService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	CreditContactService creditContactService;
	@Autowired(required=false) //自动注入
	CreditExtService creditExtService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	CreditDecisionService creditDecisionService;
	@Autowired(required=false) //自动注入
	ProductService productService;
	@Autowired(required=false) //自动注入
	CreditSignService creditSignService;
	@Autowired(required=false) //自动注入
	private CeilingAmountService ceilingAmountService;
	@Autowired(required=false) //自动注入
	private CreditAntifraudService creditAntifraudService;
	
	/**
	 * 跳转到审批分单的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String assignApprovalQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "credit/approval/creditApprovalQuery";
	}

	/**
	 * 显示查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String assignApprovalList(CreditQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		User user=(User) request.getSession().getAttribute(SysConstants.LOGIN_USER);
		
		if(queryMap.get("orgId")==null || "".equals(queryMap.get("orgId"))){
			queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		}
		if(user.getIsAdmin()!=1){
			queryMap.put("processer", loginId); // 审核人
		}
		if(StringUtils.isNullOrEmpty((String) queryMap.get("processer"))){
			queryMap.remove("processer");
		}
		if("waitTask".equals(qBean.getStateTask())){
			queryMap.put("nonStates", new String[]{"0"}); //状态
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_APPROVAL});//查询录入流程定义为审核的
		}else if("inTask".equals(qBean.getStateTask())){
			// 进行中
			if(user.getIsAdmin()!=1){
				queryMap.put("bpmLogOperator", loginId); //流程日志 实际处理人
			}
			queryMap.put("bpmLogStates", Constants.PROCESS_STATE_APPROVAL);//日志状态
			queryMap.put("nonBpmStates", new String[]{Constants.PROCESS_END_APP,Constants.PROCESS_STATE_APPROVAL});//查询录入流程定义为审核的
		}else if("offTask".equals(qBean.getStateTask())){
			if(user.getIsAdmin()!=1){
				queryMap.put("bpmLogOperator", loginId); //流程日志 实际处理人
			}
			queryMap.put("bpmLogStates", Constants.PROCESS_STATE_APPROVAL);
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_END_APP});//查询录入流程定义为审核的
		}

		int rowsCount = this.creditAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());

		List<CreditApp> creditAppList = this.creditAppService.queryBpmLoanAppList(queryMap);
		pm.setData(creditAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "credit/approval/creditApprovalList";
	}
	
	
	/**
	 * 跳转到更新信用贷款申请表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String creditVerifyUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CreditApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.creditAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "1");
			List<CreditDecision> creditDecisionList = creditDecisionService.queryList(fmap);
			if(creditDecisionList!=null&&creditDecisionList.size()>0&&id!=null){
				CreditDecision creditDecision = creditDecisionList.get(0);
				double yhkje = creditSignService.getYhkje(creditDecision.getAmount(), creditDecision.getProduct());
				map.put("yhkje1", yhkje);
				map.put("creditVerify", creditDecision);//审核信息
			}
			fmap.put("type", "2");
			List<CreditDecision> creditApprovalList = creditDecisionService.queryList(fmap);
			if(creditApprovalList!=null&&creditApprovalList.size()>0&&id!=null){
				CreditDecision creditDecision = creditApprovalList.get(0);
				double yhkje = creditSignService.getYhkje(creditDecision.getAmount(), creditDecision.getProduct());
				map.put("yhkje2", yhkje);
				map.put("creditApproval", creditDecision);//审批信息
			}else{
				CreditDecision decision = new CreditDecision();
				decision.setState("1");
				map.put("creditApproval", decision);//审批信息
			}
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_SPECIAL);
			if(bpmLogList.size() > 0){
				map.put("returnReasons", bpmLogList.get(0).getLogContent2());
				map.put("feedbackDescribe", bpmLogList.get(0).getLogContent4());
			}
			
			// 获取欺诈审核信息
			CreditAntifraud antifraud = this.creditAntifraudService.queryLatestByAppId(bean.getAppId(),BusinessConstants.CREDIT_FRAUD_STATE_OK);
			map.put("creditFraud", antifraud);
		}
		
		// 查询鼎轩融资情况
		CeilingAmountQBean dxBean = ceilingAmountService.countDxAmountOfWeek();
		map.put("dxBean", dxBean);
		
		log.info(thisMethodName+":end");
		return "credit/approval/creditApprovalUpdate";
	}

	/**
	 * 保存信用贷款 审批决策数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void creditApprovalSave(ApprovalFBean approvalFBean, BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			//保存
			CreditDecision decision = approvalFBean.getDecision();
			decision.setAmount(decision.getAmount() * 10000);
			
			//产品
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("name", decision.getProduct());
			List<Product> productList = productService.queryList(fmap);
			decision.setPeriod(productList.get(0).getPeriod());//借款期限
//			decision.setSreviceFeeRate(productList.get(0).getSreviceFeeRate());//服务费
//			decision.setInterestRate(productList.get(0).getInterestRate());//利率

			creditDecisionService.approvalUpdate(approvalFBean,loginId,org.getOrgId());
			writer.print(JsonUtil.object2json(new JsonMsg(true, String.valueOf(decision.getId()))));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "操作失败，程序异常。")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	

}
