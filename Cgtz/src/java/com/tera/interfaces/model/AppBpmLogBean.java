package com.tera.interfaces.model;

/**
 * app 流程日志实体
 * 
 * @author QYANZE
 *
 */
public class AppBpmLogBean {

	private String operatorName; // 处理人
	
	private String inTime; // 接收时间
	
	private String outTime; // 处理时间
	
	private String node; // 节点
	
	private String decision; // 决策结果
	
	private String remark; // 决策说明

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
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
}
