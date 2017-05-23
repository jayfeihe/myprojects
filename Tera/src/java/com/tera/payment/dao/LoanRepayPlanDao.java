package com.tera.payment.dao;

import java.util.List;
import java.util.Map;

import com.tera.payment.model.LoanRepayPlan;

/**
 * 
 * <br>
 * <b>功能：</b>LoanRepayPlanDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-07-04 15:27:51<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LoanRepayPlanDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);

	public List<T> queryList(Map<String, Object> map);
	
	
	public List<LoanRepayPlan> queryListExt(Map<String, Object> map);

	public T queryByKey(Object t);
	
	/**
	 * 查询未还款的记录，批处理使用
	 * @return
	 */
	public List<T> queryListPay(Map<String, Object> map); 

	/**
	 * 根据合同号删除 还款计划
	 * @param contractNo
	 */
	public void deleteByContractNo(String contractNo);
	
//	/**
//	 * 根据身份证号查询历史借款情况
//	 * @param idNo
//	 * @return
//	 */
//	public List<T> queryHistoryList(String idNo);
	
	public List<T> queryFyLoanStateList(Map<String, Object> map);
	public List<T> queryLateDaysCallList(Map<String, Object> map); 
	 /**
	  * 查询逾期的统计记录（催收-Jesse）
	  * @return
	  */
	public List<T> queryListLateStatistics(Map<String, Object> map);
	
	/**
	 * 排序查询某合同的所有逾期记录（Jesse）
	 * @param map
	 * @return
	 */
	public List<T> queryListLateByNo(Map<String, Object> map);
	/**
	 * 根据合同号查询当前所在期的信息（Jesse）
	 * @param map
	 * @return
	 */
	public List<T> queryCurInfo(Map<String, Object> map);
	/**
	 * 根据合同号查询历史逾期期数
	 * @param map
	 * @return
	 */
	public int queryLateHisNum(Map<String, Object> map);
	/**
	 * 根据合同号查询最后一期的信息
	 * @param map
	 * @return
	 */
	public List<T> queryLastInfo(Map<String, Object> map);
}
