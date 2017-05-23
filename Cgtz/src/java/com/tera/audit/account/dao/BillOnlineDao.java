package com.tera.audit.account.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.account.model.BillOnline;

/**
 * 
 * 线上放款表DAO
 * <b>功能：</b>BillOnlineDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 09:17:54<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface BillOnlineDao {
		
	public void add(BillOnline obj);	
	
	public void update(BillOnline obj);
	
	public void updateOnlyChanged(BillOnline obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<BillOnline> queryList(Map<String, Object> map);

	public BillOnline queryByKey(Object obj);

}
