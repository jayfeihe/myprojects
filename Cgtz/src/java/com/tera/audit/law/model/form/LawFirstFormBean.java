package com.tera.audit.law.model.form;

/** 评审会初审表单Bean
 * @author QYANZE
 *
 */
public class LawFirstFormBean {

	private String loanId; // 申请编号
	
	private String decision; // 决策 1：通过,0：不通过
	
	private String node; // 节点
	
	private String remark; // 说明
	
	private String lawInsideUser; // 内勤成员
	
	private String buttonType; // 按钮类型

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLawInsideUser() {
		return lawInsideUser;
	}

	public void setLawInsideUser(String lawInsideUser) {
		this.lawInsideUser = lawInsideUser;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
}
