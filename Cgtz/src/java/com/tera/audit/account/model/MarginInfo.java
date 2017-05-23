package com.tera.audit.account.model;

import java.sql.Timestamp;
import java.util.Date;

import com.tera.util.DateUtils;

/**
 * 
 * 保证金信息表实体类
 * <b>功能：</b>MarginInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-24 18:23:04<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class MarginInfo {

	//属性部分
	private int id; //
	private String orgId;
	private String orgName;
	private String contractId; //
	private double amt; //
	private Timestamp getTime; //
	private String getTimeStr; //
	private String state; //1未处理2退还借款人3垫付本金
	private String remarks; //
	private Timestamp handleTime; //处理时间
	private String handleTimeStr; //处理时间
	private Timestamp createTime; //
	private String createTimeStr; //
	private Timestamp updateTime; //
	private String updateTimeStr; //
	private String num;
    
	private String loanName;//借款人
	private double conAmt;//合同金额
	private Date startDate;//合同开始日期
	private String startDateStr;
	private Date endDate;//合同结束日期
	private String endDateStr;
	
	private Date minStartDate;
	private Date maxStartDate;
	private Date minEndDate;
	private Date maxEndDate;
	private Date minHandleDate;
	private Date maxHandleDate;
	
	//getter部分
	public int getId () {
		return this.id;
	}
	
	public String getOrgId() {
		return orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getContractId () {
		return this.contractId;
	}
	public double getAmt () {
		return this.amt;
	}
	public java.sql.Timestamp getGetTime () {
		return this.getTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getGetTimeStr () {
		return DateUtils.formatTime(this.getTime);
	}
	public String getState () {
		return this.state;
	}
	public String getRemarks () {
		return this.remarks;
	}
	public java.sql.Timestamp getHandleTime () {
		return this.handleTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getHandleTimeStr () {
		return DateUtils.formatTime(this.handleTime);
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
	public String getNum(){
		return this.num;
	}

	public String getLoanName() {
		return loanName;
	}
	public double getConAmt() {
		return conAmt;
	}
	public Date getStartDate() {
		return startDate;
	}
	public String getStartDateStr() {
		return DateUtils.formatDate(startDate);
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getEndDateStr() {
		return DateUtils.formatDate(endDate);
	}
	public Date getMinStartDate() {
		return minStartDate;
	}
	public Date getMaxStartDate() {
		return maxStartDate;
	}
	public Date getMinEndDate() {
		return minEndDate;
	}
	public Date getMaxEndDate() {
		return maxEndDate;
	}
	
	public Date getMinHandleDate() {
		return minHandleDate;
	}

	public Date getMaxHandleDate() {
		return maxHandleDate;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setContractId (String contractId) {
		this.contractId=contractId;
	}
	public void setAmt (double amt) {
		this.amt=amt;
	}
	public void setGetTime (java.sql.Timestamp getTime) {
		this.getTime=getTime;
	}
	public void setGetTimeStr (String getTimeStr) {
		this.getTimeStr=getTimeStr;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setRemarks (String remarks) {
		this.remarks=remarks;
	}
	public void setHandleTime (java.sql.Timestamp handleTime) {
		this.handleTime=handleTime;
	}
	public void setHandleTimeStr (String handleTimeStr) {
		this.handleTimeStr=handleTimeStr;
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
	public void setNum(String num){
		this.num=num;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public void setConAmt(double conAmt) {
		this.conAmt = conAmt;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public void setMinStartDate(Date minStartDate) {
		this.minStartDate = minStartDate;
	}
	public void setMaxStartDate(Date maxStartDate) {
		this.maxStartDate = maxStartDate;
	}
	public void setMinEndDate(Date minEndDate) {
		this.minEndDate = minEndDate;
	}
	public void setMaxEndDate(Date maxEndDate) {
		this.maxEndDate = maxEndDate;
	}

	public void setMinHandleDate(Date minHandleDate) {
		this.minHandleDate = minHandleDate;
	}

	public void setMaxHandleDate(Date maxHandleDate) {
		this.maxHandleDate = maxHandleDate;
	}
	
	

}

