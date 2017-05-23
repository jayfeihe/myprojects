package com.hikootech.mqcash.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.constants.StatusConstants;
import com.hikootech.mqcash.dao.UserProtocolDAO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.exception.MQExceptionConstants;
import com.hikootech.mqcash.service.UserProtocolService;

@Service("userProtocolService")
public class UserProtocolServiceImpl implements UserProtocolService {
	
	private static Logger log = LoggerFactory.getLogger(UserProtocolServiceImpl.class);

	@Autowired
	private UserProtocolDAO userProtocolDAO;
	
	@Override
	public long updateUserProtocolEffectiveToCompleted(String instalmentId) throws MQException {
		// TODO Auto-generated method stub
		log.info("修改合同协议状态，执行中-->结束");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("instalmentId", instalmentId);
		paramMap.put("protocolStatus", StatusConstants.PROTOCAL_STATUS_COMPLETED);
		paramMap.put("beforeProtocolStatus", StatusConstants.PROTOCAL_STATUS_DOING_EFFECTIVE);
		
		long row = 0;
		try {
			row = userProtocolDAO.updateUserProtocolStatus(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新分期订单为分期完成出错，数据库异常！分期订单ID：" + instalmentId, e);
			throw new MQException(MQExceptionConstants.MQ_DATABASE_EXCEPTION, MQExceptionConstants.MQ_DATABASE_EXCEPTION_DESC);
		}
		
		return row;
	}
	

}
