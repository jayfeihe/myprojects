package com.hikootech.mqcash.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
 *   
 * UserRepaymentPlansDTO  
 *   
 * @function:(用户分期订单表)  
 * @create time:Sep 15, 2015 10:34:47 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class UserRepaymentPlansDTO implements Serializable {

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
	 * 分期单费率
	 */
	private BigDecimal overDueRate;
	
		/**
		* 订单金额
		*/
		
	private BigDecimal partnerOrderAmount;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 第几期
	 */
	private Integer instalmentNumber;
	
	/**
	 * 本期应还总金额
	 */
	private BigDecimal receivableAmount;
	
	/**
	 * 本期实收总金额
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
	 * 分期计划状态：0待还款 1已还清 2部分还款（作为查询条件）
	 */
	private Integer plansStatusC;
	/**
	 * 分期计划状态：0待还款 1已还清 2部分还款
	 */
	private Integer plansStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 计划还款时间
	 */
	private Date planRepaymentTime;
	/**
	 * 实际还款时间
	 */
	private Date realRepaymentTime;
	private String operator;
	
	/**
	 * 绑定手机
	 */
	private String bindMobile;
	
	
	public UserRepaymentPlansDTO() {
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


	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getPlansStatusC() {
		return plansStatusC;
	}

	public void setPlansStatusC(Integer plansStatusC) {
		this.plansStatusC = plansStatusC;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getOverDueRate() {
		return overDueRate;
	}

	public void setOverDueRate(BigDecimal overDueRate) {
		this.overDueRate = overDueRate;
	}

	public BigDecimal getPartnerOrderAmount() {
		return partnerOrderAmount;
	}

	public void setPartnerOrderAmount(BigDecimal partnerOrderAmount) {
		this.partnerOrderAmount = partnerOrderAmount;
	}

	public String getBindMobile() {
		return bindMobile;
	}

	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
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
	
}
