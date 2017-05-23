package com.tera.interfaces.model;

import java.util.List;

public class RetMsg {

	private String code;//0000成功  1111失败
	private String msg;//说明
	private List<PushData> data;
	
	
	public RetMsg(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<PushData> getData() {
		return data;
	}
	public void setData(List<PushData> data) {
		this.data = data;
	}
}
