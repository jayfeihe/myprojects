package com.hikootech.mqcash.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.UserBankCardDAO;
import com.hikootech.mqcash.dto.UserBankCardDTO;
import com.hikootech.mqcash.po.UserBankCard;
import com.hikootech.mqcash.po.UserValidateCardLimit;
import com.hikootech.mqcash.service.UserBankCardService;
import com.hikootech.mqcash.util.ConfigUtils;

import payment.api.system.CMBEnvironment;
import payment.api.system.PaymentUserEnvironment;
import payment.api.system.TxMessenger;
import payment.api.tx.paymentbinding.Tx2502Request;
import payment.api.tx.paymentbinding.Tx2502Response;

@Service("userBankCardService")
public class UserBankCardServiceImpl implements UserBankCardService {
	
	private static Logger log = LoggerFactory.getLogger(UserBankCardServiceImpl.class);
	
	@Autowired
	private UserBankCardDAO userBankCardDAO;

	@Override
	public List<UserBankCard> queryBindingStatusBankCard(
			UserBankCardDTO bankCardDTO) throws Exception {
		// TODO Auto-generated method stub
		return userBankCardDAO.queryUserBankCard(bankCardDTO);
	}

	@Override
	public Map<String,String> ensureBankCardStatus(UserBankCard userBankCard)
			throws Exception {
		// TODO Auto-generated method stub
		log.info("向第三方发起请求查询用户绑定中银行卡绑定结果，bankCardId：" + userBankCard.getBankCardId());
		Map<String,String> retMap=new HashMap<String,String>();
		
		if(StatusConstants.USER_BANK_CARD_BIND_DOING.intValue() != userBankCard.getBindStatus().intValue()){
			log.error("银行卡帮顶记录状态有误，不为绑定中！");
			throw new Exception("银行卡帮顶记录状态有误，不为绑定中！");
		}
		
		//1.组成报文XML格式字符串
		//2.对原报文进行base64编码
		//3.对原报文进行签名，得到签名结果（十六进制字符串），算法SHA1wihRSA
		// 创建交易请求对象
        Tx2502Request txRequest = new Tx2502Request();

        txRequest.setInstitutionID(ConfigUtils.getProperty("mqcash_institution_id"));
        txRequest.setTxSNBinding(userBankCard.getThirdParyBindingId());

        // 3.执行报文处理
        txRequest.process();
        
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
        if ("10".equals(flag)) {
            respMsg = txMessenger.send(txRequest.getRequestMessage(), txRequest.getRequestSignature(), CMBEnvironment.cmbtxURL);// 0:message;
        } else if ("20".equals(flag)) {
            respMsg = txMessenger.send(txRequest.getRequestMessage(), txRequest.getRequestSignature(), PaymentUserEnvironment.paymentusertxURL);
        } else {
            respMsg = txMessenger.send(txRequest.getRequestMessage(), txRequest.getRequestSignature());// 0:message;
        }
        // 1:signature
        String plainText = new String(payment.tools.util.Base64.decode(respMsg[0]), "UTF-8");

        log.debug("[message]=[" + respMsg[0] + "]");
        log.debug("[signature]=[" + respMsg[1] + "]");
        log.debug("[plainText]=[" + plainText + "]");
        
        Tx2502Response tx2502Response = new Tx2502Response(respMsg[0], respMsg[1]);
		
		UserBankCardDTO bankCardDTO = new UserBankCardDTO();
		bankCardDTO.setBankCardId(userBankCard.getBankCardId());
		bankCardDTO.setThirdParyBindingId(userBankCard.getThirdParyBindingId());
		bankCardDTO.setPreBindStatus(userBankCard.getBindStatus());
		bankCardDTO.setUpdateTime(new Date());
		//是否需要校验第三方返回的绑定流水号是否正确（被修改）、根据返回卡片类型校验卡片类型
		if("2000".equals(tx2502Response.getCode())){
			log.info("[Message]=[" + tx2502Response.getMessage() + "]");
        	log.info("[InstitutionID]=[" + tx2502Response.getInstitutionID() + "]");
        	log.info("[TxSNBinding]=[" + tx2502Response.getTxSNBinding() + "]");
        	log.info("[Status]=[" + tx2502Response.getStatus() + "]");
        	log.info("[ResponseMessage]=[" + tx2502Response.getResponseMessage() + "]");
        	log.info("[ResponseCode]=[" + tx2502Response.getResponseCode() + "]");
        	log.info("[IssInsCode]=[" + tx2502Response.getIssInsCode() + "]");
        	log.info("[PayCardType]=[" + tx2502Response.getPayCardType() + "]");
            // 处理业务
			log.info("第三方支付绑定银行卡查询绑定状态！");
			if(10 != tx2502Response.getStatus()&&20 != tx2502Response.getStatus()
					&&30 != tx2502Response.getStatus()&&40 != tx2502Response.getStatus()){
				log.error("第三方支付绑定银行卡查询绑定状态发生错误,返回状态不是规定的10、20、30、40，而是"+tx2502Response.getStatus());
			}
			
			//处理中
			if(10 == tx2502Response.getStatus()){
				log.info("第三方支付绑定银行卡查询绑定状态，绑定处理中！");
				retMap.put("bindStatus", StatusConstants.USER_BANK_CARD_BIND_DOING.toString());
				retMap.put("desc", tx2502Response.getResponseMessage());
				return retMap;
				
			}

			if(20 == tx2502Response.getStatus()){
				log.info("第三方支付绑定银行卡查询绑定状态，绑定失败！");
				bankCardDTO.setBindStatus(StatusConstants.USER_BANK_CARD_BIND_FAIL);
			}else if(30 == tx2502Response.getStatus()){
				log.info("第三方支付绑定银行卡查询绑定状态，绑定成功！");
				bankCardDTO.setBindStatus(StatusConstants.USER_BANK_CARD_BIND);
			}else if(40 == tx2502Response.getStatus()){
				log.info("第三方支付绑定银行卡查询绑定状态，解绑成功！");
				bankCardDTO.setBindStatus(StatusConstants.USER_BANK_CARD_RELIEVE_BIND);
			}
		}else{
			log.error("第三方支付绑定银行卡查询绑定状态,绑定失败");
			throw new Exception("第三方支付绑定银行卡查询绑定状态,绑定失败");
		}
		
		int row = userBankCardDAO.updateUserBankCardBindStatus(bankCardDTO);
		
		if(row != 1){
			log.error("更新绑定银行卡状态出错，BankCardId : " + userBankCard.getBankCardId()+"，更新结果条数不为1");
			throw new Exception("更新绑定银行卡状态出错，BankCardId : " + userBankCard.getBankCardId()+"，更新结果条数不为1");
		}
		retMap.put("bindStatus", bankCardDTO.getBindStatus().toString());
		retMap.put("desc", tx2502Response.getResponseMessage());
		return retMap;
	}

	@Override
	public UserBankCard queryBankCardByKey(String bankCardId) {
		return  userBankCardDAO.queryBankCardByKey(bankCardId);
	}


	@Override
	public void updateValidateCardLimit(UserValidateCardLimit cardLimit) throws Exception{
		 
	  try {
		userBankCardDAO.updateValidateCardLimit(cardLimit);
	} catch (Exception e) {
		log.error("更新银行卡短信限制次数出错，idCard : " + cardLimit.getIdCard());
	}
	}

}
