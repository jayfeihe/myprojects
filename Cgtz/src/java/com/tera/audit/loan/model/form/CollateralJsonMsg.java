package com.tera.audit.loan.model.form;

/** 抵押json对象
 * @author QYANZE
 *
 */
public class CollateralJsonMsg {

	private int id;
	
	private String loanId;
	
	private String type;
	
	private boolean success;
	
	private String message;
 
	public CollateralJsonMsg() {
		super();
	}

	public CollateralJsonMsg(int id, String loanId, String type, boolean success, String message) {
		super();
		this.id = id;
		this.loanId = loanId;
		this.type = type;
		this.success = success;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public String getLoanId() {
		return loanId;
	}

	public String getType() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
