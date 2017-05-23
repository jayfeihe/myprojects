package com.tera.audit.account.model;

import com.tera.util.DateUtils;

/**
 * 
 * 借款人交易记录表
实体类
 * <b>功能：</b>BillAcctDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-14 09:14:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class BillAcct {

	//属性部分
	private int id; //ID
	private String loanId; //申请ID
	private String contractId; //合同id
	private String subject; //(1收利息2收本金3续贷本金转移4放款5付保证金)
	private String type; //类型（1收，2付）
	private String num; //期数说明,例5/6
	private String proof; //凭证号
	private double amt; //金额
	private double reduce; //减免
	private String remark; //说明
	private String flags;
	private String operUid; //操作人
	private java.sql.Timestamp operTime; //时间
	private String operTimeStr; //时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public String getContractId () {
		return this.contractId;
	}
	public String getSubject () {
		return this.subject;
	}
	public String getType () {
		return this.type;
	}
	public String getNum () {
		return this.num;
	}
	public String getProof () {
		return this.proof;
	}
	public double getAmt () {
		return this.amt;
	}
	public double getReduce () {
		return this.reduce;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getOperUid () {
		return this.operUid;
	}
	public java.sql.Timestamp getOperTime () {
		return this.operTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getOperTimeStr () {
		return DateUtils.formatTime(this.operTime);
	}

	
	
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setContractId (String contractId) {
		this.contractId=contractId;
	}
	public void setSubject (String subject) {
		this.subject=subject;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setNum (String num) {
		this.num=num;
	}
	public void setProof (String proof) {
		this.proof=proof;
	}
	public void setAmt (double amt) {
		this.amt=amt;
	}
	public void setReduce (double reduce) {
		this.reduce=reduce;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setOperUid (String operUid) {
		this.operUid=operUid;
	}
	public void setOperTime (java.sql.Timestamp operTime) {
		this.operTime=operTime;
	}
	public void setOperTimeStr (String operTimeStr) {
		this.operTimeStr=operTimeStr;
	}

}

