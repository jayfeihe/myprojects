package com.tera.report.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.model.BillAcctFlow;

/**
 * 
 * 质押、抵押物信息DAO
 * <b>功能：</b>CollateralDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-07 13:34:31<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface BillAcctFlowDao {
	
	public int queryCount(Map<String, Object> map);
	
	public List<BillAcctFlow> queryList(Map<String, Object> map);

}
