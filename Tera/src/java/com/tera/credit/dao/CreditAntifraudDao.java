package com.tera.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.credit.model.CreditAntifraud;

/**
 * 
 * 信用贷款反欺诈表DAO
 * <b>功能：</b>CreditAntifraudDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 12:07:52<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CreditAntifraudDao {
		
	public void add(CreditAntifraud obj);	
	
	public void update(CreditAntifraud obj);
	
	public void updateOnlyChanged(CreditAntifraud obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditAntifraud> queryList(Map<String, Object> map);

	public CreditAntifraud queryByKey(Object obj);

	public CreditAntifraud queryLatestByAppId(Map<String, Object> map);
}
