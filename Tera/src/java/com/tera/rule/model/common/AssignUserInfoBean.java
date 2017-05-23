package com.tera.rule.model.common;

public class AssignUserInfoBean {
	/**
	 * 人员编号
	 */
	private String userId;
	/**
	 * 	手中未审核/审批单子数量
	 */
	private Integer surplus;
	/**
	 * 当日已审核/审批单子数量
	 */
	private Integer processed;
	/**
	 * 员工手中单子未处理单子最早进单时间或者 未处理最长时间
	 */
//	private Date firstSurplus;
	/**
	 * 员工手中单子未处理单子最早进单时间或者 未处理最长时间单位（天）
	 */
	private Integer overstockDay;
	
	/**
	 * 状态 用户账号是否挂起 如无该功能请填写为1
	 */
	private Integer status;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getSurplus() {
		return surplus;
	}
	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}
	public Integer getProcessed() {
		return processed;
	}
	public void setProcessed(Integer processed) {
		this.processed = processed;
	}
//	public Date getFirstSurplus() {
//		return firstSurplus;
//	}
//	public void setFirstSurplus(Date firstSurplus) {
//		this.firstSurplus = firstSurplus;
//	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOverstockDay() {
		return overstockDay;
	}
	public void setOverstockDay(Integer overstockDay) {
		this.overstockDay = overstockDay;
	}
	@Override
	public String toString() {
		return "AssignUserInfo [userId=" + userId + ", surplus=" + surplus
				+ ", processed=" + processed + ", overstockDay=" + overstockDay 
				+ ", status=" + status + "]";
	}
	
}
