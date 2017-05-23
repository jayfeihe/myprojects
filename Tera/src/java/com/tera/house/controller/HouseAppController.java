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
import java.util.Calendar;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.bpm.model.BpmLog;
import com.tera.bpm.service.ProcessService;
import com.tera.house.constant.Constants;
import com.tera.house.model.HouseApp;
import com.tera.house.model.HouseDecision;
import com.tera.house.model.HouseInfo;
import com.tera.house.model.form.AppFBean;
import com.tera.house.model.form.HouseJsonMsg;
import com.tera.house.model.form.HouseQBean;
import com.tera.house.service.HouseAppService;
import com.tera.house.service.HouseContactService;
import com.tera.house.service.HouseDecisionService;
import com.tera.house.service.HouseExtService;
import com.tera.house.service.HouseInfoService;
import com.tera.img.service.ImgService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.DataDictionary;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.service.DataDictionaryService;
import com.tera.sys.service.RoleService;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;

/**
 * 
 * 信用贷款申请表控制器
 * <b>功能：</b>HouseAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/house/app")
public class HouseAppController  extends BaseController{

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(HouseAppController.class);
	
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
	private ProductService<Product> productService;
	@Autowired(required=false) //自动注入
	HouseDecisionService houseDecisionService;
	@Autowired(required=false) //自动注入
	DataDictionaryService<DataDictionary> dataDictionaryService;
	@Autowired(required=false) //自动注入
	private HouseInfoService houseInfoService;
	
	
	
	
	/**
	 * 跳转到信用贷款申请表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String houseAppQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		boolean isHead=false;
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("loginId", loginId);
		roleMap.put("orgId", sessionOrg.getOrgId());
		//查询登录用户在当前机构下的角色等级
		List<Role> roles = this.roleService.getRoleByOrgLoginId(roleMap);
		if (roles!=null) {
			for (Role role : roles) {
				if("信用营业部经理".equals(role.getName())){
					isHead=true;
					break;
				}
			}
		}
		map.put("isHead", isHead);
		log.info(thisMethodName+":end");
		return "house/app/houseAppQuery";
	}

	/**
	 * 显示信用贷款申请表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String houseAppList(HouseQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("loginId", loginId);
		roleMap.put("orgId", sessionOrg.getOrgId());
		//查询登录用户在当前机构下的角色等级
		List<Role> roles = this.roleService.getRoleByOrgLoginId(roleMap);
		String roleLever = ""; //用户当前登录机构的最大角色等级
		if (roles.size() > 0) {
			boolean isHead=false;
			roleLever = roles.get(0).getOrgRoleLever();
			for (Role role : roles) {
				if("信用营业部经理".equals(role.getName())){
					isHead=true;
					break;
				}
			}
			map.put("isHead", isHead);
		}
		if(queryMap.get("orgId")==null || "".equals(queryMap.get("orgId"))){
			queryMap.put("orgId", sessionOrg.getOrgId()); //session 机构
		}
		
		if("".equals(queryMap.get("processer"))){
			queryMap.remove("processer");
		}
		if(null != qBean.getAppState() && !"".equals(qBean.getAppState())){
			queryMap.put("states", qBean.getAppState().split(","));			
		}
		queryMap.put("customerManager",loginId); //客户经理，当前登录人 查看属于他的 单子
		queryMap.put("roleLever", roleLever); //角色等级
		if("waitTask".equals(qBean.getStateTask())){
//			queryMap.put("operator", loginId); //Session 操作员
			queryMap.put("nonStates", new String[]{"0"}); //状态//因需要查看前端拒贷数据更改
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_APP});//查询录入流程定义为录入申请的
		}else if("inTask".equals(qBean.getStateTask())){
			// 进行中
			queryMap.put("nonStates", new String[]{"0"}); //状态//因需要查看前端拒贷数据更改
			queryMap.put("nonBpmStates", new String[]{Constants.PROCESS_STATE_APP,Constants.PROCESS_END_APP});
		}else if("offTask".equals(qBean.getStateTask())){
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_END_APP});
		}
		int rowsCount = this.houseAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<HouseApp> houseAppList = this.houseAppService.queryBpmLoanAppList(queryMap);
		//如果是签约前端拒贷，产品，金额改成最终决策后的。
		if("0".equals(qBean.getAppState())){
			for(int i=0;i<houseAppList.size();i++){
				map.put("isSaleRefuse", true);
				HouseApp appTemp = houseAppList.get(i);
				//显示前端拒贷码
				Map<String, Object> refuseCodeMap = new HashMap<String, Object>();
				if(!"".equals(appTemp.getSaleRefuseCode1()) && null != appTemp.getSaleRefuseCode1()){
					//前端拒贷码01
					refuseCodeMap.put("keyName", "saleRefuseCode01");
					refuseCodeMap.put("keyProp", appTemp.getSaleRefuseCode1());
					List<DataDictionary> dataDictionaryList01 = dataDictionaryService.queryList(refuseCodeMap);
					appTemp.setSaleRefuseCode1(dataDictionaryList01.get(0).getKeyValue());
					//前端拒贷码02
					refuseCodeMap.put("keyName", "saleRefuseCode02");
					refuseCodeMap.put("keyProp", appTemp.getSaleRefuseCode2());
					List<DataDictionary> dataDictionaryList02 = dataDictionaryService.queryList(refuseCodeMap);
					appTemp.setSaleRefuseCode2(dataDictionaryList02.get(0).getKeyValue());
				}
				//决策后：金额，产品，期限的显示
				Map<String, Object> refuseDecMap = new HashMap<String, Object>();
				refuseDecMap.put("appId", appTemp.getAppId());
				refuseDecMap.put("type", "0");
				List<HouseDecision> houseDecisionList = houseDecisionService.queryList(refuseDecMap);
				if(houseDecisionList.size()>0){
					appTemp.setProduct(houseDecisionList.get(0).getProduct());
					appTemp.setAmount(houseDecisionList.get(0).getAmount());
					appTemp.setPeriod(houseDecisionList.get(0).getPeriod());
				}
			}
		}
		pm.setData(houseAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "house/app/houseAppList";
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
	public String houseAppUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
//		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
//		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		HouseApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.houseAppService.queryByKey(id);
			map.put("bean", bean);
			
			Map<String, Object> queryMap=new HashMap<String, Object>();
			queryMap.put("appId", bean.getAppId());
			queryMap.put("state", "1");
			// 房屋基本信息
			List<HouseInfo> houseInfos = houseInfoService.queryList(queryMap);
			if (houseInfos != null && houseInfos.size() > 0) {
				map.put("houseInfo", houseInfos.get(0)); 
			}
			// 常用联系人
			queryMap.put("type", "1");
			map.put("commonContacts", houseContactService.queryList(queryMap));
			
			// 重要信息说明
			map.put("houseExts", houseExtService.queryList(queryMap)); 
			
			// 经营往来人
			queryMap.put("type", "2");
			map.put("dealingsContacts", houseContactService.queryList(queryMap));	
			
			if("2".equals(bean.getState())){//审核退回
				List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_VERIFY);
				if(bpmLogList.size() > 0){
					map.put("returnReasons", bpmLogList.get(0).getLogContent2());
				}
			}else if("5".equals(bean.getState())){//核价退回
				List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_PRICE);
				if(bpmLogList.size() > 0){
					map.put("returnReasons", bpmLogList.get(0).getLogContent2());
				}
			}
		}
		log.info(thisMethodName+":end");
		return "house/app/houseAppUpdate";
	}

	/**
	 * 保存信用贷款申请表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void houseAppSave(AppFBean appFBean, BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		if(bingding.hasErrors()) {
			List<ObjectError> errors = bingding.getAllErrors();
			for (ObjectError error : errors) {
				log.info(error.getCodes() + ", "+ error.getObjectName() + ", " + error.getDefaultMessage());
			}
			System.out.println("有参数未符合要求");
		}
		try {
			HouseApp houseApp=appFBean.getHouseApp();
			//借款金额
			houseApp.setAmount(houseApp.getAmount()*10000);
			//年收入
			houseApp.setIncome(houseApp.getIncome()*10000);
			//月还款额
//			houseApp.setMonthlyPayments(houseApp.getMonthlyPayments()*10000);
			//年营业额
			houseApp.setFirmIncome(houseApp.getFirmIncome()*10000);
			//配偶年收入  
			houseApp.setSpouseIncome(houseApp.getSpouseIncome()*10000);
			//产品
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("name", houseApp.getProduct());
			List<Product> productList = productService.queryList(fmap);
			houseApp.setPeriod(productList.get(0).getPeriod());//借款期限
//			houseApp.setSreviceFeeRate(productList.get(0).getSreviceFeeRate());//服务费
//			houseApp.setInterestRate(productList.get(0).getInterestRate());//利率
			
			HouseJsonMsg msg=houseAppService.appUpdate(appFBean, org, loginId);
			writer.print(JsonUtil.object2json(msg));
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

	/**
	 * 删除信用贷款申请表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/delete.do")
	public void houseAppDelete(int[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.houseAppService.delete(id);
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
	 * 跳转到查看信用贷款申请表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String houseAppRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
//		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
//		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		HouseApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.houseAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "1");
			map.put("commonContacts", houseContactService.queryList(fmap));	//常用联系人
			map.put("houseExts", houseExtService.queryList(fmap));	//经营往来人
			fmap.put("type", "2");
			map.put("dealingsContacts", houseContactService.queryList(fmap));	//经营往来人
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_VERIFY);
			if(bpmLogList.size() > 0){
				map.put("returnReasons", bpmLogList.get(0).getLogContent2());//审核退回原因
			}
			List<BpmLog> bpmLogList1 = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_APPROVAL);
			if(bpmLogList1.size() > 0){
				map.put("sp_fkxsms", bpmLogList1.get(0).getLogContent2());//审批_反馈销售描述
			}
			List<BpmLog> bpmLogList2 = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_SPECIAL);
			if(bpmLogList2.size() > 0){
				map.put("tssp_fkxsms", bpmLogList2.get(0).getLogContent2());//特殊审批_反馈销售描述
			}
		}
		log.info(thisMethodName+":end");
		return "house/app/houseAppRead";
	}
	
	/**
	 * 审核 审批 内部嵌套页面
	 * @param id
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read1.do")
	public String houseAppRead1(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		HouseApp bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.houseAppService.queryByKey(id);
		}
		//算出性别
		String idNo = bean.getIdNo();
		if (idNo.length() == 15){
			if(Integer.parseInt(idNo.substring(14,15)) % 2 == 1) { 
				bean.setSex("男");
			} else { 
				bean.setSex("女"); 
			}
		}else if(idNo.length() == 18){
			if(Integer.parseInt(idNo.substring(16,17)) % 2 == 1) { 
				bean.setSex("男"); 
			} else { 
				bean.setSex("女");
			}
		}else if(idNo.length() != 15 || idNo.length() != 18){
			bean.setSex("不详");
		}
		//算出工作年限
		int workAge = DateUtils.getYearRange(bean.getComAddDate(), bean.getInputTime());//进件时间减去comAddDate入职时间
		bean.setWorkAge(workAge);
		
		map.put("bean", bean);

		log.info(thisMethodName+":end");
		return "house/app/houseAppRead1";
	}
	
	@RequestMapping("/updateStaffNo.do")
	public void assignVerifyUpdates(String[] ids, String changeStaffNo, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			
			for (String id : ids) {
				HouseApp houseApp = houseAppService.queryByKey(id);
				if(id!=null&&changeStaffNo!=null){
					houseApp.setStaffNo(changeStaffNo);
					houseApp.setUpdateTime(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
					houseAppService.updateOnlyChanged(houseApp);
				}
			}
			
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error", e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "系统异常！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	/**
	 * 新标签页中进行前端拒贷
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/frontRefusePopup.do")
	public String repelLoanPopup(String id,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//检查String id是id还是appId
		HouseApp bean = null;
		bean  = this.houseAppService.queryByKey(id);
		map.put("bean", bean);
		
		log.info(thisMethodName+":end");
		return "house/app/houseAppFrontPopup";
	}
	
	/**
	 * 前端拒贷补录：新标签页中进行前端拒贷
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/frontRefusePopupReplenish.do")
	public String repelLoanPopupReplenish(String id,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//检查String id是id还是appId
		HouseApp bean = null;
		bean  = this.houseAppService.queryByKey(id);
		map.put("bean", bean);
		
		log.info(thisMethodName+":end");
		return "house/app/houseAppFrontPopupReplenish";
	}
	
	/**
	 * 前端拒贷
	 * @param repelLoanFBean 		
	 * @param request 	request
	 * @param response 	response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/saleRefuse.do")
	public void repelLoan(HouseApp houseApp, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		try{
			//积木拒贷操作，修改
			boolean b = houseDecisionService.saleRefuse(houseApp, loginId);
			if(b){
				writer.print(JsonUtil.object2json(new JsonMsg(true, "前端拒贷成功！")));				
			}else{
				writer.print(JsonUtil.object2json(new JsonMsg(false, "前端拒贷失败！")));								
			}
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

	/**
	 * 前端拒贷补录：前端拒贷
	 * @param repelLoanFBean 		
	 * @param request 	request
	 * @param response 	response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/saleRefuseReplenish.do")
	public void repelLoanReplenish(HouseApp houseApp, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		try{
			//积木拒贷操作，修改
			boolean b = houseDecisionService.saleRefuseReplenish(houseApp, loginId);
			if(b){
				writer.print(JsonUtil.object2json(new JsonMsg(true, "前端拒贷补录成功！")));				
			}else{
				writer.print(JsonUtil.object2json(new JsonMsg(false, "前端拒贷补录失败！")));								
			}
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
	
	/**
	 * 跳转到 补录信用报告页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/updateReplenish.do")
	public String houseVerifyUpdateReplenish(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		/*String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		HouseApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.houseAppService.queryByKey(id);
			map.put("bean", bean);
		}
		log.info(thisMethodName+":end"); */
		
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
//		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
//		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		HouseApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.houseAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "1");
			map.put("commonContacts", houseContactService.queryList(fmap));	//常用联系人
			map.put("houseExts", houseExtService.queryList(fmap));	//经营往来人
			fmap.put("type", "2");
			map.put("dealingsContacts", houseContactService.queryList(fmap));	//经营往来人
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_VERIFY);
			if(bpmLogList.size() > 0){
				map.put("returnReasons", bpmLogList.get(0).getLogContent2());
			}
		}
		log.info(thisMethodName+":end");
		return "house/app/houseAppUpdateReplenish";
	}
}
