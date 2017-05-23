package com.tera.loanconsult.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>LoanConsultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-05-30 14:50:36<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LoanConsult {
	
	
	private int id;//ID
	private String type;//咨询人类型
	private String name;//姓名/机构全称
	private double amount;//金额
	private String phone;//电话
	private String idType;//证件类型
	private String idNo;//证件号码
	private String repaymentSource;//还款来源
	private String mortgage;//是否抵押
	private String loanPurpose;//用途
	private java.util.Date startTime;//开始时间
	private String startTimeStr;//开始时间
	private java.util.Date endTime;//结束时间
	private String endTimeStr;//结束时间
	private String customerManager;//客户经理
	private String operator;//操作员
	private String orgId;//所属机构
	private java.sql.Timestamp createTime;//创建日期
	private String createTimeStr;//结束时间
	private java.sql.Timestamp updateTime;//修改日期
	private String updateTimeStr;//结束时间
	private String state;//状态
	
	
	public String getStartTimeStr() {
		return DateUtils.formatDate(this.startTime);
	}
	public String getEndTimeStr() {
		return DateUtils.formatDate(this.endTime);
	}
	public String getCreateTimeStr() {
		return DateUtils.formatDate(this.createTime);
	}
	public String getUpdateTimeStr() {
		return DateUtils.formatDate(this.updateTime);
	}
	private String days; // 开始日期减去结束日期 == 期限
	
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id=id;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type=type;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public double getAmount() {
		return this.amount;
	}
	public void setAmount(double amount) {
		this.amount=amount;
	}
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone=phone;
	}
	public String getIdType() {
		return this.idType;
	}
	public void setIdType(String idType) {
		this.idType=idType;
	}
	public String getIdNo() {
		return this.idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo=idNo;
	}
	public String getRepaymentSource() {
		return this.repaymentSource;
	}
	public void setRepaymentSource(String repaymentSource) {
		this.repaymentSource=repaymentSource;
	}
	public String getMortgage() {
		return this.mortgage;
	}
	public void setMortgage(String mortgage) {
		this.mortgage=mortgage;
	}
	public String getLoanPurpose() {
		return this.loanPurpose;
	}
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose=loanPurpose;
	}
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime=startTime;
	}
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime=endTime;
	}
	public String getCustomerManager() {
		return this.customerManager;
	}
	public void setCustomerManager(String customerManager) {
		this.customerManager=customerManager;
	}
	public String getOperator() {
		return this.operator;
	}
	public void setOperator(String operator) {
		this.operator=operator;
	}
	public String getOrgId() {
		return this.orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId=orgId;
	}
	public java.sql.Timestamp getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public java.sql.Timestamp getUpdateTime() {
		return this.updateTime;
	}
	public void setUpdateTime(java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state=state;
	}
}

