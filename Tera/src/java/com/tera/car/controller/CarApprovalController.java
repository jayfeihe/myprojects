/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.car.controller;

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
import com.tera.car.constant.BusinessConstants;
import com.tera.car.constant.Constants;
import com.tera.car.model.CarAntifraud;
import com.tera.car.model.CarApp;
import com.tera.car.model.CarDecision;
import com.tera.car.model.form.ApprovalFBean;
import com.tera.car.model.form.CarQBean;
import com.tera.car.service.CarAntifraudService;
import com.tera.car.service.CarAppService;
import com.tera.car.service.CarContactService;
import com.tera.car.service.CarDecisionService;
import com.tera.car.service.CarExtService;
import com.tera.car.service.CarSignService;
import com.tera.cooperation.dinxuan.model.CeilingAmountQBean;
import com.tera.cooperation.dinxuan.service.CeilingAmountService;
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
 * <b>功能：</b>CarAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/car/approval")
public class CarApprovalController  extends BaseController{

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CarApprovalController.class);
	
	/**
	 * carAppService
	 */
	@Autowired(required=false) //自动注入
	private CarAppService carAppService;
	@Autowired(required=false) //自动注入
	RoleService roleService;
	@Autowired(required=false) //自动注入
	ImgService imgService;
	@Autowired(required=false) //自动注入
	CarContactService carContactService;
	@Autowired(required=false) //自动注入
	CarExtService carExtService;
	@Autowired(required=false) //自动注入
	private ProcessService processService;
	@Autowired(required=false) //自动注入
	CarDecisionService carDecisionService;
	@Autowired(required=false) //自动注入
	ProductService productService;
	@Autowired(required=false) //自动注入
	CarSignService carSignService;
	@Autowired(required=false) //自动注入
	private CeilingAmountService ceilingAmountService;
	@Autowired(required=false) //自动注入
	private CarAntifraudService carAntifraudService;
	
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
		return "car/approval/carApprovalQuery";
	}

	/**
	 * 显示查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String assignApprovalList(CarQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
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

		int rowsCount = this.carAppService.queryBpmLoanAppCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());

		List<CarApp> carAppList = this.carAppService.queryBpmLoanAppList(queryMap);
		pm.setData(carAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "car/approval/carApprovalList";
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
	public String carVerifyUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		CarApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.carAppService.queryByKey(id);
			map.put("bean", bean);
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("appId", bean.getAppId());
			fmap.put("state", "1");
			fmap.put("type", "1");
			List<CarDecision> carDecisionList = carDecisionService.queryList(fmap);
			if(carDecisionList!=null&&carDecisionList.size()>0&&id!=null){
				CarDecision carDecision = carDecisionList.get(0);
				double yhkje = carSignService.getYhkje(carDecision.getAmount(), carDecision.getProduct());
				map.put("yhkje1", yhkje);
				map.put("carVerify", carDecision);//审核信息
			}
			fmap.put("type", "2");
			List<CarDecision> carApprovalList = carDecisionService.queryList(fmap);
			if(carApprovalList!=null&&carApprovalList.size()>0&&id!=null){
				CarDecision carDecision = carApprovalList.get(0);
				double yhkje = carSignService.getYhkje(carDecision.getAmount(), carDecision.getProduct());
				map.put("yhkje2", yhkje);
				map.put("carApproval", carDecision);//审批信息
			}else{
				CarDecision decision = new CarDecision();
				decision.setState("1");
				map.put("carApproval", decision);//审批信息
			}
			List<BpmLog> bpmLogList = processService.getProcessHistoryLog(bean.getAppId(), Constants.PROCESS_STATE_SPECIAL);
			if(bpmLogList.size() > 0){
				map.put("returnReasons", bpmLogList.get(0).getLogContent2());
				map.put("feedbackDescribe", bpmLogList.get(0).getLogContent4());
			}
			
			// 获取欺诈审核信息
			CarAntifraud antifraud = this.carAntifraudService.queryLatestByAppId(bean.getAppId(),BusinessConstants.CAR_FRAUD_STATE_OK);
			map.put("carFraud", antifraud);
		}
		
		// 查询鼎轩融资情况
		CeilingAmountQBean dxBean = ceilingAmountService.countDxAmountOfWeek();
		map.put("dxBean", dxBean);
		
		log.info(thisMethodName+":end");
		return "car/approval/carApprovalUpdate";
	}

	/**
	 * 保存信用贷款 审批决策数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void carApprovalSave(ApprovalFBean approvalFBean, BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			//保存
			CarDecision decision = approvalFBean.getDecision();
			decision.setAmount(decision.getAmount() * 10000);
			
			//产品
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("name", decision.getProduct());
			List<Product> productList = productService.queryList(fmap);
			decision.setPeriod(productList.get(0).getPeriod());//借款期限
//			decision.setSreviceFeeRate(productList.get(0).getSreviceFeeRate());//服务费
//			decision.setInterestRate(productList.get(0).getInterestRate());//利率

			carDecisionService.approvalUpdate(approvalFBean,loginId,org.getOrgId());
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
