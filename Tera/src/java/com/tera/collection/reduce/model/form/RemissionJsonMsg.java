package com.tera.collection.reduce.model.form;


/**
 * 减免JSON消息bean
 * 
 * @author QYANZE
 *
 */
public class RemissionJsonMsg {

	private String message; // 消息
	private boolean success; // 是否成功
	private int id; // 减免id
	private String contractNo; // 合同编号
	
	
	public RemissionJsonMsg() {
		super();
	}

	public RemissionJsonMsg(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public RemissionJsonMsg(boolean success, String message, int id,
			String contractNo) {
		super();
		this.success = success;
		this.message = message;
		this.id = id;
		this.contractNo = contractNo;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
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
}
