package com.tera.match.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>Loan2matchDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-23 12:54:53<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Loan2match {

	//属性部分
	private int id; //ID
	private String loanAppId; //贷款申请号
	private String type; //类型-回款再投/出借
	private String matchType; //撮合类型
	private java.sql.Timestamp appTime; //申请时间
	private String appTimeStr; //申请时间
	private double loanAmount; //金额
	private String loanProduct; //产品
	private int loanPeriod; //期限
	private double loanInterestRate; //利率
	private double loanServiceRate; //服务费率
	private java.util.Date startDate; //开始日期
	private String startDateStr; //开始日期
	private java.util.Date endDate; //结束日期
	private String endDateStr; //结束日期
	private String orgId; //机构代码
	private String useage; //用途
	private double contractAmount; //合同金额
	private java.util.Date contractStartDate; //合同开始日期
	private String contractStartDateStr; //合同开始日期
	private java.util.Date contractEndDate; //合同结束日期
	private String contractEndDateStr; //合同结束日期
	private String state; //状态
	private int times; //次数
	private int lockFlag; //锁
	private String operator; //操作员
	private String orgId2; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanAppId () {
		return this.loanAppId;
	}
	public String getType () {
		return this.type;
	}
	public String getMatchType () {
		return this.matchType;
	}
	public java.sql.Timestamp getAppTime () {
		return this.appTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getAppTimeStr () {
		return DateUtils.formatTime(this.appTime);
	}
	public double getLoanAmount () {
		return this.loanAmount;
	}
	public String getLoanProduct () {
		return this.loanProduct;
	}
	public int getLoanPeriod () {
		return this.loanPeriod;
	}
	public double getLoanInterestRate () {
		return this.loanInterestRate;
	}
	public double getLoanServiceRate () {
		return this.loanServiceRate;
	}
	public java.util.Date getStartDate () {
		return this.startDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getStartDateStr () {
		return DateUtils.formatDate(this.startDate);
	}
	public java.util.Date getEndDate () {
		return this.endDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getEndDateStr () {
		return DateUtils.formatDate(this.endDate);
	}
	public String getOrgId () {
		return this.orgId;
	}
	public String getUseage () {
		return this.useage;
	}
	public double getContractAmount () {
		return this.contractAmount;
	}
	public java.util.Date getContractStartDate () {
		return this.contractStartDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getContractStartDateStr () {
		return DateUtils.formatDate(this.contractStartDate);
	}
	public java.util.Date getContractEndDate () {
		return this.contractEndDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getContractEndDateStr () {
		return DateUtils.formatDate(this.contractEndDate);
	}
	public String getState () {
		return this.state;
	}
	public int getTimes () {
		return this.times;
	}
	public int getLockFlag () {
		return this.lockFlag;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId2 () {
		return this.orgId2;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLoanAppId (String loanAppId) {
		this.loanAppId=loanAppId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setMatchType (String matchType) {
		this.matchType=matchType;
	}
	public void setAppTime (java.sql.Timestamp appTime) {
		this.appTime=appTime;
	}
	public void setAppTimeStr (String appTimeStr) {
		this.appTimeStr=appTimeStr;
	}
	public void setLoanAmount (double loanAmount) {
		this.loanAmount=loanAmount;
	}
	public void setLoanProduct (String loanProduct) {
		this.loanProduct=loanProduct;
	}
	public void setLoanPeriod (int loanPeriod) {
		this.loanPeriod=loanPeriod;
	}
	public void setLoanInterestRate (double loanInterestRate) {
		this.loanInterestRate=loanInterestRate;
	}
	public void setLoanServiceRate (double loanServiceRate) {
		this.loanServiceRate=loanServiceRate;
	}
	public void setStartDate (java.util.Date startDate) {
		this.startDate=startDate;
	}
	public void setStartDateStr (String startDateStr) {
		this.startDateStr=startDateStr;
	}
	public void setEndDate (java.util.Date endDate) {
		this.endDate=endDate;
	}
	public void setEndDateStr (String endDateStr) {
		this.endDateStr=endDateStr;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setUseage (String useage) {
		this.useage=useage;
	}
	public void setContractAmount (double contractAmount) {
		this.contractAmount=contractAmount;
	}
	public void setContractStartDate (java.util.Date contractStartDate) {
		this.contractStartDate=contractStartDate;
	}
	public void setContractStartDateStr (String contractStartDateStr) {
		this.contractStartDateStr=contractStartDateStr;
	}
	public void setContractEndDate (java.util.Date contractEndDate) {
		this.contractEndDate=contractEndDate;
	}
	public void setContractEndDateStr (String contractEndDateStr) {
		this.contractEndDateStr=contractEndDateStr;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setTimes (int times) {
		this.times=times;
	}
	public void setLockFlag (int lockFlag) {
		this.lockFlag=lockFlag;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId2 (String orgId2) {
		this.orgId2=orgId2;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}

