package com.tera.audit.loan.model;

import com.tera.util.DateUtils;

/**
 * 
 * 实体类
 * <b>功能：</b>BankBranchInfoDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-02-29 06:49:49<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class BankBranchInfo {

	//属性部分
	private int id; //
	private String bankName; //
	private String city; //
	private String bankBranch; //

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getBankName () {
		return this.bankName;
	}
	public String getCity () {
		return this.city;
	}
	public String getBankBranch () {
		return this.bankBranch;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setBankName (String bankName) {
		this.bankName=bankName;
	}
	public void setCity (String city) {
		this.city=city;
	}
	public void setBranch (String bankBranch) {
		this.bankBranch=bankBranch;
	}

}

