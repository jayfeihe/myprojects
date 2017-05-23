package com.tera.audit.law.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.law.model.Contract;
import com.tera.audit.law.model.ProjectInfo;

public interface IContractService {

	void add(Contract... objs) throws Exception;

	void update(Contract obj) throws Exception;

	void updateOnlyChanged(Contract obj) throws Exception;

	void delete(Object... ids) throws Exception;

	int queryCount(Map<String, Object> map) throws Exception;

	List<Contract> queryList(Map<String, Object> map) throws Exception;

	Contract queryByKey(Object id) throws Exception;

	Contract queryByContractId(String contractId);

	PageList<Contract> queryPageList(Map<String, Object> params);
	
	List<Contract> queryLateNoFlag();
	
    List<Contract> queryLateCon(Map<String, Object> map);
    
    List<Contract> querySalesNewLog(Map<String, Object> map);
    
    List<Contract> querySalesNewBill(Map<String, Object> map);
    
    List<Contract> queryInvalidCheck(Map<String, Object> map);
    
    void  handleOnline(List<ProjectInfo> listInfos) throws Exception ;

}