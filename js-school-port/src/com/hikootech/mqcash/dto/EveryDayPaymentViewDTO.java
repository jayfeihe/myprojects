package com.hikootech.mqcash.dto;

import java.io.Serializable;
import java.math.BigDecimal;


	/**
	* 此类描述的是：
	* @author: zhaohefei
	* @version: 2015年10月17日 下午6:24:32
	*/
	
public class EveryDayPaymentViewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String payDay;
	private String payMon;

	private int countToPay;//共还几笔钱
	private int betweenDays;//与当前日期相差几天
	private BigDecimal dayToPayment;//当天应付金额
	public String getPayDay() {
		return payDay;
	}
	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	public String getPayMon() {
		return payMon;
	}
	public void setPayMon(String payMon) {
		this.payMon = payMon;
	}
	public int getCountToPay() {
		return countToPay;
	}
	public void setCountToPay(int countToPay) {
		this.countToPay = countToPay;
	}
	public BigDecimal getDayToPayment() {
		return dayToPayment.setScale(2);
	}
	public void setDayToPayment(BigDecimal dayToPayment) {
		this.dayToPayment = dayToPayment;
	}
	public int getBetweenDays() {
		return betweenDays;
	}
	public void setBetweenDays(int betweenDays) {
		this.betweenDays = betweenDays;
	}
}
