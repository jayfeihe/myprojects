package com.tera.car.model.form;

import com.tera.car.model.CarAntifraud;
import com.tera.car.model.CarDecision;

/** 核价实体
 * @author QYANZE
 *
 */
public class PriceFBean {

	private int id;	//申请的 ID
	private String appId; //申请的 APPID

	private CarDecision decision;
	private CarAntifraud antifraud;
	
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

	public CarDecision getDecision() {
		return decision;
	}

	public void setDecision(CarDecision decision) {
		this.decision = decision;
	}

	public CarAntifraud getAntifraud() {
		return antifraud;
	}

	public void setAntifraud(CarAntifraud antifraud) {
		this.antifraud = antifraud;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
}
