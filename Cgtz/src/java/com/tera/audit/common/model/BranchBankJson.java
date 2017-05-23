package com.tera.audit.common.model;

import java.util.List;

/** 
 * 支行json实体类
 * @author QYANZE
 *
 */
public class BranchBankJson {

	private String code;
	
	private String msg;
	
	private List<String> data;

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

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
}
