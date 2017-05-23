package com.tera.report.model.interform;

/**
 * 线上返回报表json信息实体
 * @author QYANZE
 *
 */
public class ResultJson {

	private String code; // 状态码

	private String msg; // 信息
	
	private ResultInfo data; // 返回信息

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

	public ResultInfo getData() {
		return data;
	}

	public void setData(ResultInfo data) {
		this.data = data;
	}
}
