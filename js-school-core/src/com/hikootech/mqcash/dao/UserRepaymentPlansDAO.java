package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.UserForSendSmDTO;
import com.hikootech.mqcash.dto.UserRepaymentPlansDTO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.UserRepaymentPlans;

/**  
 *   
 * UserPayOrderRepayPlansDAO  
 *   
 * @function:(1362-市场订单单笔代收交易查询DAO)  
 * @create time:Sep 17, 2015 2:03:44 PM   
 * @version 1.0.0  
 * @author:张海达    
 */
public interface UserRepaymentPlansDAO {
	
	/** 
	* @Title queryUserRepaymentPlansById 
	* @Description 根据还款计划ID查询计划信息、分期单本金金额、逾期违约金费率
	* @param 参数列表 
	* @param plansId
	* @throws MQException 
	* @return 返回类型 UserRepaymentPlansDTO	
	*/
	public UserRepaymentPlansDTO queryUserRepaymentPlansById(String plansId) throws Exception;
	
	/** 
	* @Title queryUserRepaymentPlansTotalRow 
	* @Description TODO(这里用一句话描述这个方法的作用) 
	* @param 参数列表
	* @return
	* @throws Exception 
	* @return Long	返回类型 
	*/
	public Long queryUserRepaymentPlansTotalRow(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title queryUserRepaymentPlans 
	* @Description TODO(这里用一句话描述这个方法的作用) 
	* @param 参数列表
	* @return
	* @throws Exception 
	* @return List<UserRepaymentPlansDTO>	返回类型 
	*/
	public List<UserRepaymentPlansDTO> queryUserRepaymentPlansList(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title queryOverdueUserRepaymentPlansTotalRow 
	* @Description 查询还款计划总数：startTime（计划还款时间）、endTime（计划还款时间）、plansStatus（计划状态）、overdueFlag（逾期标志）、repayLock（是否代收锁）
	* @param 参数列表 
	* @param paramMap
	* @throws Exception 
	* @return 返回类型 Long	
	*/
	public Long queryOverdueUserRepaymentPlansTotalRow(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title queryOverdueUserRepaymentPlansList 
	* @Description 查询还款计划列表：startTime（计划还款时间）、endTime（计划还款时间）、plansStatus（计划状态）、overdueFlag（逾期标志）、repayLock（是否代收锁）
	* @param 参数列表 
	* @param paramMap
	* @throws Exception 
	* @return 返回类型 List<UserRepaymentPlansDTO>	包含还款计划、分期订单信息
	*/
	public List<UserRepaymentPlansDTO> queryOverdueUserRepaymentPlansList(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title updateRepaymentPlansToPaying 
	* @Description 更新还款计划为还款中
	* @param 参数列表
	* @param repaymentPlansId
	* @return
	* @throws Exception 
	* @return int	返回类型 
	*/
	public int updateRepaymentPlansToPaying(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title updateUserRepaymentPlansStatus 
	* @Description 根据还款计划ID和当前状态，修改还款计划状态
	* @param 参数列表 
	* @param paramMap
	* @throws Exception 
	* @return 返回类型 int	
	*/
	public int updateUserRepaymentPlansStatus(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title queryUserRepaymentPlansListByPaymentOrderId 
	* @Description 根据支付订单号id（支付流水号）查询对应的还款计划列表
	* @param 参数列表 
	* @param paymentOrderId
	* @return
	* @throws Exception 
	* @return 返回类型 List<UserRepaymentPlans>	
	*/
	public List<UserRepaymentPlans> queryUserRepaymentPlansListByPaymentOrderId(String paymentOrderId) throws Exception;
	
	/** 
	* @Title queryUserRepaymentPlansOfInstalment 
	* @Description 查询还款计划列表：instalmentId（分期ID）、startTime（计划还款时间）、endTime（计划还款时间）、plansStatus（计划状态）、overdueFlag（逾期标志）、repayLock（是否代收锁）
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 List<UserRepaymentPlansDTO>	
	*/
	public List<UserRepaymentPlans> queryUserRepaymentPlansOfInstalment(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title releaseUserRepaymentPlansLock 
	* @Description 更新分期订单对应所有还款计划的代收锁为：不锁定 
	* @param 参数列表 
	* @param instalmentId
	* @return
	* @throws Exception 
	* @return 返回类型 Long	
	*/
	public Long releaseUserRepaymentPlansLock(String instalmentId) throws Exception;
	
	/** 
	* @Title queryPlanToRemindByPlanId 
	* @Description 根据还款计划ID，查询代收失败发送短信需要的信息
	* @param 参数列表 
	* @param plansId
	* @throws Exception 
	* @return 返回类型 UserForSendSmDTO	
	*/
	public UserForSendSmDTO queryPlanToRemindByPlanId(String plansId) throws Exception;
	
	/** 
	* @Title updateUserRepaymentPlansToOverdue 
	* @Description 更新还款计划为逾期
	* @param 参数列表 
	* @param paramMap
	* @throws Exception 
	* @return 返回类型 Long	
	*/
	public Long updateUserRepaymentPlansToOverdue(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title updateRepaymentPlansOverdueAmount 
	* @Description 根据还款计划ID，更新逾期未还款的计划的应收逾期罚息、应收总金额
	* @param 参数列表 
	* @param plansDTO
	* @throws Exception 
	* @return 返回类型 Long	
	*/
	public Long updateRepaymentPlansOverdueAmount(UserRepaymentPlansDTO plansDTO) throws Exception;
	
	/** 
	* @Title queryOverdueRepaymentPlansInstalmentIdList 
	* @Description 查询逾期未锁定未还款完成的还款计划对应分期ID列表
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 List<String>	
	*/
	public List<String> queryOverdueRepaymentPlansInstalmentIdList(Map<String, Object> paramMap) throws Exception;
	
	/** 
	* @Title updateRepayLockByInstalmentId 
	* @Description 锁定分期订单对应所有的还款计划
	* @param 参数列表 
	* @param instalmentId
	* @throws Exception 
	* @return 返回类型 long	
	*/
	public long updateRepayLockByInstalmentId(String instalmentId) throws Exception;
	
	/** 
	* @Title queryPlansToRemindNum 
	* @Description 查询用户状态是有效的，用户有待还款的、未逾期的、未代收锁的、计划还款时间一定范围的还款计划数目
	* @param 参数列表 
	* @param paramMap
	* @throws Exception 
	* @return 返回类型 int	
	*/
	public int queryPlansToRemindNum(Map<String, Object> paramMap) throws Exception;

	/** 
	* @Title queryPlansToRemind 
	* @Description 查询待提醒的短信信息
	* @param 参数列表 
	* @param paramMap
	* @return 返回类型 List<UserForSendSmDTO>	
	*/
	public List<UserForSendSmDTO> queryPlansToRemind(Map<String, Object> paramMap);
	
	/** 
	* @Title queryInstalmentIdFromRepaymentPlans 
	* @Description 从还款计划中查询分期去重复的订单ID
	* @param 参数列表 
	* @param paramMap
	* @throws Exception 
	* @return 返回类型 List<String>	
	*/
	public List<String> queryInstalmentIdFromRepaymentPlans(Map<String, Object> paramMap) throws Exception;
	
}
