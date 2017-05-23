package com.hikootech.mqcash.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.ResponseConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.constants.UserBankCardErrorConstants;
import com.hikootech.mqcash.dto.BankDTO;
import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.po.UserInfo;
import com.hikootech.mqcash.service.BankCardService;
import com.hikootech.mqcash.service.ConfigConstantsService;
import com.hikootech.mqcash.service.UserBankCardService;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.HkEncryptUtils;
import com.hikootech.mqcash.util.HttpClientUtil;
import com.hikootech.mqcash.util.ValidateTools;

@Service("bankCardService")
public class BankCardServiceImpl implements BankCardService {

	private static Logger log = LoggerFactory.getLogger(BankCardServiceImpl.class);
	
	@Autowired
	private UserBankCardService userBankCardService;
	@Autowired
	private ConfigConstantsService configConstantsService;
	
	
	private String innerEnc = ConfigUtils.getProperty("inner_mq_plat_enc");
	private String innerDesKey = ConfigUtils.getProperty("inner_mq_plat_3des_key");
	private String innerMd5Key = ConfigUtils.getProperty("inner_mq_plat_md5_key");
	private String innerPartnerId = ConfigUtils.getProperty("inner_mq_plat_partner_id");
	private String innerVersion = ConfigUtils.getProperty("inner_mq_plat_data_version");
	private String queryBindStatusUrl = ConfigUtils.getProperty("inner_mq_bind_status_url");
	
	
	@Override
	public Map<String, Object> bindBankCard(UserBankCardDTO bankCardDTO,UserBankCardDTO cacheBankCardVO,UserInfo userInfo) {

		
		
		//页面中持卡人姓名和身份证号均不可填写，必须与用户姓名和身份证号一致
		bankCardDTO.setOwnerName(userInfo.getName());
		bankCardDTO.setOwnerIdCard(userInfo.getIdCard());
		//replaceAll("\\s*", "");
		//可以替换大部分空白字符， 不限于空格
		// \s 可以匹配空格、制表符、换页符等空白字符的其中任意一个 您可能感兴趣的文章:java去除字符串中的空格、回车、换行符、制表符的小例子

		
		//判断是否有不合法值
		bankCardDTO.setCardNumber(bankCardDTO.getCardNumber().replaceAll("\\s*", ""));
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		if(EntityUtils.isEmpty(bankCardDTO.getRelationBankId())){
			retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_BANKID);
			retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_BANKID_DESC);
			return retMap;
		}else if(EntityUtils.isEmpty(bankCardDTO.getOwnerName())){
			retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_OWNERNAME);
			retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_OWNERNAME_DESC);
			return retMap;
		}else if(EntityUtils.isEmpty(bankCardDTO.getOwnerIdCard()) || !ValidateTools.validateIdCard(bankCardDTO.getOwnerIdCard())){
			retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_IDCARD);
			retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_IDCARD_DESC);
			return retMap;
		}else if(EntityUtils.isEmpty(bankCardDTO.getCardNumber()) || !ValidateTools.validateBankCardNumber(bankCardDTO.getCardNumber())){
			retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_CARDNUMBER);
			retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_CARDNUMBER_DESC);
			return retMap;
		}else if(EntityUtils.isEmpty(bankCardDTO.getReserveMobile()) || !ValidateTools.validateMobileNumber(bankCardDTO.getReserveMobile())){
			retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_RESERVEMOBILE);
			retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_RESERVEMOBILE_DESC);
			return retMap;
		}else if(EntityUtils.isEmpty(bankCardDTO.getSmCode())){
			retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_SMCODE);
			retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_SMCODE_DESC);
			return retMap;
		}
		
		//是否需要校验：持卡人身份证号、卡号、银行、预留手机号是否被修改
		if(EntityUtils.isEmpty(cacheBankCardVO)){
			log.error("绑定银行卡失败，请先获取验证码后，在点击绑定银行卡！");
			log.info("新数据, CardNumber : " + bankCardDTO.getCardNumber()  + ",ReserveMobile : "+ bankCardDTO.getReserveMobile() + ", RelationBankId : " + bankCardDTO.getRelationBankId());
			retMap.put("code", ResponseConstants.FAIL);
			retMap.put("desc", "绑定银行卡失败，请先获取验证码后，在点击绑定银行卡！");
			return retMap;
		}
		
		if(!cacheBankCardVO.getCardNumber().equals(bankCardDTO.getCardNumber())
				|| !cacheBankCardVO.getReserveMobile().equals(bankCardDTO.getReserveMobile())
				|| !cacheBankCardVO.getRelationBankId().equals(bankCardDTO.getRelationBankId())
				){
			log.error("绑定银行卡数据被修改，绑定失败！");
			log.info("原数据, CardNumber : " + cacheBankCardVO.getCardNumber()  + ",ReserveMobile : "+ cacheBankCardVO.getReserveMobile() + ", RelationBankId : " + cacheBankCardVO.getRelationBankId());
			log.info("新数据, CardNumber : " + bankCardDTO.getCardNumber()  + ",ReserveMobile : "+ bankCardDTO.getReserveMobile() + ", RelationBankId : " + bankCardDTO.getRelationBankId());
			retMap.put("code", ResponseConstants.FAIL);
			retMap.put("desc", "绑定银行卡数据被修改，绑定失败！");
			return retMap;
		}
		
		cacheBankCardVO.setUserId(userInfo.getUserId());
		cacheBankCardVO.setSmCode(bankCardDTO.getSmCode());
		
		//判断绑定银行卡是否被支持代收扣款
		BankDTO bankDTO=null;
		try {
			bankDTO = configConstantsService.getBankById(bankCardDTO.getRelationBankId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(EntityUtils.isEmpty(bankDTO)||EntityUtils.isEmpty(bankDTO.getChargeStatus())){
			log.error("请求第三方用户银行卡用于代收支付失败，找不到对应的银行，relationBankId：" + bankCardDTO.getRelationBankId());
			 retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
			 retMap.put(ResponseConstants.RETURN_DESC, "出错了，请稍后重试！");
			 return retMap;
		}
		
		//不需要校验用户已经绑定过此张卡（用户第二次购买）
		//发送请求（预留手机号短信验证码）到中金，绑定银行卡,成功，记录绑定关系
		//校验通过，新增绑定银行卡记录信息
		//校验不通过，返回不通过原因
		//成功时要将银行卡id返回
		try {
			
			bankCardDTO.setUserId(userInfo.getUserId());
			//查询已绑定或者处理中的银行卡信息
			UserBankCard userBanCardInfo=userBankCardService.queryBankCardByCardNumber(bankCardDTO);
			cacheBankCardVO.setCustIp(bankCardDTO.getCustIp());
			 
			//查询该用户是否重复点击绑卡
			UserBankCard userBanCard = userBankCardService.queryBindStatus(bankCardDTO);
			if(userBanCard != null &&  (StatusConstants.USER_BANK_CARD_BIND_DOING.equals(userBanCard.getBindStatus()) 
			|| StatusConstants.USER_BANK_CARD_BIND_FAIL.equals(userBanCard.getBindStatus()))){
				log.error("该用户已点击过绑定银行卡，不能继续点击绑定银行卡按钮。");
				retMap.put(ResponseConstants.RETURN_CODE, ResponseConstants.FAIL);
				retMap.put(ResponseConstants.RETURN_DESC, "动态密码失效，请重新获取！");
				return retMap;
			}
			
			//没有查到信息可以继续绑定，根据中金返回状态返回通知信息
			if(EntityUtils.isEmpty(userBanCardInfo)){
				log.info("库中没有查询到有效信息，请求中金绑卡");
				cacheBankCardVO.setInstalmentId(bankCardDTO.getInstalmentId());
				retMap = userBankCardService.bindBankCard(cacheBankCardVO);
				return retMap;
			} 
			
			//查询到其状态未处理中，请求核心系统去查询中金绑定状态
			if(EntityUtils.isNotEmpty(userBanCardInfo)&&userBanCardInfo.getBindStatus()==StatusConstants.USER_BANK_CARD_BIND_DOING.intValue()){
				return reqCpcnBankCardBindStatus(userBanCardInfo.getBankCardId());
				
			}
			
			//查询到其状态已绑定， 告知成功
			if(EntityUtils.isNotEmpty(userBanCardInfo)&&userBanCardInfo.getBindStatus()==StatusConstants.USER_BANK_CARD_BIND.intValue()){
				log.info("库中查询绑定成功，直接返回"); 
				retMap.put("code", ResponseConstants.SUCCESS);
				 retMap.put("desc", "绑定成功！");
				 retMap.put("bankCardId", userBanCardInfo.getBankCardId());
				 return retMap;
			}
			
			//如果以上情况全部没有进入，则证明有问题
			log.info("其他情况，直接返回系统错误"); 
			 retMap.put("code", ResponseConstants.FAIL);
			 retMap.put("desc", "系统发生错误！");
			 return retMap;
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retMap.put("code", ResponseConstants.FAIL);
			retMap.put("desc", e.getMessage());
			
			return retMap;
		}
	}

	@Override
	public Map<String, Object> validateBindMobileNumber(UserBankCardDTO bankCardVO,UserInfo userInfo) {
		
		Map<String, Object> retMap=new HashMap<String,Object>();
		//页面中持卡人姓名和身份证号均不可填写，必须与用户姓名和身份证号一致
		bankCardVO.setOwnerName(userInfo.getName());
		bankCardVO.setOwnerIdCard(userInfo.getIdCard());
		//默认卡片类型为储蓄卡
		bankCardVO.setCardType(CommonConstants.CARD_TYPE_SAVINGS_CARD);
		bankCardVO.setUserId(userInfo.getUserId());

		log.info("中金发送验证码前，校验次数通过");
		try {
			//校验手机号合法性、校验绑定手机号是否被修改
			if(EntityUtils.isEmpty(bankCardVO.getRelationBankId())){
				retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_BANKID);
				retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_BANKID_DESC);
				return retMap;
			}else if(EntityUtils.isEmpty(bankCardVO.getOwnerName())){
				retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_OWNERNAME);
				retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_OWNERNAME_DESC);
				return retMap;
			}else if(EntityUtils.isEmpty(bankCardVO.getOwnerIdCard()) || !ValidateTools.validateIdCard(bankCardVO.getOwnerIdCard())){
				retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_IDCARD);
				retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_IDCARD_DESC);
				return retMap;
			}else if(EntityUtils.isEmpty(bankCardVO.getCardNumber()) || !ValidateTools.validateBankCardNumber(bankCardVO.getCardNumber())){
				retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_CARDNUMBER);
				retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_CARDNUMBER_DESC);
				return retMap;
			}else if(EntityUtils.isEmpty(bankCardVO.getReserveMobile()) || !ValidateTools.validateMobileNumber(bankCardVO.getReserveMobile())){
				retMap.put("code", UserBankCardErrorConstants.ERROR_CODE_RESERVEMOBILE);
				retMap.put("desc", UserBankCardErrorConstants.ERROR_CODE_RESERVEMOBILE_DESC);
				return retMap;
			}
		} catch (Exception e1) {
			log.error("中金发送验证码前，严格校验发生错误",e1);
			retMap.put("code", ResponseConstants.FAIL);
			 retMap.put("desc", "申请验证绑定手机号失败，短信发送失败。");
			 return retMap;
		}
		
		log.info("中金发送验证码前，校验全部通过，准备请求报文发送验证码");
		//发送短信验证码--调用第三方中金借口请求绑定银行卡，同时发送短信到预留手机号（中鑫请求银行发送）
		try {
			retMap = userBankCardService.requestBindBankCard(bankCardVO);
		} catch (Exception e) {
			 log.error("中金发送验证码发生错误",e);
			 retMap.put("code", ResponseConstants.FAIL);
			 retMap.put("desc", "申请验证绑定手机号失败，短信发送失败。");
			 return retMap;
		}
		
		return retMap;
	}

	public Map<String,Object> reqCpcnBankCardBindStatus(String bankCardId) throws Exception{
		log.info("请求核心查询银行卡绑定状态"); 
		Map<String,Object> retMap=new HashMap<String,Object>();
		
//		retMap.put("code", ResponseConstants.FAIL);
//		 retMap.put("desc", "银行系统延迟，请稍后查看绑卡结果！");
//		 return retMap;
		//将参数传给征信引擎
		HttpClientUtil http = new HttpClientUtil(10000);
		Map<String, String> requestMap=new HashMap<String,String>();
		requestMap.put("bankCardId", bankCardId);
		
		// 生成业务加密串
		String busParams = HkEncryptUtils.createEncryptBusParams(requestMap,innerDesKey, innerEnc);

		// 协议参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("timestamp",	DateUtil.getCurDateStr("yyyyMMddHHmmss"));
		paramMap.put("partnerId", innerPartnerId);
		paramMap.put("version", innerVersion);
		paramMap.put("params", busParams);

		// 生成验签sign
		String sign_credit = HkEncryptUtils.createMd5Sign(paramMap, innerMd5Key, innerEnc);
		paramMap.put("sign", sign_credit);
		
		String retMsg=http.doPost(queryBindStatusUrl, paramMap);
		log.info("请求中金返回消息："+retMsg);
		//转换为Map
		Map<String,String> resultMap = HkEncryptUtils.stringToMap(retMsg);
		
		String retSign = resultMap.get("sign");
		resultMap.remove("sign");
		
		String _sign = HkEncryptUtils.createMd5Sign(resultMap, innerMd5Key, innerEnc);
		 
		
		if(retSign.equals(_sign)){
			log.info("验签通过");
			Map<String, String> retBusMap = HkEncryptUtils.createDecryptBusMap(resultMap.get("params"), innerDesKey, innerEnc);
			 
			String retCode=retBusMap.get("code");
			String retBindStatus=retBusMap.get("bindStatus");
			String retDesc=retBusMap.get("desc");
			
			if(EntityUtils.isEmpty(retCode)||EntityUtils.isEmpty(retBindStatus)){
				log.error("核心系统返回信息参数为空：code："+retCode+",bindStatus:"+retBindStatus);
				 retMap.put("code", ResponseConstants.FAIL);
				 retMap.put("desc", "系统发生错误！");
				 return retMap;
			}
			
			// 返回结果，code为查询成功时 ，针对处理中和已绑定做返回处理，剩余状态认为绑定失败；   
			//        剩余情况认为绑定失败
			if(retCode.equals(ResponseConstants.SUCCESS)){
				//返回已经绑定，则直接返回绑定成功
				if( Integer.parseInt(retBindStatus)==StatusConstants.USER_BANK_CARD_BIND.intValue()){
					 log.info("核心系统返回绑定成功 " );
					 retMap.put("code", ResponseConstants.SUCCESS);
					 retMap.put("desc", "绑定成功");
					 return retMap;
				 }
				//返回处理中，则直接返回绑定失败及desc
				 if(Integer.parseInt(retBindStatus)==StatusConstants.USER_BANK_CARD_BIND_DOING.intValue()){
				
					 log.info("核心系统返回处理中，描述信息："+retDesc);
					 retMap.put("code", ResponseConstants.FAIL);
					 retMap.put("desc", "银行系统延迟，请稍后查看绑卡结果！");
					 return retMap;
				 }
				 
				 //剩余状态认为绑定失败
				 log.info("核心系统返回状态为未知绑定状态,retBindStatus："+retBindStatus+",描述信息："+retDesc);
				 retMap.put("code", ResponseConstants.FAIL);
				 retMap.put("desc", "系统错误！");
				 return retMap;
			}

			 //返回code不是成功
			 log.info("核心系统返回查询结果code不是成功，code："+retCode+",描述信息："+retDesc);
			 retMap.put("code", ResponseConstants.FAIL);
			 retMap.put("desc", "系统错误！");
			 return retMap;
			
		}else{
			log.info("验签不通过");
			 retMap.put("code", ResponseConstants.FAIL);
			 retMap.put("desc", "系统发生错误！");
			 return retMap;
		}
	}
}
