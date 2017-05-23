package com.tera.sys.service;

public class DataDirt {
	
	private String keyProp; //字典属性
	
	private String keyValue; //字典值
	
	public DataDirt( ) {
		
	}
	public DataDirt(String keyProp, String keyValue) {
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
