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
	private String instalmentId;
	private Integer dealStatus;//秒趣平台分期付款处理的结果:0秒趣分期成功,1其他失败（征信失败）
	private String partnerTransactionId;
	private String partnerOrderId;
	private BigDecimal partnerOrderAmount;
	private String partnerNotifyUrl;
	private String partnerReturnUrl;
	private Integer curTimes;		//当前已通知次数
	private Integer maxTimes;		//最大通知次数
	private Integer notifyStatus;//通知状态：0 未通知 1已通知 2通知失败
	private String resultDescp;//秒趣处理分期付款结果描述，成功为ok，失败：失败描述
	private Date createTime;
	private Date notifyTime;
	private String operator;
	
	public UserInstalmentNotifyRecord() {
		// TODO Auto-generated constructor stub
	}

	public UserInstalmentNotifyRecord(String notifyId, String result,
			String instalmentId, Integer dealStatus,
			String partnerTransactionId, String partnerOrderId,
			BigDecimal partnerOrderAmount, String partnerNotifyUrl,
			String partnerReturnUrl, Integer notifyStatus, Date createTime,
			Date notifyTime, String operator) {
		super();
		this.notifyId = notifyId;
		this.result = result;
		this.instalmentId = instalmentId;
		this.dealStatus = dealStatus;
		this.partnerTransactionId = partnerTransactionId;
		this.partnerOrderId = partnerOrderId;
		this.partnerOrderAmount = partnerOrderAmount;
		this.partnerNotifyUrl = partnerNotifyUrl;
		this.partnerReturnUrl = partnerReturnUrl;
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

	public String getInstalmentId() {
		return instalmentId;
	}

	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
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

	public String getPartnerNotifyUrl() {
		return partnerNotifyUrl;
	}

	public void setPartnerNotifyUrl(String partnerNotifyUrl) {
		this.partnerNotifyUrl = partnerNotifyUrl;
	}

	public String getPartnerReturnUrl() {
		return partnerReturnUrl;
	}

	public void setPartnerReturnUrl(String partnerReturnUrl) {
		this.partnerReturnUrl = partnerReturnUrl;
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

	public String getResultDescp() {
		return resultDescp;
	}

	public void setResultDescp(String resultDescp) {
		this.resultDescp = resultDescp;
	}

	public Integer getCurTimes() {
		return curTimes;
	}

	public void setCurTimes(Integer curTimes) {
		this.curTimes = curTimes;
	}

	public Integer getMaxTimes() {
		return maxTimes;
	}

	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
	}

	

}
