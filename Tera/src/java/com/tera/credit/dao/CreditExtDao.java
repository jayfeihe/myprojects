package com.tera.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.credit.model.CreditExt;

/**
 * 
 * 信用贷款申请信息扩展表DAO
 * <b>功能：</b>CreditExtDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:25:23<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CreditExtDao {
		
	public void add(CreditExt obj);	
	
	public void update(CreditExt obj);
	
	public void updateOnlyChanged(CreditExt obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditExt> queryList(Map<String, Object> map);

	public CreditExt queryByKey(Object obj);

}
