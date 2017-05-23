package com.tera.house.model.form;

import com.tera.house.model.HouseAntifraud;
import com.tera.house.model.HouseDecision;

/** 核价实体
 * @author QYANZE
 *
 */
public class PriceFBean {

	private int id;	//申请的 ID
	private String appId; //申请的 APPID

	private HouseDecision decision;
	private HouseAntifraud antifraud;
	
	private String buttonType;// save 保存，submit 提交，decision 拒贷,antifraud 反欺诈

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

	public HouseDecision getDecision() {
		return decision;
	}

	public void setDecision(HouseDecision decision) {
		this.decision = decision;
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
}
