package com.tera.audit.law.model;

/**
 * 线上推送债权信息
 * @author Jesse
 *
 */
public class ProjectInfo {


	private String serial_number;//线下编号
	private String debt_amount;// 债权金额
	private String project_id; //线上项目编号
	private String project_status;//项目状态
	private String title;//项目名称
	private String project_amount;//项目金额
	private String online_time;//上线时间
	private String annualized_rate;//售卖网民利率
	private String contract_rate;//线下中间利率
	
	
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serialNumber) {
		serial_number = serialNumber;
	}
	public String getDebt_amount() {
		return debt_amount;
	}
	public void setDebt_amount(String debtAmount) {
		debt_amount = debtAmount;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String projectId) {
		project_id = projectId;
	}
	public String getProject_status() {
		return project_status;
	}
	public void setProject_status(String projectStatus) {
		project_status = projectStatus;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProject_amount() {
		return project_amount;
	}
	public void setProject_amount(String projectAmount) {
		project_amount = projectAmount;
	}
	public String getOnline_time() {
		return online_time;
	}
	public void setOnline_time(String onlineTime) {
		online_time = onlineTime;
	}
	public String getAnnualized_rate() {
		return annualized_rate;
	}
	public void setAnnualized_rate(String annualizedRate) {
		annualized_rate = annualizedRate;
	}
	public String getContract_rate() {
		return contract_rate;
	}
	public void setContract_rate(String contractRate) {
		contract_rate = contractRate;
	}
	
	
}
