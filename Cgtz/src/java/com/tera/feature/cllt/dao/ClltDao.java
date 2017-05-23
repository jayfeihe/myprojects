package com.tera.feature.cllt.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.cllt.model.Cllt;

/**
 * 
 * 逾期报告DAO
 * <b>功能：</b>OverdueReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ClltDao {

	public int queryCount(Map<String, Object> map);
	
	public List<Cllt> queryList(Map<String, Object> map);

}
