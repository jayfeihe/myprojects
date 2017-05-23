package com.tera.feature.lenduser.model;

import com.tera.util.DateUtils;

/**
 * 
 * 出借人资金变动记录实体类
 * <b>功能：</b>LendUserLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-10 22:42:46<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class LendUserLog {

	//属性部分
	private int id; //
	private String lendUserId; //出借人id
	private String type; //变动类型
	private double amt; //变动金额
	private String remark; //说明
	private String bizKey; //业务主键
	private String createUid; //创建人id
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //页面显示时间
	private String  proof;//凭证号

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getLendUserId () {
		return this.lendUserId;
	}
	public String getType () {
		return this.type;
	}
	public double getAmt () {
		return this.amt;
	}
	public String getRemark () {
		return this.remark;
	}
	public String getBizKey () {
		return this.bizKey;
	}
	public String getCreateUid () {
		return this.createUid;
	}
	public java.sql.Timestamp getCreateTime () {
		return this.createTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCreateTimeStr () {
		return DateUtils.formatTime(this.createTime);
	}
    
	public String getProof() {
		return proof;
	}
	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setLendUserId (String lendUserId) {
		this.lendUserId=lendUserId;
	}
	public void setType (String type) {
		this.type=type;
	}
	public void setAmt (double amt) {
		this.amt=amt;
	}
	public void setRemark (String remark) {
		this.remark=remark;
	}
	public void setBizKey (String bizKey) {
		this.bizKey=bizKey;
	}
	public void setCreateUid (String createUid) {
		this.createUid=createUid;
	}
	public void setCreateTime (java.sql.Timestamp createTime) {
		this.createTime=createTime;
	}
	public void setCreateTimeStr (String createTimeStr) {
		this.createTimeStr=createTimeStr;
	}
	public void setProof(String proof) {
		this.proof = proof;
	}
}

