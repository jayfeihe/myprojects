package com.tera.interfaces.model;

/**
 * session状态json实体
 * 
 * @author QYANZE
 *
 */
public class SessionJson {

	private String status; // 状态
	
	private String message;// 信息
	
	public SessionJson() {
		super();
	}
	
	public SessionJson(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
