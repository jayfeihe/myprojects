package com.tera.collection.phone.model;

import com.tera.util.DateUtils;

/**
 * 
 * 催收记录表实体类
 * <b>功能：</b>CollectionRecordDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-08 14:39:13<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollectionRecord {

	//属性部分
	private int id; //id
	private String collectionUid; //催收人
	private String collectionWay; //催收渠道
	private String isHelp; //是否协催
	private String customerId; //客户id
	private String contractNo; //合同编号
	private String idType; //证件类型
	private String idNo; //证件号码
	private java.sql.Timestamp callTime; //拨打时间
	private String callTimeStr; //拨打时间
	private int lateDays; //逾期天数
	private String tel; //联系方式
	private String answerRelation; //接听人关系
	private String answerName; //联系人
	private String phoneSummary; //电催摘要
	private String remark; //备注
	private String createUid; //创建人
	private java.sql.Timestamp createTime; //创建时间
	private String createTimeStr; //创建时间
	private String updateUid; //修改人
	private java.sql.Timestamp updateTime; //修改时间
	private String updateTimeStr; //修改时间
	private String collectionName; //催收人姓名

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getCollectionUid () {
		return this.collectionUid;
	}
	public String getCollectionWay () {
		return this.collectionWay;
	}
	public String getIsHelp () {
		return this.isHelp;
	}
	public String getCustomerId () {
		return this.customerId;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public String getIdType () {
		return this.idType;
	}
	public String getIdNo () {
		return this.idNo;
	}
	public java.sql.Timestamp getCallTime () {
		return this.callTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCallTimeStr () {
		return DateUtils.formatTime(this.callTime);
	}
	public int getLateDays () {
		return this.lateDays;
	}
	public String getTel () {
		return this.tel;
	}
	public String getAnswerRelation () {
		return this.answerRelation;
	}
	public String getAnswerName () {
		return this.answerName;
	}
	public String getPhoneSummary () {
		return this.phoneSummary;
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
	public void setCollectionUid (String collectionUid) {
		this.collectionUid=collectionUid;
	}
	public void setCollectionWay (String collectionWay) {
		this.collectionWay=collectionWay;
	}
	public void setIsHelp (String isHelp) {
		this.isHelp=isHelp;
	}
	public void setCustomerId (String customerId) {
		this.customerId=customerId;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setIdType (String idType) {
		this.idType=idType;
	}
	public void setIdNo (String idNo) {
		this.idNo=idNo;
	}
	public void setCallTime (java.sql.Timestamp callTime) {
		this.callTime=callTime;
	}
	public void setCallTimeStr (String callTimeStr) {
		this.callTimeStr=callTimeStr;
	}
	public void setLateDays (int lateDays) {
		this.lateDays=lateDays;
	}
	public void setTel (String tel) {
		this.tel=tel;
	}
	public void setAnswerRelation (String answerRelation) {
		this.answerRelation=answerRelation;
	}
	public void setAnswerName (String answerName) {
		this.answerName=answerName;
	}
	public void setPhoneSummary (String phoneSummary) {
		this.phoneSummary=phoneSummary;
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
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

}

