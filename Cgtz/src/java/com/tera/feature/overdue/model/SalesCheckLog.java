package com.tera.feature.overdue.model;

import com.tera.util.DateUtils;

/**
 * 
 * 业务员，稽查人员催收跟进记录表实体类
 * <b>功能：</b>SalesCheckLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-13 14:02:03<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class SalesCheckLog {

	//属性部分
	private int id; //ID
	private String orgId; //所属机构
	private String role; //跟进人角色
	private String followUid; //跟进人id
	private String contractId; //合同id
	private String remark; //情况跟进
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private String readFlag; //1全部 2稽查部自己查看
	private String checkState; //稽查标识
	private String loanId;//申请ID

	
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getOrgId () {
		return this.orgId;
	}
	public String getRole () {
		return this.role;
	}
	public String getFollowUid () {
		return this.followUid;
	}
	public String getContractId () {
		return this.contractId;
	}
	public String getRemark () {
		return this.remark;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
	public String getReadFlag () {
		return this.readFlag;
	}
	public String getCheckState () {
		return this.checkState;
	}
	public String getLoanId() {
		return loanId;
	}
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
	}
	public void setRole (String role) {
		this.role=role;
	}
	public void setFollowUid (String followUid) {
		this.followUid=followUid;
	}
	public void setContractId (String contractId) {
		this.contractId=contractId;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setReadFlag (String readFlag) {
		this.readFlag=readFlag;
	}
	public void setCheckState (String checkState) {
		this.checkState=checkState;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
}

