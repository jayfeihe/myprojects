package com.tera.credit.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.credit.dao.CreditHousingDao;
import com.tera.credit.model.CreditHousing;

/**
 * 
 * 信用贷款申请房产信息服务类
 * <b>功能：</b>CreditHousingDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:32<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
@Service("creditHousingService")
public class CreditHousingService {

	private final static Logger log = Logger.getLogger(CreditHousingService.class);

	@Autowired(required=false)
    private CreditHousingDao dao;

	@Transactional
	public void add(CreditHousing... objs)  throws Exception {
		if(objs == null || objs.length < 1){
			return;
		}
		for(CreditHousing obj : objs ){
			dao.add(obj);
		}
	}
	
	@Transactional
	public void update(CreditHousing obj)  throws Exception {
		dao.update(obj);
	}
	
	@Transactional
	public void updateOnlyChanged(CreditHousing obj)  throws Exception {
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
	
	public List<CreditHousing> queryList(Map<String, Object> map) throws Exception {
		return dao.queryList(map);
	}

	public CreditHousing queryByKey(Object id) throws Exception {
		return (CreditHousing)dao.queryByKey(id);
	}
	
}
