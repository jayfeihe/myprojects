package com.tera.audit.loan.model.form;

/** 申请讼诉查询实体
 * @author QYANZE
 *
 */
public class LoanLawQBean {

	private int id; // 主键
	
	private String loanId; // 申请id
	
	private String checkTarget; // 核查对象
	
	private String idNo; // 证件号码
	
	private String tel; // 手机号
	
	private String lawState; // 诉讼情况
	
	private String targetType; // 对象类型  1：申请  2：担保
	
	private String lawCheckState; // 诉讼复核情况

	private String isOrig; // 是否是原申请标识
	
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

	public String getCheckTarget() {
		return checkTarget;
	}

	public void setCheckTarget(String checkTarget) {
		this.checkTarget = checkTarget;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLawState() {
		return lawState;
	}

	public void setLawState(String lawState) {
		this.lawState = lawState;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getLawCheckState() {
		return lawCheckState;
	}

	public void setLawCheckState(String lawCheckState) {
		this.lawCheckState = lawCheckState;
	}

	public String getIsOrig() {
		return isOrig;
	}

	public void setIsOrig(String isOrig) {
		this.isOrig = isOrig;
	}
}
