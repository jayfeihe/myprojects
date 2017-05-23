package com.hikootech.mqcash.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hikootech.mqcash.dao.ContractInfoDAO;
import com.hikootech.mqcash.dto.ContractInfoDTO;
import com.hikootech.mqcash.service.ContractInfoService;

@Service("contractInfoService")
public class ContractInfoServiceImpl implements ContractInfoService {

	private static Logger log=LoggerFactory.getLogger(ContractInfoServiceImpl.class);
	@Autowired
	private ContractInfoDAO contractInfoDAO;
	
	@Override
	public int modifyBankCardOfInstalment(Map<String, Object> queryMap) {
		try {
			int ret = contractInfoDAO.modifyBankCardOfInstalment(queryMap);
			if(ret!=1) {
				 throw new RuntimeException("更改结果不为1");
			}
			return ret;
		} catch (Exception e) {
			log.error("修改账单绑定的银行卡失败",e);
			throw new RuntimeException("修改账单绑定的银行卡失败",e);
		}
		
	}
	
	@Override
	public List<ContractInfoDTO> queryInstalmentOrderInfoList(Map<String, Object> queryMap) {
		
		try {
			return contractInfoDAO.queryInstalmentOrderInfoList(queryMap);
		} catch (Exception e) {
			log.error("查询合同信息失败",e);
			throw new RuntimeException("查询合同信息失败",e);
		}
	}

}
