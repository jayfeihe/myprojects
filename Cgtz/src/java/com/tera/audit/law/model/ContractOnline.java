package com.tera.audit.law.model;

import com.tera.util.DateUtils;

/**
 * 
 * 线下线上合同关联表实体类
 * <b>功能：</b>ContractOnlineDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 15:03:28<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class ContractOnline {

	//属性部分
	private int id; //
	private String loanId; //
	private String contractId; //合同id，线下
	private String onlineConId; //线上合同编号
	private String projectName; //项目名称
	private String onlineType; //线上类别
	private java.util.Date onlineStartDate; //线上开始时间
	private String onlineStartDateStr; //线上开始时间
	private java.util.Date onlineEndDate; //线上结束时间
	private String onlineEndDateStr; //线上结束时间
	private double onlineRateIn; //线上推送利率（线下结算）
	private double onlineRateOut; //线上出售利率（给网民）
	private String retWay; //还款方式
	private double onlineAmt; //线上合同金额
	private String state;// 1：未放款-2：已放款
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getContractId () {
		return this.contractId;
	}
	public String getOnlineConId () {
		return this.onlineConId;
	}
	public String getProjectName () {
		return this.projectName;
	}
	public String getOnlineType () {
		return this.onlineType;
	}
	public java.util.Date getOnlineStartDate () {
		return this.onlineStartDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getOnlineStartDateStr () {
		return DateUtils.formatDate(this.onlineStartDate);
	}
	public java.util.Date getOnlineEndDate () {
		return this.onlineEndDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getOnlineEndDateStr () {
		return DateUtils.formatDate(this.onlineEndDate);
	}
	public double getOnlineRateIn () {
		return this.onlineRateIn;
	}
	public double getOnlineRateOut () {
		return this.onlineRateOut;
	}
	public String getRetWay () {
		return this.retWay;
	}
	public double getOnlineAmt () {
		return this.onlineAmt;
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
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setContractId (String contractId) {
		this.contractId=contractId;
	}
	public void setOnlineConId (String onlineConId) {
		this.onlineConId=onlineConId;
	}
	public void setProjectName (String projectName) {
		this.projectName=projectName;
	}
	public void setOnlineType (String onlineType) {
		this.onlineType=onlineType;
	}
	public void setOnlineStartDate (java.util.Date onlineStartDate) {
		this.onlineStartDate=onlineStartDate;
	}
	public void setOnlineStartDateStr (String onlineStartDateStr) {
		this.onlineStartDateStr=onlineStartDateStr;
	}
	public void setOnlineEndDate (java.util.Date onlineEndDate) {
		this.onlineEndDate=onlineEndDate;
	}
	public void setOnlineEndDateStr (String onlineEndDateStr) {
		this.onlineEndDateStr=onlineEndDateStr;
	}
	public void setOnlineRateIn (double onlineRateIn) {
		this.onlineRateIn=onlineRateIn;
	}
	public void setOnlineRateOut (double onlineRateOut) {
		this.onlineRateOut=onlineRateOut;
	}
	public void setRetWay (String retWay) {
		this.retWay=retWay;
	}
	public void setOnlineAmt (double onlineAmt) {
		this.onlineAmt=onlineAmt;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}

