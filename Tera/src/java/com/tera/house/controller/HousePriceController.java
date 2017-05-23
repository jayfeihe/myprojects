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

import com.tera.house.constant.BusinessConstants;
import com.tera.house.constant.Constants;
import com.tera.house.model.HouseAntifraud;
import com.tera.house.model.HouseApp;
import com.tera.house.model.HouseDecision;
import com.tera.house.model.HouseInfo;
import com.tera.house.model.form.HouseQBean;
import com.tera.house.model.form.PriceFBean;
import com.tera.house.service.HouseAntifraudService;
import com.tera.house.service.HouseAppService;
import com.tera.house.service.HouseContactService;
import com.tera.house.service.HouseDecisionService;
import com.tera.house.service.HouseExtService;
import com.tera.house.service.HouseInfoService;
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
import com.tera.util.StringUtils;

/**
 * 
 * 信用贷款审批 控制器
 * <b>功能：</b>HouseAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/house/price")
public class HousePriceController  extends BaseController{

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(HousePriceController.class);
	
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
	HouseDecisionService houseDecisionService;
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	@Autowired(required=false) //自动注入
	HouseSignService houseSignService;
	@Autowired(required=false) //自动注入
	private HouseAntifraudService houseAntifraudService;
	@Autowired(required=false) //自动注入
	private HouseInfoService houseInfoService;
	
	/**
	 * 跳转到审批分单的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String housePriceQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "house/price/housePriceQuery";
	}

	/**
	 * 显示查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String housePriceList(HouseQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
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
			queryMap.put("bpmStates", new String[]{Constants.PROCESS_STATE_PRICE});
		}else if("inTask".equals(qBean.getStateTask())){
			// 进行中
			if(user.getIsAdmin()!=1){
				queryMap.put("bpmLogOperator", loginId); //流程日志 实际处理人
			}
			queryMap.put("bpmLogStates", Constants.PROCESS_STATE_PRICE);//日志状态
			queryMap.put("nonBpmStates", new String[]{Constants.PROCESS_END_APP,Constants.PROCESS_STATE_PRICE});
		}else if("offTask".equals(qBean.getStateTask())){
			if(user.getIsAdmin()!=1){
				queryMap.put("bpmLogOperator", loginId); //流程日志 实际处理人
			}
			queryMap.put("bpmLogStates", Constants.PROCESS_STATE_PRICE);
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
		return "house/price/housePriceList";
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
	public String housePriceUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		HouseApp bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.houseAppService.queryByKey(id);
			map.put("bean", bean);
			
			// 车辆信息
			Map<String, Object> queryMap = new HashMap<String,Object>();
			queryMap.put("appId", bean.getAppId());
			List<HouseInfo> houseInfos = this.houseInfoService.queryList(queryMap);
			if (houseInfos != null && houseInfos.size() > 0) {
				map.put("houseInfo", houseInfos.get(0));
			}
			
			// 获取欺诈审核信息
			HouseAntifraud antifraud = this.houseAntifraudService.queryLatestByAppId(bean.getAppId(),BusinessConstants.HOUSE_FRAUD_STATE_OK);
			map.put("houseFraud", antifraud);
		}
		log.info(thisMethodName+":end");
		return "house/price/housePriceUpdate";
	}

	/**
	 * 保存信用贷款 审批决策数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
	@RequestMapping("/save.do")
	public void housePriceSave(PriceFBean priceFBean, BindingResult bingding, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginId = (String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		Org org = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		try {
			//保存
			HouseDecision decision = priceFBean.getDecision();
			decision.setAmount(decision.getAmount() * 10000);
			
			//产品
			Map<String, Object> fmap=new HashMap<String, Object>();
			fmap.put("name", decision.getProduct());
			List<Product> productList = productService.queryList(fmap);
			decision.setPeriod(productList.get(0).getPeriod());//借款期限

			houseDecisionService.priceUpdate(priceFBean,loginId,org.getOrgId());
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
