package com.tera.cooperation.dinxuan.model;


/**
 * 鼎轩上限金额实体类
 * 
 * @author QYANZE
 *
 */
public class CeilingAmountQBean {

	private int paramId; // 参数表id
	private double ceilingAmount; // 鼎轩上限金额
	private double passAmountOfWeek; // 本周审批通过金额
	private double leaveAmountOfWeek; // 本周剩余审批金额
	private double signAmountOfWeek; // 本周签约金额
	private double leaveSignAmountOfWeek; //本周剩余签约金额 
	
	public int getParamId() {
		return paramId;
	}
	public void setParamId(int paramId) {
		this.paramId = paramId;
	}
	public double getCeilingAmount() {
		return ceilingAmount;
	}
	public void setCeilingAmount(double ceilingAmount) {
		this.ceilingAmount = ceilingAmount;
	}
	public double getPassAmountOfWeek() {
		return passAmountOfWeek;
	}
	public void setPassAmountOfWeek(double passAmountOfWeek) {
		this.passAmountOfWeek = passAmountOfWeek;
	}
	public double getLeaveAmountOfWeek() {
		return leaveAmountOfWeek;
	}
	public void setLeaveAmountOfWeek(double leaveAmountOfWeek) {
		this.leaveAmountOfWeek = leaveAmountOfWeek;
	}
	public double getSignAmountOfWeek() {
		return signAmountOfWeek;
	}
	public void setSignAmountOfWeek(double signAmountOfWeek) {
		this.signAmountOfWeek = signAmountOfWeek;
	}
	public double getLeaveSignAmountOfWeek() {
		return leaveSignAmountOfWeek;
	}
	public void setLeaveSignAmountOfWeek(double leaveSignAmountOfWeek) {
		this.leaveSignAmountOfWeek = leaveSignAmountOfWeek;
	}
}
