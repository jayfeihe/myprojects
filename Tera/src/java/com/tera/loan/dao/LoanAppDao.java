package com.tera.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.loan.model.Lending;
import com.tera.loan.model.LoanApp;

/**
 * 
 * <br>
 * <b>功能：</b>LoanAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-05 18:50:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LoanAppDao<T> {	
		
	public void add(T t);	
	
	public void update(T t);
	
	public void updateOnlyChanged(T t);
		
	public void delete(Object t);	

	public int queryCount(Map<String, Object> map);
	
	public List<T> queryList(Map<String, Object> map);

	public T queryByKey(Object t);
	
	public String getAppIdCode();
	
	public String getContractCode();
	
	public void updateAppByAppId(T t);

	
	public int queryBpmLoanAppCount(Map<String, Object> map);
	public int querySignCount(Map<String, Object> map);
	/**
	 * 根据 流程节点  用户  机构 查询  申请列表
	 * @param map
	 * 		mapkey
	 * 			bpmStates  	流程节点  字符串数组
	 * 			userLoginId 用户登录名         找到 安排给次用户的 待处理信息
	 * 			orgId		机构编码
	 * 			states		状态 字符串 数组 
	 * 			name 	姓名/机构全称
	 * 			idNo 	证件号码
	 * 			mobile 	手机号
	 * 			amountMin 	金额下限
	 * 			amountMax 	金额上限
	 * 			createTimeMin 	申请时间下限
	 * 			createTimeMax 	申请时间上限

	 * @return
	 */
	public List<T> queryBpmLoanAppList(Map<String, Object> map);
	
	/**
	 * 根据 流程节点  用户  机构 查询  签约列表
	 * @param map
	 * 		mapkey
	 * 			userLoginId 用户登录名         找到 安排给次用户的 待处理信息
	 * 			orgId		机构编码
	 * 			states		状态 字符串 数组 
	 * 			name 	姓名/机构全称
	 * 			idNo 	证件号码
	 * 			mobile 	手机号
	 * 			amountMin 	金额下限
	 * 			amountMax 	金额上限
	 * 			createTimeMin 	申请时间下限
	 * 			createTimeMax 	申请时间上限

	 * @return
	 */
	public List<T> querySignList(Map<String, Object> map);
	
	
	
	
	public List<LoanApp> getAppByAppId(Map<String, Object> map);
	public List<String> getMainFlagListByAppId(Map<String, Object> map);
	public List<LoanApp> getLoanListByContractNo(Map<String, Object> map);
	
	
	//杨长收添加，组合查询处于放款状态的申请的条数
	public int queryLendingCount(Map<String, Object> map);		
	
	//杨长收添加，组合查询处于放款状态的申请的详细信息
	public List<Lending> queryLendingList(Map<String, Object> map);

	public List<LoanApp> queryPaymentByAppId(Map<String, Object> map);

}
