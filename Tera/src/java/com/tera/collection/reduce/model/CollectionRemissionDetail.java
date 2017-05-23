package com.tera.collection.reduce.model;


/**
 * 
 * 减免明细记录表实体类
 * <b>功能：</b>CollectionRemissionDetailDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:49:16<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollectionRemissionDetail {

	//属性部分
	private int id; //id
	private String contractNo; //合同编号
	private int remissionId; //减免申请表ID
	private int loanRepayPlanId; //还款计划表ID
	private String remark; //备注
	private String state; //状态
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String updateUid; //修改人
	private java.sql.Timestamp updateTime; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public int getRemissionId () {
		return this.remissionId;
	}
	public int getLoanRepayPlanId () {
		return this.loanRepayPlanId;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getState () {
		return this.state;
	}
	public String getCreateUid () {
		return this.createUid;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	public String getUpdateUid () {
		return this.updateUid;
	}
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setRemissionId (int remissionId) {
		this.remissionId=remissionId;
	}
	public void setLoanRepayPlanId (int loanRepayPlanId) {
		this.loanRepayPlanId=loanRepayPlanId;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setCreateUid (String createUid) {
		this.createUid=createUid;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setUpdateUid (String updateUid) {
		this.updateUid=updateUid;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}

}

