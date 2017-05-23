package com.tera.feature.cllt.model;

import com.tera.util.DateUtils;

/**
 * 
 * T_CLLT_LOG实体类
 * <b>功能：</b>ClltLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-19 21:20:36<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class ClltLog {

	//属性部分
	private int id; //ID
	private String loanId; //申请id
	private String contractId; //合同id
	private String clltUid; //催收人员id
	private String clltName;//催收人员姓名
	private String name; //姓名/机构名称
	private String idType; //证件类型
	private String idNo; //证件号码
	private String clltWay; //催收方式
	private String clltRemark; //催收说明
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间

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
	public String getClltUid () {
		return this.clltUid;
	}
	
	public String getClltName() {
		return clltName;
	}
	public String getName () {
		return this.name;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public String getClltWay () {
		return this.clltWay;
	}
	public String getClltRemark () {
		return this.clltRemark;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
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
	public void setClltUid (String clltUid) {
		this.clltUid=clltUid;
	}
	
	public void setClltName(String clltName) {
		this.clltName = clltName;
	}
	public void setName (String name) {
		this.name=name;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setClltWay (String clltWay) {
		this.clltWay=clltWay;
	}
	public void setClltRemark (String clltRemark) {
		this.clltRemark=clltRemark;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
}

