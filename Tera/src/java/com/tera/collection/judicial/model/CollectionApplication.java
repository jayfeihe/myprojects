package com.tera.collection.judicial.model;

import com.tera.util.DateUtils;

/**
 * 
 * 实体类
 * <b>功能：</b>CollectionApplicationDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2015-06-12 10:43:19<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class CollectionApplication {

	//属性部分
	private int id; //id
	private String contractNo; //合同编号
	private String applyType; //申请类型
	private String collectionWay; //催收渠道
	private String distributionState; //分配状态
	private String applyUid; //申请人
	private java.sql.Timestamp applyTime; //申请时间
	private String applyTimeStr; //申请时间
	private String applyText; //申请内容
	private String checkUid; //复核人
	private java.sql.Timestamp checkTime; //复核时间
	private String checkTimeStr; //复核时间
	private String checkText; //复核意见
	private String checkResult; //复核结果
	private String approvalUid; //审批人
	private java.sql.Timestamp approvalTime; //审批时间
	private String approvalTimeStr; //审批时间
	private String approvalText; //审批意见
	private String approvalResult; //审批结果
	private String state; //状态
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
	public String getApplyType () {
		return this.applyType;
	}
	public String getCollectionWay () {
		return this.collectionWay;
	}
	public String getDistributionState () {
		return this.distributionState;
	}
	public String getApplyUid () {
		return this.applyUid;
	}
	public java.sql.Timestamp getApplyTime () {
		return this.applyTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getApplyTimeStr () {
		return DateUtils.formatTime(this.applyTime);
	}
	public String getApplyText () {
		return this.applyText;
	}
	public String getCheckUid () {
		return this.checkUid;
	}
	public java.sql.Timestamp getCheckTime () {
		return this.checkTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getCheckTimeStr () {
		return DateUtils.formatTime(this.checkTime);
	}
	public String getCheckText () {
		return this.checkText;
	}
	public String getCheckResult () {
		return this.checkResult;
	}
	public String getApprovalUid () {
		return this.approvalUid;
	}
	public java.sql.Timestamp getApprovalTime () {
		return this.approvalTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getApprovalTimeStr () {
		return DateUtils.formatTime(this.approvalTime);
	}
	public String getApprovalText () {
		return this.approvalText;
	}
	public String getApprovalResult () {
		return this.approvalResult;
	}
	public String getState () {
		return this.state;
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
	public void setApplyType (String applyType) {
		this.applyType=applyType;
	}
	public void setCollectionWay (String collectionWay) {
		this.collectionWay=collectionWay;
	}
	public void setDistributionState (String distributionState) {
		this.distributionState=distributionState;
	}
	public void setApplyUid (String applyUid) {
		this.applyUid=applyUid;
	}
	public void setApplyTime (java.sql.Timestamp applyTime) {
		this.applyTime=applyTime;
	}
	public void setApplyTimeStr (String applyTimeStr) {
		this.applyTimeStr=applyTimeStr;
	}
	public void setApplyText (String applyText) {
		this.applyText=applyText;
	}
	public void setCheckUid (String checkUid) {
		this.checkUid=checkUid;
	}
	public void setCheckTime (java.sql.Timestamp checkTime) {
		this.checkTime=checkTime;
	}
	public void setCheckTimeStr (String checkTimeStr) {
		this.checkTimeStr=checkTimeStr;
	}
	public void setCheckText (String checkText) {
		this.checkText=checkText;
	}
	public void setCheckResult (String checkResult) {
		this.checkResult=checkResult;
	}
	public void setApprovalUid (String approvalUid) {
		this.approvalUid=approvalUid;
	}
	public void setApprovalTime (java.sql.Timestamp approvalTime) {
		this.approvalTime=approvalTime;
	}
	public void setApprovalTimeStr (String approvalTimeStr) {
		this.approvalTimeStr=approvalTimeStr;
	}
	public void setApprovalText (String approvalText) {
		this.approvalText=approvalText;
	}
	public void setApprovalResult (String approvalResult) {
		this.approvalResult=approvalResult;
	}
	public void setState (String state) {
		this.state=state;
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

