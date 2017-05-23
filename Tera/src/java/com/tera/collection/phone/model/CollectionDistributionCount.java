package com.tera.collection.phone.model;

import java.sql.Timestamp;

import com.tera.util.DateUtils;

/**
 * 
 * 催收分配表实体类
 * <b>功能：</b>CollectionDistributionDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:37:50<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollectionDistributionCount {//分配统计model

	//属性部分
	private int taskNum; 			  			//分配统计总数
	private String collectionUid;     			//催收人员
	private String collectionUidName; 			//合同编号
	private double monthAmountAll;    			//月还总额的求和统计值
	private java.util.Date distributionDate; 	//分配日期
	private String distributionDateStr; 		//分配日期
	private String orgName;						//部门名称
	//add by wangyongliang 20150713
	private String name;						//催收人姓名
	private java.sql.Timestamp distributionMaxDate;	//分配日期最大区间
	private java.sql.Timestamp distributionMinDate;	//分配日期最小区间
	public int getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}
	public String getCollectionUid() {
		return collectionUid;
	}
	public void setCollectionUid(String collectionUid) {
		this.collectionUid = collectionUid;
	}
	public String getCollectionUidName() {
		return collectionUidName;
	}
	public void setCollectionUidName(String collectionUidName) {
		this.collectionUidName = collectionUidName;
	}
	public double getMonthAmountAll() {
		return monthAmountAll;
	}
	public void setMonthAmountAll(double monthAmountAll) {
		this.monthAmountAll = monthAmountAll;
	}
	public java.util.Date getDistributionDate() {
		return distributionDate;
	}
	public void setDistributionDate(java.util.Date distributionDate) {
		this.distributionDate = distributionDate;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setDistributionDateStr (String distributionDateStr) {
		this.distributionDateStr=distributionDateStr;
	}
	//getter部分,Date类型的修改获取String的方法
	public String getDistributionDateStr () {
		return DateUtils.formatDate(this.distributionDate);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public java.util.Date getDistributionMaxDate() {
		return distributionMaxDate;
	}
	public void setDistributionMaxDate(Timestamp distributionMaxDate) {
		this.distributionMaxDate = distributionMaxDate;
	}
	public java.util.Date getDistributionMinDate() {
		return distributionMinDate;
	}
	public void setDistributionMinDate(Timestamp distributionMinDate) {
		this.distributionMinDate = distributionMinDate;
	}       
	
}

