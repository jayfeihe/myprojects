package com.tera.checkcenter.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tera.checkcenter.dao.CheckCenterDao;

/**
 * 
 * 信资金流转查看服务类
 * <b>功能：</b>CreditAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:04:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("checkCenterService")
public class CheckCenterService {

	private final static Logger log = Logger.getLogger(CheckCenterService.class);

	@Autowired(required=false)
    private CheckCenterDao dao;
	//进账总额
	public double queryTotalReceipts(Map<String, Object> map)throws Exception {
		return dao.queryTotalReceipts(map);
	}
	//未撮合金额
	public double queryDisMatchAmount(Map<String, Object> map)throws Exception {
		return dao.queryDisMatchAmount(map);
	}
	//撮合占用金额
	public double queryMatchOccupyAmount(Map<String, Object> map)throws Exception {
		return dao.queryMatchOccupyAmount(map);
	}
	//出借人资金锁定金额
	public double queryLendLockAmount(Map<String, Object> map)throws Exception {
		return dao.queryLendLockAmount(map);
	}
}
