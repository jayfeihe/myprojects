package com.tera.checkcenter.model;

import com.tera.util.DateUtils;

/**
 * 
 * 资金流转查看页面
 * <b>功能：</b>CreditAppDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-01-14 15:25:28<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CheckCenter {

	//属性部分
	private double totalReceipts; //进账总额
	private double disMatchAmount; //未撮合金额
	private double lendLockAmount; //出借人资金锁定金额
	private double disposableAmount; //可支配金额
	private double matchOccupyAmount; //撮合占用金额
	
	//getter/setter
	public double getDisMatchAmount() {
		return disMatchAmount;
	}
	public void setDisMatchAmount(double disMatchAmount) {
		this.disMatchAmount = disMatchAmount;
	}
	public double getTotalReceipts() {
		return totalReceipts;
	}
	public void setTotalReceipts(double totalReceipts) {
		this.totalReceipts = totalReceipts;
	}
	public double getDisposableAmount() {
		return disposableAmount;
	}
	public void setDisposableAmount(double disposableAmount) {
		this.disposableAmount = disposableAmount;
	}
	public double getMatchOccupyAmount() {
		return matchOccupyAmount;
	}
	public void setMatchOccupyAmount(double matchOccupyAmount) {
		this.matchOccupyAmount = matchOccupyAmount;
	}
	public double getLendLockAmount() {
		return lendLockAmount;
	}
	public void setLendLockAmount(double lendLockAmount) {
		this.lendLockAmount = lendLockAmount;
	}
}

