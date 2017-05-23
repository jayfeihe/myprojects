package com.hikootech.mqcash.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
 *   
 * UserPaymentOrderDTO  
 *   
 * @function:(已还款订单明细VO)  
 * @create time:Sep 18, 2015 3:48:35 PM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class UserPaymentOrderDTO implements Serializable {

	
	private static final long serialVersionUID = 2394685527838398505L;
	
	/** 支付流水号,和第三方通讯关联的支付交易流水号 */
	private String userPaymentOrderId;
	/** 支付类型：0被动支付（代收） 1主动支付（用户主动还款） */
	private Integer paymentType;
	/** 支付方式：网银支付 */
	private Integer paymentMethod;
	/** 支付方式：哪一个第三方 */
	private String paymentCompanyId;
	/** 用户id */
	private String userId;
	/** 支付使用绑定银行卡id */
	private String bankCardId;
	/** 发送状态:0请求失败 1请求成功 */
	private Integer requestStatus;
	/** 给第三方的订单号 */
	private String paymentOrderNo;
	 
	private String paymentChannelId;
	
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
	/** 支付状态：0未支付  1处理中 2支付成功 3支付失败 4已退款 */
	private Integer paymentStatus;
	/** 支付状态：0未支付  1处理中 2支付成功 3支付失败 4已退款 用于显示*/
	private String paymentStatusC;
	/** 实际支付时间（第三方接受到银行的通知时间） */
	private Date paymentTime;
	/** 还款笔数 */
	private Integer paymentCount;
	/** 付款银行标识，参考《银行编码表》 */
	private String bankId;
	/** 创建时间 */
	private Date createTime;
	/** 银行处理时间 */
	private Date bankTxTime;
	/** 修改时间 */
	private Date updateTime;
	/** 描述备注 */
	private String descp;
	
	//--------------------------------------
	/**  
	 * bankName:TODO（还款银行）  
	 */  
	private String bankName = "";
	
	/**  
	 * instalment_number:TODO（分期号）  
	 */  
	private String instalmentNumber = "" ;
	
	/**  
	 * instalmentCount:TODO（分期数）  
	 */  
	private String instalmentCount = "" ;
	
	/**  
	 * partnerChannel:TODO（订单来源）  
	 */  
	private String partnerChannel = "" ;
	/**  
	 * partnerChannel:TODO（还款时间）  
	 */  
	private String paymentTimeStr = "" ;
	
	/**  
	 * instalmentId:TODO（秒趣账单号）  
	 */  
	private String instalmentId = "" ;
	/**  
	 * partnerOrderId:TODO（网厅订单号）  
	 */  
	private String partnerOrderId = "" ;
	/**  
	 * productName:TODO（商品名称）  
	 */  
	private String productName = "" ;
	
	

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

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentCompanyId() {
		return paymentCompanyId;
	}

	public void setPaymentCompanyId(String paymentCompanyId) {
		this.paymentCompanyId = paymentCompanyId;
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

	public String getPaymentChannelId() {
		return paymentChannelId;
	}

	public void setPaymentChannelId(String paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
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

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}

	public String getPaymentStatusC() {
		return paymentStatusC;
	}

	public void setPaymentStatusC(String paymentStatusC) {
		this.paymentStatusC = paymentStatusC;
	}

	public String getInstalmentNumber() {
		return instalmentNumber;
	}

	public void setInstalmentNumber(String instalmentNumber) {
		this.instalmentNumber = instalmentNumber;
	}

	public String getInstalmentCount() {
		return instalmentCount;
	}

	public void setInstalmentCount(String instalmentCount) {
		this.instalmentCount = instalmentCount;
	}

	public String getPartnerChannel() {
		return partnerChannel;
	}

	public void setPartnerChannel(String partnerChannel) {
		this.partnerChannel = partnerChannel;
	}

	public String getPaymentTimeStr() {
		return paymentTimeStr;
	}

	public void setPaymentTimeStr(String paymentTimeStr) {
		this.paymentTimeStr = paymentTimeStr;
	}

	public String getInstalmentId() {
		return instalmentId;
	}

	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
