package com.hikootech.mqcash.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
 *   
 * UserRepaymentPlansVO  
 *   
 * @function:(发送短信用户dto)  
 * @create time:Sep 15, 2015 10:34:47 AM   
 * @version 1.0.0  
 * @author:张海达    
 */
public class UserForSendSmDTO implements Serializable {

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
	 * 计划还款时间
	 */
	private Date planRepaymentTime;
	/**
	 * 绑定手机
	 */
	private String bindMobile;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 银行卡号
	 */
	private String cardNumber;
	/**
	 * 身份证号
	 */
	private String idCard;
	
	public UserForSendSmDTO() {
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

	public BigDecimal getPartnerOrderAmount() {
		return partnerOrderAmount;
	}

	public void setPartnerOrderAmount(BigDecimal partnerOrderAmount) {
		this.partnerOrderAmount = partnerOrderAmount;
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

	public Date getPlanRepaymentTime() {
		return planRepaymentTime;
	}

	public void setPlanRepaymentTime(Date planRepaymentTime) {
		this.planRepaymentTime = planRepaymentTime;
	}

	public String getBindMobile() {
		return bindMobile;
	}

	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
}
