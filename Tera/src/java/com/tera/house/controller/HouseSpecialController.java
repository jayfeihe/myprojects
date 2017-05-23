/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.house.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.tera.bpm.service.ProcessService;
import com.tera.house.constant.BusinessConstants;
import com.tera.house.constant.Constants;
import com.tera.house.model.HouseAntifraud;
import com.tera.house.model.HouseApp;
import com.tera.house.model.HouseDecision;
import com.tera.house.model.form.HouseQBean;
import com.tera.house.model.form.SpecialApprovalFBean;
import com.tera.house.service.HouseAntifraudService;
import com.tera.house.service.HouseAppService;
import com.tera.house.service.HouseContactService;
import com.tera.house.service.HouseDecisionService;
import com.tera.house.service.HouseExtService;
import com.tera.house.service.HouseSignService;
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

/**
 * 
 * 信用贷款特殊审批控制器
 * <b>功能：</b>houseAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/house/special")
public class HouseSpecialController  extends BaseController{

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(HouseApprovalController.class);
	
	/**
	 * houseAppService
	 */
	@Autowired(required=false) //自动注入
	private HouseAppService houseAppService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	HouseContactService houseContactService;
	@Autowired(required=false) //自动注入
	HouseExtService houseExtService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	HouseDecisionService houseDecisionService;
	@Autowired(required=false) //自动注入
	ProductService productService;
	@Autowired(required=false) //自动注入
	HouseSignService houseSignService;
	@Autowired(required=false) //自动注入
	private HouseAntifraudService houseAntifraudService;


	/**
	 * 跳转到特殊审批的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String specialApprovalQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "house/special/specialApprovalQuery";
	}

	/**
	 * 显示特殊审批查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String specialApprovalList(HouseQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
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
		if("waitTask".equals(qBean.getStateTask())){
			if(user.getIsAdmin()!=1){
				queryMap.put("processer", loginId); // 审核人
			}
			if("".equals(queryMap.get("processer"))){
				queryMap.remove("processer");
			}
			queryMap.put("nonStates", new String[]{"0"}); //状态
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_SPECIAL});//查询录入流程定义为审核的
		}else if("inTask".equals(qBean.getStateTask())){
			// 进行中
			if(user.getIsAdmin()!=1){
				queryMap.put("bpmLogOperator", loginId); //流程日志 实际处理人
			}
			queryMap.put("bpmLogStates", Constants.PROCESS_STATE_SPECIAL);//日志状态
			queryMap.put("nonBpmStates", new String[]{Constants.PROCESS_END_APP,Constants.PROCESS_STATE_SPECIAL});//查询录入流程定义为审核的
		}else if("offTask".equals(qBean.getStateTask())){
			if(user.getIsAdmin()!=1){
				queryMap.put("bpmLogOperator", loginId); //流程日志 实际处理人
			}
			queryMap.put("bpmLogStates", Constants.PROCESS_STATE_SPECIAL);
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_END_APP});//查询录入流程定义为审核的
		}
		int rowsCount = this.houseAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<HouseApp> houseAppList = this.houseAppService.queryBpmLoanAppList(queryMap);
		pm.setData(houseAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "house/special/specialApprovalList";
	}

	/**
	 * 跳转到特殊审批页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/update.do")
	public String specialApprovalUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		HouseApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.houseAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "1");
			List<HouseDecision> houseVerityList = houseDecisionService.queryList(fmap);
			if(houseVerityList.size() > 0){
				HouseDecision houseDecision = houseVerityList.get(0);
				double yhkje = houseSignService.getYhkje(houseDecision.getAmount(), houseDecision.getProduct());
				map.put("yhkje1", yhkje);
			}
			map.put("houseDecision1", houseVerityList.get(0));//审核信息
			fmap.put("type", "2");
			List<HouseDecision> approvalList = houseDecisionService.queryList(fmap);
			if(approvalList.size() > 0){
				HouseDecision houseDecision = approvalList.get(0);
				double yhkje = houseSignService.getYhkje(houseDecision.getAmount(), houseDecision.getProduct());
				map.put("yhkje2", yhkje);
			}
			map.put("houseDecision2", approvalList.get(0));//审批决策信息
			fmap.put("type", "3");
			List<HouseDecision> specialApprovalList = new ArrayList<HouseDecision>();
			specialApprovalList = houseDecisionService.queryList(fmap);
			if(specialApprovalList.size() <= 0){
				HouseDecision decision = new HouseDecision();
				decision.setState("1");
				specialApprovalList.add(decision);
			}else{
				HouseDecision houseDecision = specialApprovalList.get(0);
				double yhkje = houseSignService.getYhkje(houseDecision.getAmount(), houseDecision.getProduct());
				map.put("yhkje3", yhkje);
			}
			map.put("houseDecision3", specialApprovalList.get(0));//特殊审批信息
			
			// 获取欺诈审核信息
			HouseAntifraud antifraud = this.houseAntifraudService.queryLatestByAppId(bean.getAppId(),BusinessConstants.HOUSE_FRAUD_STATE_OK);
			map.put("houseFraud", antifraud);
		}
		log.info(thisMethodName+":end");
		return "house/special/specialApprovalUpdate";
	}
	
	/**
	 * 保存信用贷款特殊审批数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void houseSpecialSave(SpecialApprovalFBean specialApprovalFBean, BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			HouseDecision decision = specialApprovalFBean.getDecision();
			decision.setAmount(decision.getAmount() * 10000);
			//借款期限
//			Product product = null;
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("name", decision.getProduct());
			List<Product> productList = productService.queryList(fmap);
			decision.setPeriod(productList.get(0).getPeriod());
			//保存
			houseDecisionService.specialApprovalUpdate(specialApprovalFBean,loginId,org.getOrgId());
			writer.print(JsonUtil.object2json(new JsonMsg(true, "操作成功。")));
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
