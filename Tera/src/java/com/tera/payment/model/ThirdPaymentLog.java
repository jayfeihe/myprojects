package com.tera.payment.model;

import com.tera.util.DateUtils;

/**
 * 
 * <br>
 * <b>功能：</b>ThirdPaymentLogDao<br>
 * <b>作者：</b>CodeGenerater<br>
 * <b>日期：</b>2014-07-04 17:49:44<br>
 * <b>版权所有：<b>天瑞兴隆<br>
 */
public class ThirdPaymentLog {

	//属性部分
	private int id; //ID
	private String orderNo; //交易订单号
	private String sn; //批序号
	private int paymentId; //收付ID
	private String contractNo; //合同号
	private int periodNum; //期数
	private java.sql.Timestamp sendTime; //发盘时间
	private String sendTimeStr; //发盘时间
	private String sourceAccount; //原账户
	private String targetAccount; //目标账户
	private double amount; //金额
	private String subject; //科目
	private String state; //状态
	private String reason; //原因
	private java.sql.Timestamp receiveTime; //回盘时间
	private String receiveTimeStr; //回盘时间
	private String operator; //操作员
	private String orgId; //所属机构
	private java.sql.Timestamp createTime; //创建日期
	private String createTimeStr; //创建日期
	private java.sql.Timestamp updateTime; //修改日期
	private String updateTimeStr; //修改日期

	//getter部分
	public int getId () {
		return this.id;
	}
	public String getOrderNo () {
		return this.orderNo;
	}
	public String getSn () {
		return this.sn;
	}
	public int getPaymentId () {
		return this.paymentId;
	}
	public String getContractNo () {
		return this.contractNo;
	}
	public int getPeriodNum () {
		return this.periodNum;
	}
	public java.sql.Timestamp getSendTime () {
		return this.sendTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getSendTimeStr () {
		return DateUtils.formatTime(this.sendTime);
	}
	public String getSourceAccount () {
		return this.sourceAccount;
	}
	public String getTargetAccount () {
		return this.targetAccount;
	}
	public double getAmount () {
		return this.amount;
	}
	public String getSubject () {
		return this.subject;
	}
	public String getState () {
		return this.state;
	}
	public String getReason () {
		return this.reason;
	}
	public java.sql.Timestamp getReceiveTime () {
		return this.receiveTime;
	}
	//getter部分,Timestamp类型的修改获取String的方法
	public String getReceiveTimeStr () {
		return DateUtils.formatTime(this.receiveTime);
	}
	public String getOperator () {
		return this.operator;
	}
	public String getOrgId () {
		return this.orgId;
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
	public void setOrderNo (String orderNo) {
		this.orderNo=orderNo;
	}
	public void setSn (String sn) {
		this.sn=sn;
	}
	public void setPaymentId (int paymentId) {
		this.paymentId=paymentId;
	}
	public void setContractNo (String contractNo) {
		this.contractNo=contractNo;
	}
	public void setPeriodNum (int periodNum) {
		this.periodNum=periodNum;
	}
	public void setSendTime (java.sql.Timestamp sendTime) {
		this.sendTime=sendTime;
	}
	public void setSendTimeStr (String sendTimeStr) {
		this.sendTimeStr=sendTimeStr;
	}
	public void setSourceAccount (String sourceAccount) {
		this.sourceAccount=sourceAccount;
	}
	public void setTargetAccount (String targetAccount) {
		this.targetAccount=targetAccount;
	}
	public void setAmount (double amount) {
		this.amount=amount;
	}
	public void setSubject (String subject) {
		this.subject=subject;
	}
	public void setState (String state) {
		this.state=state;
	}
	public void setReason (String reason) {
		this.reason=reason;
	}
	public void setReceiveTime (java.sql.Timestamp receiveTime) {
		this.receiveTime=receiveTime;
	}
	public void setReceiveTimeStr (String receiveTimeStr) {
		this.receiveTimeStr=receiveTimeStr;
	}
	public void setOperator (String operator) {
		this.operator=operator;
	}
	public void setOrgId (String orgId) {
		this.orgId=orgId;
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

