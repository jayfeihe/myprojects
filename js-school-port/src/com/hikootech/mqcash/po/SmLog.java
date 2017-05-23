package com.hikootech.mqcash.po;

import java.util.Date;

//短信管理
public class SmLog {
	private String smOrderId;
	private String mobileNumber;
	private String data;
	private int sendType;//发送类型：0.延迟发送 1.立即发送
	private Date bookTime;// 预计发送时间
	private Date sendTime;// 发送时间
	private int sendStatus;// 发送状态（0-发送成功,1-发送失败）
	private String templateId;// 模板id
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private String operator;//操作人


	public String getSmOrderId() {
		return smOrderId;
	}

	public void setSmOrderId(String smOrderId) {
		this.smOrderId = smOrderId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getBookTime() {
		return bookTime;
	}

	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	 
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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

	public int getSendType() {
		return sendType;
	}

	public void setSendType(int sendType) {
		this.sendType = sendType;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

}
