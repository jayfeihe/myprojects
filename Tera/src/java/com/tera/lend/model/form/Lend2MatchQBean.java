package com.tera.lend.model.form;

public class Lend2MatchQBean {

	private int id; //ID
	private String appId; //出借申请号
	private String customerType; //类型-个人/机构
	private String name; //姓名
	private String idNo; //证件号码
	private String operator; //操作员
	private String state; //状态
	private double amount; //出借金额
	private String product; //出借产品
	private java.util.Date serviceEndDate; //服务截至日期
	private java.sql.Timestamp createTime; //创建日期
	private String orgId; //所属机构
	private String mobile; //手机号
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public java.util.Date getServiceEndDate() {
		return serviceEndDate;
	}
	public void setServiceEndDate(java.util.Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	

}
