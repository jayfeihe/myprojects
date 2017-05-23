package com.hikootech.mqcash.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import payment.api.notice.Notice1318Request;
import payment.api.notice.Notice1363Request;
import payment.api.system.PaymentEnvironment;
import payment.api.system.TxMessenger;
import payment.api.tx.marketorder.Tx1311Request;
import payment.api.tx.marketorder.Tx1320Request;
import payment.api.tx.marketorder.Tx1320Response;
import payment.api.tx.marketorder.Tx1362Request;
import payment.api.tx.marketorder.Tx1362Response;

import com.hikootech.mqcash.constants.CpcnConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.exception.MQExceptionConstants;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.service.CpcnService;
import com.hikootech.mqcash.service.UserPaymentOrderService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.ValidateTools;

@Service("cpcnService")
public class CpcnServiceImpl implements CpcnService {

	private static Logger logger = LoggerFactory.getLogger(CpcnServiceImpl.class);
	
	@Autowired
	private UserPaymentOrderService userPaymentOrderService;
	
	
	@Override
	public Map<String, Object> notice1318(Notice1318Request nr)
			throws MQException {
		// TODO Auto-generated method stub
		logger.info("中金市场订单支付状态变更通知tx1318 : " + nr.getPaymentNo());
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		// 检查交易流水号是否为空
		String paymentOrderId = nr.getPaymentNo(); // 交易流水号
		if (EntityUtils.isEmpty(paymentOrderId)) {
			logger.error("主动付款异步通知错误，第三方通知中交易流水号为空，请检查原因");
		}

		// 检查返回交易状态是否为已知协定状态
		int status = nr.getStatus();
		if (status != 20 && status != 10) {
			logger.error("主动付款异步通知错误， 返回支付状态未知：流水号：" + paymentOrderId + ",状态码：" + status);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1318_RETURN_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1318_RETURN_STATUS_EXCEPTION_DESC);
		}
		
		//查询支付订单
		UserPaymentOrder _paymentOrder = userPaymentOrderService.queryPaymentOrderById(paymentOrderId);
		//校验支付订单请求状态和支付状态是否正确
		if(EntityUtils.isEmpty(_paymentOrder) 
				|| EntityUtils.isEmpty(_paymentOrder.getRequestStatus()) 
				|| StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue() != _paymentOrder.getPaymentStatus()){
			logger.error("代收请求返回支付流水号对应的支付订单支付状态有误! 返回流水号：" + paymentOrderId + ",请求状态：" 
				+ _paymentOrder.getRequestStatus() + ",支付状态：" + _paymentOrder.getPaymentStatus());
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION_DESC);
		}
		
		if(20 == status){
			// 获取银行处理时间
			Date bankTxTime = new Date();
			try {
				bankTxTime = DateUtil.transStrToDate(nr.getBankNotificationTime(), DateUtil.FORMAT_SS_NO_SYMBOL);
			} catch (Exception e1) {
				logger.error("代收请求通知错误，转换中金BankNotificationTime字段失败，默认还款成功时间为当前时间", e1);
			}
			
			//处理支付成功订单
			String msg = "中金市场订单支付通知返回状态为：支付成功，处理支付成功订单，支付流水号：" + paymentOrderId;
			logger.info(msg);
			retMap = userPaymentOrderService.dealPaymentOrderOfPaSuccess(_paymentOrder, bankTxTime, msg);
			
			String retCode = EntityUtils.isNotEmpty(retMap.get(ResponseConstants.RETURN_CODE))? (String) retMap.get(ResponseConstants.RETURN_CODE) : "";
			if(ResponseConstants.SUCCESS.equals(retCode)){
				String retMsg = "中金市场订单支付通知返回状态为：支付成功，处理'支付成功'订单成功！";
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
				retMap.put(ResponseConstants.RETURN_DESC, retMsg);
				retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS);//支付订单状态
				return retMap;
			}else{
				logger.info("处理支付成功订单失败，支付流水号：" + paymentOrderId);
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION_DESC);
			}
		}else if(status == 10){
			String retMsg = "中金市场订单支付通知返回状态为：未支付，无需处理未支付订单，支付流水号：" + paymentOrderId;
			logger.info(retMsg);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, retMsg);
			retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY);//支付订单状态
			return retMap;
		}
		
		String retMsg = "中金市场订单支付通知处理完毕，处理异常，处理流程有误！";
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, retMsg);
		return retMap;
	}

	@Override
	public Map<String, Object> notice1363(Notice1363Request nr)
			throws MQException {
		// TODO Auto-generated method stub
		logger.info("中金市场订单代收结果通知tx1363，支付订单流水号：" + nr.getTxSN() + "，支付订单订单号：" + nr.getOrderNo());
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		// 检查交易流水号是否为空
		String paymentOrderId = nr.getTxSN(); // 交易流水号
		if (EntityUtils.isEmpty(paymentOrderId)) {
			logger.error("代收请求通知错误，第三方通知中交易流水号为空，请检查原因,我方发送流水号：" + nr.getTxSN());
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_RETURN_TXSN_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_RETURN_TXSN_EXCEPTION_DESC);
		}
		
		int status = nr.getStatus();
		if (status != 20 && status != 30 && status != 40) {
			logger.error("代收请求通知错误， 返回中金支付状态未知：流水号：" + paymentOrderId + ",状态码：" + status);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_RETURN_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_RETURN_STATUS_EXCEPTION_DESC);
		}
		
		// 获取银行处理时间
		Date bankTxTime = new Date();
		try {
			bankTxTime = DateUtil.transStrToDate(nr.getBankTxTime(), DateUtil.FORMAT_SS_NO_SYMBOL);
		} catch (Exception e1) {
			logger.error("代收请求通知错误，转换中金BankTxTime字段失败，默认还款成功时间为当前时间，错误：" + e1.getMessage());
		}
		
		//查询支付订单
		UserPaymentOrder _paymentOrder = userPaymentOrderService.queryPaymentOrderById(paymentOrderId);
		//校验支付订单请求状态和支付状态是否正确
		if(EntityUtils.isEmpty(_paymentOrder)
				|| EntityUtils.isEmpty(_paymentOrder.getRequestStatus()) 
				|| (StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue() != _paymentOrder.getPaymentStatus()
					&& StatusConstants.PAYMENT_ORDER_STATUS_DEALING.intValue() != _paymentOrder.getPaymentStatus())
				){
			logger.error("代收请求返回支付流水号对应的支付订单支付状态有误! 返回流水号：" + paymentOrderId + ",请求状态：" 
				+ _paymentOrder.getRequestStatus() + ",支付状态：" + _paymentOrder.getPaymentStatus());
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION_DESC);
		}
		
		//返回状态：20处理中
		if(status == 20){
			logger.info("中金代收同步返回状态为：处理中，修改支付订单状态为处理中，返回流水号：" + paymentOrderId);
			userPaymentOrderService.updatePaymentOrderToPaying(paymentOrderId, bankTxTime, nr.getResponseMessage());
			String retMsg = "中金代收同步返回状态为：处理中，修改支付订单状态为'处理中'成功！";
			logger.info("中金代收同步返回状态为：处理中，修改支付订单状态为处理中成功！");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, retMsg);
			retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_DEALING);//支付订单状态
			return retMap;
		}
		
		//返回状态：40代扣失败
		if(status == 40){
			//处理支付失败订单
			logger.info("中金代收同步返回状态为：代扣失败，处理支付失败订单，支付流水号：" + paymentOrderId);
			retMap = userPaymentOrderService.dealPaymentOrderOfPayFailed(_paymentOrder, false, bankTxTime, nr.getResponseMessage());
			
			String retCode = EntityUtils.isNotEmpty(retMap.get(ResponseConstants.RETURN_CODE))? (String) retMap.get(ResponseConstants.RETURN_CODE) : "";
			if(ResponseConstants.SUCCESS.equals(retCode)){
				String retMsg = "中金代收同步返回状态为：代扣失败，处理'支付失败'订单成功！";
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
				retMap.put(ResponseConstants.RETURN_DESC, retMsg);
				retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_PAY_FAILED);//支付订单状态
				return retMap;
			}else{
				logger.info("处理支付失败订单失败，支付流水号：" + paymentOrderId);
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYFAILED_FAILED_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYFAILED_FAILED_EXCEPTION_DESC);
			}
		}
		
		//返回状态：30代扣成功
		if(status == 30){
			//处理支付成功订单
			logger.info("中金代收同步返回状态为：代扣成功，处理支付成功订单，支付流水号：" + paymentOrderId);
			retMap = userPaymentOrderService.dealPaymentOrderOfPaSuccess(_paymentOrder, bankTxTime, nr.getResponseMessage());
			
			String retCode = EntityUtils.isNotEmpty(retMap.get(ResponseConstants.RETURN_CODE))? (String) retMap.get(ResponseConstants.RETURN_CODE) : "";
			if(ResponseConstants.SUCCESS.equals(retCode)){
				String retMsg = "中金代收同步返回状态为：代扣成功，处理'支付成功'订单成功！";
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
				retMap.put(ResponseConstants.RETURN_DESC, retMsg);
				retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS);//支付订单状态
				return retMap;
			}else{
				logger.info("处理支付成功订单失败，支付流水号：" + paymentOrderId);
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION_DESC);
			}
		}
		
		String retMsg = "中金代收处理完毕，代收处理异常，处理流程有误！";
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, retMsg);
		return retMap;
	}

	@Override
	public Map<String, Object> request1362(UserPaymentOrder paymentOrder)
			throws MQException {
		// TODO Auto-generated method stub
		logger.info("市场订单单笔代收交易查询tx1362");
		// 1.创建交易请求对象
		Tx1362Request tx1362Request = new Tx1362Request();
		 // 执行报文处理
		try {
	        tx1362Request.setInstitutionID(ConfigUtils.getProperty("mqcash_institution_id")); //机构编码
	        tx1362Request.setTxSN(paymentOrder.getUserPaymentOrderId()); //交易流水号
	       
	        tx1362Request.process();
		}catch(Exception e){
			logger.info("组装中金tx1362报文：异常！", e);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_PROCESS_REQUEST_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_PROCESS_REQUEST_EXCEPTION_DESC);
		}
        
        logger.debug("txCode:1362,txName:市场订单单笔代收交易查询,执行报文处理,原请求xml : ");
        logger.debug(tx1362Request.getRequestPlainText());
        logger.debug("请求message : ");
        logger.debug(tx1362Request.getRequestMessage());
        logger.debug("请求signature : ");
        logger.debug(tx1362Request.getRequestSignature());
        
        // 获得参数message和signature
        String message = tx1362Request.getRequestMessage();
        String signature =  tx1362Request.getRequestSignature();
       
        // 与支付平台进行通讯
        TxMessenger txMessenger = new TxMessenger();
        String[] respMsg = null;
       
        // Flag=10:cmb, 20:paymentAccount   发送信息并收返回信息
        try {
		    respMsg = txMessenger.send(message, signature);// 0:message;
		} catch (Exception e) {
			logger.error("请求中金市场订单代收交易查询异常!支付订单流水号：" + paymentOrder.getUserPaymentOrderId() 
					+ "，支付订单订单号：" + paymentOrder.getPaymentOrderNo()
					, e);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1362_REQUEST_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1362_REQUEST_EXCEPTION_DESC);
		}
        
        // 1:signature
        String plainText = null;
		try {
			plainText = new String(payment.tools.util.Base64.decode(respMsg[0]), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("中金市场订单代收同步返回信息Base64解码失败，解码串：" + respMsg[0]);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION_DESC);
		}

        logger.debug("[message]=[" + respMsg[0] + "]");
        logger.debug("[signature]=[" + respMsg[1] + "]");
        logger.debug("[plainText]=[" + plainText + "]");
		
			
		Tx1362Response txResponse = null;
		try {
			txResponse = new Tx1362Response(respMsg[0], respMsg[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("中金市场订单代收同步返回信息解析成返回对应出错，转换串：message=" + respMsg[0] + ",signature=" + respMsg[1]);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION_DESC);
		}
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		// 查询失败
		if (!"2000".equals(txResponse.getCode())) {
			if (!"2000".equals(txResponse.getCode())) {
				String retMsg = "代收请求失败，返回code代码：" + txResponse.getCode() + ",描述信息：" + txResponse.getMessage() + ",我方发送交易流水号："
						+ paymentOrder.getUserPaymentOrderId();
				logger.info(retMsg);
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, retMsg);
				return retMap;
			}
		}

		// 查询成功
		logger.info("[Message]=[" + txResponse.getMessage() + "]");
		logger.info("[InstitutionID]=[" + txResponse.getInstitutionID() + "]");
		logger.info("[TxSN]=[" + txResponse.getTxSN() + "]");
		logger.info("[OrderNo]=[" + txResponse.getOrderNo() + "]");
		logger.info("[Amount]=[" + txResponse.getAmount() + "]");
		logger.info("[Status]=[" + txResponse.getStatus() + "]");
		logger.info("[BankTxTime]=[" + txResponse.getBankTxTime() + "]");
		logger.info("[ResponseCode]=[" + txResponse.getResponseCode() + "]");
		logger.info("[ResponseMessage]=[" + txResponse.getResponseMessage() + "]");
		logger.info("[BankID]=[" + txResponse.getBankID() + "]");
		logger.info("[Amount]=[" + txResponse.getAmount() + "]");
		logger.info("[AccountType]=[" + txResponse.getAccountType() + "]");
		logger.info("[AccountName]=[" + txResponse.getAccountName() + "]");
		logger.info("[AccountNumber]=[" + txResponse.getAccountNumber() + "]");
		logger.info("[BranchName]=[" + txResponse.getBranchName() + "]");
		logger.info("[Province]=[" + txResponse.getProvince() + "]");
		logger.info("[City]=[" + txResponse.getCity() + "]");
		logger.info("[Note]=[" + txResponse.getNote() + "]");
		logger.info("[ContractUserID]=[" + txResponse.getContractUserID() + "]");
		logger.info("[PhoneNumber]=[" + txResponse.getPhoneNumber() + "]");
		logger.info("[Email]=[" + txResponse.getEmail() + "]");
		logger.info("[IdentificationType]=[" + txResponse.getIdentificationType() + "]");

		// 检查交易流水号是否为空
		String paymentOrderId = txResponse.getTxSN(); // 交易流水号
		if (EntityUtils.isEmpty(paymentOrderId)) {
			logger.error("代收请求通知错误，第三方通知中交易流水号为空，请检查原因,我方发送流水号：" + txResponse.getTxSN());
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_RETURN_TXSN_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_RETURN_TXSN_EXCEPTION_DESC);
		}
		
		int status = txResponse.getStatus();
		if (status != 20 && status != 30 && status != 40) {
			logger.error("代收请求通知错误， 返回中金支付状态未知：流水号：" + paymentOrderId + ",状态码：" + status);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_RETURN_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_RETURN_STATUS_EXCEPTION_DESC);
		}
		
		// 获取银行处理时间
		Date bankTxTime = new Date();
		try {
			bankTxTime = DateUtil.transStrToDate(txResponse.getBankTxTime(), DateUtil.FORMAT_SS_NO_SYMBOL);
		} catch (Exception e1) {
			logger.error("代收请求通知错误，转换中金BankTxTime字段失败，默认还款成功时间为当前时间，错误：" + e1.getMessage());
		}
		
		//查询支付订单
		UserPaymentOrder _paymentOrder = userPaymentOrderService.queryPaymentOrderById(paymentOrderId);
		//校验支付订单请求状态和支付状态是否正确
		if(EntityUtils.isEmpty(_paymentOrder)
				|| EntityUtils.isEmpty(_paymentOrder.getRequestStatus()) 
				|| (StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue() != _paymentOrder.getPaymentStatus()
						&& StatusConstants.PAYMENT_ORDER_STATUS_DEALING.intValue() != _paymentOrder.getPaymentStatus())
				){
			logger.error("代收请求返回支付流水号对应的支付订单支付状态有误! 返回流水号：" + paymentOrderId + ",请求状态：" 
				+ _paymentOrder.getRequestStatus() + ",支付状态：" + _paymentOrder.getPaymentStatus());
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION_DESC);
		}
		
		
		//返回状态：20处理中
		if(status == 20){
			logger.info("中金代收同步返回状态为：处理中，修改支付订单状态为处理中，返回流水号：" + paymentOrderId);
			userPaymentOrderService.updatePaymentOrderToPaying(paymentOrderId, bankTxTime, txResponse.getResponseMessage());
			String retMsg = "中金代收同步返回状态为：处理中，修改支付订单状态为'处理中'成功！";
			logger.info("中金代收同步返回状态为：处理中，修改支付订单状态为处理中成功！");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, retMsg);
			retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_DEALING);//支付订单状态
			return retMap;
		}
		
		//返回状态：40代扣失败
		if(status == 40){
			//处理支付失败订单
			logger.info("中金代收同步返回状态为：代扣失败，处理支付失败订单，支付流水号：" + paymentOrderId);
			retMap = userPaymentOrderService.dealPaymentOrderOfPayFailed(_paymentOrder, false, bankTxTime, txResponse.getResponseMessage());
			
			String retCode = EntityUtils.isNotEmpty(retMap.get(ResponseConstants.RETURN_CODE))? (String) retMap.get(ResponseConstants.RETURN_CODE) : "";
			if(ResponseConstants.SUCCESS.equals(retCode)){
				String retMsg = "中金代收同步返回状态为：代扣失败，处理'支付失败'订单成功！";
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
				retMap.put(ResponseConstants.RETURN_DESC, retMsg);
				retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_PAY_FAILED);//支付订单状态
				return retMap;
			}else{
				logger.info("处理支付失败订单失败，支付流水号：" + paymentOrderId);
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYFAILED_FAILED_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYFAILED_FAILED_EXCEPTION_DESC);
			}
		}
		
		//返回状态：30代扣成功
		if(status == 30){
			//处理支付成功订单
			logger.info("中金代收同步返回状态为：代扣成功，处理支付成功订单，支付流水号：" + paymentOrderId);
			retMap = userPaymentOrderService.dealPaymentOrderOfPaSuccess(_paymentOrder, bankTxTime, txResponse.getResponseMessage());
			
			String retCode = EntityUtils.isNotEmpty(retMap.get(ResponseConstants.RETURN_CODE))? (String) retMap.get(ResponseConstants.RETURN_CODE) : "";
			if(ResponseConstants.SUCCESS.equals(retCode)){
				String retMsg = "中金代收同步返回状态为：代扣成功，处理'支付成功'订单成功！";
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
				retMap.put(ResponseConstants.RETURN_DESC, retMsg);
				retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS);//支付订单状态
				return retMap;
			}else{
				logger.info("处理支付成功订单失败，支付流水号：" + paymentOrderId);
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION_DESC);
			}
		}
		
		String retMsg = "中金代收处理完毕，代收处理异常，处理流程有误！";
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, retMsg);
		return retMap;
	}

	@Override
	public Map<String, String> request1311(Map<String, String> paramMap)
			throws MQException {
		// TODO Auto-generated method stub
		logger.info("调用中金tx1311生成：市场订单支付（直通车）的支付URL和参数");
		
		Map<String, String> retMap = new HashMap<String, String>();
		
		String orderNo = paramMap.get("orderNo");
		String paymentNo = paramMap.get("paymentNo");
		String amount = paramMap.get("amount");
		String fee = paramMap.get("fee");
		String payerID = paramMap.get("payerID");
		String payerName = paramMap.get("payerName");
		String usage = paramMap.get("usage");
		String remark = paramMap.get("remark");
		String payees = paramMap.get("payees");
		String bankID = paramMap.get("bankID");
		String notificationURL = paramMap.get("notificationURL");
		
		if(!ValidateTools.validateInteger(amount)){
			logger.error("支付金额参数必须为正整形！");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retMap.put(ResponseConstants.RETURN_DESC, "支付金额参数必须为正整形！");
			return retMap;
		}
		
		if(EntityUtils.isNotEmpty(fee) && !ValidateTools.validateInteger(fee)){
			logger.error("支付金额参数必须为正整形！");
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.INNER_INTERFACE_PARAM_NULL);
			retMap.put(ResponseConstants.RETURN_DESC, "支付金额参数必须为正整形！");
			return retMap;
		}
		
		String institutionID = ConfigUtils.getProperty("mqcash_institution_id");
		int accountType = CpcnConstants.ACCOUNT_TYPE_PRIVATE;
//		String notificationURL = ConfigUtils.getProperty("cpcn_notification_url");
		
		Tx1311Request tx1311Request = new Tx1311Request();
		tx1311Request.setInstitutionID(institutionID);
		tx1311Request.setOrderNo(orderNo);
		tx1311Request.setPaymentNo(paymentNo);
		tx1311Request.setAmount(Long.parseLong(amount));
		tx1311Request.setFee(Long.parseLong(fee));
		tx1311Request.setPayerID(payerID);
		tx1311Request.setPayerName(payerName);
		tx1311Request.setUsage(usage);
		tx1311Request.setRemark(remark);
		tx1311Request.setNotificationURL(notificationURL);
		tx1311Request.setBankID(bankID);
		tx1311Request.setAccountType(accountType);
		if (null != payees && payees.length() > 0) {
			String[] payeeList = payees.split(";");
			for (int i = 0; i < payeeList.length; i++) {
				tx1311Request.addPayee(payeeList[i]);
			}
		}

		// 3.执行报文处理
		try {
			tx1311Request.process();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("tx1311组装报文出错", e);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1311_PROCESS_REQUEST_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1311_PROCESS_REQUEST_EXCEPTION_DESC);
		}
		
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, "支付金额参数必须为正整形！");
		
//		retMap.put("plainText", tx1311Request.getRequestPlainText());
//		retMap.put("txCode", "1311");
//		retMap.put("txName", "市场订单支付直通车");
		
		retMap.put("message", tx1311Request.getRequestMessage());
		retMap.put("signature", tx1311Request.getRequestSignature());
		retMap.put("action", PaymentEnvironment.paymentURL);
		
		return retMap;
	}

	@Override
	public Map<String, String> request1320(Map<String, String> paramMap)
			throws MQException {
		// TODO Auto-generated method stub
		logger.info("调用中金tx1320：市场订单支付查询");
		
		Map<String, String> retMap = new HashMap<String, String>();
		
		String paymentNo = paramMap.get("paymentNo");
		
		Tx1320Response txResponse = this.requestQueryPaymentOrder(paymentNo);
		
		//查询失败
		if (!"2000".equals(txResponse.getCode())) {
			String retMsg = "市场订单支付查询失败，返回code代码：" + txResponse.getCode() + ",描述信息：" + txResponse.getMessage() + ",我方发送交易流水号："
					+ paymentNo;
			logger.info(retMsg);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, retMsg);
			return retMap;
		}
		
		logger.info("[Message]=[" + txResponse.getMessage() + "]");
        logger.info("[InstitutionID]=[" + txResponse.getInstitutionID() + "]");
        logger.info("[PaymentNo]=[" + txResponse.getPaymentNo() + "]");
        logger.info("[Amount]=[" + txResponse.getAmount() + "]");
        logger.info("[Remark]=[" + txResponse.getRemark() + "]");
        logger.info("[Status]=[" + txResponse.getStatus() + "]");
        logger.info("[BankNotificationTime]=[" + txResponse.getBankNotificationTime() + "]");
		
		// 检查交易流水号是否为空
		String paymentOrderId = txResponse.getPaymentNo();// 交易流水号
		if (EntityUtils.isEmpty(paymentOrderId)) {
			logger.error("市场订单支付查询错误，第三方通知中交易流水号为空，请检查原因,我方发送流水号：" + paymentNo);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1320_RETURN_PAYMENTNO_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1320_RETURN_PAYMENTNO_EXCEPTION_DESC);
		}
		
		// 检查返回交易状态是否为已知协定状态
		int status = txResponse.getStatus();
		if (status != 20 && status != 10) {
			logger.error("主动付款异步通知错误， 返回支付状态未知：流水号：" + paymentOrderId + ",状态码：" + status);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1318_RETURN_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1318_RETURN_STATUS_EXCEPTION_DESC);
		}
		
		//查询支付订单
		UserPaymentOrder _paymentOrder = userPaymentOrderService.queryPaymentOrderById(paymentOrderId);
		//校验支付订单请求状态和支付状态是否正确
		if(EntityUtils.isEmpty(_paymentOrder) 
				|| EntityUtils.isEmpty(_paymentOrder.getRequestStatus()) 
				|| StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue() != _paymentOrder.getPaymentStatus()){
			logger.error("代收请求返回支付流水号对应的支付订单支付状态有误! 返回流水号：" + paymentOrderId + ",请求状态：" 
				+ _paymentOrder.getRequestStatus() + ",支付状态：" + _paymentOrder.getPaymentStatus());
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION_DESC);
		}
		
		if(20 == status){
			// 获取银行处理时间
			Date bankTxTime = new Date();
			try {
				bankTxTime = DateUtil.transStrToDate(txResponse.getBankNotificationTime(), DateUtil.FORMAT_SS_NO_SYMBOL);
			} catch (Exception e1) {
				logger.error("市场订单支付查询错误，转换中金BankNotificationTime字段失败，默认还款成功时间为当前时间，错误：" + e1.getMessage());
			}
			
			//处理支付成功订单
			String msg = "中金市场订单支付通知返回状态为：支付成功，处理支付成功订单，支付流水号：" + paymentOrderId;
			logger.info(msg);
			Map<String, Object> resMap = userPaymentOrderService.dealPaymentOrderOfPaSuccess(_paymentOrder, bankTxTime, msg);
			
			String retCode = EntityUtils.isNotEmpty(resMap.get(ResponseConstants.RETURN_CODE))? (String) resMap.get(ResponseConstants.RETURN_CODE) : "";
			if(ResponseConstants.SUCCESS.equals(retCode)){
				String retMsg = "中金市场订单支付通知返回状态为：支付成功，处理'支付成功'订单成功！";
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
				retMap.put(ResponseConstants.RETURN_DESC, retMsg);
				retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS.toString());//支付订单状态--如果要区分重复付款，这里注释掉，dealPaymentOrderOfPaSuccess方法返回了对应状态
//				retMap.put("status", resMap.get("status").toString());
				return retMap;
			}else{
				logger.info("处理支付成功订单失败，支付流水号：" + paymentOrderId);
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_DEAL_PAYSUCCESS_FAILED_EXCEPTION_DESC);
			}
		}else if(status == 10){
			String retMsg = "中金市场订单支付通知返回状态为：未支付，无需处理未支付订单，支付流水号：" + paymentOrderId;
			logger.info(retMsg);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
			retMap.put(ResponseConstants.RETURN_DESC, retMsg);
			retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.toString());//支付订单状态
			return retMap;
		}
		
		String retMsg = "中金市场订单支付通知处理完毕，处理异常，处理流程有误！";
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, retMsg);
		return retMap;
	}
	
	private Tx1320Response requestQueryPaymentOrder(String paymentNo){
		logger.info("tx1320报文组装和调用");
		
		String institutionID = ConfigUtils.getProperty("mqcash_institution_id");
		
		Tx1320Request tx1320Request = new Tx1320Request();
		tx1320Request.setInstitutionID(institutionID);
		tx1320Request.setPaymentNo(paymentNo);

		try {
			tx1320Request.process();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("tx1320组装报文出错", e);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1320_PROCESS_REQUEST_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1320_PROCESS_REQUEST_EXCEPTION_DESC);
		}
		
		logger.info("组装中金tx1320报文：结束");
		
		logger.info("原请求xml : ");
		logger.info(tx1320Request.getRequestPlainText());
		logger.info("请求message : ");
		logger.info(tx1320Request.getRequestMessage());
		logger.info("请求signature : ");
		logger.info(tx1320Request.getRequestSignature());
		
		// 与支付平台进行通讯
		TxMessenger txMessenger = new TxMessenger();
		String[] respMsg = null;

		// 发送信息 并接受返回信息
		try {
			respMsg = txMessenger.send(tx1320Request.getRequestMessage(), tx1320Request.getRequestSignature());// 0:message;
		} catch (Exception e) {
			logger.error("请求市场订单支付查询异常!支付订单流水号：" + paymentNo, e);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1320_REQUEST_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1320_REQUEST_EXCEPTION_DESC);
		}

		String plainText = null;
		try {
			plainText = new String(payment.tools.util.Base64.decode(respMsg[0]), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("中金市场订单支付查询返回信息Base64解码失败，解码串：" + respMsg[0]);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1320_REQUEST_DECODE_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1320_REQUEST_DECODE_EXCEPTION_DESC);
		}
		
		logger.info("中金市场订单支付查询报文信息：");
		logger.info("[message]=[" + respMsg[0] + "]");
		logger.info("[signature]=[" + respMsg[1] + "]");
		logger.info("[plainText]=[" + plainText + "]");

		Tx1320Response txResponse = null;
		try {
			txResponse = new Tx1320Response(respMsg[0], respMsg[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("中金市场订单支付查询返回信息解析成返回对应出错，转换串：message=" + respMsg[0] + ",signature=" + respMsg[1]);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1320_REQUEST_DECODE_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1320_REQUEST_DECODE_EXCEPTION);
		}

		return txResponse;
	}

}
