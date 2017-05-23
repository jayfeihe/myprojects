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

import java.sql.Timestamp;
import java.util.Date;
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

import com.allinpay.ets.client.PaymentResult;
import com.allinpay.ets.client.RequestOrder;
import com.tera.contract.model.Contract;
import com.tera.contract.service.ContractService;
import com.tera.credit.model.CreditApp;
import com.tera.credit.service.CreditAppService;
import com.tera.customer.model.Customer;
import com.tera.lend.model.LendApp;
import com.tera.loan.model.LoanApp;
import com.tera.paycenter.controller.form.PayDisplayFBean;
import com.tera.payment.constant.AllinpayConstant;
import com.tera.payment.model.Payment;
import com.tera.payment.model.ThirdPaymentLog;
import com.tera.payment.service.PaymentService;
import com.tera.payment.service.ThirdPaymentService;
import com.tera.product.model.Product;
import com.tera.product.service.ProductService;
import com.tera.sys.model.PageModel;
import com.tera.sys.model.Parameter;
import com.tera.sys.service.ParameterService;
import com.tera.util.DateUtils;
import com.tera.util.ObjectUtils;
import com.tera.util.RequestUtils;

/**
 * 
 * <br>
 * <b>功能：</b>PaymentController<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-21 14:54:17<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Controller
@RequestMapping("/payment/**")
public class PaycenterController {

	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(PaycenterController.class);
	
	/**
	 * PaymentService
	 */
	@Autowired(required=false) //自动注入
	private PaymentService<Payment> paymentService;
	
	@Autowired(required=false) //自动注入
	private ThirdPaymentService thirdPaymentService;

	@Autowired(required=false) //自动注入
	private ParameterService<Parameter> parameterService;
	
	@Autowired(required=false) //自动注入
	private ContractService contractService;
	
	@Autowired(required=false) //自动注入
	private ProductService<Product> productService;
	
	
	/**
	 * 跳转到支付明细表的查询条件页面
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/query.htm")
	public String paymentQuery(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		log.info(thisMethodName+":end");
		return "payment/paymentQuery";
	}

	/**
	 * 显示支付明细表的查询列表
	 * @param request request
	 * @param map map
	 * @throws Exception exception
	 * @return 借款咨询; InnoDB free: 11264 kB list
	 */
	@RequestMapping("/list.htm")
	public String paymentList(HttpServletRequest request, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//得到当前 登录 客户
		Customer customer=(Customer) request.getSession().getAttribute("loginCustomer");
		PageModel pm = new PageModel();
		Object bean = RequestUtils.getRequestBean(Payment.class, request);
		Map<String, Object> queryMap = ObjectUtils.describe(bean);
		queryMap.put("customerType",customer.getCustomerType());
		queryMap.put("name",customer.getName());
		queryMap.put("idNo",customer.getIdNo());
		queryMap.put("idType",customer.getIdType());
		queryMap.put("states",new String[]{"1","6"});
		int rowsCount = this.paymentService.getWaitingPaymentCount(queryMap);
		pm.init(request, rowsCount, null, bean);
		queryMap.put("rowS", pm.getRowS());
		queryMap.put("rowE", pm.getRowE());
		List<Payment> paymentList = this.paymentService.getWaitingPayment(queryMap);
		pm.setData(paymentList);
		map.put("pm", pm);
		map.put("customerType", customer.getCustomerType());
		log.info(thisMethodName+":end");
		return "payment/paymentList";

		
	}

//	/**
//	 * 跳转到更新支付明细表的页面
//	 * @param id id
//	 * @param request request
//	 * @param response response
//	 * @param map map
//	 * @throws Exception exception
//	 * @return string
//	 */
//	@RequestMapping("/update.htm")
//	public String paymentUpdate(String id, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
//		Payment bean = null;
//		//如果存在
//		if (null != id && !"".equals(id)) {
//			bean  = this.paymentService.queryByKey(id);
//		}
//		map.put("bean", bean);
//		return "payment/paymentUpdate";
//	}
//
//	/**
//	 * 保存支付明细表数据
//	 * @param request request
//	 * @param map map
//	 * @throws Exception exception
//	 */
//	@RequestMapping("/save.htm")
//	public void paymentSave(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception  {
//		response.setContentType("application/json;charset=UTF-8");
//		PrintWriter writer = response.getWriter();
//		try {
//			//TODO service操作 需要修改
//			Payment bean = (Payment) RequestUtils.getRequestBean(Payment.class, request);
//			//如果存在
//			if (bean.getId() != 0) {
//				this.paymentService.updateOnlyChanged(bean);
//			} else { //如果不存在
//				this.paymentService.add(bean);
//			}
//			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功")));
//		} catch (Exception e) {
//			writer.print(JsonUtil.object2json(new JsonMsg(false, "失败")));
//			writer.flush();
//			writer.close();
//			throw e;
//		}
//		writer.flush();
//		writer.close();
//	}
//
//	/**
//	 * 删除支付明细表数据
//	 * @param request request
//	 * @param map map
//	 * @param response response
//	 * @param id id
//	 * @throws Exception exception
//	 */
////	@RequestMapping("/delete.htm")
//	public void paymentDelete(String[] id, HttpServletResponse response,
//			HttpServletRequest request, ModelMap map) throws Exception {
//		response.setContentType("application/json;charset=UTF-8");
//		PrintWriter writer = response.getWriter();
//		try {
//			this.paymentService.delete(id);
//			writer.print(JsonUtil.object2json(new JsonMsg(true, "成功！")));
//		} catch (Exception e) {
//			writer.print(JsonUtil.object2json(new JsonMsg(false, "关联数据，不能删除！")));
//			writer.flush();
//			writer.close();
//			throw e;
//		}
//		writer.flush();
//		writer.close();
//	}
	
	/**
	 * 跳转到查看支付明细表的页面
	 * @param request request
	 * @param response response
	 * @param map map
	 * @throws Exception exception
	 * @return string
	 */
	@RequestMapping("/read.htm")
	public String paymentRead(String id, String customerType,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		Payment bean = null;
		String type="";
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.paymentService.queryByKey(id);
			map.put("bean", bean);
		}
		String appOrContractNo=bean.getContractNo();
		//LendApp lendApp=paymentService.queryAppByAppId(appOrContractNo).get(0);
		if (appOrContractNo.startsWith("B")) {
			//如果是合同号 就查借款人信息
			LoanApp loanApp=paymentService.queryLoanAppByContractNo(appOrContractNo);
			type="B";
			map.put("appBean", loanApp);
		}else if (appOrContractNo.startsWith("L")) {
			type="L";
			LendApp lendApp=paymentService.getLendListByAppId(appOrContractNo);
			map.put("appBean", lendApp);
		}else{
			type="C";
			CreditApp creditApp = paymentService.getCreditListByContractNo(appOrContractNo);
			map.put("appBean", creditApp);
			Map<String, Object> map1 = new HashMap<String, Object>();
			map.put("contractNo", appOrContractNo);
			List<Contract> contractList = contractService.queryList(map1);
			if(null != contractList && contractList.size() > 0){
				map.put("contract", contractList.get(0));
				Product pro = productService.queryByName(contractList.get(0).getLoanProduct());
				map.put("product", pro);
			}
		}
		map.put("type", type);
		map.put("customerType", customerType);
		map.put("actualAmount", bean.getActualAmount());
		log.info(thisMethodName+":end");
		return "payment/paymentRead";
	}
	
	/**
	 *  确认支付 跳转到支付页面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/allinpay.htm") 
	public String allinpay(String id,HttpServletRequest request, HttpServletResponse response,ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		String rootPath="";
		
		Payment bean = null;
		List<ThirdPaymentLog> tlogList=null;
		// 如果存在
		if (null != id && !"".equals(id)) {
			bean = this.paymentService.queryByKey(id);
			// 得到第三方请求 操作日志。 并且支付状态 是完成支付的。
			tlogList=thirdPaymentService.queryBypaymentId(bean.getId(),"5");
		}
		if(bean!=null&&!"5".equals(bean.getState())){
			if(tlogList==null||tlogList.size()==0){
				Date xzDate=new Date();
				ThirdPaymentLog tlog=new ThirdPaymentLog();
				tlog.setSn("0");
				tlog.setPaymentId(bean.getId());
				tlog.setContractNo(bean.getContractNo());
				tlog.setPeriodNum(0);
				tlog.setSendTime(new Timestamp(xzDate.getTime()));
				tlog.setAmount(bean.getActualAmount());
				tlog.setSubject(bean.getSubject());
				tlog.setState("1");
				tlog.setOrgId(bean.getOrgId());
				tlog.setCreateTime(new Timestamp(xzDate.getTime()));
				tlog.setUpdateTime(new Timestamp(xzDate.getTime()));
				//商户系统订单号
				String orderNo="NO"+DateUtils.toString(xzDate, "yyyyMMddHHmmssSSS");
				tlog.setOrderNo(orderNo);
				thirdPaymentService.add(tlog);
				//商户的订单提交时间
				String orderDatetime=DateUtils.toString(xzDate, "yyyyMMddHHmmss");
				
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort()
						+ path + "/";
				
				RequestOrder requestOrder = new RequestOrder();
				requestOrder.setInputCharset(AllinpayConstant.inputCharset);
				requestOrder.setPickupUrl(basePath+AllinpayConstant.pickupUrl);
				requestOrder.setReceiveUrl(basePath+AllinpayConstant.receiveUrl);
				requestOrder.setVersion(AllinpayConstant.version);
				requestOrder.setMerchantId(AllinpayConstant.merchantId);
				requestOrder.setSignType(AllinpayConstant.signType);
				requestOrder.setPayType(AllinpayConstant.payType);
				
				requestOrder.setOrderNo(orderNo);
				requestOrder.setOrderDatetime(orderDatetime);
				requestOrder.setOrderAmount(Math.round(bean.getActualAmount()*100));
				//支付跳转 URL
				String redirectUrl=AllinpayConstant.serverIp+"?";
				redirectUrl+=requestOrder.getSrc();
				requestOrder.setKey(AllinpayConstant.key);
				redirectUrl+="signMsg="+requestOrder.doSign();
				log.info(thisMethodName+":end");
				return "redirect:"+redirectUrl;
			}else{
				map.put("message", "订单已经完成支付请不要重复提交！");
				log.info(thisMethodName+":end");
				return "message";
			}
		}else{
			map.put("message", "操作有误，请返回列表页后从新操作。");
			log.info(thisMethodName+":end");
			return "message";
		}
	}
	/**
	 * 客户回显  支付成功 ，用于回显使用。客户的客户端请求
	 * @param payFbean
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/paydisplay.htm") 
	public String payDisplay(PayDisplayFBean payFbean,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception{
		String thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		log.info(thisMethodName+":start");
		//判断 用户是否登录
		Customer customer=(Customer) request.getSession().getAttribute("loginCustomer");
		if(customer!=null){
			String rootPath=parameterService.queryByParamName("certpath").getParamValue();
			rootPath+=AllinpayConstant.cerName;
			PaymentResult paymentResult = new PaymentResult();
			paymentResult.setPayAmount(payFbean.getPayAmount());
			paymentResult.setOrderAmount(payFbean.getOrderAmount());
			paymentResult.setPayDatetime(payFbean.getPayDatetime());
			paymentResult.setSignType(payFbean.getSignType());
			paymentResult.setReturnDatetime(payFbean.getReturnDatetime());
			paymentResult.setPaymentOrderId(payFbean.getPaymentOrderId());
			paymentResult.setVersion(payFbean.getVersion());
			paymentResult.setIssuerId(payFbean.getIssuerId());
			paymentResult.setOrderNo(payFbean.getOrderNo());
			paymentResult.setExt1(payFbean.getExt1());
			paymentResult.setExt2(payFbean.getExt2());
			paymentResult.setPayResult(payFbean.getPayResult());
			paymentResult.setErrorCode(payFbean.getErrorCode());
			paymentResult.setPayType(payFbean.getPayType());
			paymentResult.setMerchantId(payFbean.getMerchantId());
			paymentResult.setLanguage(payFbean.getLanguage());
			paymentResult.setOrderDatetime(payFbean.getOrderDatetime());
			paymentResult.setSignMsg(payFbean.getSignMsg());
			paymentResult.setCertPath(rootPath); 
			//验证签名：返回true代表验签成功；否则验签失败。
			boolean verifyResult = paymentResult.verify();
			//验签成功，还需要判断订单状态，为"1"表示支付成功。
			boolean paySuccess = verifyResult && payFbean.getPayResult().equals("1");
			if(!paySuccess){
				map.put("msg", "支付失败，错误代码"+payFbean.getErrorCode());
			}else{
				//确认支付成功 ， 更改 订单信息
				paymentService.payUpdate(payFbean.getOrderNo(), paySuccess, payFbean.getErrorCode());
				map.put("msg", "支付成功。");
			}
			map.put("payFbean",payFbean);
			log.info(thisMethodName+":end");
		}
		return "payment/display";
	}
	
	

}
