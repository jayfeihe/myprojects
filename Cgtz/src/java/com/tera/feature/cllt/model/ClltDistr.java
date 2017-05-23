package com.tera.feature.cllt.model;

import com.tera.util.DateUtils;

/**
 * 
 * 催收分配表实体类
 * <b>功能：</b>ClltDistrDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2016-01-20 09:25:01<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class ClltDistr {

	//属性部分
	private int id; //ID
	private String contractId; //合同ID
	private String clltUid; //催收人ID
	private String isCur; //是否当前标识
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getContractId () {
		return this.contractId;
	}
	public String getClltUid () {
		return this.clltUid;
	}
	public String getIsCur () {
		return this.isCur;
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
	public java.sql.Timestamp getUpdateTime () {
		return this.updateTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getUpdateTimeStr () {
		return DateUtils.formatTime(this.updateTime);
	}

	//setter部分
	public void setId (int id) {
		this.id=id;
	}
	public void setContractId (String contractId) {
		this.contractId=contractId;
	}
	public void setClltUid (String clltUid) {
		this.clltUid=clltUid;
	}
	public void setIsCur (String isCur) {
		this.isCur=isCur;
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
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}

