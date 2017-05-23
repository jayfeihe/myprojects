package com.tera.audit.judge.model.form;

/** 评审会初审表单Bean
 * @author QYANZE
 *
 */
public class JudgeFormBean {

	private String loanId; // 申请编号
	
	private String decision; // 决策 1：通过,0：不通过
	
	private String node; // 节点
	
	private String remark; // 说明
	
	private String[] judgeUsers; // 评审成员

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

	public String[] getJudgeUsers() {
		return judgeUsers;
	}

	public void setJudgeUsers(String[] judgeUsers) {
		this.judgeUsers = judgeUsers;
	}
}
