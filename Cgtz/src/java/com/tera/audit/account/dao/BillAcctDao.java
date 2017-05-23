package com.tera.audit.account.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.account.model.BillAcct;

/**
 * 
 * 借款人交易记录表
DAO
 * <b>功能：</b>BillAcctDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 09:14:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface BillAcctDao {
		
	public void add(BillAcct obj);	
	
	public void update(BillAcct obj);
	
	public void updateOnlyChanged(BillAcct obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<BillAcct> queryList(Map<String, Object> map);

	public BillAcct queryByKey(Object obj);

}
