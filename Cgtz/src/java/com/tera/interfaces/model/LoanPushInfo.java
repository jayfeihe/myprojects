package com.tera.interfaces.model;

import java.util.List;

/**
 * 债权线上推送信息bean
 * @author Jesse
 *
 */

public class LoanPushInfo {
	
//	private String appId;//第三方的appid，约定秘钥
//	private String sign;//请求参数md5值
//	private String citme;//当前10位时间戳
	private PushData data; //数据体
	

	public PushData getData() {
		return data;
	}

	public void setData(PushData data) {
		this.data = data;
	}
	


	
	
	
	

}