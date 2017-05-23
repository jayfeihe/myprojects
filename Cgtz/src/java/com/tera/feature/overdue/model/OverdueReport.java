package com.tera.feature.overdue.model;

import java.sql.Timestamp;

import com.tera.util.DateUtils;

/**
 * 
 * 逾期报告实体类
 * <b>功能：</b>OverdueReportDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 11:22:29<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class OverdueReport {

	//属性部分
	private int id; //ID
	private String loanId; //
	private String contractId; //合同ID
	private int num; //期数
	private String submitUid; //提交人
	private Timestamp submitTme; //提交时间
	private String submitTmeStr; //提交时间
	private String submitRemark; //提交说明
	private String auditUid; //审批人
	private Timestamp auditTime; //审批时间
	private String auditTimeStr; //审批时间
	private String auditRemark; //审批结果审批说明
	private String auditResult;//报告审核结果
	private String submitName;
	private String auditName;
	
	private Timestamp orgAuditTime; //审批时间
	private String orgAuditTimeStr; //审批时间
	private String orgAuditRemark; //审批结果审批说明
	private String orgAuditResult;//报告审核结果
	private String orgAuditName;
	private String orgAuditUid;

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
	public int getNum () {
		return this.num;
	}
	public String getSubmitUid () {
		return this.submitUid;
	}
	public java.sql.Timestamp getSubmitTme () {
		return this.submitTme;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getSubmitTmeStr () {
		return DateUtils.formatTime(this.submitTme);
	}
	public String getSubmitRemark () {
		return this.submitRemark;
	}
	public String getAuditUid () {
		return this.auditUid;
	}
	public java.sql.Timestamp getAuditTime () {
		return this.auditTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getAuditTimeStr () {
		return DateUtils.formatTime(this.auditTime);
	}
	public String getAuditRemark () {
		return this.auditRemark;
	}
	public String getAuditResult(){
		return this.auditResult;
    }
    
	public String getSubmitName() {
		return submitName;
	}
	public String getAuditName() {
		return auditName;
	}
	
	public Timestamp getOrgAuditTime() {
		return orgAuditTime;
	}
	public String getOrgAuditTimeStr() {
		return DateUtils.formatTime(orgAuditTime);
	}
	public String getOrgAuditRemark() {
		return orgAuditRemark;
	}
	public String getOrgAuditResult() {
		return orgAuditResult;
	}
	public String getOrgAuditName() {
		return orgAuditName;
	}
	public String getOrgAuditUid() {
		return orgAuditUid;
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
	public void setNum (int num) {
		this.num=num;
	}
	public void setSubmitUid (String submitUid) {
		this.submitUid=submitUid;
	}
	public void setSubmitTme (java.sql.Timestamp submitTme) {
		this.submitTme=submitTme;
	}
	public void setSubmitTmeStr (String submitTmeStr) {
		this.submitTmeStr=submitTmeStr;
	}
	public void setSubmitRemark (String submitRemark) {
		this.submitRemark=submitRemark;
	}
	public void setAuditUid (String auditUid) {
		this.auditUid=auditUid;
	}
	public void setAuditTime (java.sql.Timestamp auditTime) {
		this.auditTime=auditTime;
	}
	public void setAuditTimeStr (String auditTimeStr) {
		this.auditTimeStr=auditTimeStr;
	}
	public void setAuditRemark (String auditRemark) {
		this.auditRemark=auditRemark;
	}
	public void setAuditResult(String auditResult){
		this.auditResult=auditResult;
	}
	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public void setOrgAuditTime(java.sql.Timestamp orgAuditTime) {
		this.orgAuditTime = orgAuditTime;
	}
	public void setOrgAuditTimeStr(String orgAuditTimeStr) {
		this.orgAuditTimeStr = orgAuditTimeStr;
	}
	public void setOrgAuditRemark(String orgAuditRemark) {
		this.orgAuditRemark = orgAuditRemark;
	}
	public void setOrgAuditResult(String orgAuditResult) {
		this.orgAuditResult = orgAuditResult;
	}
	public void setOrgAuditName(String orgAuditName) {
		this.orgAuditName = orgAuditName;
	}
	public void setOrgAuditUid(String orgAuditUid) {
		this.orgAuditUid = orgAuditUid;
	}
    
}

