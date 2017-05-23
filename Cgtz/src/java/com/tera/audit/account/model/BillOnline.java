package com.tera.audit.account.model;

import com.tera.util.DateUtils;

/**
 * 
 * 线上放款表实体类
 * <b>功能：</b>BillOnlineDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 09:17:54<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class BillOnline {

	//属性部分
	private int id; //
	private String type; //(1出借人模式2直投)
	private String loanId; //
	private String contractId; // 合同号
	private String lendUsreId; //
	private String proof; //
	private double amt; //
	private String remark; //
	private String state; //
	private java.sql.Timestamp createTime; //
	private String createTimeStr; //
	private String createUid; //

	//getter部分
	public int getId () {
		return this.id;
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
}

