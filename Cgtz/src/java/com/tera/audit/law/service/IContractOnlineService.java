package com.tera.audit.law.service;

import java.util.List;
import java.util.Map;

import com.tera.audit.law.model.ContractOnline;

/**
 * 
 * 线下线上合同关联表服务类
 * <b>功能：</b>ContractOnlineDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 15:03:28<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */

public interface IContractOnlineService {

	
	public void add(ContractOnline... objs)  throws Exception ;
	
	
	public void update(ContractOnline obj)  throws Exception;
	
	public void updateOnlyChanged(ContractOnline obj)  throws Exception;
	
	
	public void delete(Object... ids) throws Exception ;
	
	public int queryCount(Map<String, Object> map)throws Exception ;
	
	public List<ContractOnline> queryList(Map<String, Object> map) throws Exception;

	public ContractOnline queryByKey(Object id) throws Exception;
	
}
