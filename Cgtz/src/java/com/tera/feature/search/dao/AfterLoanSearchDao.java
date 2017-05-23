package com.tera.feature.search.dao;

import java.util.List;
import java.util.Map;

import com.tera.feature.search.model.AfterLoanSearchBean;

/**
 *功能:AfterLoanSearchDao  稽查、逾期相关dao
 *时间:2016年3月7日上午10:45:22
 *@author Ldh
 */
public interface AfterLoanSearchDao {
    //稽查查询
	public int queryCount(Map<String, Object> map);
	
	public List<AfterLoanSearchBean> queryList(Map<String, Object> map);
	
	public List<AfterLoanSearchBean> queryTaskList(Map<String, Object> map);
	
	public List<AfterLoanSearchBean> queryRecordList(Map<String, Object> map);
	
	public List<AfterLoanSearchBean> queryDetailList(Map<String, Object> map);
	
	public int queryLateConCount(Map<String, Object> map);
}
