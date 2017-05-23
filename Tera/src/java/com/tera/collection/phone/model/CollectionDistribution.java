package com.tera.collection.phone.model;

import com.tera.util.DateUtils;

/**
 * 
 * 催收分配表实体类
 * <b>功能：</b>CollectionDistributionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:37:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollectionDistribution {

	//属性部分
	private int id; //id
	private String contractNo; //合同编号
	private String collectionWay; //催收渠道
	private String collectionUid; //催收人员
	private String isCur; //是否当前标识
            
	private String isHelp; //是否协催
	private String isDone; //是否催收成功
	private String state; //状态
	private java.util.Date distributionDate; //分配日期
	private String distributionDateStr; //分配日期
	private String remark; //备注
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private String updateUid; //修改人
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getCollectionWay () {
		return this.collectionWay;
	}
	public String getCollectionUid () {
		return this.collectionUid;
	}
	public String getIsCur () {
		return this.isCur;
	}
	public String getIsHelp () {
		return this.isHelp;
	}
	public String getIsDone () {
		return this.isDone;
	}
	public String getState () {
		return this.state;
	}
	public java.util.Date getDistributionDate () {
		return this.distributionDate;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getDistributionDateStr () {
		return DateUtils.formatDate(this.distributionDate);
	}
	public String getRemark () {
		return this.remark;
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
	public String getUpdateUid () {
		return this.updateUid;
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
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setCollectionWay (String collectionWay) {
		this.collectionWay=collectionWay;
	}
	public void setCollectionUid (String collectionUid) {
		this.collectionUid=collectionUid;
	}
	public void setIsCur (String isCur) {
		this.isCur=isCur;
	}
	public void setIsHelp (String isHelp) {
		this.isHelp=isHelp;
	}
	public void setIsDone (String isDone) {
		this.isDone=isDone;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setDistributionDate (java.util.Date distributionDate) {
		this.distributionDate=distributionDate;
	}
	public void setDistributionDateStr (String distributionDateStr) {
		this.distributionDateStr=distributionDateStr;
	}
	public void setRemark (String remark) {
		this.remark=remark;
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
	public void setUpdateUid (String updateUid) {
		this.updateUid=updateUid;
	}
	public void setUpdateTime (java.sql.Timestamp updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTimeStr (String updateTimeStr) {
		this.updateTimeStr=updateTimeStr;
	}

}

