package com.tera.audit.common.model;

/**
 * 用于前台页面下拉支行
 * @author QYANZE
 *
 */
public class BranchBankBean {

	private String keyProp;
	private String keyValue;
	
	public BranchBankBean() {
		super();
	}
	public BranchBankBean(String keyProp, String keyValue) {
		super();
		this.keyProp = keyProp;
		this.keyValue = keyValue;
	}
	public String getKeyProp() {
		return keyProp;
	}
	public void setKeyProp(String keyProp) {
		this.keyProp = keyProp;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
}
