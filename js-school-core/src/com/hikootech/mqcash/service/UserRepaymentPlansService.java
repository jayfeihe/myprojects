package com.hikootech.mqcash.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.dto.UserForSendSmDTO;
import com.hikootech.mqcash.dto.UserRepaymentPlansDTO;
import com.hikootech.mqcash.exception.MQException;
import com.hikootech.mqcash.po.UserPaymentOrder;
import com.hikootech.mqcash.po.UserRepaymentPlans;

/** 
* @ClassName IUserRepaymentPlansService 
* @Description 用户还款计划业务层
* @author 余巍 yuweiqwe@126.com 
* @date 2016年1月20日 下午5:35:35 
*   
*/
public interface UserRepaymentPlansService {
	
	/** 
	* @Title queryUserRepaymentPlansById 
	* @Description 根据还款计划ID查询计划信息、分期单本金金额、逾期违约金费率
	* @param 参数列表 
	* @param plansId
	* @throws MQException 
	* @return 返回类型 UserRepaymentPlansDTO	
	*/
	public UserRepaymentPlansDTO queryUserRepaymentPlansById(String plansId) throws MQException;
	
	/** 
	* @Title queryUserRepaymentPlansTotalRow 
	* @Description 查询还款计划总数：startTime（计划还款时间）、endTime（计划还款时间）、plansStatus（计划状态）、overdueFlag（逾期标志）、repayLock（是否代收锁）
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 Long	
	*/
	public Long queryUserRepaymentPlansTotalRow(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title queryUserRepaymentPlansList 
	* @Description 查询还款计划列表：startTime（计划还款时间）、endTime（计划还款时间）、plansStatus（计划状态）、overdueFlag（逾期标志）、repayLock（是否代收锁）
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 List<UserRepaymentPlansDTO>	
	*/
	public List<UserRepaymentPlansDTO> queryUserRepaymentPlansList(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title queryOverdueUserRepaymentPlansTotalRow 
	* @Description 查询还款计划总数：startTime（计划还款时间）、endTime（计划还款时间）、plansStatus（计划状态）、overdueFlag（逾期标志）、repayLock（是否代收锁）
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 Long	
	*/
	public Long queryOverdueUserRepaymentPlansTotalRow(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title queryOverdueUserRepaymentPlansList 
	* @Description 查询还款计划列表：startTime（计划还款时间）、endTime（计划还款时间）、plansStatus（计划状态）、overdueFlag（逾期标志）、repayLock（是否代收锁）
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 List<UserRepaymentPlansDTO>	包含还款计划、分期订单信息
	*/
	public List<UserRepaymentPlansDTO> queryOverdueUserRepaymentPlansList(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title recoverUserRepaymentPlansDealingStatus 
	* @Description 当代收失败时，需要将计划状态为处理中的还款计划的计划状态恢复，恢复后状态可能为：待还款、已逾期
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void recoverUserRepaymentPlansDealingStatus(UserRepaymentPlans plan) throws MQException;
	
	/** 
	* @Title updateUserRepaymentPlansDealingToOverdue 
	* @Description 将计划状态为处理中的还款计划的计划状态修改为：已逾期
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void updateUserRepaymentPlansDealingToOverdue(String plansId) throws MQException;
	
	/** 
	* @Title updateUserRepaymentPlansToWaiting 
	* @Description 将计划状态为处理中的还款计划的计划状态修改为：待还款
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void updateUserRepaymentPlansDealingToWaiting(String plansId) throws MQException;
	
	/** 
	* @Title updateUserRepaymentPlansWaitingToPaySuccess 
	* @Description 将计划状态为待还款的还款计划的计划状态修改为：已还款
	* @param 参数列表 
	* @param plan
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void updateUserRepaymentPlansWaitingToPaySuccess(UserRepaymentPlans plan, UserPaymentOrder paymentOrder) throws MQException;
	
	/** 
	* @Title updateUserRepaymentPlansWaitingToOverdue 
	* @Description 将计划状态为待还款的还款计划的计划状态修改为：已逾期
	* @param 参数列表 
	* @param plansId
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void updateUserRepaymentPlansWaitingToOverdue(String plansId) throws MQException;
	
	/** 
	* @Title updateUserRepaymentPlansDealingToPaySuccess 
	* @Description 将计划状态为处理中的还款计划的计划状态修改为：已还款
	* @param 参数列表 
	* @param plan
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void updateUserRepaymentPlansDealingToPaySuccess(UserRepaymentPlans plan, UserPaymentOrder paymentOrder) throws MQException;
	
	/** 
	* @Title updateUserRepaymentPlansOverdueToPaySuccess 
	* @Description 将计划状态为已逾期的还款计划的计划状态修改为：已还款
	* @param 参数列表 
	* @param plan
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void updateUserRepaymentPlansOverdueToPaySuccess(UserRepaymentPlans plan, UserPaymentOrder paymentOrder) throws MQException;
	
	/** 
	* @Title queryUserRepaymentPlansOfInstalment 
	* @Description 查询还款计划列表：instalmentId（分期ID）、startTime（计划还款时间）、endTime（计划还款时间）、plansStatus（计划状态）、overdueFlag（逾期标志）、repayLock（是否代收锁）
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 List<UserRepaymentPlansDTO>	
	*/
	public List<UserRepaymentPlans> queryUserRepaymentPlansOfInstalment(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title releaseUserRepaymentPlansLock 
	* @Description TODO(这里用一句话描述这个方法的作用) 
	* @param 参数列表 
	* @param instalmentId
	* @return
	* @throws MQException 
	* @return 返回类型 Long	
	*/
	public Long releaseUserRepaymentPlansLock(String instalmentId) throws MQException;
	
	/** 
	* @Title checkOverdue 
	* @Description 检查startTime、endTime时间范围内还款计划是否逾期
	* @param 参数列表 
	* @param startTime
	* @param endTime
	* @throws MQException 
	* @return 返回类型 Long	
	*/
	public Long checkOverdue(Date startTime, Date endTime) throws MQException;
	
	/** 
	* @Title calOverdue 
	* @Description 计算逾期手续费
	* @param 参数列表 
	* @param plansDTO
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void calOverdue(UserRepaymentPlansDTO plansDTO) throws MQException;
	
	/** 
	* @Title queryOverdueRepaymentPlansInstalmentIdList 
	* @Description 查询逾期未锁定未还款完成的还款计划对应分期ID列表
	* @param 参数列表 
	* @param startTime
	* @param endTime
	* @throws MQException 
	* @return 返回类型 List<String>	
	*/
	public List<String> queryOverdueRepaymentPlansInstalmentIdList(Date startTime, Date endTime) throws MQException;
	
	/** 
	* @Title lockOverduePlans 
	* @Description 逾期代收锁定：根据分期订单号判断对应分期还款计划是否已逾期、未锁定，如果是，锁定该分期订单对应所有分期还款计划
	* @param 参数列表 
	* @param instalmentId
	* @throws MQException 
	* @return 返回类型 void	
	*/
	public void lockOverduePlans(String instalmentId) throws MQException;
	
	/** 
	* @Title queryPlansToRemindNum 
	* @Description 查询用户状态是有效的，用户有待还款的、未逾期的、未代收锁的、计划还款时间一定范围的还款计划数目
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 int	
	*/
	public int queryPlansToRemindNum(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title queryPlansToRemind 
	* @Description 查询待提醒的短信信息
	* @param 参数列表 
	* @param paramMap
	* @throws MQException 
	* @return 返回类型 List<UserForSendSmDTO>	
	*/
	public List<UserForSendSmDTO> queryPlansToRemind(Map<String, Object> paramMap) throws MQException;
	
	/** 
	* @Title judgeInstalmentCompletedByPlans 
	* @Description 根据还款计划判断分期是否完成，当分期订单状态不为待还款和已逾期时，不修改分期订单状态
	* @param 参数列表 
	* @param plan
	* @throws MQException 
	* @return 返回类型 Map<String,Object>	
	*/
	public Map<String, Object> judgeInstalmentCompletedByPlans(UserRepaymentPlans plan) throws MQException;
	
}
