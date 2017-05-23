/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.checkcenter.controller;

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
import com.tera.credit.constant.Constants;
import com.tera.checkcenter.model.CheckCenter;
import com.tera.checkcenter.service.CheckCenterService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.model.form.AppFBean;
import com.tera.credit.model.form.CreditJsonMsg;
import com.tera.credit.model.form.CreditQBean;
import com.tera.credit.service.CreditAppService;
import com.tera.credit.service.CreditContactService;
import com.tera.credit.service.CreditExtService;
import com.tera.img.service.ImgService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.controller.BaseController;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.Org;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Role;
import com.tera.sys.service.RoleService;
import com.tera.util.DateUtils;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;

/**
 * 
 * 资金流转查看控制器
 * <b>功能：</b>CreditAppController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController  extends BaseController{

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(CheckCenterController.class);

	@Autowired(required=false) //自动注入
	private CheckCenterService checkCenterService;

	/**
	 * 资金流转查询页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String creditReviewQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "checkcenter/checkcenterQuery";
	}

	/**
	 * 资金流转查看页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 三个数值
	 */
	@RequestMapping("/list.do")
	public String checkcenterList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		
		CheckCenter checkcenter = new CheckCenter();
		//进账总额
		double totalReceipts = checkCenterService.queryTotalReceipts(null);
		checkcenter.setTotalReceipts(totalReceipts);
		//未撮合金额
		double disMatchAmount = checkCenterService.queryDisMatchAmount(null);
		checkcenter.setDisMatchAmount(disMatchAmount);
		//出借人资金锁定金额
		double lendLockAmount = checkCenterService.queryLendLockAmount(null);
		checkcenter.setLendLockAmount(lendLockAmount);
		//可支配金额=未撮合金额-出借人资金锁定金额
		double disposableAmount = disMatchAmount - lendLockAmount;
		checkcenter.setDisposableAmount(disposableAmount);
		//撮合占用金额
		double matchOccupyAmount = checkCenterService.queryMatchOccupyAmount(null);
		checkcenter.setMatchOccupyAmount(matchOccupyAmount);

		//取到值，传给页面
		map.put("checkcenter", checkcenter);
		log.info(thisMethodName+":end");
		return "checkcenter/checkcenterList";
	}

}
