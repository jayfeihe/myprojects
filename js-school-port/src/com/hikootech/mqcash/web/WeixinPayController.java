package com.hikootech.mqcash.web;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.WriterException;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dto.WxUnifiedOrderDTO;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.service.InstalmentManageService;
import com.hikootech.mqcash.service.UserPaymentOrderService;
import com.hikootech.mqcash.service.WxNativePayService;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.QRCodeUtil;
import com.hikootech.mqcash.util.UserUtils;

/**
 * 微信支付Controller
 * 
 * @author QYANZE
 *
 */
@Controller
@RequestMapping("/wxPay")
public class WeixinPayController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WxNativePayService wxNativePayService;
	@Autowired
	private UserPaymentOrderService userPaymentOrderService;
	@Autowired
	private InstalmentManageService instalmentManageService;

	/**
	 * 获取二维码地址-模式二，生成订单获取二维码地址
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("/nativePay.jhtml")
	public String getQRCodeUrlModel2(String paymentOrderId,String paymentInfoToken, Model model) {
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("paymentOrderId", paymentOrderId);
			queryMap.put("userId", getUserInfo().getUserId());
			UserPaymentOrder paymentOrder = userPaymentOrderService.queryPaymentOrderById(queryMap);

			WxUnifiedOrderDTO orderDTO = this.createWxUnifiedOrder(paymentOrder);
			
			String code_url = wxNativePayService.getQRCodeUrlModel2(orderDTO);
			
			code_url = Base64.encodeBase64URLSafeString(code_url.getBytes());
			
			model.addAttribute("code_url", code_url);
			model.addAttribute("paymentOrderNo", paymentOrder.getPaymentOrderNo());
			model.addAttribute("paymentOrderId", paymentOrderId);
			model.addAttribute("payAmount",paymentOrder.getPaymentAmount().toString());
			model.addAttribute("userInfo", getUserInfo());
			model.addAttribute("paymentInfoToken", paymentInfoToken);
		} catch (Exception e) {
			logger.error("跳转至微信扫码页面时出错：" + e.getMessage(), e);
		}
		return "wxPay/nativePay";
	}

	/**
	 * 重新获取二维码
	 * 
	 * @param paymentOrderId 订单id
	 * @param paymentInfoToken 支付token
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getQR.jhtml", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getQR(String paymentOrderId, String paymentInfoToken) {
		
		// 更新原先的订单为无效
		this.disabledOldPaymentOrder(paymentOrderId);
		
		Map<String, Object> returnMap = new HashMap<String,Object>();
		
		// 从缓存中获取订单
		Map<String, Object> sessionPayMap = UserUtils.getPaymentOrderToPayFromCache(getRequest().getSession());
		
		if(sessionPayMap==null||sessionPayMap.get(paymentInfoToken)==null){
			logger.error("缓存中找不到对应的还款相关信息，key：" + paymentInfoToken);
			returnMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			returnMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			return returnMap;
		}
		
		Map<String, Object> payInfoMap = (Map<String, Object>) sessionPayMap.get(paymentInfoToken);
		// 创建新订单
		Map<String, Object> wxPayMap = instalmentManageService.sureWxPay(payInfoMap, getUserInfo());
		if (wxPayMap.get(ResponseConstants.RETURN_CODE).equals(ResponseConstants.SUCCESS)) {
			String newPaymentOrderId = (String) wxPayMap.get("paymentOrderId");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("userId", getUserInfo().getUserId());
			queryMap.put("paymentOrderId", newPaymentOrderId);
			UserPaymentOrder newPaymentOrder = this.userPaymentOrderService.queryPaymentOrderById(queryMap);
			// 根据新订单获取二维码url
			try {
				WxUnifiedOrderDTO orderDTO = this.createWxUnifiedOrder(newPaymentOrder);
				String code_url = wxNativePayService.getQRCodeUrlModel2(orderDTO);
				logger.info("重新获取微信支付维码url：" + code_url);
				code_url = Base64.encodeBase64URLSafeString(code_url.getBytes());
				returnMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
				returnMap.put("code_url", code_url);
				returnMap.put("paymentOrderId", newPaymentOrderId);
				return returnMap;
			} catch (Exception e) {
				logger.error("重新获取微信支付维码url异常：" + e.getMessage(), e);
				returnMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				returnMap.put(ResponseConstants.RETURN_DESC, "出错了，请返回重试！");
				return returnMap;
			} 
		} else {
			return wxPayMap;
		}
	}
	
	/**
	 * 创建微信统一下单对象
	 * 
	 * @param paymentOrder
	 * @return
	 * @throws ParseException
	 */
	private WxUnifiedOrderDTO createWxUnifiedOrder(UserPaymentOrder paymentOrder) throws Exception {
		WxUnifiedOrderDTO orderDTO = new WxUnifiedOrderDTO();
		// 商户订单号
		orderDTO.setOut_trade_no(paymentOrder.getUserPaymentOrderId());
		// 商品描述
		orderDTO.setBody("秒趣还款");
		// 商品id
		orderDTO.setProduct_id(paymentOrder.getPaymentOrderNo());
		// 金额
		orderDTO.setTotal_fee(paymentOrder.getPaymentAmount().multiply(new BigDecimal(100)).intValue());
		// 设置订单失效时间为65
		String time_start = DateUtil.getCurDateStr(DateUtil.FORMAT_SS_NO_SYMBOL);
		String time_end = DateUtil.formatDate(DateUtil
				.addDate(DateUtil.transStrToDate(time_start, DateUtil.FORMAT_SS_NO_SYMBOL), Calendar.SECOND, 65),
				DateUtil.FORMAT_SS_NO_SYMBOL);
		orderDTO.setTime_start(time_start);
		orderDTO.setTime_expire(time_end);
		
		return orderDTO;
	}
	
	/**
	 * 将失效的订单置为无效
	 * 
	 * @param paymentOrderId
	 */
	private void disabledOldPaymentOrder(String paymentOrderId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("paymentOrderId", paymentOrderId);
		queryMap.put("userId", getUserInfo().getUserId());
		UserPaymentOrder paymentOrder = userPaymentOrderService.queryPaymentOrderById(queryMap);
		if (paymentOrder != null) {
			// 更新操作
			UserPaymentOrder order = new UserPaymentOrder();
			order.setUserPaymentOrderId(paymentOrderId);
			order.setRequestStatus(StatusConstants.REQUEST_STATUS_FAILED);
			order.setPaymentStatus(StatusConstants.PAYMENT_ORDER_STATUS_PAY_FAILED);
			order.setUpdateTime(new Date());
			order.setDescp("微信二维码失效,支付失败");
			this.userPaymentOrderService.modifyPaymentOrderById(order);
		}
	}

	/**
	 * 生成二维码
	 * 
	 * @param code_url
	 * @param response
	 */
	@RequestMapping(value = "/writeQr.jhtml", method = RequestMethod.GET)
	public void writeQr(String code_url, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		code_url = new String(Base64.decodeBase64(code_url));
		logger.info("处理后二维码url：" + code_url);
		try {
			if (EntityUtils.isNotEmpty(code_url)) {
				OutputStream outputStream = response.getOutputStream();
				QRCodeUtil.writeToStream(code_url, "jpg", outputStream);
				outputStream.flush();
				outputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询微信订单
	 * 
	 * @param paymentInfoToken
	 * @return
	 */
	@RequestMapping("/queryOrder.jhtml")
	@ResponseBody
	public Map<String, String> queryOrder(String paymentOrderId) {
		
		Map<String, String> returnMap = new HashMap<String, String>();
		
		Map<String, String> queryOrderMap = this.wxNativePayService.queryOrder(paymentOrderId );
		String return_code = queryOrderMap.get("resultCode");
		if (ResponseConstants.SUCCESS.equals(return_code)) {
			returnMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			returnMap.put("trade_state", queryOrderMap.get("trade_state"));
		} else {
			returnMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
		}
		
		return returnMap;
	}
	
	/**
	 * 微信支付成功页面跳转
	 * 
	 * @param payAmount 还款金额
	 * @param model
	 * @return
	 */
	@RequestMapping("/nativePaySuccess.jhtml")
	public String nativePaySuccess(String payAmount, Model model) {
		model.addAttribute("userInfo", getUserInfo());
		model.addAttribute("payAmount", payAmount);
		return "wxPay/nativePaySuccess";
	}
	
	/**
	 * 微信支付失败页面跳转
	 * 
	 * @param payAmount 还款金额
	 * @param model
	 * @return
	 */
	@RequestMapping("/nativePayFail.jhtml")
	public String nativePayFail(String payAmount, Model model) {
		model.addAttribute("userInfo", getUserInfo());
		model.addAttribute("payAmount", payAmount);
		return "wxPay/nativePayFail";
	}
}
