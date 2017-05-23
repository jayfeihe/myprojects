package com.tera.audit.account.model;

import com.tera.util.DateUtils;

/**
 * 
 * 公司收支明细表实体类
 * <b>功能：</b>BillBaseDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 09:15:17<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class BillBase {

	//属性部分
	private int id; //
	private String subject; //科目（1收利息，2收本金3付出借人回款4付线上垫付5付保证金））
	private String type; //(1收入，2支出)
	private String loanId; //
	private String contractId; // 合同号
	private String lendUsreId; //
	private String proof; //
	private int num;// 垫付期数
	private double amt; //
	private String remark; //
	private String state; //（其他默认1）线上回款默认是2，平账后变为1
	private java.sql.Timestamp createTime; //
	private String createTimeStr; //
	private String createUid; //

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getSubject () {
		return this.subject;
	}
	public String getType () {
		return this.type;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getLendUsreId () {
		return this.lendUsreId;
	}
	public String getProof () {
		return this.proof;
	}
	public double getAmt () {
		return this.amt;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getState () {
		return this.state;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public String getCreateUid () {
		return this.createUid;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setSubject (String subject) {
		this.subject=subject;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setLendUsreId (String lendUsreId) {
		this.lendUsreId=lendUsreId;
	}
	public void setProof (String proof) {
		this.proof=proof;
	}
	public void setAmt (double amt) {
		this.amt=amt;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setCreateUid (String createUid) {
		this.createUid=createUid;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}

