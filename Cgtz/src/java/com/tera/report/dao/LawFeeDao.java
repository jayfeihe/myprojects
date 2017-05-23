package com.tera.report.dao;

import java.util.List;
import java.util.Map;

import com.tera.report.model.LawFee;

/**
 * 
 * 还款计划表DAO
 * <b>功能：</b>RetPlanDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 12:02:12<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LawFeeDao {
		

	public int queryCount(Map<String, Object> map);
	
	public List<LawFee> queryList(Map<String, Object> map);
	
}
