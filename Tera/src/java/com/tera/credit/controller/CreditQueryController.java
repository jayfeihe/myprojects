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

import com.tera.credit.model.CreditApp;
import com.tera.credit.model.CreditDecision;
import com.tera.credit.model.form.CreditQBean;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditContactService;
import com.tera.credit.service.CreditDecisionService;
import com.tera.credit.service.CreditExtService;
import com.tera.credit.service.CreditSignService;
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
 * <b>功能：</b>CreditAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/credit/query")
public class CreditQueryController  extends BaseController{

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
	CreditDecisionService creditDecisionService;
	@Autowired(required=false) //自动注入
	CreditSignService creditSignService;


	/**
	 * 跳转到特殊审批的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String creditQueryQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "credit/query/creditQueryQuery";
	}

	/**
	 * 显示特殊审批查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String creditQueryList(CreditQBean qBean,HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		PageModel pm = new PageModel();
		Org sessionOrg = (Org) request.getSession().getAttribute(SysConstants.LOGIN_ORG);
		
		Map<String, Object> queryMap = ObjectUtils.describe(qBean);
		queryMap.put("orgIdLike", sessionOrg.getOrgId());
		
		int rowsCount = this.creditAppService.queryCreditQueryCount(queryMap);
		pm.init(request, rowsCount, null, qBean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<CreditApp> creditAppList = this.creditAppService.queryCreditQueryList(queryMap);
		pm.setData(creditAppList);
		map.put("pm", pm);
		map.put("stateType", qBean.getStateTask());
		log.info(thisMethodName+":end");
		return "credit/query/creditQueryList";
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
	public String creditQueryRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
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
			List<CreditDecision> creditVerityList = creditDecisionService.queryList(fmap);
			if(null != creditVerityList && creditVerityList.size() > 0){
				CreditDecision creditDecision = creditVerityList.get(0);
				double yhkje = creditSignService.getYhkje(creditDecision.getAmount(), creditDecision.getProduct());
				map.put("yhkje1", yhkje);
				map.put("creditDecision1", creditVerityList.get(0));//审核信息
			}
			fmap.put("type", "2");
			List<CreditDecision> approvalList = creditDecisionService.queryList(fmap);
			if(null != approvalList && approvalList.size() > 0){
				CreditDecision creditDecision = approvalList.get(0);
				double yhkje = creditSignService.getYhkje(creditDecision.getAmount(), creditDecision.getProduct());
				map.put("yhkje2", yhkje);
				map.put("creditDecision2", approvalList.get(0));//审批决策信息
			}
			fmap.put("type", "3");
			List<CreditDecision> specialApprovalList = creditDecisionService.queryList(fmap);
			if(null != specialApprovalList && specialApprovalList.size() > 0){
				CreditDecision creditDecision = specialApprovalList.get(0);
				double yhkje = creditSignService.getYhkje(creditDecision.getAmount(), creditDecision.getProduct());
				map.put("yhkje3", yhkje);
				map.put("creditDecision3", specialApprovalList.get(0));//特殊审批信息
			}
		}
		log.info(thisMethodName+":end");
		return "credit/query/creditQueryRead";
	}

}
