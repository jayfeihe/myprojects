package com.tera.car.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.car.dao.CarCreditReportDao;
import com.tera.car.model.CreditReport;

/**
 * 
 * 信用贷款申请人行信息服务类
 * <b>功能：</b>CreditReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:53:55<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("carCreditReportService")
public class CarCreditReportService {

	private final static Logger log = Logger.getLogger(CarCreditReportService.class);

	@Autowired(required=false)
    private CarCreditReportDao dao;

	@Transactional
	public void add(CreditReport... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CreditReport obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CreditReport obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CreditReport obj)  throws Exception {
		dao.updateOnlyChanged(obj);
	}
	
	@Transactional
	public void delete(Object... ids) throws Exception {
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			dao.delete(id);
		}
	}
	
	public int queryCount(Map<String, Object> map)throws Exception {
		return dao.queryCount(map);
	}
	
	public List<CreditReport> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CreditReport queryByKey(Object id) throws Exception {
		return (CreditReport)dao.queryByKey(id);
	}
	
}
