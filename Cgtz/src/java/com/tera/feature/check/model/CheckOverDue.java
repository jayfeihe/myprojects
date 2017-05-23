package com.tera.feature.check.model;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;

import com.tera.util.DateUtils;

public class CheckOverDue {
	
	//合同表
    private String contractId;//合同编号
    private String loanId;//申请编号
    private String org;//所属机构
    private String lateNum;//逾期期数
    private String overdueType;//逾期类型(1利息逾期，2本金逾期)
    private String checkReportState;//逾期报告审核状态(0未提交1未审核2未通过3已通过)
    private String checkState;//稽查标识(1未处理2已处理3移交法务)
    private String isAccept;//当期逾期是否受理(0未受理1已受理)
    private Timestamp checkInTime;//进入稽查时间
    private String checkInTimeStr;//
    private String isCheck;//是否处于稽查状态(0否1是)
    private String checkSource;//稽查来源(1新逾期2业务员新跟进3还款变化)
     
	//申请表
    private String loanName;//申请人/机构姓名
    private String product;//产品
    private  double loanAmt;//借款金额
    private String salesMan;//业务员
    
    //org表
    private String orgName;//机构名称
    
    //user表
    private String saleName;//业务经办人姓名
    
    //还款计划表
    private int lateDays;//逾期天数
    private Date retDate;//当期还款日
    //还款日区间
    private Date startTime;//
    private Date endTime;
    private String retDateStr;
    private double dueCapital;//本金逾期金额
    private double dueInterest;//利息逾期金额
    
    //salesCheck表
    private Timestamp createTime;
    private String createTimeStr;
    
    //getter部分
    
	public String getContractId() {
		return contractId;
	}
	public String getLoanId() {
		return loanId;
	}
	
	public String getSaleName() {
		return saleName;
	}
	public String getOrg() {
		return org;
	}
	public String getLateNum() {
		return lateNum;
	}
	public String getOverdueType() {
		return overdueType;
	}
	public String getCheckReportState() {
		return checkReportState;
	}
	public String getCheckState() {
		return checkState;
	}
	public String getIsAccept() {
		return isAccept;
	}
	public Timestamp getCheckInTime() {
		return checkInTime;
	}
	public String getCheckInTimeStr(){
		return DateUtils.formatTime(this.checkInTime);
	}
	public String getIsCheck() {
		return isCheck;
	}
	public String getCheckSource() {
		return checkSource;
	}
	public String getLoanName() {
		return loanName;
	}
	public String getProduct() {
		return product;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public String getSalesMan() {
		return salesMan;
	}
	public String getOrgName() {
		return orgName;
	}
	public int getLateDays() {
		return lateDays;
	}
	public java.util.Date getRetDate() {
		return retDate;
	}
	public double getDueCapital() {
		return dueCapital;
	}
	public double getDueInterest() {
		return dueInterest;
	}
	public String getRetDateStr() {
		return DateUtils.formatDate(this.retDate);
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public String getCreateTimeStr() {
		return DateUtils.formatTime(this.createTime);
	}
	//查询区间
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	
	//setter部分
	
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	public void setCheckInTimeStr(String checkInTimeStr) {
		this.checkInTimeStr = checkInTimeStr;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public void setLateNum(String lateNum) {
		this.lateNum = lateNum;
	}
	public void setOverdueType(String overdueType) {
		this.overdueType = overdueType;
	}
	public void setCheckReportState(String checkReportState) {
		this.checkReportState = checkReportState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}
	public void setCheckInTime(Timestamp checkInTime) {
		this.checkInTime = checkInTime;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public void setCheckSource(String checkSource) {
		this.checkSource = checkSource;
	}
	public void setLoanName(String name) {
		this.loanName = name;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}
	public void setRetDate(Date retDate) {
		this.retDate = retDate;
	}
	public void setRetDateStr(String retDateStr) {
		this.retDateStr = retDateStr;
	}
	public void setDueCapital(double dueCapital) {
		this.dueCapital = dueCapital;
	}
	public void setDueInterest(double dueInterest) {
		this.dueInterest = dueInterest;
	}
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
    
   
}
