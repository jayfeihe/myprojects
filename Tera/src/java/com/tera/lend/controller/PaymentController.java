/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.lend.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tera.payment.model.Payment;
import com.tera.payment.service.PaymentService;
import com.tera.sys.constant.SysConstants;
import com.tera.sys.model.JsonMsg;
import com.tera.sys.model.PageModel;
import com.tera.util.JsonUtil;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * <br>
 * <b>功能：</b>PaymentController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-07-07 14:05:30<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/payment/*")
public class PaymentController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(PaymentController.class);
	
	/**
	 * PaymentService
	 */
	@Autowired(required=false) //自动注入
	private PaymentService<Payment> paymentService;
	
	/**
	 * 跳转到支付明细表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.do")
	public String paymentQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "lend/payment/paymentQuery";
	}

	/**
	 * 显示支付明细表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.do")
	public String paymentList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Payment.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);

		queryMap.put("state", "9"); //状态 是等待支付审核的
		queryMap.put("inOut", "2"); //至查询 状态是 放款的 付
//		queryMap.put("newDate", new Date()); //当前时间，判断是否大于还款日
		
		
		int rowsCount = this.paymentService.queryLendPaymentCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Payment> paymentList = this.paymentService.queryLendPaymentList(queryMap);
		pm.setData(paymentList);
		map.put("pm", pm);
		log.info(thisMethodName+":end");
		return "lend/payment/paymentList";
	}

	/**
	 * 跳转到更新支付明细表的页面
	 * @param id id
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
//	@RequestMapping("/update.do")
	public String paymentUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		Payment bean = null;
		//如果存在
		if (null != id && !"".equals(id)) {
			bean  = this.paymentService.queryByKey(id);
		}
		map.put("bean", bean);
		return "lend/payment/paymentUpdate";
	}

	/**
	 * 保存支付明细表数据
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 */
//	@RequestMapping("/save.do")
	public void paymentSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			//TODO service操作 需要修改
			Payment bean = (Payment) RequestUtils.getRequestBean(Payment.class, request);
			//如果存在
			if (bean.getId() != 0) {
				this.paymentService.updateOnlyChanged(bean);
			} else { //如果不存在
				this.paymentService.add(bean);
			}
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
	}

	/**
	 * 删除支付明细表数据
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
//	@RequestMapping("/delete.do")
	public void paymentDelete(String[] id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			this.paymentService.delete(id);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
	}
	
	/**
	 * 确认 支付
	 * @param request request
	 * @param map map
	 * @param response response
	 * @param id id
	 * @throws Exception exception
	 */
	@RequestMapping("/approve.do")
	public void paymentApprove(int id, HttpServletResponse response,
			HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String loginid=(String) request.getSession().getAttribute(SysConstants.LOGIN_ID);
		try {
			Payment pt=paymentService.queryByKey(id);
			pt.setState("1");
			pt.setOperator(loginid);
			pt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			this.paymentService.updateOnlyChanged(pt);
			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
		} catch (Exception e) {
			log.error(thisMethodName+":error",e);
			writer.print(JsonUtil.object2json(new JsonMsg(false, "数据变更失败！")));
			writer.flush();
			writer.close();
			throw e;
		}
		writer.flush();
		writer.close();
		log.info(thisMethodName+":end");
	}
	
	
	/**
	 * 跳转到查看支付明细表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.do")
	public String paymentRead(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Payment bean = null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.paymentService.queryByKey(id);
		}
		map.put("bean", bean);
		log.info(thisMethodName+":end");
		return "lend/payment/paymentRead";
	}

}
