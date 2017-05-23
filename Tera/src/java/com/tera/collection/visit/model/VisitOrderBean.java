package com.tera.collection.visit.model;

import com.tera.util.DateUtils;

public class VisitOrderBean {
	private String id;
	private String contractNo;//合同号
	
	private String answerState;//接听状态
	private java.sql.Timestamp orderTime;//预约时间
	private String orderTimeStr;//预约时间（字符串）
	private String phoneSummary;//催收摘要
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAnswerState() {
		return answerState;
	}
	public void setAnswerState(String answerState) {
		this.answerState = answerState;
	}
	public java.sql.Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(java.sql.Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public String getPhoneSummary() {
		return phoneSummary;
	}
	public void setPhoneSummary(String phoneSummary) {
		this.phoneSummary = phoneSummary;
	}
	public String getOrderTimeStr() {
		return orderTimeStr;
	}
	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr =  DateUtils.formatTime(this.orderTime);
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
}
