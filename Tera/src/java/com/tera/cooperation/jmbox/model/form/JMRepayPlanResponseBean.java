package com.tera.cooperation.jmbox.model.form;

import java.util.Date;

/**
 * 项目还款计划 返回 结果对象
 * 
 * @author XunXiake
 * 
 */
public class JMRepayPlanResponseBean {

	private Date planpayat; 	// 预期还款时间
	private int plantype; 		// 还款类型   1:本金   ; 2:利息   3:服务费
	private double amount; 		// 预计还款的金额
	
	
	public Date getPlanpayat() {
		return planpayat;
	}
	public void setPlanpayat(Date planpayat) {
		this.planpayat = planpayat;
	}
	public int getPlantype() {
		return plantype;
	}
	public void setPlantype(int plantype) {
		this.plantype = plantype;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
