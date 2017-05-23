package com.tera.interfaces.model;

import java.util.List;

import com.tera.audit.loan.model.Contact;
import com.tera.audit.loan.model.LoanApp;
import com.tera.audit.loan.model.LoanBase;

public class AppBean {

	private LoanBase loanBase;
	
	private LoanApp loanApp;
	
	private List<Contact> contacts;
	
	private String buttonType;
	
	private String loginId;

	public LoanBase getLoanBase() {
		return loanBase;
	}
	public void setLoanBase(LoanBase loanBase) {
		this.loanBase = loanBase;
	}
	public LoanApp getLoanApp() {
		return loanApp;
	}
	public void setLoanApp(LoanApp loanApp) {
		this.loanApp = loanApp;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	public String getButtonType() {
		return buttonType;
	}
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
