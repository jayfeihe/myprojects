package com.tera.interfaces.model;

import com.tera.feature.loanguar.model.LoanGuar;

/** 
 * App担保接口bean
 * @author QYANZE
 *
 */
public class AppGuarBean {

	private LoanGuar loanGuar;
	private String loginId;
	
	public LoanGuar getLoanGuar() {
		return loanGuar;
	}
	public void setLoanGuar(LoanGuar loanGuar) {
		this.loanGuar = loanGuar;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
