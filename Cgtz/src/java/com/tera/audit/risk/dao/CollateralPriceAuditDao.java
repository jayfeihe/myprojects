package com.tera.audit.risk.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.risk.model.CollateralPriceAudit;

/**
 * 
 * 核价表DAO
 * <b>功能：</b>CollateralPriceAuditDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-09 18:57:07<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollateralPriceAuditDao {
		
	public void add(CollateralPriceAudit obj);	
	
	public void update(CollateralPriceAudit obj);
	
	public void updateOnlyChanged(CollateralPriceAudit obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollateralPriceAudit> queryList(Map<String, Object> map);

	public CollateralPriceAudit queryByKey(Object obj);

	public List<CollateralPriceAudit> queryByCollateralId(String collateralId);

	public CollateralPriceAudit queryLatestByCollateralId(String collateralId);

}
