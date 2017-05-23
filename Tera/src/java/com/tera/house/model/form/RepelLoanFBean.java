package com.tera.house.model.form;

/**
 * 积木盒子拒贷传入参数
 * @author Administrator
 *
 */
public class RepelLoanFBean {

	private String appId; //申请ID
	
	/*拒代码1和拒代码2合并起来存到t_bpm_log表里的log_content4字段里*/
	private String decisionCode3; //拒贷码1
	private String decisionCode4; //拒贷码2
	
	private String feedbackDescribe;  //反馈销售描述（实际存到t_bpm_log表里的log_content4字段里）

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDecisionCode3() {
		return decisionCode3;
	}

	public void setDecisionCode3(String decisionCode3) {
		this.decisionCode3 = decisionCode3;
	}

	public String getDecisionCode4() {
		return decisionCode4;
	}

	public void setDecisionCode4(String decisionCode4) {
		this.decisionCode4 = decisionCode4;
	}

	public String getFeedbackDescribe() {
		return feedbackDescribe;
	}

	public void setFeedbackDescribe(String feedbackDescribe) {
		this.feedbackDescribe = feedbackDescribe;
	}
	
}
