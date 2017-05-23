package com.tera.loan.model;

import java.util.Date;

import com.tera.util.DateUtils;


/**
 * 
 * <br>
 * <b>功能：</b>AccountDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-06-13 11:27:41<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class Lending {

	//属性部分
	private int id; //ID
	private String contractNo; //合同号
	private String state; //状态
	private String reason; //原因
	private String loanName; //借款人姓名
	private String loanIdNo; //借款人证件号码
	private java.util.Date signDate; //合同签约日期 
	
	private String signDateStr;
	
	private String loanProduct; //借款产品
	private double loanAmount; //借款金额(用来在列表中显示)
	private int loanPeriod; //借款期限
	private String orgId; //所属机构
	private String mobile; //手机号
	
	private java.util.Date signDateBegin; //合同签约日期的下限（用来做查询条件）
	private java.util.Date signDateEnd; //合同签约日期的下限（用来做查询条件）
	private double loanAmountMin; //借款金额最小值（用来做查询条件）
	private double loanAmountMax; //借款金额最大值（用来做查询条件）
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getLoanIdNo() {
		return loanIdNo;
	}
	public void setLoanIdNo(String loanIdNo) {
		this.loanIdNo = loanIdNo;
	}
	
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public int getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(int loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	
	
	
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public double getLoanAmountMin() {
		return loanAmountMin;
	}
	public void setLoanAmountMin(double loanAmountMin) {
		this.loanAmountMin = loanAmountMin;
	}
	public double getLoanAmountMax() {
		return loanAmountMax;
	}
	public void setLoanAmountMax(double loanAmountMax) {
		this.loanAmountMax = loanAmountMax;
	}
	public java.util.Date getSignDate() {
		return signDate;
	}
	public void setSignDate(java.util.Date signDate) {
		this.signDate = signDate;
	}
	public java.util.Date getSignDateBegin() {
		return signDateBegin;
	}
	public void setSignDateBegin(java.util.Date signDateBegin) {
		this.signDateBegin = signDateBegin;
	}
	public java.util.Date getSignDateEnd() {
		return signDateEnd;
	}
	public void setSignDateEnd(java.util.Date signDateEnd) {
		this.signDateEnd = signDateEnd;
	}
	public String getSignDateStr() {
		return DateUtils.formatDate(this.signDate);
	}
}

