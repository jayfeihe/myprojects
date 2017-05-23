package com.tera.lend.model.form;

import java.util.Date;

public class Loan2MatchQBean {

	//为人工撮合审批和人工撮合收费添加
	private String loan_id;
	private String id; //Id号
	private String loanAppId; //贷款申请号
	private String name; //姓名/机构全称
	private String orgId; //机构代码
	private String idType; //证件类型
	private String idNo; //证件号码
	private int loanPeriod; //期限
	private double contractAmountMin; //合同金额的最小值
	private double contractAmountMax; //合同金额的最大值
	private Date appTimeStr; //申请时间
	private String useage; //用途
	private double loanAmount; //金额
	private String loanProduct; //产品
	private double loanInterestRate; //利率
	private double loanServiceRate; //服务费率
	private int times;//撮合次数（杨长收添加，做人工撮合审批的时候添加的）
	private String mobile;//手机号（杨长收添加，做人工撮合审批的时候添加的）
	
	
	
	
	public String getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(String loanId) {
		loan_id = loanId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public Date getAppTimeStr() {
		return appTimeStr;
	}
	public void setAppTimeStr(Date appTimeStr) {
		this.appTimeStr = appTimeStr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUseage() {
		return useage;
	}
	public void setUseage(String useage) {
		this.useage = useage;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public double getLoanInterestRate() {
		return loanInterestRate;
	}
	public void setLoanInterestRate(double loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}
	public double getLoanServiceRate() {
		return loanServiceRate;
	}
	public void setLoanServiceRate(double loanServiceRate) {
		this.loanServiceRate = loanServiceRate;
	}
	public String getLoanAppId() {
		return loanAppId;
	}
	public void setLoanAppId(String loanAppId) {
		this.loanAppId = loanAppId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public int getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(int loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public double getContractAmountMin() {
		return contractAmountMin;
	}
	public void setContractAmountMin(double contractAmountMin) {
		this.contractAmountMin = contractAmountMin;
	}
	public double getContractAmountMax() {
		return contractAmountMax;
	}
	public void setContractAmountMax(double contractAmountMax) {
		this.contractAmountMax = contractAmountMax;
	}
	
	
	
}
