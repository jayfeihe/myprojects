package com.tera.lend.dao;

import java.util.List;
import java.util.Map;

import com.tera.lend.model.LendApp;

/**
 * 
 * 财富端申请表DAO
 * <b>功能：</b>LendAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-30 14:19:25<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public interface LendAppDao {
		
	public void add(LendApp obj);	
	
	public void update(LendApp obj);
	
	public void updateOnlyChanged(LendApp obj);
		
	public void delete(Object obj);

	public int queryCount(Map<String, Object> map);
	
	public List<LendApp> queryList(Map<String, Object> map);

	public LendApp queryByKey(Object obj);
	
	public LendApp queryByAppId(String appId);
	
	public List<LendApp> getLendListByContractNo(Object t);
	
	public List<LendApp> getLendListByAppId(Object t);
	
	public String getAppIdCode();
	
	public int queryBpmLendAppCount(Map<String, Object> map);
	/**
	 * 根据 流程节点  用户  机构 查询  申请列表
	 * @param map
	 * 		mapkey
	 * 			states      状态列表
	 * 			bpmStates   	流程节点列表
	 * 			userLoginId 用户登录名         找到 安排给次用户的 待处理信息
	 * 			orgId		机构编码
	 * 			roleLever 角色等级
	 * 			customerManager 客户经理
	 * 			name 	姓名/机构全称
	 * 			idNo 	证件号码
	 * 			mobile 	手机号
	 * 			amountMin 	金额下限
	 * 			amountMax 	金额上限
	 * 			createTimeMin 	申请时间下限
	 * 			createTimeMax 	申请时间上限
	 * @return
	 */
	public List<LendApp> queryBpmLendAppList(Map<String, Object> map);
	public int queryDivestCount(Map<String, Object> queryMap);

	public List<LendApp> queryDivestList(Map<String, Object> queryMap);

}
