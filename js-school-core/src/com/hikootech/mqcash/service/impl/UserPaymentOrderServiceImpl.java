package com.hikootech.mqcash.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.CpcnConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.UserBankCardDAO;
import com.hikootech.mqcash.dao.UserPaymentOrderDAO;
import com.hikootech.mqcash.dao.UserRepaymentOrderDAO;
import com.hikootech.mqcash.dao.UserRepaymentPlansDAO;
import com.hikootech.mqcash.dto.UserForSendSmDTO;
import com.hikootech.mqcash.dto.UserRepaymentPlansDTO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.exception.MQExceptionConstants;
import com.hikootech.mqcash.po.SmOrder;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.po.UserInstalment;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserPaymentRecordItem;
import com.hikootech.mqcash.po.UserRepaymentPlans;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.SmOrderService;
import com.hikootech.mqcash.service.UserInstalmentService;
import com.hikootech.mqcash.service.UserPaymentOrderService;
import com.hikootech.mqcash.service.UserRepaymentPlansService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.InstalmentRuleUtils;
import com.hikootech.mqcash.util.InstalmentUtils;
import com.hikootech.mqcash.util.OrderIdRuleUtil;
import com.hikootech.mqcash.util.UUIDTools;

import payment.api.system.TxMessenger;
import payment.api.tx.marketorder.Tx1361Request;
import payment.api.tx.marketorder.Tx1361Response;

@Service("userPaymentOrderService")
public class UserPaymentOrderServiceImpl implements UserPaymentOrderService {

	private static Logger logger = LoggerFactory.getLogger(UserPaymentOrderServiceImpl.class);
	
	@Autowired
	private UserPaymentOrderDAO userPaymentOrderDAO;
	@Autowired
	private UserRepaymentPlansDAO userRepaymentPlansDAO;
	@Autowired
	private UserInstalmentService userInstalmentService;
	@Autowired
	private UserRepaymentPlansService userRepaymentPlansService;
	@Autowired
	private ConfigKvService configKvService;
	@Autowired
	private SmOrderService smOrderService;
	@Autowired
	private UserRepaymentOrderDAO userRepaymentOrderDAO;
	@Autowired
	private UserBankCardDAO userBankCardDAO;

	@Override
	public void createPayOrder(UserRepaymentPlansDTO repaymentPlansDTO, Date repaymentTime) throws MQException {
		logger.info("生成代收支付订单，还款计划id：" + repaymentPlansDTO.getRepaymentPlansId());
		logger.debug("所有应还金额 = 本期应还本金:" + repaymentPlansDTO.getReceivablePrincipal() + ",本期还款服务费:"
				+ repaymentPlansDTO.getReceivableService() + ",本期逾期罚息：" + repaymentPlansDTO.getReceivableOverdue());
		logger.debug("所有以还金额 = 本期实收本金:" + repaymentPlansDTO.getReceivedPrincipal() + ",本期实收服务费:"
				+ repaymentPlansDTO.getReceivedService() + ",本期实收逾期罚息：" + repaymentPlansDTO.getReceivedOverdue());
		logger.debug("减免的逾期罚息:" + repaymentPlansDTO.getReduceOverdue());
		
		//计算应还金额，校验是否等于receivableAmount：写一个公共方法计算应还金额（本金+服务费+逾期罚息-减免=应还金额）
		BigDecimal realOverdue = InstalmentUtils.calOverdue(repaymentPlansDTO.getPartnerOrderAmount(), repaymentPlansDTO.getOverdueRate(), repaymentPlansDTO.getPlanRepaymentTime(), new Date());
		logger.info("实际应收罚息：" + realOverdue + " ，实际收取罚息：" + repaymentPlansDTO.getReceivableOverdue());
		
		if(realOverdue.compareTo(repaymentPlansDTO.getReceivableOverdue()) != 0){
			logger.error("还款计划id为 : " + repaymentPlansDTO.getRepaymentPlansId()
					+ ",errorCode : " + MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_OVERDUE_INCORRENT 
					+ ",errorMsg : " + MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_OVERDUE_INCORRENT_DESC);
			throw new MQException(MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_OVERDUE_INCORRENT, MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_OVERDUE_INCORRENT_DESC);
		}
		
		BigDecimal receivableAmount = repaymentPlansDTO.getReceivableAmount();
		BigDecimal realAmount = InstalmentRuleUtils.calcPayAmount(repaymentPlansDTO.getReceivablePrincipal() , repaymentPlansDTO.getReceivableService(), repaymentPlansDTO.getReceivableOverdue(), repaymentPlansDTO.getReduceOverdue());
		logger.info("实际应收总金额：" + realAmount + " ，实际收取总金额：" + receivableAmount);
		
		if(receivableAmount.compareTo(realAmount) != 0 || realAmount.compareTo(new BigDecimal(0)) == 0){
			logger.error("还款计划id为 : " + repaymentPlansDTO.getRepaymentPlansId()
					+ ",errorCode : " + MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_AMOUNT_INCORRENT 
					+ ",errorMsg : " + MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_AMOUNT_INCORRENT_DESC);
			throw new MQException(MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_AMOUNT_INCORRENT, MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_AMOUNT_INCORRENT_DESC);
		}
		

//		String paymentOrderId = GenerateKey.getId(CommonConstants.PAYMENT_ORDER_ID_PREFIX, ConfigUtils.getProperty("db_id"));
		// 修改流水号生成规则
		String paymentOrderId = OrderIdRuleUtil.getId(CommonConstants.PARTNER_10001_PAYMENT_ORDER_ID_PREFIX);
		String paymentOrderNo = GenerateKey.getId(CommonConstants.PAYMENT_SEQ_PREFIX, ConfigUtils.getProperty("db_id"));
		String descp = paymentOrderId;
		String batchNo = GenerateKey.getId(CommonConstants.PAYMENT_BATCH_NO_PREFIX, ConfigUtils.getProperty("db_id"));
		
		// 支付订单信息
		UserPaymentOrder paymentOrder = new UserPaymentOrder();
		paymentOrder.setUserPaymentOrderId(paymentOrderId);//交易流水号
		paymentOrder.setPaymentOrderNo(paymentOrderNo);// 市场订单号
		paymentOrder.setBatchNo(batchNo);
	 
		paymentOrder.setPaymentType(CommonConstants.PAYMENT_TYPE_PASSIVE);// 被动支付
		paymentOrder.setPaymentChannelId(CommonConstants.PAYMENT_CHANNEL_ID_CPCN);// 第三方支付公司：中金
		paymentOrder.setUserId(repaymentPlansDTO.getUserId());// 用户id
		paymentOrder.setBankCardId(repaymentPlansDTO.getBankCardId());// 用户支付使用的绑定银行卡id
		paymentOrder.setRequestStatus(StatusConstants.REQUEST_STATUS_AWAIT);// 请求状态：10-待请求
		paymentOrder.setAccountName(repaymentPlansDTO.getOwnerName());// 账户名
		paymentOrder.setAccountNumber(repaymentPlansDTO.getCardNumber());// 账户号
		paymentOrder.setIdentificationNumber(repaymentPlansDTO.getOwnerIdCard());// 证件号
		paymentOrder.setPhoneNumber(repaymentPlansDTO.getReserveMobile());//手机号
		paymentOrder.setPaymentAmount(repaymentPlansDTO.getReceivableAmount());// 支付金额
		paymentOrder.setPaymentFee(new BigDecimal(0));// 手续费
		paymentOrder.setPaymentStatus(StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY);// 支付状态：0-待支付
		paymentOrder.setPaymentTime(null);// 实际支付时间，在支付成功时会修改
		paymentOrder.setPaymentCount(1);
		paymentOrder.setBankId(repaymentPlansDTO.getThirdPartyBankId());// 第三方银行id
		paymentOrder.setBankName(repaymentPlansDTO.getBankName());
		paymentOrder.setCreateTime(new Date());// 创建时间
		paymentOrder.setUpdateTime(null);// 修改时间
		paymentOrder.setBankTxTime(null);// 银行处理时间，在支付成功时会修改
		paymentOrder.setDescp(descp);// 描述备注

		List<UserPaymentRecordItem> list = new ArrayList<UserPaymentRecordItem>();

		UserPaymentRecordItem userPaymentRecordItem = new UserPaymentRecordItem();
		userPaymentRecordItem.setUpRecordItemId(GenerateKey.getId(CommonConstants.PAYMENT_RECORD_ITEM_ID_PREFIX, ConfigUtils.getProperty("db_id")));// 还款记录项id
		userPaymentRecordItem.setRepaymentPlansId(repaymentPlansDTO.getRepaymentPlansId()); // 还款计划id
		userPaymentRecordItem.setUserPaymentOrderId(paymentOrder.getUserPaymentOrderId()); // 用户主动和被动支付订单表id
		userPaymentRecordItem.setInstalmentId(repaymentPlansDTO.getInstalmentId());
		userPaymentRecordItem.setNeedPaymentAmount(repaymentPlansDTO.getReceivableAmount());// 记录项对应应还金额（可能此记录项对应的是部分还款）
		userPaymentRecordItem.setRealPaymentAmount(realAmount);// 实收金额（可能此记录项对应的是部分还款）
		userPaymentRecordItem.setRepaymentType(StatusConstants.PAYMENT_TYPE_PAYALL); // 还款类型：0全部还款、1部分还款.(通过实际支付金额与应支付金额判断)
		userPaymentRecordItem.setPaymentTime(null);
		userPaymentRecordItem.setCreateTime(new Date());
		userPaymentRecordItem.setPaymentStatus(StatusConstants.PAYMENT_ORDER_ITEM_PAYSTATUS_AWAIT_PAY);
		userPaymentRecordItem.setOperator(CommonConstants.DEFAULT_OPERATOR);
		list.add(userPaymentRecordItem);

		try {
			this.savePaymentOrder(paymentOrder, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("还款计划ID : " + repaymentPlansDTO.getRepaymentPlansId() + ",入库支付订单出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		//生成还款订单完毕， 将该计划更改为还款中
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("repaymentPlansId", userPaymentRecordItem.getRepaymentPlansId());
		if(repaymentPlansDTO.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY.intValue()){
			paramMap.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY);
		}else{
			paramMap.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE);
		}
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY);
		paramMap.put("updateTime", new Date());
		
		int effectedRow = 0;
		try {
			effectedRow = userRepaymentPlansDAO.updateRepaymentPlansToPaying(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("还款计划ID : " + repaymentPlansDTO.getRepaymentPlansId() + ",更新还款计划状态为支付中出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(effectedRow != 1){
			logger.error("还款计划ID : " + repaymentPlansDTO.getRepaymentPlansId() + ",更新还款计划状态为支付中失败，找不到对应还款计划或者还款计划状态有误，系统业务异常！");
			throw new MQException(MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_UPDATE_STATUS_FAIL, MQExceptionConstants.MQ_CREATE_PAYMENT_ORDER_UPDATE_STATUS_FAIL_DESC);
		}
	}

	private void savePaymentOrder(UserPaymentOrder paymentOrder, List<UserPaymentRecordItem> list) throws Exception {
		// 新增还款订单
		userPaymentOrderDAO.insertUserPaymentOrder(paymentOrder);
		//新增还款订单项
		for (UserPaymentRecordItem userPaymentRecordItem : list ) {
			userPaymentOrderDAO.insertUserPaymentRecordItem(userPaymentRecordItem);
		}
	}

	@Override
	public Long queryUserPaymentOrderTotalRow(Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userPaymentOrderDAO.queryUserPaymentOrderTotalRow(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询支付订单总数出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public List<UserPaymentOrder> queryUserPaymentOrderList(Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userPaymentOrderDAO.queryUserPaymentOrderList(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询支付订单列表出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public Map<String, Object> requestCpcnToPayOrder(UserPaymentOrder paymentOrder, boolean isSendSMS) throws MQException {
		// TODO Auto-generated method stub
		logger.info("支付订单代扣，支付订单流水号：" + paymentOrder.getUserPaymentOrderId() + "，支付订单订单号：" + paymentOrder.getPaymentOrderNo());
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//请求中金
		Tx1361Response txResponse = requestTx1361(paymentOrder);
		//查询失败
		if (!"2000".equals(txResponse.getCode())) {
			String retMsg = "代收请求失败，返回code代码：" + txResponse.getCode() + ",描述信息：" + txResponse.getMessage() + ",我方发送交易流水号："
					+ paymentOrder.getUserPaymentOrderId();
			logger.info(retMsg);
			retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			retMap.put(ResponseConstants.RETURN_DESC, retMsg);
			return retMap;
		}
		
		// 查询成功
		logger.info("[Message]=[" + txResponse.getMessage() + "]");
		logger.info("[InstitutionID]=[" + txResponse.getInstitutionID() + "]");
		logger.info("[TxSN]=[" + txResponse.getTxSN() + "]");
		logger.info("[Amount]=[" + txResponse.getAmount() + "]");
		logger.info("[Status]=[" + txResponse.getStatus() + "]");
		logger.info("[BankTxTime]=[" + txResponse.getBankTxTime() + "]");
		logger.info("[ResponseCode]=[" + txResponse.getResponseCode() + "]");
		logger.info("[ResponseMessage]=[" + txResponse.getResponseMessage() + "]");
		logger.info("[OrderNo]=[" + txResponse.getOrderNo() + "]");
		
		// 检查交易流水号是否为空
		String paymentOrderId = txResponse.getTxSN(); // 交易流水号
		if (EntityUtils.isEmpty(paymentOrderId)) {
			logger.error("代收请求通知错误，第三方通知中交易流水号为空，请检查原因,我方发送流水号：" + paymentOrder.getUserPaymentOrderId());
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
		UserPaymentOrder _paymentOrder = this.queryPaymentOrderById(paymentOrderId);
		//校验支付订单请求状态和支付状态是否正确--判断支付订单是否处理过
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
			this.updatePaymentOrderToPaying(paymentOrderId, bankTxTime, txResponse.getResponseMessage());
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
			retMap = this.dealPaymentOrderOfPayFailed(_paymentOrder, isSendSMS, bankTxTime, txResponse.getResponseMessage());
			
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
			retMap = this.dealPaymentOrderOfPaSuccess(_paymentOrder, bankTxTime, txResponse.getResponseMessage());
			
			String retCode = EntityUtils.isNotEmpty(retMap.get(ResponseConstants.RETURN_CODE))? (String) retMap.get(ResponseConstants.RETURN_CODE) : "";
			//是否需要区分重复还款
			if(ResponseConstants.SUCCESS.equals(retCode)){
				String retMsg = "中金代收同步返回状态为：代扣成功，处理'支付成功'订单成功！";
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
				retMap.put(ResponseConstants.RETURN_DESC, retMsg);
				retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS);//支付订单状态--如果要区分重复还款这里注释就好
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
	
	private Tx1361Response requestTx1361(UserPaymentOrder paymentOrder){
		logger.info("准备调用中金tx1361请求");
		// 1.创建交易请求对象
		// 中国银行需要分支行名称
		Tx1361Request tx1361Request = new Tx1361Request();

		// 执行报文处理
		logger.info("组装中金tx1361报文：开始");
		try {
			tx1361Request.setInstitutionID(ConfigUtils.getProperty("mqcash_institution_id"));
			tx1361Request.setTxSN(paymentOrder.getUserPaymentOrderId());
			tx1361Request.setOrderNo(paymentOrder.getPaymentOrderNo());
			tx1361Request.setAmount(paymentOrder.getPaymentAmount().movePointRight(2).longValue());
			tx1361Request.setBankID(paymentOrder.getBankId());
			tx1361Request.setAccountName(paymentOrder.getAccountName());
			tx1361Request.setAccountNumber(paymentOrder.getAccountNumber());
			tx1361Request.setAccountType(CpcnConstants.ACCOUNT_TYPE_PRIVATE);
			tx1361Request.setNote(paymentOrder.getDescp());
			tx1361Request.setPhoneNumber(paymentOrder.getPhoneNumber());
			tx1361Request.setIdentificationNumber(paymentOrder.getIdentificationNumber());
			tx1361Request.setIdentificationType(CpcnConstants.IDENTIFICATION_TYPE_ID_CARD);

			tx1361Request.process();
		} catch (Exception e) {
			logger.info("组装中金tx1361报文：异常！", e);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_PROCESS_REQUEST_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_PROCESS_REQUEST_EXCEPTION_DESC);
		}
		logger.info("组装中金tx1361报文：结束");
		
		logger.info("原请求xml : ");
		logger.info(tx1361Request.getRequestPlainText());
		logger.info("请求message : ");
		logger.info(tx1361Request.getRequestMessage());
		logger.info("请求signature : ");
		logger.info(tx1361Request.getRequestSignature());

		// 与支付平台进行通讯
		TxMessenger txMessenger = new TxMessenger();
		String[] respMsg = null;

		// 发送信息 并接受返回信息
		try {
			respMsg = txMessenger.send(tx1361Request.getRequestMessage(), tx1361Request.getRequestSignature());// 0:message;
		} catch (Exception e) {
			logger.error("请求中金市场订单代收交易异常!支付订单流水号：" + paymentOrder.getUserPaymentOrderId() 
					+ "，支付订单订单号：" + paymentOrder.getPaymentOrderNo()
					, e);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_EXCEPTION_DESC);
		}

		String plainText = null;
		try {
			plainText = new String(payment.tools.util.Base64.decode(respMsg[0]), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("中金市场订单代收同步返回信息Base64解码失败，解码串：" + respMsg[0]);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION_DESC);
		}
		
		logger.info("中金市场订单代收交易同步返回报文信息：");
		logger.info("[message]=[" + respMsg[0] + "]");
		logger.info("[signature]=[" + respMsg[1] + "]");
		logger.info("[plainText]=[" + plainText + "]");

		Tx1361Response txResponse = null;
		try {
			txResponse = new Tx1361Response(respMsg[0], respMsg[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("中金市场订单代收同步返回信息解析成返回对应出错，转换串：message=" + respMsg[0] + ",signature=" + respMsg[1]);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_1361_REQUEST_DECODE_EXCEPTION_DESC);
		}

		return txResponse;
	}

	@Override
	public Long updatePaymentOrderById(Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userPaymentOrderDAO.updatePaymentOrderById(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("更新支付订单出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}
	
	@Override
	public Long updatePaymentOrderByIdAndPaymentStatus(Map<String, Object> paramMap) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userPaymentOrderDAO.updatePaymentOrderByIdAndPaymentStatus(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("更新支付订单出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public UserPaymentOrder queryPaymentOrderById(String paymentOrderId) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userPaymentOrderDAO.queryPaymentOrderById(paymentOrderId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("支付订单ID : " + paymentOrderId + ",根据支付订单id查询支付订单出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public Map<String, Object> dealPaymentOrderOfPayFailed(UserPaymentOrder paymentOrder,
			boolean isSendSMS, Date bankTxTime, String msg) throws MQException {
		// TODO Auto-generated method stub
		logger.info("处理支付失败的支付订单，支付订单号ID：" + paymentOrder.getUserPaymentOrderId());
		if(EntityUtils.isEmpty(paymentOrder)){
			logger.error("处理支付失败的支付订单，参数不能为空!流水号：" + paymentOrder.getUserPaymentOrderId());
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC);
		}
		
		//代扣--被动支付，支付订单状态：未支付、处理中
		if(paymentOrder.getPaymentType().intValue() == CommonConstants.PAYMENT_TYPE_PASSIVE){
			if(StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue() != paymentOrder.getPaymentStatus()
					&& StatusConstants.PAYMENT_ORDER_STATUS_DEALING.intValue() != paymentOrder.getPaymentStatus()){
				logger.error("处理支付失败的支付订单，支付订单支付状态有误，支付订单已处理过，重复处理! 返回流水号：" + paymentOrder.getUserPaymentOrderId() + ",请求状态：" 
						+ paymentOrder.getRequestStatus() + ",支付状态：" + paymentOrder.getPaymentStatus());
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION_DESC);
			}
		}else if(paymentOrder.getPaymentType().intValue() == CommonConstants.PAYMENT_TYPE_ACTIVE){
			//主动支付，支付订单状态：未支付
			if(StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue() != paymentOrder.getPaymentStatus()){
				logger.error("处理支付失败的支付订单，支付订单支付状态有误，支付订单已处理过，重复处理! 返回流水号：" + paymentOrder.getUserPaymentOrderId() + ",请求状态：" 
						+ paymentOrder.getRequestStatus() + ",支付状态：" + paymentOrder.getPaymentStatus());
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION_DESC);
			}
		}
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//查询还款计划
		List<UserRepaymentPlans> plans = null;
		try {
			plans = userRepaymentPlansDAO.queryUserRepaymentPlansListByPaymentOrderId(paymentOrder.getUserPaymentOrderId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("根据支付订单号id（支付流水号）查询对应的还款计划列表出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(EntityUtils.isEmpty(plans)){
			logger.error("根据支付订单id找不到对应还款计划!支付订单号ID：" + paymentOrder.getUserPaymentOrderId());
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_NOT_FOUND_PLANS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_NOT_FOUND_PLANS_EXCEPTION_DESC);
		}
		
		logger.info("支付订单号ID：" + paymentOrder.getUserPaymentOrderId() + "，支付订单的还款类型：" + paymentOrder.getPaymentType());
		for (UserRepaymentPlans plan : plans) {
			logger.info("还款计划ID：" + plan.getRepaymentPlansId() + "，还款计划状态：" + plan.getPlansStatus());
			
			if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_INIT.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付失败，还款计划当前计划状态为：10初始化，状态有误，不需要修改，初始化状态不应该能还款！");
				continue;
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付失败，还款计划当前计划状态为：20待还款，状态有误，不需要修改，计划状态不应该为待还款状态！");
				continue;
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY.intValue() == plan.getPlansStatus()){
				logger.info("支付订单支付失败，还款计划当前计划状态为：30还款中，状态无误，需要修改还款中状态为待还款！");
				userRepaymentPlansService.recoverUserRepaymentPlansDealingStatus(plan);
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_COMPLETED.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付失败，还款计划当前计划状态为：40已还款，状态有误，不需要修改，已还款状态不应该在通知还款失败！");
				continue;
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付失败，还款计划当前计划状态为：50已逾期，状态有误，不需要修改，已逾期状态不应该在通知还款失败！");
				continue;
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_CANCELED.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付失败，还款计划当前计划状态为：60已取消 ，状态有误，不需要修改，已取消状态不应该在通知还款失败！");
				continue;
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_SUSPENDED.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付失败，还款计划当前计划状态为：70已中止 ，状态有误，不需要修改，已中止状态不应该在通知还款失败！");
				continue;
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_REFUND.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付失败，还款计划当前计划状态为：80待退款 ，状态有误，不需要修改，待退款状态不应该在通知还款失败！");
				continue;
			}
			
		}
			
		logger.info("修改支付订单状态为3支付失败，支付订单ID：" + paymentOrder.getUserPaymentOrderId());
		this.updatePaymentOrderToPayFailed(paymentOrder.getUserPaymentOrderId(), paymentOrder.getPaymentStatus(), bankTxTime, msg);
		
		//发送
		if(isSendSMS){
			this.sendRepaymentFailedSMS(plans.get(0).getRepaymentPlansId());
		}
		
		String retMsg = "处理支付失败的支付订单成功！支付订单号ID：" + paymentOrder.getUserPaymentOrderId();
		logger.info(retMsg);
		
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, retMsg);
		return retMap;
	}

	@Override
	public Map<String, Object> dealPaymentOrderOfPaSuccess(
			UserPaymentOrder paymentOrder, Date bankTxTime, String msg) throws MQException {
		// TODO Auto-generated method stub
		logger.info("处理支付成功的支付订单，支付订单号ID：" + paymentOrder.getUserPaymentOrderId());
		if(EntityUtils.isEmpty(paymentOrder)){
			logger.error("处理支付成功的支付订单，参数不能为空!流水号：" + paymentOrder.getUserPaymentOrderId());
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC);
		}
		
		//校验状态代扣--被动支付，支付订单状态：未支付、处理中
		if(paymentOrder.getPaymentType().intValue() == CommonConstants.PAYMENT_TYPE_PASSIVE){
			if(StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue() != paymentOrder.getPaymentStatus()
					&& StatusConstants.PAYMENT_ORDER_STATUS_DEALING.intValue() != paymentOrder.getPaymentStatus()){
				logger.error("处理支付成功的支付订单，支付订单支付状态有误，支付订单已处理过，重复处理! 返回流水号：" + paymentOrder.getUserPaymentOrderId() + ",请求状态：" 
						+ paymentOrder.getRequestStatus() + ",支付状态：" + paymentOrder.getPaymentStatus());
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION_DESC);
			}
		}else if(paymentOrder.getPaymentType().intValue() == CommonConstants.PAYMENT_TYPE_ACTIVE){
			//主动支付，支付订单状态：未支付
			if(StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY.intValue() != paymentOrder.getPaymentStatus()){
				logger.error("处理支付成功的支付订单，支付订单支付状态有误，支付订单已处理过，重复处理! 返回流水号：" + paymentOrder.getUserPaymentOrderId() + ",请求状态：" 
						+ paymentOrder.getRequestStatus() + ",支付状态：" + paymentOrder.getPaymentStatus());
				throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_PAYMENT_STATUS_EXCEPTION_DESC);
			}
		}
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		//查询还款计划
		List<UserRepaymentPlans> plans = null;
		try {
			plans = userRepaymentPlansDAO.queryUserRepaymentPlansListByPaymentOrderId(paymentOrder.getUserPaymentOrderId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("根据支付订单号id（支付流水号）查询对应的还款计划列表出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		if(EntityUtils.isEmpty(plans)){
			logger.error("根据支付订单id找不到对应还款计划!支付订单号ID：" + paymentOrder.getUserPaymentOrderId());
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_NOT_FOUND_PLANS_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_NOT_FOUND_PLANS_EXCEPTION_DESC);
		}
		
		boolean isAgain = false;
		List<String> againList = new ArrayList<String>();
		logger.info("支付订单号ID：" + paymentOrder.getUserPaymentOrderId() + "，支付订单的还款类型：" + paymentOrder.getPaymentType());
		for (UserRepaymentPlans plan : plans) {
			logger.info("还款计划ID：" + plan.getRepaymentPlansId() + "，还款计划状态：" + plan.getPlansStatus());
			
			if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_INIT.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付成功，还款计划当前计划状态为：10初始化，状态有误，不需要修改，初始化状态不应该能还款！");
				continue;
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY.intValue() == plan.getPlansStatus()){
				logger.info("支付订单支付成功，还款计划当前计划状态为：20待还款，状态无误，需要修改计划状态为40已还款状态！");
				plan.setRealRepaymentTime(bankTxTime);
				userRepaymentPlansService.updateUserRepaymentPlansWaitingToPaySuccess(plan, paymentOrder);
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY.intValue() == plan.getPlansStatus()){
				logger.info("支付订单支付成功，还款计划当前计划状态为：30还款中，状态无误，需要修改计划状态为40已还款状态！");
				plan.setRealRepaymentTime(bankTxTime);
				userRepaymentPlansService.updateUserRepaymentPlansDealingToPaySuccess(plan, paymentOrder);
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_COMPLETED.intValue() == plan.getPlansStatus()){
				logger.info("支付订单支付成功，还款计划当前计划状态为：40已还款，状态有误，需要修改，该支付订单包含重复还款，计划已还款状态不需要修改！");
				isAgain = true;
				againList.add(plan.getRepaymentPlansId());
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE.intValue() == plan.getPlansStatus()){
				logger.info("支付订单支付成功，还款计划当前计划状态为：50已逾期，状态无误，需要修改，需要修改计划状态为40已还款状态！");
				plan.setRealRepaymentTime(bankTxTime);
				userRepaymentPlansService.updateUserRepaymentPlansOverdueToPaySuccess(plan, paymentOrder);
				logger.info("支付订单支付成功，还款计划当前计划状态为：50已逾期，状态已修改为40已还款状态；判断是否需要修改分期订单状态！");
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_CANCELED.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付成功，还款计划当前计划状态为：60已取消 ，状态有误，不需要修改，已取消状态不应该在通知还款失败！");
				continue;
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_SUSPENDED.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付成功，还款计划当前计划状态为：70已中止 ，状态有误，不需要修改，已中止状态不应该在通知还款失败！");
				continue;
			}else if(StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_REFUND.intValue() == plan.getPlansStatus()){
				logger.warn("支付订单支付成功，还款计划当前计划状态为：80待退款 ，状态有误，不需要修改，待退款状态不应该在通知还款失败！");
				continue;
			}
			
			
			//处理分期：检查还款计划是否是最后一期，最后一期还款成功，分期订单状态-->分期结束
			if(EntityUtils.isNotEmpty(plan.getInstalmentCount()) 
					&& EntityUtils.isNotEmpty(plan.getInstalmentNumber()) 
					&& plan.getInstalmentCount().intValue() == plan.getInstalmentNumber().intValue()){
				//校验是否所有还款计划都还款完毕，当分期订单状态修改失败时，还款不抛异常
				userRepaymentPlansService.judgeInstalmentCompletedByPlans(plan);
			}
			
		}
		
		//判断是否需要还原分期订单状态-->待还款，如果还有逾期还款计划没有还款，则不需要改变
		this.recoverUserInstalment(plans.get(0).getInstalmentId());
			
		//如果还款计划状态为还款完毕，此笔支付订单为重复还款
		if(isAgain){
			logger.info("修改支付订单状态为50重复支付，支付订单ID：" + paymentOrder.getUserPaymentOrderId());
			this.updatePaymentOrderToPaySuccess(paymentOrder, bankTxTime, msg, againList);
			retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_PAY_AGAIN);//重复支付
		}else{
			logger.info("修改支付订单状态为30支付成功，支付订单ID：" + paymentOrder.getUserPaymentOrderId());
			this.updatePaymentOrderToPaySuccess(paymentOrder, bankTxTime, msg, againList);
			retMap.put("status", StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS);//支付成功
		}
		
		//处理还款计划：检查还款计划是否上锁（主动支付），如果上锁了，解锁 
		this.releaseUserRepaymentPlansLock(paymentOrder, plans.get(0));
		
		String retMsg = "处理支付成功的支付订单成功！支付订单号ID：" + paymentOrder.getUserPaymentOrderId();
		logger.info(retMsg);
		
		retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.SUCCESS);
		retMap.put(ResponseConstants.RETURN_DESC, retMsg);
		return retMap;
	}
	
	/**
	 * @param instalmentId
	 * 判断是否需要还原分期订单状态-->待还款，如果还有逾期还款计划没有还款，则不需要改变
	 */
	private void recoverUserInstalment(String instalmentId) {
		// TODO Auto-generated method stub
		logger.info("判断分期订单状态是否需要还原");
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		Date endTime = DateUtil.dayOfOrigin(new Date());
		queryMap.put("endTime", endTime);
		queryMap.put("instalmentId", instalmentId);
		queryMap.put("plansStatusList", new Integer[]{StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY,
				StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY,
				StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE});
		
		List<UserRepaymentPlans> overduePlans = userRepaymentPlansService.queryUserRepaymentPlansOfInstalment(queryMap);
		
		if(overduePlans == null || overduePlans.size() == 0){
			logger.info("分期没有逾期或者逾期还款中的还款计划，恢复分期状态！分期ID：" + instalmentId);
			UserInstalment instalment = userInstalmentService.queryUserInstalmentById(instalmentId);
			if(StatusConstants.INSTALMENT_STATUS_AWAIT_PAY.equals(instalment.getInstalmentStatus())){
				logger.info("分期订单当前待还款，无需还原分期订单状态");
				
			}else if(StatusConstants.INSTALMENT_STATUS_OVERDUE.equals(instalment.getInstalmentStatus())){
				logger.info("分期订单当前已逾期，需还原分期订单状态-->待还款");
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("instalmentId", instalmentId);
				paramMap.put("instalmentStatus", StatusConstants.INSTALMENT_STATUS_AWAIT_PAY);
				paramMap.put("beforeInstalmentStatus", StatusConstants.INSTALMENT_STATUS_OVERDUE);
				
				long row = userInstalmentService.updateInstalmentStatus(paramMap);
			}else if(StatusConstants.INSTALMENT_STATUS_COMPLETED.equals(instalment.getInstalmentStatus())){
				logger.info("分期订单当前已经结清，无需还原分期订单状态");
				
			}
		}else{
			logger.info("分期有逾期或者逾期还款中的还款计划，无法恢复分期状态！分期ID：" + instalmentId);
		}
	}

	@Override
	public void updatePaymentOrderToPaying(String paymentOrderId, Date bankTxTime, String msg)
			throws MQException {
		logger.info("修改支付订单状态为处理中，返回流水号：" + paymentOrderId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userPaymentOrderId", paymentOrderId);
		paramMap.put("requestStatus", StatusConstants.REQUEST_STATUS_SUCCESS);//请求状态：请求成功
		paramMap.put("paymentStatus", StatusConstants.PAYMENT_ORDER_STATUS_DEALING);//支付状态：处理中
		paramMap.put("paymentTime", bankTxTime);
		paramMap.put("bankTxTime", bankTxTime);
		paramMap.put("updateTime", new Date());
		paramMap.put("descp", msg);
		
		long row = this.updatePaymentOrderById(paramMap);
		if(row != 1){
			logger.error("根据支付订单id修改支付订单为'处理中'失败! 返回流水号：" + paymentOrderId + "，修改行数：" + row);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_DEALING_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_DEALING_EXCEPTION_DESC);
		}
	}
	
	@Override
	public void updatePaymentOrderToPayFailed(String paymentOrderId, Integer beforeStatus, Date bankTxTime, String msg) throws MQException {
		// TODO Auto-generated method stub
		logger.info("修改支付订单状态为支付失败，返回流水号：" + paymentOrderId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userPaymentOrderId", paymentOrderId);
		paramMap.put("beforeStatus", beforeStatus);
		paramMap.put("requestStatus", StatusConstants.REQUEST_STATUS_SUCCESS);//请求状态：请求成功
		paramMap.put("paymentStatus", StatusConstants.PAYMENT_ORDER_STATUS_PAY_FAILED);//支付状态：2支付成功
		paramMap.put("paymentTime", bankTxTime);
		paramMap.put("bankTxTime", bankTxTime);
		paramMap.put("updateTime", new Date());
		paramMap.put("descp", msg);
		
		long row = this.updatePaymentOrderByIdAndPaymentStatus(paramMap);
		if(row != 1){
			logger.error("根据支付订单id修改支付订单为'处理失败'失败! 返回流水号：" + paymentOrderId + "，修改行数：" + row);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_PAYFAILED_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_PAYFAILED_EXCEPTION_DESC);
		}
	}

	@Override
	public void updatePaymentOrderToPaySuccess(UserPaymentOrder paymentOrder, Date bankTxTime, String msg, List<String> againList) throws MQException {
		// TODO Auto-generated method stub
		String paymentOrderId = paymentOrder.getUserPaymentOrderId();
		Integer beforeStatus = paymentOrder.getPaymentStatus();
		
		logger.info("修改支付订单状态为支付成功，支付订单号ID：" + paymentOrderId);
		//处理支付订单
		Integer paymentStatus = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userPaymentOrderId", paymentOrderId);
		paramMap.put("beforeStatus", beforeStatus);
		paramMap.put("requestStatus", StatusConstants.REQUEST_STATUS_SUCCESS);//请求状态：请求成功
		if(EntityUtils.isNotEmpty(againList)){
			logger.info("支付订单中有重复还款订单项，状态为重复付款，支付订单号ID：" + paymentOrderId);
			paymentStatus = StatusConstants.PAYMENT_ORDER_STATUS_PAY_AGAIN;//支付状态：5重复付款
		}else{
			logger.info("支付订单中没有重复还款订单项，状态为支付成功，支付订单号ID：" + paymentOrderId);
			paymentStatus = StatusConstants.PAYMENT_ORDER_STATUS_PAY_SUCCESS;//支付状态：2支付成功
		}
		paramMap.put("paymentStatus", paymentStatus);//支付状态
		paramMap.put("paymentTime", bankTxTime);
		paramMap.put("bankTxTime", bankTxTime);
		paramMap.put("updateTime", new Date());
		paramMap.put("descp", msg);
		
		// 微信支付需要回填银行信息
		if (CommonConstants.PAYMENT_CHANNEL_ID_WX.equals(paymentOrder.getPaymentChannelId())
				&& CommonConstants.PAYMENT_TYPE_WX_NATIVE.equals(paymentOrder.getPaymentType())) {
			paramMap.put("bankId", paymentOrder.getBankId());
			paramMap.put("bankName", paymentOrder.getBankName());
		}
		
		long row = this.updatePaymentOrderByIdAndPaymentStatus(paramMap);
		if(row != 1){
			logger.error("根据支付订单id修改支付订单为'支付成功'失败! 返回流水号：" + paymentOrderId + "，修改行数：" + row);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_PAYFAILED_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_PAYFAILED_EXCEPTION_DESC);
		}
		
		//处理支付订单项
		List<UserPaymentRecordItem> recordItems = this.queryUserPaymentRecordItemListByPaymentOrderId(paymentOrderId);
		if(EntityUtils.isNotEmpty(recordItems)){
			for (UserPaymentRecordItem recordItem : recordItems) {
				if(againList.contains(recordItem.getRepaymentPlansId())){
					logger.info("该支付订单项为重复还款-->重复还款，支付订单项ID：" + recordItem.getRepaymentPlansId());
					recordItem.setPaymentStatus(StatusConstants.PAYMENT_ORDER_ITEM_PAYSTATUS_PAY_AGAIN);//重复还款
				}else{
					logger.info("该支付订单项为正常还款-->支付成功，支付订单项ID：" + recordItem.getRepaymentPlansId());
					recordItem.setPaymentStatus(StatusConstants.PAYMENT_ORDER_ITEM_PAYSTATUS_PAY_SUCCESS);//支付成功
				}
				recordItem.setUpdateTime(new Date());
				recordItem.setPaymentTime(bankTxTime);
				
				long irow = 0;
				try {
					irow = userPaymentOrderDAO.updatePaymentOrderItemById(recordItem);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("更新支付订单项出错，数据库异常！订单项ID：" + recordItem.getUpRecordItemId(), e);
					throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
				}
				
				if(irow != 1){
					logger.error("根据支付订单项id修改支付订单项为'支付成功'失败! 返回流水号：" + paymentOrderId + "，修改行数：" + row);
					throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_PAYFAILED_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_MQ_UPDATE_PAYMENT_ORDER_STATUS_PAYFAILED_EXCEPTION_DESC);
				}
			}
		}
		
		//入t_user_repayment_order 用户还款订单:聚合所有支付方式成功支付还款的订单
		/* 作废
		try {
			UserRepaymentOrder userRepaymentOrder = new UserRepaymentOrder();
			userRepaymentOrder.setRepaymentOrderId(GenerateKey.getId(CommonConstants.REPAYMENT_ORDER_ID_PREFIX, ConfigUtils.getProperty("db_id")));
			userRepaymentOrder.setUserPaymentOrderId(paymentOrderId);
			userRepaymentOrder.setCreateTime(new Date());
			userRepaymentOrder.setPaymentStatus(paymentStatus);
			userRepaymentOrder.setPaymentTime(bankTxTime);
			userRepaymentOrderDAO.addUserRepaymentOrderFromPayment(userRepaymentOrder);
			logger.info("插入还款成功订单聚合表成功,关联还款订单表表id为：" + paymentOrderId);
		} catch (Exception e) {
			logger.error("插入还款成功订单聚合表失败,关联还款订单表表id为：" + paymentOrderId, e);
			throw new MQException(MQExceptionConstants.MQ_REQUEST_CPCN_INSERT_REPAYMENT_ORDER_EXCEPTION, MQExceptionConstants.MQ_REQUEST_CPCN_INSERT_REPAYMENT_ORDER_EXCEPTION_DESC);
		}
		*/
	}

	@Override
	public List<UserPaymentRecordItem> queryUserPaymentRecordItemListByPaymentOrderId(
			String paymentOrderId) throws MQException {
		// TODO Auto-generated method stub
		try {
			return userPaymentOrderDAO.queryUserPaymentRecordItemListByPaymentOrderId(paymentOrderId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询支付订单总数出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
	}

	@Override
	public void releaseUserRepaymentPlansLock(UserPaymentOrder paymentOrder, String instalmentId)
			throws MQException {
		// TODO Auto-generated method stub
		logger.info("处理代收锁，判断是否需要解除代收锁，支付订单ID：" + paymentOrder.getUserPaymentOrderId() + "，支付订单类型：" + paymentOrder.getPaymentType() + "，分期订单ID：" + instalmentId);
		
		if(paymentOrder.getPaymentType().intValue() == CommonConstants.PAYMENT_TYPE_PASSIVE){
			//被动支付
			logger.info("代收成功会解除代收锁");
			userRepaymentPlansService.releaseUserRepaymentPlansLock(instalmentId);
		}else if(paymentOrder.getPaymentType().intValue() == CommonConstants.PAYMENT_TYPE_ACTIVE){
			//主动支付
			logger.info("主动支付成功会解除代收锁");
			userRepaymentPlansService.releaseUserRepaymentPlansLock(instalmentId);
		}
	}

	@Override
	public boolean sendRepaymentFailedSMS(String plansId) throws MQException {
		// TODO Auto-generated method stub
		logger.info("根据还款计划ID，发送代收失败短信，计划ID：" + plansId);
		UserForSendSmDTO smDTO = null;
		try {
			smDTO = userRepaymentPlansDAO.queryPlanToRemindByPlanId(plansId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("根据还款计划ID，查询代收失败发送短信需要的信息出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		if(EntityUtils.isEmpty(smDTO)){
			logger.error("根据还款计划ID，查询代收失败发送短信需要的信息有误，查询不到相关信息！");
			throw new MQException(MQExceptionConstants.MQ_RUNTIME_EXCEPTION, MQExceptionConstants.MQ_RUNTIME_EXCEPTION_DESC);
		}
		
		SmOrder smOrder = new SmOrder();
		smOrder.setSmOrderId(UUIDTools.getFormatUUID());
		smOrder.setMobileNumber(smDTO.getBindMobile());
		String cardNumber = EntityUtils.isNotEmpty(smDTO.getCardNumber()) ? smDTO.getCardNumber().substring(smDTO.getCardNumber().length()-4, smDTO.getCardNumber().length()) : ""; 

		String dataStr = smDTO.getUserName() + CommonConstants.SM_DATA_SEPARATOR
					+ smDTO.getReceivableAmount() + CommonConstants.SM_DATA_SEPARATOR
					+ configKvService.get(CommonConstants.MQ_CASH_PORT_URL)
					+ cardNumber ;
		smOrder.setData(dataStr);
		smOrder.setSendType(StatusConstants.SEND_SMS_TYPE_NOW);
		smOrder.setBookTime(DateUtil.getCurDate());
		smOrder.setTemplateId(ConfigUtils.getProperty("repay_fail_template_id"));
		smOrder.setCreateTime(DateUtil.getCurDate());
		smOrder.setUpdateTime(DateUtil.getCurDate());
		smOrder.setOperator("system");
		
		smOrderService.insertSmOrder(smOrder);
		
		return false;
	}

	@Override
	public void releaseUserRepaymentPlansLock(UserPaymentOrder paymentOrder,
			UserRepaymentPlans plan) throws MQException {
		// TODO Auto-generated method stub
		if(EntityUtils.isEmpty(plan.getRepayLock()) || plan.getRepayLock() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_REPAY_UNLOCK.intValue()){
			logger.info("还款计划没有被锁定，表示该分期单对应还款计划没有被锁定，无需解锁！分期订单ID：" + plan.getInstalmentId() + "，还款计划ID：" + plan.getRepaymentPlansId());
		}else{
			logger.info("还款计划被锁定，表示该分期单对应还款计划被锁定，需解锁！分期订单ID：" + plan.getInstalmentId() + "，还款计划ID：" + plan.getRepaymentPlansId());
			this.releaseUserRepaymentPlansLock(paymentOrder, plan.getInstalmentId());
		}
	}

	@Override
	public Map<String, Object> requestCpcnToPayInstalment(String instalmentId,
			boolean isSendSMS) throws MQException {
		Map<String, Object> map=new HashMap<String, Object>();
		String msg = "";
		logger.info("主动代收逾期的分期单，id="+instalmentId);
		
		//校验分期单
		UserInstalment instalment=userInstalmentService.queryUserInstalmentById(instalmentId);
		if(instalment==null || !instalment.getInstalmentStatus().equals(StatusConstants.INSTALMENT_STATUS_OVERDUE)){
			logger.info("查询分期单:id="+instalmentId+",status="+(instalment==null?"null":instalment.getInstalmentStatus()));
			msg="分期单未逾期，不能进行主动代收！";
			map.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			map.put(ResponseConstants.RETURN_DESC, msg);
			return map;
		}
		
		//校验分期计划
		Map paramMap=new HashMap();
		paramMap.put("instalmentId", instalmentId);
		paramMap.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE);
		List<UserRepaymentPlansDTO> overdues=userRepaymentPlansService.queryOverdueUserRepaymentPlansList(paramMap);
		if(overdues==null || overdues.size()==0){
			logger.info("该分期单没有逾期的分期计划");
			msg="该分期单没有逾期的分期计划！";
			map.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			map.put(ResponseConstants.RETURN_DESC, msg);
			return map;
		}
		logger.info("逾期的分期计划数量："+overdues.size());
		
		//查询银行卡信息
		UserBankCard bankCard=userBankCardDAO.queryBankCardByKey(instalment.getBankCardId());
		
		//生成还款单、账单
//		String paymentOrderId = GenerateKey.getId(CommonConstants.PAYMENT_ORDER_ID_PREFIX, ConfigUtils.getProperty("db_id"));
		// 修改流水号生成规则
		String paymentOrderId = OrderIdRuleUtil.getId(CommonConstants.PARTNER_10001_PAYMENT_ORDER_ID_PREFIX);
		String paymentOrderNo = GenerateKey.getId(CommonConstants.PAYMENT_SEQ_PREFIX, ConfigUtils.getProperty("db_id"));
		
		String descp = paymentOrderId;
		String batchNo = GenerateKey.getId(CommonConstants.PAYMENT_BATCH_NO_PREFIX, ConfigUtils.getProperty("db_id"));
		
		UserPaymentOrder paymentOrder = new UserPaymentOrder();
		paymentOrder.setUserPaymentOrderId(paymentOrderId);//交易流水号
		paymentOrder.setPaymentOrderNo(paymentOrderNo);// 市场订单号
		paymentOrder.setBatchNo(batchNo);
	 
		paymentOrder.setPaymentType(CommonConstants.PAYMENT_TYPE_PASSIVE);// 被动支付
		paymentOrder.setPaymentChannelId(CommonConstants.PAYMENT_CHANNEL_ID_CPCN);// 第三方支付公司：中金
		paymentOrder.setUserId(instalment.getUserId());// 用户id
		paymentOrder.setBankCardId(instalment.getBankCardId());// 用户支付使用的绑定银行卡id
		paymentOrder.setRequestStatus(StatusConstants.REQUEST_STATUS_AWAIT);// 请求状态：10-待请求
		paymentOrder.setAccountName(bankCard.getOwnerName());// 账户名
		paymentOrder.setAccountNumber(bankCard.getCardNumber());// 账户号
		paymentOrder.setIdentificationNumber(bankCard.getOwnerIdCard());// 证件号
		paymentOrder.setPhoneNumber(bankCard.getReserveMobile());//手机号
		paymentOrder.setPaymentFee(new BigDecimal(0));// 手续费
		paymentOrder.setPaymentStatus(StatusConstants.PAYMENT_ORDER_STATUS_AWAIT_PAY);// 支付状态：0-待支付
		paymentOrder.setPaymentTime(null);// 实际支付时间，在支付成功时会修改
		paymentOrder.setPaymentCount(overdues.size());
		paymentOrder.setBankId(bankCard.getThirdPartyBankId());// 第三方银行id
		paymentOrder.setBankName(bankCard.getBankName());
		paymentOrder.setCreateTime(new Date());// 创建时间
		paymentOrder.setUpdateTime(null);// 修改时间
		paymentOrder.setBankTxTime(null);// 银行处理时间，在支付成功时会修改
		paymentOrder.setDescp(descp);// 描述备注

		BigDecimal paymentAmount=new BigDecimal(0);
		List<UserPaymentRecordItem> list = new ArrayList<UserPaymentRecordItem>();
		for(UserRepaymentPlansDTO plan:overdues){
			//计算还款单金额
			paymentAmount=paymentAmount.add(plan.getReceivableAmount());
			
			UserPaymentRecordItem userPaymentRecordItem = new UserPaymentRecordItem();
			userPaymentRecordItem.setUpRecordItemId(GenerateKey.getId(CommonConstants.PAYMENT_RECORD_ITEM_ID_PREFIX, ConfigUtils.getProperty("db_id")));// 还款记录项id
			userPaymentRecordItem.setRepaymentPlansId(plan.getRepaymentPlansId()); // 还款计划id
			userPaymentRecordItem.setUserPaymentOrderId(paymentOrder.getUserPaymentOrderId()); // 用户主动和被动支付订单表id
			userPaymentRecordItem.setInstalmentId(instalment.getInstalmentId());
			userPaymentRecordItem.setNeedPaymentAmount(plan.getReceivableAmount());// 记录项对应应还金额（本金+服务费+逾期-减免）
			userPaymentRecordItem.setRealPaymentAmount(plan.getReceivableAmount());// 实收金额
			userPaymentRecordItem.setRepaymentType(StatusConstants.PAYMENT_TYPE_PAYALL); // 还款类型：0全部还款、1部分还款.(通过实际支付金额与应支付金额判断)
			userPaymentRecordItem.setPaymentTime(null);
			userPaymentRecordItem.setCreateTime(new Date());
			userPaymentRecordItem.setPaymentStatus(StatusConstants.PAYMENT_ORDER_ITEM_PAYSTATUS_AWAIT_PAY);
			userPaymentRecordItem.setOperator(CommonConstants.DEFAULT_OPERATOR);
			list.add(userPaymentRecordItem);
			
			// 将该计划更改为还款中
			Map<String, Object> pm = new HashMap<String, Object>();
			pm.put("repaymentPlansId", userPaymentRecordItem.getRepaymentPlansId());
			if(plan.getPlansStatus() == StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY.intValue()){
				pm.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_AWAIT_PAY);
			}else{
				pm.put("beforeStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_OVERDUE);
			}
			pm.put("plansStatus", StatusConstants.INSTALMENT_REPAYMENT_PLANS_STATUS_DOING_PAY);
			pm.put("updateTime", new Date());
			
			try {
				userRepaymentPlansDAO.updateRepaymentPlansToPaying(pm);
			} catch (Exception e) {
				logger.error("还款计划ID : " + plan.getRepaymentPlansId() + ",更新还款计划状态为支付中出错，数据库异常！", e);
				throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
			}
		}
		
		try {
			paymentOrder.setPaymentAmount(paymentAmount);// 支付金额
			this.savePaymentOrder(paymentOrder, list);
		} catch (Exception e) {
			logger.error("还款单ID : " + paymentOrder.getUserPaymentOrderId() + ",入库支付订单出错，数据库异常！", e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		return this.requestCpcnToPayOrder(paymentOrder,true);
	}
}
