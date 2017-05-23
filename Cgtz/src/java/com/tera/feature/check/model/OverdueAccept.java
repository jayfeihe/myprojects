package com.tera.feature.check.model;

import com.tera.util.DateUtils;

/**
 * 
 * 逾期受理登记表实体类
 * <b>功能：</b>OverdueAcceptDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-18 19:46:37<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class OverdueAccept {

	//属性部分
	private int id; //
	private String loanId; //申请id
	private String contractId; //合同id
	private int num; //期数
	private String dept; //部门
	private java.util.Date regDate; //日期
	private String regDateStr; //日期
	private String busName; //业务经办人
	private String tel; //联系方式
	private String property; //性质(本金逾期，利息逾期)
	private String deptOwner; //部门负责人
	private String auditMem; //评审成员
	private String riskName; //风控人员
	private String lawName; //法务人员
	private String proType; //产品类型
	private java.util.Date lateDate; //逾期时间
	private String lateDateStr; //逾期时间
	private double amt; //金额
	private String loanInfo; //借款人的基本情况
	private String reportSummary; //逾期报告摘要
	private String deptAuditAdv; //部门经理审核意见
	private String busAdv; //承办人审核意见
	private String checkAdv; //稽查部门负责人意见
	private String keepAdv; //保全小组意见
	private String leaderAdv; //领导意见
	private String state; //状态（1可以修改2存档）
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private String updateUid; //修改人
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
	public int getNum () {
		return this.num;
	}
	public String getDept () {
		return this.dept;
	}
	public java.util.Date getRegDate () {
		return this.regDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getRegDateStr () {
		return DateUtils.formatDate(this.regDate);
	}
	public String getBusName () {
		return this.busName;
	}
	public String getTel () {
		return this.tel;
	}
	public String getProperty () {
		return this.property;
	}
	public String getDeptOwner () {
		return this.deptOwner;
	}
	public String getAuditMem () {
		return this.auditMem;
	}
	public String getRiskName () {
		return this.riskName;
	}
	public String getLawName () {
		return this.lawName;
	}
	public String getProType () {
		return this.proType;
	}
	public java.util.Date getLateDate () {
		return this.lateDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getLateDateStr () {
		return DateUtils.formatDate(this.lateDate);
	}
	public double getAmt () {
		return this.amt;
	}
	public String getLoanInfo () {
		return this.loanInfo;
	}
	public String getReportSummary () {
		return this.reportSummary;
	}
	public String getDeptAuditAdv () {
		return this.deptAuditAdv;
	}
	public String getBusAdv () {
		return this.busAdv;
	}
	public String getCheckAdv () {
		return this.checkAdv;
	}
	public String getKeepAdv () {
		return this.keepAdv;
	}
	public String getLeaderAdv () {
		return this.leaderAdv;
	}
	public String getState () {
		return this.state;
	}
	public String getCreateUid () {
		return this.createUid;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public String getUpdateUid () {
		return this.updateUid;
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
	public void setNum (int num) {
		this.num=num;
	}
	public void setDept (String dept) {
		this.dept=dept;
	}
	public void setRegDate (java.util.Date regDate) {
		this.regDate=regDate;
	}
	public void setRegDateStr (String regDateStr) {
		this.regDateStr=regDateStr;
	}
	public void setBusName (String busName) {
		this.busName=busName;
	}
	public void setTel (String tel) {
		this.tel=tel;
	}
	public void setProperty (String property) {
		this.property=property;
	}
	public void setDeptOwner (String deptOwner) {
		this.deptOwner=deptOwner;
	}
	public void setAuditMem (String auditMem) {
		this.auditMem=auditMem;
	}
	public void setRiskName (String riskName) {
		this.riskName=riskName;
	}
	public void setLawName (String lawName) {
		this.lawName=lawName;
	}
	public void setProType (String proType) {
		this.proType=proType;
	}
	public void setLateDate (java.util.Date lateDate) {
		this.lateDate=lateDate;
	}
	public void setLateDateStr (String lateDateStr) {
		this.lateDateStr=lateDateStr;
	}
	public void setAmt (double amt) {
		this.amt=amt;
	}
	public void setLoanInfo (String loanInfo) {
		this.loanInfo=loanInfo;
	}
	public void setReportSummary (String reportSummary) {
		this.reportSummary=reportSummary;
	}
	public void setDeptAuditAdv (String deptAuditAdv) {
		this.deptAuditAdv=deptAuditAdv;
	}
	public void setBusAdv (String busAdv) {
		this.busAdv=busAdv;
	}
	public void setCheckAdv (String checkAdv) {
		this.checkAdv=checkAdv;
	}
	public void setKeepAdv (String keepAdv) {
		this.keepAdv=keepAdv;
	}
	public void setLeaderAdv (String leaderAdv) {
		this.leaderAdv=leaderAdv;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setCreateUid (String createUid) {
		this.createUid=createUid;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setUpdateUid (String updateUid) {
		this.updateUid=updateUid;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}

