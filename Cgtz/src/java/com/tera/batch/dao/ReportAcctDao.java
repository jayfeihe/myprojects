package com.tera.batch.dao;

import java.util.List;
import java.util.Map;

import com.tera.batch.model.ReportAcct;

/**
 * 
 * 报表数据，每天汇总一次。各个公司的存量，转贷情况等统计DAO
 * <b>功能：</b>ReportAcctDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-15 10:21:02<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ReportAcctDao {
		
	public void add(ReportAcct obj);	
	
	public void update(ReportAcct obj);
	
	public void updateOnlyChanged(ReportAcct obj);
		
	public void delete(Object obj);
	
	//根据日期批量删除
	public void deleteByDate (Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<ReportAcct> queryList(Map<String, Object> map);

	public ReportAcct queryByKey(Object obj);
	
	public List<ReportAcct> queryNor(Object obj);
	
	public List<ReportAcct> queryOver(Object obj);
	
	public List<ReportAcct> queryTran();
	
	
	

}
