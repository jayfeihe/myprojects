package com.tera.audit.loan.model.form;

import java.util.List;

import com.tera.audit.loan.model.Contact;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.LoanBase;

/**
 * 表单对象Bean
 * @author QYANZE
 *
 */
public class AppFormBean {

	private LoanApp appTypeLoan; // 个人申请信息
	private LoanApp comTypeLoan; // 公司申请信息
	private String saleRemark; // 客服备注
	private LoanBase loanBase; // 申请基本信息
	private List<Contact> contacts; // 联系人
	
	private String loanWay; // 渠道类型
	
	private String buttonType; // 按钮类型   save:保存,  giveUp:客户放弃,  submit:提交
	
	private String contractId; // 续贷使用
	
	public LoanApp getAppTypeLoan() {
		return appTypeLoan;
	}
	public void setAppTypeLoan(LoanApp appTypeLoan) {
		this.appTypeLoan = appTypeLoan;
	}
	public LoanApp getComTypeLoan() {
		return comTypeLoan;
	}
	public void setComTypeLoan(LoanApp comTypeLoan) {
		this.comTypeLoan = comTypeLoan;
	}
	public String getSaleRemark() {
		return saleRemark;
	}
	public void setSaleRemark(String saleRemark) {
		this.saleRemark = saleRemark;
	}
	public LoanBase getLoanBase() {
		return loanBase;
	}
	public void setLoanBase(LoanBase loanBase) {
		this.loanBase = loanBase;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	public String getLoanWay() {
		return loanWay;
	}
	public void setLoanWay(String loanWay) {
		this.loanWay = loanWay;
	}
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
}
