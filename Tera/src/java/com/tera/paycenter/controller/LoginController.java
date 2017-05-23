/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.paycenter.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tera.customer.model.Customer;
import com.tera.paycenter.controller.form.PaymentFBean;
import com.tera.payment.model.Payment;
import com.tera.payment.service.PaymentService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.User;
import com.tera.util.NetUtils;

/**
 * 
 * <br>
 * <b>功能：</b>PaymentController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-21 14:54:17<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/*")
public class LoginController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(LoginController.class);
	
	/**
	 * PaymentService
	 */
	@Autowired(required=false) //自动注入
	private PaymentService<Payment> paymentService;
	
	
	@RequestMapping(value="/", method = RequestMethod.GET) 
	public String loginFilter(HttpServletRequest request) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Customer customer=(Customer) request.getSession().getAttribute("loginCustomer");
		if(customer!=null){
			log.info(thisMethodName+":end");
			return "redirect:payment/query.htm";
		}
		log.info(thisMethodName+":end");
		return "redirect:login.htm";
	}
	
	@RequestMapping(value="/login.htm", method = RequestMethod.GET) 
	public String login(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Customer customer=(Customer) request.getSession().getAttribute("loginCustomer");
		if(customer!=null){
			log.info(thisMethodName+":end");
			return "redirect:payment/query.htm";
		}
		log.info(thisMethodName+":end");
		return "login";
	}	
	@RequestMapping(value="/login.htm", method = RequestMethod.POST) 
	public String login(PaymentFBean prBean, HttpServletRequest request,HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		System.out.println(prBean.getUserName()+prBean.getIdType()+prBean.getIdNo());
		
		if (prBean.getUserName() == null || "".equals(prBean.getUserName())) {
			log.info(thisMethodName+":end");
			return "redirect:login.htm?errLoginMsg=" + URLEncoder.encode("姓名不同为空！", "UTF-8");
		}
		if (prBean.getIdType() == null || "".equals(prBean.getIdType())) {
			log.info(thisMethodName+":end");
			return "redirect:login.htm?errLoginMsg=" + URLEncoder.encode("证件类型不能为空！", "UTF-8");
		}
		if (prBean.getIdNo() == null || "".equals(prBean.getIdNo())) {
			log.info(thisMethodName+":end");
			return "redirect:login.htm?errLoginMsg=" + URLEncoder.encode("证件号码不能为空！", "UTF-8");
		}
		Customer customer=this.paymentService.checkIsCustomer(prBean.getUserType(),prBean.getUserName(),prBean.getIdType(),prBean.getIdNo());
		if(null != customer){
			request.getSession().setAttribute("loginCustomer", customer);
			log.info("客户登录成功！");
		}else {
			log.info(thisMethodName+":end");
			return "redirect:login.htm?errLoginMsg=" + URLEncoder.encode("登录信息有误，请从新登录！", "UTF-8");
		}
		log.info(thisMethodName+":end");
		return "redirect:payment/query.htm";
	}

	/**
	 * @param request request
	 * @param response response
	 * @param map map
	 * @return string
	 */
	@RequestMapping("/logout.htm")
	public String goLogin(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		request.getSession().removeAttribute("loginCustomer");
		request.getSession().invalidate();
		log.info(thisMethodName+":end");
		return "redirect:login.htm";
	}

}
