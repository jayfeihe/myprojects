package com.tera.audit.account.model.form;

public class AccountJsonMsg {

	private int id; // 申请主键
	private String loanId;
	private String contractId;
	private boolean success;
	private String message;
	
	public AccountJsonMsg() {
		super();
	}
	public AccountJsonMsg(int id, String loanId, String contractId, boolean success, String message) {
		super();
		this.id = id;
		this.loanId = loanId;
		this.contractId = contractId;
		this.success = success;
		this.message = message;
	} 
	public AccountJsonMsg(String loanId, String contractId, boolean success, String message) {
		super();
		this.loanId = loanId;
		this.contractId = contractId;
		this.success = success;
		this.message = message;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoanId() {
		return loanId;
	}
	public String getContractId() {
		return contractId;
	}
	public boolean isSuccess() {
		return success;
	}
	public String getMessage() {
		return message;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
