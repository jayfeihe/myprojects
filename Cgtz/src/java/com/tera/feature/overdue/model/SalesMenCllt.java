package com.tera.feature.overdue.model;

import java.sql.Timestamp;
import java.util.Date;

import com.tera.util.DateUtils;

/**
 *功能:SalesMenCllt  业务员、分公司逾期处理bean
 *时间:2016年3月7日上午10:50:36
 *@author Ldh
 */
public class SalesMenCllt {	
	//合同表
	private String contractId;//合同编号
    private String loanId;//申请编号
    private String org;//所属机构
    private String roleId;
    private String lateNum;//逾期期数
    private String overdueType;//逾期类型(1利息逾期，2本金逾期)
    private String checkReportState;//逾期报告审核状态(0未提交1未审核2未通过3已通过)
    private String checkState;//稽查标识(1未处理2已处理3移交法务)
    private String isAccept;//当期逾期是否受理(0未受理1已受理)
    private String orgAuditResult;//分公司审核结果(0未通过1已通过)
	//申请表
    private String tel;
	private String loanName;//申请人/机构姓名
    private String product;//产品
    private  double loanAmt;//借款金额
    private String salesman;//业务员
    private String idType;//证件类型
    private String idNo;//证件号码
	//机构表
	 private String orgName;//机构名称
	//user表
	private String saleName;//业务经办人姓名
	private String loginId;//登录id
	//还款计划表
	private int lateDays;//逾期天数
    private Date retDate;//当期还款日
    private String retDateStr;
    //时间区间
    private Date minRetDate;
    private Date maxRetDate;
    private double planInterest;//应还利息
    private double realInterest;//实还利息
    private double planCapital;//应还本金
    private double realCapital;//实还本金
    private double dueInterest;//利息逾期金额
    private double dueCapital;//本金逾期金额    
    
    //属性getter部分
	public String getContractId() {
		return contractId;
	}
	public String getLoanId() {
		return loanId;
	}
	public String getOrg() {
		return org;
	}
	public String getRoleId(){
		return roleId;
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
	public String getOrgAuditResult(){
		return orgAuditResult;
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
	public String getSalesman() {
		return salesman;
	}
	public String getTel(){
		return tel;
	}
	public String getIdType() {
		return idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public String getSaleName() {
		return saleName;
	}
	public String getLoginId() {
		return loginId;
	}
	public int getLateDays() {
		return lateDays;
	}
	public Date getRetDate() {
		return retDate;
	}
	public String getRetDateStr() {
		return DateUtils.formatDate(this.retDate);
	}
	public double getPlanInterest() {
		return planInterest;
	}
	public double getRealInterest() {
		return realInterest;
	}
	public double getPlanCapital() {
		return planCapital;
	}
	public double getRealCapital() {
		return realCapital;
	}
	public double getDueInterest() {
		return dueInterest;
	}
	public double getDueCapital() {
		return dueCapital;
	}
	
	
	public Date getMinRetDate() {
		return minRetDate;
	}
	public Date getMaxRetDate() {
		return maxRetDate;
	}
	//属性setter部分
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public void setRoleId(String roleId){
		this.roleId=roleId;
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
	public void setOrgAuditResult(String orgAuditResult){
		this.orgAuditResult=orgAuditResult;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public void setTel(String tel){
		this.tel=tel;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public void setSalesman(String salesMan) {
		this.salesman = salesMan;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
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
	public void setPlanInterest(double planInterest) {
		this.planInterest = planInterest;
	}
	public void setRealInterest(double realInterest) {
		this.realInterest = realInterest;
	}
	public void setPlanCapital(double planCapital) {
		this.planCapital = planCapital;
	}
	public void setRealCapital(double realCapital) {
		this.realCapital = realCapital;
	}
	public void setDueInterest(double dueInterest) {
		this.dueInterest = dueInterest;
	}
	public void setDueCapital(double dueCapital) {
		this.dueCapital = dueCapital;
	}
	public void setMinRetDate(Date minRetDate) {
		this.minRetDate = minRetDate;
	}
	public void setMaxRetDate(Date maxRetDate) {
		this.maxRetDate = maxRetDate;
	}
    
    
}
