package com.tera.credit.dao;

import java.util.List;
import java.util.Map;

import com.tera.credit.model.CreditHousing;

/**
 * 
 * 信用贷款申请房产信息DAO
 * <b>功能：</b>CreditHousingDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-09-04 11:45:32<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CreditHousingDao {
		
	public void add(CreditHousing obj);	
	
	public void update(CreditHousing obj);
	
	public void updateOnlyChanged(CreditHousing obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CreditHousing> queryList(Map<String, Object> map);

	public CreditHousing queryByKey(Object obj);

}
