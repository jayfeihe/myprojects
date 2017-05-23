package com.tera.loan.model.form;

import com.tera.customer.model.Customer;
import com.tera.loan.model.LoanApp;
import com.tera.loan.model.LoanAppCollateral;
import com.tera.loan.model.LoanAppContact;

public class LoanAppBean {
	private int appNum;
	
	//借款端申请
	private LoanApp loanapp;

	//借款申请联系人
	private LoanAppContact contact;
	//借款申请机构联系人
	private LoanAppContact fdOrgContact;
	private LoanAppContact sqOrgContact;
	private LoanAppContact cwOrgContact;
	private LoanAppContact poContact;
	//财富客户信息
	private Customer customer;
	
	//机构借款端申请
	private LoanApp orgLoanApp;

	private Customer orgCustomer;

	public LoanApp getLoanapp() {
		return loanapp;
	}

	public void setLoanapp(LoanApp loanapp) {
		this.loanapp = loanapp;
	}

	public LoanAppContact getContact() {
		return contact;
	}

	public void setContact(LoanAppContact contact) {
		this.contact = contact;
	}

	public LoanAppContact getFdOrgContact() {
		return fdOrgContact;
	}

	public void setFdOrgContact(LoanAppContact fdOrgContact) {
		this.fdOrgContact = fdOrgContact;
	}

	public LoanAppContact getSqOrgContact() {
		return sqOrgContact;
	}

	public void setSqOrgContact(LoanAppContact sqOrgContact) {
		this.sqOrgContact = sqOrgContact;
	}

	public LoanAppContact getCwOrgContact() {
		return cwOrgContact;
	}

	public void setCwOrgContact(LoanAppContact cwOrgContact) {
		this.cwOrgContact = cwOrgContact;
	}



	public LoanAppContact getPoContact() {
		return poContact;
	}

	public void setPoContact(LoanAppContact poContact) {
		this.poContact = poContact;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LoanApp getOrgLoanApp() {
		return orgLoanApp;
	}

	public void setOrgLoanApp(LoanApp orgLoanApp) {
		this.orgLoanApp = orgLoanApp;
	}

	public Customer getOrgCustomer() {
		return orgCustomer;
	}

	public void setOrgCustomer(Customer orgCustomer) {
		this.orgCustomer = orgCustomer;
	}

	public int getAppNum() {
		return appNum;
	}

	public void setAppNum(int appNum) {
		this.appNum = appNum;
	}

}
