package com.tera.feature.cllt.model;

import java.util.Date;

import com.tera.util.DateUtils;

/**
 *功能:Cllt  贷后催收bean
 *时间:2016年3月7日上午10:51:11
 *@author Ldh
 */
public class Cllt {
	
	//contract表
	private String contractId;//合同ID
	private String loanId;//申请id
	private int lateNum;//期数
	private String org;//部门
	private double loanAmt;//借款金额
	private Date startDate;//开始日期
	private String startDateStr;//格式日期字符串
	private Date endDate;//结束日期
	private String endDateStr;//格式日期字符串
	private String getLoanWay;//融资方式
	
	//loan_base表
	private String product;//产品类型
	private String isRenew;//是否续贷
	private String loanName;//借款人姓名
	private String idNo;//证件号码

    //cllt_distr表
	private String clltUid;//催收人id
	
	//user表
	private String clltName;//催收人
	
	//org表
	private String orgName;//机构名
	
	//ret_plan表
	private Date retDate; //应还款日期
	//日期区间
	private Date minRetDate;
	private Date maxRetDate;
	private String retDateStr;//应还款日期的字符串表示
	private int lateDays;//逾期天数
	
	public String getClltUid() {
		return clltUid;
	}
	public String getContractId() {
		return contractId;
	}
	public String getLoanId() {
		return loanId;
	}
	
	public String getIdNo() {
		return idNo;
	}
	public String getProduct() {
		return product;
	}
	public String getOrg() {
		return org;
	}
	public Date getRetDate() {
		return retDate;
	}
	public String getRetDateStr() {
		return DateUtils.formatDate(this.retDate);
	}
	public int getLateDays() {
		return lateDays;
	}
	public int getLateNum() {
		return lateNum;
	}

	public double getLoanAmt() {
		return loanAmt;
	}
	public Date getStartDate() {
		return startDate;
	}
	public String getStartDateStr(){
		return DateUtils.formatDate(this.startDate);
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getEndDateStr(){
		return DateUtils.formatDate(this.endDate);
	}
	public String getGetLoanWay() {
		return getLoanWay;
	}
	public String getIsRenew() {
		return isRenew;
	}
	public String getLoanName() {
		return loanName;
	}

	public String getClltName() {
		return clltName;
	}
	public String getOrgName() {
		return orgName;
	}
	
	
	public Date getMinRetDate() {
		return minRetDate;
	}
	public Date getMaxRetDate() {
		return maxRetDate;
	}
	public void setClltUid(String clltUid) {
		this.clltUid = clltUid;
	}
	public void setContract_id(String contractId) {
		this.contractId = contractId;
	}
	public void setLoan_id(String loanId) {
		this.loanId = loanId;
	}
	
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public void setRetDate(Date retDate) {
		this.retDate = retDate;
	}
	public void setRetDateStr(String retDateStr) {
		this.retDateStr = retDateStr;
	}
	public void setLateNum(int num) {
		this.lateNum = num;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setLoanWay(String loanWay) {
		this.getLoanWay = loanWay;
	}
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public void setClltName(String clltName) {
		this.clltName = clltName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public void setGetLoanWay(String getLoanWay) {
		this.getLoanWay = getLoanWay;
	}
	public void setMinRetDate(Date minRetDate) {
		this.minRetDate = minRetDate;
	}
	public void setMaxRetDate(Date maxRetDate) {
		this.maxRetDate = maxRetDate;
	}
	
	
}
