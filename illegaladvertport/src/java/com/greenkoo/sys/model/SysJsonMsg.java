package com.greenkoo.sys.model;

/**
 * 系统json对象
 * 
 * @author QYANZE
 *
 */
public class SysJsonMsg {

	public boolean success;
	
	public String message;

	public SysJsonMsg() {
		super();
	}

	public SysJsonMsg(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
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
