package com.tera.audit.loan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tera.audit.loan.dao.BankBranchInfoDao;
import com.tera.audit.loan.model.BankBranchInfo;


public interface IBankBranchInfoService {


//	 void add(BankBranchInfo... objs) throws Exception;

//	 void update(BankBranchInfo obj) throws Exception;
//	
//	
//	 void updateOnlyChanged(BankBranchInfo obj) throws Exception;
//	
//	
//	 void delete(Object... ids) throws Exception;
//	
//	 int queryCount(Map<String, Object> map) throws Exception;
	
	 List<BankBranchInfo> queryList(Map<String, Object> map) throws Exception ;


	/**
	 * 判断是否有此分行，如果没有新添加
	 * @param city 城市名
	 * @param bankName 银行名称
	 * @param bankBranch分行名称
	 * @throws Exception
	 */
	 void verifyBranch(String city,String bankName,String bankBranch)  throws Exception ;
	
	 void verifyBank(String bankName,HttpServletRequest request)  throws Exception ;
}
