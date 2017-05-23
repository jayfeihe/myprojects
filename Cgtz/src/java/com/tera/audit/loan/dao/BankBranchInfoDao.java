package com.tera.audit.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.loan.model.BankBranchInfo;

/**
 * 
 * DAO
 * <b>功能：</b>BankBranchInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-29 06:49:49<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface BankBranchInfoDao {
		
	public void add(BankBranchInfo obj);	
	
	public void update(BankBranchInfo obj);
	
	public void updateOnlyChanged(BankBranchInfo obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<BankBranchInfo> queryList(Map<String, Object> map);

	public BankBranchInfo queryByKey(Object obj);

}
