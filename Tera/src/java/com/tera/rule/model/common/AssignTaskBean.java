package com.tera.rule.model.common;

public class AssignTaskBean {
	/**
	 * 申请编号
	 */
	private String appNo;
	
	/**
	 * 借款金额
	 */
	private Double amount;

	/**
	 * 人员编号
	 */
	private String userId;

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
