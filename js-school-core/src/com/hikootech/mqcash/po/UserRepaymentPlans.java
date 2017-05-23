package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月6日
 * 用户分期订单表
 */
public class UserRepaymentPlans implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 分期付款计划流水号
	 */
	private String repaymentPlansId;
	/**
	 * 分期付款订单号
	 */
	private String instalmentId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 第几期
	 */
	private Integer instalmentNumber;
	/**
	 * 共几期
	 */
	private Integer instalmentCount;
	
	/**
	* 本期应还总额
	*/
	private BigDecimal receivableAmount;
	
	/**
	* 本期实收总额
	*/
	private BigDecimal receivedAmount;
	/**
	 * 本期应还本金
	 */
	private BigDecimal receivablePrincipal;
	/**
	 * 本期应还本金
	 */
	private BigDecimal receivedPrincipal;
	/**
	 * 本期还款服务费
	 */
	private BigDecimal receivableService;
	/**
	 * 本期还款服务费
	 */
	private BigDecimal receivedService;
	/**
	 * 本期逾期罚息
	 */
	private BigDecimal receivableOverdue;
	/**
	 * 本期逾期罚息
	 */
	private BigDecimal receivedOverdue;
	/**
	 * 减免的逾期罚息
	 */
	private BigDecimal reduceOverdue;
	/**
	 * 分期计划状态：0待还款 1已还清 2部分还款
	 */
	private Integer plansStatus;
	/** 
	* @Fields repayLock : 支付锁
	*/ 
	private Integer repayLock;
	/**
	 * 计划还款时间
	 */
	private Date planRepaymentTime;
	/**
	 * 实际还款时间
	 */
	private Date realRepaymentTime;
	
	private Date createTime;
	private Date updateTime;
	private String operator;
	
	public UserRepaymentPlans() {
		// TODO Auto-generated constructor stub
	}

	public String getRepaymentPlansId() {
		return repaymentPlansId;
	}

	public void setRepaymentPlansId(String repaymentPlansId) {
		this.repaymentPlansId = repaymentPlansId;
	}

	public String getInstalmentId() {
		return instalmentId;
	}

	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getInstalmentNumber() {
		return instalmentNumber;
	}

	public void setInstalmentNumber(Integer instalmentNumber) {
		this.instalmentNumber = instalmentNumber;
	}

	public BigDecimal getReceivablePrincipal() {
		return receivablePrincipal;
	}

	public void setReceivablePrincipal(BigDecimal receivablePrincipal) {
		this.receivablePrincipal = receivablePrincipal;
	}

	public BigDecimal getReceivedPrincipal() {
		return receivedPrincipal;
	}

	public void setReceivedPrincipal(BigDecimal receivedPrincipal) {
		this.receivedPrincipal = receivedPrincipal;
	}

	public BigDecimal getReceivableService() {
		return receivableService;
	}

	public void setReceivableService(BigDecimal receivableService) {
		this.receivableService = receivableService;
	}

	public BigDecimal getReceivedService() {
		return receivedService;
	}

	public void setReceivedService(BigDecimal receivedService) {
		this.receivedService = receivedService;
	}

	public BigDecimal getReceivableOverdue() {
		return receivableOverdue;
	}

	public void setReceivableOverdue(BigDecimal receivableOverdue) {
		this.receivableOverdue = receivableOverdue;
	}

	public BigDecimal getReceivedOverdue() {
		return receivedOverdue;
	}

	public void setReceivedOverdue(BigDecimal receivedOverdue) {
		this.receivedOverdue = receivedOverdue;
	}

	public BigDecimal getReduceOverdue() {
		return reduceOverdue;
	}

	public void setReduceOverdue(BigDecimal reduceOverdue) {
		this.reduceOverdue = reduceOverdue;
	}

	public Integer getPlansStatus() {
		return plansStatus;
	}

	public void setPlansStatus(Integer plansStatus) {
		this.plansStatus = plansStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPlanRepaymentTime() {
		return planRepaymentTime;
	}

	public void setPlanRepaymentTime(Date planRepaymentTime) {
		this.planRepaymentTime = planRepaymentTime;
	}

	public Date getRealRepaymentTime() {
		return realRepaymentTime;
	}

	public void setRealRepaymentTime(Date realRepaymentTime) {
		this.realRepaymentTime = realRepaymentTime;
	}

	public Integer getRepayLock() {
		return repayLock;
	}

	public void setRepayLock(Integer repayLock) {
		this.repayLock = repayLock;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getInstalmentCount() {
		return instalmentCount;
	}

	public void setInstalmentCount(Integer instalmentCount) {
		this.instalmentCount = instalmentCount;
	}
	
}
