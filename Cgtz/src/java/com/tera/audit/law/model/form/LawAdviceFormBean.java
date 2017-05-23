package com.tera.audit.law.model.form;

/**
 * 法律意见表单Bean
 * @author QYANZE
 *
 */
public class LawAdviceFormBean {

	private String loanId;
	private String remark;
	
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
