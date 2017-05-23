package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yuwei 2015年9月12日 用户主动和被动支付订单表：记录用户主动提交支付申请信息以及支付结果、
 *         用户被动（秒趣委托第三方代收）支付信息以及支付结果。
 */
public class UserPaymentOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 支付流水号,和第三方通讯关联的支付交易流水号  */
	private String userPaymentOrderId;
	/** 支付类型：0被动支付（代收） 1主动支付（用户主动还款） */
	private Integer paymentType;
	/** 支付方式：哪一个第三方 */
	private String paymentChannelId;
	/** 用户id */
	private String userId;
	/** 支付使用绑定银行卡id */
	private String bankCardId;
	 
	private Integer requestStatus;
	/** 给第三方的订单号 */
	private String paymentOrderNo;
	/** 账户名称 */
	private String accountName;
	/** 账户号码 */
	private String accountNumber;
	/** 证件号码 */
	private String identificationNumber;
	/** 支付金额 */
	private BigDecimal paymentAmount;
	/** 支付服务手续费 */
	private BigDecimal paymentFee;
	/** 支付状态：0未支付 1处理中 2支付成功 3支付失败 4已退款 */
	private Integer paymentStatus;
	/** 实际支付时间（第三方接受到银行的通知时间） */
	private Date paymentTime;
	/** 还款笔数 */
	private Integer paymentCount;
	/** 付款银行标识，参考《银行编码表》 */
	private String bankId;
	/** 付款银行名称 */
	private String bankName;
	/** 创建时间 */
	private Date createTime;
	/** 银行处理时间 */
	private Date bankTxTime;
	/** 修改时间 */
	private Date updateTime;
	/** 描述备注 */
	private String descp;
	
	/**收款完毕后发送短信的手机号*/
	private String phoneNumber;
 
	public UserPaymentOrder() {
		// TODO Auto-generated constructor stub
	}
	public String getUserPaymentOrderId() {
		return userPaymentOrderId;
	}
	public void setUserPaymentOrderId(String userPaymentOrderId) {
		this.userPaymentOrderId = userPaymentOrderId;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentChannelId() {
		return paymentChannelId;
	}
	public void setPaymentChannelId(String paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBankCardId() {
		return bankCardId;
	}
	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}
	public Integer getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(Integer requestStatus) {
		this.requestStatus = requestStatus;
	}
	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}
	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public BigDecimal getPaymentFee() {
		return paymentFee;
	}
	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = paymentFee;
	}
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Integer getPaymentCount() {
		return paymentCount;
	}
	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getBankTxTime() {
		return bankTxTime;
	}
	public void setBankTxTime(Date bankTxTime) {
		this.bankTxTime = bankTxTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
