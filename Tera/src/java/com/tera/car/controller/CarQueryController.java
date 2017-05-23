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

import com.tera.car.model.CarApp;
import com.tera.car.model.CarDecision;
import com.tera.car.model.form.CarQBean;
import com.tera.car.service.CarAppService;
import com.tera.car.service.CarContactService;
import com.tera.car.service.CarDecisionService;
import com.tera.car.service.CarExtService;
import com.tera.car.service.CarSignService;
import com.tera.img.service.ImgService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.service.RoleService;
import com.tera.util.ObjectUtils;

/**
 * 
 * 信用贷款 查询 控制器
 * <b>功能：</b>carAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/car/query")
public class CarQueryController  extends BaseController{

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
	CarDecisionService carDecisionService;
	@Autowired(required=false) //自动注入
	CarSignService carSignService;


	/**
	 * 跳转到特殊审批的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String carQueryQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "car/query/carQueryQuery";
	}

	/**
	 * 显示特殊审批查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String carQueryList(CarQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		PageModel pm = new PageModel();
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		queryMap.put("orgIdLike", sessionOrg.getOrgId());
		
		int rowsCount = this.carAppService.queryCarQueryCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CarApp> carAppList = this.carAppService.queryCarQueryList(queryMap);
		pm.setData(carAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "car/query/carQueryList";
	}

	/**
	 * 跳转到审批详情页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String carQueryRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
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
			List<CarDecision> carVerityList = carDecisionService.queryList(fmap);
			if(null != carVerityList && carVerityList.size() > 0){
				CarDecision carDecision = carVerityList.get(0);
				double yhkje = carSignService.getYhkje(carDecision.getAmount(), carDecision.getProduct());
				map.put("yhkje1", yhkje);
				map.put("carDecision1", carVerityList.get(0));//审核信息
			}
			fmap.put("type", "2");
			List<CarDecision> approvalList = carDecisionService.queryList(fmap);
			if(null != approvalList && approvalList.size() > 0){
				CarDecision carDecision = approvalList.get(0);
				double yhkje = carSignService.getYhkje(carDecision.getAmount(), carDecision.getProduct());
				map.put("yhkje2", yhkje);
				map.put("carDecision2", approvalList.get(0));//审批决策信息
			}
			fmap.put("type", "3");
			List<CarDecision> specialApprovalList = carDecisionService.queryList(fmap);
			if(null != specialApprovalList && specialApprovalList.size() > 0){
				CarDecision carDecision = specialApprovalList.get(0);
				double yhkje = carSignService.getYhkje(carDecision.getAmount(), carDecision.getProduct());
				map.put("yhkje3", yhkje);
				map.put("carDecision3", specialApprovalList.get(0));//特殊审批信息
			}
		}
		log.info(thisMethodName+":end");
		return "car/query/carQueryRead";
	}

}
