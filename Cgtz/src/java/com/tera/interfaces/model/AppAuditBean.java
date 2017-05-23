package com.tera.interfaces.model;

import java.util.List;

import com.tera.audit.judge.model.JudgeAdv;

/**
 * App接口审批bean
 * @author QYANZE
 *
 */
public class AppAuditBean {

	private String loanId; // 申请编号
	
	private String isTgth; // 是否共同借款人
	
	private String nodeType; // 节点类型 1、2
	
	private String node; // 退回节点名称
	
	private String decision; // 决策  1通过 0不通过
	
	private String remark; // 说明
	
	private List<AppJudgeUser> judgeUsers; // 评审会成员 
	
	private List<JudgeAdv> judgeAdvs;// 评审会意见列表
	
	private String loginId; // 登录人

	private List<String> judgeUids; // 接受回传的uid

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getIsTgth() {
		return isTgth;
	}

	public void setIsTgth(String isTgth) {
		this.isTgth = isTgth;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<AppJudgeUser> getJudgeUsers() {
		return judgeUsers;
	}

	public void setJudgeUsers(List<AppJudgeUser> judgeUsers) {
		this.judgeUsers = judgeUsers;
	}

	public List<JudgeAdv> getJudgeAdvs() {
		return judgeAdvs;
	}

	public void setJudgeAdvs(List<JudgeAdv> judgeAdvs) {
		this.judgeAdvs = judgeAdvs;
	}

	public List<String> getJudgeUids() {
		return judgeUids;
	}

	public void setJudgeUids(List<String> judgeUids) {
		this.judgeUids = judgeUids;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
