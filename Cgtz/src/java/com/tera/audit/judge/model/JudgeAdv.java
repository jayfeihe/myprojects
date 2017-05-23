package com.tera.audit.judge.model;

import java.sql.Timestamp;

import com.tera.util.DateUtils;

/**
 * 
 * 评审会意见实体类
 * <b>功能：</b>JudgeAdvDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-11 15:43:53<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class JudgeAdv {

	//属性部分
	private int id; //ID
	private String loanId; //申请id
	private int num; //序号
	private String auditUid; //评审人
	private String auditState; //评审情况(1未评2已评)
	private String auditAdv; //评审意见
	private java.sql.Timestamp auditTime; //评审时间
	private String auditTimeStr; //评审时间
	private String state; //状态
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr;
	
	private String auditUser; // 评审人，页面显示

	public JudgeAdv() {
		super();
	}
	public JudgeAdv(String loanId, int num, String auditUid, Timestamp createTime) {
		super();
		this.loanId = loanId;
		this.num = num;
		this.auditUid = auditUid;
		this.createTime = createTime;
	}
	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLoanId () {
		return this.loanId;
	}
	public int getNum () {
		return this.num;
	}
	public String getAuditUid () {
		return this.auditUid;
	}
	public String getAuditState () {
		return this.auditState;
	}
	public String getAuditAdv () {
		return this.auditAdv;
	}
	public java.sql.Timestamp getAuditTime () {
		return this.auditTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getAuditTimeStr () {
		return DateUtils.formatTime(this.auditTime);
	}
	public String getState () {
		return this.state;
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLoanId (String loanId) {
		this.loanId=loanId;
	}
	public void setNum (int num) {
		this.num=num;
	}
	public void setAuditUid (String auditUid) {
		this.auditUid=auditUid;
	}
	public void setAuditState (String auditState) {
		this.auditState=auditState;
	}
	public void setAuditAdv (String auditAdv) {
		this.auditAdv=auditAdv;
	}
	public void setAuditTime (java.sql.Timestamp auditTime) {
		this.auditTime=auditTime;
	}
	public void setAuditTimeStr (String auditTimeStr) {
		this.auditTimeStr=auditTimeStr;
	}
	public void setState (String state) {
		this.state=state;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		return DateUtils.formatTime(this.createTime);
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}

