package com.tera.audit.law.model;

import java.util.List;

public class OnlineInfo {

//	private String sign;//请求参数md5值
//	private String citme;//当前10位时间戳
	private List<ProjectInfo> data;

	public List<ProjectInfo> getData() {
		return data;
	}
	public void setData(List<ProjectInfo> data) {
		this.data = data;
	}
	
	
	
}
