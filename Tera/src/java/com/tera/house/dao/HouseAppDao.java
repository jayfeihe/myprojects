package com.tera.house.dao;

import java.util.List;
import java.util.Map;

import com.tera.house.model.form.RepeatCheckQBean;
import com.tera.house.model.HouseApp;

/**
 * 
 * 信用贷款申请表DAO
 * <b>功能：</b>HouseAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-04-10 16:11:54<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface HouseAppDao {
		
	public void add(HouseApp obj);	
	
	public void update(HouseApp obj);
	
	public void updateOnlyChanged(HouseApp obj);
		
	public void delete(int obj);

	public int queryCount(Map<String, Object> map);
	
	public List<HouseApp> queryList(Map<String, Object> map);

	public HouseApp queryByKey(Object obj);
	
	/**
	 * 得到编码序号
	 * @return
	 */
	public String getAppIdCode();
	
	/**
	 * 根据 流程节点  用户  机构 查询  申请列表
	 * @param map
	 * 		mapkey
	 * 			appId			申请编号
	 * 			appCode			申请编码
	 * 			bpmStates  		包含流程节点  字符串数组
	 * 			nonBpmStates 	不包含流程节点  字符串数组
	 * 			processer 		流程待处理人
	 * 			nonProcesser 	非流程待处理人
	 * 			operator		操作员
	 * 			customerManager	客户经理
	 * 			orgId			机构编码
	 * 			states			包含状态 字符串 数组 
	 * 			nonStates		不包含状态 字符串 数组 
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 * 			mobile 			手机号
	 * 			amountMin 		金额下限
	 * 			amountMax 		金额上限
	 * 			createTimeMin 	申请时间下限
	 * 			createTimeMax 	申请时间上限
	 * 			inputTimeMin 	申请时间下限
	 * 			inputTimeMax 	申请时间上限
	 *  		updateTimeMin 	更改时间下限
	 * 			updateTimeMax 	更改时间上限
	 * 			staffNo			营销人员 工号
	 * @return
	 */
	public List<HouseApp> queryBpmLoanAppList(Map<String, Object> map);
	public int     queryBpmLoanAppCount(Map<String, Object> map);

	
	/**
	 *  根据 流程节点  用户  机构 合同 查询  申请列表
	 * @param map
	 * 		mapkey
	 * 			appId			申请编号
	 * 			contractNo		合同编码
	 *			decisionAmount	最终决策金额
	 *			decisionProduct 最终决策产品
	 *			decisionPeriod  最终决策期数
	 *			decisionOperator最终决策人
	 * 			bpmStates  		包含流程节点  字符串数组
	 * 			nonBpmStates 	不包含流程节点  字符串数组
	 * 			processer 		流程待处理人
	 * 			nonProcesser 	非流程待处理人
	 * 			operator		操作员
	 * 			customerManager	客户经理
	 * 			orgId			机构编码
	 * 			states			包含状态 字符串 数组 
	 * 			nonStates		不包含状态 字符串 数组 
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 * 			mobile 			手机号
	 * 			amountMin 		金额下限
	 * 			amountMax 		金额上限
	 * 			createTimeMin 	申请时间下限
	 * 			createTimeMax 	申请时间上限
	 * 			inputTimeMin 	申请时间下限
	 * 			inputTimeMax 	申请时间上限
	 *  		updateTimeMin 	更改时间下限
	 * 			updateTimeMax 	更改时间上限
	 * 			staffNo			营销人员 工号
	 * @return
	 */
	public List<HouseApp> queryBpmAppAndContractList(Map<String, Object> map);
	public int queryBpmAppAndContractCount(Map<String, Object> map);
	
	/**
	 *  查询信用贷款列表(功能区模块的)
	 * @param map
	 * 		mapkey
	 * 			appId			申请编号
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 * 			inputTimeMin	进件时间开始
	 * 			inputTimeMax	进件时间结束
	 *			product 		产品
	 *			orgId  			营业部
	 *			stateTask		流程状态
	 * @return
	 */
	public List<HouseApp> queryHouseQueryList(Map<String, Object> map);
	public int queryHouseQueryCount(Map<String, Object> map);
	
	public List<HouseApp> getSaleReTimeFreezeList(HouseApp obj);
	
	public List<RepeatCheckQBean> repeatCheckQuery(Map<String, Object> map);
	
	public List<RepeatCheckQBean> coupleSeparateLoan(Map<String, Object> map);

	public HouseApp queryByContractNo(String contractNo);
	
}
