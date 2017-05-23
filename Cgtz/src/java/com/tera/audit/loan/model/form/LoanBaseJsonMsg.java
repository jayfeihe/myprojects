package com.tera.audit.loan.model.form;

/** 页面Json对象
 * @author QYANZE
 *
 */
public class LoanBaseJsonMsg {

	private int id; // loanBase主键
	private String loanId; // 申请编号
	private boolean success; // 成功标志
	private String message; // 返回信息
	
	public LoanBaseJsonMsg() {
		super();
	}

	public LoanBaseJsonMsg(int id, String loanId, boolean success, String message) {
		super();
		this.id = id;
		this.loanId = loanId;
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

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
