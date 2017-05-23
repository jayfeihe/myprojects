package com.tera.audit.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.loan.model.Collateral;

/**
 * 
 * 质押、抵押物信息DAO
 * <b>功能：</b>CollateralDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollateralDao {
		
	public void add(Collateral obj);	
	
	public void update(Collateral obj);
	
	public void updateOnlyChanged(Collateral obj);
	
	public void updatePriceInfo(Collateral obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Collateral> queryList(Map<String, Object> map);

	public Collateral queryByKey(Object obj);
	
	public List<Collateral> queryListByLoanId(Map<String, Object> map);

}
