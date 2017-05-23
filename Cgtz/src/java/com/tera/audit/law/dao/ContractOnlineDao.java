package com.tera.audit.law.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.law.model.ContractOnline;

/**
 * 
 * 线下线上合同关联表DAO
 * <b>功能：</b>ContractOnlineDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 15:03:28<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ContractOnlineDao {
		
	public void add(ContractOnline obj);	
	
	public void update(ContractOnline obj);
	
	public void updateOnlyChanged(ContractOnline obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<ContractOnline> queryList(Map<String, Object> map);

	public ContractOnline queryByKey(Object obj);

}
