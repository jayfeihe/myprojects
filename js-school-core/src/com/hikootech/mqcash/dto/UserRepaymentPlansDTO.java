package com.hikootech.mqcash.dto;

import java.math.BigDecimal;
import java.util.Date;

/** 
* @ClassName UserRepaymentPlansDTO 
* @Description 用户还款计划DTO
* @author 余巍 yuweiqwe@126.com 
* @date 2016年1月20日 下午3:46:44 
*  
*/
public class UserRepaymentPlansDTO {
	
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
	 * 分期计划状态：10初始化 20待还款 30还款中 40已还款 50已逾期 60已取消 70已中止 80待退款
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
	
	
	/** 
	* @Fields bankCardId : 绑定银行卡id
	*/ 
	private String bankCardId;
	/**
	 * 第三方银行id
	 */
	private String thirdPartyBankId;
	/**
	 * 持卡人身份证号
	 */
	private String ownerIdCard;
	/**
	 * 持卡人姓名
	 */
	private String ownerName;
	/**
	 * 卡片类型 0储蓄卡 1信用卡
	 */
	private Integer cardType;
	/**
	 * 卡号
	 */
	private String cardNumber;
	/**
	 * 预留手机号
	 */
	private String reserveMobile;
	
	/**
	 * 商户电渠订单金额（分期本金）
	 */
	private BigDecimal partnerOrderAmount;
	/**
	 * 逾期费率
	 */
	private BigDecimal overdueRate;
	
	/**
	 * 银行名称
	 */
	private String bankName;
	
	/** 
	* @Fields id : 还款失败还款计划记录ID
	*/ 
	private String id;
	/** 
	* @Fields repeatRepaymentTimes : 还款失败还款计划记录重复代收次数
	*/ 
	private String repeatRepaymentTimes;
	
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

	public Integer getInstalmentCount() {
		return instalmentCount;
	}

	public void setInstalmentCount(Integer instalmentCount) {
		this.instalmentCount = instalmentCount;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getThirdPartyBankId() {
		return thirdPartyBankId;
	}

	public void setThirdPartyBankId(String thirdPartyBankId) {
		this.thirdPartyBankId = thirdPartyBankId;
	}

	public String getOwnerIdCard() {
		return ownerIdCard;
	}

	public void setOwnerIdCard(String ownerIdCard) {
		this.ownerIdCard = ownerIdCard;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getReserveMobile() {
		return reserveMobile;
	}

	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}

	public BigDecimal getPartnerOrderAmount() {
		return partnerOrderAmount;
	}

	public void setPartnerOrderAmount(BigDecimal partnerOrderAmount) {
		this.partnerOrderAmount = partnerOrderAmount;
	}

	public BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getRepayLock() {
		return repayLock;
	}

	public void setRepayLock(Integer repayLock) {
		this.repayLock = repayLock;
	}
	
}
