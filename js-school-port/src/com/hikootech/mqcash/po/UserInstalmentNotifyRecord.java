package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月13日
 * 用户分期通知记录
 */
public class UserInstalmentNotifyRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String notifyId;
	private String result;//合作伙伴调用结果：0失败 1成功
	private String tradeNo;
	private Integer tradeStatus;//秒趣平台分期付款处理的结果:0秒趣分期成功,1其他失败（征信失败）
	private String partnerTransactionId;
	private String partnerOrderId;
	private BigDecimal partnerOrderAmount;
	private String notifyUrl;
	private Integer notifyStatus;//通知状态：0 未通知 1已通知 2通知失败
	private Date createTime;
	private Date notifyTime;
	private String operator;
	
	public UserInstalmentNotifyRecord() {
		// TODO Auto-generated constructor stub
	}

	public UserInstalmentNotifyRecord(String notifyId, String result,
			String tradeNo, Integer tradeStatus, String partnerTransactionId,
			String partnerOrderId, BigDecimal partnerOrderAmount,
			String notifyUrl, Integer notifyStatus, Date createTime,
			Date notifyTime, String operator) {
		super();
		this.notifyId = notifyId;
		this.result = result;
		this.tradeNo = tradeNo;
		this.tradeStatus = tradeStatus;
		this.partnerTransactionId = partnerTransactionId;
		this.partnerOrderId = partnerOrderId;
		this.partnerOrderAmount = partnerOrderAmount;
		this.notifyUrl = notifyUrl;
		this.notifyStatus = notifyStatus;
		this.createTime = createTime;
		this.notifyTime = notifyTime;
		this.operator = operator;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(Integer tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getPartnerTransactionId() {
		return partnerTransactionId;
	}

	public void setPartnerTransactionId(String partnerTransactionId) {
		this.partnerTransactionId = partnerTransactionId;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public BigDecimal getPartnerOrderAmount() {
		return partnerOrderAmount;
	}

	public void setPartnerOrderAmount(BigDecimal partnerOrderAmount) {
		this.partnerOrderAmount = partnerOrderAmount;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Integer getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Integer notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
