package com.tera.contract.dao;

import java.util.List;
import java.util.Map;

import com.tera.contract.model.Contract;

/**
 * 
 * 合同表DAO
 * <b>功能：</b>ContractDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-12-09 16:22:06<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface ContractDao {
		
	public void add(Contract obj);	
	
	public void update(Contract obj);
	
	public void updateOnlyChanged(Contract obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<Contract> queryList(Map<String, Object> map);

	public Contract queryByKey(Object obj);

	//杨长收添加
	//只查class=01的合同
	public Contract queryByAppId(Object t);
	
	public String getContractCode();
	
	public List<Contract> getContractByAppId(Map<String, Object> map);
	
	/**
	 * 根据申请号查询历史借款情况
	 * @param appId
	 * @return
	 */
	public List<Contract> queryHistoryList(String appId);
	
	/**
	 * 根据 根据合同查询  还款情况
	 * @param map
	 * 		mapkey
	 * 			contractNo		合同编码
	 * 			processer 		流程待处理人
	 * 			orgId			机构编码
	 * 			states			包含状态 字符串 数组 
	 * 			nonStates		不包含状态 字符串 数组 
	 * 			name 			姓名
	 * 			idType 			证件号码
	 * 			idNo 			证件号码
	 * @return
	 */
	public List<Contract> queryContractRepayPlanList(Map<String, Object> map)throws Exception;
	public int queryContractRepayPlanCount(Map<String, Object> map)throws Exception;

	/**
	 *  查询拒贷列表(功能区模块的)
	 * @param map
	 * 		mapkey
	 * 			appId			申请编号
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 * 			inputTimeMin	进件时间开始
	 * 			inputTimeMax	进件时间结束
	 *			product 		产品
	 *			orgId  			营业部
	 *			platformName	平台名称(如：积木盒子)
	 * @return
	 */
	public List<Contract> queryCreditRepelLoanList(Map<String, Object> map);
	public int queryCreditRepelLoanCount(Map<String, Object> map);
	
	
	
	
	/**
	 *  查询拒贷列表(功能区模块的)
	 * @param map
	 * 		mapkey
	 * 			appId			申请编号
	 *			orgId  			营业部
	 * 			contractNo		合同编号
	 * 			name 			姓名
	 * 			idNo 			证件号码
	 *			appStates  		申请状态 数组
	 *			contractStates  合同状态数组
	 * @return
	 */
	public List<Contract> queryManageList(Map<String, Object> map);
	public int queryManageCount(Map<String, Object> map);	
	
	
	
	
	
	
	
	
	
}
