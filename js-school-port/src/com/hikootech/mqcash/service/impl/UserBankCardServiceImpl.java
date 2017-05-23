package com.hikootech.mqcash.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.ZhongjinConstants;
import com.hikootech.mqcash.dao.ConfigBankDAO;
import com.hikootech.mqcash.dao.UserBankCardDAO;
import com.hikootech.mqcash.dto.BankDTO;
import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.InstalmentValidateCardLimit;
import com.hikootech.mqcash.po.RequestValidateBankCardLog;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.po.UserValidateCardLimit;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.UserBankCardService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.IpUtils;
import com.hikootech.mqcash.util.UUIDTools;

import payment.api.system.TxMessenger;
import payment.api.tx.paymentbinding.Tx2531Request;
import payment.api.tx.paymentbinding.Tx2531Response;
import payment.api.tx.paymentbinding.Tx2532Request;
import payment.api.tx.paymentbinding.Tx2532Response;
import payment.tools.util.StringUtil;

@Service("userBankCardService")
public class UserBankCardServiceImpl implements UserBankCardService {
	
	private static Logger log = LoggerFactory.getLogger(UserBankCardServiceImpl.class);
	
	@Autowired
	private UserBankCardDAO userBankCardDAO;
	@Autowired
	private ConfigBankDAO configBankDAO;
	@Autowired
	private ConfigKvService configKvService;

	@Override
	public Map<String, Object> bindBankCard(UserBankCardDTO bankCardDTO) {
		// TODO Auto-generated method stub
		log.info("用户绑定银行卡（短信验证），userid : " + bankCardDTO.getUserId() + ",smCode :" + bankCardDTO.getSmCode());
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		RequestValidateBankCardLog bankCardlog  = new RequestValidateBankCardLog();
    	try {
    		
    		//插入绑定银行卡日志表t_log_request_validate_bank_card
        	bankCardlog.setBankCardLogId(GenerateKey.getId(CommonConstants.LOG_REQUEST_VALIDATE_BANK_ID_PREFIX, ConfigUtils.getProperty("db_id")));
        	bankCardlog.setOperationType(StatusConstants.BANK_CARD_OPERATION_BIND);
        	bankCardlog.setBankCardId(bankCardDTO.getBankCardId());
        	bankCardlog.setThirdParyBindingId(bankCardDTO.getThirdParyBindingId());
        	bankCardlog.setConfigBankId(bankCardDTO.getRelationBankId());
        	bankCardlog.setCustIp(bankCardDTO.getCustIp());
        	bankCardlog.setCustIpInt(IpUtils.ipToInt(bankCardDTO.getCustIp()));
        	bankCardlog.setUserId(bankCardDTO.getUserId());
        	bankCardlog.setOwnerIdCard(bankCardDTO.getOwnerIdCard());
        	bankCardlog.setOwnerName(bankCardDTO.getOwnerName());
        	bankCardlog.setCardType(bankCardDTO.getCardType());
        	bankCardlog.setReserveMobile(bankCardDTO.getReserveMobile());
        	bankCardlog.setCardNumber(bankCardDTO.getCardNumber());
        	bankCardlog.setVerifyStatus(null);
        	bankCardlog.setStatus(null);
        	bankCardlog.setBindStatus(null);
    		bankCardlog.setBankTxTime(null);
    		bankCardlog.setIssInsCode(null);
    		bankCardlog.setPayCardType(null);
    		bankCardlog.setCode(null);
    		bankCardlog.setMsg(null);
    		bankCardlog.setResponseCode(null);
        	bankCardlog.setResponseMsg(null);
        	bankCardlog.setCreateTime(new Date());
        	bankCardlog.setUpdateTime(new Date());
        	userBankCardDAO.insertRequestValidateBankCardLog(bankCardlog);
        	
		} catch (Exception e) {
			e.printStackTrace();
			log.error("绑定银行卡接口-->插入绑卡日志记录异常："  + ",idCard：" + bankCardDTO.getOwnerIdCard() + ",ownerName" + bankCardDTO.getOwnerName());
			retMap.put("code", ResponseConstants.FAIL);
			retMap.put("desc", "绑定失败！");
			return retMap;
		}
    	
		//点击绑卡次数是否超过最大限制
		if(!validateCardLimitCount(bankCardDTO,bankCardlog)){
			String customerTel = configKvService.get(CommonConstants.CUSTOMER_SERVICE_TEL);
			retMap.put("code", ResponseConstants.FAIL);
			retMap.put("desc", "绑定次数超限,需联系秒趣客服（"+ customerTel +"）处理。");
			return retMap;
		}
    	
		//校验卡类型、卡号、持卡人身份证号、预留手机号
		
		//调用中鑫第三方支付的接口校验用户银行卡信息
		//调用中鑫第三方支付的接口校验用户预留手机号
		//1.组成报文XML格式字符串
		//2.对原报文进行base64编码
		//3.对原报文进行签名，得到签名结果（十六进制字符串），算法SHA1wihRSA
		//创建交易请求对象
        Tx2532Request txRequest = new Tx2532Request();

        txRequest.setInstitutionID(ConfigUtils.getProperty("mqcash_institution_id"));
        txRequest.setTxSNBinding(bankCardDTO.getThirdParyBindingId());
        txRequest.setSMSValidationCode(bankCardDTO.getSmCode());
		
        // 3.执行报文处理
        try {
			txRequest.process();
		} catch (Exception e) {
			log.error("组装请求中金报文失败",e);
			throw new RuntimeException("组装请求中金报文失败",e);
		}
        
        log.debug("原请求xml : ");
		log.debug(txRequest.getRequestPlainText());
		log.debug("请求message : ");
		log.debug(txRequest.getRequestMessage());
		log.debug("请求signature : ");
		log.debug(txRequest.getRequestSignature());
        
        // 5.与支付平台进行通讯
        TxMessenger txMessenger = new TxMessenger();
        String[] respMsg=null;
		try {
			respMsg = txMessenger.send(txRequest.getRequestMessage(), txRequest.getRequestSignature());
		} catch (Exception e) {
			log.error("向中金发送报文失败",e);
			throw new RuntimeException("向中金发送报文失败",e);
		}
        
        String plainText="";
		try {
			plainText = new String(payment.tools.util.Base64.decode(respMsg[0]), StringUtil.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			log.error("转化中金返回报文失败",e);
			throw new RuntimeException("转化中金返回报文失败",e);
		}
        
        log.info("[message]=[" + respMsg[0] + "]");
        log.info("[signature]=[" + respMsg[1] + "]");
        log.info("[plainText]=[" + plainText + "]");
        
        // 6.解析响应报文
      //是否需要校验第三方返回的绑定流水号是否正确（被修改）、根据返回卡片类型校验卡片类型
        Tx2532Response tx2532Response=null;
		try {
			tx2532Response = new Tx2532Response(respMsg[0], respMsg[1]);
		} catch (Exception e) {
			log.error("构造tx2532Response对象失败",e);
			throw new RuntimeException("构造tx2532Response对象失败",e);
		}
    	
        if ("2000".equals(tx2532Response.getCode())) {  // 处理成功
        	log.debug("[Message]=[" + tx2532Response.getMessage() + "]"); 
        	log.debug("[InstitutionID]=[" + tx2532Response.getInstitutionID() + "]");
        	log.debug("[TxSNBinding]=[" + tx2532Response.getTxSNBinding() + "]");
        	log.debug("[VerifyStatus]=[" + tx2532Response.getVerifyStatus()+ "]");
        	log.debug("[Status]=[" + tx2532Response.getStatus() + "]");
        	log.debug("[BankTxTime]=[" + tx2532Response.getBankTxTime() + "]");
        	log.debug("[ResponseMessage]=[" + tx2532Response.getResponseMessage() + "]");
        	log.debug("[ResponseCode]=[" + tx2532Response.getResponseCode() + "]");
        	log.debug("[IssInsCode]=[" + tx2532Response.getIssInsCode() + "]");
        	log.debug("[PayCardType]=[" + tx2532Response.getPayCardType() + "]");
        	
        	String showMsg = tx2532Response.getResponseMessage();
        	if(EntityUtils.isEmpty(showMsg)){
        		showMsg = "绑定失败，请重新获取验证码！";
        	}
        	
        	if(20 == tx2532Response.getVerifyStatus()){
				log.error("第三方支付绑定银行卡验证并绑定，验证失败，短信验证码超时！");
				retMap.put("code", ResponseConstants.FAIL);
				retMap.put("desc", "验证码超时，请重新获取验证码！");
//				retMap.put("desc", showMsg);
			}else if(30 == tx2532Response.getVerifyStatus()){
				log.error("第三方支付绑定银行卡验证并绑定，验证失败，短信验证码不正确！");
				retMap.put("code", ResponseConstants.FAIL);
				retMap.put("desc", "验证码未通过，请重新获取验证码！");
//				retMap.put("desc", showMsg);
			}else if(40 == tx2532Response.getVerifyStatus()){
				log.info("第三方支付绑定银行卡验证并绑定，验证码通过！");
				if(10 == tx2532Response.getStatus()){
					log.info("第三方支付绑定银行卡验证并绑定，绑定处理中！");
					bankCardDTO.setBindStatus(StatusConstants.USER_BANK_CARD_BIND_DOING);//需要一个定时调度去查询帮顶中的银行卡绑定结果
					retMap.put("code", ResponseConstants.FAIL);
//					retMap.put("desc", "银行系统延迟，请稍候重试！");
					retMap.put("desc", showMsg);
				}else if(20 == tx2532Response.getStatus()){
					log.error("第三方支付绑定银行卡验证并绑定，绑定失败！");
					bankCardDTO.setBindStatus(StatusConstants.USER_BANK_CARD_BIND_FAIL);
					retMap.put("code", ResponseConstants.FAIL);
//					retMap.put("desc", "绑定失败！");
					retMap.put("desc", showMsg);
				}else if(30 == tx2532Response.getStatus()){
					log.info("第三方支付绑定银行卡验证并绑定，绑定成功！");
					bankCardDTO.setBindStatus(StatusConstants.USER_BANK_CARD_BIND);
					retMap.put("code", ResponseConstants.SUCCESS);
					retMap.put("desc", "绑定成功！");
				}else{
					log.info("第三方支付绑定银行卡验证并绑定，未知异常！");
					bankCardDTO.setBindStatus(StatusConstants.USER_BANK_CARD_BIND_FAIL);
					retMap.put("code", ResponseConstants.FAIL);
					retMap.put("desc", "绑定失败，请重新获取验证码！");
				}
			}
        }else{
        	log.error("第三方支付绑定银行卡验证并绑定失败" + tx2532Response.getMessage());
        	bankCardDTO.setBindStatus(StatusConstants.USER_BANK_CARD_BIND_FAIL);
			retMap.put("code", ResponseConstants.FAIL);
			retMap.put("desc", "绑定失败，请重新获取验证码！");
        }	
		
        bankCardDTO.setBankCardId(GenerateKey.getId(CommonConstants.BANK_CARD_ID_PREFIX, ConfigUtils.getProperty("db_id")));
        bankCardDTO.setCreateTime(new Date());
//		bankCardVO.setBindStatus(StatusConstants.USER_BANK_CARD_BIND);
		
		//在验证通过的情况下增加用户银行卡,且返回成功信息
		if(retMap.get("code").equals(ResponseConstants.SUCCESS)){
			//绑定银行卡
			try {
				userBankCardDAO.insertUserBankCard(bankCardDTO);
				retMap.put("bankCardId", bankCardDTO.getBankCardId());
			} catch (Exception e) {
				log.error("增加绑定银行卡失败",e);
				bankCardDTO.setBindStatus(StatusConstants.USER_BANK_CARD_BIND_FAIL);
				retMap.put("code", ResponseConstants.FAIL);
				retMap.put("desc", "绑定失败，请重新获取验证码！");
			}
		}
		
		try {	//插入绑定银行卡日志表
	    	bankCardlog.setVerifyStatus(tx2532Response.getVerifyStatus());
	    	Integer bindStatus =StatusConstants.USER_BANK_CARD_BIND_FAIL ;
	    	if (40 == tx2532Response.getVerifyStatus() ) {
	    		if(10 == tx2532Response.getStatus()){
	    			bindStatus = StatusConstants.USER_BANK_CARD_BIND_DOING; //绑定中
				}else if(20 == tx2532Response.getStatus()){
					bindStatus = StatusConstants.USER_BANK_CARD_BIND_FAIL; //绑定失败
				}else if(30 == tx2532Response.getStatus()){
					bindStatus = StatusConstants.USER_BANK_CARD_BIND; //绑定成功
				}else{
					bindStatus =StatusConstants.USER_BANK_CARD_BIND_FAIL; //绑定失败
				}
			}
	    	bankCardlog.setStatus(tx2532Response.getStatus());
			bankCardlog.setBindStatus(bindStatus);
			if (EntityUtils.isNotEmpty(tx2532Response.getBankTxTime())) {
				try {
					bankCardlog.setBankTxTime(DateUtil.transStrToDate(tx2532Response.getBankTxTime(), "yyyyMMddHHmmss"));
				} catch (ParseException e) {
					 log.error("中金返回银行处理时间转换格式失败");
				}
			}else{
				bankCardlog.setBankTxTime(null);
			}
			
			bankCardlog.setIssInsCode(tx2532Response.getIssInsCode());
			bankCardlog.setPayCardType((tx2532Response.getPayCardType() != null && !"".equals(tx2532Response.getPayCardType()) ?Integer.parseInt(tx2532Response.getPayCardType()) : null));
	    	
			bankCardlog.setCode(tx2532Response.getCode());
			bankCardlog.setMsg(tx2532Response.getMessage());
			bankCardlog.setResponseCode(tx2532Response.getResponseCode());
	    	bankCardlog.setResponseMsg(tx2532Response.getResponseMessage());
	    	bankCardlog.setUpdateTime(new Date());
    	
	    	userBankCardDAO.updateRequestBindBankCardLog(bankCardlog);
		} catch (Exception e) {
			log.error("增加请求绑定银行卡记录表失败",e);
		}
		
		return retMap;
	}

	@Override
	public Map<String, Object> requestBindBankCard(UserBankCardDTO bankCardVO) {
		log.info("请求第三方绑定用户银行卡（发送短信校验），idCard：" + bankCardVO.getOwnerIdCard());
		Map<String, Object> retMap=new HashMap<String,Object>();
		//获取银行信息、校验银行卡是否属于该银行
		BankDTO bankDTO=null;
		try {
			bankDTO = configBankDAO.getBankById(bankCardVO.getRelationBankId());
		} catch (Exception e) {
			 log.error("根据主键查询银行卡相关信息失败",e);
			 throw new RuntimeException("根据主键查询银行卡相关信息失败",e);
		}
		if(EntityUtils.isEmpty(bankDTO)){
			log.error("请求第三方绑定用户银行卡失败，找不到对应的银行，relationBankId：" + bankCardVO.getRelationBankId());
			throw new RuntimeException("请求第三方绑定用户银行卡失败，找不到对应的银行，relationBankId：" + bankCardVO.getRelationBankId());
		}
		
		RequestValidateBankCardLog bankCardlog  = new RequestValidateBankCardLog();
		try {
			//插入绑定银行卡日志表
	    	bankCardlog.setBankCardLogId(GenerateKey.getId(CommonConstants.LOG_REQUEST_VALIDATE_BANK_ID_PREFIX, ConfigUtils.getProperty("db_id")));
	    	bankCardlog.setOperationType(StatusConstants.BANK_CARD_OPERATION_VALIDATE);
	    	bankCardlog.setCustIp(bankCardVO.getCustIp());
	    	bankCardlog.setCustIpInt(IpUtils.ipToInt(bankCardVO.getCustIp()));
	    	bankCardlog.setBankCardId(bankCardVO.getBankCardId());
	    	bankCardlog.setThirdParyBindingId(bankCardVO.getThirdParyBindingId());
	    	bankCardlog.setConfigBankId(bankCardVO.getRelationBankId());
	    	bankCardlog.setUserId(bankCardVO.getUserId());
	    	bankCardlog.setOwnerIdCard(bankCardVO.getOwnerIdCard());
	    	bankCardlog.setOwnerName(bankCardVO.getOwnerName());
	    	bankCardlog.setCardType(bankCardVO.getCardType());
	    	bankCardlog.setCardNumber(bankCardVO.getCardNumber());
	    	bankCardlog.setReserveMobile(bankCardVO.getReserveMobile());
	    	bankCardlog.setBindStatus(StatusConstants.USER_BANK_CARD_UNBIND); //未绑定
	    	bankCardlog.setVerifyStatus(null);
	    	bankCardlog.setStatus(null);
			bankCardlog.setBankTxTime(null);
			bankCardlog.setIssInsCode(null);
			bankCardlog.setPayCardType(null);
			
			bankCardlog.setCode(null);
			bankCardlog.setMsg(null);
	    	bankCardlog.setResponseCode(null);
	    	bankCardlog.setResponseMsg(null);
	    	bankCardlog.setCreateTime(new Date());
	    	bankCardlog.setUpdateTime(new Date());
    	
    	
			userBankCardDAO.insertRequestValidateBankCardLog(bankCardlog);
		} catch (Exception e) {
			log.error("增加绑定银行卡日志失败",e);
		}
		
		//验证点击验证码是否超过最大限制
		if (!validateCardLimitCount(bankCardVO,bankCardlog)) {
			try {
				
			 bankCardlog.setCode(CommonConstants.SEND_VALID_CODE_LIMIT);
			 bankCardlog.setUpdateTime(new Date());
			 userBankCardDAO.updateRequestValidateBankCardLog(bankCardlog);
			} catch (Exception e) {
				log.error("增加绑定银行卡日志失败",e);
			}
			
			String customerTel = configKvService.get(CommonConstants.CUSTOMER_SERVICE_TEL);
			log.info("客服电话：" + customerTel);
			retMap.put("code", ResponseConstants.FAIL);
			retMap.put("desc", "绑定次数超限,需联系秒趣客服（"+ customerTel +"）处理。");
			return retMap;
		}
		
		//生成帮顶流水（中金）
		String TxSNBinding = UUIDTools.getFormatUUID();
		bankCardVO.setThirdParyBindingId(TxSNBinding);
		
		//1.组成报文XML格式字符串
		//2.对原报文进行base64编码
		//3.对原报文进行签名，得到签名结果（十六进制字符串），算法SHA1wihRSA
		// 创建交易请求对象
        Tx2531Request txRequest = new Tx2531Request();

        txRequest.setInstitutionID(ConfigUtils.getProperty("mqcash_institution_id"));
        txRequest.setTxSNBinding(bankCardVO.getThirdParyBindingId());
        txRequest.setBankID(bankDTO.getThirdPartyBankId());
        txRequest.setAccountName(bankCardVO.getOwnerName());
        txRequest.setAccountNumber(bankCardVO.getCardNumber());
        txRequest.setIdentificationNumber(bankCardVO.getOwnerIdCard());
        txRequest.setIdentificationType(ZhongjinConstants.IDENTIFICATION_TYPE_ID_CARD);
        txRequest.setPhoneNumber(bankCardVO.getReserveMobile());
        if(EntityUtils.isEmpty(bankCardVO.getCardType()) 
				|| bankCardVO.getCardType().intValue() == CommonConstants.CARD_TYPE_SAVINGS_CARD.intValue()){
			txRequest.setCardType(ZhongjinConstants.CARD_TYPE_SAVINGS_CARD);
		}else{
			log.error("绑定银行卡类型为信用卡，目前系统暂不支持！" + bankCardVO.getCardNumber());
			throw new RuntimeException("绑定银行卡类型为信用卡，目前系统暂不支持！" + bankCardVO.getCardNumber());
//			txRequest.setCardType(ZhongjinConstants.CARD_TYPE_CREDIT_CARD);
//			txRequest.setValidDate(validDate);
//			txRequest.setCvn2(cvn2);
		}

        // 3.执行报文处理
        try {
			txRequest.process();
		} catch (Exception e) {
			log.error("组装请求报文失败" ,e);
			throw new RuntimeException("组装请求报文失败" ,e);
		}
		
		log.debug("原请求xml : ");
		log.debug(txRequest.getRequestPlainText());
		log.debug("请求message : ");
		log.debug(txRequest.getRequestMessage());
		log.debug("请求signature : ");
		log.debug(txRequest.getRequestSignature());
		
		 // 与支付平台进行通讯
        TxMessenger txMessenger = new TxMessenger();
        String[] respMsg = null;
        // Flag=10:cmb, 20:paymentAccount
        String flag = null;
     
        try {
			respMsg = txMessenger.send(txRequest.getRequestMessage(), txRequest.getRequestSignature());
		} catch (Exception e) {
			log.error("向中金发送信息时失败",e);
			throw new RuntimeException("向中金发送信息时失败",e);
		}
        
        // 0:message;
        // 1:signature
        String plainText="";
		try {
			plainText = new String(payment.tools.util.Base64.decode(respMsg[0]), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("转化中金返回报文失败",e);
			throw new RuntimeException("转化中金返回报文失败",e);
		}

        log.debug("[message]=[" + respMsg[0] + "]");
        log.debug("[signature]=[" + respMsg[1] + "]");
        log.debug("[plainText]=[" + plainText + "]");
		
		Tx2531Response tx2531Response=null;
		try {
			tx2531Response = new Tx2531Response(respMsg[0], respMsg[1]);
		} catch (Exception e) {
			log.error("构造Tx2531Response对象失败",e);
			throw new RuntimeException("构造Tx2531Response对象失败",e);
		}
		log.debug("plainText : " + tx2531Response.getResponsePlainText());
		try {
			
			bankCardlog.setCode(tx2531Response.getCode());
			bankCardlog.setMsg(tx2531Response.getMessage());
	    	bankCardlog.setUpdateTime(new Date());
			 userBankCardDAO.updateRequestValidateBankCardLog(bankCardlog);

		} catch (Exception e) {
			log.error("增加绑定银行卡日志失败",e);
		}
    	
		if ("2000".equals(tx2531Response.getCode())) {
			log.info("[Message]=[" + tx2531Response.getMessage() + "]");
			log.info("请求第三绑定用户银行卡（发送短信校验）成功");
			// 处理业务
			
			retMap.put("code", ResponseConstants.SUCCESS);
			retMap.put("desc", "申请验证绑定手机号成功，短信已发送成功，请查收。");
			return retMap;
		}else{
			log.info("请求第三绑定用户银行卡（发送短信校验）失败");
			
			retMap.put("code", ResponseConstants.FAIL);
			retMap.put("desc", "申请验证绑定手机号失败，短信发送失败。");
			return retMap;
		}
	}
	
	@Override
	public List<UserBankCardDTO> queryBankInfoByUser(Map<String,Object> queryMap) {
		
		try {
			return userBankCardDAO.queryBankInfoByUser(queryMap);
		} catch (Exception e) {
			log.error("查询银行卡是否存在失败",e);
			throw new RuntimeException("查询银行卡是否存在失败",e);
		}
	}

	@Override
	public UserBankCard queryBankCardByCardNumber(UserBankCardDTO bankCardVo) {
		
		try {
			return userBankCardDAO.queryBankCardByCardNumber(bankCardVo);
		} catch (Exception e) {
			log.error("查询银行卡是否存在失败",e);
			throw new RuntimeException("查询银行卡是否存在失败",e);
		}
	}

	@Override
	public UserBankCard queryBankCardByKey(Map<String, Object> paramMap) {
		
		return userBankCardDAO.queryBankCardByKey(paramMap);
	}
	
	
	@Override
	public UserValidateCardLimit queryValidateCardLimit(UserBankCardDTO bankCardVO) {
			
		try {
			return 	 userBankCardDAO.queryValidateCardLimit(bankCardVO);
		} catch (Exception e) {
			log.error("查询限制绑定次数失败",e);
			throw new RuntimeException("查询限制绑定次数失败",e);
		}
	}

	@Override
	public void insertValidateCardLimit(UserValidateCardLimit cardLimit) {
		 	 try {
				userBankCardDAO.insertValidateCardLimit(cardLimit);
			} catch (Exception e) {
				log.error("增加绑定限制信息失败",e);
				throw new RuntimeException("增加绑定限制信息失败",e);
			}
	}

	@Override
	public void updateValidateCardLimit(UserValidateCardLimit cardLimit) {
	 	 try {
			userBankCardDAO.updateValidateCardLimit(cardLimit);
		} catch (Exception e) {
			log.error("修改绑定银行卡限制失败",e);
			throw new RuntimeException("修改绑定银行卡限制失败",e);
		}
	}

	@Override
	public UserBankCard queryBindStatus(UserBankCardDTO bankCardDTO) {
		return 	userBankCardDAO.queryBindStatus(bankCardDTO);
	}

	@Override
	public InstalmentValidateCardLimit queryInstalmentValidateCardLimit(UserBankCardDTO bankCardDTO) {
		return 	userBankCardDAO.queryInstalmentValidateCardLimit(bankCardDTO);

	}

	@Override
	public void insertInstalmentValidateCardLimit(InstalmentValidateCardLimit cardLimit) {
		userBankCardDAO.insertInstalmentValidateCardLimit(cardLimit);
	}

	@Override
	public void updateInstalmentValidateCardLimit(InstalmentValidateCardLimit cardLimit) {
		userBankCardDAO.updateInstalmentValidateCardLimit(cardLimit);
	}

	/**  
	 * validateCardLimitCount(验证用户绑定银行卡前点击发送验证码，对次数进行限制，在限制次数内可以发送多次验证码)  
	 * @param bankCardVO
	 * @return   
	 * String 
	 * @create time： Nov 3, 2015 3:38:50 PM 
	 * @author：张海达  
	 * @since  1.0.0
	 */
	private boolean  validateCardLimitCount(UserBankCardDTO bankCardDTO,RequestValidateBankCardLog bankCardlog){
		//1.先验证该用户是否点击过发送验证码，如果未发送过，则插入一条数据
		InstalmentValidateCardLimit userValidateCardLimit = this.queryInstalmentValidateCardLimit(bankCardDTO);
		int maxTimes = Integer.parseInt(configKvService.get(CommonConstants.BIND_CARD_MAX_TIMES));
		log.info("配置点击绑定银行卡按钮的最大次数：" + maxTimes);
		String customerTel = configKvService.get(CommonConstants.CUSTOMER_SERVICE_TEL);
		log.info("客服电话：" + customerTel);
		
		if (StatusConstants.BANK_CARD_OPERATION_VALIDATE == bankCardlog.getOperationType()){
				 
			if(userValidateCardLimit!= null && EntityUtils.isNotEmpty(userValidateCardLimit.getCurTimes()) 
				&& userValidateCardLimit.getCurTimes() >= maxTimes) {
				log.info("发送短信验证码，检查结果：绑定银行卡超限，禁止发送验证码。");
				return false;
			}
		}else if(StatusConstants.BANK_CARD_OPERATION_BIND == bankCardlog.getOperationType()){
			InstalmentValidateCardLimit cardLimit = new InstalmentValidateCardLimit();
			cardLimit.setInstalmentId(bankCardDTO.getInstalmentId());
		
			cardLimit.setOperator(bankCardDTO.getOwnerName());
			cardLimit.setCreateTime(new Date());
			cardLimit.setUpdateTime(new Date());
			if (userValidateCardLimit == null) { //用户未发送过验证码，插入数据
				log.info("用户首次点击发送验证码。");
				
				//cardLimit.setMaxTimes(maxTimes);
				cardLimit.setCurTimes(1);
				cardLimit.setTotalTimes(1);
				this.insertInstalmentValidateCardLimit(cardLimit);
				return true;
					
			} else {
				log.info("用户非首次点击发送验证码。当前次数：" + userValidateCardLimit.getCurTimes() + ",最大次数：" + maxTimes);
				 //如果该用户发送过验证码，则对发送次数加一，修改表
				//判断当前次数时否和最大次数相等，如果大于或者等于则不能修改
				if (userValidateCardLimit.getCurTimes() < maxTimes) {
					cardLimit.setCurTimes(userValidateCardLimit.getCurTimes() + 1);
					cardLimit.setTotalTimes(userValidateCardLimit.getTotalTimes()+1);
					this.updateInstalmentValidateCardLimit(cardLimit);
					return true;
				}else{
					log.info("用户非首次点击发送验证码。当前次数：" + userValidateCardLimit.getCurTimes() + ",次数达到最大限制，不能再次点击按钮");
						return false;
				}
			}
		}
		
		return true;
		
	}
	
}
