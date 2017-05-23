package com.tera.audit.account.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.account.model.BillBase;

/**
 * 
 * 公司收支明细表DAO
 * <b>功能：</b>BillBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 09:15:17<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface BillBaseDao {
		
	public void add(BillBase obj);	
	
	public void update(BillBase obj);
	
	public void updateOnlyChanged(BillBase obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<BillBase> queryList(Map<String, Object> map);

	public BillBase queryByKey(Object obj);

}
