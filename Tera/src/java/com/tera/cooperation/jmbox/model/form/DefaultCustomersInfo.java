package com.tera.cooperation.jmbox.model.form;

import java.util.Date;


/**
 * 违约的 客户信息 实体类 
 * @author XunXiake
 *
 */
public class DefaultCustomersInfo {
	
	private String projectId;		//项目id
	private String customerName;	//姓名
	private String idCard;			//身份证号
	private double moneyOverDue;	//拖欠的本金
	private double interestOverDue;	//拖欠的利息
	private double moneyLeave;		//贷款余额
	private Date payDate;			//应还款日期
	private Date loanBackDate;		//实际还款日期
	private int dueDays;			//逾期天数
	private String reason;			//逾期原因
	private int overdueNumber;		//历史逾期次数
	
	
	
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public double getMoneyOverDue() {
		return moneyOverDue;
	}
	public void setMoneyOverDue(double moneyOverDue) {
		this.moneyOverDue = moneyOverDue;
	}
	public double getInterestOverDue() {
		return interestOverDue;
	}
	public void setInterestOverDue(double interestOverDue) {
		this.interestOverDue = interestOverDue;
	}
	public double getMoneyLeave() {
		return moneyLeave;
	}
	public void setMoneyLeave(double moneyLeave) {
		this.moneyLeave = moneyLeave;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getLoanBackDate() {
		return loanBackDate;
	}
	public void setLoanBackDate(Date loanBackDate) {
		this.loanBackDate = loanBackDate;
	}
	public int getDueDays() {
		return dueDays;
	}
	public void setDueDays(int dueDays) {
		this.dueDays = dueDays;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getOverdueNumber() {
		return overdueNumber;
	}
	public void setOverdueNumber(int overdueNumber) {
		this.overdueNumber = overdueNumber;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	
}
