package com.tera.renhang.model;

import com.tera.util.DateUtils;

/**
 * 
 * 信用贷款人行报告交易逾期透支记录实体类
 * <b>功能：</b>RhTransDefaultDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 16:06:33<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class RhTransDefault {

	//属性部分
	private int id; //ID
	private String appId; //申请ID
	private int transId; //交易ID
	private String type; //类型
	private java.util.Date startDate; //逾期透支记录开始时间
	private String startDateStr; //逾期透支记录开始时间
	private java.util.Date endDate; //逾期透支记录结束时间
	private String endDateStr; //逾期透支记录结束时间
	private java.util.Date defaultDate; //逾期透支月份
	private String defaultDateStr; //逾期透支月份
	private int defaultDuringMonth; //逾期透支持续月数
	private double defaultAmount; //逾期透支金额
	private String remarks; //备注
	private String state; //状态
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getAppId () {
		return this.appId;
	}
	public int getTransId () {
		return this.transId;
	}
	public String getType () {
		return this.type;
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
	public java.util.Date getDefaultDate () {
		return this.defaultDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getDefaultDateStr () {
		return DateUtils.formatDate(this.defaultDate);
	}
	public int getDefaultDuringMonth () {
		return this.defaultDuringMonth;
	}
	public double getDefaultAmount () {
		return this.defaultAmount;
	}
	public String getRemarks () {
		return this.remarks;
	}
	public String getState () {
		return this.state;
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId () {
		return this.orgId;
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
	public void setAppId (String appId) {
		this.appId=appId;
	}
	public void setTransId (int transId) {
		this.transId=transId;
	}
	public void setType (String type) {
		this.type=type;
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
	public void setDefaultDate (java.util.Date defaultDate) {
		this.defaultDate=defaultDate;
	}
	public void setDefaultDateStr (String defaultDateStr) {
		this.defaultDateStr=defaultDateStr;
	}
	public void setDefaultDuringMonth (int defaultDuringMonth) {
		this.defaultDuringMonth=defaultDuringMonth;
	}
	public void setDefaultAmount (double defaultAmount) {
		this.defaultAmount=defaultAmount;
	}
	public void setRemarks (String remarks) {
		this.remarks=remarks;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
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

