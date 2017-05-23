package com.tera.audit.branch.model;


/** 审核表单bean
 * @author QYANZE
 *
 */
public class AuditFormBean {

	private String loanId; // 申请编号
	
	private String decision; // 决策 1：通过,0：不通过
	
	private String node; // 节点
	
	private String remark; // 说明

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
}
