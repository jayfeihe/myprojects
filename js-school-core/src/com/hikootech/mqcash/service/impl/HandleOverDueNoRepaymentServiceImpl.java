package com.hikootech.mqcash.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.CommonConstants;
import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.HandleOverDueNoRepaymentDAO;
import com.hikootech.mqcash.dao.LogDAO;
import com.hikootech.mqcash.dto.UserForSendSmDTO;
import com.hikootech.mqcash.po.SmLog;
import com.hikootech.mqcash.service.ConfigKvService;
import com.hikootech.mqcash.service.HandleOverDueNoRepaymentService;
import com.hikootech.mqcash.util.CommonUtils;
import com.hikootech.mqcash.util.ConfigUtils;
import com.hikootech.mqcash.util.DateUtil;
import com.hikootech.mqcash.util.EntityUtils;
import com.hikootech.mqcash.util.GenerateKey;
import com.hikootech.mqcash.util.SmUtils;

@Service("handleOverDueNoRepaymentService")
public class HandleOverDueNoRepaymentServiceImpl implements HandleOverDueNoRepaymentService {
	private static Logger log = LoggerFactory.getLogger(HandleOverDueNoRepaymentServiceImpl.class);

	@Autowired
	private HandleOverDueNoRepaymentDAO handleOverDueNoRepaymentDAO;
	@Autowired
	private LogDAO logDAO;
	@Autowired
	private ConfigKvService configKvService;
	

	@Override
	public void sendOverDueNoRepaymentSMS() throws Exception{
		
		//查询计划还款时间的范围
		
		Date endDate = DateUtil.weeHours(new Date(),0);
		Date startDate = DateUtil.weeHours(DateUtil.addDate(endDate, Calendar.DATE, -Integer.parseInt(configKvService.get(CommonConstants.OVERDUE_DAYS_MIDDLE))),0);
		
		log.info("查询逾期未还款时间范围,开始时间：" + startDate + ",结束时间：" + endDate);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", startDate); //开始日期
		paramMap.put("endDate", endDate);	//结束日期
		
		List<UserForSendSmDTO> list = handleOverDueNoRepaymentDAO.queryOverDueNoRepaymentList(paramMap);

		if (list != null && list.size() > 0) {
			//获得逾期订单的客户手机号并发送短信
			for (UserForSendSmDTO plansDto :list) {
				try {
					if (EntityUtils.isNotEmpty(plansDto.getBindMobile())) {
						log.info("逾期未还款客户手机号：" + plansDto.getBindMobile() + ",idCard:" + plansDto.getIdCard() + ",发送短信开始。" );
						
						
						
						String cardNumber = EntityUtils.isNotEmpty(plansDto.getCardNumber())?plansDto.getCardNumber().substring(plansDto.getCardNumber().length()-4,plansDto.getCardNumber().length()):""; 
						log.info("逾期未还款客户银行卡号：" + plansDto.getCardNumber()+ ",开始发送短信。");
						String[] sendData=new String[]{plansDto.getUserName(),plansDto.getReceivableAmount().toString(),configKvService.get(CommonConstants.MQ_CASH_PORT_URL), configKvService.get(CommonConstants.OVERDUE_DEPOSIT_TIME),cardNumber };
						boolean isSucces = SmUtils.sendMsg(plansDto.getBindMobile(),sendData , ConfigUtils.getProperty("overdue_remind_template_id"));
						
						SmLog sms = new SmLog();
						sms.setSmOrderId(GenerateKey.getId(CommonConstants.SM_ORDER_ID_PREFIX, ConfigUtils.getProperty("db_id")));
						sms.setMobileNumber(plansDto.getBindMobile() );
						sms.setData(CommonUtils.join(sendData, CommonConstants.SM_DATA_SEPARATOR));
						sms.setBookTime(DateUtil.weeHours(new Date(), 2)); //预计发送时间 8：00：00
						sms.setSendTime(new Date());//实际发送时间
						sms.setSendStatus(StatusConstants.SEND_SMS_WAITTING); //默认待发送
						sms.setSendType(StatusConstants.SEND_SMS_TYPE_NOW);//立即发送
						sms.setTemplateId(ConfigUtils.getProperty("overdue_remind_template_id"));
						sms.setCreateTime(new Date());
						sms.setUpdateTime(new Date());
						sms.setOperator(CommonConstants.DEFAULT_OPERATOR);
						
						if(isSucces){
							log.info("发送成功");
							sms.setSendStatus(StatusConstants.SEND_SMS_SUCCESS);			
						}else{
							log.info("发送失败");
							sms.setSendStatus(StatusConstants.SEND_SMS_FAILED); 
						}
						
						logDAO.insertSmLog(sms);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("逾期未还款客户，发送还款短信异常"  + ",idCard:" + plansDto.getIdCard(),e);
					throw new RuntimeException("逾期未还款客户，发送还款短信异常"  + ",idCard:" + plansDto.getIdCard(),e);
				}
				
				
			}
		}
		
	}
	
}
