package com.hikootech.mqcash.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuwei
 * 2015年8月16日
 * 合作伙伴调用信息
 */
public class PartnerCallInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String infoId;
	private String instalmentId;
	private String partnerTransactionId;
	private String partnerNotifyUrl;
	private String partnerReturnUrl;
	private Date createTime;
	private Date updateTime;
	private String operator;
	
	public PartnerCallInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInstalmentId() {
		return instalmentId;
	}

	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
	}

	public String getPartnerTransactionId() {
		return partnerTransactionId;
	}

	public void setPartnerTransactionId(String partnerTransactionId) {
		this.partnerTransactionId = partnerTransactionId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

}
