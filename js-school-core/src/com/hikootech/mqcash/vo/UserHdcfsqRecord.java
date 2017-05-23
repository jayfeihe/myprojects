package com.hikootech.mqcash.vo;

//好贷网  借款重复查询次数(类型码650)
public class UserHdcfsqRecord {

	private String[] message;// 返回信息
	private String type;// 贷款类型(不为空命中)
	private String money;// 贷款金额
	private int c_time;// 申请时间
	private String use_company;// 贷款用途

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public int getC_time() {
		return c_time;
	}

	public void setC_time(int c_time) {
		this.c_time = c_time;
	}

	public String getUse_company() {
		return use_company;
	}

	public void setUse_company(String use_company) {
		this.use_company = use_company;
	}

}
