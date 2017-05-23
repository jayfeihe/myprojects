package com.tera.interfaces.model;

/**
 * App诉讼接口bean
 * @author QYANZE
 *
 */
public class AppLoanLawBean {

	private int id;
	
	private String loanId; // 申请编号
	
	private String lawState; // 诉讼情况 0无 1有
	private String lawRemark; // 情况说明  
	
	private String lawCheckState; // 诉讼处理 0不通过  1通过
	private String lawCheckRemark; // 处理说明
	
	private String targetType; // 对象类型  1：申请  2：担保
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLawState() {
		return lawState;
	}
	public void setLawState(String lawState) {
		this.lawState = lawState;
	}
	public String getLawRemark() {
		return lawRemark;
	}
	public void setLawRemark(String lawRemark) {
		this.lawRemark = lawRemark;
	}
	public String getLawCheckState() {
		return lawCheckState;
	}
	public void setLawCheckState(String lawCheckState) {
		this.lawCheckState = lawCheckState;
	}
	public String getLawCheckRemark() {
		return lawCheckRemark;
	}
	public void setLawCheckRemark(String lawCheckRemark) {
		this.lawCheckRemark = lawCheckRemark;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
}
