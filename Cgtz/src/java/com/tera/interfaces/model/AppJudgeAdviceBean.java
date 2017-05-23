package com.tera.interfaces.model;

/**
 * 评审会意见接口bean
 * @author QYANZE
 *
 */
public class AppJudgeAdviceBean {

	private String loanId; // 申请编号
	private String auditAdv; // 评审会意见
	private String loginId; // 登录名
	
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getAuditAdv() {
		return auditAdv;
	}
	public void setAuditAdv(String auditAdv) {
		this.auditAdv = auditAdv;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
