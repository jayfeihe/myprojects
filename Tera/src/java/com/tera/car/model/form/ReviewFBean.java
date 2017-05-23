package com.tera.car.model.form;



public class ReviewFBean {
	
	private int id;				//申请的 ID
	private String appId;		//申请的 APPID
	private String buttonType;	//submit 通过，back 退回签约
	private String backMsg;			//退回原因
	
	

	public String getBackMsg() {
		return backMsg;
	}
	public void setBackMsg(String backMsg) {
		this.backMsg = backMsg;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	
}

