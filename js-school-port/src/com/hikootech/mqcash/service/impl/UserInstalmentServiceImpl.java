package com.hikootech.mqcash.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.UserInstalmentDAO;
import com.hikootech.mqcash.po.UserInstalment;
import com.hikootech.mqcash.service.UserInstalmentService;



/**
 * @author yuwei
 * 2015年8月6日
 * 分期贷款借口
 */
@Service("userInstalmentService")
public class UserInstalmentServiceImpl implements UserInstalmentService{
	
	private static Logger log = LoggerFactory.getLogger(UserInstalmentServiceImpl.class);
	
	@Autowired
	private UserInstalmentDAO userInstalmentDAO;


	@Override
	public UserInstalment queryInstalmentById(Map<String,Object> queryMap) {
		try {
			return userInstalmentDAO.queryInstalmentById(queryMap);
		} catch (Exception e) {
			log.error("根据账单id和用户id查询账单信息失败",e);
			throw new RuntimeException("根据账单id和用户id查询账单信息失败",e);
		}
	}
	
}
