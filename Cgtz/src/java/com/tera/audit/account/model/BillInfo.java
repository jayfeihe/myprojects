package com.tera.audit.account.model;

public class BillInfo {
	
	private String contractId="";
	private int retnum=0;  //期数
	private String payTime="";  //实际还款时间
	private double intAmt=0;  //利息总金额
	private double defaultFee=0;   //罚息
	private double penaltyFee=0;  //滞纳金
	private double storFee=0;  //仓储费
	private double otherFee=0;  //收取的贷后其他费用
	private String remarks="";  //备注
	private String proof="";//凭证号
	private String loginId="";//操作人
	private java.sql.Timestamp operTime; //操作时间
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public int getRetnum() {
		return retnum;
	}
	public void setRetnum(int retnum) {
		this.retnum = retnum;
	}
	
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public double getIntAmt() {
		return intAmt;
	}
	public void setIntAmt(double intAmt) {
		this.intAmt = intAmt;
	}
	public double getDefaultFee() {
		return defaultFee;
	}
	public void setDefaultFee(double defaultFee) {
		this.defaultFee = defaultFee;
	}
	public double getPenaltyFee() {
		return penaltyFee;
	}
	public void setPenaltyFee(double penaltyFee) {
		this.penaltyFee = penaltyFee;
	}
	public double getStorFee() {
		return storFee;
	}
	public void setStorFee(double storFee) {
		this.storFee = storFee;
	}
	public double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(double otherFee) {
		this.otherFee = otherFee;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProof() {
		return proof;
	}
	public void setProof(String proof) {
		this.proof = proof;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public java.sql.Timestamp getOperTime() {
		return operTime;
	}
	public void setOperTime(java.sql.Timestamp operTime) {
		this.operTime = operTime;
	}
	
	

}
