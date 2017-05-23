package com.tera.audit.account.model.form;

public class AccountFormBean {

	private int id; // 申请主键
	
	private String loanId;
	
	private String contractId;
	
	private String onLineContractId;
	
	private String getLoanWay;
	
	private String lendUserId;
	
	private String proof;
	
	private double amt;
	
	private String remark;
	
	private String subject; // 收款科目-1：收利息,2：收本金
	
	private String totalPeriod; // 总期数
	
	private int curPeriod; // 当前还款期数
	
	private int payOutNum; // 垫付期数
	
	private double defaultFee; // 罚息
	private double penaltyFee; // 滞纳金
	private double otherFee; // 其他费用
	private double storFee; // 仓储费
	
	private String payDate; // 收款日期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getOnLineContractId() {
		return onLineContractId;
	}

	public void setOnLineContractId(String onLineContractId) {
		this.onLineContractId = onLineContractId;
	}

	public String getGetLoanWay() {
		return getLoanWay;
	}

	public void setGetLoanWay(String getLoanWay) {
		this.getLoanWay = getLoanWay;
	}

	public String getLendUserId() {
		return lendUserId;
	}

	public void setLendUserId(String lendUserId) {
		this.lendUserId = lendUserId;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public double getAmt() {
		return amt;
	}

	public String getRemark() {
		return remark;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTotalPeriod() {
		return totalPeriod;
	}

	public int getCurPeriod() {
		return curPeriod;
	}

	public void setTotalPeriod(String totalPeriod) {
		this.totalPeriod = totalPeriod;
	}

	public void setCurPeriod(int curPeriod) {
		this.curPeriod = curPeriod;
	}

	public int getPayOutNum() {
		return payOutNum;
	}

	public void setPayOutNum(int payOutNum) {
		this.payOutNum = payOutNum;
	}

	public double getDefaultFee() {
		return defaultFee;
	}

	public double getPenaltyFee() {
		return penaltyFee;
	}

	public void setDefaultFee(double defaultFee) {
		this.defaultFee = defaultFee;
	}

	public void setPenaltyFee(double penaltyFee) {
		this.penaltyFee = penaltyFee;
	}

	public double getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(double otherFee) {
		this.otherFee = otherFee;
	}

	public double getStorFee() {
		return storFee;
	}

	public void setStorFee(double storFee) {
		this.storFee = storFee;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
}
