package com.tera.audit.law.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.law.model.Contract;

/**
 * 
 * 合同信息表DAO
 * <b>功能：</b>ContractDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 11:51:45<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ContractDao {
		
	public void add(Contract obj);	
	
	public void update(Contract obj);
	
	public void updateOnlyChanged(Contract obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Contract> queryList(Map<String, Object> map);

	public Contract queryByKey(Object obj);

	public Contract queryByContractId(String ContractId);
	
	public List<Contract> queryLateNoFlag();
	
	public List<Contract> queryLateCon(Map<String, Object> map);
	
	public List<Contract> querySalesNewLog(Map<String, Object> map);
	
	public List<Contract> querySalesNewBill(Map<String, Object> map);
	
	public List<Contract> queryInvalidCheck(Map<String, Object> map);


	
}
