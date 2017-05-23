package com.tera.loan.model.form;

public class LoanQBean {

	private String orgId; // 机构编码
	private String name; // 姓名/机构全称
	private String idNo; // 证件号码
	private String mobile; // 手机号
	private double amountMin; // 金额下限
	private double amountMax; // 金额上限
	private java.sql.Timestamp createTimeMin; // 申请时间下限
	private java.sql.Timestamp createTimeMax; // 申请时间上限
	private String stateTask;
	
	public String getStateTask() {
		return stateTask;
	}
	public void setStateTask(String stateTask) {
		this.stateTask = stateTask;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public double getAmountMin() {
		return amountMin;
	}
	public void setAmountMin(double amountMin) {
		this.amountMin = amountMin;
	}
	public double getAmountMax() {
		return amountMax;
	}
	public void setAmountMax(double amountMax) {
		this.amountMax = amountMax;
	}
	public java.sql.Timestamp getCreateTimeMin() {
		return createTimeMin;
	}
	public void setCreateTimeMin(java.sql.Timestamp createTimeMin) {
		this.createTimeMin = createTimeMin;
	}
	public java.sql.Timestamp getCreateTimeMax() {
		return createTimeMax;
	}
	public void setCreateTimeMax(java.sql.Timestamp createTimeMax) {
		this.createTimeMax = createTimeMax;
	}

}
