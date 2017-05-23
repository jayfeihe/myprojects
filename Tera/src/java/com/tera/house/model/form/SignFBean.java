package com.tera.house.model.form;

import com.tera.contract.model.Contract;
import com.tera.house.model.HouseAntifraud;


public class SignFBean {
	
	private int id;				//申请的 ID
	private String appId;		//申请的 APPID
	private HouseAntifraud antifraud;	//反欺诈处理
	private String buttonType;			// save 保存，submit 提交，decision 拒贷,antifraud 反欺诈
	private Contract contract;			//合同
	
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
	public HouseAntifraud getAntifraud() {
		return antifraud;
	}
	public void setAntifraud(HouseAntifraud antifraud) {
		this.antifraud = antifraud;
	}
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
}
