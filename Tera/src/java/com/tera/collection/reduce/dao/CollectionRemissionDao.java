package com.tera.collection.reduce.dao;

import java.util.List;
import java.util.Map;

import com.tera.collection.reduce.model.CollectionRemission;
import com.tera.collection.reduce.model.form.RemissionQBean;
import com.tera.payment.model.LoanRepayPlan;

/**
 * 
 * 减免申请审批表DAO
 * <b>功能：</b>CollectionRemissionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:48:41<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface CollectionRemissionDao {
		
	public void add(CollectionRemission obj);	
	
	public void update(CollectionRemission obj);
	
	public void updateOnlyChanged(CollectionRemission obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<CollectionRemission> queryList(Map<String, Object> map);

	public CollectionRemission queryByKey(Object obj);

	public int queryRemissionApplyCount(Map<String, Object> map);
	
	public CollectionRemission queryLatestApplyByContractNo(String contractNo);
	
	public List<RemissionQBean> queryRemissionApplyList (Map<String, Object> map);
	
	public int queryRemissionReviewCount(Map<String, Object> map);
	
	public List<RemissionQBean> queryRemissionReviewList (Map<String, Object> map);
	
	public int queryRemissionApprovalCount(Map<String, Object> map);
	
	public List<RemissionQBean> queryRemissionApprovalList (Map<String, Object> map);
	
	public List<LoanRepayPlan> queryLatePlanList(Map<String, Object> map);
}
