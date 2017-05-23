package com.tera.contract.model.form;


public class PaymentManageMsgBean {
	
	
	private boolean success;	//方法处理结果
	private String message;		//方法处理结果信息
		
	public PaymentManageMsgBean() {
		// TODO Auto-generated constructor stub
	}
	
	public PaymentManageMsgBean(boolean success,String message) {
		this.success=success;
		this.message=message;
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
