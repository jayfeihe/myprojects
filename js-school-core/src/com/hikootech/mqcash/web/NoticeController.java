package com.hikootech.mqcash.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import payment.api.notice.Notice1318Request;
import payment.api.notice.Notice1363Request;
import payment.api.notice.NoticeRequest;
import payment.api.notice.NoticeResponse;
import payment.tools.util.Base64;

import com.hikootech.mqcash.service.CpcnService;

@RequestMapping("/zhongjin")
@Controller
public class NoticeController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private CpcnService cpcnService;
	
	@RequestMapping("/receiveNotice.do")
	@ResponseBody
	public String cpcn(){
		logger.info("收到中金异步通知");
		
		 // 1 获得参数message和signature
        String message = getRequest().getParameter("message");
        String signature = getRequest().getParameter("signature");

        logger.info("[message]=[" + message + "]");
        logger.info("[signature]=[" + signature + "]");

        // 2 生成交易结果对象
        try {
			NoticeRequest noticeRequest = new NoticeRequest(message, signature);
			
			if ("1318".equals(noticeRequest.getTxCode())) {
				logger.info("[1318] --- [主动付款支付结果通知] 开始");
                Notice1318Request nr = new Notice1318Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxCode]       = [1318]");
                logger.info("[TxName]       = [市场订单支付状态变更通知]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[PaymentNo]    = [" + nr.getPaymentNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankNotificationTime]       = [" + nr.getBankNotificationTime() + "]");
                cpcnService.notice1318(nr);
                logger.info("[1318] --- [主动付款支付结果通知] 结束");
                
            }else if ("1363".equals(noticeRequest.getTxCode())) {
            	logger.info("[1363] --- [单笔代收结果通知] 开始");
                Notice1363Request nr = new Notice1363Request(noticeRequest.getDocument());
                // ！！！ 在这里添加商户处理逻辑！！！
                // 以下为演示代码
                logger.info("[TxName]       = [单笔代收结果通知]");
                logger.info("[TxCode]       = [1363]");
                logger.info("[InstitutionID]= [" + nr.getInstitutionID() + "]");
                logger.info("[TxSN]    = [" + nr.getTxSN() + "]");
                logger.info("[OrderNo]      = [" + nr.getOrderNo() + "]");
                logger.info("[Amount]       = [" + nr.getAmount() + "]");
                logger.info("[Status]       = [" + nr.getStatus() + "]");
                logger.info("[BankTxTime]       = [" + nr.getBankTxTime() + "]");
                logger.info("[ResponseCode]       = [" + nr.getResponseCode() + "]");
                logger.info("[ResponseMessage]       = [" + nr.getResponseMessage() + "]");
                if (30 == nr.getStatus() || 40 == nr.getStatus()) {
                    logger.info("receive 1363 notification success");
                    cpcnService.notice1363(nr);
                }
                
                logger.info("[1363] --- [单笔代收结果通知] 结束");
            }
			
			String xmlString = new NoticeResponse().getMessage();
			String base64String = new String(Base64.encode(xmlString.getBytes("UTF-8")));
			logger.info(xmlString);
			logger.info(base64String);
			
			return base64String;
		} catch (Exception e) {
			logger.error("受到中金异步通知异常" + e.getMessage(), e);
		}
        
        return null;
	}

}
