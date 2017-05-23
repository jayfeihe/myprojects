package com.tera.car.model.form;

import com.tera.car.model.CarAntifraud;
import com.tera.car.model.CarDecision;

public class SpecialApprovalFBean {
	
	private int id;				//申请的 ID
	private String appId;		//申请的 APPID

	private CarDecision decision;
	private CarAntifraud antifraud;
	
	private String buttonType;			// save 保存，submit 提交，decision 拒贷,antifraud 反欺诈
	private String carApprovalOperator;      // 审批决策人
	
	/*---------------审批决策和特殊审批拒贷的时候用到---------------------------*/
	private String feedbackDescribe;  //反馈销售描述（实际存到t_bpm_log表里的log_content2字段里）
	
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
	public String getCarApprovalOperator() {
		return carApprovalOperator;
	}
	public void setCarApprovalOperator(String carApprovalOperator) {
		this.carApprovalOperator = carApprovalOperator;
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
	public String getFeedbackDescribe() {
		return feedbackDescribe;
	}
	public void setFeedbackDescribe(String feedbackDescribe) {
		this.feedbackDescribe = feedbackDescribe;
	}
	
}
